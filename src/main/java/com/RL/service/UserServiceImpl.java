package com.RL.service;

import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.UserDTO;
import com.RL.dto.mapper.UserMapper;
import com.RL.dto.request.RegisterRequest;
import com.RL.exception.ConflictException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.RoleRepository;
import com.RL.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;




      @Override
      //@Transactional(noRollbackFor = SomeException)
    public User saveUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }
        Role role = roleRepository.findByName(RoleType.ROLE_ANONYMOUS)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage
                        .ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_ANONYMOUS.name())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = userMapper.userDTOToUser(registerRequest);
        userRepository.save(user);
return user;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> findStudents(String lastName) {
        return null;
    }

    @Override
    public User findStudent(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void createUser(UserDTO userDTO) {

    }



    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public void deleteUser(Long id) {

    }
}