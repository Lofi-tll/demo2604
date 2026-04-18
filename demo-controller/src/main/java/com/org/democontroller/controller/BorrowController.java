package com.org.democontroller.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.democommon.Result.R;
import com.org.demoentity.BorrowRecord;
import com.org.demoentity.DTO.BorrowReturnDTO;
import com.org.demoservice.BorrowRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "图书借阅管理", description = "提供图书借阅相关的API接口")
public class BorrowController {

    private final BorrowRecordService borrowRecordService;
    BorrowController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    //==================admin相关业务====================

    @GetMapping("/admin/getByUser/{userId}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "获取任一用户的借阅记录", description = "返回指定用户的借阅记录列表")
    public R<List<BorrowRecord>> getBorrowRecordsByUserId(
            @PathVariable
            @Parameter(name = "userId", description = "待查询用户id", required = true, example = "123")
            Long userId){
        List<BorrowRecord> borrowRecords = borrowRecordService.getBorrowRecordsByUserId(userId);
        return R.success(borrowRecords);
    }

    @GetMapping("/admin/getAll")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "获取全部借阅记录", description = "返回全部借阅记录的分页列表，按创建时间逆序排序")
    public R<Page<BorrowRecord>> getBorrowRecords(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    )
    {
        Page<BorrowRecord> borrowRecordPage = borrowRecordService.getBorrowRecords(pageNum, pageSize);
        return R.success(borrowRecordPage);
    }

    @GetMapping("/admin/getByStatus/{status}")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "按借阅状态获取记录", description = "返回指定借阅状态的借阅记录的分页列表，按创建时间逆序排序。未归还0 已归还1")
    public R<Page<BorrowRecord>> getBorrowRecordsByStatus(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @PathVariable
            @Parameter(name = "status", description = "借阅状态", required = true, example = "0")
            Integer status
    )
    {
        Page<BorrowRecord> borrowRecordPage = borrowRecordService.getBorrowRecordsByStatus(pageNum, pageSize, status);
        return R.success(borrowRecordPage);
    }

    //==================user相关业务====================

    @GetMapping("/user/info")
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "获取当前用户的借阅记录", description = "返回当前用户的借阅记录列表，按创建时间逆序排序")
    public R<List<BorrowRecord>> getCurrentBorrowRecord(){
        return R.success(borrowRecordService.getCurrentBorrowRecords());
    }

    @PostMapping("/user/borrow")
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "借阅图书", description = "返回成功借阅的结果，借阅失败会抛出异常并返回错误信息")
    public R<Void> borrowBook(
            @RequestParam
            @Parameter(name = "bookId", description = "书籍id", required = true, example = "123")
            Long bookId){
        borrowRecordService.borrowBook(bookId);
        return R.success();
    }

    @PutMapping("/user/return")
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "归还图书", description = "返回成功归还的结果，归还失败会抛出异常并返回错误信息")
    public R<Void> returnBook(
            @RequestBody
            @Schema(
                    description = "归还图书信息，id为必填项，其他字段可选",
                    example = """
                    {
                        "id": 1
                    }"""
            )
            BorrowReturnDTO borrowReturnDTO){
        borrowRecordService.returnBook(borrowReturnDTO);
        return R.success();
    }

}
