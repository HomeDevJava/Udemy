package io.codemalone.springboot_jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.codemalone.springboot_jpa.dto.PersonDto;
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

    //este metodo obtiene solo ciertos campos  del objeto person, aunque es recomendable devolver el objeto completo
    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonData();

    //Sobrecarga de metodo
    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonData( String programmingLanguage);

    //obtiene 1 solo objeto desarmado
    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.id = ?1")
    Object obtenerPersonDataById( Long id);

    //otra forma de obtener el objeto completo y un campo adicional
    @Query("SELECT p, p.programmingLanguage FROM Person p")
    List<Object[]> findMixAllPerson();

    //obtene un objeto personalizado con los campos que se le indiquen usando el constructor de la clase Person
    @Query("SELECT new Person(p.name, p.lastname) FROM Person p")
    List<Person> findAllObjectPersonalized();

    //uso de clase DTO
    @Query("SELECT new io.codemalone.springboot_jpa.dto.PersonDto(p.name, p.lastname) FROM Person p")
    List<PersonDto> findAllPersonDto();


    @Query("SELECT DISTINCT p.programmingLanguage FROM Person p")
    List<String> findDistinctProgrammingLanguage();
    
    @Query("SELECT  COUNT(DISTINCT(p.programmingLanguage)) FROM Person p")
    List<Object[]> findDistinctProgrammingLanguageCount();




    Optional<Person> findById(Long id);

    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);
   
    @Query("SELECT p FROM Person p WHERE p.name = ?1")
    Optional<Person> findOneName(String name);
   
    @Query("SELECT p FROM Person p WHERE p.name like %?1%")
    Optional<Person> findOneLikeName(String name);
    Optional<Person> findByNameContaining(String string);
    
    

}