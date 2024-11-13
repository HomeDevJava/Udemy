package io.codemalone.springboot_jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.codemalone.springboot_jpa.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    //estos metodos usando la nomenclatura de query method que ejecuta por debajo una query JPQL
    List<Person> findByName(String name);
    List<Person> findByLastname(String lastname);
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    //usando JPQL Query
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    //este metodo  cumple el mismo rol que el anterior pero usando la nomenclatura de query method
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

}