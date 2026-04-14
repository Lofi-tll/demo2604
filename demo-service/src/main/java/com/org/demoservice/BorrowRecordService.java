package com.org.demoservice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.demoentity.BorrowRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author MR
* @description 针对表【borrow_record(借阅记录表)】的数据库操作Service
* @createDate 2026-04-14 12:49:29
*/
public interface BorrowRecordService extends IService<BorrowRecord> {

    //==================user相关业务====================

    public List<BorrowRecord> getCurrentBorrowRecords();

    public void borrowBook(Long bookId);

    public void returnBook(BorrowRecord borrowRecord);

    //==================admin相关业务====================
    public List<BorrowRecord> getBorrowRecordsByUserId(Long userId);

    public Page<BorrowRecord> getBorrowRecords(Integer pageNum, Integer pageSize);

    public Page<BorrowRecord>  getBorrowRecordsByStatus(Integer pageNum, Integer pageSize, Integer status);
}
