package com.org.democontroller.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.democommon.Result.R;
import com.org.demoentity.Book;
import com.org.demoentity.DTO.BookAddDTO;
import com.org.demoentity.DTO.BookUpdateDTO;
import com.org.demoservice.BookService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    //Get接口

    @GetMapping("/page")
    public R<Page<Book>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category
    ){
        Page<Book> bookList = bookService.pageBooks(pageNum, pageSize, keyword, category);
        return R.success(bookList);
    }

    @GetMapping("/{id}")
    public R<Book> getById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return R.success(book);
    }

    @GetMapping("/ISBN/{isbn}")
    public R<Book> getByISBN(@PathVariable String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return R.success(book);
    }

    //Post接口

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public R<Void> addBook(@Valid @RequestBody BookAddDTO bookAddDTO){
        bookService.addBook(bookAddDTO);
        return R.success();
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole('admin')")
    public R<Void> addBooks(@Valid @RequestBody List<BookAddDTO> bookAddDTOs){
        bookService.addBooks(bookAddDTOs);
        return R.success();
    }

    //Update接口

    @PutMapping("")
    @PreAuthorize("hasRole('admin')")
    public R<Void> updateBook(@Valid @RequestBody BookUpdateDTO bookUpdateDTO){
        bookService.updateBook(bookUpdateDTO);
        return R.success();
    }

    //Delete接口

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public R<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return R.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasRole('admin')")
    public R<Void> deleteBooks(@RequestParam List<Long> ids){
        bookService.deleteBooks(ids);
        return R.success();
    }

}
