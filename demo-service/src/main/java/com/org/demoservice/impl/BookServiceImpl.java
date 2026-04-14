package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.demoentity.Book;
import com.org.demoentity.DTO.BookAddDTO;
import com.org.demoentity.DTO.BookUpdateDTO;
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
    public void addBook(BookAddDTO bookAddDTO) {
        Book book = new Book();
        book.setName(bookAddDTO.getName());
        book.setAuthor(bookAddDTO.getAuthor());
        book.setIsbn(bookAddDTO.getIsbn());
        book.setCategoryId(bookAddDTO.getCategoryId());
        book.setTotalStock(bookAddDTO.getTotalStock());
        book.setBorrowableStock(bookAddDTO.getBorrowableStock());
        book.setPublisher(bookAddDTO.getPublisher());
        book.setPublishDate(bookAddDTO.getPublishDate());
        book.setDescription(bookAddDTO.getDescription());
        book.setIsBorrowable(bookAddDTO.getIsBorrowable());
        bookMapper.insert(book);
    }

    @Override
    @Transactional
    public void addBooks(List<BookAddDTO> bookAddDTOS) {
        List<Book> books = bookAddDTOS.stream().map(bookAddDTO -> {
            Book book = new Book();
            book.setName(bookAddDTO.getName());
            book.setAuthor(bookAddDTO.getAuthor());
            book.setIsbn(bookAddDTO.getIsbn());
            book.setCategoryId(bookAddDTO.getCategoryId());
            book.setTotalStock(bookAddDTO.getTotalStock());
            book.setBorrowableStock(bookAddDTO.getBorrowableStock());
            book.setPublisher(bookAddDTO.getPublisher());
            book.setPublishDate(bookAddDTO.getPublishDate());
            book.setDescription(bookAddDTO.getDescription());
            book.setIsBorrowable(bookAddDTO.getIsBorrowable());
            return book;
        }).toList();

        bookMapper.insertBatch(books);
    }

    @Override
    public void updateBook(BookUpdateDTO bookUpdateDTO) {
        Book book = new Book();
        book.setId(bookUpdateDTO.getId());
        book.setName(bookUpdateDTO.getName());
        book.setAuthor(bookUpdateDTO.getAuthor());
        book.setIsbn(bookUpdateDTO.getIsbn());
        book.setCategoryId(bookUpdateDTO.getCategoryId());
        book.setTotalStock(bookUpdateDTO.getTotalStock());
        book.setBorrowableStock(bookUpdateDTO.getBorrowableStock());
        book.setPublisher(bookUpdateDTO.getPublisher());
        book.setPublishDate(bookUpdateDTO.getPublishDate());
        book.setDescription(bookUpdateDTO.getDescription());
        book.setIsBorrowable(bookUpdateDTO.getIsBorrowable());
        bookMapper.updateById(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteBooks(List<Long> ids) {
        bookMapper.deleteBatchIds(ids);
    }
}
