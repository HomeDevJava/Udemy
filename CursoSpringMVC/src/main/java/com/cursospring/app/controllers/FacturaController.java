package com.cursospring.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.entity.Factura;
import com.cursospring.app.models.entity.Producto;
import com.cursospring.app.models.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	
	@Autowired private IClienteService clienteService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable Long clienteId, Model m, RedirectAttributes flash) {
		
		Cliente cliente= clienteService.findOne(clienteId);
		if(cliente==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD!!!");
			return "redirect:/listar";
		}
		
		Factura factura= new Factura();
		factura.setCliente(cliente);
		
		m.addAttribute("factura", factura);
		m.addAttribute("titulo", "Crear Factura");
		return "factura/form";
	}
	
	@GetMapping(value="/cargar-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProducto(@PathVariable String term){
		return clienteService.findByNombre(term);
	}
	

}
