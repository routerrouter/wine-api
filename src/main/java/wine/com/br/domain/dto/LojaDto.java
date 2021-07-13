package wine.com.br.domain.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "EnderecoDto" , description = "Objecto de modelo do recurso da Loja")
public class LojaDto extends RepresentationModel<LojaDto>{

	@EqualsAndHashCode.Include
	@Schema(name = "Código da loja" , description = "Código do recurso da Loja")
	private Integer codigo;
	
	@Schema(name = "Nome da loja" , description = "Nome do recurso da Loja")
	private String nome;
	
	@Schema(name = "Contacto da loja" , description = "Contacto do recurso da Loja")
	private String contacto;
	
	@Schema(name = "Endereco da loja" , description = "Endereco do recurso da Loja")
	private String endereco;
	
	@Schema(name = "Status da loja" , description = "Status do recurso da Loja -> ativo|inactivado")
	private boolean enabled;
	
	@Schema(name = "isOpen" , description = "isOpen do recurso da Loja -> aberto|fechado")
	private boolean isOpen;
}
