package com.org.democontroller.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.democommon.Result.R;
import com.org.demoentity.Book;
import com.org.demoentity.DTO.BookAddDTO;
import com.org.demoentity.DTO.BookUpdateDTO;
import com.org.demoservice.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "图书信息管理", description = "提供图书数据相关的API接口")
public class BookController {

    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    //Get接口

    @GetMapping("/page")
    @Operation(summary = "分页查询书籍", description = "返回分页的书籍列表，支持按关键词和分类名过滤")
    public R<Page<Book>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false)
            @Parameter(name = "keyword", description = "关键词（支持书名，作者，出版社等关键词模糊查询）", required = false, example = "余华")
            String keyword,
            @RequestParam(required = false)
            @Parameter(name = "category", description = "分类名称", required = false, example = "小说")
            String category
    ){
        Page<Book> bookList = bookService.pageBooks(pageNum, pageSize, keyword, category);
        return R.success(bookList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "id查询书籍", description = "返回指定ID的书籍信息")
    public R<Book> getById(
            @PathVariable
            @Parameter(name = "id", description = "图书id", required = true, example = "123")
            Long id) {
        Book book = bookService.findById(id);
        return R.success(book);
    }

    @GetMapping("/ISBN/{isbn}")
    @Operation(summary = "ISBN查询书籍", description = "返回指定ISBN的书籍信息")
    public R<Book> getByISBN(
            @PathVariable
            @Parameter(name = "isbn", description = "图书isbn", required = true, example = "693340123")
            String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return R.success(book);
    }

    //Post接口

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "添加书籍", description = "管理员添加单本图书，必须选择二级分类")
    public R<Void> addBook(
            @Valid
            @RequestBody
            @Schema(
                    description = "待添加图书信息",
                    example = """
                    {
                        "bookName": "Java编程思想",
                        "author": "Bruce Eckel",
                        "categoryId": 2,
                        "publisher": "机械工业出版社",
                        "publishDate": "2020-01-01",
                        "totalStock": 10,
                        "borrowableStock": 10,
                        "isBorrowable": 1
                    }"""
            )
            BookAddDTO bookAddDTO){
        bookService.addBook(bookAddDTO);
        return R.success();
    }

    @PostMapping("/batch")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "批量添加书籍", description = "管理员批量导入图书")
    public R<Void> addBooks(
            @Valid
            @RequestBody
            @Schema(
                    description = "待添加图书信息列表",
                    example = """
                    [
                        {
                            "bookName": "Java编程思想",
                            "author": "Bruce Eckel",
                            "categoryId": 2,
                            "publisher": "机械工业出版社",
                            "totalStock": 10,
                            "borrowableStock": 10
                        },
                        {
                            "bookName": "MySQL从入门到精通",
                            "author": "张三",
                            "categoryId": 3,
                            "publisher": "人民邮电出版社",
                            "totalStock": 5,
                            "borrowableStock": 5
                        }
                    ]"""
            )
            List<BookAddDTO> bookAddDTOs){
        bookService.addBooks(bookAddDTOs);
        return R.success();
    }

    //Update接口

    @PutMapping("")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "更新书籍信息", description = "根据图书ID更新，必须携带ID")
    public R<Void> updateBook(
            @Valid
            @RequestBody
            @Schema(
                    description = "图书更新信息",
                    example = """
                    {
                        "id": 1,
                        "bookName": "Java编程思想（第4版）",
                        "author": "Bruce Eckel",
                        "categoryId": 2,
                        "totalStock": 15,
                        "borrowableStock": 15
                    }"""
            )
            BookUpdateDTO bookUpdateDTO){
        bookService.updateBook(bookUpdateDTO);
        return R.success();
    }

    //Delete接口

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "删除书籍", description = "返回删除成功的书籍信息")
    public R<Void> deleteBook(
            @PathVariable
            @Parameter(name = "id", description = "图书id", required = true, example = "123")
            Long id){
        bookService.deleteBook(id);
        return R.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "批量删除书籍", description = "返回删除成功的书籍信息")
    public R<Void> deleteBooks(
            @RequestParam
            @Parameter(name = "ids", description = "图书id列表", required = true, example = "[1,2,3]")
            List<Long> ids){
        bookService.deleteBooks(ids);
        return R.success();
    }

}
