package com.RL.controller;


import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class LoanController {
    public LoanService loanService;

    @PostMapping("/loans")
    public ResponseEntity<Map<String,Boolean>> createLoan(HttpServletRequest request,

                                                          @RequestParam(value = "bookId") Book bookId,
                                                          @Valid @RequestBody Loan loan) throws Exception {

        Long userId=(Long)request.getAttribute("id");
        loanService.createLoan(loan,userId,bookId);

        Map<String,Boolean>map=new HashMap<>();
        map.put("Loan created", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }


}