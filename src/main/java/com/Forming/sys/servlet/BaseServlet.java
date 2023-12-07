package com.Forming.sys.servlet;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;

//系统公共的Servlet，约定客户端提交的请求中的action就是在Servlet中方法的名称
public abstract class BaseServlet extends HttpServlet {

    protected PageUtils pageUtils;


    /*通过反射的方式调用对象中的方法*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取传递的action参数
        String action = req.getParameter("action");
        try {
            //       获取当前对象对应的Method对象
            Method Method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            //       调用方法
            Method.invoke(this, req, resp);

        } catch (Exception e) {
            //调用方法执行的时候出现了异常
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    //定义增删改查的基础方法
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        pageUtils = new PageUtils();
        //查询前端所传递来的分页参数
        String ps = req.getParameter("pageSize");
        String pn = req.getParameter("pageNum");
        String key = req.getParameter("key");
        //声明默认的分页参数
        int pageSize = 5;// 默认显示5条
        int pageNum = 1;//默认的显示第一页
        if (StringUtils.isNotEmpty(ps)) {//如果不是空
            pageSize = Integer.parseInt(ps);

        }
        if (StringUtils.isNotEmpty(pn)) {//如果不是空
            pageNum = Integer.parseInt(pn);
        }
        if (StringUtils.isNotEmpty(key)) {
            //如果条件不为空，则设置当前页面为  1
            pageNum = 1;
        }
        pageUtils.setKey(key);
        pageUtils.setPageNum(pageNum);
        pageUtils.setPageSize(pageSize);
    }

    public abstract void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    public abstract void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    public abstract void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    public abstract void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    public SysUser getCurrentLoginUser(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession();
        Object obj = session.getAttribute(Constant.LOGIN_USER);
       SysUser loginUser = null;//   当前登录的用户
        if (obj != null) {
            loginUser = (SysUser) obj;
            String rolename = loginUser.getRolename();
            if (rolename.contains("管理员")) {
                //当前用户是管理员
                loginUser.setIsAdmin(true);
            } else {  //普通用户
                loginUser.setIsAdmin(false);
            }
        }
        return loginUser;
    }
}
