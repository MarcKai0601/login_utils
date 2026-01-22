package org.loginutils.mgr.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.loginutils.common.utils.hash.MD5Utils;
import org.loginutils.dal.mappers.UserMapper;
import org.loginutils.dal.model.UserDo;
import org.loginutils.mgr.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
@Slf4j
public class UserService {

    @Resource
    UserMapper userMapper;

    @Transient
    public void addUser(UserDo user){

        String password = user.getPassword();

//      生成密碼
//        if (StringUtils.isEmpty(password)){
//            password = UserUtil.generatePassword();
//        }
        user.setPassword(MD5Utils.getMD5String(password));

        userMapper.insert(user);
    }
}
