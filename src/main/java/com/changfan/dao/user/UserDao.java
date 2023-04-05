package com.changfan.dao.user;

import com.changfan.pojo.Role;
import com.changfan.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    // 得到要登陆的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException;

    // 修改当前用户的密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException;

    // 查询用户数量
    public int getUserCount(Connection connection, String name, int role) throws SQLException;

    // 查询用户
    public List<User> getUserList(Connection connection, String name, int role,int currentPageNo,int pageSize ) throws SQLException;

}