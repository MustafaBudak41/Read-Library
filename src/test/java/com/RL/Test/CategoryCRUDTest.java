package com.RL.Test;
import com.RL.domain.*;
import com.RL.repository.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class CategoryCRUDTest {
    @Autowired
    private CategoryRepository repository;
    String name = "Zekeriya Kaya test0111";
    @Test
    public void CategoryCreateTest() {
        Category Category = new Category();
        Category.setName(name);
        Category.setBuiltIn(false);
        repository.save(Category);
    }
    @Test
    public void CategoryUpdateTest() {
        List<Category> objs = repository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name)) {
                each.setName(name + " Update");
                repository.save(each);
            }
        }
    }
    @Test
    public void CategoryDeleteTest() {
        List<Category> objs = repository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name + " Update") || each.getName().equals(name)) {
                repository.deleteById(each.getId());
            }
        }
    }
}