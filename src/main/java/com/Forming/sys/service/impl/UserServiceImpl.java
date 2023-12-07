package com.Forming.sys.service.impl;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.dao.IUserDao;
import com.Forming.sys.dao.impl.UserDaoImpl;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

/* sevice 接口实现

 * 1 、完成具体的业务处理
 * 2、 调用 Dao 处理数据库的业务
 * */
public class UserServiceImpl implements IUserService {
    private IUserDao dao = new UserDaoImpl();

    @Override
    public List<SysUser> list(SysUser user) {
        return dao.list(user);
    }


    @Override
    public int save(SysUser user) {
        return dao.save(user);
    }

    @Override
    public SysUser findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(SysUser user) {
        return dao.updateById(user);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public void listPage(PageUtils pageUtils) {
        List<SysUser> list = dao.listPage(pageUtils);
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
    public SysUser findByName(String userName) {
       List<SysUser> list= dao.findByName(userName);
        return list!=null&&list.size()>0? list.get(0):null;
    }





}
