package com.spring.boot.service;

import com.spring.boot.dao.UserDAO;
import com.spring.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
    Реализация Service
 */

@Service
@Transactional
public class UserService implements ServiceI<User> {
    /*
        Внедряем слой DAO, в котором spring-data-jpa реализовал все за нас
        Также внедряем passwordEncoder для добавления и модификации пароля в бд
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserDAO dao;

    @Autowired
    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        dao.deleteById(id);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.saveAndFlush(user);
    }

    /*
        Метод findById возвращает Optional<User>
        Transactional(readOnly = true) указывает, что метод не нуждается в транзакции, он просто возвращает значение.
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<User> get(long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User get(String field) {
        return dao.findByLogin(field);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return dao.findAll();
    }
}
