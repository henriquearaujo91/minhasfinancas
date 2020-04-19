package com.myapps.minhasfinancas.service;

import java.util.Optional;

import com.myapps.minhasfinancas.model.entity.Usuario;

public interface IUsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvar(Usuario usuario);

	void validarEmail(String email);
	
	Optional<Usuario> obterPorId(Long id);
	
}
