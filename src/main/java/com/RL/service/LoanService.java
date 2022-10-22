package com.RL.service;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.dto.LoanDTO;
import com.RL.dto.request.UpdateLoanDTO;
import com.RL.dto.response.LoanResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Page<LoanDTO> findAllLoansByUserId(Long userId,Pageable pageable){
        User user=userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,userId)) );

        return loanRepository.findAllByUserId(userId,pageable);

    }

    //4. method
    public Page<LoanDTO> getLoanedBookByBookId(Long bookId,Pageable pageable) {
        Book book=bookRepository.findById(bookId).orElseThrow(()->
                new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE,bookId)) );

        return loanRepository.findAllByBookId(book,pageable);

    }

    //5.method
    @Transactional
    public Loan getLoanById(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if(loan.isPresent()){
            return loan.get(); // findById methodu JPA'nın kendi methodu olduğu için
            // OOptional<Loan> dönüyor içinden Loan objesini çekmek
            // için get() kullandım.
        }else{
            throw new ResourceNotFoundException(String.format(LOAN_NOT_FOUND_MSG, loanId));
        }
    }

    public void updateLoan(Long loanId, UpdateLoanDTO updateLoanDTO) throws BadRequestException {
        Loan loan = loanRepository.findById(loanId).orElseThrow(()->
                new ResourceNotFoundException(String.format(LOAN_NOT_FOUND_MSG, loanId)));

        User user = loan.getUserId();
        Book book = loan.getBookId();

        if(updateLoanDTO.getReturnDate()!=null){
            book.setLoanable(true);
            loan.setReturnDate(updateLoanDTO.getReturnDate());
            loanRepository.save(loan);
            bookRepository.save(book);
            if(updateLoanDTO.getReturnDate().isEqual(loan.getReturnDate()) || updateLoanDTO.getReturnDate().isBefore(loan.getReturnDate())){
                user.setScore(user.getScore()+1);
                userRepository.save(user);
            }else{
                user.setScore(user.getScore()-1);
                userRepository.save(user);
            }
        }else{
            loan.setExpireDate(updateLoanDTO.getExpireDate());
            loan.setNotes(updateLoanDTO.getNotes());
            loanRepository.save(loan);
        }

        userRepository.save(user);
        bookRepository.save(book);
        loanRepository.save(loan);


    }


    //1.method
    @Transactional(readOnly=true)
    public Page<LoanDTO> findLoansWithPageByUserId(Long userId, Pageable pageable) {
        return  loanRepository.findAllWithPageByUserId(userId,pageable);

    }
    //2.method
    public Loan getByIdAndUserId(Long loanId, Long userId) {
        Loan loan = loanRepository.findByIdAndUserId(loanId, userId);
        return loan;
    }


}