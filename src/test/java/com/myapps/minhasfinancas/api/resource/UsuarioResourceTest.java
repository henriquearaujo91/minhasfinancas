package com.myapps.minhasfinancas.api.resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapps.minhasfinancas.api.dto.UsuarioDTO;
import com.myapps.minhasfinancas.exception.ErroAutenticacao;
import com.myapps.minhasfinancas.exception.RegraNegocioException;
import com.myapps.minhasfinancas.model.entity.Usuario;
import com.myapps.minhasfinancas.resource.UsuarioResource;
import com.myapps.minhasfinancas.service.ILancamentoService;
import com.myapps.minhasfinancas.service.IUsuarioService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioResource.class)
@AutoConfigureMockMvc
public class UsuarioResourceTest {

	static final String API = "/api/usuarios";
	static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	IUsuarioService service;
	
	@MockBean
	ILancamentoService lancamentoService;
	
	@Test
	public void deveAutenticarUmUsuario() throws Exception{
		String email = "teste@teste.com";
		String senha = "1234";
				
		//CENARIO
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		Usuario usuario = Usuario.builder().id(1l).email(email).senha(senha).build();
		
		Mockito.when(service.autenticar(email, senha)).thenReturn(usuario);//CRIA MOCK PARA A CHAMADA DO METODO autenticar
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		//EXECUCAO - VERIFICACAO
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/autenticar"))
																		.accept(JSON)
																		.contentType(JSON)
																		.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
		
	}
	
	
	@Test
	public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception{
		String email = "teste@teste.com";
		String senha = "1234";
				
		//CENARIO
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		Mockito.when(service.autenticar(email, senha)).thenThrow(ErroAutenticacao.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		//EXECUCAO - VERIFICACAO
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API.concat("/autenticar"))
																		.accept(JSON)
																		.contentType(JSON)
																		.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void deveCriarUmNovoUsuario() throws Exception{
		String email = "teste@teste.com";
		String senha = "1234";
				
		//CENARIO
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		Usuario usuario = Usuario.builder().id(1l).email(email).senha(senha).build();
		
		Mockito.when(service.salvar(Mockito.any(Usuario.class))).thenReturn(usuario);//CRIA MOCK PARA A CHAMADA DO METODO salvar
		String json = new ObjectMapper().writeValueAsString(dto);
		
		//EXECUCAO - VERIFICACAO
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
																		.accept(JSON)
																		.contentType(JSON)
																		.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
		
	}
	
	@Test
	public void deveRetornarBadRequestAoTentarCriarUmUsuarioInvalido() throws Exception{
		String email = "teste@teste.com";
		String senha = "1234";
				
		//CENARIO
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		
		Mockito.when(service.salvar(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		//EXECUCAO - VERIFICACAO
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
																		.accept(JSON)
																		.contentType(JSON)
																		.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
}
