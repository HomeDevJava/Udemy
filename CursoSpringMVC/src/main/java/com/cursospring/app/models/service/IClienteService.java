package com.cursospring.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cursospring.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable p);

	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);
}
