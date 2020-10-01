package com.bolsadeideas.springboot.webflux.app.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import com.bolsadeideas.springboot.webflux.app.models.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ProductoController {

	@Autowired
	private ProductoService service;
	private static final Logger log = LoggerFactory.getLogger(SpringBootApplication.class);

	@GetMapping({ "/listar", "/" })
	public String listar(Model model) {

		Flux<Producto> productos = service.findAllConNombreUppercase();

		productos.subscribe(prod -> log.info(prod.getNombre()));

		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "listar";
	}

	@GetMapping("/listar-datadriver")
	public String listarDataDriver(Model model) {

		Flux<Producto> productos = service.findAllConNombreUppercaseRepeat().delayElements(Duration.ofMillis(250));

		productos.subscribe(prod -> log.info(prod.getNombre()));

		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 1));
		model.addAttribute("titulo", "Listado de productos");
		return "listar";
	}

	@GetMapping("/listar-full")
	public String listaFull(Model model) {

		Flux<Producto> productos = service.findAllConNombreUppercaseRepeat();

		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "listar";
	}

	@GetMapping("/listar-chunked")
	public String listachunked(Model model) {

		Flux<Producto> productos = service.findAllConNombreUppercaseRepeat();

		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "listar-chunked";
	}

	@GetMapping("/form")
	public Mono<String> crear(Model m){
		m.addAttribute("producto", new Producto());
		m.addAttribute("titulo", "Form de Producto");
		return Mono.just("form");
	}
	
	@PostMapping("/form")
	public Mono<String> guardar(Producto producto){
		return service.save(producto).doOnNext(p ->{
			log.info("Producto guardado: " + p.getNombre() + " Id: " + p.getId());
		}).thenReturn("redirect:/listar");
	}

}
