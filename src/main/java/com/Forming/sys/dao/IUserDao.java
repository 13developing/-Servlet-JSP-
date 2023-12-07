package com.Forming.sys.dao;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

/* Dao层的接口对象 */
public interface IUserDao {
    /*查询所有的用户信息*/
    public List<SysUser> list(SysUser entity);   // 此处是一个List返回值的方法，用以返回查询到的信息
    public List<SysUser> listPage(PageUtils pageUtils);   // 分页查询   pagenum是页数，前端传递过来从一开始    user使我们的查询条件
    public int save(SysUser entity);  //  定义了 一个方法，后面的类中会进行实现
    public SysUser findById(int id);
    public int updateById(SysUser entity);

    public int deleteById(int id);

    int count(PageUtils pageUtils);


    List<SysUser> findByName(String userName);


}
