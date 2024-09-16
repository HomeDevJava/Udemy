package com.bolsadeideas.springboot.webflux.app.controllers;

import java.time.Duration;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import com.bolsadeideas.springboot.webflux.app.models.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SessionAttributes("producto")
@Controller
public class ProductoController {

	@Autowired private ProductoService service;
	private static final Logger log = LoggerFactory.getLogger(SpringBootApplication.class);

	@GetMapping({ "/listar", "/" })
	public Mono<String> listar(Model model) {

		Flux<Producto> productos = service.findAllConNombreUppercase();

		productos.subscribe(prod -> log.info(prod.getNombre()));

		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return Mono.just("listar");
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
	public Mono<String> crear(Model m) {
		m.addAttribute("producto", new Producto());
		m.addAttribute("titulo", "Form de Producto");
		return Mono.just("form");
	}

	/*
	 * hace uso del @SessionAttributes para no usar el input hidden en el html, por
	 * lo tanto hay que cerrar la sesion cuando guardamos
	 */
	@PostMapping("/form")
	public Mono<String> guardar(Model m, @Valid Producto producto, BindingResult result, SessionStatus status) {

		if (result.hasErrors()) {
			m.addAttribute("titulo", "Editar Producto");
			return Mono.just("form");
		} else {
			
			status.setComplete();
			
			if(producto.getCreateat() == null) {
				producto.setCreateat(new Date());
			}
			
			return service.save(producto).doOnNext(p -> {
				log.info("Producto guardado: " + p.getNombre() + " Id: " + p.getId());
			}).thenReturn("redirect:/listar?success=Producto+guardado+exitosamente");
		}
	}

	@GetMapping("/form/{id}")
	public Mono<String> editar(@PathVariable String id, Model m) {
		Mono<Producto> productoMono = service.findById(id).doOnNext(p -> {
			log.info("producto: " + p.getNombre());
		});

		m.addAttribute("titulo", "Editar Producto");
		m.addAttribute("producto", productoMono);

		return Mono.just("form");
	}

	/*
	 * version 2 de editar, este metodo requiere que se habilite en el form html el
	 * input hidden para el campo ID, aqui no funciona @SessionAttributes
	 */
	@GetMapping("/form-v2/{id}")
	public Mono<String> editarV2(@PathVariable String id, Model m) {

		return service.findById(id).doOnNext(p -> {
			log.info("producto: " + p.getNombre());
			m.addAttribute("titulo", "Editar Producto");
			m.addAttribute("producto", p);
		}).defaultIfEmpty(new Producto()).flatMap(p -> {
			if (p.getId() == null) {
				return Mono.error(new InterruptedException("no existe el producto"));
			} else {
				return Mono.just(p);
			}

		}).then(Mono.just("form")).onErrorResume(ex -> Mono.just("redirect:/listar?error=no+existe+el+producto"));

	}

}
