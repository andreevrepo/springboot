package com.spring.boot.controller;

import com.spring.boot.model.Role;
import com.spring.boot.model.User;
import com.spring.boot.service.ServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//Контроллер Spring
@Controller
//Контроллер слушает запрос /admin/**
@RequestMapping("/admin")
public class AdminController {

    private ServiceI<User> userService;
    private ServiceI<Role> roleService;

    @Autowired
    public AdminController(ServiceI<User> userService, ServiceI<Role> roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //@GetMapping - означает, что будет слушаться запрос /admin GET методом
    @GetMapping
    public String adminPanel(Model model) {

        //Закладываем в модель List<Users>, а view по ключу "users" будет отображать данные
        model.addAttribute("users", userService.getAll());

        //Возвращаем имя view, в данном случае admin.html.
        return "/admin";
        //Spring boot автоматически маппит view в директории /resources/templates
    }

    //params="add" означает, что метод будет слушать GET запрос /admin?add
    @GetMapping(params = "add")
    public String addFromForm(Model model){
        //Закладываем в модель роли для отображения во view
        model.addAttribute("roles", roleService.getAll());
        //Закладываем в модель объект класса User для заполнения во View.
        //Ключ по умолчанию будет именем класса с первой буквой в нижнем регистре - user
        model.addAttribute(new User());
        return "/add";
    }

    /*
        @PostMapping(params = "add") означает, что слушаться будет POST запрос /admin?add
        В данном случае, этот метод будет вызываться после отправки формы
        Т.к в форме не указан параметр action="", при отправке формы будет вызван тот же запрос /admin?add
        Но уже POST запросом
    */
    @PostMapping(params = "add")
    public String add(User user){

        //Достаем роль из базы данных по имени роли (Во View мы заложили имя роли в User поле roleName
        Role role = roleService.get(user.getRoleName());
        //Тут мы сеттим уже настоящую роль с id и именем
        user.setRole(role);
        //Добавляем пользователя в базу данных
        userService.add(user);
        //"redirect: означает, что будет перенаправление на запрос /admin, а возврат view admin.html
        return "redirect:/admin";
    }

    /*
        @GetMapping("/update") - слушает запрос GET - /admin/update
        @RequestParam - автоматически внедряет значение в переменную long id из запроса с параметром id
        - "/admin/update?id=2" внедрит в переменную id литерал 2
     */
    @GetMapping("/update")
    public String updateForm(@RequestParam long id, Model model) {
        //Получаем пользователя по идентификатору, т.к Сервис возвращает Optional<User>, нужно вызвать get класса Optional
        User user = userService.get(id).get();
        //Закладываем в модель полученный класс
        model.addAttribute("user", user);
        //Закладываем роли в модель
        model.addAttribute("roles", roleService.getAll());
        //Возвращаем view update.html для редактирования
        return "/update";
    }

    /*
        После редактирования User, форма выполняет Post запрос с заполненным объектом User (Заполняется с помощью Thymeleaf)
        Форма method="POST" без настройки action совершает запрос по тому же url, но только POST запросом
     */
    @PostMapping("/update")
    public String update(User user){
        //Извлекается из базы данных Роль по имени роли
        Role role = roleService.get(user.getRoleName());
        //Сетим роль
        user.setRole(role);
        //Обновляем User
        userService.update(user);
        //редирект на admin
        return "redirect:/admin";
    }

    /*
        Запрос на /admin/delete
        берется параметр id из реквеста
     */
    @GetMapping("/delete")
    public String delete(@RequestParam long id){
        //Удаление пользователя по id
        userService.delete(id);
        return "redirect:/admin";
    }
}
