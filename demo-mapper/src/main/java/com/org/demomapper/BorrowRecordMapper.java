package com.org.demomapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.demoentity.BorrowRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author MR
* @description 针对表【borrow_record(借阅记录表)】的数据库操作Mapper
* @createDate 2026-04-14 12:49:29
* @Entity com.org.demoentity.BorrowRecord
*/
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {

    @Select("""
            SELECT
            id,
            user_id,
            book_id,
            borrow_status,
            IF(expected_return_time < NOW(), 1, 0) AS is_overdue
            FROM borrow_record
            WHERE user_id = #{userId}
            """)
    public List<BorrowRecord> selectByUserId(@Param("userId") Long userId);

    @Select("""
            SELECT
            id,
            user_id,
            book_id,
            borrow_status,
            IF(borrow_status = 0 AND expected_return_time < NOW(), 1, 0) AS is_overdue
            FROM borrow_record
            ORDER BY create_time DESC
            """)
    public Page<BorrowRecord> selectAll(Page<BorrowRecord> page);

    @Select("""
            SELECT
            id,
            user_id,
            book_id,
            borrow_status,
            IF(borrow_status = 0 AND expected_return_time < NOW(), 1, 0) AS is_overdue
            FROM borrow_record
            WHERE borrow_status = #{status}
            ORDER BY create_time DESC
            """)
    public Page<BorrowRecord> selectPageByStatus(Page<BorrowRecord> page,@Param("status") Integer status);
}




