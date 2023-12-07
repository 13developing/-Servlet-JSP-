package com.Forming.book.service.impl;

import com.Forming.book.bean.Book;
import com.Forming.book.bean.BorrowCard;
import com.Forming.book.bean.BorrowRecoder;
import com.Forming.book.dao.IBorrowCardDao;
import com.Forming.book.dao.IBorrowRecoderDao;
import com.Forming.book.dao.impl.BorrowCardDaoImpl;
import com.Forming.book.dao.impl.BorrowRecoderDaoImpl;
import com.Forming.book.service.IBookService;
import com.Forming.book.service.IBorrowCardService;
import com.Forming.book.service.IBorrowRecoderService;
import com.Forming.sys.bean.SysUser;
import com.Forming.sys.utils.PageUtils;

import java.util.List;

public class BorrowRecoderServiceImpl implements IBorrowRecoderService {
    private IBorrowRecoderDao dao = new BorrowRecoderDaoImpl();
private IBookService bookService=new BookServiceImpl();

    @Override
    public void listPage(PageUtils pageUtils, SysUser user) {
        List<BorrowRecoder> list = dao.listPage(pageUtils, user);
        //  需要根据 bookid查询出对应的图片
        for (BorrowRecoder borrowRecoder : list) {
            if(borrowRecoder.getBookId()!=null){
                Book book=bookService.findById(borrowRecoder.getBookId());
                borrowRecoder.setImg(book.getImg());
            }
        }
        int count =dao.count(pageUtils,user);
        pageUtils.setList(list);
        pageUtils.setTotalCount(count);
    }

    @Override
    public int count(PageUtils pageUtils, SysUser user) {
        return dao.count(pageUtils, user);
    }

    @Override
    public List<BorrowRecoder> list() {
        return dao.list();
    }

    @Override
    public int save(BorrowRecoder entity) {
        return dao.save(entity);
    }

    @Override
    public BorrowRecoder findById(int id) {
        return dao.findById(id);
    }

    @Override
    public int updateById(BorrowRecoder entity) {
        return dao.updateById(entity);
    }

    @Override
    public int deleteById(int id) {
        return dao.deleteById(id);
    }
}

