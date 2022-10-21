package com.RL.dto.request;

import com.RL.domain.Book;
import com.RL.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
