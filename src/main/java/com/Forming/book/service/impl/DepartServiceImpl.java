package com.Forming.book.service.impl;

import com.Forming.book.bean.Depart;
import com.Forming.book.dao.IDepartDao;
import com.Forming.book.dao.impl.DepartDaoImpl;
import com.Forming.book.service.IDepartService;
import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.ArrayList;
import java.util.List;

public class DepartServiceImpl implements IDepartService {
    IDepartDao dao = new DepartDaoImpl();

    @Override
    public void listPage(PageUtils pageUtils) {
        List<Depart> list = dao.listPage(pageUtils);
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
    public int save(Depart entity) {
        return dao.save(entity);
    }

    @Override
    public Depart findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(Depart entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean isDispatcherById(String id) {
        int intId = Integer.parseInt(id);
        return dao.isDispatcherById(intId) > 0 ? true : false;
    }

    @Override
    public List<Depart> list() {
        return dao.list();
    }
}
