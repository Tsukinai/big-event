package com.tsukinai.service.impl;

import com.tsukinai.mapper.UserMapper;
import com.tsukinai.pojo.User;
import com.tsukinai.service.UserService;
import com.tsukinai.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户
     * @param username
     * @return User
     */
    @Override
    public User findByUsername(String username) {
        //查询数据库
        User u = userMapper.findByUsername(username);
        return u;
    }

    /**
     * 注册
     * @param username
     */
    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);

        //保存到数据库
        userMapper.add(username, md5String);
    }
}
