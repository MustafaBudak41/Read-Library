package com.RL.controller;
import com.RL.domain.Book;
import com.RL.dto.BookDTO;
import com.RL.dto.BookUpdateDTO;
import com.RL.dto.response.BookResponse;

import com.RL.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@AllArgsConstructor
@RestController
public class BookController {

        private IBookService bookService;

        @PostMapping("/books")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookDTO bookDTO){

                Book book = bookService.createBook(bookDTO);

                BookResponse response=new BookResponse();
                response.setId(book.getId());
                response.setName(book.getName());
                response.setIsbn(book.getIsbn());

                return ResponseEntity.ok(response);

        }

        @DeleteMapping("/books/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<BookResponse> deleteBook(@PathVariable ("id") Long id){
                Book book = bookService.deleteBook(id);

                BookResponse response=new BookResponse();
                response.setId(id);
                response.setName(book.getName());
                response.setIsbn(book.getIsbn());

                return ResponseEntity.ok(response);
        }

        @GetMapping("/books/{id}")
        public ResponseEntity<BookResponse> getBookById(@PathVariable ("id") Long id){
                Book book = bookService.findBookById(id);

                BookResponse response=new BookResponse();
                response.setId(id);
                response.setName(book.getName());
                response.setIsbn(book.getIsbn());

                return ResponseEntity.ok(response);
        }

        @PutMapping("/books/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<BookResponse> updateBook(@PathVariable ("id") Long id, @Valid @RequestBody BookUpdateDTO bookUpdateDTO){
                Book book = bookService.updateBook(id, bookUpdateDTO);

                BookResponse response=new BookResponse();
                response.setId(id);
                response.setName(book.getName());
                response.setIsbn(book.getIsbn());

                return ResponseEntity.ok(response);
        }

        @GetMapping("/books")
        public ResponseEntity<Page<BookResponse>> getAllWithPage(@RequestParam(required = false ,value="q") String name,
                                                                 @RequestParam(required = false,value ="cat") Long categoryId,
                                                                 @RequestParam(required = false,value ="author") Long authorId,
                                                                 @RequestParam(required = false,value ="publisher") Long publisherId,
                                                                 @RequestParam(required = false,value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                                 @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                                 @RequestParam(required = false,value = "direction", defaultValue = "ASC") Sort.Direction direction){

                Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
                Page<BookResponse> bookPage= bookService.findAllWithPage(pageable);

                return ResponseEntity.ok(bookPage);

        }

}