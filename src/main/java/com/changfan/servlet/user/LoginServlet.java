package com.changfan.servlet.user;

import com.changfan.pojo.User;
import com.changfan.service.user.UserServiceImpl;
import com.changfan.util.constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    //servlet:控制层 调用业务层
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--Start....");

        // 获取参数
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //和数据库对比 调用业务层
        UserServiceImpl userService = new UserServiceImpl();

        try {
            User user = userService.login(userCode, userPassword);
            if (user != null && user.getUserPassword().equals(userPassword)) {
                req.getSession().setAttribute(constants.USER_SESSION, user);
                req.setAttribute("data", user);
                resp.sendRedirect("/jsp/frame.jsp");
            } else {
                req.setAttribute("error", "用户名或者密码不正确");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
