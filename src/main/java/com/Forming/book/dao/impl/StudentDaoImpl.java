package com.Forming.book.dao.impl;

import com.Forming.book.bean.Classes;
import com.Forming.book.bean.Depart;
import com.Forming.book.bean.Student;
import com.Forming.book.dao.IStudentDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {
    @Override
    public List<Student> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_student limit ?,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) { //  查看关键字是否不为 空

            sql = "select * from t_student where name like '%" + pageUtils.getKey() + "%' or classname like '%" + pageUtils.getKey()
                    + "%' or departname like '%" + pageUtils.getKey() + "%' or address like '%" + pageUtils.getKey() + "%'  limit ?,?";
        }
        try {
            List<Student> list = queryRunner.query(sql, new BeanListHandler<Student>(Student.class), pageUtils.getStart(), pageUtils.getPageSize());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int count(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from t_student  ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select count(1) from t_student where name like '%" + pageUtils.getKey() + "%' or classname like '%"
                    + pageUtils.getKey() + "%' or departname like '%" + pageUtils.getKey() + "%' or address like '%" + pageUtils.getKey() + "%'  ";
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
    public int save(Student entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into t_student(account,name,stuno,age,gender,email,telephone," +
                "wechat,address,classid,classname,departname)values(?,?,?,?,?,?,?,?,?,?,?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getAccount(), entity.getName(), entity.getStuno(),
                    entity.getAge(), entity.getGender(), entity.getEmail(), entity.getTelephone(), entity.getWechat(),
                    entity.getAddress(), entity.getClassid(), entity.getClassname(), entity.getDepartname());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public Student findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_student where id = ?";
        try {
            return queryRunner.query(sql, new BeanHandler<Student>(Student.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(Student entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "update t_student set account=?,stuno= ?, name = ?,age = ? ,gender = ?,email=?," +
                "telephone=?,address=?,wechat=?,classid=?,classname=?,departname=?  where id=?";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getAccount(), entity.getStuno(), entity.getName(), entity.getAge(),
                    entity.getGender(), entity.getEmail(), entity.getTelephone(), entity.getAddress(), entity.getWechat(),
                    entity.getClassid(), entity.getClassname(), entity.getDepartname(), entity.getId());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "delete from t_student where id = ? ";

        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;//表示删除失败
    }

    @Override
    public List<Student> list() {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_student ";
        try {
            List<Student> list = queryRunner.query(sql, new BeanListHandler<Student>(Student.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

