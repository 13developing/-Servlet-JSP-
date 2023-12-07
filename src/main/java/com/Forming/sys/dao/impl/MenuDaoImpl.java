package com.Forming.sys.dao.impl;

import com.Forming.sys.bean.SysMenu;
import com.Forming.sys.dao.IMenuDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements IMenuDao {
    @Override
    public List<SysMenu> list(SysMenu entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_menu order by seq asc";
        try {
            List<SysMenu> list = queryRunner.query(sql, new ResultSetHandler<List<SysMenu>>() {   //  这里是将我们的查询结果放入list中

                @Override
                public List<SysMenu> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysMenu> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysMenu entity = new SysMenu();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setUrl(resultSet.getString("url"));
                        entity.setParentId(resultSet.getInt("parent_id"));
                        entity.setSeq(resultSet.getInt("seq"));
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
    public List<SysMenu> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_menu where parent_id =-1  limit ? ,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            //需要带条件查询
            sql = "select * from sys_menu  where parent_id =-1 and name like '%" + pageUtils.getKey() + "%'  limit ? ,?";
        }

        //计算分页开始的位置
        int startNo = pageUtils.getStart();

        try {
            List<SysMenu> list = queryRunner.query(sql, new ResultSetHandler<List<SysMenu>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<SysMenu> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysMenu> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysMenu entity = new SysMenu();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setUrl(resultSet.getString("url"));
                        entity.setParentId(resultSet.getInt("parent_id"));
                        entity.setSeq(resultSet.getInt("seq"));
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
    public int save(SysMenu entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "insert into sys_menu(name,url,parent_id,seq)values(?,?,?,?)";
        try {
            return queryRunner.update(sql, entity.getName(), entity.getUrl(), entity.getParentId(), entity.getSeq());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SysMenu findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_menu where id=?";
        try {
            return queryRunner.query(sql, new ResultSetHandler<SysMenu>() {   //  这里是将我们的查询结果放入list中

                @Override
                public SysMenu handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器

                    if (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysMenu entity = new SysMenu();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setUrl(resultSet.getString("url"));
                        entity.setParentId(resultSet.getInt("parent_id"));
                        entity.setSeq(resultSet.getInt("seq"));
                        entity.setCreateTime(resultSet.getDate("create_time"));
                        return entity;
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
    public int updateById(SysMenu entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "update sys_menu set name = ? ,url = ?,parent_id= ? ,seq= ? where id = ?";
        try {
            return queryRunner.update(sql, entity.getName(), entity.getUrl(), entity.getParentId(), entity.getSeq(), entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "delete from sys_menu where id = ?";
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
        String sql = "select count(1) from sys_menu ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select count(1) from sys_menu where username like '%" + pageUtils.getKey() + "%' or nickname like '%" + pageUtils.getKey() + "%' ";
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
    public List<SysMenu> getALlParent() {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_menu where parent_id=-1";
        try {
            List<SysMenu> list = queryRunner.query(sql, new ResultSetHandler<List<SysMenu>>() {   //  这里是将我们的查询结果放入list中

                @Override
                public List<SysMenu> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysMenu> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysMenu entity = new SysMenu();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setUrl(resultSet.getString("url"));
                        entity.setParentId(resultSet.getInt("parent_id"));
                        entity.setSeq(resultSet.getInt("seq"));
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
    public boolean isDispatcher(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from sys_role_menu where menu_id=?";
        try {
          return   queryRunner.query(sql, new ResultSetHandler<Boolean>() {

                @Override
                public Boolean handle(ResultSet resultSet) throws SQLException {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    return count > 0 ? true : false;
                }
            },id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean haveSubMenu(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from sys_menu where parent_id = ? ";
        try {
           return  queryRunner.query(sql, new ResultSetHandler<Boolean>() {
                @Override
                public Boolean handle(ResultSet resultSet) throws SQLException {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    return count > 0 ? true : false;
                }
            },id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<SysMenu> findByRoleId(Integer roleId) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_menu where id in(select menu_id from sys_role_menu where role_id = ?) order by seq asc";
        try {
            List<SysMenu> list = queryRunner.query(sql, new ResultSetHandler<List<SysMenu>>() {   //  这里是将我们的查询结果放入list中

                @Override
                public List<SysMenu> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysMenu> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysMenu entity = new SysMenu();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setUrl(resultSet.getString("url"));
                        entity.setParentId(resultSet.getInt("parent_id"));
                        entity.setSeq(resultSet.getInt("seq"));
                        entity.setCreateTime(resultSet.getDate("create_time"));
                        list.add(entity);//把查询到的记录封装到了集合的容器中
                    }
                    return list;
                }
            },roleId);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
