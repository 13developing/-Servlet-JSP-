package com.Forming.book.dao;

import com.Forming.book.bean.BookType;
import com.Forming.book.bean.Depart;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IBookTypeDao {


    List<BookType> listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    List<BookType> list();

    int count(PageUtils pageUtils);

    int save(BookType entity);  //  定义了 一个方法，后面的类中会进行实现

    BookType findById(int id);

    int updateById(BookType entity);

    int deleteById(int id);


    int isDispatcherById(int id);


}
