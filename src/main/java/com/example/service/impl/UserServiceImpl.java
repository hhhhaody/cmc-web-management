package com.example.service.impl;

import com.example.config.JwtUtil;
import com.example.mapper.UserMapper;
import com.example.pojo.PageBean;
import com.example.pojo.User;
import com.example.pojo.Value;
import com.example.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = findUserByUsername(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, Long id, String name, String username, String phone, String adminType, Integer status) {
        PageHelper.startPage(page, pageSize);
        // 确保此处将参数传递给 Mapper
        List<User> userList = userMapper.listAllUsers(name, phone, adminType);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return new PageBean(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void insert(User user, String token) {
        // 从令牌中提取用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(token);

        // 检查用户名是否已存在
        if (usernameExists(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 设置 create_user 和 update_user 字段
        user.setCreateUser(currentUserId);
        user.setUpdateUser(currentUserId);

        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);

        // 插入用户数据
        userMapper.insert(user);
    }

    @Override
    public User getById(Long user_id) {
        return userMapper.getById(user_id);
    }

    @Override
    public void update(User user, String token) {
        // 从令牌中提取用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(token);

        // 设置update_user字段
        user.setUpdateUser(currentUserId);

        userMapper.update(user);
    }

    @Override
    public void updateUserStatus(User user, String token) {
        // 从令牌中提取用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(token);

        // 设置update_user字段
        user.setUpdateUser(currentUserId);

        userMapper.updateUserStatus(user);
    }

    @Override
    public void updateUserPassword(Long userId, String token) {
        // 从令牌中提取用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(token);

        //新密码
        String newPassword = "Zjkj@2023";

        userMapper.updateUserPassword(userId, newPassword, currentUserId);
    }

    @Override
    public void updatePersonalCenterPassword(String oldPassword, String newPassword, String token) {
        // 从令牌中提取用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(token);

        // 获取当前用户的信息，主要是为了获取旧密码
        User currentUser = userMapper.getById(currentUserId);

        // 验证旧密码是否正确
        if (oldPassword.equals(currentUser.getPassword())) {
            // 如果旧密码正确，直接使用新密码更新数据库
            userMapper.updateUserPassword(currentUserId, newPassword, currentUserId);
        } else {
            // 如果旧密码不正确，抛出一个异常或返回错误信息
            throw new RuntimeException("旧密码不正确");
        }
    }

    @Override
    public void deleteUserById(Long user_id) {
        userMapper.deleteUserById(user_id);
    }

    @Override
    public List<Value> searchField(String field) {
        return userMapper.searchField(field);
    }

    @Override
    public List<Value> search(User user, String field) {
        String name = user.getName();
        String phone = user.getPhone();
        String adminType = user.getAdminType();
        return userMapper.search(name, phone, adminType, field);
    }

    @Override
    public List<Value> searchAdvance(User user, String field) {
        String name = user.getName();
        String phone = user.getPhone();
        String adminType = user.getAdminType();
        return userMapper.searchAdvance(name, phone, adminType, field);
    }

    private boolean usernameExists(String username) {
        return userMapper.findUserByUsername(username) != null;
    }
}
