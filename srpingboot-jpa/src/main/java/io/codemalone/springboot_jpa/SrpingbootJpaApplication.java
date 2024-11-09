package io.codemalone.springboot_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.codemalone.springboot_jpa.entity.Person;
import io.codemalone.springboot_jpa.repository.PersonRepository;

@SpringBootApplication
public class SrpingbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SrpingbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Person> persons = (List<Person>) personRepository.findAll();
		persons.stream().forEach(System.out::println);
	}

}
