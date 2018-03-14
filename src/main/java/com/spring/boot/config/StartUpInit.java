package com.spring.boot.config;

import com.spring.boot.model.Role;
import com.spring.boot.model.User;
import com.spring.boot.service.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * Метод init() будет вызываться после завершения настройки контекста Spring
 */
@Component
public class StartUpInit {

    //Сервис работы с юзером
    private ServiceI<User> userService;
    //Сервис работы с ролями
    private ServiceI<Role> roleService;

    //Автоматическое внедрение компонентов через конструктор, в данном случае внедрение будет по типу
    @Autowired
    public StartUpInit(ServiceI<User> userService, ServiceI<Role> roleService) {
        this.userService = userService; // Сюда внедрится UserService
        this.roleService = roleService; // Сюда внедрится RoleService
    }//В данном случае чтобы компоненты были внедрены, они были помечены аннотацией @Service

    /*
        В методе init() создаются роли и дефолтный юзер
     */
    @PostConstruct
    public void init(){
        User user = new User("admin","admin", "admin");
        Role roleAdmin = new Role("ADMIN");
        Role roleUser = new Role("USER");
        user.setRole(roleAdmin);
        roleService.add(roleAdmin);
        roleService.add(roleUser);
        userService.add(user);
    }
}
