package com.RL.controller;


import com.RL.exception.ResourceNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.RL.domain.Author;

import com.RL.dto.AuthorDTO;
import com.RL.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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

//    @GetMapping("/authors")
//    public ResponseEntity<List<AuthorDTO>> getAll(){
//        List<AuthorDTO> authors=authorService.getAll();
//        return ResponseEntity.ok(authors);
//    }

    //   http://localhost:8080/authors?size=10&page=0&sort=name&direction=ASC
    @GetMapping("/authors")
    public ResponseEntity<Page<AuthorDTO>> getAllUserByPage(@RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sort") String prop,
                                                            @RequestParam("direction") Direction direction){

        Pageable pageable=PageRequest.of(page, size, Sort.by(direction,prop));
        Page<AuthorDTO> userDTOPage=authorService.getUserPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Map<String,String>>  findById(@PathVariable("id") Long id){
        AuthorDTO authorDTO= authorService.findById(id);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", authorDTO.getId().toString());
        map.put("name : ",authorDTO.getName());
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @PutMapping("/authors/{id}")
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

    @GetMapping("/authors/download")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getUserReport(){
        String fileName="authors.xlsx";

        try {
            ByteArrayInputStream bais= authorService.getAuthorsReport();
            InputStreamResource file=new InputStreamResource(bais);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                    .contentType(MediaType.parseMediaType("application/vmd.ms-excel")).body(file);

        } catch (IOException e) {
            throw  new ResourceNotFoundException("bla bla bla bla");
        }
    }


}