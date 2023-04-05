package com.changfan.dao.user;

import com.changfan.dao.BaseDao;
import com.changfan.pojo.Role;
import com.changfan.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        User user = null;
        if (null != connection) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            // 查询sql
            ResultSet rs = BaseDao.query(connection, sql, params);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        int result = 0;

        if (connection != null) {
            PreparedStatement pstm = null;
            String sql = "update smbms_user set userPassword=? where id=?";
            Object params[] = {password, id};
            result = BaseDao.execute(connection, sql, params);
            BaseDao.closeResource(connection, pstm, null);
        }

        return result;
    }


    public int getUserCount(Connection connection, String name, int role) throws SQLException {
        int result = 0;
        ResultSet res = null;

        if (connection != null) {
            PreparedStatement pstm = null;

            ArrayList<Object> arrayList = new ArrayList<>();
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user left join smbms_role sr ON smbms_user.userRole = sr.id");

            if (!StringUtils.isNullOrEmpty(name)) {
                sql.append(" where smbms_user.userName like ?");
                arrayList.add('%' + name + '%');
            }

            if (role > 0) {
                sql.append(" AND sr.roleName=?");
                arrayList.add(role);
            }


            res = BaseDao.query(connection, sql.toString(), arrayList.toArray());
            if (res.next()) {
                result = res.getInt("count");
            }

            BaseDao.closeResource(connection, pstm, res);
        }

        return result;
    }

    public List<User> getUserList(Connection connection, String name, int role, int currentPageNo, int pageSize) throws SQLException {
        ResultSet res = null;
        List<User> users = new ArrayList<>();

        if (connection != null) {
            ArrayList<Object> arrayList = new ArrayList<>();
            StringBuffer sql = new StringBuffer();
            sql.append("select smbms_user.* from smbms_user left join smbms_role sr on smbms_user.userRole = sr.id");

            if (!StringUtils.isNullOrEmpty(name)) {
                sql.append(" where smbms_user.userName like ?");
                arrayList.add('%' + name + '%');
            }

            if (role > 0) {
                sql.append(" AND sr.roleName=?");
                arrayList.add(role);
            }

            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            arrayList.add(currentPageNo);
            arrayList.add(pageSize);

            System.out.println("sql--->" + sql.toString());
            res = BaseDao.query(connection, sql.toString(), arrayList.toArray());

            while (res.next()) {
                User _user = new User();
                _user.setId(res.getInt("id"));
                _user.setUserName(res.getString("userName"));
                _user.setGender(res.getInt("gender"));
                _user.setBirthday(res.getDate("birthday"));
                _user.setPhone(res.getString("phone"));
                _user.setAddress(res.getString("address"));
                _user.setUserRole(res.getInt("userRole"));
                _user.setCreationDate(res.getDate("creationDate"));
                users.add(_user);
            }
            BaseDao.closeResource(connection, null, res);
        }

        return users;
    }
}
