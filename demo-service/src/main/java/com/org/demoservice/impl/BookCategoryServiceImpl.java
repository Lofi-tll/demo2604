package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.demoentity.BookCategory;
import com.org.demomapper.BookCategoryMapper;
import com.org.demoservice.BookCategoryService;
import org.springframework.stereotype.Service;

/**
* @author MR
* @description 针对表【book_category(图书种类表)】的数据库操作Service实现
* @createDate 2026-04-17 17:25:14
*/
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory>
    implements BookCategoryService{

    private final BookCategoryMapper bookCategoryMapper;
    public BookCategoryServiceImpl(BookCategoryMapper bookCategoryMapper) {
        this.bookCategoryMapper = bookCategoryMapper;
    }



}




