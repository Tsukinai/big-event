package com.tsukinai.controller;

import com.tsukinai.pojo.Result;
import com.tsukinai.pojo.User;
import com.tsukinai.service.UserService;
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

}
