package com.Forming.book.service.impl;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BorrowCard;
import com.Forming.book.dao.IBorrowCardDao;
import com.Forming.book.dao.impl.BorrowCardDaoImpl;
import com.Forming.book.service.IBorrowCardService;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class BorrowCardServiceImpl implements IBorrowCardService {
    IBorrowCardDao dao = new BorrowCardDaoImpl();

    @Override
    public void listPage(PageUtils pageUtils, SysUser user) {
        List<BorrowCard> books = dao.listPage(pageUtils,user);
        int count = dao.count(pageUtils,user);
        pageUtils.setList(books);
        pageUtils.setTotalCount(count);
    }

    @Override
    public int count(PageUtils pageUtils, SysUser user) {
        return dao.count(pageUtils,  user);
    }

    @Override
    public List<BorrowCard> list() {
        return dao.list();
    }

    @Override
    public int save(BorrowCard entity) {
        return dao.save(entity);
    }

    @Override
    public BorrowCard findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(BorrowCard entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }

    @Override
    public List<BorrowCard> listCanUseCard(Integer userId) {
        return dao.listCanUseCard(userId);
    }
}

