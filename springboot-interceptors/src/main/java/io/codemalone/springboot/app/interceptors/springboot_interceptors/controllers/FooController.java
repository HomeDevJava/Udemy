package io.codemalone.springboot.app.interceptors.springboot_interceptors.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class FooController {

    @GetMapping("/bar")
    public Map<String, String> bar() {
        return Collections.singletonMap("message", "Message sending from FooController");
    }
    @GetMapping("/tar")
    public Map<String, String> tar() {
        return Collections.singletonMap("message", "Message sending from FooController");
    }
    @GetMapping("/zar")
    public Map<String, String> zar() {
        return Collections.singletonMap("message", "Message sending from FooController");
    }
    

}
