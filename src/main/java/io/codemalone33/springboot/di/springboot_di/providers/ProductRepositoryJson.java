package io.codemalone33.springboot.di.springboot_di.providers;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.codemalone33.springboot.di.springboot_di.models.Product;

public class ProductRepositoryJson implements ProductoRepository {

    private List<Product> data;

    public ProductRepositoryJson() {

        ClassPathResource resource = new ClassPathResource("json/products.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            data = Arrays.asList(mapper.readValue(resource.getFile(), Product[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> findAll() {
        return data;
    }

    @Override
    public Product findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
