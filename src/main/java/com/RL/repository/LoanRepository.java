package com.RL.repository;

import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query("SELECT l from Loan l " +
            "where l.expireDate<l.returnDate and l.userId=?1")
    List<Loan>findExpiredLoansBy(Long userId);

    List<Loan> findAllByUserId(User userId);

    List<Loan> findAllByBookId(Book bookId);
}