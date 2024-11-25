package io.codemalone.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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

		update();
	}

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	public void listPerson() {

		// List<Person> persons = (List<Person>) personRepository.findAll();
		List<Person> persons = (List<Person>) personRepository.findByProgrammingLanguageAndName("Python", "Pepe");
		persons.stream().forEach(System.out::println);

		List<Object[]> personData = personRepository.obtenerPersonData("Java");
		personData.stream().forEach(person -> System.out.println(person[0] + " es experto en: " + person[1]));
	}

	@Transactional
	public void create(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre: ");
		String name = scanner.nextLine();
		System.out.println("Ingrese el apellido: ");
		String lastname = scanner.nextLine();
		System.out.println("Ingrese el lenguaje de programacion: ");
		String programmingLanguage = scanner.nextLine();
		
		Person person = new Person();
		person.setName(name);
		person.setLastname(lastname);
		person.setProgramingLanguage(programmingLanguage);
		Person personNew= personRepository.save(person);
		scanner.close();

		personRepository.findById(personNew.getId()).ifPresent(System.out::println);
		
	}

	@Transactional
	public void update(){
		//solicita el id y lo busca
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id: ");
		Long id=scanner.nextLong();
		Optional<Person> person=personRepository.findById(id);

		//valida si existe el id
		if(!person.isPresent()){
			System.out.println("No existe el id");			
		}

		//si existe el id lo actualiza
		person.ifPresent(p -> {
			System.out.println(p);
			System.out.println("Ingrese el lenguaje de programacion: ");
			String programmingLanguage = scanner.next();
			p.setProgramingLanguage(programmingLanguage);
			Person personDb=personRepository.save(p);
			System.out.println(personDb);
		});
		scanner.close();
	}

}
