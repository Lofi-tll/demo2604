package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public Book findByIsbn(String isbn) {
        Book book = bookMapper.selectBookWithIsbn(isbn);
        if (book == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> pageBooks(
            Integer pageNum,
            Integer pageSize,
            String keyword,
            String category
    ){
        Page<Book> page = new Page<>(pageNum, pageSize);
        return bookMapper.pageBooks(page, keyword, category);
    }

    @Override
    public void addBook(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public void addBooks(List<Book> books) {
        bookMapper.insertBatch(books);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateById(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookMapper.deleteById(id);
    }

    @Override
    public void deleteBooks(List<Long> ids) {
        bookMapper.deleteBatchIds(ids);
    }
}
