package com.cursospring.app.models.dao;

import java.util.List;

import com.cursospring.app.models.entity.Cliente;

public interface IClienteDao {

	public List<Cliente> findAll();
}
