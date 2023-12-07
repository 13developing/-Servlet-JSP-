package com.Forming.book.servlet;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BookType;
import com.Forming.book.service.IBookService;
import com.Forming.book.service.IBookTypeService;
import com.Forming.book.service.impl.BookServiceImpl;
import com.Forming.book.service.impl.BookTypeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "showServlet", urlPatterns = {"/book/showBookServlet"})
public class ShowServlet extends HttpServlet {
    private IBookTypeService typeService = new BookTypeServiceImpl();
    private IBookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BookType> list = typeService.list();
        if (list != null && list.size() > 0) {
            for (BookType type : list) {
                Book book = new Book();
                book.setTypeid(type.getId());
                List<Book> books = bookService.list(book);
                type.setBooks(books);
            }
        }
        req.setAttribute("list", list);
        req.getRequestDispatcher("/book/book/showBook.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
