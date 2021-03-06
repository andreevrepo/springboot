package com.spring.boot.dao;

import com.spring.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * см RoleDAO
 */
@Repository
public interface UserDAO extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
