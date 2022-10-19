package com.RL.service;

import com.RL.domain.Author;
import com.RL.dto.AuthorDTO;
import com.RL.dto.mapper.AuthorMapper;
import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.AuthorRepository;
import com.RL.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public List<AuthorDTO> getAll() {
        List<Author> authorList = repository.findAll();
        return authorMapper.map(authorList);
    }

    public AuthorDTO findById(Long id) {
        Author author = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return authorMapper.authorToAuthorDTO(author);
    }

    public Author updateAuthor(Long id, Author author) {
        Author foundAuthor = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        if(foundAuthor.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        foundAuthor.setId(id);
        foundAuthor.setName(author.getName());
        foundAuthor.setBuiltIn(author.getBuiltIn());
        repository.save(foundAuthor);
        return foundAuthor;
    }

    public Author deleteById(Long id) {
        Author author = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        if(!author.getBooks().isEmpty()) {
            throw  new ResourceNotFoundException("You cannot delete an author who has a book");
        }
        repository.deleteById(id);
        return author;
    }
}