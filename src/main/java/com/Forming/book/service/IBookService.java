package com.Forming.book.service;

import com.Forming.book.bean.Book;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IBookService {

    void listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    int count(PageUtils pageUtils);

    List<Book> list(Book book );

    int save(Book entity);  //  定义了 一个方法，后面的类中会进行实现

    Book findById(int id);

    int updateById(Book entity);

    int deleteById(int id);
}
