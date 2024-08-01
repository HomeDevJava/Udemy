package io.codemalone33.springboot.di.springboot_di.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import io.codemalone33.springboot.di.springboot_di.models.Product;
import io.codemalone33.springboot.di.springboot_di.services.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class SomeController {

    private ProductService productService= new ProductService();

    @GetMapping("/")
    public List<Product> lista() {
        return new ProductService().findAll();
    }
    
    @GetMapping("/{id}")
    public Product show(@PathVariable Long id) {
        return productService.findById(id);
    }

}
