package com.bolsadeideas.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootApplication.class);
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	private ProductoDao dao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		Flux.just(
				new Producto("TV Panasonic", 456.89),
				new Producto("Sony Camara HD Digital", 177.89),
				new Producto("Apple iPod", 46.89),
				new Producto("Sony Notebook", 846.89),
				new Producto("Hewlett Packard Multifuncional", 200.89 ),
				new Producto("HP Notebook Omen", 2500.89),
				new Producto("Mica Comoda 5 Cajones", 150.89),
				new Producto("TV Sony Bravia OLED 4K Ultra HD", 2555.89)
				)
		.flatMap(producto -> {
			producto.setCreateat(new Date());
			return dao.save(producto);
			})
		.subscribe(producto -> log.info("insert: " + producto.getId()));
	}

}
