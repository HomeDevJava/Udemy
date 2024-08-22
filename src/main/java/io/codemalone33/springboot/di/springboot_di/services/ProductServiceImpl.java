package io.codemalone33.springboot.di.springboot_di.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.codemalone33.springboot.di.springboot_di.models.Product;
import io.codemalone33.springboot.di.springboot_di.providers.ProductoRepository;

@Service
public class ProductServiceImpl  implements ProductService {

    @Autowired
    @Qualifier("productRepositoryJson")
    private ProductoRepository productRepository;

    @Value("${config.product.price.tax}")
    private Double tax;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream().map(p ->{
            Double priceTax= p.getPrice() * tax;
            /* p.setPrice(priceTax.longValue());
            return p; */
            Product newProd=(Product) p.clone();
            newProd.setPrice(priceTax.longValue());
            return newProd ;
           
            //se regresa un nuevo objeto para que no se modifique el objeto original
            //return new Product(p.getId(), p.getName(), priceTax.longValue());
        }).collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }
}
