package com.Forming.book.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookType {
    private Integer id;
    private String notes;
    private String name;
    private Date createtime;
    private List<Book> books;   //  当前类型对应的图书信息
}
