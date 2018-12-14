package com.cursospring.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.cursospring.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
}
