package com.org.demoservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.demoentity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.org.demoentity.DTO.BookAddDTO;
import com.org.demoentity.DTO.BookUpdateDTO;

import java.util.List;


/**
* @author Lofi
* @description 针对表【book(图书表)】的数据库操作Service
* @createDate 2026-04-08 14:14:15
*/
public interface BookService extends IService<Book> {

    //查询类接口

    Book findById(Long id);

    Book findByIsbn(String isbn);

    Page<Book> pageBooks(
            Integer pageNum,
            Integer pageSize,
            String keyword,
            String category
    );

    //增删改类接口

    void addBook(BookAddDTO bookAddDTO);

    void addBooks(List<BookAddDTO> bookAddDTOList);

    void updateBook(BookUpdateDTO bookUpdateDTO);

    void deleteBook(Long id);

    void deleteBooks(List<Long> ids);

}
