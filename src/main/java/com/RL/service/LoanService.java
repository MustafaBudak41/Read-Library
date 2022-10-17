package com.RL.service;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.repository.BookRepository;
import com.RL.repository.LoanRepository;
import com.RL.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.RL.exception.message.ErrorMessage.BOOK_NOT_FOUND_MESSAGE;
import static com.RL.exception.message.ErrorMessage.USER_NOT_FOUND_MESSAGE;


@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public final static String LOAN_NOT_FOUND_MSG="Loan with id %d not found";



    public void createLoan(Loan loan, Long userId, Book bookId) throws BadRequestException {

        User user=userRepository.findById(userId).orElseThrow(()->

                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,  userId)));


        List<Loan> expiredLoans=loanRepository.findExpiredLoansBy(userId);

            if(expiredLoans.size()>0)

                throw new IllegalStateException("You do not have a permission to loan books");


            if(bookId.isLoanable()){

                bookId.setLoanable(false);
            }else
                throw new BadRequestException("The book is not available");

        loan.setUserId(user);
        loan.setBookId(bookId);
        loanRepository.save(loan);

    }

    public List<Loan> findAllLoansByUserId(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,userId)) );

        return loanRepository.findAllByUserId(user);

    }


    public List<Loan> getLoanedBookByBookId(Long bookId) {
        Book book=bookRepository.findById(bookId).orElseThrow(()->
               new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE,bookId)) );

        return loanRepository.findAllByBookId(book);

    }

    public void updateLoan(Long loanId, LocalDateTime expireDate, LocalDateTime returnDate, String notes) throws BadRequestException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()->
                new ResourceNotFoundException(String.format(LOAN_NOT_FOUND_MSG, loanId)));

        User user = loan.getUserId();

        Book book = loan.getBookId();


        if(returnDate!=null){
            book.setLoanable(true);
            loan.setReturnDate(returnDate);
            if(returnDate.isEqual(loan.getReturnDate()) || returnDate.isBefore(loan.getReturnDate())){
                user.setScore(user.getScore()+1);
            }else{
                user.setScore(user.getScore()-1);
            }
        }else{
            loan.setExpireDate(expireDate);
            loan.setNotes(notes);
        }

        userRepository.save(user);
        bookRepository.save(book);
        loanRepository.save(loan);


    }
}