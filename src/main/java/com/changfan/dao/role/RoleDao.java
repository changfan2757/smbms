package com.changfan.dao.role;

import com.changfan.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    //查询角色
    public List<Role> getRoleList(Connection connection ) throws SQLException;
}
