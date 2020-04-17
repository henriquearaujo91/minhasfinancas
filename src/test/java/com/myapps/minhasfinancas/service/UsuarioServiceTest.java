package com.myapps.minhasfinancas.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapps.minhasfinancas.exception.ErroAutenticacao;
import com.myapps.minhasfinancas.exception.RegraNegocioException;
import com.myapps.minhasfinancas.model.entity.Usuario;
import com.myapps.minhasfinancas.model.repository.IUsuarioRepository;
import com.myapps.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private static final String EMAIL = "henrique.araujo.ads@gmail.com";
	private static final String SENHA = "fa9fa2";

	IUsuarioService usuarioService;

	@MockBean
	IUsuarioRepository usuarioRepository;

	@BeforeEach
	public void setUp() {
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		Assertions.assertDoesNotThrow(() -> {
			// CENARIO

			Usuario usuario = retornaUsuario();
			Mockito.when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));

			// ACAO
			Usuario result = usuarioService.autenticar(EMAIL, SENHA);

			// RESULTADO
			Assertions.assertNotNull(result);
		});
	}

	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		Throwable exception = Assertions.assertThrows(ErroAutenticacao.class, () -> {
			// CENARIO
			Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

			// ACAO
			usuarioService.autenticar(EMAIL, SENHA);
		});
		
		Assertions.assertEquals(exception.getMessage(), "Usuário não encontrado para o email informado.");
	}

	@Test
	public void deveLacarErroQuandoSenhaNaoBater() {
		Throwable exception = Assertions.assertThrows(ErroAutenticacao.class, () -> {
			// CENARIO
			Usuario usuario = retornaUsuario();
			Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

			// ACAO
			usuarioService.autenticar(EMAIL, "ksj67sd");
		});

		Assertions.assertEquals(exception.getMessage(), "Senha inválida.");
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

	public static Usuario retornaUsuario() {
		return Usuario.builder().email(EMAIL).senha(SENHA).id(1l).build();
	}
}
