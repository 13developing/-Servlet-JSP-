package com.Forming.book.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Book {
    private int id;
    private String name;
    private String author;
    private String publish;
    private String img;
    private String notes;
    private String isbn;
    private int state;    //   0 空闲  1 借出
    private int price;
    private int typeid;
    private String typename;
    private Date createtime;

}
