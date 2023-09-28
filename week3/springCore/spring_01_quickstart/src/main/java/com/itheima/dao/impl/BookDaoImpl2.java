package com.itheima.dao.impl;

import com.itheima.dao.BookDao;

public class BookDaoImpl2 implements BookDao {
    @Override
    public void save() {
        System.out.println("book dao save ...");
    }
}
