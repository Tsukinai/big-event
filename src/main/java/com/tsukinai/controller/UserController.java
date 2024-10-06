package com.tsukinai.controller;

import com.tsukinai.pojo.Result;
import com.tsukinai.pojo.User;
import com.tsukinai.service.UserService;
import com.tsukinai.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @RequestMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询数据库是否存在该用户
        User u = userService.findByUsername(username);
        if (u != null) {
            //用户已存在
            return Result.error("该用户已存在");
        } else {
            //注册
            userService.register(username, password);
            return Result.success();
        }

    }

    //登录
    @RequestMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询数据库是否存在该用户
        User loginUser = userService.findByUsername(username);
        if (loginUser == null) {
            //用户不存在
            return Result.error("用户不存在");
        } else {
            //判断密码是否正确
            if (loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
                return Result.success("登录成功");
            } else {
                return Result.error("密码错误");
            }
        }
    }

}
