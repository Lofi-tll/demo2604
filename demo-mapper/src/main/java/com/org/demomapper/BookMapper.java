package com.org.demomapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.demoentity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Lofi
* @description 针对表【book(图书表)】的数据库操作Mapper
* @createDate 2026-04-08 14:14:15
* @Entity com.org.demoentity.entity.Book
*/

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    //查询操作的mapper实现

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.id = #{id} AND b.is_delete = 0
            """)
    public Book selectBookCategory(@Param("id") Long id);

    @Select("""
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.isbn = #{isbn} AND b.is_delete = 0
            """)
    public Book selectBookWithIsbn(@Param("isbn") String isbn);

    @Select("""
        <script>
            SELECT b.*, c.name AS category_name
            FROM book b
            LEFT JOIN book_category c ON b.category_id = c.id
            WHERE b.is_delete = 0

            <if test="keyword != null">
            (
                AND (b.name LIKE CONCAT('%', #{keyword}, '%')
                     OR b.author LIKE CONCAT('%', #{keyword}, '%')
                     OR b.publisher LIKE CONCAT('%', #{keyword}, '%'))
            )
            </if>

            <if test="category != null">
                AND c.name = #{category}
            </if>
            ORDER BY b.id DESC
        </script>
        """)
    Page<Book> pageBooks(
            Page<Book> page,
            @Param("keyword") String keyword,
            @Param("category") String category
    );

    //批量添加操作

    @Insert("""
            INSERT INTO book (
                name, author, isbn, category_id, total_stock,
                borrowable_stock, publisher, publish_date,
                description, is_borrowable
            )
            VALUES
            (#{book.name}, #{book.author}, #{book.isbn}, #{book.categoryId},
             #{book.totalStock}, #{book.borrowableStock}, #{book.publisher},
             #{book.publishDate}, #{book.description}, #{book.isBorrowable}
            )
            """)
    void addBook(@Param("book") Book book);

    @Insert("""
        <script>
            INSERT INTO book (
                name, author, isbn, category_id, total_stock,
                borrowable_stock, publisher, publish_date,
                description, is_borrowable
            )
            VALUES
            <foreach collection="bookList" item= "book" separator=",">
                (#{book.name}, #{book.author}, #{book.isbn}, #{book.categoryId},
                 #{book.totalStock}, #{book.borrowableStock}, #{book.publisher},
                 #{book.publishDate}, #{book.description}, #{book.isBorrowable}
                )
            </foreach>
        </script>
        """)
    void insertBatch(@Param("bookList") List<Book> bookList);

}
