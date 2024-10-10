package com.tsukinai.controller;

import com.tsukinai.pojo.Result;
import com.tsukinai.pojo.User;
import com.tsukinai.service.UserService;
import com.tsukinai.utils.JwtUtil;
import com.tsukinai.utils.Md5Util;
import com.tsukinai.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
                Map<String, Object> claims = new HashMap<>();
                //通过id和用户名生成token
                claims.put("id", loginUser.getId());
                claims.put("username", loginUser.getUsername());
                String token = JwtUtil.genToken(claims);
                return Result.success(token);
            } else {
                return Result.error("密码错误");
            }
        }
    }

    //获取用户信息
    @RequestMapping("/userInfo")
    public Result<User> userInfo() {
        //根据用户名查询用户
        //Map<String, Object> map = JwtUtil.parseToken(token);
        //String username = (String) map.get("username");

        //从ThreadLocal中获取用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    //更新用户信息
    @PutMapping ("/update")
    public Result update(@RequestBody User user) {
        //更新用户信息
        userService.update(user);
        return Result.success();
    }

}
