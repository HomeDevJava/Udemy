package com.cursospring.app.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.service.IClienteService;
import com.cursospring.app.models.service.IUploadFileService;
import com.cursospring.app.util.paginator.PageRender;
import com.cursospring.app.view.xml.ClienteList;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired	private IClienteService clienteService;
	@Autowired	private IUploadFileService uploadFileService;
	@Autowired MessageSource messageSource; //lo utilizamos para la internacionalizacion
	
	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = { "/listar", "/" }, method = RequestMethod.GET)
	public String listar(@RequestParam(defaultValue = "0") int page, Model m, Authentication authentication,
			HttpServletRequest req, Locale locale) {

		// 1a forma para obtener el usuario autenticado dentro del controller
		if (authentication != null) {
			log.info("Hola Usuario autenticado, tu username es: " + authentication.getName());
		}

		// 2a forma(de manera estatica) de obtener el usuario autenticado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.equals(null)) {
			log.info("Usuario autenticado Utilizando forma estatica: " + auth.getName());
		}

		if (hasRole("ROLE_ADMIN")) {
			log.info("Hola " + auth.getName() + " tienes acceso");
		} else {
			log.info("Hola " + auth.getName() + " NO tienes acceso");
		}

		// 3a forma de obtener un usuario autenticado
		SecurityContextHolderAwareRequestWrapper securityCtx = new SecurityContextHolderAwareRequestWrapper(req,
				"ROLE_");

		if (securityCtx.isUserInRole("ADMIN")) {
			log.info("Forma usando SecurityContextHolderAwareRequestWrapper" + auth.getName() + " tienes acceso");
		}
		if (req.isUserInRole("ROLE_ADMIN")) {
			log.info("Forma nativa usando HttpServletRequest" + auth.getName() + " tienes acceso");
		}

		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);

		m.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		m.addAttribute("lista", clientes);
		m.addAttribute("page", pageRender);
		return "listar3";
	}

	//listado de Manera Handler REST usando ResponseBody  y si queremos utilizar XML debe utilizarse el wrapper ClientList
	@GetMapping(value ="/listar-rest")
	@ResponseBody
	public ClienteList listarRest() {
		return new ClienteList(clienteService.findAll());
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Model m, Locale locale) {
		m.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo.crear", null, locale));
		m.addAttribute("cliente", new Cliente());
		return "form";
	}

	@PreAuthorize("hasRole('ROLE_USERS')") 
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model m, RedirectAttributes flash) {
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);// clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD");
			return "redirect:/listar";
		}

		m.addAttribute("cliente", cliente);
		m.addAttribute("titulo", "Detalle Cliente: " + cliente.getNombre());
		return "ver";
	}

	@Secured("ROLE_ADMIN") 
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, @RequestParam("file") MultipartFile foto, BindingResult result,
			Model m, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}

		// se evalua que la foto no este vacia
		if (!foto.isEmpty()) {
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null  && cliente.getFoto().length() > 0) {

				uploadFileService.delete(cliente.getFoto());
				/*
				 * Path rootPath =
				 * Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath(); File
				 * archivo = rootPath.toFile(); if (archivo.exists() && archivo.canRead()) { if
				 * (archivo.delete()) { flash.addFlashAttribute("info", "Foto: " +
				 * cliente.getFoto() + "eliminada con exito!"); } }
				 */
			}

			String uniqueFile = null;
			try {
				uniqueFile = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			cliente.setFoto(uniqueFile);
			flash.addFlashAttribute("info", "Has subido correctamente " + uniqueFile);

			/*
			 * // 2a Alternativa creamos una variable para tener un unico nombre de
			 * archivo,obtenemos el path de la carpeta upload que esta // en la raiz de
			 * nuestro proyecto y obtenemos el path absoluto String uniqueFile =
			 * UUID.randomUUID().toString() + foto.getOriginalFilename(); Path rootPath =
			 * Paths.get(UPLOAD_FOLDER).resolve(uniqueFile); Path rootAbsolutpath =
			 * rootPath.toAbsolutePath();
			 * 
			 * // mostramos en consola el path log.info("rootPath: " + rootPath);
			 * log.info("rootAbsolute: " + rootAbsolutpath); try { // 1a
			 * Alternativa.-creamos el path completo con nombre de la foto y leemos los //
			 * bytes, luego se escribe al archivo
			 * 
			 * Path fotoPath=Paths.get(rootPath+ "//"+foto.getOriginalFilename()); byte[]
			 * bytes=foto.getBytes(); Files.write(fotoPath, bytes);
			 * 
			 * 
			 * // 2a Alternativa para guardar la foto en el path absoluto(carpeta upload
			 * dentro // del proyecto) Files.copy(foto.getInputStream(), rootAbsolutpath);
			 * 
			 * // asignamos el nombre de la foto al campo en la BD y enviamos un msj al
			 * usuario // cliente.setFoto(foto.getOriginalFilename()); // 2a Alternativa
			 * para asignar el nombre del archivo unique para que no se // sobrescriba
			 * cliente.setFoto(uniqueFile); flash.addFlashAttribute("info",
			 * "Has subido correctamente " + foto.getOriginalFilename());
			 * 
			 * } catch (IOException e) { e.printStackTrace(); }
			 */

		}

		String msjFlash = (cliente.getId() != null) ? "Cliente editado con exito" : "Cliente creado con Exito!";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", msjFlash);
		return "redirect:/listar";
	}

	@Secured("ROLE_ADMIN")
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

	@Secured("ROLE_ADMIN") 
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if (id > 0) {
			// buscamos el cliente para poder eliminar la foto
			Cliente cliente = clienteService.findOne(id);
			// borramos el cliente
			clienteService.delete(id);
			// enviamos un mensaje al usuario atraves de un flashAttribute
			flash.addFlashAttribute("success", "Cliente Eliminado con éxito!");

			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Foto: " + cliente.getFoto() + "eliminada con exito!");
			}

			/*
			 * // obtenemos el path de la foto del cliente Path rootPath =
			 * Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath(); // se
			 * pasa a un objeto File para poder borrarlo pero antes validamos que exista //
			 * y sea leido File archivo = rootPath.toFile(); if (archivo.exists() &&
			 * archivo.canRead()) { if (archivo.delete()) { flash.addFlashAttribute("info",
			 * "Foto: " + cliente.getFoto() + "eliminada con exito!"); } }
			 */

		}
		return "redirect:/listar";
	}

	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth2 = context.getAuthentication();
		if (auth2 == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth2.getAuthorities();

		return authorities.contains(new SimpleGrantedAuthority(role));

		/*
		 * for (GrantedAuthority authority : authorities) {
		 * if(role.equals(authority.getAuthority())) {
		 * log.info("Hola usuario ".concat(auth2.getName()).concat("tu role es: ".concat
		 * (authority.getAuthority()))); return true; } }
		 * 
		 * return false;
		 */

	}

}