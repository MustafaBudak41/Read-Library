package com.RL.service;


import com.RL.domain.Category;
import com.RL.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }
}

