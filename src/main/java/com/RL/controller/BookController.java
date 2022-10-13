package com.RL.controller;
import com.RL.dto.BookDTO;
import com.RL.service.BookServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

        private BookServiceImpl bookService;

        @PostMapping("/add")
        public ResponseEntity createBook(@Valid @RequestBody BookDTO bookDTO){
      bookService.createBook(bookDTO);
       return new ResponseEntity<>(HttpStatus.CREATED);

        }
    }

