package com.spring.boot.service;

import com.spring.boot.dao.RoleDAO;
import com.spring.boot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * By AndrewPC on 11.03.2018, in 23:43
 */

@Service
@Transactional
public class RoleService implements ServiceI<Role> {

    private RoleDAO dao;

    @Autowired
    public RoleService(RoleDAO dao) {
        this.dao = dao;
    }

    @Override
    public void add(Role role) {
        dao.saveAndFlush(role);
    }

    @Override
    public void delete(long id) {
        dao.deleteById(id);
    }

    @Override
    public void update(Role role) {
        dao.saveAndFlush(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> get(long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Role get(String field) {
        return dao.findByRole(field);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAll() {
        return dao.findAll();
    }
}
