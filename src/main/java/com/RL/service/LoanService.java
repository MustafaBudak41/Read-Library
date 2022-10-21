package com.RL.service;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.dto.BookDTO;
import com.RL.dto.LoanDTO;
import com.RL.dto.mapper.LoanMapper;
import com.RL.dto.request.CreateLoanDTO;
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
import java.util.stream.Stream;

import static com.RL.exception.message.ErrorMessage.BOOK_NOT_FOUND_MESSAGE;
import static com.RL.exception.message.ErrorMessage.USER_NOT_FOUND_MESSAGE;


@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private LoanMapper loanMapper;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public final static String LOAN_NOT_FOUND_MSG="Loan with id %d not found";



    public boolean createLoan(CreateLoanDTO createLoanDTO) throws BadRequestException {

        //  1) IF USER NOT EXİST THROW EXCEPTION AND FINISH.
        User user=userRepository.findById(createLoanDTO.getUserId()).orElseThrow(()->

                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,  createLoanDTO.getUserId())));
        Book book=bookRepository.findById(createLoanDTO.getUserId()).orElseThrow(()->

                new ResourceNotFoundException(String.format(BOOK_NOT_FOUND_MESSAGE,  createLoanDTO.getBookId())));

        //  2) GET THE SPECIFIC USER'S EXPIRED LOANS
        List<Loan> expiredLoans=loanRepository.findExpiredLoansBy(createLoanDTO.getUserId());
        //  3) GET THE SPECIFIC USER'S ACTIVE LOANS NOT THE RETURNED LOANS (SO THE NUMBER OF THE BOOK THAT THE USER HAVE CURRENTLY)
        List<Loan> activeLoansOfUser = loanRepository.findActiveLoansOfUser(createLoanDTO.getUserId());

        //  4) IF THE USER HAVE AN UNRETURNED BOOK THEN THROW EXCEPTION AND FINISH.
        if(expiredLoans.size()>0)

            throw new IllegalStateException("You do not have a permission to loan books because you have an unreturned book");

        //  5) IF THE BOOK IS NOT AVAILABLE THROW EXCEPTION AND FINISH
        if(!book.isLoanable()) {
            return book.isLoanable();
        }
        //  6) WHEN WE PASSED THE CONDITIONS ABOVE WE WILL CREATE A LOAN
      //  Loan loan = new Loan();
        Loan loan = loanMapper.createLoanDTOToLoan(createLoanDTO);

        //  7) AND WE WILL CREATE A LOAN DATE
        LocalDateTime loanDate = LocalDateTime.now();

        //  8) WE CHECKED THE USER SCORE HERE AND SET FOR HOW MANY DAYS HE/SHE CAN BORROW A BOOK
        switch (user.getScore()) {
            case 2:// if user's score is 2 and if he currently have books less than 5 he/she can borrow a new book for 20 days
                if (activeLoansOfUser.size() < 5) {
                    loan.setLoanDate(loanDate);
                    loan.setExpireDate(loanDate.plusDays(20));
                }
                break;
            case 1:
                if (activeLoansOfUser.size() < 4) {
                    loan.setLoanDate(loanDate);
                    loan.setExpireDate(loanDate.plusDays(15));
                }
                break;
            case 0:
                if (activeLoansOfUser.size() < 3) {
                    loan.setLoanDate(loanDate);
                    loan.setExpireDate(loanDate.plusDays(10));
                }
                break;
            case -1:
                if (activeLoansOfUser.size() < 2) {
                    loan.setLoanDate(loanDate);
                    loan.setExpireDate(loanDate.plusDays(6));
                }
                break;
            case -2:
                if (activeLoansOfUser.size() < 1) {
                    loan.setLoanDate(loanDate);
                    loan.setExpireDate(loanDate.plusDays(3));
                }
                break;
            default:
                throw new BadRequestException("The user score is not between -2 and +2, " +
                        "(from createLoan Method in the LoanService)");
        }

        //  9) WE CONTINUED TO SET THE LOAN'S FIELDS
        loan.setUserId(user);
        loan.setBookId(book);
        loan.setNotes(createLoanDTO.getNotes());
        //  10) AND WE SET THE BOOK IS NOT AVAILABLE FROM NOW ON;
        book.setLoanable(false);
        //  11) FINALLY WE SAVED THE LOAN TO THE DATABASE
        loanRepository.save(loan);
        return book.isLoanable();
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

    //7.method
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