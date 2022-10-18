package com.RL.controller;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import com.RL.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
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

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('EMPLOYEE')")
    public ResponseEntity<RLResponse> getAuthenticatedUser(HttpServletRequest request){
        Long id= (Long) request.getAttribute("id");
        UserDTO userDTO= userService.findById(id);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setName(userDTO.getFirstName());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or  hasRole('EMPLOYEE')")				//@PathVariable ile almak mamtikli dehil
    public ResponseEntity<Page<RLResponse>> getAllUsersByPage(@RequestParam("page") int page,//kac sayfa gelcek
                                                       @RequestParam("size") int size,// bir sayfada kac tane olcak
                                                       @RequestParam("sort") String prop, //hangi attribute e yada properties e gore sort edicem
                                                       @RequestParam("type") Direction type){//direction ne olcak
        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page<RLResponse> userDTOPage=userService.getUsersPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('EMPLOYEE')")
    public ResponseEntity<RLResponse> getUserByIdAdmin(@PathVariable Long id){
        UserDTO userDTO= userService.findById(id);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setName(userDTO.getFirstName());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/users/loans")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<PageResponse>> getAllUserLoansByPage(@RequestParam("page") int page,//kac sayfa gelcek
        @RequestParam("size") int size,// bir sayfada kac tane olcak
        @RequestParam("sort") String prop, //hangi attribute e yada properties e gore sort edicem
        @RequestParam("type") Direction type){//direction ne olcak

            Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
            Page<PageResponse> userDTOPage=userService.getUserLoanPage(pageable);
            return ResponseEntity.ok(userDTOPage);

    }

    /*
    An admin can update any type of user,
while an employee can update only
member type users.
     */
//    @PutMapping("/users/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<RLResponse> updateUserByAdmin(@PathVariable Long id, @Valid @RequestBody
//            RLResponse userUpdateRequest){
//
//        userService.updateUser(id,userUpdateRequest);
//
//
//
//        RLResponse response=new RLResponse();
//        response.setId(id);
//        response.setName(userDTO.getFirstName());
//
//        return ResponseEntity.ok(response);
//
//    }
    /*
    while an employee can update only
member type users.
     */
//    @PutMapping("/users/{id}")
//    @PreAuthorize("hasRole('EMPLOYEE')")
//    public ResponseEntity<RLResponse> updateUserByAdmin(@PathVariable Long id, @Valid @RequestBody
//            RLResponse userUpdateRequest){
//
//        userService.updateUser(id,userUpdateRequest);
//
//
//
//        RLResponse response=new RLResponse();
//        response.setId(id);
//        response.setName(userDTO.getFirstName());
//
//        return ResponseEntity.ok(response);
//
//    }








    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RLResponse> deleteUser(@PathVariable Long id){

        UserDTO userDTO=  userService.deleteUser(id);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setName(userDTO.getFirstName());

        return ResponseEntity.ok(response);

    }
}
