package com.RL.Test;

import com.RL.domain.*;
import com.RL.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserCRUDTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    String name = "Zekeriya Kaya test0111";
    @Test
    public void userCreateTest() throws ParseException {
        User user = new User();
        user.setFirstName(name);
        user.setLastName("Kaya test0111");
        user.setScore(1);
        user.setAddress("usa new york 44");
        user.setPhone("(541) 317-8828");
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        user.setBirthDate(simpleDateFormat.parse("12-01-2013"));
        user.setEmail("aslan1@email.com");
        user.setPassword("$2a$10$m6sGgv3WQJP0aM1uWYpZfeuKA153q2h66G6tVt6OdYZw1VEKi4Au6");
        user.setCreateDate(LocalDateTime.now());
        user.setResetPasswordCode("fawer");
        user.setBuiltIn(false);
        repository.save(user);
    }
 //   @Test
    public void bookUpdateTest() {
        List<User> objs = repository.findAll();
        for (var each : objs) {
            if (each.getFirstName().equals(name)) {
                each.setFirstName(name + " Update");
                repository.save(each);
            }
        }
    }

  //  @Test
    public void bookDeleteTest() {
        List<User> objs = repository.findAll();
        for (var each : objs) {
            if (each.getFirstName().equals(name + " Update") || each.getFirstName().equals(name)) {
                repository.deleteById(each.getId());
            }
        }
    }

}
