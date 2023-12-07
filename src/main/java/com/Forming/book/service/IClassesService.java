package com.Forming.book.service;

import com.Forming.book.bean.Classes;
import com.Forming.book.bean.Depart;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IClassesService {

     void  listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    int count(PageUtils pageUtils);

     int save(Classes entity);  //  定义了 一个方法，后面的类中会进行实现

     Classes findById(int id);

     int updateById(Classes entity);

     int deleteById(int id);

    List<Classes> list(Classes cls);

}
