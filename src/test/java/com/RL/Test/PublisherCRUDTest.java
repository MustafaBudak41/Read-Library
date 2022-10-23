package com.RL.Test;

import com.RL.domain.Publisher;
import com.RL.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PublisherCRUDTest {
    @Autowired
    private PublisherRepository repository;
    String name = "Zekeriya Kaya test0111";

    @Test
    public void publisherCreateTest() {
        Publisher author = new Publisher();
        author.setName(name);
        author.setBuiltIn(false);
        repository.save(author);

    }

    @Test
    public void publisherUpdateTest() {
        List<Publisher> objs = repository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name)) {
                each.setName(name + " Update");
                repository.save(each);
            }
        }
    }

    @Test
    public void publisherDeleteTest() {
        List<Publisher> objs = repository.findAll();
        for (var each : objs) {
            if (each.getName().equals(name + " Update") || each.getName().equals(name)) {
                repository.deleteById(each.getId());
            }
        }
    }


}
