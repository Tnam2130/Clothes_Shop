package com.main.asm.repository;

import com.main.asm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

}
