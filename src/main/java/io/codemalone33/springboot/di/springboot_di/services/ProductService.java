package io.codemalone33.springboot.di.springboot_di.services;

import java.util.List;

import io.codemalone33.springboot.di.springboot_di.models.Product;

public interface ProductService {

    List<Product> findAll();

    Product findById(Long id);

}
