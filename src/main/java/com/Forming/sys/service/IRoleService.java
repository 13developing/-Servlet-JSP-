package com.Forming.sys.service;

import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysRoleMenu;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IRoleService {

    public List<SysRole> list(SysRole entity);

    public int save(SysRole entity);

    public SysRole findById(int id);

    public int updateById(SysRole entity);

    int deleteById(int id);

    void listPage(PageUtils pageUtils);

    int count(PageUtils pageUtils);

    boolean checkRoleDispatch(int id);  //  用于检查是否此角色id被分配

    void deleteMenuByRoleId(int parseInt);

    void saveDispacterMenu(int roleId, String menuId);


    List<SysRoleMenu> queryByRoleId(int roleId);
}
