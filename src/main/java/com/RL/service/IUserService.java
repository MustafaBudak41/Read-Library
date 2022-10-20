package com.RL.service;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.request.UpdateRequest;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import com.RL.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserService {


    List<UserDTO> getAllUsers();
    User saveUser(CreateUserRequest createUserRequest);
    User register(RegisterRequest registerRequest);
    User updateUserByAdmin(Long id, UpdateRequest updateRequest);
    User memberUserUpdate(Long id, UpdateRequest updateRequest);
  //  User adminAndEmployeeCanUpdate(Long id, HttpServletRequest request, UpdateRequest updateRequest);
    UserDTO deleteUser(Long id);
    UserDTO findById(Long id);
    Page<UserDTO> getUserLoanPage(Pageable pageable);
    Page<RLResponse> getUsersPage(Pageable pageable);



}
