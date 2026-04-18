package com.org.demomapper;

import com.org.demoentity.BookCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author MR
* @description 针对表【book_category(图书种类表)】的数据库操作Mapper
* @createDate 2026-04-17 17:25:14
* @Entity com.org.demoentity.BookCategory
*/
public interface BookCategoryMapper extends BaseMapper<BookCategory> {

}




