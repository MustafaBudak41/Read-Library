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
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

    @Query("SELECT l from Loan l " +
            "where l.expireDate<?2 and  l.returnDate is null and l.userId.id=?1")
    List<Loan>findExpiredLoansBy(Long userId, LocalDateTime current);

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


    @Query(value = "SELECT  u.first_name, l.user_id ,count(l.user_id) as number from tbl_loan l\n" +
            "    inner join tbl_user u on l.user_id=u.id\n" +
            "    group by l.user_id, u.first_name  order by number desc", nativeQuery = true)
    Page findMostBorrowers(Pageable pageable);



    @Query(value = "SELECT  u.name, u.id, u.isbn  from tbl_loan l \n" +
            "inner join tbl_book u on l.book_id=u.id\n" +
            "group by l.book_id, u.name,u.id, u.isbn  order by  count(l.book_id) desc", nativeQuery = true)
    Page findMostPopularBooks(Pageable pageable);


    List<Loan>findLoansByUserIdAndExpireDateIsNull(User userId);

    List<Loan> findLoanByReturnDateIsNull();

    List<Loan> findAllByUserId(User userId);

    List<Loan> findAllByBookId(Book bookId);


}
