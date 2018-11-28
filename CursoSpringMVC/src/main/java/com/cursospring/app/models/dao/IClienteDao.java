package com.cursospring.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.cursospring.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}