package com.RL.Test;

import com.RL.domain.*;
import com.RL.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class LoanCRUDTest {

    @Autowired
    private LoanRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    String name = "Zekeriya Kaya test0111";


    @Test
    public void loanCreateTest() {
        Loan loan = new Loan();
        loan.setLoanDate(LocalDateTime.now());
        loan.setExpireDate(LocalDateTime.now());
        Optional<User> user = userRepository.findById(Long.parseLong(String.valueOf(122)));
        loan.setUserId(user.get());
        Optional<Book> book = bookRepository.findById(Long.parseLong(String.valueOf(143)));
        loan.setBookId(book.get());
        repository.save(loan);
    }
//    @Test
//    public void bookUpdateTest() {
//        List<Book> objs = repository.findAll();
//        for (var each : objs) {
//            if (each.getName().equals(name)) {
//                each.setName(name + " Update");
//                repository.save(each);
//            }
//        }
//    }
//
//    @Test
//    public void bookDeleteTest() {
//        List<Book> objs = repository.findAll();
//        for (var each : objs) {
//            if (each.getName().equals(name + " Update") || each.getName().equals(name)) {
//                repository.deleteById(each.getId());
//            }
//        }
//    }

}
