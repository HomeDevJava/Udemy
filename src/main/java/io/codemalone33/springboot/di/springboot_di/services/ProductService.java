package io.codemalone33.springboot.di.springboot_di.services;

import java.util.List;
import java.util.stream.Collectors;

import io.codemalone33.springboot.di.springboot_di.models.Product;
import io.codemalone33.springboot.di.springboot_di.providers.ProductRepository;

public class ProductService {

    private ProductRepository productRepository= new ProductRepository();

    public List<Product> findAll() {
        return productRepository.findAll().stream().map(p ->{
            Double priceImp= p.getPrice() * 1.21;
            p.setPrice(priceImp.longValue());
            return p;
        }).collect(Collectors.toList());
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }
}
