package com.RL.Test;

import com.RL.domain.*;
import com.RL.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorCRUDTest {
    @Autowired
    private AuthorRepository authorRepository;
    String name = "Author Test";

    @Test
    public void authorUpdateTest() {
        var objs = authorRepository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name)) {
//              authorRepository.deleteById(each.getId());
                each.setName(name + " Update");
                authorRepository.save(each);
            }
        }
    }

    @Test
    public void authorCreateTest() {
        Author author = new Author();
        author.setName(name);
        author.setBuiltIn(false);
        authorRepository.save(author);
    }

    @Test
    public void authorDeleteTest() {
        var objs = authorRepository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name + " Update") ) {
                authorRepository.deleteById(each.getId());
            }
        }
    }

}