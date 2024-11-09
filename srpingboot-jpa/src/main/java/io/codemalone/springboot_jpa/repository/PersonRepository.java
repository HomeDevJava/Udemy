package io.codemalone.springboot_jpa.repository;

import org.springframework.data.repository.CrudRepository;

import io.codemalone.springboot_jpa.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    

}