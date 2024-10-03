package io.codemalone33.springboot.error.springboot_error.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.codemalone33.springboot.error.springboot_error.models.User;
import io.codemalone33.springboot.error.springboot_error.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class AppController {
    @Autowired
    private UserService userService;

    @GetMapping("/app")
    public String index() {
        int value = 1 / 0;
        System.out.println(value);
        return "ok 200";
    }

    @GetMapping("/parsear")
    public String parsear() {
        int value = Integer.parseInt("10x");
        System.out.println(value);
        return "ok 200 - parseando/convirtiendo a entero";
    }

    @GetMapping("/listar")
    public List<User> listarAll() {
        return userService.findAll();
    }

    @GetMapping("/ver/{id}")
    public User findByIdUser(@PathVariable(name = "id") Long idLong) {
        User user = userService.findById(idLong);
        System.out.println(user.getEmail());
        return user;
    }
    
    

}
