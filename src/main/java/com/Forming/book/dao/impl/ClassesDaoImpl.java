package com.Forming.book.dao.impl;

import com.Forming.book.bean.Classes;
import com.Forming.book.bean.Depart;
import com.Forming.book.dao.IClassesDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassesDaoImpl implements IClassesDao {
    @Override
    public List<Classes> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_classes limit ? ,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            //需要带条件查询
            sql = "select * from t_classes  where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey() + "%' limit ? ,?";
        }
        //计算分页开始的位置
        int startNo = pageUtils.getStart();
        try {
            List<Classes> list = queryRunner.query(sql, new ResultSetHandler<List<Classes>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<Classes> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<Classes> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        Classes classes = new Classes();
                        classes.setId(resultSet.getInt("id"));
                        classes.setName(resultSet.getString("name"));
                        classes.setNotes(resultSet.getString("notes"));
                        classes.setDepartId(resultSet.getInt("depart_id"));
                        classes.setDepartName(resultSet.getString("departname"));
                        classes.setCreateTime(resultSet.getDate("create_time"));
                        list.add(classes);//把查询到的记录封装到了集合的容器中
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
        String sql = "select count(1) from t_classes ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select count(1) from t_classes  where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey() + "%' ";
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
    public int save(Classes entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into t_classes(name,notes,depart_id,departname)values(?,?,?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(), entity.getDepartId(), entity.getDepartName());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public Classes findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_classes where id = ?";
        try {
            return queryRunner.query(sql, new ResultSetHandler<Classes>() {
                @Override
                public Classes handle(ResultSet resultSet) throws SQLException {
                    if (resultSet.next()) {   //   这里是遍历数据中的指定 table

                        //每循环一次，  user存储一条数据
                        Classes entity = new Classes();
                        entity.setId(resultSet.getInt("id"));
                        entity.setName(resultSet.getString("name"));
                        entity.setNotes(resultSet.getString("notes"));
                        entity.setDepartId(resultSet.getInt("depart_id"));
                        entity.setDepartName(resultSet.getString("departname"));
                        entity.setCreateTime(resultSet.getDate("create_time"));
                        return entity;
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
    public int updateById(Classes entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "update t_classes set name = ?,notes = ? ,depart_id = ?,departname= ? where id=?";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(), entity.getDepartId(), entity.getDepartName(), entity.getId());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "delete from t_classes where id = ? ";

        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;//表示删除失败
    }

    @Override
    public List<Classes> list(Classes cls) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_classes where 1=1";
        if (cls != null) {
            if (cls.getDepartId() != null && cls.getDepartId() > 0) {
                sql += " and depart_id = " + cls.getDepartId();
            }
        }

        try {
            List<Classes> list = queryRunner.query(sql, new ResultSetHandler<List<Classes>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<Classes> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<Classes> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        Classes classes = new Classes();
                        classes.setId(resultSet.getInt("id"));
                        classes.setName(resultSet.getString("name"));
                        classes.setNotes(resultSet.getString("notes"));
                        classes.setDepartId(resultSet.getInt("depart_id"));
                        classes.setDepartName(resultSet.getString("departname"));
                        classes.setCreateTime(resultSet.getDate("create_time"));
                        list.add(classes);//把查询到的记录封装到了集合的容器中
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
