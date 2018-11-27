package com.cursospring.app.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.service.IClienteService;


@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model m) {
		m.addAttribute("titulo", "Listado de Clientes");
		m.addAttribute("lista", clienteService.findAll());
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Model m) {
		m.addAttribute("titulo","Formulario de cliente");
		m.addAttribute("cliente", new Cliente());
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model m, SessionStatus status) {
		if(result.hasErrors()) {
			m.addAttribute("titulo","Formulario de cliente");
			return "form";
		}
		
		clienteService.save(cliente);
		status.setComplete();
		return "redirect:/listar";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable Long id, Model m) {
		Cliente cliente=null;
		if(id>0) {
			cliente= clienteService.findOne(id);
			m.addAttribute("titulo","Editar Cliente");
			m.addAttribute("cliente", cliente);
			return "form";
		}else {
			return "redirect:/listar";
		}
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		clienteService.delete(id);
		return "redirect:/listar";
	}
		
}