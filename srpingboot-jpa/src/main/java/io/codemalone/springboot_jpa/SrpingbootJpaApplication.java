package io.codemalone.springboot_jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.codemalone.springboot_jpa.entity.Person;
import io.codemalone.springboot_jpa.repository.PersonRepository;

@SpringBootApplication
public class SrpingbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SrpingbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		findOne();
		create();
	}

	private void findOne() {

		/* Person person = null;
		Optional<Person> personOptional = personRepository.findById(8L);
		if (personOptional.isPresent()) {
			person = personOptional.get();
		}
		System.out.println(person); */

		//el codigo de arriba se simplifica con el siguiente
		personRepository.findOne(1L).ifPresent(System.out::println);
		
		personRepository.findOneName("Jisashi").ifPresent(System.out::println);
		personRepository.findOneLikeName("fa").ifPresent(System.out::println);
		personRepository.findByNameContaining("ria").ifPresent(System.out::println);


		/*
		Person person = personRepository.findById(1L).orElseThrow(null);
		System.out.println(person); */
	}

	public void listPerson() {

		// List<Person> persons = (List<Person>) personRepository.findAll();
		List<Person> persons = (List<Person>) personRepository.findByProgrammingLanguageAndName("Python", "Pepe");
		persons.stream().forEach(System.out::println);

		List<Object[]> personData = personRepository.obtenerPersonData("Java");
		personData.stream().forEach(person -> System.out.println(person[0] + " es experto en: " + person[1]));
	}

	public void create(){
		
		Person person = new Person();
		person.setName("Lalo");
		person.setLastname("Thor");
		person.setProgramingLanguage("Python");
		personRepository.save(person);
	}

}
