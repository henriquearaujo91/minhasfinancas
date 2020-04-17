package com.myapps.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// CENARIO
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// EXECUCAO
		Boolean result = repository.existsByEmail("henrique.araujo.ads@gmail.com");
		// VERIFICACAO
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		// EXECUCAO
		Boolean result = repository.existsByEmail("henrique.araujo.ads@gmail.com");
		// VERIFICACAO
		Assertions.assertThat(result).isFalse();
	}

	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		// CENARIO
		Usuario usuario = criarUsuario();

		// EXECUCAO
		Usuario usuarioSalvo = repository.save(usuario);

		// VERIFICACAO
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}

	@Test
	public void deveBuscarUsuarioPorEmail() {
		// CENARIO
		Usuario usuario = criarUsuario();

		// EXECUCAO
		entityManager.persist(usuario);

		// VERIFICACAO
		Optional<Usuario> usuarioSalvo = repository.findByEmail("henrique.araujo.ads@gmail.com");
		Assertions.assertThat(usuarioSalvo.isPresent()).isTrue();
	}

	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		// VERIFICACAO
		Optional<Usuario> usuarioSalvo = repository.findByEmail("henrique.araujo.ads@gmail.com");
		Assertions.assertThat(usuarioSalvo.isPresent()).isFalse();
	}

	public static Usuario criarUsuario() {
		return Usuario.builder().nome("Henrique").email("henrique.araujo.ads@gmail.com").senha("asdf2").build();
	}
}
