package com.Forming.sys.servlet;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.service.IMenuService;
import com.Forming.sys.service.impl.MenuServiceImpl;
import com.Forming.sys.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "menuServlet", urlPatterns = {"/sys/menuServlet"})
public class MenuServlet extends BaseServlet {
    private IMenuService service = new MenuServiceImpl();

    /**
     * 菜单功能不做分页
     */
    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<SysMenu> list = service.list(null);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/sys/menu/list.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//获取表单提交的数据
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String url = req.getParameter("url");
        String seq = req.getParameter("seq");
        String parentId = req.getParameter("parentId");
        SysMenu menu = new SysMenu();
        menu.setUrl(url);
        menu.setName(name);

        if (StringUtils.isNotEmpty(seq)) {
            menu.setSeq(Integer.parseInt(seq));
        }
        if (StringUtils.isNotEmpty(parentId)) {
            menu.setParentId(Integer.parseInt(parentId));
        }
        if (StringUtils.isNotEmpty(id)) {//  判断是不是更新操作
            menu.setId(Integer.parseInt(id));
            service.updateById(menu);
        } else {
            service.save(menu);
            //保存分配的菜单信息   获取id
        }
        resp.sendRedirect("/sys/menuServlet?action=list");

    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //首先要查询的是我们的父菜单  parentId=-1
        List<SysMenu> list = service.getAllParent();
        req.setAttribute("parents", list);
        //判断是否是更新
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            SysMenu entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        req.getRequestDispatcher("/sys/menu/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        //判断 被分配则不可以被删除
        boolean flag = service.isDispatcher(id);
        String msg = "ok";
        if (flag) {
            //表示被分配
            msg = "error";
        } else {
            SysMenu entity = service.findById(id);
            if (entity.getParentId() == -1) {     //需要删除的菜单是父菜单 //有子菜单的父菜单不可以被删除
                flag = service.haveSubMenu(id);
                if (flag) {//有子菜单
                    msg = "error";
                } else {//父菜单无子菜单
                    service.deleteById(id);
                }
            } else {//子菜单可以被删除                          注意这三个事顺序的，有着严密的逻辑关系
                service.deleteById(id);
            }
        }
        System.out.println(msg);
        PrintWriter writer = resp.getWriter();
        writer.write(msg);
        writer.flush();
    }
    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
