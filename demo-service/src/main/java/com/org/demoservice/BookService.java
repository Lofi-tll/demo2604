package com.org.demoservice;

import com.org.demoentity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author MR
* @description 针对表【book(图书表)】的数据库操作Service
* @createDate 2026-04-08 14:14:15
*/
public interface BookService extends IService<Book> {

    Book findById(Long id);

    List<Book> findByName(String name);

    Book findByIsbn(String isbn);

    List<Book> findByAuthor(String author);

    List<Book> findByCategory(Integer category);

    List<Book> findByPublisher(String publisher);

    List<Book> findAllBook();
}
