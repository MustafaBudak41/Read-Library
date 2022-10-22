package com.RL.dto.mapper;

import com.RL.domain.Loan;
import com.RL.domain.User;
import com.RL.dto.response.RLResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface MostBorrowers {

   // Map<User,Loan> mostBorrowerToRLResponse(Loan loan, User user);
}
