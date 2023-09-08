package com.example.controller;

import com.example.config.JwtUtil;
import com.example.pojo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理用户登录的功能
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // 调用userService的authenticate方法来验证用户的用户名和密码
        boolean isAuthenticated = userService.authenticate(user.getUsername(), user.getPassword());

        // 如果用户认证成功
        if (isAuthenticated) {
            // 使用jwtUtil生成一个JWT令牌
            String token = jwtUtil.generateToken(user.getUsername());
            // 返回该令牌和HTTP状态码200
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            // 如果用户认证失败，返回"登录失败"和HTTP状态码401（未授权）
            return new ResponseEntity<>("登录失败", HttpStatus.UNAUTHORIZED);
        }
    }
}
