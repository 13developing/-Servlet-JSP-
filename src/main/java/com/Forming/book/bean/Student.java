package com.Forming.book.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Student {

    private int id;
    private int account;
    private String stuno;
    private String name;
    private int age;
    private String gender;
    private String email;
    private String telephone;
    private String address;
    private String wechat;
    private int classid;
    private String classname;
    private String departname;
    private int departId;
    private Date createtime;

}
