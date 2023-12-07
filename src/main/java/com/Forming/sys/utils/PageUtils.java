package com.Forming.sys.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*分页的工具类*/
public class PageUtils {

    private String key;//查询的关键字
    private int pageSize = 5;  //每页显示的条数
    private int pageNum = 1;//第几页，默认第一页
    private int totalCount;//总数据条数
    private int totalPage;//总数据页数
    private List<?> list;
    private List<String> pageList;//前端分页的页码列表


    public List<String> getPageList() {
        pageList = new ArrayList<>();
        totalPage=getTotalPage();
        if (totalPage < 7) {
            //总共没有7条记录
            for (int i = 1; i <= totalPage; i++) {
                pageList.add(i + "");
            }
        } else {
            if (pageNum == 1 || pageNum == 2 || pageNum == 3) {
       pageList= Arrays.asList("1","2","3","...",totalPage+"");
            }else{
                pageList=Arrays.asList((pageNum-2)+"",(pageNum-1)+"",pageNum+"","..."+totalPage);
                //对于分页的列表进行了一个简单的 处理
            }
        }
        return pageList;
    }

    /**
     * 获取sql中分页开始的位置
     *
     * @return
     */
    public int getStart() {
        return (this.getPageNum() - 1) * this.getPageSize();

    }

    public int getEnd() {
        return this.getPageNum() * this.getPageSize();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取总的页数
     *
     * @return
     */
    public int getTotalPage() {
        totalPage = (int) Math.ceil(((double) totalCount) / pageSize);  //向上取整进行页码的计算

        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }


}
