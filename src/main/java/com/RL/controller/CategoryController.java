package com.RL.controller;
import com.RL.domain.Category;
import com.RL.service.CategoryService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get Categories With Pages
    @GetMapping()
    public ResponseEntity<Page<Category>> getCategoryWithPage(
            @RequestParam(required = false,value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false,value = "direction", defaultValue = "ASC") Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Category> catetegoryPage = categoryService.getAllWithPage(pageable);

        return ResponseEntity.ok(catetegoryPage);
    }

    //Get Category With Id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        Category category =  categoryService.getCategory(id);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    //Create Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
        categoryService.saveCategory(category);

        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    // Update Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category){
        categoryService.updateCategory(id,category);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    //Delete Category
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
        Category category =  categoryService.deleteCategory(id);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

}