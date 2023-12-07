package com.Forming.sys.filter;

import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;
import oracle.jrockit.jfr.events.RequestableEventEnvironment;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 认证的过滤器，拦截所有的请求，
 * 1、判断是否是登录状态
 * 2、认证的资源是否可以匿名访问
 * 3、都不满足就跳转回登录页面
 */
@WebFilter(filterName = "authName", urlPatterns = {"/*"})  // ” /* “ 即所有的资源都要进行认证
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 对封装请求和响应的对象做向下转型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取被拦截请求的访问地址
        String requestURI = request.getRequestURI();
        request.setAttribute("msg","请先登录");
        if (checkAccessible(requestURI)) {
            //直接放过
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            // 即拦截  需要登录后才可以访问
            HttpSession session=request.getSession();
            Object loginUser = session.getAttribute(Constant.LOGIN_USER);//取会话的内容
            if(loginUser!=null){
                //说明是登录的状态   放过请求
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                //拦截   跳转回login页面
                request.setAttribute("msg","请先登录");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }
    }

    private boolean checkAccessible(String requestURI) {
        //在没有登录的情况下可以访问的资源   登录页面   处理登录逻辑的Servlet  已经各种 Js css  img
        List<String> urls = Arrays.asList("login.jsp", "loginServlet", ".css", "/js/", ".jpg", ".img");
        boolean flag = false;
        for (String url : urls) {
            if (requestURI.contains(url)) {

                return true;
            }
        }
        return false;
    }
}
