package io.codemalone33.springboot.error.springboot_error.services;

import io.codemalone33.springboot.error.springboot_error.models.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);
    
}
