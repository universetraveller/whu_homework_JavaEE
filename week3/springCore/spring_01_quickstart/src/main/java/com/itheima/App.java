package com.itheima;

import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.service.impl.BookServiceImpl;

public class App {
    public static void main(String[] args) {
        BookDao bookDao=new BookDaoImpl();
        //BookDao bookDao=new BookDaoImpl2();
        BookServiceImpl bookService = new BookServiceImpl();
        //依赖关系在使用的时候进行设置
        bookService.setBookDao(bookDao);
        bookService.save();
    }
}
