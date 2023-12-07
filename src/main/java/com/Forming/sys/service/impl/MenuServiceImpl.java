package com.Forming.sys.service.impl;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.dao.IMenuDao;
import com.Forming.sys.dao.impl.MenuDaoImpl;
import com.Forming.sys.service.IMenuService;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class MenuServiceImpl implements IMenuService {
    private IMenuDao dao=new MenuDaoImpl();

    @Override
    public List<SysMenu> list(SysMenu entity) {
        return dao.list(entity);
    }

    @Override
    public int save(SysMenu entity) {
        return dao.save(entity);
    }

    @Override
    public SysMenu findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(SysMenu entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public void listPage(PageUtils pageUtils) {
        List<SysMenu> list = dao.listPage(pageUtils);
        int count = dao.count(pageUtils);
        //封装分页的数据
        pageUtils.setList(list);
        pageUtils.setTotalCount(count);

    }

    @Override
    public int count(PageUtils pageUtils) {
        return  dao.count(pageUtils);
    }

    @Override
    public List<SysMenu> getAllParent() {
        return dao.getALlParent();
    }

    @Override
    public boolean isDispatcher(int id) {
        return dao.isDispatcher(id);
    }

    @Override
    public boolean haveSubMenu(int id) {
        return dao.haveSubMenu(id);
    }

    @Override
    public List<SysMenu> findByRoleId(Integer roleId) {
        return dao.findByRoleId(roleId);
    }

}
