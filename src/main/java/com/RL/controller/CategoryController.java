package com.RL.controller;
import com.RL.domain.Category;
import com.RL.service.CategoryService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
        categoryService.saveCategory(category);
        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

}
