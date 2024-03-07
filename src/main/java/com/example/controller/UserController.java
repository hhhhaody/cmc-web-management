package com.example.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.config.JwtUtil;
import com.example.pojo.*;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理用户登录的功能
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录功能
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody User user) {
        User foundUser = userService.findUserByUsername(user.getUsername());

        // 检查用户名是否存在及密码是否正确
        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 检查用户是否被禁用
        if (foundUser.getStatus() != null && foundUser.getStatus().equals(0)) {
            return Result.error("用户已禁用");
        }

        // 用户验证成功，生成JWT令牌，创建一个包含用户信息的 Map
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", foundUser.getId());
        claims.put("username", foundUser.getUsername());
        claims.put("name", foundUser.getName());
        claims.put("phone", foundUser.getPhone());
        claims.put("sex", foundUser.getSex());
        claims.put("idNumber", foundUser.getIdNumber());
        claims.put("status", foundUser.getStatus());
        claims.put("adminType", foundUser.getAdminType());
        // 添加其他需要的字段

        // 生成JWT令牌
        String token = jwtUtil.generateToken(claims);
        return Result.success(token);
    }

    /**
     * 分页获取用户信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @param username
     * @param phone
     * @param adminType
     * @param status
     * @return
     */
    @GetMapping("userInfo")
    public Result userInfoPage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               Long id, String name, String username, String phone, String adminType, Integer status) {
        log.info("分页查询，参数：{},{},{},{},{},{},{}", page, pageSize, id, name, username, phone, adminType, status);
        PageBean pageBean = userService.page(page, pageSize, id, name, username, phone, adminType, status);
        return Result.success(pageBean);
    }

    /**
     * 添加用户
     *
     * @param user
     * @param token
     * @return
     */
    @PostMapping("/addUserInfo")
    public ResponseEntity<Result> addUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            userService.insert(user, token);
            return ResponseEntity.ok(Result.success("添加成功"));
        } catch (RuntimeException e) {
            if ("用户名已存在".equals(e.getMessage())) {
                // 用户名已存在的错误处理
                // 修改这里，使其返回200 OK，但在body中指明错误
                return ResponseEntity.ok(Result.error("用户名已存在，请选择其他用户名"));
            } else {
                // 其他运行时异常的处理
                // 依然返回500状态码，表示服务器内部错误
                return ResponseEntity.internalServerError().body(Result.error("内部服务器错误"));
            }
        } catch (Exception e) {
            // 处理其他非运行时异常
            return ResponseEntity.internalServerError().body(Result.error("内部服务器错误"));
        }
    }

    /**
     * 根据用户id查询信息
     *
     * @param user_id
     * @return
     */
    @GetMapping("/{user_id}")
    public Result getById(@PathVariable Long user_id) {
        log.info("根据id查询用户信息， id：{}", user_id);
        User user = userService.getById(user_id);
        return Result.success(user);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @PutMapping("/updateUser")
    public Result update(@RequestBody User user, @RequestHeader("Authorization") String token) {
        log.info("更新用户信息");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        userService.update(user, token);
        return Result.success("更新成功");
    }

    /**
     * 更新用户状态
     *
     * @param user
     * @param token
     * @return
     */
    @PutMapping("/updateUserStatus")
    public Result updateUserStatus(@RequestBody User user, @RequestHeader("Authorization") String token) {
        log.info("更新用户状态");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        userService.updateUserStatus(user, token);
        return Result.success("更新成功");
    }

    /**
     * 重置密码功能
     *
     * @param userId
     * @param token
     * @return
     */
    @PutMapping("/{userId}/resetPassword")
    public Result updatePassword(@PathVariable("userId") Long userId, @RequestHeader("Authorization") String token) {
        log.info("更新用户密码，用户ID：{}", userId);

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        userService.updateUserPassword(userId, token);
        return Result.success("重置密码成功");
    }

    /**
     * 个人中心修改密码功能
     *
     * @param passwordUpdateDto
     * @param token
     * @return
     */
    @PutMapping("/updatePassword")
    public ResponseEntity<Result> updatePersonalPassword(@RequestBody PasswordUpdateDto passwordUpdateDto, @RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            userService.updatePersonalCenterPassword(passwordUpdateDto.getOldPassword(), passwordUpdateDto.getNewPassword(), token);
            return ResponseEntity.ok(Result.success("密码修改成功"));
        } catch (RuntimeException e) {
            // 捕获旧密码不正确的异常
            if ("旧密码不正确".equals(e.getMessage())) {
                // 旧密码不正确的错误处理，返回200 OK，但在body中指明错误
                return ResponseEntity.ok(Result.error("   ，请重新输入"));
            } else {
                // 其他运行时异常的处理
                return ResponseEntity.internalServerError().body(Result.error("内部服务器错误"));
            }
        } catch (Exception e) {
            // 处理其他非运行时异常
            return ResponseEntity.internalServerError().body(Result.error("内部服务器错误"));
        }
    }


    /**
     * 删除用户
     *
     * @param user_id
     * @return
     */
    @DeleteMapping("/{user_id}")
    public Result deleteUserById(@PathVariable Long user_id) {
        log.info("根据id删除用户：{}", user_id);

        userService.deleteUserById(user_id);
        return Result.success("删除成功");
    }

    /**
     * 搜索框联想查询
     *
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field) {
        log.info("根据field查询已有数据：{}", field);

        List<Value> res = userService.searchField(field);
        return Result.success(res);
    }

    @PostMapping("/searchAdvance/{field}")
    public Result searchAdvanceSuggestion(@RequestBody User user, @PathVariable String field) {
        log.info("根据用户已输入信息查询已有数据：{},{}", user, field);

        List<Value> res = userService.searchAdvance(user, field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     *
     * @param user
     * @param field
     * @return
     */
    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody User user, @PathVariable String field) {
        log.info("根据用户已输入信息查询已有数据：{},{}", user, field);

        List<Value> res = userService.search(user, field);
        return Result.success(res);
    }
}
