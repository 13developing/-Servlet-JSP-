package com.Forming.book.service;

import com.Forming.book.bean.Depart;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IDepartService {

     void  listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件

    int count(PageUtils pageUtils);

     int save(Depart entity);  //  定义了 一个方法，后面的类中会进行实现

     Depart findById(int id);

     int updateById(Depart entity);

     int deleteById(int id);

    boolean isDispatcherById(String id);

    List<Depart> list();
}
