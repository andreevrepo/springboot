package com.spring.boot.dao;

import com.spring.boot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * By AndrewPC on 11.03.2018, in 23:39
 */
@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
