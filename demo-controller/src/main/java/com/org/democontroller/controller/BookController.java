package com.org.democontroller.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.democommon.Result.R;
import com.org.demoentity.Book;
import com.org.demoservice.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

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
    public R<Book> getByISBN(@PathVariable String isbn){
        Book book = bookService.findByIsbn(isbn);
        return R.success(book);
    }

    //Post接口

    @PostMapping("")
    public R<Void> addBook(@Valid @RequestBody Book book){
        bookService.addBook(book);
        return R.success();
    }

    @PostMapping("/batch")
    public R<Void> addBooks(@Valid @RequestBody List<Book> books){
        bookService.addBooks(books);
        return R.success();
    }

    //Update接口

    @PutMapping("")
    public R<Void> updateBook(@Valid @RequestBody Book book){
        bookService.updateBook(book);
        return R.success();
    }

    //Delete接口

    @DeleteMapping("")
    public R<Void> deleteBook(@RequestBody Book book){
        bookService.deleteBook(book.getId());
        return R.success();
    }

    @DeleteMapping("/batch")
    public R<Void> deleteBooks(@RequestBody List<Book> books){
        List<Long> ids = books.stream().map(Book::getId).toList();
        bookService.deleteBooks(ids);
        return R.success();
    }

}
