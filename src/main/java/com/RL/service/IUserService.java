package com.RL.service;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import com.RL.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {


    List<User> getAll();
    List<User> findUsers(String lastName);
    User findUser(Long id) throws ResourceNotFoundException;
    User saveUser(CreateUserRequest createUserRequest);
    User register(RegisterRequest registerRequest);
    void updateUser(UserDTO userDTO);
    UserDTO deleteUser(Long id);
    UserDTO findById(Long id);
    Page<PageResponse> getUserLoanPage(Pageable pageable);
    Page<RLResponse> getUsersPage(Pageable pageable);



}
