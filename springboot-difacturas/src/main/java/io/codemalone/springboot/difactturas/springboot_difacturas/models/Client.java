package io.codemalone.springboot.difactturas.springboot_difacturas.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@RequestScope
@JsonIgnoreProperties({"targetSource","advisors"})
public class Client {

    @Value("${client.name}")
    private String name;
   
    @Value("${client.lastname}")
    private String lastname;

    // constructor
    public Client() {}

    public Client(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
