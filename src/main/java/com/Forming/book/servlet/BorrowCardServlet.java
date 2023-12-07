package com.Forming.book.servlet;

import com.Forming.book.bean.BorrowCard;
import com.Forming.book.bean.Student;
import com.Forming.book.service.IBorrowCardService;
import com.Forming.book.service.IStudentService;
import com.Forming.book.service.impl.BorrowCardServiceImpl;
import com.Forming.book.service.impl.StudentServiceImpl;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.DateUtils;
import com.Forming.sys.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "borrowCardServlet", urlPatterns = {"/book/borrowCardServlet"})
public class BorrowCardServlet extends BaseServlet {
    IBorrowCardService service = new BorrowCardServiceImpl();

    IStudentService studentService = new StudentServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);
        service.listPage(pageUtils, getCurrentLoginUser(req,resp));
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        req.getRequestDispatcher("/book/borrowCard/list.jsp").forward(req, resp);
    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        String stuid = req.getParameter("stuid");
        String starttime = req.getParameter("starttime");
        String endtime = req.getParameter("endtime");
        String state = req.getParameter("state");
        BorrowCard card = new BorrowCard();
        if (StringUtils.isNotEmpty(state)) {
            card.setState(Integer.parseInt(state));
        } else {
            card.setState(0);
        }

        if (StringUtils.isNotEmpty(stuid)) {
            Integer stuId = Integer.parseInt(stuid);
            Student stu = studentService.findById(stuId);
            card.setStuid(stuId);
            card.setStuname(stu.getName());
            card.setUserid(stu.getAccount());
        }
        if (StringUtils.isNotEmpty(starttime)) {
            //  做日期格式字符串的转换
            card.setStarttime(DateUtils.stringToDate(starttime, DateUtils.DATE_PATTERN1));
        }
        if (StringUtils.isNotEmpty(endtime)) {
            card.setEndtime(DateUtils.stringToDate(endtime, DateUtils.DATE_PATTERN1));
        }
        if (StringUtils.isNotEmpty(id)) {
            card.setId(Integer.parseInt(id));
            service.updateById(card);
        } else {
            service.save(card);
        }
        resp.sendRedirect("/book/borrowCardServlet?action=list");
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //需要查询出所有的学生信息
        List<Student> list = studentService.list();
        req.setAttribute("stus", list);
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            BorrowCard entity = service.findById(Integer.parseInt(id));
            req.setAttribute("entity", entity);
        }
        req.getRequestDispatcher("/book/borrowCard/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        //删除操作本质上做的是下架操作
        BorrowCard entity = service.findById(Integer.parseInt(id));
        entity.setState(3);
        service.updateById(entity);
        PrintWriter writer = resp.getWriter();
        writer.write("ok");
        writer.flush();
    }

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    /**
     * 检查当前的用户是否有可用的借书卡
     */
    public void checkHaveCard(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<BorrowCard> list = service.listCanUseCard(getCurrentLoginUser(req,resp).getId());
        Map<String,Object> map=new HashMap<>();
        String msg = "error";
        if (list != null && list.size() > 0) {
            msg = "ok";
        }
        map.put("msg",msg);
        map.put("cards",list);
        //然后把map转化为Json数据
        String s= JSONObject.toJSONString(map);
        //设置响应的编码
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(s);
        writer.flush();
    }
}
