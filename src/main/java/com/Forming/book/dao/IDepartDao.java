package com.Forming.book.dao;

import com.Forming.book.bean.Depart;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IDepartDao {


    public List<Depart> listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件
    int count(PageUtils pageUtils);

    public int save(Depart entity);  //  定义了 一个方法，后面的类中会进行实现

    public Depart findById(int id);

    public int updateById(Depart entity);

    public int deleteById(int id);


    int isDispatcherById(int id);

    List<Depart> list();
}
