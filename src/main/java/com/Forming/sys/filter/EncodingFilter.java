package com.Forming.sys.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
统一设置post方式提交数据的编码方式

*/


@WebFilter(filterName="encodingFilter",urlPatterns = "/*")
public class EncodingFilter extends HttpFilter {  //  这里是为了解决提交后乱码问题、

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
     req.setCharacterEncoding("UTF-8");   //   设置编码方式为UTF-8
     chain.doFilter(req,res);  //  放过请求
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
