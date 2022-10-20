package com.RL.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private Long books;
    private Long authors;
    private Long publishers;
    private Long categories;
    private Long loans;
    private Long unReturnedBooks;
    private Long expiredBooks;
    private Long members;


}
