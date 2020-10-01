package com.bolsadeideas.springboot.webflux.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.webflux.app.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired private ProductoDao productoDao;

	@Override
	public Flux<Producto> findAll() {		 
		return productoDao.findAll();
	}

	@Override
	public Mono<Producto> findById(String id) {		 
		return productoDao.findById(id);
	}

	@Override
	public Mono<Producto> save(Producto p) {		 
		return productoDao.save(p);
	}

	@Override
	public Mono<Void> delete(Producto p) {		 
		return productoDao.delete(p);
	}

	@Override
	public Flux<Producto> findAllConNombreUppercase() {
		return productoDao.findAll().map(p ->{
			p.setNombre(p.getNombre().toUpperCase());
			return p;
		});
		
	}

	@Override
	public Flux<Producto> findAllConNombreUppercaseRepeat() {
		return findAllConNombreUppercase().repeat(5000);
	}

}
