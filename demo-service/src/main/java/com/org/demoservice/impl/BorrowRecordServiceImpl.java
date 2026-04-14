package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.context.UserContext;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.demoentity.Book;
import com.org.demoentity.BorrowRecord;
import com.org.demomapper.BookMapper;
import com.org.demomapper.BorrowRecordMapper;
import com.org.demoservice.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author MR
* @description 针对表【borrow_record(借阅记录表)】的数据库操作Service实现
* @createDate 2026-04-14 12:49:29
*/
@Service
public class BorrowRecordServiceImpl extends ServiceImpl<BorrowRecordMapper, BorrowRecord>
    implements BorrowRecordService{

    private final BorrowRecordMapper borrowRecordMapper;
    private final BookMapper bookMapper;


    //自注入防止事务失效
    @Autowired
    //懒加载防止循环依赖报错
    @Lazy
    private BorrowRecordService self;

    BorrowRecordServiceImpl(BorrowRecordMapper borrowRecordMapper, BookMapper bookMapper) {
        this.borrowRecordMapper = borrowRecordMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getCurrentBorrowRecords() {
        Long userId = UserContext.getUser().getId();
        return borrowRecordMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void borrowBook(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }

        List<BorrowRecord> borrowRecords = self.getCurrentBorrowRecords();

        // ===================== 核心校验 =====================
        // 校验1：是否有逾期（只要有一条逾期，直接拒绝）
        boolean hasOverdue = borrowRecords.stream()
                .anyMatch(r -> r.getBorrowStatus() == 0 && r.getIsOverdue() == 1);
        if (hasOverdue) {
            throw new UsualException(ErrorCode.BORROW_OVERDUE);
        }

        // 校验2：是否已经借过这本书（未归还的）同时保证幂等
        boolean alreadyBorrow = borrowRecords.stream()
                .anyMatch(r -> r.getBorrowStatus() == 0 && r.getBookId().equals(bookId));
        if (alreadyBorrow) {
            throw new UsualException(ErrorCode.ALREADY_BORROW);
        }

        // 校验3：当前借阅中的数量是否 >= 10
        long borrowingCount = borrowRecords.stream()
                .filter(r -> r.getBorrowStatus() == 0)
                .count();
        if (borrowingCount >= 10) {
            throw new UsualException(ErrorCode.MAX_BORROW_LIMIT);
        }

//        int cnt = 0;
//        for(BorrowRecord borrowRecord : borrowRecords){
//            if(borrowRecord.getBorrowStatus() == 0) {    // 0借阅中 1已归还
//                cnt++;
//                if (cnt >= 10) {
//                    throw new UsualException(ErrorCode.MAX_BORROW_LIMIT);
//                }
//
//                if (borrowRecord.getBookId().equals(bookId)) {
//                    throw new UsualException(ErrorCode.ALREADY_BORROW);
//                }
//
//                if (borrowRecord.getIsOverdue() == 1) {   // 0未逾期 1已逾期
//                    throw new UsualException(ErrorCode.BORROW_OVERDUE);
//                }
//            }
//        }

        if (book.getIsBorrowable() != 1 || book.getBorrowableStock() <= 0) {
            throw new UsualException(ErrorCode.BOOK_STOCK_NOT_ENOUGH);
        }

        book.setBorrowableStock(book.getBorrowableStock() - 1);
        if(book.getBorrowableStock() == 0){
            book.setIsBorrowable(0);
        }
        bookMapper.updateById(book);
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBookId(bookId);
        borrowRecord.setUserId(UserContext.getUser().getId());
        borrowRecordMapper.insert(borrowRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnBook(BorrowRecord borrowRecord) {
        if(borrowRecord == null){
            throw new UsualException(ErrorCode.BORROW_RECORD_NOT_EXIST);
        }

        BorrowRecord recordInDB = borrowRecordMapper.selectById(borrowRecord.getId());

        if (recordInDB == null) {
            throw new UsualException(ErrorCode.BORROW_RECORD_NOT_EXIST);
        }

        if (recordInDB.getBorrowStatus() == 1) {
            throw new UsualException(ErrorCode.ALREADY_RETURNED);
        }

        Book book = bookMapper.selectById(borrowRecord.getBookId());
        if (book == null) {
            throw new UsualException(ErrorCode.BOOK_NOT_EXIST);
        }

        book.setBorrowableStock(book.getBorrowableStock() + 1);

        if(book.getBorrowableStock() > 0){
            book.setIsBorrowable(1);
        }

        bookMapper.updateById(book);

        recordInDB.setActualReturnTime(LocalDateTime.now());
        recordInDB.setBorrowStatus(1);
        borrowRecordMapper.updateById(recordInDB);
    }



    //=======================================================
    @Override
    public List<BorrowRecord> getBorrowRecordsByUserId(Long userId) {
        return borrowRecordMapper.selectByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BorrowRecord> getBorrowRecords(Integer pageNum, Integer pageSize) {
        Page<BorrowRecord> page = new Page<>(pageNum, pageSize);
        return borrowRecordMapper.selectAll(page);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BorrowRecord> getBorrowRecordsByStatus(Integer pageNum, Integer pageSize, Integer status) {
        Page<BorrowRecord> page = new Page<>(pageNum, pageSize);
        return borrowRecordMapper.selectPageByStatus(page, status);
    }
}




