package com.RL.controller;



import com.RL.domain.Author;
import com.RL.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
public class AuthorController {
    //..Mustafa
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Author author){
        authorService.createAuthor(author);
        return ResponseEntity.ok("the author is created");
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll(){
        List<Author> student=authorService.getAll();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable("id") Long id){
        Author author= authorService.findById(id);
        return  ResponseEntity.ok(author);
    }

}