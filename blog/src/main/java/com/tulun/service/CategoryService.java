package com.tulun.service;

import com.tulun.dao.CategoryMapper;
import com.tulun.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> loadAllCategory() {
        return categoryMapper.selectAllCategory();
    }

    public Category loadCategoryById(Integer id) {
        return categoryMapper.selectCategoryById(id);
    }
}
