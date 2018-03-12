package com.spring.boot.controller;

import com.spring.boot.model.User;
import com.spring.boot.service.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * By AndrewPC on 12.03.2018, in 11:24
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private ServiceI<User> userService;

    @Autowired
    public UserController(ServiceI<User> userService) {
        this.userService = userService;
    }

    @GetMapping
    public String user(Model model){
        model.addAttribute("users", userService.getAll());
        return "/user";
    }
}
