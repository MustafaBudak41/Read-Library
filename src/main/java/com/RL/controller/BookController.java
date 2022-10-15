package com.RL.controller;
import com.RL.domain.Book;
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
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class BookController {

        private BookServiceImpl bookService;

        @PostMapping("/books")
        public ResponseEntity<Map<String,String>>  createBook(@Valid @RequestBody BookDTO bookDTO){

                Book book =bookService.createBook(bookDTO);


                Map<String,String> map=new HashMap<>();
                map.put("id : ", book.getId().toString());
                map.put("name : ",book.getName());

                return new ResponseEntity<>(map,HttpStatus.CREATED);

        }
    }

