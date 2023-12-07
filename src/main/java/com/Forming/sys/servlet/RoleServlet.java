package com.Forming.sys.servlet;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysRoleMenu;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.dao.IRoleDao;
import com.Forming.sys.service.IMenuService;
import com.Forming.sys.service.IRoleService;
import com.Forming.sys.service.impl.MenuServiceImpl;
import com.Forming.sys.service.impl.RoleServiceImpl;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "roleServlet", urlPatterns = {"/sys/roleServlet"})
public class RoleServlet extends BaseServlet {
    private IRoleService service = new RoleServiceImpl();
    private IMenuService menuService = new MenuServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);//封装了分页的相关操作
        //写我们自己的查询处理
        service.listPage(pageUtils);
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        //页面的重定向和跳转
        req.getRequestDispatcher("/sys/role/list.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String name = req.getParameter("name");
        String notes = req.getParameter("notes");
        String id = req.getParameter("id");

        SysRole entity = new SysRole();   //  注意这里是lombok插件 的注解@data 功劳
        entity.setName(name);
        entity.setNotes(notes);

        //获取分配的 菜单的
        String[] menuIds = req.getParameterValues("menuId");

        if (StringUtils.isNotEmpty(id)) {
            int roleId = Integer.parseInt(id);
            //表示的是更新操作
            entity.setId(Integer.parseInt(id));
            service.updateById(entity);//更新用户信息
            //更新分配的菜单
            // 删除当前角色分配的所有菜单
            service.deleteMenuByRoleId(Integer.parseInt(id));
            //插入新分配的菜单
            if (menuIds != null && menuIds.length > 0) {
                for (String menuId : menuIds) {
                    service.saveDispacterMenu(roleId, menuId);
                }
            }
        } else {
            //表示的是添加操作
            // 调用service的方法去完成存储
            service.save(entity);
        }
        //要做数据的查询
        resp.sendRedirect("/sys/roleServlet?action=" + Constant.BASE_ACTION_LIST); // 重定向页面
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //需获取客户端提交的id信息
        String id = req.getParameter("id");
        //查询出所有的菜单信息
        List<SysMenu> menus = menuService.list(null);
        if (StringUtils.isNotEmpty(id)) {
            int roleId = Integer.parseInt(id);
            //根据当前的角色编号查询出已经分配的菜单信息
            List<SysRoleMenu> roleMenus = service.queryByRoleId(roleId);
            if (roleMenus != null && roleMenus.size() > 0) {
                List<Integer> ownerMenus = roleMenus.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
                for (SysMenu menu : menus) {
                    if (ownerMenus.contains(menu.getId())) {
                        menu.setCheck(true);
                    }
                }
            }
            //说明是更新操作，需要更具id查询具体的信息
            SysRole entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        req.setAttribute("menus", menus);
        req.getRequestDispatcher("/sys/role/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        //如果角色已经分配给了用户，那么这个角色就不可以被删除
        boolean flag = service.checkRoleDispatch(Integer.parseInt(id));
        PrintWriter writer = resp.getWriter();
        if (flag) {
            //表示没有被分配可以删除
            service.deleteById(Integer.parseInt(id));
            writer.write("ok");
        } else {
            writer.write("error");
            //表示不可以被删除
        }
        writer.flush();
        writer.close();
    }

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
