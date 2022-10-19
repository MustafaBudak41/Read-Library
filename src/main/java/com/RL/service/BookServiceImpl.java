package com.RL.service;

import com.RL.domain.Author;
import com.RL.domain.Book;
import com.RL.domain.Category;
import com.RL.domain.Publisher;
import com.RL.dto.BookDTO;
import com.RL.dto.mapper.BookMapper;
import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BookServiceImpl implements IBookService {



        private BookRepository bookRepository;
        private AuthorRepository authorRepository;
        private CategoryRepository categoryRepository;
        private PublisherRepository publisherRepository;
        private LoanRepository loanRepository;
        private BookMapper bookMapper;

        @Override
        public Book createBook(BookDTO bookDTO)  {

            Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(()-> new
                    ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookDTO.getAuthorId())));

            Category category = categoryRepository.findById(bookDTO.getCategoryId()).orElseThrow(()-> new
                    ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookDTO.getCategoryId())));

            Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId()).orElseThrow(()-> new
                    ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookDTO.getPublisherId())));

            Book book = bookMapper.bookDTOToBook(bookDTO);

            book.setAuthorId(author);
            book.setCategoryId(category);
            book.setPublisherId(publisher);


            bookRepository.save(book);

            return book;
        }

//    @Override
//    public Book deleteBook(Long id) {
//        Book book = bookRepository.findById(id).orElseThrow(()-> new
//                ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));
//
//        boolean exists = loanRepository.existsByBookId(book);
//
//        if (exists){
//            throw new BadRequestException(ErrorMessage.BOOK_LOANED_OUT);
//        }
//        bookRepository.deleteById(id);
//
//        return  book;
//    }

    @Override
    public Book findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        return book;
    }


}


