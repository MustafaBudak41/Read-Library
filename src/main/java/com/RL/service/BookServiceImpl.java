package com.RL.service;

import com.RL.domain.Author;
import com.RL.domain.Book;
import com.RL.domain.Category;
import com.RL.domain.Publisher;
import com.RL.dto.BookDTO;
import com.RL.dto.BookUpdateDTO;
import com.RL.dto.mapper.BookMapper;
import com.RL.dto.response.BookResponse;
import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

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
    public Book createBook(BookDTO bookDTO) {

        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookDTO.getAuthorId())));

        Category category = categoryRepository.findById(bookDTO.getCategoryId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookDTO.getCategoryId())));

        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookDTO.getPublisherId())));

        Book book = bookMapper.bookDTOToBook(bookDTO);

        book.setAuthorId(author);
        book.setCategoryId(category);
        book.setPublisherId(publisher);


        bookRepository.save(book);

        return book;
    }

    @Override
    public Book deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new
                ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        boolean exists = loanRepository.existsByBookId(book);

        if (exists){
            throw new BadRequestException(ErrorMessage.BOOK_LOANED_OUT);
        }
        bookRepository.deleteById(id);

        return  book;
    }

    @Override
    public Book findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        return book;
    }

    @Override
    public Book updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        Book foundBook = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, id)));

        if (foundBook.isBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Author author = authorRepository.findById(bookUpdateDTO.getAuthorId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, bookUpdateDTO.getAuthorId())));

        Category category = categoryRepository.findById(bookUpdateDTO.getCategoryId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, bookUpdateDTO.getCategoryId())));

        Publisher publisher = publisherRepository.findById(bookUpdateDTO.getPublisherId()).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, bookUpdateDTO.getPublisherId())));

        Book book = bookMapper.bookUpdateDTOToBook(bookUpdateDTO);
        book.setId(foundBook.getId());
        book.setAuthorId(author);
        book.setCategoryId(category);
        book.setPublisherId(publisher);

        bookRepository.save(book);
        return book;
    }



    EntityManager entityManager;

    public Page findAllWithPageForMemberQuery(String name, Long cat, Long author, Long publisher, Pageable pageable) throws BadRequestException {

        if (name == null && cat == null && author == null && publisher == null){

            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        } else{
            StringBuilder sb = new StringBuilder();
            sb.append("select b.id, b.name, b.isbn from Book b where");
            if(name != null){
                sb.append(" b.name= :name and");
            }
            if( cat != null){
                sb.append( " b.categoryId.id= :cat and" );
            }
            if(author != null){
                sb.append(" b.authorId.id= :author and");
            }
            if(publisher != null){
                sb.append(" b.publisherId.id= :publisher and");
            }
            sb.append(" b.active=true");
            Query query = entityManager.createQuery(sb.toString());
            if(name != null){
                query.setParameter("name", name);
            }
            if( cat != null){
                query.setParameter("cat", cat);
            }
            if(author != null){
                query.setParameter("author", author);
            }
            if(publisher != null){
                query.setParameter("publisher", publisher);
            }
            List books = new ArrayList<>();
            books = query.getResultList();

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), books.size());
            final Page<Book> page = new PageImpl<>(books.subList(start, end), pageable, books.size());

            return page;
        }


    }

    public Page findAllWithPageForAdminQuery(String name, Long cat, Long author, Long publisher, Pageable pageable) throws BadRequestException {

        if (name == null && cat == null && author == null && publisher == null){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        } else{
            StringBuilder sb = new StringBuilder();
            sb.append("select b.id, b.name, b.isbn from Book b where");
            if(name != null){
                sb.append(" b.name= :name and");
            }
            if( cat != null){
                sb.append( " b.categoryId.id= :cat and" );
            }
            if(author != null){
                sb.append(" b.authorId.id= :author and");
            }
            if(publisher != null){
                sb.append(" b.publisherId.id= :publisher and");
            }
            sb.append(" b.id is not null");
            Query query = entityManager.createQuery(sb.toString());
            if(name != null){
                query.setParameter("name", name);
            }
            if( cat != null){
                query.setParameter("cat", cat);
            }
            if(author != null){
                query.setParameter("author", author);
            }
            if(publisher != null){
                query.setParameter("publisher", publisher);
            }
            List books = new ArrayList<>();
            books = query.getResultList();

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), books.size());
            final Page<Book> page = new PageImpl<>(books.subList(start, end), pageable, books.size());

            return page;
        }
    }


}