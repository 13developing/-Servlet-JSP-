package com.Forming.book.service.impl;

import com.Forming.book.bean.BookType;
import com.Forming.book.dao.IBookTypeDao;
import com.Forming.book.dao.impl.BookTypeDaoImpl;
import com.Forming.book.service.IBookTypeService;
import com.Forming.sys.utils.PageUtils;

import javax.print.attribute.standard.PageRanges;
import java.util.ArrayList;
import java.util.List;

public class BookTypeServiceImpl implements IBookTypeService {
    private IBookTypeDao dao = new BookTypeDaoImpl();

    @Override
    public void listPage(PageUtils pageUtils) {
        List<BookType> BookTypes = dao.listPage(pageUtils);
        int count = dao.count(pageUtils);
        pageUtils.setList(BookTypes);
        pageUtils.setTotalCount(count);
    }

    @Override
    public int count(PageUtils pageUtils) {
        return dao.count(pageUtils);
    }

    @Override
    public List<BookType> list() {
        return dao.list();
    }

    @Override
    public int save(BookType entity) {
        return dao.save(entity);
    }

    @Override
    public BookType findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(BookType entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }


}
