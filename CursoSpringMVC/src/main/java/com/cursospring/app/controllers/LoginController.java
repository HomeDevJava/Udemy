package com.cursospring.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model m, Principal principal, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, RedirectAttributes flash) {

		if (principal != null) {
			flash.addFlashAttribute("info", "Ya ha iniciado sesión");
			return "redirect:/listar";
		}
		
		if(error!= null) {
			m.addAttribute("error", "Error en el login: Usuario o Password incorrecto!");
		}
		

		if(logout!= null) {
			m.addAttribute("success", "Se ha cerrado la sesión con Exito!");
		}

		return "login";

	}
}
