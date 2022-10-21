package com.RL.controller;
import com.RL.domain.Book;
import com.RL.domain.User;
import com.RL.dto.BookDTO;
import com.RL.dto.request.RegisterRequest;
import com.RL.service.BookServiceImpl;

import com.RL.service.IBookService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class BookController {

        private IBookService bookService;

        @PostMapping("/books")
        public ResponseEntity<Map<String,String>> createBook(@Valid @RequestBody BookDTO bookDTO){

                Book book = bookService.createBook(bookDTO);

                Map<String,String> map=new HashMap<>();
                map.put("id : ", book.getId().toString());
                map.put("name : ",book.getName());

                return new ResponseEntity<>(map, HttpStatus.CREATED);

        }

//        @DeleteMapping("/books/{id}")
//        public ResponseEntity<Map<String,String>> deleteBook(@PathVariable ("id") Long id){
//                Book book = bookService.deleteBook(id);
//
//                Map<String,String> map=new HashMap<>();
//                map.put("id : ", book.getId().toString());
//                map.put("name : ",book.getName());
//
//                return new ResponseEntity<>(map, HttpStatus.OK);
//        }

        @GetMapping("/books/{id}")
        public ResponseEntity<Book> getBookById(@PathVariable ("id") Long id){
                Book bookDTO = bookService.findBookById(id);

                return ResponseEntity.ok(bookDTO);
        }

    }

