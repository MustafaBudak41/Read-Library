package com.RL.controller;
import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.UpdateRequest;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import com.RL.exception.message.ErrorMessage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class UsersController {

    private IUserService userService;

    @GetMapping("/user/loans")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<UserDTO>> getAllUserLoansByPage(@RequestParam("page") int page,
                                                                    @RequestParam("size") int size,
                                                                    @RequestParam("sort") String prop,
                                                                    @RequestParam("type") Direction type){

        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page<UserDTO> userDTOPage=userService.getUserLoanPage(pageable);
        return ResponseEntity.ok(userDTOPage);

    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or  hasRole('EMPLOYEE')")
    public ResponseEntity<Page<RLResponse>> getAllUsersByPage(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("type") Direction type){
        Pageable pageable= PageRequest.of(page, size, Sort.by(type, prop));
        Page<RLResponse> userDTOPage=userService.getUsersPage(pageable);

        return ResponseEntity.ok(userDTOPage);
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('EMPLOYEE')")
    public ResponseEntity<RLResponse> getUserById(@PathVariable Long id){
        UserDTO userDTO= userService.findById(id);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setFirstName(userDTO.getFirstName());

        return ResponseEntity.ok(response);

    }
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or  hasRole('EMPLOYEE')")
    public  ResponseEntity<Map<String,String>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        User newUser= userService.saveUser(createUserRequest);

        Map<String,String> map=new HashMap<>();
        map.put("id : ", newUser.getId().toString());
        map.put("name : ",newUser.getFirstName());

        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RLResponse> updateUsersByAdmin(@PathVariable Long id, @Valid @RequestBody
            UpdateRequest userUpdateRequest){

        User userUpdated= userService.updateUserByAdmin(id,userUpdateRequest);
        userService.updateUserByAdmin(id,userUpdateRequest);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setFirstName(userUpdated.getFirstName());

        return ResponseEntity.ok(response);

    }
    @PutMapping("/user/{id}")//TODO ayri yapildi tek yapilabilir mi
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Map<String,String>> updateUserByEmployee(@PathVariable Long id, @Valid @RequestBody
            UpdateRequest userUpdateRequest){

        User userUpdated=  userService.memberUserUpdate(id,userUpdateRequest);
        Map<String,String> map=new HashMap<>();

        if (userUpdateRequest.getFirstName()!=userUpdated.getFirstName()){

            map.put("id : ", id.toString());
            String str= ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE;
            map.put("error : ",str);
        }else{
            map.put("id : ", userUpdated.getId().toString());
            map.put("name : ",userUpdated.getFirstName());
        }
        return new ResponseEntity<>(map,HttpStatus.OK);
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('EMPLOYEE')")
    public ResponseEntity<RLResponse> getAuthenticatedUser(HttpServletRequest request){
        Long id= (Long) request.getAttribute("id");
        UserDTO userDTO= userService.findById(id);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setFirstName(userDTO.getFirstName());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RLResponse> deleteUser(@PathVariable Long id){

        UserDTO userDTO=  userService.deleteUser(id);

        RLResponse response=new RLResponse();
        response.setId(id);
        response.setFirstName(userDTO.getFirstName());

        return ResponseEntity.ok(response);

    }

//    @PutMapping("/userss{id}")
//    @PreAuthorize("hasRole('ADMIN') or  hasRole('EMPLOYEE')")
//    public ResponseEntity<RLResponse> getAuthenticatedUserUpdate(@PathVariable Long userId,
//                                                                 HttpServletRequest request,
//                                                                 @Valid @RequestBody
//                                                                 UpdateRequest updateRequest){
//        Long id= (Long) request.getAttribute("id");
//        User user= userService.adminAndEmployeeCanUpdate(userId,request,updateRequest);
//
//        RLResponse response=new RLResponse();
//        response.setId(userId);
//        response.setFirstName(user.getFirstName());
//
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/users/all")//extra yazildi
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<UserDTO>> getAllUsers(){
//        List<UserDTO> users=userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
}
