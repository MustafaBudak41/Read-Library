package com.RL.controller;



import com.RL.domain.Author;
import com.RL.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
public class AuthorController {
    //..Mustafa
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author author){
        authorService.createAuthor(author);
        return ResponseEntity.ok("the author is created");
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll(){
        List<Author> author=authorService.getAll();
        return ResponseEntity.ok(author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable("id") Long id){
        Author author= authorService.findById(id);
        return  ResponseEntity.ok(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String,String>> updateArrow(@PathVariable Long id,@Valid @RequestBody Author author){
        authorService.updateAuthor(id,author);
        Map<String,String> map=new HashMap<>();
        map.put("message", "Student updated successfuly");
        map.put("status", "true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}