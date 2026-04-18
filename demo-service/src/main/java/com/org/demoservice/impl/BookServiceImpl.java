package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.demoentity.Book;
import com.org.demoentity.BookCategory;
import com.org.demoentity.DTO.BookAddDTO;
import com.org.demoentity.DTO.BookUpdateDTO;
import com.org.demoentity.DTO.UserUpdateDTO;
import com.org.demomapper.BookCategoryMapper;
import com.org.demomapper.BookMapper;
import com.org.demomapper.UserMapper;
import com.org.demoservice.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Lofi
* @description 针对表【book(图书表)】的数据库操作Service实现
* @createDate 2026-04-08 14:14:15
*/
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    private final BookMapper bookMapper;
    private final BookCategoryMapper bookCategoryMapper;

    public BookServiceImpl(BookMapper bookMapper, BookCategoryMapper bookCategoryMapper) {
        this.bookMapper = bookMapper;
        this.bookCategoryMapper = bookCategoryMapper;
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
    @Transactional
    public void addBook(BookAddDTO bookAddDTO) {
        BookCategory category = bookCategoryMapper.selectById(bookAddDTO.getCategoryId());

        if (category == null) {
            throw new UsualException(ErrorCode.BOOK_CATEGORY_NOT_EXIST);
        }

        if (category.getParentId() == 0) {
            throw new UsualException(ErrorCode.BOOK_CATEGORY_ERROR);
        }

        Book book = new Book();
        BeanUtils.copyProperties(bookAddDTO, book);
        bookMapper.addBook(book);
    }

    @Override
    @Transactional
    public void addBooks(List<BookAddDTO> bookAddDTOS) {

        Set<Long> categoryIds = bookAddDTOS.stream()
                .map(BookAddDTO::getCategoryId)
                .collect(Collectors.toSet());

        List<BookCategory> categoryList = bookCategoryMapper.selectBatchIds(categoryIds);
        Map<Long, BookCategory> categoryMap = categoryList.stream()
                .collect(Collectors.toMap(BookCategory::getId, c -> c));

        List<Book> bookList = new ArrayList<>();
        for (BookAddDTO dto : bookAddDTOS) {
            Long cid = dto.getCategoryId();
            BookCategory category = categoryMap.get(cid);

            if (category == null) {
                throw new UsualException(ErrorCode.BOOK_CATEGORY_NOT_EXIST);
            }

            if (category.getParentId() == 0) {
                throw new UsualException(ErrorCode.BOOK_CATEGORY_ERROR);
            }

            Book book = new Book();
            BeanUtils.copyProperties(dto, book);
            bookList.add(book);
        }

        bookMapper.insertBatch(bookList);
    }

    @Override
    @Transactional
    public void updateBook(BookUpdateDTO bookUpdateDTO) {
        Book exist = bookMapper.selectById(bookUpdateDTO.getId());
        if (exist == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }
        BookCategory category = bookCategoryMapper.selectById(bookUpdateDTO.getCategoryId());
        if (category == null) {
            throw new UsualException(ErrorCode.BOOK_CATEGORY_NOT_EXIST);
        }

        if (category.getParentId() == 0) {
            throw new UsualException(ErrorCode.BOOK_CATEGORY_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookUpdateDTO, book);
        bookMapper.updateById(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book exist = bookMapper.selectById(id);
        if (exist == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }
        bookMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteBooks(List<Long> ids) {

        if (ids == null) {
            throw new UsualException(ErrorCode.PARAM_ERROR);
        }

        if (ids.isEmpty()) {
            return;
        }

        // 过滤掉null值，避免MyBatis-Plus抛出异常
        List<Long> validIds = ids.stream()
                .filter(Objects::nonNull)
                .toList();

        if (validIds.isEmpty()) {
            return;
        }


        bookMapper.deleteBatchIds(ids);
    }
}
