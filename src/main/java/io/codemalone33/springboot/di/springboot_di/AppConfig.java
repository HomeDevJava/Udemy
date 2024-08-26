package io.codemalone33.springboot.di.springboot_di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import io.codemalone33.springboot.di.springboot_di.providers.ProductRepositoryJson;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {



    @Value("classpath:json/product.json")
    private Resource resource;

    @Bean("productRepositoryJson")
    public ProductRepositoryJson productRepositoryJson() {
        return new ProductRepositoryJson(resource);
    }
    

}
