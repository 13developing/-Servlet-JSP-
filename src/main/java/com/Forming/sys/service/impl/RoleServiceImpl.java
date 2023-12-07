package com.Forming.sys.service.impl;

import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysRoleMenu;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.dao.IRoleDao;
import com.Forming.sys.dao.impl.RoleDaoImpl;
import com.Forming.sys.service.IRoleService;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class RoleServiceImpl implements IRoleService {
    private IRoleDao dao=new RoleDaoImpl();
    private IUserService userService=new UserServiceImpl();
    @Override
    public List<SysRole> list(SysRole entity) {
        return dao.list(entity);
    }

    @Override
    public int save(SysRole entity) {
        return dao.save(entity);
    }

    @Override
    public SysRole findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(SysRole entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public void listPage(PageUtils pageUtils) {
        List<SysRole> list = dao.listPage(pageUtils);
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
    public boolean checkRoleDispatch(int roleId) {
        SysUser entity=new SysUser();
        entity.setRoleId(roleId);
        return userService.list(entity).size()==0?true:false;
    }

    @Override
    public void deleteMenuByRoleId(int id) {
        dao.deleteMenuByRoleId(id);
    }

    @Override
    public void saveDispacterMenu(int roleId, String menuId) {
        dao.saveDispacterMenu(roleId,menuId);
    }

    @Override
    public List<SysRoleMenu> queryByRoleId(int roleId) {
        return dao.queryByRoleId(roleId);
    }
}
