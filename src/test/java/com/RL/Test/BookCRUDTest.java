package com.RL.Test;

import com.RL.domain.*;
import com.RL.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class BookCRUDTest {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    String name = "Zekeriya Kaya test0111";


   @Test
    public void bookCreateTest() {
        Book book = new Book();
        book.setIsbn("12345678901234555");
        book.setLoanable(true);
        book.setShelfCode("ShelfC");
        book.setActive(true);
        book.setFeatured(false);
        book.setCreateDate(LocalDateTime.now());
        book.setBuiltIn(false);
        book.setName(name);

        Optional<Author> author = authorRepository.findById(Long.parseLong(String.valueOf(1)));
        book.setAuthorId(author.get());
        Optional<Publisher> publisher = publisherRepository.findById(Long.parseLong(String.valueOf(141)));
        book.setPublisherId(publisher.get());
        Optional<Category> category = categoryRepository.findById(Long.parseLong(String.valueOf(129)));
        book.setCategoryId(category.get());

        repository.save(book);
    }
    @Test
    public void bookUpdateTest() {
        List<Book> objs = repository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name)) {
                each.setName(name + " Update");
                repository.save(each);
            }
        }
    }

    @Test
    public void bookDeleteTest() {
        List<Book> objs = repository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name + " Update") || each.getName().equals(name)) {
                repository.deleteById(each.getId());
            }
        }
    }

}
