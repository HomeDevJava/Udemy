package com.bolsadeideas.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

@Repository
public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
