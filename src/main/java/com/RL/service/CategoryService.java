package com.RL.service;


import com.RL.domain.Author;
import com.RL.domain.Category;
import com.RL.dto.AuthorDTO;
import com.RL.dto.CategoryDTO;
import com.RL.dto.mapper.AuthorMapper;
import com.RL.dto.mapper.CategoryMapper;
import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.AuthorRepository;
import com.RL.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class CategoryService {

    private CategoryRepository repository;
    private CategoryMapper categoryMapper;

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        repository.save(category);
        return category;
    }
    public List<CategoryDTO> getAll() {
        List<Category> categoryList = repository.findAll();
        return categoryMapper.map(categoryList);
    }

    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoryMapper.categoryToCategoryDTO(category);
    }

    public Category updateCategory(Long id, Category category) {
        Category foundCategory = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if(foundCategory.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        foundCategory.setId(id);
        foundCategory.setName(category.getName());
        repository.save(foundCategory);
        return foundCategory;
    }

    public Category deleteById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if(category.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if(!category.getBooks().isEmpty()) {
            throw  new ResourceNotFoundException("You cannot delete an category who has a book");
        }
        repository.deleteById(id);
        return category;
    }

    public Page<CategoryDTO> getCategoryPage(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        Page<CategoryDTO> dtoPage = categories.map(category -> categoryMapper.categoryToCategoryDTO(category));

        return dtoPage;
    }


}