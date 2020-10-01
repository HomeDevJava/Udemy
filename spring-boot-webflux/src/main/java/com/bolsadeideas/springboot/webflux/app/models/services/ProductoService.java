package com.bolsadeideas.springboot.webflux.app.models.services;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
	
	public Flux<Producto> findAll();
	public Flux<Producto> findAllConNombreUppercase();
	public Flux<Producto> findAllConNombreUppercaseRepeat();
	public Mono<Producto> findById(String id);
	public Mono<Producto> save(Producto p);
	public Mono<Void> delete(Producto p);

}
