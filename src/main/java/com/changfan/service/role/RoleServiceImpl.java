package com.changfan.service.role;

import com.changfan.dao.BaseDao;
import com.changfan.dao.role.RoleDao;
import com.changfan.dao.role.RoleDaoImpl;
import com.changfan.pojo.Role;
import com.changfan.pojo.User;
import com.changfan.service.user.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }


    public List<Role> getRole() throws SQLException {
        Connection connection = null;
        List<Role> roles = null;
        connection = BaseDao.getConnection();
        roles = roleDao.getRoleList(connection);
        return roles;
    }

//    @Test
//    public void test() throws SQLException {
//        RoleServiceImpl roleService = new RoleServiceImpl();
//        List<Role> role = roleService.getRole();
//        System.out.println(Arrays.toString(role.toArray()));
//    }

}
