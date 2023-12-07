package com.Forming.book.bean;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowCard {

    private Integer id;
    private Integer stuid;
    private Integer userid;
    private String stuname;
    private Integer state;
    private Date starttime;
    private Date endtime;

}
