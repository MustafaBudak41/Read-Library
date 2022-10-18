package com.RL.controller;



import com.RL.domain.Author;
import com.RL.domain.User;
import com.RL.dto.AuthorDTO;
import com.RL.dto.request.RegisterRequest;
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
@RequestMapping
@AllArgsConstructor
public class AuthorController {
    //..Mustafa Budak
    private final AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity<Map<String,String>> createAuthor(@Valid @RequestBody AuthorDTO authorDTO){

     Author newAuthor  = authorService.createAuthor(authorDTO);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", newAuthor.getId().toString());
        map.put("name : ",newAuthor.getName());
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }



    @GetMapping("/author")
    public ResponseEntity<List<AuthorDTO>> getAll(){
        List<AuthorDTO> author=authorService.getAll();
        return ResponseEntity.ok(author);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") Long id){
        AuthorDTO author= authorService.findById(id);
        return  ResponseEntity.ok(author);
    }



//    @PatchMapping
//    @PreAuthorize("hasRole('MEMBER')")
//    public ResponseEntity<UserDTO> updateUser(HttpServletRequest request, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
//        Long id = (Long) request.getAttribute("id");
//        UserDTO userDTO = userService.updateUser(id,userUpdateRequest);
//        return ResponseEntity.ok(userDTO);
//    }


    @PutMapping("/author/{id}")
    public ResponseEntity<Map<String,String>> updateAuthor(@RequestParam("id") Long id, @Valid @RequestBody AuthorDTO authorDTO){

        authorService.updateAuthor(id,authorDTO);
        Map<String,String> map=new HashMap<>();
        map.put("id : ", id.toString());
        map.put("name : ", authorDTO.getName());
        return new ResponseEntity<>(map,HttpStatus.OK);

    }


}