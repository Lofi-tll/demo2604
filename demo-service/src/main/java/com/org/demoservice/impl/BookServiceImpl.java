package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.demoentity.Book;
import com.org.demomapper.BookMapper;
import com.org.demoservice.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Lofi
* @description 针对表【book(图书表)】的数据库操作Service实现
* @createDate 2026-04-08 14:14:15
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    private final BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        Book book = bookMapper.selectBookCategory(id);
        if (book == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }
         return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByName(String name) {
        return bookMapper.selectBookWithName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findByIsbn(String isbn) {
        Book book = super.getOne(lambdaQuery().eq(Book::getIsbn, isbn),false);
        if (book == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }
        return bookMapper.selectBookCategory(book.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthor(String author) {
        return bookMapper.selectBookWithAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByCategory(Integer categoryId) {
        return bookMapper.selectListBookWithCategory(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPublisher(String publisher) {
        return bookMapper.selectBookWithPublisher(publisher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAllBook(){
        return bookMapper.selectListBookAll();
    }
}
