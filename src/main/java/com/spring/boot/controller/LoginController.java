package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * By AndrewPC on 12.03.2018, in 18:45
 */
@Controller
@RequestMapping("/")
public class LoginController {

    //возвращает форму
    @GetMapping
    public String login(){
        return "/login";
    }
}
