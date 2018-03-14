package com.spring.boot.dao;

import com.spring.boot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * @Repository - показывает Spring, что интерфейс является Репозиторием
 * JpaRepository помогает избежать шаблонного кода Data Access Object
 * Для реализации мы должны указать сущность и тип первичного ключа
 * При автоматическом внедрении будут реализованы CRUD методы за нас
 */
@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    /*
        Мы можем объявлять абстрактные методы, которые также будут реализованы spring-data-jpa
        findBy + имя поля будет реализовывать SELECT * From Role where Имя_поля =
     */
    Role findByRole(String role);
}
