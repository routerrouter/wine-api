package wine.com.br.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wine.com.br.auditor.Auditable;

@Data
@Entity
@Table(name = "cep")
public class Cep extends Auditable<String> { 
	/*
	 * Implementação/mapeamento da auditoria na entidade cep.
	 * */

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "codigo_loja")
	private Loja loja;
	
	@NotBlank
	@Size(max = 8)
	@Column(name="faixa_inicio", nullable = false)
	private Long faixaInicio;
	
	@NotBlank
	@Size(max = 8)
	@Column(name="faixa_fim", nullable = false)
	private Long faixaFim;
	
	
	
}
