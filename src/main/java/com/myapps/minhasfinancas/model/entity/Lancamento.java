package com.myapps.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.myapps.minhasfinancas.model.entity.enums.StatusLancamento;
import com.myapps.minhasfinancas.model.entity.enums.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento", schema = "financas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Lancamento {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;

	@Column(name = "mes", nullable = false)
	private Integer mes;

	@Column(name = "ano", nullable = false)
	private Integer ano;

	@Column(name = "valor", nullable = true, length = 16)
	private BigDecimal valor;

	@Column(name = "tipo", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;

	@Column(name = "status", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento status;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private Date dataCadastro;

}
