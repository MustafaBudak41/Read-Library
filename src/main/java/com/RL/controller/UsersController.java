package com.RL.controller;

import com.RL.domain.User;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.service.IUserService;
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

@RestController
@RequestMapping
@AllArgsConstructor
public class UsersController {

    private IUserService userService;
        //TODO DateTimeFormatter oldPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //DateTimeFormatter newPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //
        //LocalDateTime datetime = LocalDateTime.parse(input, oldPattern);
        //String output = datetime.format(newPattern);
    @PostMapping("/users")
    public  ResponseEntity<Map<String,String>> saveUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        User newUser= userService.saveUser(createUserRequest);

        Map<String,String> map=new HashMap<>();
        map.put("id : ", newUser.getId().toString());
        map.put("name : ",newUser.getFirstName());

        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }



}
