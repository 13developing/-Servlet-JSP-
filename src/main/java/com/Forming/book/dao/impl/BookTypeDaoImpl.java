package com.Forming.book.dao.impl;


import com.Forming.book.bean.BookType;
import com.Forming.book.bean.Student;
import com.Forming.book.dao.IBookTypeDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookTypeDaoImpl implements IBookTypeDao {
    @Override
    public List<BookType> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_book_type limit ?,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) { //  查看关键字是否不为 空

            sql = "select * from t_book_type where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey()
                    + "%'  limit ?,?";
        }
        try {
            List<BookType> list = queryRunner.query(sql, new BeanListHandler<BookType>(BookType.class), pageUtils.getStart(), pageUtils.getPageSize());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookType> list() {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_book_type ";
        try {
            return queryRunner.query(sql, new BeanListHandler<BookType>(BookType.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int count(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from t_book_type ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select * from t_book_type where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey()
                    + "%' ";
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
    public int save(BookType entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into t_book_type(id,name,notes)values(?,?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getId(), entity.getName(), entity.getNotes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public BookType findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_book_type where id = ?";
        try {
            List<BookType> list = queryRunner.query(sql, new BeanListHandler<BookType>(BookType.class), id);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(BookType entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "update t_book_type set iname= ?,notes=? where id= ?";
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(),entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "delete from t_book_type  where id= ?";
        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int isDispatcherById(int id) {
        return 0;
    }
}
