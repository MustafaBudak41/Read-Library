package com.RL.service;

import com.RL.domain.Author;
import com.RL.exception.ResourceNotFoundException;
import com.RL.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository repository;
    public void createAuthor(Author author){
        repository.save(author);
    }

    public List<Author> getAll(){
        return repository.findAll();
    }

    public Author findById(Long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Author not found"));
    }


    public void updateAuthor(Long id,Author author) {
        repository.deleteById(id);
        repository.save(author);
    };

}
