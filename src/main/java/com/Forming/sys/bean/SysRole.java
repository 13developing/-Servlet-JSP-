package com.Forming.sys.bean;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {

    private Integer id;
    private String name;
    private String notes;
    private Date createTime;
}
