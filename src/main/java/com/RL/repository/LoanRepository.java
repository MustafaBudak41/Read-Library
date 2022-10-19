package com.RL.repository;

import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.dto.LoanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query("SELECT l from Loan l " +
            "where l.expireDate<l.returnDate and l.userId=?1")
    List<Loan>findExpiredLoansBy(Long userId);



    List<Loan> findAllByBookId(Book bookId);

   // 1. method // Hocaya sorulacak
    Page<LoanDTO> findAllWithPageByUserId(Long userId, Pageable pageable);

    //2.method
    @Query("SELECT l from Loan l WHERE l.id = ?1 and l.userId = ?2")
    Loan findByIdAndUserId(Long loanId, Long userId);

    // 3.method // Hocaya Sorulacak
    Page<LoanDTO> findAllByUserId(Long userId, Pageable pageable);

    // muzaffer beyden gelen
    boolean existsByUserId(User user);

    // yahya beyden gelen
    boolean existsByBookId(Book book);

}