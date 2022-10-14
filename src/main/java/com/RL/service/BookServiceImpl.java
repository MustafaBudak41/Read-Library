package com.RL.service;

import com.RL.domain.Author;
import com.RL.domain.Book;
import com.RL.domain.Category;
import com.RL.domain.Publisher;
import com.RL.dto.BookDTO;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.AuthorRepository;
import com.RL.repository.BookRepository;
import com.RL.repository.CategoryRepository;
import com.RL.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements IBookService {



        private BookRepository bookRepository;
        private AuthorRepository authorRepository;
        private CategoryRepository categoryRepository;
        private PublisherRepository publisherRepository;

        @Override
        public void createBook(BookDTO bookDTO) {

            Author author = authorRepository.findById(bookDTO.getAuthorId().getId()).orElseThrow(()-> new
                    ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookDTO.getAuthorId().getId())));

            Category category = categoryRepository.findById(bookDTO.getCategoryId().getId()).orElseThrow(()-> new
                    ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookDTO.getCategoryId().getId())));

            Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId().getId()).orElseThrow(()-> new
                    ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookDTO.getPublisherId().getId())));

            Book book = new Book();
            book.setName(bookDTO.getName());
            book.setIsbn(bookDTO.getIsbn());
            book.setPageCount(bookDTO.getPageCount());
            book.setPublishDate(bookDTO.getPublishDate());
            book.setShelfCode(bookDTO.getShelfCode());

            book.setAuthorId(author);
            book.setCategoryId(category);
            book.setPublisherId(publisher);

            bookRepository.save(book);
        }


}


