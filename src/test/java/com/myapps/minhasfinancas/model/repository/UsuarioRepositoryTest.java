package com.myapps.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapps.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {

	@Autowired
	private IUsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// CENARIO
		Usuario usuario = Usuario.builder().nome("Henrique Valença").email("henrique.araujo.ads@gmail.com").build();
		repository.save(usuario);
		// EXECUCAO
		Boolean result = repository.existsByEmail("henrique.araujo.ads@gmail.com");
		// VERIFICACAO
		Assertions.assertThat(result).isTrue();
	}
}
