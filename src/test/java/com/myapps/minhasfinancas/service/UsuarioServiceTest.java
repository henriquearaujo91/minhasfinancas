package com.myapps.minhasfinancas.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
	private static final String NOME = "Henrique";
	private static final Long UM_LONG = Long.valueOf(1);

	@SpyBean
	UsuarioServiceImpl usuarioService;

	@MockBean
	IUsuarioRepository usuarioRepository;

	@Test
	public void deveSalvarUmUsuario() {
		Assertions.assertDoesNotThrow(() -> {
			// CENARIO
			Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
			Usuario usuario = retornaUsuario();

			Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

			// ACAO
			Usuario result = usuarioService.salvar(usuario);

			// RESULTADO
			Assertions.assertEquals(UM_LONG, result.getId());
			Assertions.assertEquals(EMAIL, result.getEmail());
			Assertions.assertEquals(NOME, result.getNome());
			Assertions.assertEquals(SENHA, result.getSenha());
		});

	}

	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			// CENARIO
			Usuario usuario = retornaUsuario();
			Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(EMAIL);

			// ACAO
			usuarioService.salvar(usuario);

			// VERIFICACAO
			Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
		});
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
		return Usuario.builder().nome(NOME).email(EMAIL).senha(SENHA).id(UM_LONG).build();
	}
}
