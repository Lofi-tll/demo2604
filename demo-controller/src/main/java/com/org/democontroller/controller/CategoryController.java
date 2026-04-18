package com.org.democontroller.controller;

import com.org.democommon.Result.R;
import com.org.demoentity.BookCategory;
import com.org.demoservice.BookCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Tag(name = "图书分类管理", description = "提供图书分类相关的API接口")

public class CategoryController {
    private final BookCategoryService bookCategoryService;
    public CategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping("/parents")
    @Operation(summary = "获取一级分类", description = "返回所有一级分类的列表")
    public R<List<BookCategory>> findParentCategories(){
        List<BookCategory> bookCategoryList = bookCategoryService.findParentCategories();
        return R.success(bookCategoryList);
    }

    @GetMapping("/secondaries/{parentId}")
    @Operation(summary = "根据一级分类获取目下的二级分类", description = "返回目下的二级分类的列表")
    public R<List<BookCategory>> findSecondaryCategories(
            @PathVariable
            @Parameter(
                    name = "parentId",
                    description = "父分类id",
                    required = true,
                    example = "123"
            )
            Integer parentId){
        List<BookCategory> bookCategoryList = bookCategoryService.findSecondaryCategories(parentId);
        return R.success(bookCategoryList);
    }

    @GetMapping("/names")
    @Operation(summary = "根据类名获取分类", description = "返回模糊查询符合条件的分类的列表")
    public R<List<BookCategory>> findByName(
            @RequestParam
            @Parameter(
                    name = "name",
                    description = "分类名称",
                    required = true,
                    example = "文学"
            )
            String name){
        List<BookCategory> bookCategoryList = bookCategoryService.findByName(name);
        return R.success(bookCategoryList);
    }

    @PostMapping
    @Operation(summary = "新增分类")
    public R<Void> addCategory(
            @RequestBody
            @Schema(
                    description = "图书分类信息，parentId默认一级分类，二级分类填对应的一级分类id",
                    example = """
                    {
                        "parentId": 0,
                        "name" : "文学"
                    }"""
            )
            BookCategory bookCategory) {
        bookCategoryService.addCategory(bookCategory);
        return R.success();
    }

    @PutMapping
    @Operation(summary = "更新分类")
    public R<Void> updateCategory(
            @RequestBody
            @Schema(
                    description = "图书分类更新信息",
                    example = """
                    {
                        "id": 1,
                        "parentId": 0,
                        "name" : "文学"
                    }"""
            )
            BookCategory bookCategory) {
        bookCategoryService.updateCategory(bookCategory);
        return R.success();
    }

    @DeleteMapping
    @Operation(summary = "删除分类")
    public R<Void> deleteCategory(
            @RequestParam
            @Parameter(
                    name = "id",
                    description = "待删除的分类id，若为一级分类其下所有二级分类也会删除",
                    required = true,
                    example = "123"
            )
            Long categoryId){
        bookCategoryService.deleteCategory(categoryId);
        return R.success();
    }
}
