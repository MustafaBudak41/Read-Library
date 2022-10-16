package com.RL.service;

import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.UserDTO;
import com.RL.dto.mapper.UserMapper;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.request.SignInRequest;
import com.RL.exception.ConflictException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.RoleRepository;
import com.RL.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }

      // mapper ile yapildi
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        String resetPassword=registerRequest.getLastName();

        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,
                                RoleType.ROLE_MEMBER.name())));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

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

      //  User user = userMapper.registerRequestToUser(registerRequest);

        userRepository.save(user);
        return user;
    }




    @Override
    //@Transactional(noRollbackFor = SomeException)
    public User saveUser(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, createUserRequest.getEmail()));
        }

         String resetPassword=createUserRequest.getPassword().replace(createUserRequest.getPassword(), createUserRequest.getLastName());


        User user = userMapper.createUserRequestToUser(createUserRequest);
        user.setResetPasswordCode(resetPassword);//mapper da bunu null geldigi icin ugrasmadim

        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAll() {


        return null;
    }

    @Override
    public List<User> findUsers(String lastName) {
        return null;
    }

    @Override
    public User findUser(Long id) throws ResourceNotFoundException {
        return null;
    }


    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public void deleteUser(Long id) {

    }
}