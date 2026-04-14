package com.org.democontroller.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.democommon.Result.R;
import com.org.demoentity.BorrowRecord;
import com.org.demoservice.BorrowRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    private final BorrowRecordService borrowRecordService;
    BorrowController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    //==================admin相关业务====================

    @GetMapping("/admin/getByUser/{userId}")
    @PreAuthorize("hasRole('admin')")
    public R<List<BorrowRecord>> getBorrowRecordsByUserId(@PathVariable Long userId){
        List<BorrowRecord> borrowRecords = borrowRecordService.getBorrowRecordsByUserId(userId);
        return R.success(borrowRecords);
    }

    @GetMapping("/admin/getAll")
    @PreAuthorize("hasRole('admin')")
    public R<Page<BorrowRecord>> getBorrowRecordsById(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    )
    {
        Page<BorrowRecord> borrowRecordPage = borrowRecordService.getBorrowRecords(pageNum, pageSize);
        return R.success(borrowRecordPage);
    }

    @GetMapping("/admin/getByStatus/{status}")
    @PreAuthorize("hasRole('admin')")
    public R<Page<BorrowRecord>> getBorrowRecordsByStatus(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @PathVariable Integer status
    )
    {
        Page<BorrowRecord> borrowRecordPage = borrowRecordService.getBorrowRecordsByStatus(pageNum, pageSize, status);
        return R.success(borrowRecordPage);
    }

    //==================user相关业务====================

    @GetMapping("/user/info")
    @PreAuthorize("hasRole('user')")
    public R<List<BorrowRecord>> getCurrentBorrowRecord(){
        return R.success(borrowRecordService.getCurrentBorrowRecords());
    }

    @PostMapping("/user/borrow")
    @PreAuthorize("hasRole('user')")
    public R<Void> borrowBook(@RequestParam Long bookId){
        borrowRecordService.borrowBook(bookId);
        return R.success();
    }

    @PutMapping("/user/return")
    @PreAuthorize("hasRole('user')")
    public R<Void> returnBook(@RequestBody BorrowRecord borrowRecord){
        borrowRecordService.returnBook(borrowRecord);
        return R.success();
    }

}
