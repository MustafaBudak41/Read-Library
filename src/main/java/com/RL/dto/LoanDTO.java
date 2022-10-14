package com.RL.dto;




import com.RL.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoanDTO {

    private Long id;

    @NotNull(message="Please provide load date")
    private LocalDateTime loanDate;

    @NotNull(message="Please provide expire date")
    private LocalDateTime expireDate;

    @NotNull(message="Please provide return")
    private LocalDateTime returnDate;

    @Size(min = 10,max = 200, message = "Message must be between ${min} and ${max} chars long")
    private String notes;

    private UserDTO userId;

    private BookDTO bookId;

    public LoanDTO(Loan loan){
        this.id=loan.getId();
        this.loanDate=loan.getLoanDate();
        this.expireDate=loan.getExpireDate();
        this.returnDate=loan.getReturnDate();
        this.notes=loan.getNotes();
        //this.userId= new UserDTO(loan.getUserId());  after userDTO creating
        //this.bookId=new BookDTO(loan.getBookId());
    }
}
