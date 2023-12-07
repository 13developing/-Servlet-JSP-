package com.Forming.book.service;

import com.Forming.book.bean.BorrowCard;
import com.Forming.book.bean.BorrowRecoder;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IBorrowRecoderService {

    void listPage(PageUtils pageUtils, SysUser user);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    int count(PageUtils pageUtils, SysUser user);

    List<BorrowRecoder> list( );

    int save(BorrowRecoder entity);  //  定义了 一个方法，后面的类中会进行实现

    BorrowRecoder findById(int id);

    int updateById(BorrowRecoder entity);

    int deleteById(int id);
}
