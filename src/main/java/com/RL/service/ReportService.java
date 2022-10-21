package com.RL.service;

import com.RL.domain.Book;
import com.RL.domain.Loan;
import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import com.RL.dto.UserDTO;
import com.RL.dto.mapper.BookMapper;
import com.RL.dto.mapper.UserMapper;
import com.RL.dto.response.BookResponse;
import com.RL.dto.response.ReportResponse;
import com.RL.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private BookMapper bookMapper;

    public ReportResponse getReportAboutAllData() {

        ReportResponse report = new ReportResponse();

        LocalDateTime now = LocalDateTime.now();
        report.setBooks(bookRepository.count());
        report.setAuthors(authorRepository.count());
        report.setPublishers(publisherRepository.count());
        report.setCategories(categoryRepository.count());
        report.setLoans(loanRepository.count());
        report.setUnReturnedBooks(loanRepository.findLoanByReturnDateIsNull().stream().count());
        report.setExpiredBooks(loanRepository.findLoanByReturnDateIsNull().
                stream().filter(t -> t.getExpireDate().isBefore(now)).count());
        report.setMembers(roleRepository.countOfMember());

        return report;
    }

    public Page<BookResponse> findReportsWithPage(Pageable pageable) {
        // Page<Book> books = bookRepository.findAll(pageable);

        Page<Book> books = bookRepository.findBookByLoanableIsFalse(pageable);

        Page<BookResponse>dtoPage =  books.map(book -> bookMapper.bookToBookResponse(book));
        return dtoPage;
    }


}
