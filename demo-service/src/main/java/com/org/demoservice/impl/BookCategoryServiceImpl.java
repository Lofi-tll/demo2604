package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.demoentity.BookCategory;
import com.org.demomapper.BookCategoryMapper;
import com.org.demoservice.BookCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author MR
* @description 针对表【book_category(图书种类表)】的数据库操作Service实现
* @createDate 2026-04-17 17:25:14
*/
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory>
    implements BookCategoryService{

    private final BookCategoryMapper bookCategoryMapper;

    public BookCategoryServiceImpl(BookCategoryMapper bookCategoryMapper) {
        this.bookCategoryMapper = bookCategoryMapper;
    }

    @Override
    public List<BookCategory> findParentCategories() {
        return this.lambdaQuery()
                .eq(BookCategory::getParentId, 0)
                .eq(BookCategory::getIsDelete, 0)
                .list();
    }

    @Override
    public List<BookCategory> findSecondaryCategories(Integer parentId) {
        return this.lambdaQuery()
                .eq(BookCategory::getParentId, parentId)
                .eq(BookCategory::getIsDelete, 0)
                .list();
    }

    @Override
    public List<BookCategory> findByName(String name) {
        return this.lambdaQuery()
                .like(BookCategory::getName, name)
                .eq(BookCategory::getIsDelete, 0)
                .list();
    }

    @Override
    public void addCategory(BookCategory bookCategory) {
        if(this.lambdaQuery()
                .eq(BookCategory::getName, bookCategory.getName())
                .eq(BookCategory::getParentId, bookCategory.getParentId())
                .exists()) {
            throw new UsualException(ErrorCode.CATEGORY_EXIST);
        }
        BookCategory parent = this.getById(bookCategory.getParentId());
        if(parent != null && parent.getParentId() > 0){
            throw new UsualException(ErrorCode.DEEP_CATEGORY);
        }
        bookCategoryMapper.insert(bookCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        BookCategory bookCategory = this.getById(categoryId);
        if(bookCategory.getParentId() == 0){
            lambdaUpdate()
                    .eq(BookCategory::getParentId, bookCategory.getId())
                    .remove();
        }
        bookCategoryMapper.deleteById(bookCategory.getId());
    }

    @Override
    public void updateCategory(BookCategory bookCategory) {
        if(this.lambdaQuery()
                .ne(BookCategory::getId, bookCategory.getId())
                .eq(BookCategory::getName, bookCategory.getName())
                .eq(BookCategory::getParentId, bookCategory.getParentId())
                .exists()) {
            throw new UsualException(ErrorCode.CATEGORY_EXIST);
        }
        BookCategory parent = this.getById(bookCategory.getParentId());
        if(parent != null && parent.getParentId() > 0){
            throw new UsualException(ErrorCode.DEEP_CATEGORY);
        }
        bookCategoryMapper.updateById(bookCategory);
    }

}




