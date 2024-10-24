package com.cursospring.app.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.entity.Factura;
import com.cursospring.app.models.entity.Producto;

public interface IClienteService {

	public List<Cliente> findAll();	
	public Page<Cliente> findAll(Pageable p);
	public Cliente findOne(Long id);
	public void delete(Long id);	
	public void save(Cliente cliente);
	public Cliente fetchByIdWithFacturas(Long id);
	
	public Factura findFacturaById(Long id);
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
	public void deleteFactura(Long id);
	public void saveFactura(Factura factura);
	
	
	public Producto findProductoById(Long id);
	public List<Producto> findByNombre(String term);
}