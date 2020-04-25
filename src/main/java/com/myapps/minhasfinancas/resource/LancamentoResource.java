package com.myapps.minhasfinancas.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapps.minhasfinancas.api.dto.AtualizaStatusDTO;
import com.myapps.minhasfinancas.api.dto.LancamentoDTO;
import com.myapps.minhasfinancas.exception.RegraNegocioException;
import com.myapps.minhasfinancas.model.entity.Lancamento;
import com.myapps.minhasfinancas.model.entity.Usuario;
import com.myapps.minhasfinancas.model.entity.enums.StatusLancamento;
import com.myapps.minhasfinancas.model.entity.enums.TipoLancamento;
import com.myapps.minhasfinancas.service.ILancamentoService;
import com.myapps.minhasfinancas.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

	private final ILancamentoService service;
	private final IUsuarioService usuarioService;

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "usuario") Long idUsuario) {

		return usuarioService.obterPorId(idUsuario).map(usuario -> {
			Lancamento lancamentoFiltro = Lancamento.builder().descricao(descricao).mes(mes).ano(ano).usuario(usuario)
					.build();
			List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
			return ResponseEntity.ok().body(lancamentos);
		}).orElseGet(() -> new ResponseEntity(
				"Não foi possivel realizar a consulta. Usuário não encontrado para o Id informado.",
				HttpStatus.BAD_REQUEST));
	}

	@GetMapping("{id}")
	public ResponseEntity obterLancamento(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(lancamento -> new ResponseEntity(converter(lancamento), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		return service.obterPorId(id).map(entidade -> {
			try {
				Lancamento lancamento = converter(dto);
				lancamento.setId(id);
				service.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
		return service.obterPorId(id).map(lancamento -> {
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			if (statusSelecionado == null) {
				return ResponseEntity.badRequest()
						.body("Não foi possivel atualizar o status do lançamento, envie um status valido.");
			} else {
				try {
					lancamento.setStatus(statusSelecionado);
					service.atualizar(lancamento);
					return ResponseEntity.ok(lancamento);
				} catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(lancamento -> {
			service.deletar(lancamento);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	private LancamentoDTO converter(Lancamento lancamento) {
		return LancamentoDTO.builder().id(lancamento.getId()).descricao(lancamento.getDescricao())
				.valor(lancamento.getValor()).mes(lancamento.getMes()).ano(lancamento.getAno())
				.status(lancamento.getStatus().name()).tipo(lancamento.getTipo().name())
				.usuario(lancamento.getUsuario().getId()).build();
	}

	private Lancamento converter(LancamentoDTO dto) {
		Usuario usuario = usuarioService.obterPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o Id informado."));

		return Lancamento.builder().ano(dto.getAno()).descricao(dto.getDescricao()).id(dto.getId()).mes(dto.getMes())
				.valor(dto.getValor())
				.status(dto.getStatus() != null ? StatusLancamento.valueOf(dto.getStatus()) : null)
				.tipo(dto.getTipo() != null ? TipoLancamento.valueOf(dto.getTipo()) : null).usuario(usuario).build();
	}
}
