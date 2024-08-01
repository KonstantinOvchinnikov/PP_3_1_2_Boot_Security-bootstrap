package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userService;

    @Autowired
    public AdminController(UserServiceImp userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "id", defaultValue = "0") long id) {
        if (id == 0) {
            model.addAttribute("list", userService.showAllUsers());
        } else {
            model.addAttribute("list", userService.showUserById(id));
        }
        return "/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.showUserById(id));
        return "/update";
    }

    @PostMapping("/update")
    public String userupdate(@ModelAttribute("user") User user, @RequestParam("id") long id) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/show")
    public String show(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.showUserById(id));
        return "/show";
    }
}
