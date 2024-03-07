package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.User;
import com.example.pojo.Value;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    boolean authenticate(String username, String password);

    PageBean page(Integer page, Integer pageSize, Long id, String name, String username, String phone, String adminType, Integer status);

    void insert(User user, String token) throws Exception;

    /**
     * 根据id查询用户
     * @param user_id
     * @return
     */
    User getById(Long user_id);

    /**
     * 更新用户
     * @param user
     */
    void update(User user, String token);

    /**
     * 更新用户状态
     * @param user
     */
    void updateUserStatus(User user, String token);

    /**
     * 更新用户密码
     * @param userId
     * @param token
     */
    void updateUserPassword(Long userId, String token);

    /**
     * 个人中心修改密码功能
     * @param oldPassword
     * @param newPassword
     * @param token
     */
    void updatePersonalCenterPassword(String oldPassword, String newPassword,String token);

    /**
     * 删除用户
     * @param user_id
     */
    void deleteUserById(Long user_id);

    List<Value> searchField(String field);

    List<Value> search(User user, String field);

    List<Value> searchAdvance(User user, String field);
}
