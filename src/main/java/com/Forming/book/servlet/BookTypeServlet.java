package com.Forming.book.servlet;

import com.Forming.book.bean.BookType;
import com.Forming.book.service.IBookTypeService;
import com.Forming.book.service.impl.BookTypeServiceImpl;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(name = "bookTypeServlet", urlPatterns = {"/book/bookTypeServlet"})
public class BookTypeServlet extends BaseServlet {
    private IBookTypeService service = new BookTypeServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);
        service.listPage(pageUtils);
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        req.getRequestDispatcher("/book/bookType/list.jsp").forward(req, resp);

    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        String notes = req.getParameter("notes");
        String name = req.getParameter("name");
        BookType type = new BookType();
        type.setName(name);
        type.setNotes(notes);
        if (StringUtils.isNotEmpty(id)) {
            type.setId(Integer.parseInt(id));
            service.updateById(type);
        } else {
            service.save(type);
        }
        resp.sendRedirect("/book/bookTypeServlet?action=list");
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            BookType entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        req.getRequestDispatcher("/book/bookType/addOrUpdate.jsp").forward(req, resp);
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
}
