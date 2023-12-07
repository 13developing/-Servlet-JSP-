package com.Forming.book.servlet;

import com.Forming.book.bean.Depart;
import com.Forming.book.service.IDepartService;
import com.Forming.book.service.impl.DepartServiceImpl;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(name = "departServlet", urlPatterns = {"/book/departServlet"})
public class DepartServlet extends BaseServlet {
    private IDepartService service = new DepartServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);
        service.listPage(pageUtils);
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        //页面的重定向和跳转
        req.getRequestDispatcher("/book/depart/list.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            //说明是更新操作
            Depart entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        req.getRequestDispatcher("/book/depart/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//接受表单提交的数据
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String notes = req.getParameter("notes");
        Depart depart = new Depart();
        depart.setNotes(notes);
        depart.setName(name);
        if (StringUtils.isNotEmpty(id)) {
            depart.setId(Integer.parseInt(id));
            service.updateById(depart);
        } else {
            //保存数据
            service.save(depart);
        }
        //更新后或者是添加后需要重定向
        resp.sendRedirect("/book/departServlet?action=list");
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        String msg = "ok";
//  这里新增加一个判断   -- 判断我们的学院id有没有被学生分配，如果有，那么就是不可以被删除
        boolean flag = service.isDispatcherById(id);
        if (flag) {
            msg = "error";
        }else {
            service.deleteById(Integer.parseInt(id));
        }
        PrintWriter writer = resp.getWriter();
        writer.write(msg);
        writer.flush();
    }

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
