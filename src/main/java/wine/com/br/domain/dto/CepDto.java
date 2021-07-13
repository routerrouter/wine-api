package wine.com.br.domain.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wine.com.br.domain.entity.Loja;

@Data
@Schema(name = "CepDto" , description = "Objecto de modelo do recurso do Cep")
public class CepDto extends RepresentationModel<CepDto>{

	@EqualsAndHashCode.Include
	@Schema(name = "Código do Cep" , description = "Código do recurso do Cep")
	private Integer codigo;
	
	@Schema(name = "Loja" , description = "Loja do recurso do Cep")
	private Loja loja;
	
	@Schema(name = "FAIXA_INICO do Cep" , description = "Faixa Inicial do recurso da Loja")
	private Long faixaInicio;
	
	@Schema(name = "FAIXA_FIM do Cep" , description = "Faixa Fim do recurso da Loja")
	private Long faixaFim;
	
	
}
