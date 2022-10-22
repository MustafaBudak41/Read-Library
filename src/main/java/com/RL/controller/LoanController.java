package com.RL.controller;



import com.RL.domain.Loan;

import com.RL.dto.LoanDTO;

import com.RL.dto.request.UpdateLoanDTO;
import com.RL.dto.response.LoanResponse;
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
    public ResponseEntity<Page<LoanDTO>> findAllLoansByUserId( @PathVariable Long userId,
                                                               @RequestParam ("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("loanDate") LocalDateTime loanDate){
        Pageable pageable= PageRequest.of(page,size, Sort.by(String.valueOf(loanDate)).descending());

        Page<LoanDTO> loans = loanService.findAllLoansByUserId(userId,pageable);
        return new ResponseEntity<>(loans, HttpStatus.OK);

    }

    //4. method
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/loans/book/{bookId}")
    public ResponseEntity<Page<LoanDTO>> findLoanedBookByBookId(@PathVariable Long bookId,
                                                                @RequestParam ("page") int page,
                                                                @RequestParam("size") int size,
                                                                @RequestParam("loanDate") LocalDateTime loanDate) {

        Pageable pageable= PageRequest.of(page,size, Sort.by(String.valueOf(loanDate)).descending());
        Page<LoanDTO> loans = loanService.getLoanedBookByBookId(bookId,pageable);

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
    public ResponseEntity<Map<String, Boolean>> updateLoan(@PathVariable(value = "id") Long loanId,
                                                           @Valid @RequestBody UpdateLoanDTO updateLoanDTO
    ) throws Exception {
        loanService.updateLoan(loanId, updateLoanDTO);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //1. method
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/loans")
    public ResponseEntity<Page<LoanDTO>>getLoansWithPageByUserId(HttpServletRequest request,
                                                                 @RequestParam ("page") int page,
                                                                 @RequestParam("size") int size,
                                                                 @RequestParam("loanDate") LocalDateTime loanDate){

        Long userId=(Long)request.getAttribute("id");

        Pageable pageable= PageRequest.of(page,size, Sort.by(String.valueOf(loanDate)).descending());
        Page<LoanDTO>loans=loanService.findLoansWithPageByUserId(userId,pageable);
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