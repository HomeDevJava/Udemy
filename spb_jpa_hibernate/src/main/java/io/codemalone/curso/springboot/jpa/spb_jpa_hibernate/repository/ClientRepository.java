package io.codemalone.curso.springboot.jpa.spb_jpa_hibernate.repository;

import org.springframework.data.repository.CrudRepository;

import io.codemalone.curso.springboot.jpa.spb_jpa_hibernate.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
