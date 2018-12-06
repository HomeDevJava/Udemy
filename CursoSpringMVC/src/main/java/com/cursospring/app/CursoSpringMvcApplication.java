package com.cursospring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursospring.app.models.service.IUploadFileService;

@SpringBootApplication
public class CursoSpringMvcApplication implements CommandLineRunner{
	
	@Autowired IUploadFileService updloadService;

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		updloadService.deleteAll();
		updloadService.init();
	}
}
