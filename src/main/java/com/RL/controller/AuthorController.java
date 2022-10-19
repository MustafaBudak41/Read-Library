package com.RL.controller;

import com.RL.domain.Author;
import com.RL.domain.User;
import com.RL.dto.AuthorDTO;
import com.RL.dto.mapper.AuthorMapper;
import com.RL.dto.request.RegisterRequest;
import com.RL.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/authors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> createAuthor(@Valid @RequestBody AuthorDTO authorDTO){

     Author newAuthor  = authorService.createAuthor(authorDTO);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", newAuthor.getId().toString());
        map.put("name : ",newAuthor.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDTO>> getAll(){
        List<AuthorDTO> authors=authorService.getAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Map<String,String>>  findById(@PathVariable("id") Long id){
        AuthorDTO authorDTO= authorService.findById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", authorDTO.getId().toString());
        map.put("name : ",authorDTO.getName());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PatchMapping("/authors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody Author author){
        Author author1 = authorService.updateAuthor(id,author);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", author1.getId().toString());
        map.put("name : ",author1.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }
    @DeleteMapping("/authors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> deleteById(@PathVariable("id") Long id){
       Author author= authorService.deleteById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", author.getId().toString());
        map.put("name : ",author.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

}