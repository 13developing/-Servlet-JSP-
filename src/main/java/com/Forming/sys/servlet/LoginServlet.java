package com.Forming.sys.servlet;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.service.IMenuService;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.service.impl.MenuServiceImpl;
import com.Forming.sys.service.impl.UserServiceImpl;
import com.Forming.sys.utils.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.List;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = {"/sys/loginServlet"})
public class LoginServlet extends HttpServlet {
    private String msg = "欢迎登录";
    private IUserService service = new UserServiceImpl();
    private IMenuService menuService = new MenuServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //做登录认证的操作
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        //根据账号去数据库查询记录
        SysUser user = service.findByName(userName);
        if (user == null) {   // 账号不存在
            req.setAttribute("msg", "登录失败,账号不存在");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else if (!user.getPassword().equals(password)) {//  账号存在但是 密码错误
            req.setAttribute("msg", "登录失败,密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {  // login success
            HttpSession session = req.getSession();
            user.setPassword(null);//   记录账号，但是把密码置空
            session.setAttribute(Constant.LOGIN_USER, user);
            //登录成功后需要获取当前登录用户的菜单信息
            Integer roleId = user.getRoleId();
            if (roleId != null) {
                //查询对应的用户菜单信息
                List<SysMenu> menus = menuService.findByRoleId(roleId);
                session.setAttribute(Constant.LOGIN_MENUS, menus);
            }
            resp.sendRedirect("/main.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
