package com.Forming.sys.servlet;

import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.service.IRoleService;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.service.impl.RoleServiceImpl;
import com.Forming.sys.service.impl.UserServiceImpl;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "userServlet", urlPatterns = {"/sys/userServlet"})
public class UserServlet extends BaseServlet {
    private IUserService service = new UserServiceImpl();
    private IRoleService roleService = new RoleServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);//调用父类中的方法完成分页数据的处理

        //下面就开始做分页的查询
        service.listPage(pageUtils);
        //把查询到的数据都存储在 request 作用域中
        req.setAttribute("pageUtils", pageUtils);
        //通过服务端转发的方式，跳转页面
        req.getRequestDispatcher("/sys/user/list.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        String id = req.getParameter("id");
        String img = req.getParameter("img");
        String roleId = req.getParameter("roleId");
        SysUser user = new SysUser();   //  注意这里是lombok插件 的注解@data 功劳

        if (StringUtils.isNotEmpty(roleId)) {
            //根据角色的id查询出对应的信息
            SysRole role = roleService.findById(Integer.parseInt(roleId));
            user.setRoleId(Integer.parseInt(roleId));
            user.setRolename(role.getName());
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setImg(img);
        if(StringUtils.isNotEmpty(id)) {
        //表示的是更新操作
        user.setId(Integer.parseInt(id));
        service.updateById(user);//更新用户信息

    } else {
        //表示的是添加操作
        // 调用service的方法去完成存储
        service.save(user);
    }
    //要做数据的查询
        resp.sendRedirect("/sys/userServlet?action="+Constant.BASE_ACTION_LIST); // 重定向页面
}

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //查询出所有的身份角色信息
        List<SysRole> roles = roleService.list(null);
        req.setAttribute("roles", roles);
        //需获取客户端提交的id信息
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            //说明是更新操作，需要更具id查询具体的信息
            SysUser user = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", user);
        }
        req.getRequestDispatcher("/sys/user/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        int count = service.deleteById(Integer.parseInt(id));
        PrintWriter writer = resp.getWriter();
        writer.write(count + "");
        writer.flush();
        writer.close();
    }
    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
