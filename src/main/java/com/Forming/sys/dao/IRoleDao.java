package com.Forming.sys.dao;

import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysRoleMenu;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IRoleDao {
    public List<SysRole> list(SysRole entity);   // 此处是一个List返回值的方法，用以返回查询到的信息
    public List<SysRole> listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件
    public int save(SysRole entity);  //  定义了 一个方法，后面的类中会进行实现
    public SysRole findById(int id);
    public int updateById(SysRole entity);

    public int deleteById(int id);

    int count(PageUtils pageUtils);

    boolean checkRoleDispatch(int roleId);

    void deleteMenuByRoleId(int id);

    void saveDispacterMenu(int roleId, String menuId);

    List<SysRoleMenu> queryByRoleId(int roleId);
}
