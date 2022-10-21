package com.RL.service;

import com.RL.domain.Book;
import com.RL.dto.BookDTO;
import com.RL.dto.BookUpdateDTO;
import com.RL.dto.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {
    Book createBook (BookDTO bookDTO);
    Book deleteBook (Long id);
    Book findBookById(Long id);

    Book updateBook(Long id, BookUpdateDTO bookUpdateDTO);

    Page<BookResponse> findAllWithPage(Pageable pageable);
}