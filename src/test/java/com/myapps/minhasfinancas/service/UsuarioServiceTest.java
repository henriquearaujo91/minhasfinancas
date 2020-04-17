package com.myapps.minhasfinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapps.minhasfinancas.exception.RegraNegocioException;
import com.myapps.minhasfinancas.model.repository.IUsuarioRepository;
import com.myapps.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	IUsuarioService usuarioService;

	@MockBean
	IUsuarioRepository usuarioRepository;

	@BeforeEach
	public void setUp() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}

	@Test
	public void deveValidarEmail() {
		Assertions.assertDoesNotThrow(() -> {
			// CENARIO
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
			// ACAO
			usuarioService.validarEmail("henrique.araujo.ads@gmail.com");
		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			// CENARIO
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

			// ACAO
			usuarioService.validarEmail("henrique.araujo.ads@gmail.com");
		});

	}
}
