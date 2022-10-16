package com.RL.service;

import com.RL.domain.Author;
import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.AuthorDTO;
import com.RL.dto.mapper.AuthorMapper;
import com.RL.dto.request.RegisterRequest;
import com.RL.exception.BadRequestException;
import com.RL.exception.ConflictException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.AuthorRepository;
import com.RL.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthorService {

    private AuthorRepository repository;
    private BookRepository bookRepository;
    private AuthorMapper authorMapper;

    public Author createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        repository.save(author);
        return author;
    }

    @Transactional(readOnly = true)
    public List<AuthorDTO> getAll() {
        List<Author> authorList = repository.findAll();
        return authorMapper.map(authorList);
    }

    @Transactional(readOnly = true)
    public AuthorDTO findById(Long id) {
        Author author = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return authorMapper.authorToAuthorDTO(author);
    }


//    public UserDTO updateUser(Long id, UserUpdateRequest request) {
//        boolean existEmail= userRepository.existsByEmail(request.getEmail());
//
//        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND_MESSAGE));
//
//        if(user.getBuiltIn()){
//            throw new BadRequestException("Not permitted");
//        }
//
//        if (existEmail && !request.getEmail().equals(user.getEmail())){
//            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,user.getEmail()));
//        }
//
//        User updateUser = userMapper.updateUserBySelf(request);
//        updateUser.setCreateDate(user.getCreateDate());
//        updateUser.setBuiltIn(user.getBuiltIn());
//        updateUser.setScore(user.getScore());
//        updateUser.setPassword(user.getPassword());
//        updateUser.setId(user.getId());
//        updateUser.setRoles(user.getRoles());
//
//        userRepository.save(updateUser);
//        return userMapper.userToUserDTO(updateUser);
//    }


    @Transactional
   public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author foundAuthor = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        if(foundAuthor.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Author author = authorMapper.authorDTOToAuthor(authorDTO);

        author.setId(foundAuthor.getId());
        author.setName(authorDTO.getName());

        repository.save(author);
       return authorMapper.authorToAuthorDTO(author);

    }

}
