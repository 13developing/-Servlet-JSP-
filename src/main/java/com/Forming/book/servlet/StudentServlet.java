package com.Forming.book.servlet;

import com.Forming.book.bean.Classes;
import com.Forming.book.bean.Depart;
import com.Forming.book.bean.Student;
import com.Forming.book.service.IClassesService;
import com.Forming.book.service.IDepartService;
import com.Forming.book.service.IStudentService;
import com.Forming.book.service.impl.ClassesServiceImpl;
import com.Forming.book.service.impl.DepartServiceImpl;
import com.Forming.book.service.impl.StudentServiceImpl;
import com.Forming.sys.bean.SysRole;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.service.IRoleService;
import com.Forming.sys.service.IUserService;
import com.Forming.sys.service.impl.RoleServiceImpl;
import com.Forming.sys.service.impl.UserServiceImpl;
import com.Forming.sys.servlet.BaseServlet;
import com.Forming.sys.utils.Constant;
import com.Forming.sys.utils.StringUtils;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import javax.lang.model.element.VariableElement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

@WebServlet(name = "studentServlet", urlPatterns = {"/book/studentServlet"})
public class StudentServlet extends BaseServlet {

    private IStudentService service = new StudentServiceImpl();
    private IDepartService departService = new DepartServiceImpl();
    private IClassesService classesService = new ClassesServiceImpl();
    private IUserService userService = new UserServiceImpl();
    private IRoleService roleService = new RoleServiceImpl();

    @Override
    public void list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.list(req, resp);//处理分页相关的数据
        service.listPage(pageUtils);
        //存储数据到作用域中
        req.setAttribute(Constant.LIST_PAGE_UTILS, pageUtils);
        req.getRequestDispatcher("/book/student/list.jsp").forward(req, resp);

    }

    @Override
    public void saveOrUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String stuno = req.getParameter("stuno");
        String age = req.getParameter("age");
        String gender = req.getParameter("gender");
        String telephone = req.getParameter("telephone");
        String email = req.getParameter("email");
        String wechat = req.getParameter("wechat");
        String address = req.getParameter("address");
        String classid = req.getParameter("classid");
        Student stu = new Student();
        stu.setName(name);
        stu.setStuno(stuno);
        stu.setGender(gender);
        stu.setWechat(wechat);
        stu.setEmail(email);
        stu.setTelephone(telephone);
        stu.setAddress(address);
        if (StringUtils.isNotEmpty(age)) {
            stu.setAge(Integer.parseInt(age));
        }
        if (StringUtils.isNotEmpty(classid)) {
            Integer classId = Integer.parseInt(classid);
            Classes classes = classesService.findById(classId);
            //根据班级的信息去找院系的信息
            Depart depart = departService.findById(classes.getDepartId());
            stu.setClassname(classes.getName());
            stu.setDepartname(depart.getName());
            stu.setClassid(classId);
        }
        if (StringUtils.isNotEmpty(id)) {
            stu.setId(Integer.parseInt(id));
            Student student = service.findById(Integer.parseInt(id));
            SysUser user = userService.findById(student.getAccount());
            //更新为当前表单的数据
            user.setUsername(stu.getName());
            user.setNickname(stu.getName());
            user.setId(student.getAccount());
            userService.updateById(user);//  同步更新账户信息中的信息
            stu.setAccount( student.getAccount());
            service.updateById(stu);//更新学生信息
        } else {
            SysUser user = new SysUser();
            user.setUsername(stu.getName());
            user.setNickname(stu.getName());
            user.setPassword("123456");//   默认密码为123456
            roleService.list(null);
            //创建学同步的需要创建账号信息
            SysRole role = new SysRole();
            role.setName("学生");
            List<SysRole> roles = roleService.list(role);
            if (roles != null && roles.size() > 0) {
                role = roles.get(0);
                user.setRoleId(role.getId());
                user.setRolename(role.getName());
            }
            //创建账号
            userService.save(user);
            //查询出刚刚创建的账号信息
            user = userService.findByName(user.getUsername());
            stu.setAccount( user.getId());//绑定账号
            service.save(stu);
        }
        resp.sendRedirect("/book/studentServlet?action=list");
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //查询出所属院系班级
        List<Depart> departs = departService.list();
        req.setAttribute("departs",departs);
        //判断是更新还是添加
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            Student entity = service.findById(Integer.parseInt(id));
            Classes cls = classesService.findById(entity.getClassid());
            req.setAttribute("classes",cls);
            entity.setDepartId(cls.getDepartId());
            req.setAttribute(Constant.UPDATE_ENTITY, entity);

        }
        req.getRequestDispatcher("/book/student/addOrUpdate.jsp").forward(req, resp);
    }

    @Override
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        service.deleteById(Integer.parseInt(id));
        PrintWriter writer = resp.getWriter();
        writer.write("ok");
        writer.flush();
        // TODO  同步删除账号信息
    }

    @Override
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
