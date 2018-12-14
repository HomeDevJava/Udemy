package com.cursospring.app.controllers;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.entity.Factura;
import com.cursospring.app.models.entity.ItemFactura;
import com.cursospring.app.models.entity.Producto;
import com.cursospring.app.models.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable Long clienteId, Model m, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD!!!");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		m.addAttribute("factura", factura);
		m.addAttribute("titulo", "Crear Factura");
		return "factura/form";
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, RedirectAttributes flash, Model m) {
		
		Factura factura= clienteService.findFacturaById(id);
		
		if(factura==null) {
			flash.addFlashAttribute("error", "La factura no existe en la BD");
			return "redirect:/listar";
		}
		
		m.addAttribute("factura", factura);
		m.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
		return "factura/ver";
		
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProducto(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model m,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Long[] cantidad, RedirectAttributes flash,
			SessionStatus status) {
		
		if(result.hasErrors()) {
			m.addAttribute("titulo", "Crear Factura");
			return "factura/form";
		}
		
		if(itemId==null || itemId.length==0) {
			m.addAttribute("titulo", "Crear Factura");
			m.addAttribute("error", "Error: la factura  debe contener al menos 1 linea de producto");
			return "factura/form";
		}
		
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);

			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);

			factura.addItemFactura(linea);

			log.info("ID: " + itemId[i] + ", cantidad: " + cantidad[i]);

		}

		clienteService.saveFactura(factura);
		status.setComplete();// se cierra el sessionAtribute

		flash.addFlashAttribute("success", "Factura creada con exito");
		return "redirect:/ver/" + factura.getCliente().getId();

	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		Factura factura= clienteService.findFacturaById(id);
		if(factura!=null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "Factura eliminada con exito");
			return "redirect:/ver/"+factura.getCliente().getId();
		}
		
		flash.addFlashAttribute("error", "Factura NO existe en la BD, no se pudo eliminar");
		return "redirect:/listar";
	}

}
