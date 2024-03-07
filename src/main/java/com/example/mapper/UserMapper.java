package com.example.mapper;

import com.example.pojo.User;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findUserByUsername(String username);

    // 获取用户信息
    @Select("<script>"
            + "SELECT id, name, username, phone, sex, admin_type AS adminType, status, update_time "
            + "FROM users "
            + "<where>"
            + "   <if test='name != null and !name.trim().isEmpty()'>"
            + "       AND name LIKE CONCAT('%',#{name},'%')"
            + "   </if>"
            + "   <if test='phone != null and !phone.trim().isEmpty()'>"
            + "       AND phone LIKE CONCAT('%',#{phone},'%')"
            + "   </if>"
            + "   <if test='adminType != null and !adminType.isEmpty()'>"
            + "       AND admin_type = #{adminType}"
            + "   </if>"
//            + "   <if test='adminType != null and !adminType.trim().isEmpty()'>"
//            + "       AND admin_type LIKE CONCAT('%',#{adminType},'%')"
//            + "   </if>"
            + "</where>"
            + "ORDER BY update_time DESC"
            + "</script>")
    List<User> listAllUsers(@Param("name") String name, @Param("phone") String phone, @Param("adminType") String adminType);

    // 插入用户信息
    @Insert("INSERT INTO users (name, username, password, phone, sex, id_number, admin_type, create_user, update_user, create_time, update_time) VALUES (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{adminType}, #{createUser}, #{updateUser}, #{createTime}, #{updateTime})")
    void insert(User user);

    /**
     * 根据id查询用户
     *
     * @param user_id
     * @return
     */
    @Select("SELECT * FROM users WHERE id = #{user_id}")
    User getById(Long user_id);

    /**
     * 更新用户信息
     *
     * @param user
     */
    @Update("UPDATE users SET name = #{name}, username = #{username}, phone = #{phone}, sex = #{sex}, admin_type = #{adminType}, status = #{status}, update_user = #{updateUser}, update_time = NOW() WHERE id = #{id}")
    void update(User user);

    /**
     * 更新用户状态
     *
     * @param user
     */
    @Update("UPDATE users SET status = #{status}, update_user = #{updateUser}, update_time = NOW() WHERE id = #{id}")
    void updateUserStatus(User user);

    /**
     * 更新用户密码
     *
     * @param userId
     * @param password
     * @param updateUser
     */
    @Update("UPDATE users SET password = #{password}, update_user = #{updateUser}, update_time = NOW() WHERE id = #{userId}")
    void updateUserPassword(@Param("userId") Long userId, @Param("password") String password, @Param("updateUser") Long updateUser);

    /**
     * 删除用户
     *
     * @param user_id
     */
    @Delete("DELETE from users where id = #{user_id}")
    void deleteUserById(Long user_id);

    @Select("select distinct ${field} as value from users order by value")
    List<Value> searchField(String field);

    @Select("SELECT distinct ${field} as value from users order by value")
    List<Value> search(String name, String phone, String adminType, String field);

    @Select("SELECT distinct ${field} as value from users order by value")
    List<Value> searchAdvance(String name, String phone, String adminType, String field);
}
