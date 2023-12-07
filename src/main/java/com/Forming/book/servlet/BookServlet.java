package com.Forming.book.servlet;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BookType;
import com.Forming.book.service.IBookService;
import com.Forming.book.service.IBookTypeService;
import com.Forming.book.service.impl.BookServiceImpl;
import com.Forming.book.service.impl.BookTypeServiceImpl;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;

import javax.lang.model.element.VariableElement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "bookServlet", urlPatterns = {"/book/bookServlet"})
public class BookServlet extends BaseServlet {
    private IBookService service = new BookServiceImpl();
    private IBookTypeService typeService = new BookTypeServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);
        service.listPage(pageUtils);
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        req.getRequestDispatcher("/book/book/list.jsp").forward(req, resp);

    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        String notes = req.getParameter("notes");
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String publish = req.getParameter("publish");
        String img = req.getParameter("img");
        String isbn = req.getParameter("isbn");
        String price = req.getParameter("price");
        String typeId = req.getParameter("typeId");
        String state = req.getParameter("state");

        Book book = new Book();
        book.setName(name);
        book.setNotes(notes);
        book.setAuthor(author);
        book.setPublish(publish);
        book.setImg(img);
        book.setIsbn(isbn);
        book.setPrice(Integer.parseInt(price));

        if (StringUtils.isNotEmpty(typeId)) {
            if (StringUtils.isNotEmpty(state)) {
                book.setState(Integer.parseInt(state));
            }
            BookType type = typeService.findById(Integer.parseInt(typeId));
            book.setTypeid(type.getId());
            book.setTypename(type.getName());
        }
        if (StringUtils.isNotEmpty(id)) {
            book.setId(Integer.parseInt(id));
            service.updateById(book);
        } else {
            book.setState(0);
            service.save(book);
        }
        resp.sendRedirect("/book/bookServlet?action=list");
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            Book entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        //  查询出所有的类别
        List<BookType> list = typeService.list();
        req.setAttribute("types", list);
        req.getRequestDispatcher("/book/book/addOrUpdate.jsp").forward(req, resp);
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
