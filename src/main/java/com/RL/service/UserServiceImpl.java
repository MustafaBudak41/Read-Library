package com.RL.service;
import java.util.function.Function;
import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.UserDTO;
import com.RL.dto.mapper.UserMapper;
import com.RL.dto.request.CreateUserRequest;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.request.SignInRequest;
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
    private LoanRepository loanRepository;

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


    //    @Transactional(readOnly = true)
    public Page<PageResponse> getUserLoanPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        Page<PageResponse> dtoPage = users.map(new Function<User, PageResponse>() {

            @Override
            public PageResponse apply(User user) {
                return userMapper.userToPageResponse(user);
            }
        });

        return dtoPage;
    }
    public Page<RLResponse> getUsersPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        Page<RLResponse> dtoPage = users.map(new Function<User, RLResponse>() {

            @Override
            public RLResponse apply(User user) {
                return userMapper.userToRLResponse(user);
            }
        });

        return dtoPage;

    }
    /**
     * @param id PK for user
     * for the user that has related records in
     * loans table, delete operation is
     * not permitted
     * @return deleted userDTO
     */
    @Override
    public UserDTO deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        boolean exists = loanRepository.existsByUserId(user);
        if(exists) {
            throw new BadRequestException(ErrorMessage.USER_USED_BY_LOAN_MESSAGE);
        }

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

       userRepository.deleteById(id);

        return userMapper.userToUserDTO(user);



    }







    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        return userMapper.userToUserDTO(user);
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


}