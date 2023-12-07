package com.Forming.sys.dao.impl;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.dao.IUserDao;
import com.Forming.sys.utils.MyDbUtils;
import com.Forming.sys.utils.PageUtils;
import com.Forming.sys.utils.StringUtils;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import jdk.internal.org.objectweb.asm.Handle;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.lang.model.element.NestingKind;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*系统用户的实现类*/
public class UserDaoImpl implements IUserDao {
    /*
    对于查询数据的逻辑梳理：
Dao层：
首先，dao是一种模式，他不是一个具体的封装好的具体工具类，而是代表的是一个接口，我们定义这个接口，在再用具体类去实现接口中的每一个方法。
而我们的Dbutils 是一个已经封装好的工具类，我们在dao层中使用dubutils进行数据库的具体操作。
首先是dbutils的查询功能，此处使用 其子类 queryRunner进行查询，利用 queryRunner 中的六个方法之一的 query() 方法进行查询  ，

    * */
    @Override
    public List<SysUser> list(SysUser user) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_user  ";
        if (user.getRoleId() != null && user.getRoleId() > 0) {
            //添加查询的条件
            sql = sql + " where  role_id = " + user.getRoleId();

        }
        try {
            List<SysUser> list = queryRunner.query(sql, new ResultSetHandler<List<SysUser>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<SysUser> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysUser> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysUser user = new SysUser();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setNickname(resultSet.getString("nickname"));
                        user.setRoleId(resultSet.getInt("role_id"));
                        user.setRolename(resultSet.getString("rolename"));
                        user.setImg(resultSet.getString("img"));
                        user.setCreateTime(resultSet.getDate("create_time"));
                        list.add(user);//把查询到的记录封装到了集合的容器中

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
    public List<SysUser> listPage(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_user limit ? ,?";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            //需要带条件查询
            sql = "select * from sys_user  where username like '%" + pageUtils.getKey() + "%' or nickname like '%" + pageUtils.getKey() + "%' limit ? ,?";
        }

        //计算分页开始的位置
        int startNo = pageUtils.getStart();

        try {
            List<SysUser> list = queryRunner.query(sql, new ResultSetHandler<List<SysUser>>() {   //  这里是将我们的查询结果放入list中
                /*上面这里，是执行sql语句 ，并且将我们的返回结果 设置为List <SysUser> */
                @Override
                public List<SysUser> handle(ResultSet resultSet) throws SQLException {
                    //这里声明的集合是 存储返回结果的容器
                    List<SysUser> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysUser user = new SysUser();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setNickname(resultSet.getString("nickname"));
                        user.setRoleId(resultSet.getInt("role_id"));
                        user.setRolename(resultSet.getString("rolename"));
                        user.setImg(resultSet.getString("img"));
                        user.setCreateTime(resultSet.getDate("create_time"));
                        list.add(user);//把查询到的记录封装到了集合的容器中
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

    //============================================以上部分是查询功能的实现===============================
    @Override
    public int save(SysUser user) {   //   这里实际上也是我们的接口的实现
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "insert into sys_user(username,password,nickname,img,role_id,rolename)values(?,?,?,?,?,?)";//  问号是占位符
        try {
            return queryRunner.update(sql, user.getUsername(), user.getPassword(), user.getNickname(), user.getImg(), user.getRoleId(), user.getRolename());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public SysUser findById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_user where id = ?";
        try {
            SysUser user = queryRunner.query(sql, new ResultSetHandler<SysUser>() {   //  这里是将我们的查询结果放入list中
                @Override
                public SysUser handle(ResultSet resultSet) throws SQLException {
                    if (resultSet.next()) {   //   这里是遍历数据中的指定 table

                        //每循环一次，  user存储一条数据
                        SysUser user = new SysUser();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setNickname(resultSet.getString("nickname"));
                        user.setRoleId(resultSet.getInt("role_id"));
                        user.setRolename(resultSet.getString("rolename"));
                        user.setImg(resultSet.getString("img"));
                        user.setCreateTime(resultSet.getDate("create_time"));
                        return user;
                    }
                    return null;
                }
            }, id);//不要漏掉此处的id
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateById(SysUser user) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "update sys_user set username=?,password=?,nickname=?,img=? ,role_id=?, rolename= ?  where id=?";//  问号是占位符
        try {
            return queryRunner.update(sql, user.getUsername(), user.getPassword(), user.getNickname(), user.getImg(), user.getRoleId(), user.getRolename(), user.getId());  //queryRunner中的六种方法之一的 update ，后面对应着三个占位符
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  //  没有插入成功就会 返回 -1
    }

    @Override
    public int deleteById(int id) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();  //实现Dbutils 下的子类的创建
        String sql = "delete from sys_user where id = ? ";
        try {
            return queryRunner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;//表示删除失败
    }

    @Override
    public int count(PageUtils pageUtils) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select count(1) from sys_user ";
        if (StringUtils.isNotEmpty(pageUtils.getKey())) {
            sql = "select count(1) from sys_user where nickname like '%" + pageUtils.getKey() + "%' or nickname like '%" + pageUtils.getKey() + "%' ";
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
    public List<SysUser> findByName(String userName) {
        QueryRunner queryRunner = MyDbUtils.getQueryRunner();
        String sql = "select * from sys_user where username= ? ";
        try {
            return queryRunner.query(sql, new ResultSetHandler<List<SysUser>>() {
                @Override
                public List<SysUser> handle(ResultSet resultSet) throws SQLException {
                    List<SysUser> list = new ArrayList<>();
                    while (resultSet.next()) {   //   这里是遍历数据中的指定 table
                        //每循环一次，  user存储一条数据
                        SysUser user = new SysUser();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setNickname(resultSet.getString("nickname"));
                        user.setRoleId(resultSet.getInt("role_id"));
                        user.setRolename(resultSet.getString("rolename"));
                        user.setImg(resultSet.getString("img"));
                        user.setCreateTime(resultSet.getDate("create_time"));
                        list.add(user);//把查询到的记录封装到了集合的容器中
                    }
                    return list;
                }
            }, userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
