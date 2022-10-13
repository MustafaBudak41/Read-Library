package com.RL.service;


import com.RL.domain.Author;

import java.util.List;


public interface AuthorService {

    void createAuthor(Author author);

    List<Author> getAll();

    Author findById(Long id);

}
