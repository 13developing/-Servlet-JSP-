package com.Forming.book.service;

import com.Forming.book.bean.BookType;
import com.Forming.book.bean.Classes;
import com.Forming.sys.utils.PageUtils;

import java.lang.reflect.Type;
import java.util.List;

public interface IBookTypeService {

    void listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    int count(PageUtils pageUtils);

    List<BookType> list( );

    int save(BookType entity);  //  定义了 一个方法，后面的类中会进行实现

    BookType findById(int id);

    int updateById(BookType entity);

    int deleteById(int id);
}
