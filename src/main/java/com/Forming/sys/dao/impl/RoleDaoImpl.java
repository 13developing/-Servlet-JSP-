package com.Forming.sys.dao.impl;

import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysRoleMenu;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.dao.IRoleDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements IRoleDao {
    @Override
    public List<SysRole> list(SysRole entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_role where 1=1";
        if (entity != null) {
            if (StringUtils.isNotEmpty(entity.getName())) {
        sql+=" and name = '"+entity.getName()+"'";
            }
        }


        try {
            List<SysRole> list = queryRunner.query(sql, new ResultSetHandler<List<SysRole>>() {   //  这里是将我们的查询结果放入list中

                @Override
                public List<SysRole> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysRole> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysRole entity = new SysRole();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setNotes(resultSet.getString("notes"));
                        entity.setCreateTime(resultSet.getDate("create_time"));
                        list.add(entity);//把查询到的记录封装到了集合的容器中

                    }
                    return list;
                }
            });
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<SysRole> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_role limit ? ,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            //需要带条件查询
            sql = "select * from sys_role  where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey() + "%' limit ? ,?";
        }

        //计算分页开始的位置
        int startNo = pageUtils.getStart();

        try {
            List<SysRole> list = queryRunner.query(sql, new ResultSetHandler<List<SysRole>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<SysRole> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysRole> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysRole entity = new SysRole();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setNotes(resultSet.getString("notes"));
                        entity.setCreateTime(resultSet.getDate("create_time"));
                        list.add(entity);//把查询到的记录封装到了集合的容器中

                    }


                    return list;
                }
            }, startNo, pageUtils.getPageSize());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int save(SysRole entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "insert into sys_role (name,notes)  values(?,?)";
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return 0;
    }

    @Override
    public SysRole findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_role where id=?";
        try {
            return queryRunner.query(sql, new ResultSetHandler<SysRole>() {   //  这里是将我们的查询结果放入list中

                @Override
                public SysRole handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    if (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysRole entity = new SysRole();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setNotes(resultSet.getString("notes"));
                        entity.setCreateTime(resultSet.getDate("create_time"));
                        return entity;//把查询到的记录封装到了集合的容器中

                    }
                    return null;
                }
            }, id);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateById(SysRole entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "update  sys_role set name=?,notes=? where id=?";
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(), entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return 0;
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "delete from  sys_role where id=?";
        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int count(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from sys_role ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select count(1) from sys_role where username like '%" + pageUtils.getKey() + "%' or nickname like '%" + pageUtils.getKey() + "%' ";
        }
        try {
            return queryRunner.query(sql, new ResultSetHandler<Integer>() {    //因为这里返回的是查询到的记录的条数，所以这里的结果集 返回类型是 Integet
                public Integer handle(ResultSet resultSet) throws SQLException {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean checkRoleDispatch(int roleId) {
        return false;
    }

    @Override
    public void deleteMenuByRoleId(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "delete from sys_role_menu where role_id = ? ";
        try {
            queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void saveDispacterMenu(int roleId, String menuId) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "insert into  sys_role_menu(role_id,menu_id) values ( ? , ? )";
        try {
            queryRunner.update(sql, roleId, menuId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SysRoleMenu> queryByRoleId(int roleId) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();

        String sql = "select * from sys_role_menu where role_id=?";
        try {
            List<SysRoleMenu> list = queryRunner.query(sql, new ResultSetHandler<List<SysRoleMenu>>() {   //  这里是将我们的查询结果放入list中

                @Override
                public List<SysRoleMenu> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysRoleMenu> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysRoleMenu entity = new SysRoleMenu();
                        entity.setId(resultSet.getInt("id"));
                        entity.setRoleId(resultSet.getInt("role_id"));
                        entity.setMenuId(resultSet.getInt("menu_id"));

                        list.add(entity);//把查询到的记录封装到了集合的容器中

                    }
                    return list;
                }
            }, roleId);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
