package com.RL.dto.mapper;

import com.RL.domain.Loan;
import com.RL.dto.request.CreateLoanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "bookId", ignore = true)
    Loan createLoanDTOToLoan(CreateLoanDTO createLoanDTO);
}
