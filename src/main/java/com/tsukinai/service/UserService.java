package com.tsukinai.service;

import com.tsukinai.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findByUsername(String username);

    //注册
    void register(String username, String password);

    //更新用户信息
    void update(User user);
}
