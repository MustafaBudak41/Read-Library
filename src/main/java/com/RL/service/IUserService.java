package com.RL.service;

import com.RL.domain.User;
import com.RL.dto.UserDTO;
import com.RL.dto.request.RegisterRequest;
import com.RL.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserService {


    List<User> getAll();
    List<User> findStudents(String lastName);
    User findStudent(Long id) throws ResourceNotFoundException;
    void createUser(UserDTO userDTO);
    User saveUser(RegisterRequest registerRequest);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);


}
