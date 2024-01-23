package online.aquan.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.aquan.shortlink.admin.common.convention.exception.ServiceException;
import online.aquan.shortlink.admin.common.enums.UserErrorCodeEnums;
import online.aquan.shortlink.admin.dao.eneity.UserDo;
import online.aquan.shortlink.admin.dao.mapper.UserMapper;
import online.aquan.shortlink.admin.dto.resp.UserRespDto;
import online.aquan.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {
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
}
