package com.Forming.sys.bean;

import lombok.Data;

import java.util.Date;

@Data
public class SysMenu {

    private Integer id;
    private String name;
    private String url;
    private Integer parentId;
    private int seq;    //排序字段
    private Date createTime;
    private boolean check=false;

}
