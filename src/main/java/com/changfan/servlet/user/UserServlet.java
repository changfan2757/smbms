package com.changfan.servlet.user;

import com.changfan.pojo.Role;
import com.changfan.pojo.User;
import com.changfan.service.role.RoleServiceImpl;
import com.changfan.service.user.UserServiceImpl;
import com.changfan.util.PageSupport;
import com.changfan.util.constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("query")) {
            try {
                this.query(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (method.equals("savepwd")) {
            this.savePwd(req, resp);
        }
    }

    public void query(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {

        // 从前端获取数据
        String queryname = req.getParameter("queryname");
        String tmp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");

        UserServiceImpl userService = new UserServiceImpl();

        // 第一次走这个请求
        int pageSize = 5;
        int currentPageNo = 1;

        if (queryname == null) {
            queryname = "";
        }

        int queryUserRole = 0;
        if (tmp != null && tmp.equals("")) {
            queryUserRole = Integer.parseInt(tmp);
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户总数(分页：上一页 下一页的情况)
        int userCount = userService.getUserCount(queryname, queryUserRole);
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(userCount);

        int totalCount = pageSupport.getTotalCount();
        if (totalCount < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalCount) {
            currentPageNo = totalCount;
        }

        // 获取用户列表
        List<User> userList = userService.getUserList(queryname, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList", userList);

        // 获取角色列表
        RoleServiceImpl roleService = new RoleServiceImpl();

        List<Role> role = roleService.getRole();
        req.setAttribute("roleList", role);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", pageSupport.getTotalPageCount());

        req.getRequestDispatcher("userlist.jsp").forward(req, resp);

    }


    public void savePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 从Session里面拿到ID
        Object attribute = req.getSession().getAttribute(constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        UserServiceImpl userService = new UserServiceImpl();
        boolean flag = false;
        if (attribute != null && !StringUtils.isNullOrEmpty(newpassword)) {
            try {
                flag = userService.updatePwd(((User) attribute).getId(), newpassword);
                if (flag) {
                    req.setAttribute("message", "新密码设置成功！请退出，请使用新密码登陆！");
                    req.getSession().removeAttribute(constants.USER_SESSION);
                } else {
                    req.setAttribute("message", "新密码设置失败！！！");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            req.setAttribute("message", "新密码有问题！！！");
        }

        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
