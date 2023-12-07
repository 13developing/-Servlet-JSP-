package com.Forming.sys.servlet;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.service.impl.UserServiceImpl;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "modifyUserInfoServlet", urlPatterns = {"/sys/modifyUserInfoServlet"})
public class modifyUserInfoServlet extends HttpServlet {
    private IUserService service = new UserServiceImpl();
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取传递的action参数
        String action = req.getParameter("action");
        try {
            //       获取当前对象对应的Method对象
            Method Method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //       调用方法
            Method.setAccessible(true);
            Method.invoke(this, req, resp);

        } catch (Exception e) {
            //调用方法执行的时候出现了异常
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    public void modify(HttpServletRequest req, HttpServletResponse resp) throws Exception {  // 修改头像
        String id = req.getParameter("id");
        String img = req.getParameter("img");
        String password = req.getParameter("password");
        SysUser user = service.findById(Integer.parseInt(id));
        if (StringUtils.isNotEmpty(img)) {
            user.setImg(img);
        }
        if (StringUtils.isNotEmpty(password)) {
            user.setPassword(password);
        }
        service.updateById(user);

        resp.sendRedirect("/sys/modifyUserInfoServlet?action=ListInfo"); // 重定向页面
    }

    public void modifyPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {  // 修改头像
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            SysUser user = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", user);
        }
        req.getRequestDispatcher("/sys/modify/modifyPage.jsp").forward(req, resp);
    }

    /**
     * 展示个人的资料
     */
    public void ListInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        SysUser user = service.findById(Integer.parseInt(id));
        req.setAttribute("personalInf", user);
        req.getRequestDispatcher("/sys/modify/list.jsp").forward(req, resp);
    }
}
