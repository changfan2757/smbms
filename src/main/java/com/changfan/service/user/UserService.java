package com.changfan.service.user;

import com.changfan.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    // 用户登陆
    public User login(String userCode, String password) throws SQLException;

    // 根据用户ID修改密码
    public boolean updatePwd(int id, String passwd) throws SQLException;

    // 查询用户数量
    public int getUserCount(String username, int role) throws SQLException;

    public List<User> getUserList(String username, int role, int currentPageNo, int pageSize) throws SQLException;

}
