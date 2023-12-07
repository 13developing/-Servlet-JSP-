package com.Forming.sys.dao;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.bean.SysRole;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IMenuDao {
    public List<SysMenu> list(SysMenu entity);   // 此处是一个List返回值的方法，用以返回查询到的信息
    public List<SysMenu> listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件
    public int save(SysMenu entity);  //  定义了 一个方法，后面的类中会进行实现
    public SysMenu findById(int id);
    public int updateById(SysMenu entity);

    public int deleteById(int id);

    int count(PageUtils pageUtils);

    boolean checkRoleDispatch(int roleId);

    List<SysMenu> getALlParent();

    boolean isDispatcher(int id);   //判断是否被分配

    boolean haveSubMenu(int id);   //判断父菜单是否有子菜单

    List<SysMenu> findByRoleId(Integer roleId);
}
