package com.org.demoservice;

import com.org.demoentity.BookCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lofi
* @description 针对表【book_category(图书种类表)】的数据库操作Service
* @createDate 2026-04-17 17:25:14
*/
public interface BookCategoryService extends IService<BookCategory> {

    public List<BookCategory> findParentCategories();

    public List<BookCategory> findSecondaryCategories(Integer parentId);

    public List<BookCategory> findByName(String name);

    public void addCategory(BookCategory bookCategory);

    public void deleteCategory(Long categoryId);

    public void updateCategory(BookCategory bookCategory);
}
