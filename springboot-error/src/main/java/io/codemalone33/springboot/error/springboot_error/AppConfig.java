package io.codemalone33.springboot.error.springboot_error;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.codemalone33.springboot.error.springboot_error.models.User;

@Configuration
public class AppConfig {

    @Bean
    List<User> users() {
        List<User> users = List.of(
            new User(1L, "John", "JGonzalez@correo.com"),
            new User(2L, "Jane", "JRamirez@correo.com"),
            new User(3L, "Joe", "JSantos@correo.com"),
            new User(4L, "Sandra", "SLopez@correo.com"),
            new User(5L, "Mario", "MMendez@correo.com"));

        return users;
    }

}
