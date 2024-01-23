package online.aquan.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.exception.ServiceException;
import online.aquan.shortlink.admin.common.enums.UserErrorCodeEnums;
import online.aquan.shortlink.admin.dao.eneity.UserDo;
import online.aquan.shortlink.admin.dao.mapper.UserMapper;
import online.aquan.shortlink.admin.dto.req.UserRegisterReqDto;
import online.aquan.shortlink.admin.dto.resp.UserRespDto;
import online.aquan.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    //布隆过滤器
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    
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

    @Override
    public void register(UserRegisterReqDto requestParam) {
        
    }
}
