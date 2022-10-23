package com.RL.dto.response;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor

public class LoanResponseBookUser {

    public Long id;
    public User userId;
    public Book bookId;


    public LoanResponseBookUser(Loan loan){
        this.id=loan.getId();
        this.userId=loan.getUserId();
        this.bookId=loan.getBookId();

    }

}









