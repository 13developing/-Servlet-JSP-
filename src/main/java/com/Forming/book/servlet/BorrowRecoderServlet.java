package com.Forming.book.servlet;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BorrowCard;
import com.Forming.book.bean.BorrowRecoder;
import com.Forming.book.service.IBookService;
import com.Forming.book.service.IBorrowCardService;
import com.Forming.book.service.IBorrowRecoderService;
import com.Forming.book.service.impl.BookServiceImpl;
import com.Forming.book.service.impl.BorrowCardServiceImpl;
import com.Forming.book.service.impl.BorrowRecoderServiceImpl;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;

import javax.lang.model.element.VariableElement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "borrowRecoderServlet", urlPatterns = {"/book/borrowRecoderServlet"})
public class BorrowRecoderServlet extends BaseServlet {
    private IBorrowRecoderService service = new BorrowRecoderServiceImpl();
    private IBorrowCardService cardService = new BorrowCardServiceImpl();
    private IBookService bookService = new BookServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);
        service.listPage(pageUtils, getCurrentLoginUser(req, resp));
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
req.getRequestDispatcher("/book/bookRecoder/list.jsp").forward(req,resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String bookId = req.getParameter("bookId");
        String cardId = req.getParameter("cardId");
        String id = req.getParameter("id");
        SysUser currentLoginUser = this.getCurrentLoginUser(req, resp);
        BorrowRecoder entity = new BorrowRecoder();
        if (StringUtils.isNotEmpty(cardId)) {
            BorrowCard card = cardService.findById(Integer.parseInt(cardId));
            entity.setCardId(card.getId());
            entity.setStuname(card.getStuname());
            entity.setUserid(card.getUserid());
            if (StringUtils.isEmpty(id)) {
                //说明是添加借书记录，同步更新借书卡的状态
                card.setState(1);
                cardService.updateById(card);
            }
        }
        if (StringUtils.isNotEmpty(bookId)) {
            Book book = bookService.findById(Integer.parseInt(bookId));
            entity.setBookId(book.getId());
            entity.setBookname(book.getName());
            if (StringUtils.isEmpty(id)) {
                //更新图书的状态
                book.setState(1);
                bookService.updateById(book);
            }
        }

        if (StringUtils.isNotEmpty(id)) {
            //更新

        } else {//保存
            //还要同步进行书籍和借书卡的状态信息
            service.save(entity);
        }
        PrintWriter writer = resp.getWriter();
        writer.write("ok");
        writer.flush();
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    public void  backBook(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String id = req.getParameter("id");
        //根据id查询出对应的借阅记录
        BorrowRecoder entity =service.findById(Integer.parseInt(id));
        entity.setEndtime(new Date());//设置归还的时间
        service.updateById(entity);
        //更新借书卡 和 书籍信息的状态
        Book book =bookService.findById(entity.getBookId());
        book.setState(0);
        bookService.updateById(book);//更新到数据库

        BorrowCard card=cardService.findById(entity.getCardId());
        card.setState(0);
        cardService.updateById(card);

        PrintWriter writer = resp.getWriter();
        writer.write("ok");
        writer.flush();
    }
}
