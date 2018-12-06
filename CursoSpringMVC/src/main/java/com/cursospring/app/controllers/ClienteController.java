package com.cursospring.app.controllers;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cursospring.app.models.entity.Cliente;
import com.cursospring.app.models.service.IClienteService;
import com.cursospring.app.models.service.IUploadFileService;
import com.cursospring.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired private IClienteService clienteService;
	@Autowired private IUploadFileService uploadFileService;
	
	
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(defaultValue = "0") int page, Model m) {

		Pageable pageRequest = PageRequest.of(page, 10);

		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);

		m.addAttribute("titulo", "Listado de Clientes");
		m.addAttribute("lista", clientes);
		m.addAttribute("page", pageRender);
		return "listar3";
	}

	@RequestMapping(value = "/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Formulario de cliente");
		m.addAttribute("cliente", new Cliente());
		return "form";
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model m, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la BD");
			return "redirect:/listar";
		}

		m.addAttribute("cliente", cliente);
		m.addAttribute("titulo", "Detalle Cliente: " + cliente.getNombre());
		return "ver";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, @RequestParam("file") MultipartFile foto, BindingResult result,
			Model m, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}

		// se evalua que la foto no este vacia
		if (!foto.isEmpty()) {
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				
				uploadFileService.delete(cliente.getFoto());
				/*Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
				File archivo = rootPath.toFile();
				if (archivo.exists() && archivo.canRead()) {
					if (archivo.delete()) {
						flash.addFlashAttribute("info", "Foto: " + cliente.getFoto() + "eliminada con exito!");
					}
				}*/
			}
			
			String uniqueFile=null;
			try {
				uniqueFile = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cliente.setFoto(uniqueFile);
			flash.addFlashAttribute("info", "Has subido correctamente " + uniqueFile);

			/*// 2a Alternativa creamos una variable para tener un unico nombre de archivo,obtenemos el path de la carpeta upload que esta
			// en la raiz de nuestro proyecto y obtenemos el path absoluto
			String uniqueFile = UUID.randomUUID().toString() + foto.getOriginalFilename();
			Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(uniqueFile);
			Path rootAbsolutpath = rootPath.toAbsolutePath();

			// mostramos en consola el path
			log.info("rootPath: " + rootPath);
			log.info("rootAbsolute: " + rootAbsolutpath);
			try {
				// 1a Alternativa.-creamos el path completo con nombre de la foto y leemos los
				// bytes, luego se escribe al archivo
				
				 * Path fotoPath=Paths.get(rootPath+ "//"+foto.getOriginalFilename()); byte[]
				 * bytes=foto.getBytes(); Files.write(fotoPath, bytes);
				 

				// 2a Alternativa para guardar la foto en el path absoluto(carpeta upload dentro
				// del proyecto)
				Files.copy(foto.getInputStream(), rootAbsolutpath);

				// asignamos el nombre de la foto al campo en la BD y enviamos un msj al usuario
				// cliente.setFoto(foto.getOriginalFilename());
				// 2a Alternativa para asignar el nombre del archivo unique para que no se
				// sobrescriba
				cliente.setFoto(uniqueFile);
				flash.addFlashAttribute("info", "Has subido correctamente " + foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}*/

		}

		String msjFlash = (cliente.getId() != null) ? "Cliente editado con exito" : "Cliente creado con Exito!";

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
			
			
			/*// obtenemos el path de la foto del cliente
			Path rootPath = Paths.get(UPLOAD_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
			// se pasa a un objeto File para poder borrarlo pero antes validamos que exista
			// y sea leido
			File archivo = rootPath.toFile();
			if (archivo.exists() && archivo.canRead()) {
				if (archivo.delete()) {
					flash.addFlashAttribute("info", "Foto: " + cliente.getFoto() + "eliminada con exito!");
				}
			}*/

		}
		return "redirect:/listar";
	}

}