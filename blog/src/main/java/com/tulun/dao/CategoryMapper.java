package com.tulun.dao;

import com.tulun.model.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> selectAllCategory();

    Category selectCategoryById(Integer id);
}
