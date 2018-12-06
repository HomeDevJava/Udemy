package com.cursospring.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService implements IUploadFileService {

	private final static String UPLOAD_FOLDER = "upload";
	// crear un debug para mostrar en consola
	private final Logger log = LoggerFactory.getLogger(getClass());

	
	//este metodo se utiliza para cargar la imagen programaticamente en lugar de utilizar la configuracion de AddResourceHandler
	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFile = UUID.randomUUID().toString() + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFile);
		// Path rootAbsolutpath = rootPath.toAbsolutePath();
		log.info("rootPath: " + rootPath);
		// log.info("rootAbsolute: " + rootAbsolutpath);
		Files.copy(file.getInputStream(), rootPath);

		return uniqueFile;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

}
