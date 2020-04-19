package com.myapps.minhasfinancas.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.myapps.minhasfinancas.exception.RegraNegocioException;
import com.myapps.minhasfinancas.model.entity.Lancamento;
import com.myapps.minhasfinancas.model.entity.enums.StatusLancamento;
import com.myapps.minhasfinancas.model.repository.ILancamentoRepository;
import com.myapps.minhasfinancas.model.repository.LancamentoRepositoryTest;
import com.myapps.minhasfinancas.service.impl.LancamentoServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@SpyBean
	LancamentoServiceImpl service;
	
	@MockBean
	ILancamentoRepository repository;
	
	@Test
	public void deveSalvarUmLancamento() {
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
		Mockito.doNothing().when(service).validar(lancamentoASalvar);
		
		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
		lancamentoSalvo.setId(1l);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
		Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);
		
		Lancamento lancamento = service.salvar(lancamentoASalvar);
		
		assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
		assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.PENDENTE);
	}
	
	@Test
	public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao() {
		// CENARIO
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
		Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

		//EXECUCAO/VERIFICACAO
		catchThrowableOfType(() -> service.salvar(lancamentoASalvar), RegraNegocioException.class);//RETORNA RegraNegocioException AO TENTAR SALVAR
		Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);// VERIFICA QUE NUNCA CHAMOU O METODO SAVE
	}
	
	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
		lancamentoSalvo.setId(1l);
		lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);
		
		Mockito.doNothing().when(service).validar(lancamentoSalvo);//CRIA UM MOCK DO "lancamentoSalvo" UTILIZADO NO METODO DE VALIDAR
		
		Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);//CRIA UM MOCK DO "lancamentoSalvo" NO METODO SAVE 
		
		service.atualizar(lancamentoSalvo);
		
		Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);//VALIDA SE  A CHAMADA AO METODO "save" FOI EXECUTADA COM SUCESSO
	}
	
	@Test
	public void deveLancarErroAoTentarAtualizarUmLancamentoQueAindaNaoFoiSalvo() {
		// CENARIO
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

		//EXECUCAO/VERIFICACAO
		catchThrowableOfType(() -> service.atualizar(lancamento), NullPointerException.class);//RETORNA NullPointerException AO TENTAR SALVAR
		Mockito.verify(repository, Mockito.never()).save(lancamento);// VERIFICA QUE NUNCA CHAMOU O METODO SAVE
	}
	
	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();//PREENCHE A INSTANCIA DO LANCAMENTO
		lancamento.setId(1l);
		
		service.deletar(lancamento);//DELETE O LANCAMENTO
		
		Mockito.verify(repository).delete(lancamento);// VERIFICA SE A DELECAO FOI EXECUTADA PARA O LANCAMENTO
	}
	
	
	@Test
	public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo() {
		// CENARIO
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

		//EXECUCAO
		catchThrowableOfType(() -> service.deletar(lancamento), NullPointerException.class);//RETORNA NullPointerException AO TENTAR DELETAR
		
		//VERIFICACAO
		Mockito.verify(repository, Mockito.never()).delete(lancamento);// VERIFICA QUE NUNCA CHAMOU O METODO DELETE
	}
	
	@Test
	public void deveFiltrarLancamentos() {
		// CENARIO
		Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
		lancamento.setId(1l);

		List<Lancamento> lista = Arrays.asList(lancamento);
		Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);
		
		// EXECUCAO
		List<Lancamento> resultado = service.buscar(lancamento);
		
		// VERIFICACAO
		assertThat(resultado)
			.isNotEmpty()
			.hasSize(1)
			.contains(lancamento);
	}
}
