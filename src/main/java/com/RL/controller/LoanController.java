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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class LoanController {
    public LoanService loanService;

    @PostMapping("/loans")
    public ResponseEntity<Map<String, Boolean>> createLoan(@RequestParam(value="userId") Long userId,

                                                           @RequestParam(value = "bookId") Book bookId,
                                                           @Valid @RequestBody Loan loan) throws Exception {


        loanService.createLoan(loan, userId, bookId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Loan created", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<List<Loan>> findAllLoansByUserId(@PathVariable Long userId) {
        List<Loan> loans = loanService.findAllLoansByUserId(userId);
        return new ResponseEntity<>(loans, HttpStatus.OK);

    }

    @GetMapping("/loans/book/bookId")
    public ResponseEntity<List<Loan>> findLoanedBookByBookId(Long bookId) {

        List<Loan> loans = loanService.getLoanedBookByBookId(bookId);

        return new ResponseEntity<>(loans,HttpStatus.OK);

    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<Map<String, Boolean>> updateLoan(@RequestParam(value = "loanId") Long loanId,
                                                           @Valid @RequestBody
                                                                   LocalDateTime expireDate,
                                                           @Valid @RequestBody
                                                                   LocalDateTime returnDate,
                                                           @Valid @RequestBody String notes) throws Exception {
        loanService.updateLoan(loanId, expireDate, returnDate, notes);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }





}
