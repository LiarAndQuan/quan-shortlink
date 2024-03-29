package online.aquan.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.constant.RedisCacheConstant;
import online.aquan.shortlink.admin.common.convention.exception.ClientException;
import online.aquan.shortlink.admin.common.convention.exception.ServiceException;
import online.aquan.shortlink.admin.common.enums.UserErrorCodeEnums;
import online.aquan.shortlink.admin.dao.entity.UserDo;
import online.aquan.shortlink.admin.dao.mapper.UserMapper;
import online.aquan.shortlink.admin.dto.req.UserLoginReqDto;
import online.aquan.shortlink.admin.dto.req.UserRegisterReqDto;
import online.aquan.shortlink.admin.dto.req.UserUpdateReqDto;
import online.aquan.shortlink.admin.dto.resp.UserLoginResDto;
import online.aquan.shortlink.admin.dto.resp.UserRespDto;
import online.aquan.shortlink.admin.service.GroupService;
import online.aquan.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static online.aquan.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static online.aquan.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;
import static online.aquan.shortlink.admin.common.enums.UserErrorCodeEnums.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    //布隆过滤器
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final GroupService groupService;

    /**
     * 根据用户名查询用户的信息
     */
    @Override
    public UserRespDto getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> lambdaQueryWrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(lambdaQueryWrapper);
        if (userDo == null) {
            throw new ServiceException(UserErrorCodeEnums.USER_NULL);
        }
        UserRespDto result = new UserRespDto();
        BeanUtils.copyProperties(userDo, result);
        return result;
    }

    /**
     * 查询用户名是否存在,如果不使用布隆过滤器和redis,那么数据库的压力会很大
     */
    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    /**
     * 用户注册
     */
    @Override
    public void register(UserRegisterReqDto requestParam) {
        if (hasUsername(requestParam.getUsername())) {
            throw new ClientException(UserErrorCodeEnums.USER_EXIST);
        }
        //如果大量请求携带着同一个未注册的用户名过来,那么需要使用分布式锁防止系统崩溃,使用用户名作为锁
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            //如果拿到了锁,就插入
            if (lock.tryLock()) {
                try {
                    int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDo.class));
                    if (inserted < 1) {
                        throw new ClientException(USER_REGISTER_ERROR);
                    }
                } catch (DuplicateKeyException e) {
                    throw new ClientException(USER_EXIST);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                groupService.saveGroup(requestParam.getUsername(), "默认分组");
                return;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 更新用户信息
     */
    @Override
    public void update(UserUpdateReqDto requestParam) {
        if (!hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NULL);
        }
        UserDo userDo = new UserDo();
        BeanUtil.copyProperties(requestParam, userDo);
        LambdaUpdateWrapper<UserDo> wrapper = Wrappers.lambdaUpdate(UserDo.class)
                .eq(UserDo::getUsername, requestParam.getUsername());
        baseMapper.update(userDo, wrapper);
    }

    /**
     * 用户登录功能
     *
     * @param requestParam username和password
     * @return token
     */
    @Override
    public UserLoginResDto login(UserLoginReqDto requestParam) {
        LambdaQueryWrapper<UserDo> wrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, requestParam.getUsername())
                .eq(UserDo::getPassword, requestParam.getPassword())
                .eq(UserDo::getDelFlag, 0);
        UserDo userDo = baseMapper.selectOne(wrapper);
        if (userDo == null) {
            throw new ClientException(USER_NULL);
        }
        //用户已经登录返回对应的token
        Map<Object, Object> loginMap = stringRedisTemplate.opsForHash().entries(USER_LOGIN_KEY + requestParam.getUsername());
        if (CollUtil.isNotEmpty(loginMap)) {
            //用户已经登录,刷新持续时间
            stringRedisTemplate.expire(USER_LOGIN_KEY + requestParam.getUsername(), 30L, TimeUnit.MINUTES);
            String token = loginMap.keySet().stream().findFirst().map(Object::toString).orElseThrow(() -> new ClientException("用户登录错误"));
            return new UserLoginResDto(token);
        }

        /*
         * 接下来存入redis的结构如下:
         * Key: login_用户名
         * Value:
         *   Key: token
         *   value: Json 字符串(用户信息)
         * */

        //首先生成uuid作为token
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(USER_LOGIN_KEY + requestParam.getUsername(), uuid, JSON.toJSONString(userDo));
        stringRedisTemplate.expire(USER_LOGIN_KEY + requestParam.getUsername(), 30, TimeUnit.DAYS);
        return new UserLoginResDto(uuid);
    }

    /**
     * 判断是否已经登录
     */
    @Override
    public Boolean hasLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }


    /**
     * 退出登录,并且需要删除redis里面的信息
     */
    @Override
    public void logout(String username, String token) {
        if (!hasLogin(username, token)) {
            throw new ClientException(USER_NOT_LOGIN);
        }
        stringRedisTemplate.delete(USER_LOGIN_KEY + username);
    }
}
