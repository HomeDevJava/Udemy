package io.codemalone33.springboot.di.springboot_di.providers;

import java.util.List;

import io.codemalone33.springboot.di.springboot_di.models.Product;

public class ProductRepository {

    List<Product> data;

    public ProductRepository() {
        this.data= List.of(
                new Product(1L, "Memoria Corsair 32GB", 659L),
                new Product(2L, "Cpu Intel Core i7 11200F", 2000L),
                new Product(3L, "MotherBoard MSI B450", 1500L),
                new Product(4L, "Gabinete Corsair", 400L),
                new Product(5L, "Placa de Video MSI", 1000L),
                new Product(6L, "Disco SSD 1TB", 800L)
        );
    }

    public List<Product> findAll() {
        return data;
    }

    public Product findById(Long id) {
        return data.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    

}
