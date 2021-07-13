package wine.com.br.domain.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "ClienteDto" , description = "Objecto de modelo do recurso do Cliente")
public class ClienteDto extends RepresentationModel<ClienteDto>{

	@EqualsAndHashCode.Include
	@Schema(name = "Código do Cliente" , description = "Código do recurso do Cliente")
	private Integer codigo;
	
	@Schema(name = "Nome do Cliente" , description = "Nome do recurso do Cliente")
	private String nome;
	
	@Schema(name = "Telefone do Cliente" , description = "Telefone do recurso da Loja")
	private String telefone;
	
	@Schema(name = "Status" , description = "Status do recurso da Loja -> ativo|desactivado")
	private boolean enabled;
}
