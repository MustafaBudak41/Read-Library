package com.RL.dto;

import com.RL.domain.Author;
import com.RL.domain.Category;
import com.RL.domain.Publisher;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

        private Long id;
        @Size(min = 2, max = 80, message = "")
        @NotNull(message = "Please provide book name")
        private String name;
        @Size(min = 17, max = 17, message = "")
        @NotNull(message = "Please provide isbn code")
        @Pattern(regexp = "")
        private String isbn;

        private int pageCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
                "yyyy", timezone = "Turkey")
        private int publishDate;

        private File image;

        @Size(min = 6, max = 6, message = "")
        @NotNull(message = "Please provide shelf code")
        @Pattern(regexp = "")
        private String shelfCode;
        @NotNull(message = "Please provide book featured")
        private boolean featured=false;
        @NotNull(message = "Please provide book activity")
        private boolean active=true;
        private boolean loanable=true;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
                "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
        @NotNull(message = "Please provide book created date")
        private LocalDateTime createDate= LocalDateTime.now();
        private boolean builtIn=false;
        @NotNull(message = "PLease provide author for book")
        private Author authorId;
        @NotNull(message = "Please provide publisher for book")
        private Publisher publisherId;
        @NotNull(message = "Please provide category for book")
        private Category categoryId;
//    private List<Loan> loansList;

    }

