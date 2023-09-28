package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.service.BookService;

public class BookServiceImpl implements BookService{
    private BookDao bookDao;

    public void setBookDao2(BookDao bookDao2) {
        this.bookDao = bookDao2;
    }

    @Override
    public void save() {
        System.out.println("book service save ...");
        bookDao.save();
    }
}
