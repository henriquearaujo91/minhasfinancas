package com.myapps.minhasfinancas.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario", schema = "financas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Usuario {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome", nullable = true, length = 150)
	private String nome;

	@Column(name = "email", nullable = true, length = 100)
	private String email;

	@Column(name = "senha", nullable = true, length = 20)
	@JsonIgnore
	private String senha;

}
