package com.RL.service;

import com.RL.domain.Author;
import com.RL.domain.Book;
import com.RL.domain.Category;
import com.RL.domain.Publisher;
import com.RL.dto.BookDTO;

public interface IBookService {

    Book createBook (BookDTO bookDTO);
}