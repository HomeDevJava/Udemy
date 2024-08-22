package io.codemalone33.springboot.di.springboot_di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.codemalone33.springboot.di.springboot_di.providers.ProductRepositoryJson;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {


    @Bean("productRepositoryJson")
    public ProductRepositoryJson productRepositoryJson() {
        return new ProductRepositoryJson();
    }

}
