package com.cursospring.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	private Logger log=LoggerFactory.getLogger(getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//obtenemos el path absoluto de la carpeta upload dentro de la raiz del proyecto
		String resourcesPath=Paths.get("upload").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/upload/**").addResourceLocations(resourcesPath);
		//addResourceLocations("file:/C:/Temp/upload/");
		
		log.info(resourcesPath);
	}
	

	//agregamos el mapping y view personalizado para mostrar una pagina de error de Acceso denegado mas amistosa
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	//se registra un bean Bcrypt para usar la autenticacion con BD JDBC
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//implementando multilenguaje----------------------------------------------------------
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localResolver= new SessionLocaleResolver();
		localResolver.setDefaultLocale(new Locale("es", "ES"));
		return localResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor= new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		return localeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {		
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
	
}
