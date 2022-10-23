package com.RL.service;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.dto.LoanDTO;
import com.RL.dto.request.UpdateLoanDTO;
import com.RL.dto.response.LoanResponse;
import com.RL.dto.response.LoanResponseBookUser;
import com.RL.dto.response.LoanUpdateResponse;
import com.RL.exception.BadRequestException;
import com.RL.exception.ResourceNotFoundException;
import com.RL.repository.BookRepository;
import com.RL.repository.LoanRepository;
import com.RL.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

import static com.RL.exception.message.ErrorMessage.*;


@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public LoanResponse createLoan(long bookId, Long userId, String notes) {

        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,userId)));
        Book book=bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE,bookId)));

        LocalDateTime current=LocalDateTime.now();
        List<Loan> expiredLoans=loanRepository.findExpiredLoansBy(userId,current);
        List<Loan> activeLoansOfUser = loanRepository.findLoansByUserIdAndExpireDateIsNull(user);

        if(!book.isLoanable()) throw new BadRequestException("book is not loanable");

        if(expiredLoans.size()>0) throw new IllegalStateException("You do not have a permission to loan");

        Loan loan=new Loan();


        switch (user.getScore()) {
            case 2:
                loan.setLoanDate(current);
                loan.setExpireDate(current.plusDays(20));

                break;
            case 1:
                if (activeLoansOfUser.size() < 4) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(15));
                }
                break;
            case 0:
                if (activeLoansOfUser.size() < 3) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(10));
                }
                break;
            case -1:
                if (activeLoansOfUser.size() < 2) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(6));
                }
                break;
            case -2:
                if (activeLoansOfUser.size() < 1) {
                    loan.setLoanDate(current);
                    loan.setExpireDate(current.plusDays(3));
                }
                break; default: throw new BadRequestException("The user score is not between -2 and +2, " +
                    "(from createLoan Method in the LoanService)");

        }


        loan.setBookId(book);
        loan.setUserId(user);
        loan.setNotes(notes);
        loanRepository.save(loan);
        book.setLoanable(false);
        bookRepository.save(book);
        LoanResponse loanResponse=new LoanResponse();
        loanResponse.setId(loan.getId());
        loanResponse.setUserId(loan.getUserId().getId());
        loanResponse.setBookId(loan.getBookId());
        return loanResponse;


    }


    //3. method
    @Transactional
    public List<LoanResponseBookUser> findAllLoansByUserId(Long userId, Pageable pageable) throws ResourceNotFoundException{
        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,userId)) );

        return loanRepository.findAllLoanByUser(userId,pageable);

    }

    //4. method
    public List<Loan> getLoanedBookByBookId(Long bookId, Pageable pageable) {
        Book book=bookRepository.findById(bookId).orElseThrow(()->
                new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE,bookId)) );

        return loanRepository.findAllByBookId(book,pageable);

    }

    //5.method
    @Transactional
    public Loan getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()->
                new BadRequestException(String.format(LOAN_NOT_FOUND_MSG, loanId)));

        return  loan;
        // findById methodu JPA'nın kendi methodu olduğu için
        // OOptional<Loan> dönüyor içinden Loan objesini çekmek
        // için get() kullandım.
    }

    public LoanUpdateResponse updateLoan(Long loanId, UpdateLoanDTO updateLoanDTO) throws BadRequestException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()->
                new ResourceNotFoundException(String.format(LOAN_NOT_FOUND_MSG, loanId)));

        User user = loan.getUserId();
        Book book = loan.getBookId();

        try {
            if(updateLoanDTO.getReturnDate()!=null){
                book.setLoanable(true);
                loan.setReturnDate(updateLoanDTO.getReturnDate());
                bookRepository.save(book);
                loanRepository.save(loan);
                if(updateLoanDTO.getReturnDate().isEqual(loan.getReturnDate()) || updateLoanDTO.getReturnDate().isBefore(loan.getReturnDate())){
                    user.setScore(user.getScore()+1);
                    userRepository.save(user);
                    return new LoanUpdateResponse(loan);
                }else{
                    user.setScore(user.getScore()-1);
                    userRepository.save(user);
                    return new LoanUpdateResponse(loan);
                }
            }else{
                loan.setExpireDate(updateLoanDTO.getExpireDate());
                loan.setNotes(updateLoanDTO.getNotes());
                bookRepository.save(book);
                userRepository.save(user);
                loanRepository.save(loan);
                return new LoanUpdateResponse(loan);

            }
        }catch(RuntimeException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }finally{
            return new LoanUpdateResponse(loan);
        }

//        return new LoanUpdateResponse(loan);
    }


    //1.method
    @Transactional(readOnly=true)
    public List<Loan> findLoansWithPageByUserId(Long userId, Pageable pageable) {
        return  loanRepository.findAllWithPageByUserId(userId,pageable);

    }
    //2.method
    public Loan getByIdAndUserId(Long loanId, Long userId) {
        Loan loan = loanRepository.findByIdAndUserId(loanId, userId);
        return loan;
    }


}