package io.codemalone.springboot.difactturas.springboot_difacturas;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import io.codemalone.springboot.difactturas.springboot_difacturas.models.Item;
import io.codemalone.springboot.difactturas.springboot_difacturas.models.Product;

@Configuration
@PropertySource("classpath:data.properties")
public class AppConfig {


    @Bean
    List<Item> iteminvoice(){
        Product p1= new Product("Laptop Lenovo L440", 1000.0);
        Product p2= new Product("Mouse Gears", 50.0);
        Product p3= new Product("Keyboard Logitech", 100.0);
        
        return List.of(new Item(p1, 1), new Item(p2, 2), new Item(p3, 1));
    }

    
    @Bean
    @Primary
    List<Item> iteminvoiceOffice(){
        Product p1= new Product("Impresora Epson L440", 700.0);
        Product p2= new Product("Escritorio GameStop", 850.0);
        Product p3= new Product("Silla xChair", 1100.0);
        
        return List.of(new Item(p1, 1), new Item(p2, 1), new Item(p3, 1));
    }

}
