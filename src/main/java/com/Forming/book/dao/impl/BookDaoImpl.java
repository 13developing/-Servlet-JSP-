package com.Forming.book.dao.impl;


import com.Forming.book.bean.Book;
import com.Forming.book.bean.BookType;
import com.Forming.book.dao.IBookDao;
import com.Forming.book.dao.IBookTypeDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDaoImpl implements IBookDao {
    @Override
    public List<Book> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_books limit ?,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) { //  查看关键字是否不为 空
            sql = "select * from t_books where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey()
                    + "%'  limit ?,?";
        }
        try {
            List<Book> list = queryRunner.query(sql, new BeanListHandler<Book>(Book.class), pageUtils.getStart(), pageUtils.getPageSize());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> list(Book book) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_books where 1=1 ";
        if (book != null) {
            if (book.getTypeid() > 0) {
                sql += " and typeid=" + book.getTypeid();
            }
        }
        try {
            return queryRunner.query(sql, new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int count(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from t_books ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select * from t_books where name like '%" + pageUtils.getKey() + "%' or notes like '%" + pageUtils.getKey()
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
    public int save(Book entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into t_books(name,notes,author,publish,img,state,isbn,price,typeid,typename)values(?,?,?,?,?,?,?,?,?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(), entity.getAuthor(), entity.getPublish(), entity.getImg()
                    , entity.getState(), entity.getIsbn(), entity.getPrice(), entity.getTypeid(), entity.getTypename());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public Book findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_books where id = ?";
        try {
            List<Book> list = queryRunner.query(sql, new BeanListHandler<Book>(Book.class), id);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int updateById(Book entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "update t_books set name= ?,notes=?,author=?, publish=?, img=?,state=?," +
                "isbn=?,price=?,typeid=?,typename=? where id= ?";
        try {
            return queryRunner.update(sql, entity.getName(), entity.getNotes(), entity.getAuthor(), entity.getPublish(), entity.getImg()
                    , entity.getState(), entity.getIsbn(), entity.getPrice(), entity.getTypeid(), entity.getTypename(), entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "delete from t_books  where id= ?";
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
