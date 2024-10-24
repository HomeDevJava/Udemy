package io.codemalone.springboot.calendar.interceptor.springboot_horario.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class AppController {

    @GetMapping("/")
    public ResponseEntity<?> foo(HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "Bienvenidos alsistema de atencion de clientes");
        body.put("time", new Date());
        body.put("message", request.getAttribute("message"));
        return ResponseEntity.ok(body);
    }
}
