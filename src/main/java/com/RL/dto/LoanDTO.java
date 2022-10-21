package com.RL.dto;




import com.RL.domain.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private Long id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message="Please provide load date")
    private LocalDateTime loanDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message="Please provide expire date")
    private LocalDateTime expireDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    private LocalDateTime returnDate;

    @Size(min = 10,max = 200, message = "Message must be between ${min} and ${max} chars long")
    private String notes;

    private Long userId;

    private Long bookId;

    public LoanDTO(Loan loan){
        this.id=loan.getId();
        this.loanDate=loan.getLoanDate();
        this.expireDate=loan.getExpireDate();
        this.returnDate=loan.getReturnDate();
        this.notes=loan.getNotes();
        this.userId= loan.getUserId().getId();
        this.bookId= loan.getBookId().getId();
    }
}
