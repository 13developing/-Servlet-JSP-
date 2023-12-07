package com.Forming.book.dao.impl;

import com.Forming.book.bean.BorrowCard;
import com.Forming.book.bean.BorrowRecoder;
import com.Forming.book.dao.IBorrowRecoderDao;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BorrowRecoderDaoImpl implements IBorrowRecoderDao {
    @Override
    public List<BorrowRecoder> listPage(PageUtils pageUtils, SysUser user) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_borrow_recoder where 1 = 1 ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) { //  查看关键字是否不为 空、
            sql += " and stuname like '%" + pageUtils.getKey() + "%'  ";
        }
        if (user != null && user.getIsAdmin() == false) {
            sql += " and userid = "+user.getId();
        }
        sql+=" limit ?,? ";
        try {
            List<BorrowRecoder> list = queryRunner.query(sql, new BeanListHandler<BorrowRecoder>(BorrowRecoder.class), pageUtils.getStart(), pageUtils.getPageSize());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BorrowRecoder> list() {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_borrow_recoder";
        try {
            return queryRunner.query(sql, new BeanListHandler<BorrowRecoder>(BorrowRecoder.class));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int count(PageUtils pageUtils, SysUser user) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from t_borrow_recoder where 1 = 1";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) { //  查看关键字是否不为 空
            sql += " and stuname like '%" + pageUtils.getKey() + "%'  ";
        }
        if (user != null && user.getIsAdmin() == false) {
            sql += " and userid = "+user.getId();
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
    public int save(BorrowRecoder entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into t_borrow_recoder(cardid,bookid,bookname,userid,stuname)values(?,?,?,?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, entity.getCardId(),entity.getBookId(), entity.getBookname(), entity.getUserid(), entity.getStuname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public BorrowRecoder findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from t_borrow_recoder where id = ?";
        try {
            List<BorrowRecoder> list = queryRunner.query(sql, new BeanListHandler<BorrowRecoder>(BorrowRecoder.class), id);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(BorrowRecoder entity) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "update t_borrow_recoder set endtime=now() where id= ?";
        try {
            return queryRunner.update(sql,  entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "delete from t_borrow_recoder where id= ?";
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
