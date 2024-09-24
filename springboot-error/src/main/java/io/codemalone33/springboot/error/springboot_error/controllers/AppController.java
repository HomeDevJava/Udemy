package io.codemalone33.springboot.error.springboot_error.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AppController {

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

}
