package com.RL.repository;

import com.RL.domain.Role;
import com.RL.domain.User;
import com.RL.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

  //  Long countMember( User role);



}