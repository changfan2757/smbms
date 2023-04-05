package com.changfan.service.user;


import com.changfan.dao.BaseDao;
import com.changfan.dao.user.UserDao;
import com.changfan.dao.user.UserDaoImpl;
import com.changfan.pojo.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    //业务层都会调用dao层，所以我们要引入Dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    public User login(String userCode, String password) throws SQLException {
        Connection connection = null;
        User user = null;

        connection = BaseDao.getConnection();
        user = userDao.getLoginUser(connection, userCode);
        BaseDao.closeResource(connection, null, null);
        return user;
    }

    public boolean updatePwd(int id, String passwd) throws SQLException {
        Connection connection = null;
        boolean flag = false;

        connection = BaseDao.getConnection();
        if (userDao.updatePwd(connection, id, passwd) > 0) {
            flag = true;
        }
        BaseDao.closeResource(connection, null, null);

        return flag;
    }

    public int getUserCount(String username, int role) throws SQLException {
        Connection connection = null;
        int num = 0;
        connection = BaseDao.getConnection();


        num = userDao.getUserCount(connection, username, role);

        BaseDao.closeResource(connection, null, null);

        return num;
    }


    public List<User> getUserList(String username, int role, int currentPageNo, int pageSize) throws SQLException {
        Connection connection = null;
        List<User> users = null;
        connection = BaseDao.getConnection();

        System.out.println("username--->" + username);
        System.out.println("role--->" + role);
        System.out.println("currentPageNo--->" + currentPageNo);
        System.out.println("pageSize--->" + pageSize);

        users = userDao.getUserList(connection, username, role, currentPageNo, pageSize);

        BaseDao.closeResource(connection, null, null);
        return users;
    }

//    @Test
//    public void test() throws SQLException {
//        UserServiceImpl userService = new UserServiceImpl();
//        int count = userService.getUserCount("changfan", 0);
//        System.out.println(count);
//    }
}
