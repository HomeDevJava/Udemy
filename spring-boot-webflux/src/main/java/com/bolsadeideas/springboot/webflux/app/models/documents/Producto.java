package com.bolsadeideas.springboot.webflux.app.models.documents;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "productos")
public class Producto {

	@Id
	private String id;
	
	@NotEmpty(message = "no puede ser vacio")
	private String nombre;
	
	@NotNull
	private Double precio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createat;
	
	public Producto() {}

	public Producto(String nombre, Double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getCreateat() {
		return createat;
	}

	public void setCreateat(Date createat) {
		this.createat = createat;
	}
	
	
	
	
}
