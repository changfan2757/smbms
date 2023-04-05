package com.changfan.dao.role;

import com.changfan.dao.BaseDao;
import com.changfan.pojo.Role;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    public List<Role> getRoleList(Connection connection) throws SQLException {
        ResultSet res = null;
        List<Role> roleList = new ArrayList<>();

        if (connection != null) {
            String sql = "select * FROM smbms_role";
            Object[] params = {};
            res = BaseDao.query(connection, sql, params);
            while (res.next()) {
                Role _role = new Role();
                _role.setId(res.getInt("id"));
                _role.setRoleCode(res.getString("roleCode"));
                _role.setRoleName(res.getString("roleName"));
                roleList.add(_role);
            }
            BaseDao.closeResource(connection, null, res);
        }

        return roleList;
    }

    @Test
    public void test() throws SQLException {
        RoleDaoImpl roleDao = new RoleDaoImpl();
        List<Role> roleList = roleDao.getRoleList(BaseDao.getConnection());
        System.out.println(Arrays.toString(roleList.toArray()));

    }
}
