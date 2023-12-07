package com.Forming.book.service.impl;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BookType;
import com.Forming.book.dao.IBookDao;
import com.Forming.book.dao.impl.BookDaoImpl;
import com.Forming.book.service.IBookService;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class BookServiceImpl implements IBookService {
    private IBookDao dao = new BookDaoImpl();
    public void listPage(PageUtils pageUtils) {
        List<Book> books= dao.listPage(pageUtils);
        for (Book book:books){
            String notes=book.getNotes();
            if(notes.length()>8){
                book.setNotes(notes.substring(0,8)+"...");
            }
        }
        int count = dao.count(pageUtils);
        pageUtils.setList(books);
        pageUtils.setTotalCount(count);
    }

    @Override
    public int count(PageUtils pageUtils) {
        return dao.count(pageUtils);
    }

    @Override
    public List<Book> list(Book book) {
        return dao.list(book);
    }

    @Override
    public int save(Book entity) {
        return dao.save(entity);
    }

    @Override
    public Book findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(Book entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }
}
