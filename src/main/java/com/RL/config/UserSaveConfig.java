package com.RL.config;

import com.RL.domain.User;
import com.RL.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
public class UserSaveConfig {

//    @Bean
//    CommandLineRunner commandLineRunner1(UserRepository userRepository) {
//
//        return args -> {
//
//            User aliCan = new User( "aslan", "kral",
//                    0,
//                    "karsiyaka",
//                    "555-555-5555",
//                    LocalDate.of(2002, Month.JANUARY, 21),
//                    "ac@gmail.com",
//                    "12345",
//                    LocalDateTime.now(),
//                  "54321",
//                   false);
//
//
//
//           userRepository.saveAll(List.of(aliCan ));
//        };
//
//    }
}
