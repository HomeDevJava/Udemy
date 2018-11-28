package com.cursospring.app.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.service.IClienteService;
import com.cursospring.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(defaultValue="0") int page, Model m) {
		
		Pageable pageRequest= PageRequest.of(page, 10);
		
		Page<Cliente> clientes= clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender= new PageRender<Cliente>("/listar", clientes);
		
		m.addAttribute("titulo", "Listado de Clientes");
		m.addAttribute("lista", clientes);
		m.addAttribute("page", pageRender);
		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Formulario de cliente");
		m.addAttribute("cliente", new Cliente());
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model m, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		
		String msjFlash=(cliente.getId() != null)? "Cliente editado con exito" : "Cliente creado con Exito!";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", msjFlash);
		return "redirect:/listar";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El Id del cliente no existe en la BD");
				return "redirect:/listar";
			} else {
				m.addAttribute("titulo", "Editar Cliente");
				m.addAttribute("cliente", cliente);
				return "form";
			}
		} else {
			flash.addFlashAttribute("error", "El Id del cliente no puede ser 0");
			return "redirect:/listar";
		}
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		clienteService.delete(id);
		flash.addFlashAttribute("success", "CLiente Eliminado con éxito!");
		return "redirect:/listar";
	}

}