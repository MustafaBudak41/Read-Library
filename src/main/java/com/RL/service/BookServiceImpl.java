package com.RL.service;

import com.RL.domain.Book;
import com.RL.dto.BookDTO;
import com.RL.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements IBookService {



        private BookRepository bookRepository;
        @Override
        public void createBook(BookDTO bookDTO) {
            Book book = new Book();
            book.setName(book.getName());
            book.setIsbn(bookDTO.getIsbn());
            book.setPageCount(bookDTO.getPageCount());
            book.setPublishDate(bookDTO.getPublishDate());
            book.setShelfCode(bookDTO.getShelfCode());
            book.setAuthorId(bookDTO.getAuthorId());
            book.setPublisherId(bookDTO.getPublisherId());
            book.setCategoryId(book.getCategoryId());
            bookRepository.save(book);
        }


}


