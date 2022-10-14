package com.RL.repository;


import com.RL.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock//geercek olmayan repository uretip buraya veridirir
    UserRepository repository;

    @InjectMocks//service instance olusturu ve yukarida ki
    // mock lanan her objeyi bu classs a enjekte eder
    //UserService service;

    @Autowired
    User user;
 //   @Test
//    void testSaveUser() {
//        try {
//            when(repository.save(user)).
//                    thenReturn("Mockito");
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//
//        //List<String> actualList = service.createUser();
//
//        assertNotNull(actualList);
//        assertEquals(2, actualList.size());
//    }


}
