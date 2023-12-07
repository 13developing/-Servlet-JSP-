package com.Forming.book.service.impl;

import com.Forming.book.bean.Student;
import com.Forming.book.dao.IStudentDao;
import com.Forming.book.dao.impl.StudentDaoImpl;
import com.Forming.book.service.IStudentService;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class StudentServiceImpl implements IStudentService {
    private IStudentDao dao = new StudentDaoImpl();

    @Override
    public void listPage(PageUtils pageUtils) {
        List<Student> students = dao.listPage(pageUtils);
        int count = dao.count(pageUtils);
        pageUtils.setList(students);
        pageUtils.setTotalCount(count);
    }

    @Override
    public int count(PageUtils pageUtils) {
        return dao.count(pageUtils);
    }

    @Override
    public int save(Student entity) {
        return dao.save(entity);
    }

    @Override
    public Student findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(Student entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public List<Student> list() {
        return dao.list();
    }
}
