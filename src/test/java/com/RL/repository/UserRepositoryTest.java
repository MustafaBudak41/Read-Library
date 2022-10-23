package com.RL.repository;

import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@SpringBootTest
public class UserRepositoryTest {

        @Autowired
    UserRepository userRepository;
   // @Test

    public void saveUserTest(){
        List<User> userList=new ArrayList<>();

        User user=new User();
       // user.setId(1L);
        user.setFirstName("victor");
        user.setLastName("nice");
        user.setScore(0);
        user.setAddress("www. street");
        user.setPhone("(541) 317-8828");
        user.setBirthDate(Date.valueOf("1990-10-25"));
        user.setEmail("maaam@mail.com");
        user.setPassword("1211ad13da");
        user.setCreateDate(LocalDateTime.now());
        user.setResetPasswordCode("dadad5454");
      //  user.setBuiltIn(false);
       // user.setRoles(RoleType.ROLE_ADMIN);


       // userList.add(user);
       // userRepository.save(user);
//        assertNotNull(userList);
//        when(userRepository.saveUser(user)).thenReturn(userList);
        verify(userRepository,times(1)).save(user);

        userRepository.save(user);

        verify(userRepository).save(user);

       // assertEquals("victor",  userRepository.findAll().get(0).getFirstName());
      //  assertEquals("victor",  user.getFirstName());

    }
}
