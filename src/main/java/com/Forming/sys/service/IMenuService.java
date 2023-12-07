package com.Forming.sys.service;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.bean.SysRole;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IMenuService {

    public List<SysMenu> list(SysMenu entity);

    public int save(SysMenu entity);

    public SysMenu findById(int id);

    public int updateById(SysMenu entity);

    int deleteById(int id);

    void listPage(PageUtils pageUtils);

    int count(PageUtils pageUtils);

    List<SysMenu> getAllParent();

    boolean isDispatcher(int id);

    boolean haveSubMenu(int id);

    List<SysMenu> findByRoleId(Integer roleId);

}
