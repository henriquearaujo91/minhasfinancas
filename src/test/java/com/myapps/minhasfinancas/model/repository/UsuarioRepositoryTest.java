package com.myapps.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.myapps.minhasfinancas.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		// CENARIO
		repository.deleteAll();
		// EXECUCAO
		Boolean result = repository.existsByEmail("henrique.araujo.ads@gmail.com");
		// VERIFICACAO
		Assertions.assertThat(result).isFalse();
	}
}
