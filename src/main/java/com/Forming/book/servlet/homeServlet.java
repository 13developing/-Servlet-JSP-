package com.Forming.book.servlet;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BookType;
import com.Forming.book.service.IBookService;
import com.Forming.book.service.IBookTypeService;
import com.Forming.book.service.impl.BookServiceImpl;
import com.Forming.book.service.impl.BookTypeServiceImpl;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "homeServlet", urlPatterns = {"/homeServlet"})
public class homeServlet extends HttpServlet {
    private IBookTypeService typeService = new BookTypeServiceImpl();
    private IBookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //查询出所有书籍类别信息  和对应的书籍的数量
        List<BookType> types = typeService.list();
        List<String> typeNames = new ArrayList<>();
        List<Integer> bookNums = new ArrayList<>();
        List<Map<String,Object>>  charts2=new ArrayList<>();


        if (types != null && types.size() > 0) {
            for (BookType type : types) {
                //根据图书类别查询图书的数量
                Book book = new Book();
                book.setTypeid(type.getId());
                List<Book> list = bookService.list(book);
                typeNames.add(type.getName());
                bookNums.add(list.size());

                Map<String,Object> map=new HashMap<>();
                map.put("name",type.getName());
                map.put("value",list.size());
                charts2.add(map);
            }
        }
        // 返回的统计的数据，通过map进行统一的处理
        Map<String, Object> map = new HashMap<>();
        map.put("typeNames", typeNames);
        map.put("bookNums", bookNums);
        map.put("charts2",charts2);
        resp.setContentType("application/json;charset=utf-8");
        // map转化为对应的 json 数据
        String json = JSONObject.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
