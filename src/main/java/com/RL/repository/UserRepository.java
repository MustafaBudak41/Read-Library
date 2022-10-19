package com.RL.repository;


import com.RL.domain.User;
import com.RL.dto.response.PageResponse;
import com.RL.dto.response.RLResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

//    @Query("SELECT new com.RL.dto.response.RLResponse(user)  FROM User user")
//    Page<RLResponse> findAllCarWithPage(Pageable pageable);

}