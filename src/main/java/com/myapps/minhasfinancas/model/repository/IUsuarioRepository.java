package com.myapps.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.minhasfinancas.model.entity.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	Boolean existsByEmail(String email);

	Optional<Usuario> findByEmail(String email);

}
