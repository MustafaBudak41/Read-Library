package com.RL.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoanDTO {


    @NotNull(message = "Please provide your userId ")
    private Long userId;

    @NotNull(message = "Please provide your bookId ")
    private Long bookId;

    @Size(max = 300)
    @NotNull(message = "Please provide your notes ")
    private String notes;
}
