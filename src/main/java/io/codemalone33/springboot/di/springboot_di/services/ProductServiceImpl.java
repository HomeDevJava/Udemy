package io.codemalone33.springboot.di.springboot_di.services;

import java.util.List;
import java.util.stream.Collectors;

import io.codemalone33.springboot.di.springboot_di.models.Product;
import io.codemalone33.springboot.di.springboot_di.providers.ProductRepositoryImpl;

public class ProductServiceImpl  implements ProductService {

    private ProductRepositoryImpl productRepository= new ProductRepositoryImpl();

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream().map(p ->{
            Double priceTax= p.getPrice() * 1.21;
            //p.setPrice(priceTax.longValue());
            
            Product newProd=(Product) p.clone();
            newProd.setPrice(priceTax.longValue());
            //se regresa un nuevo objeto para que no se modifique el objeto original
            //return new Product(p.getId(), p.getName(), priceTax.longValue());
            return newProd ;
        }).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }
}
