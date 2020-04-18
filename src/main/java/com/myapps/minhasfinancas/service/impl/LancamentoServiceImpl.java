package com.myapps.minhasfinancas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapps.minhasfinancas.model.entity.Lancamento;
import com.myapps.minhasfinancas.model.entity.enums.StatusLancamento;
import com.myapps.minhasfinancas.model.repository.ILancamentoRepository;
import com.myapps.minhasfinancas.service.ILancamentoService;

@Service
public class LancamentoServiceImpl implements ILancamentoService {

	private ILancamentoRepository repository;

	public LancamentoServiceImpl(ILancamentoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		return repository.save(lancamento);
	}

	@Override
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		// TODO Auto-generated method stub

	}

}
