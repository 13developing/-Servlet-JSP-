package com.Forming.book.servlet;

import com.Forming.book.bean.Classes;
import com.Forming.book.bean.Depart;
import com.Forming.book.dao.IClassesDao;
import com.Forming.book.service.IClassesService;
import com.Forming.book.service.IDepartService;
import com.Forming.book.service.impl.ClassesServiceImpl;
import com.Forming.book.service.impl.DepartServiceImpl;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "classesServlet", urlPatterns = {"/book/classesServlet"})
public class ClassesServlet extends BaseServlet {

    private IClassesService service = new ClassesServiceImpl();
    private IDepartService departService = new DepartServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);
        service.listPage(pageUtils);
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        req.getRequestDispatcher("/book/class/list.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String notes = req.getParameter("notes");
        String departId = req.getParameter("departId");
        Classes classes = new Classes();
        classes.setNotes(notes);
        classes.setName(name);

        if (StringUtils.isNotEmpty(departId)) {  //  查看是否已经分配院系
            Depart depart = departService.findById(Integer.parseInt(departId));
            classes.setDepartId(Integer.parseInt(departId));
            classes.setDepartName(depart.getName());
            service.updateById(classes);
        }
        if (StringUtils.isNotEmpty(id)) {//  查看是否是更新操作
            classes.setId(Integer.parseInt(id));
            service.updateById(classes);
        } else {
            //保存数据
            service.save(classes);
        }
        //更新后或者是添加后需要重定向
        resp.sendRedirect("/book/classesServlet?action=list");
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            //说明是更新操作
            Classes entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        List<Depart> departs = departService.list();
        req.setAttribute("departs", departs);
        req.getRequestDispatcher("/book/class/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        service.deleteById(Integer.parseInt(id));
        PrintWriter writer = resp.getWriter();
        writer.write("ok");
        writer.flush();
    }

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    }

    /**
     * 根据院系id进行该院系的班级查询
     * @param req
     * @param resp
     */
    public void findByDepartId(HttpServletRequest req,HttpServletResponse resp)throws  Exception{
        //获取部门的编号
        String departId = req.getParameter("departId");
        Classes cls=new Classes();
        cls.setDepartId(Integer.parseInt(departId));
        List<Classes> list=service.list(cls);   //  即此时我们拿到的是该院系下的所有的班级
        //下一步需要做的即 将集合对象转化为JSON字符串响应给前端的客户端
        String json = JSONArray.toJSONString(list);
        //设置字符编码
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.flush();
    }
}
