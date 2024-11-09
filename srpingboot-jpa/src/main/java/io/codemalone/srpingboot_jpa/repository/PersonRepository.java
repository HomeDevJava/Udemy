package io.codemalone.srpingboot_jpa.repository;

import org.springframework.data.repository.CrudRepository;

import io.codemalone.srpingboot_jpa.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    

}