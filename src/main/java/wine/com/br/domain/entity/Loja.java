package wine.com.br.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wine.com.br.auditor.Auditable;

@Data
@Entity
@Table(name = "loja")
public class Loja extends Auditable<String> { 
	/*
	 * Implementação/mapeamento da auditoria na entidade loja.
	 * */

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;

	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name="contacto", nullable = false)
	private String contacto;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="isOpen")
	private boolean isOpen;
	
	@Column(name ="endereco", nullable = false)
	private String endereco;
	
}
