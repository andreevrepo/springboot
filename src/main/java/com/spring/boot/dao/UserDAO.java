package com.spring.boot.dao;

import com.spring.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * By AndrewPC on 11.03.2018, in 23:38
 */
@Repository
public interface UserDAO extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
