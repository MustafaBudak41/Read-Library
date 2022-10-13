package com.RL.repository;



import com.RL.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {


   // @Query("select l from Loan l " +
   //         "left join fetch l.user u " +
   //         "left join fetch u.id uid " +
   //         "where l.expireDate<?2 and uid=?1")
   // List<Loan> findExpiredLoansFindByUserId(Long id,LocalDateTime time);
    @Query("SELECT l from Loan l " +
            "where l.expireDate<current_date")
    List<Loan>findExpiredLoansBy();
}