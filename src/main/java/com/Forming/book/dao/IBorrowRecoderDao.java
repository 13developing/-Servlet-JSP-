package com.Forming.book.dao;

import com.Forming.book.bean.BorrowCard;
import com.Forming.book.bean.BorrowRecoder;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IBorrowRecoderDao {


    List<BorrowRecoder> listPage(PageUtils pageUtils, SysUser user);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    List<BorrowRecoder> list();

    int count(PageUtils pageUtils, SysUser user);

    int save(BorrowRecoder entity);  //  定义了 一个方法，后面的类中会进行实现

    BorrowRecoder findById(int id);

    int updateById(BorrowRecoder entity);

    int deleteById(int id);

    int isDispatcherById(int id);

}
