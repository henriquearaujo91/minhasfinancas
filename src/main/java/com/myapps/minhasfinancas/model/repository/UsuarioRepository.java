package com.myapps.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
