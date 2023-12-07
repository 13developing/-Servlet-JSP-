package com.Forming.book.service.impl;

import com.Forming.book.bean.Classes;
import com.Forming.book.dao.IClassesDao;
import com.Forming.book.dao.impl.ClassesDaoImpl;
import com.Forming.book.service.IClassesService;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class ClassesServiceImpl implements IClassesService {

    IClassesDao dao=new ClassesDaoImpl();
    @Override
    public void listPage(PageUtils pageUtils) {
        List<Classes> list = dao.listPage(pageUtils);
        int count = dao.count(pageUtils);
        //封装分页的数据
        pageUtils.setList(list);
        pageUtils.setTotalCount(count);
    }

    @Override
    public int count(PageUtils pageUtils) {
        return dao.count(pageUtils);
    }

    @Override
    public int save(Classes entity) {
        return dao.save(entity);
    }

    @Override
    public Classes findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(Classes entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public List<Classes> list(Classes cls) {
        return dao.list(cls);
    }

}
