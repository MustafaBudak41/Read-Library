package com.RL.dto.response;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {

    public Long id;
    public Long userId;
    public Book bookId;

    public LoanResponse(Loan loan){
        this.id=loan.getId();
        this.userId=loan.getUserId().getId();
        this.bookId=loan.getBookId();
    }
}