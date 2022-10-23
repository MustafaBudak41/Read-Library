package com.RL.controller;



import com.RL.domain.Loan;

import com.RL.dto.LoanDTO;

import com.RL.dto.request.UpdateLoanDTO;
import com.RL.dto.response.LoanResponse;
import com.RL.dto.response.LoanResponseBookUser;
import com.RL.dto.response.LoanUpdateResponse;
import com.RL.service.LoanService;
import com.RL.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public UserServiceImpl userService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @PostMapping("/loans")
    public ResponseEntity <LoanResponse> createLoan(@RequestParam(value = "bookId") Long bookId,
                                                    @RequestParam(value = "userId") Long userId,
                                                    @RequestParam(value = "notes") String notes
    ){

        LoanResponse loansResponse= loanService.createLoan(bookId,userId,notes);
        return new ResponseEntity<>(loansResponse,HttpStatus.CREATED);
    }
    // 3. method
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<List<LoanResponseBookUser>> findAllLoansByUserId(@PathVariable Long userId,
                                                                           @RequestParam ("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam("sort") String prop,
                                                                           @RequestParam("direction") Sort.Direction direction){
        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        // Pageable pageable=PageRequest.of(page, size);

        List<LoanResponseBookUser> loans = loanService.findAllLoansByUserId(userId,pageable);
        return new ResponseEntity<>(loans, HttpStatus.OK);

    }

    //4. method
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/book/{bookId}")
    public ResponseEntity<List<Loan>> findLoanedBookByBookId(@PathVariable Long bookId,
                                                             @RequestParam ("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sort") String prop,
                                                             @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        List<Loan> loans = loanService.getLoanedBookByBookId(bookId,pageable);

        return new ResponseEntity<>(loans,HttpStatus.OK);

    }

    //5. method
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/auth/{id}")
    public ResponseEntity<Loan> getLoanWithId(@PathVariable(value="id") Long loanId){
        Loan loan = loanService.getLoanById(loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);

    }
    //7.method
    @PutMapping("/loans/{id}")
    @PreAuthorize("hasRole('ADMIN') or  hasRole('EMPLOYEE')")
    public ResponseEntity<LoanUpdateResponse> updateLoan(@PathVariable(value = "id") Long loanId,
                                                         @Valid @RequestBody UpdateLoanDTO updateLoanDTO
    ) {
        LoanUpdateResponse updatedLoanResponse =loanService.updateLoan(loanId, updateLoanDTO);
        return new ResponseEntity<>(updatedLoanResponse, HttpStatus.OK);
    }

    //1. method
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/loans")
    public ResponseEntity<List<Loan>>getLoansWithPageByUserId(HttpServletRequest request,
                                                              @RequestParam ("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("direction") Sort.Direction direction){

        Long userId=(Long)request.getAttribute("id");

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        List<Loan>loans=loanService.findLoansWithPageByUserId(userId,pageable);
        return new ResponseEntity<>(loans,HttpStatus.OK);

    }

    //2. method
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanById(HttpServletRequest request,
                                            @PathVariable(value="id") Long loanId){

        Long userId = (Long) request.getAttribute("id");
        Loan loan = loanService.getByIdAndUserId(loanId, userId);

        return new ResponseEntity<>(loan, HttpStatus.OK);
    }





}