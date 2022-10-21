package com.RL.repository;

import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.dto.BookDTO;
import com.RL.dto.LoanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

    //6.method
    @Query("SELECT l from Loan l " +
            "where l.expireDate<current_date and  l.returnDate is null and l.userId=?1")
    List<Loan>findExpiredLoansBy(Long userId);

    //4.method
    Page<LoanDTO> findAllByBookId(Book bookId,Pageable pageable);

    // 1. method
    @Query("SELECT l from Loan l " +
            "where l.userId.id=?1")
    Page<LoanDTO> findAllWithPageByUserId(Long userId, Pageable pageable);


    //2.method
    @Query("SELECT l from Loan l WHERE l.id = ?1 and l.userId.id = ?2")
    Loan findByIdAndUserId(Long loanId, Long userId);

    // 3.method
    @Query("SELECT l from Loan l " +
            "where l.userId.id=?1")
    Page<LoanDTO> findAllByUserId(Long userId, Pageable pageable);

    // muzaffer beyden gelen
    boolean existsByUserId(User user);

    // yahya beyden gelen
    boolean existsByBookId(Book book);

    @Query(value = "SELECT l.bookId.id,l.bookId.name, l.bookId.isbn, count(l.id) as number from Loan l " +
            "group by l.bookId order by number desc limit ?amount", nativeQuery = true)
    Page<BookDTO> findMostPopularLimitByAmount( int amount, Pageable pageable);


    @Query("SELECT l from Loan l " +
            "where l.returnDate is null and l.userId=?1")
    List<Loan> findActiveLoansOfUser(Long userId);

    List<Loan> findLoanByReturnDateIsNull();

    List<Loan> findAllByUserId(User userId);

    List<Loan> findAllByBookId(Book bookId);


}
