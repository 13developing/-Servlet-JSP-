package com.Forming.book.bean;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowRecoder {
    private Integer id;
    private Integer cardId;
    private Integer bookId;
    private String bookname;
    private String img;
    private Date starttime;
    private Date endtime;
    private String stuname;
    private int userid;
}
