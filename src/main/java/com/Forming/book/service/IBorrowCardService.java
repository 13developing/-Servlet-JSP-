package com.Forming.book.service;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BorrowCard;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IBorrowCardService {

    void listPage(PageUtils pageUtils, SysUser user);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    int count(PageUtils pageUtils, SysUser user);

    List<BorrowCard> list( );

    int save(BorrowCard entity);  //  定义了 一个方法，后面的类中会进行实现

    BorrowCard findById(int id);

    int updateById(BorrowCard entity);

    int deleteById(int id);

    List<BorrowCard> listCanUseCard(Integer id);


}
