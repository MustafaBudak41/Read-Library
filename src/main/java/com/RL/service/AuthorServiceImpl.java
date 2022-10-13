package com.RL.service;


import com.RL.domain.Author;
import com.RL.exception.ResourceNotFoundException;
import com.RL.repository.AuthorRepository;
import com.RL.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository repository;

    @Override
    public void createAuthor(Author author) {
        repository.save(author);
    }



    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Author not found"));
    }




}
