package com.Forming.sys.service;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public interface IUserService {

    public List<SysUser> list(SysUser entity);

    public int save(SysUser entity);

    public SysUser findById(int id);

    public int updateById(SysUser entity);

    int deleteById(int id);

    void listPage(PageUtils pageUtils);

    int count(PageUtils pageUtils);

    SysUser findByName(String userName);


}
