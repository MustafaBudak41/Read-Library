package com.RL.controller;
import com.RL.domain.Author;
import com.RL.domain.Category;
import com.RL.dto.AuthorDTO;
import com.RL.dto.CategoryDTO;
import com.RL.service.AuthorService;
import com.RL.service.CategoryService;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){

        Category newCategory  = categoryService.createCategory(categoryDTO);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", newCategory.getId().toString());
        map.put("name : ",newCategory.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    //   http://localhost:8080/categorys?size=10&page=0&sort=name&direction=ASC
    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllUserByPage(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        Page<CategoryDTO> userDTOPage=categoryService.getCategoryPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Map<String,String>>  findById(@PathVariable("id") Long id){
        CategoryDTO categoryDTO= categoryService.findById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", categoryDTO.getId().toString());
        map.put("name : ",categoryDTO.getName());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody Category category){
        Category category1 = categoryService.updateCategory(id,category);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", category1.getId().toString());
        map.put("name : ",category1.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }
    @DeleteMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> deleteById(@PathVariable("id") Long id){
        Category category= categoryService.deleteById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", category.getId().toString());
        map.put("name : ",category.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

}
