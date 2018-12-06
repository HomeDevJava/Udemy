package com.cursospring.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="clientes")
@Getter @Setter
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellidos;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotNull
	@Column(name="create_at")
	@Temporal(TemporalType.DATE) @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	@Column(name="foto")
	private String foto;
	
	/*@PrePersist
	public void preGuardado() {
		createAt= new Date();
	}*/
}