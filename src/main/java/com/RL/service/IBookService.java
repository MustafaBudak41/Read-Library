package com.RL.service;

import com.RL.domain.Book;
import com.RL.dto.BookDTO;
import com.RL.dto.BookUpdateDTO;
import com.RL.dto.response.BookResponse;
import com.RL.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {
    Book createBook(BookDTO bookDTO);

    Book deleteBook(Long id);

    Book findBookById(Long id);

    Book updateBook(Long id, BookUpdateDTO bookUpdateDTO);

    Page findAllWithPageForMemberQuery(String name, Long cat, Long author, Long publisher, Pageable pageable) throws BadRequestException;

    Page findAllWithPageForAdminQuery(String name, Long cat, Long author, Long publisher, Pageable pageable)
            throws BadRequestException;
}