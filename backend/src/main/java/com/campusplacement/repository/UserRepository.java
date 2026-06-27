package com.campusplacement.repository;

import com.campusplacement.enums.Role;
import com.campusplacement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByRole(Role role);

    boolean existsByEmail(String email);

    List<User> findAllByRole(Role role);


    List<User> findAllByRoleAndIsActive(Role role, boolean isActive);
}