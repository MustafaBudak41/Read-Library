package com.RL.service;
import com.RL.domain.Category;
import com.RL.repository.CategoryRepository;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    BookRepository bookRepository;
    public Category saveCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }
    public Page<Category> getAllWithPage(Pageable pageable) {
        return  categoryRepository.findAll(pageable);
    }
    public Category updateCategory(Long id, Category category) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));
        foundCategory.setName(category.getName());
        foundCategory.setSequence(category.getSequence());
        foundCategory.setBuiltIn(category.getBuiltIn());
        categoryRepository.save(foundCategory);
        category.setId(foundCategory.getId());
        return category;
    }
    public Category deleteCategory(Long id) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));
        return foundCategory;
       /* if(bookRepository.existsBookCategoryId(id) != null){
            throw new ConflictException(String.format(ErrorMessage.CATEGORY_NOT_DELETE_MESSAGE,id));
        }else{
            categoryRepository.delete(foundCategory);
        }
        return foundCategory;*/
    }
    public Category getCategory(Long id) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));
        return foundCategory;
    }
}