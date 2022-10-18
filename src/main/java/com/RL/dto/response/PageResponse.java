package com.RL.dto.response;

import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

        Long id;
        User name;
        Book bookId;

}
