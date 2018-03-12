package com.spring.boot.service;

import com.spring.boot.dao.UserDAO;
import com.spring.boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * By AndrewPC on 11.03.2018, in 23:42
 */

@Service
@Transactional
public class UserService implements ServiceI<User> {

    private UserDAO dao;

    @Autowired
    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void add(User user) {
        dao.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        dao.deleteById(id);
    }

    @Override
    public void update(User user) {
        dao.saveAndFlush(user);
    }

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
