package com.Forming.book.dao.impl;

import com.Forming.book.bean.Depart;
import com.Forming.book.dao.IDepartDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartDaoImpl implements IDepartDao {

    @Override
    public List<Depart> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_depart limit ? ,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            //需要带条件查询
            sql = "select * from t_depart  where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey() + "%' limit ? ,?";
        }
        //计算分页开始的位置
        int startNo = pageUtils.getStart();
        try {
            List<Depart> list = queryRunner.query(sql, new ResultSetHandler<List<Depart>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<Depart> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<Depart> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        Depart depart = new Depart();
                        depart.setId(resultSet.getInt("id"));
                        depart.setName(resultSet.getString("name"));
                        depart.setNotes(resultSet.getString("notes"));
                        depart.setCreateTime(resultSet.getDate("create_time"));
                        list.add(depart);//把查询到的记录封装到了集合的容器中
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
    public int count(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from t_depart ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select count(1) from t_depart  where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey() + "%' ";
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
    public int save(Depart entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into t_depart(name,notes)values(?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public Depart findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_depart where id = ?";
        try {
            return queryRunner.query(sql, new ResultSetHandler<Depart>() {
                @Override
                public Depart handle(ResultSet resultSet) throws SQLException {
                    if (resultSet.next()) {   //   这里是遍历数据中的指定 table

                        //每循环一次，  user存储一条数据
                        Depart depart = new Depart();
                        depart.setId(resultSet.getInt("id"));
                        depart.setName(resultSet.getString("name"));
                        depart.setNotes(resultSet.getString("notes"));
                        depart.setCreateTime(resultSet.getDate("create_time"));
                        return depart;
                    }
                    return null;
                }
            }, id);//不要漏掉此处的id

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(Depart entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "update t_depart set name = ?,notes = ? where id=?";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(), entity.getId());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "delete from t_depart where id = ? ";

        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;//表示删除失败
    }


    /**
     *  下面这个函数是判断为判定院系是否被分配，并且依据的是Name这个属性
     */
    @Override
    public int isDispatcherById(int  id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from t_student where departname in(select name from t_depart where id = ?)";
        try {
         return    queryRunner.query(sql, new ResultSetHandler<Integer>() {
                @Override
                public Integer handle(ResultSet resultSet) throws SQLException {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    return count;
                }
            }, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Depart> list() {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_depart ";
        try {
            List<Depart> list = queryRunner.query(sql, new ResultSetHandler<List<Depart>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<Depart> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<Depart> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        Depart depart = new Depart();
                        depart.setId(resultSet.getInt("id"));
                        depart.setName(resultSet.getString("name"));
                        depart.setNotes(resultSet.getString("notes"));
                        depart.setCreateTime(resultSet.getDate("create_time"));
                        list.add(depart);//把查询到的记录封装到了集合的容器中
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


}
