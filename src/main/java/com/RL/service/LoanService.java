package com.RL.service;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.exception.ResourceNotFoundException;
import com.RL.repository.BookRepository;
import com.RL.repository.LoanRepository;
import com.RL.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final static String USER_NOT_FOUND_MSG="User with Id %d not found";

    public void createLoan(Loan loan, Long userId, Book bookId) throws Exception {

        User user=userRepository.findById(userId).orElseThrow(()->

                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG,  userId)));


        List<Loan> expiredLoans=loanRepository.findExpiredLoansBy(userId);

            if(expiredLoans.size()>0)

                throw new IllegalStateException("You do not have a permission to loan books");


            if(bookId.isLoanable()){

                bookId.setLoanable(false);
            }else
                throw new Exception("The book is not available");

        loan.setUserId(user);
        loan.setBookId(bookId);
        loanRepository.save(loan);

    }






}