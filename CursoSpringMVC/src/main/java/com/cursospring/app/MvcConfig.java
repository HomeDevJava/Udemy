package com.cursospring.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	private Logger log=LoggerFactory.getLogger(getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//obtenemos el path absoluto de la carpeta upload dentro de la raiz del proyecto
		String resourcesPath=Paths.get("upload").toAbsolutePath().toUri().toString();
		//WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/upload/**").addResourceLocations(resourcesPath);
		//addResourceLocations("file:/C:/Temp/upload/");
		
		log.info(resourcesPath);
	}
	

	//agregamos el mapping y view personalizado para mostrar una pagina de error de Acceso denegado mas amistosa
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
}
