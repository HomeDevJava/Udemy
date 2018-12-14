package com.cursospring.app.models.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cursospring.app.models.dao.IClienteDao;
import com.cursospring.app.models.dao.IFacturaDao;
import com.cursospring.app.models.dao.IProductoDao;
import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.entity.Factura;
import com.cursospring.app.models.entity.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired private IClienteDao clienteDao;
	@Autowired private IProductoDao productoDao;
	@Autowired private IFacturaDao facturaDao;

	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);		
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable p) {
		return clienteDao.findAll(p);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findByNombre(String term) {		
		//return productoDao.findByNombre(term);
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);		
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long id) {
				return facturaDao.findById(id).orElse(null);
	}

}