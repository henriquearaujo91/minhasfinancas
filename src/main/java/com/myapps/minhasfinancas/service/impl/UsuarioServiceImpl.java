package com.myapps.minhasfinancas.service.impl;

import com.myapps.minhasfinancas.model.entity.Usuario;
import com.myapps.minhasfinancas.model.repository.IUsuarioRepository;
import com.myapps.minhasfinancas.service.IUsuarioService;

public class UsuarioServiceImpl implements IUsuarioService {

	private IUsuarioRepository repository;

	public UsuarioServiceImpl(IUsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		// TODO Auto-generated method stub

	}

}
