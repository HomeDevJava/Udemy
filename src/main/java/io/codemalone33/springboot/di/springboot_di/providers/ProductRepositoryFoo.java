package io.codemalone33.springboot.di.springboot_di.providers;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import io.codemalone33.springboot.di.springboot_di.models.Product;

/*Cuando se tienen mas de 2 Repositorys, se recomienda usar @Primary para establecer como default*/

@Primary
@Repository
public class ProductRepositoryFoo implements ProductoRepository {


    @Override
    public List<Product> findAll() {
       return Collections.singletonList(new Product(1L, "Memoria Corsair 32GB",659L));
    }

    @Override
    public Product findById(Long id) {
       return new Product(1L, "Memoria Corsair 32GB",659L);
    }

}
