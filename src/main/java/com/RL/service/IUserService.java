package com.RL.service;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserService {


    List<User> getAll();
    List<User> findUsers(String lastName);
    User findUser(Long id) throws ResourceNotFoundException;
    User saveUser(CreateUserRequest createUserRequest);
    User register(RegisterRequest registerRequest);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);



}
