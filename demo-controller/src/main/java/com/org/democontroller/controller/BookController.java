package com.org.democontroller.controller;

import com.org.democommon.Result.R;
import com.org.demoentity.Book;
import com.org.demoservice.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getById/{id}")
    public R<Book> getById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return R.success(book);
    }

}
