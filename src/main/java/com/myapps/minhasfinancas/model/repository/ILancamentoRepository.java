package com.myapps.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.minhasfinancas.model.entity.Lancamento;

public interface ILancamentoRepository extends JpaRepository<Lancamento, Long> {

}
