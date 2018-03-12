package com.spring.boot.controller;

import com.spring.boot.model.Role;
import com.spring.boot.model.User;
import com.spring.boot.service.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * By AndrewPC on 11.03.2018, in 23:49
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ServiceI<User> userService;
    private ServiceI<Role> roleService;

    @Autowired
    public AdminController(ServiceI<User> userService, ServiceI<Role> roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.getAll());
        return "/admin";
    }

    @GetMapping(params = "add")
    public String addFromForm(Model model){
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("user", new User());
        return "/add";
    }

    @PostMapping(params = "add")
    public String add(User user){
        Role role = roleService.get(user.getRoleName());
        user.setRole(role);
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam long id, Model model) {
        User user = userService.get(id).get();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAll());
        return "/update";
    }

    //TODO заполнять роль в view
    @PostMapping("/update")
    public String update(User user){
        Role role = roleService.get(user.getRoleName());
        user.setRole(role);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam long id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
