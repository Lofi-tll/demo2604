package com.org.demomapper;

import com.org.demoentity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author MR
* @description 针对表【book(图书表)】的数据库操作Mapper
* @createDate 2026-04-08 14:14:15
* @Entity com.org.demoentity.entity.Book
*/

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.id = #{id} AND b.is_delete = 0
            """)
    public Book selectBookCategory(Long id);

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.name LIKE CONCAT('%', #{name}, '%') AND b.is_delete = 0
            """)
    public List<Book> selectBookWithName(String name);

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.author LIKE CONCAT('%', #{author}, '%') AND b.is_delete = 0
            """)
    public List<Book> selectBookWithAuthor(String author);

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.publisher LIKE CONCAT('%', #{publisher}, '%') AND b.is_delete = 0
            """)
    public List<Book> selectBookWithPublisher(String publisher);

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.category_id = #{categoryId} AND b.is_delete = 0
            """)
    public List<Book> selectListBookWithCategory(Integer categoryId);

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.is_delete = 0
            """)
    public List<Book> selectListBookAll();
}
