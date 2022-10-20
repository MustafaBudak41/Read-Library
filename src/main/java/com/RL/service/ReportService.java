package com.RL.service;

import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.UserDTO;
import com.RL.dto.mapper.UserMapper;
import com.RL.dto.response.ReportResponse;
import com.RL.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.RL.domain.enums.RoleType.ROLE_MEMBER;

@Service
@AllArgsConstructor
public class ReportService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private CategoryRepository categoryRepository;
    private LoanRepository loanRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public ReportResponse getReportAboutAllData() {

        ReportResponse report =new ReportResponse();

        report.setBooks(bookRepository.count());
        report.setAuthors(authorRepository.count());
        report.setPublishers(publisherRepository.count());
        report.setCategories(categoryRepository.count());
        report.setLoans(loanRepository.count());// dusun
        report.setUnReturnedBooks(1111L);// emanette ki kitaplar
        report.setExpiredBooks(1111L);  //suresi gecen kitaplar
        report.setMembers(roleRepository.countOfMember());

        return report;
    }
}
