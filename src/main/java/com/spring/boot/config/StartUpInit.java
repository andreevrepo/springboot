package com.spring.boot.config;

import com.spring.boot.model.Role;
import com.spring.boot.model.User;
import com.spring.boot.service.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * By AndrewPC on 12.03.2018, in 7:33
 */
@Component
public class StartUpInit {

    private ServiceI<User> userService;
    private ServiceI<Role> roleService;

    @Autowired
    public StartUpInit(ServiceI<User> userService, ServiceI<Role> roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init(){
        User user = new User("admin","admin", "admin");
        Role roleAdmin = new Role("admin");
        Role roleUser = new Role("user");
        user.setRole(roleAdmin);
        roleService.add(roleAdmin);
        roleService.add(roleUser);
        userService.add(user);
    }
}
