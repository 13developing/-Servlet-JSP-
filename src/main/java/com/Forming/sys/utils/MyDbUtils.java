package com.Forming.sys.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

/*操作数据库的工具类*/
public class MyDbUtils {
    private static MysqlDataSource dataSource;  //定义的数据源

    static{   //在这里完成数据源的初始化操作     这里实际上是利用jdbc'中的连接池
        dataSource=new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/books?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
        dataSource.setUser("root");
        dataSource.setPassword("123456");

    }

    public static QueryRunner getQueryRunner() {

        return new QueryRunner(dataSource);

    }

}
