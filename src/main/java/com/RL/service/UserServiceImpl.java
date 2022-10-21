package com.RL.service;

import java.util.*;
import java.util.function.Function;

import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.UserDTO;
import com.RL.dto.mapper.UserMapper;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.request.SignInRequest;
import com.RL.dto.request.UpdateRequest;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import com.RL.exception.BadRequestException;
import com.RL.exception.ConflictException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.LoanRepository;
import com.RL.repository.RoleRepository;
import com.RL.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private LoanRepository loanRepository;

    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        String resetPassword = registerRequest.getLastName();

        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,
                                RoleType.ROLE_MEMBER.name())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        role.setRoleCount(role.getRoleCount() + 1L);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setScore(0);
        user.setCreateDate(LocalDateTime.now());
        user.setResetPasswordCode(resetPassword);
        user.setBuiltIn(false);

        user.setRoles(roles);

        roleRepository.save(role);

        userRepository.save(user);
        return user;
    }

    @Override
    public User saveUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, createUserRequest.getEmail()));
        }

        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,
                                RoleType.ROLE_MEMBER.name())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        role.setRoleCount(role.getRoleCount() + 1L);

        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        String resetPassword = createUserRequest.getLastName();

        User user = userMapper.createUserRequestToUser(createUserRequest);
        user.setResetPasswordCode(resetPassword);

        user.setPassword(encodedPassword);
        roleRepository.save(role);
        userRepository.save(user);
        return user;
    }

    public Page<UserDTO> getUserLoanPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        Page<UserDTO> dtoPage = users.map(user -> userMapper.userToUserDTO(user));

        return dtoPage;
    }

    public Page<RLResponse> getUsersPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        Page<RLResponse> dtoPage = users.map(user -> userMapper.userToRLResponse(user));

        return dtoPage;

    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        return userMapper.userToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.map(users);
    }


    @Override
    public User updateUserByAdmin(Long id, UpdateRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        User userUpdated = userMapper.updateRequestToUser(updateRequest);
        userUpdated.setId(user.getId());
        userUpdated.setResetPasswordCode(updateRequest.getLastName());

        userRepository.save(userUpdated);

        return userUpdated;
    }

    @Override
    public User memberUserUpdate(Long id, UpdateRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }


        if (user.getRoles().toString().contains(RoleType.ROLE_MEMBER.name())) {
            User userUpdated = userMapper.updateRequestToUser(updateRequest);
            userUpdated.setId(user.getId());
            userUpdated.setResetPasswordCode(updateRequest.getLastName());

            userRepository.save(userUpdated);


        }
        return user;
    }


    /**
     * @param id PK for user
     *           for the user that has related records in
     *           loans table, delete operation is
     *           not permitted
     * @return deleted userDTO
     */
    @Override
    @Transactional
    public UserDTO deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        boolean exists = loanRepository.existsByUserId(user);
        if (exists) {
            throw new BadRequestException(ErrorMessage.USER_USED_BY_LOAN_MESSAGE);
        }

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }else{
            Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).
                    orElseThrow(() -> new ResourceNotFoundException(
                            String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,
                                    RoleType.ROLE_MEMBER.name())));


            role.setRoleCount(role.getRoleCount() - 1L);
            roleRepository.save(role);
        }


        userRepository.deleteById(id);
        return userMapper.userToUserDTO(user);

    }

}