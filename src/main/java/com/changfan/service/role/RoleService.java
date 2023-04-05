package com.changfan.service.role;

import com.changfan.pojo.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleService {
    public List<Role> getRole() throws SQLException;
}
