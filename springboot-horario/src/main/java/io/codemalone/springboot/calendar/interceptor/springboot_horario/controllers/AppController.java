package io.codemalone.springboot.calendar.interceptor.springboot_horario.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    public ResponseEntity<?> foo() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "Bienvenidos alsistema de atencion de clientes");
        body.put("time", new Date());
        return ResponseEntity.ok(body);
    }
}
