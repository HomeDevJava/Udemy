package io.codemalone33.springboot.error.springboot_error.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AppController {

@GetMapping("/app")
public String index(@RequestParam String param) {
    return "ok 200";
}

    
}
