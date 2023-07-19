package com.main.asm.repository;

import com.main.asm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

//    Optional<Role> findByName(String name);

}
