package io.codemalone33.springboot.di.springboot_di.providers;

import java.util.List;

import io.codemalone33.springboot.di.springboot_di.models.Product;

public interface ProductoRepository {

    List<Product> findAll();

    Product findById(Long id);

}
