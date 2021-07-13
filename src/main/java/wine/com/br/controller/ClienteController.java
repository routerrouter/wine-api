package wine.com.br.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import wine.com.br.domain.dto.ClienteDto;
import wine.com.br.exceptions.ApiErrorResponse;
import wine.com.br.services.ClienteService;

@RestController
@RequestMapping("/api/cliente/v1")
public class ClienteController {

	@Autowired
	private ClienteService service;

	// Http GET method - Operação de leitura

	@Operation(summary = "Buscar Cliente", description = "Buscar o cadastro do Cliente pelo código do cliente ", method = "GET", tags = {
			"Cliente" })
	@Parameter(name = "codigo", example = "1", required = true, description = "Cliente codigo", in = ParameterIn.PATH)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do cliente não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<ClienteDto> buscarCliente(@PathVariable(name = "codigo") @Positive Integer codigo) {

		Link link = linkTo(methodOn(ClienteController.class).buscarCliente(codigo)).withSelfRel();
		ClienteDto dto = service.buscarClientePeloCodigo(codigo);
		dto.add(link);

		return EntityModel.of(dto);
	}

	@Operation(summary = "Buscar todos Clientes", description = "Obter todos os registros do Cliente", method = "GET", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do cliente não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public CollectionModel<ClienteDto> buscarTodosClientes() {

		List<ClienteDto> list = service.listarTodosClientes();
		for (ClienteDto dto : list) {
			Link link = linkTo(methodOn(ClienteController.class).buscarCliente(dto.getCodigo())).withSelfRel();
			dto.add(link);
		}
		Link link = linkTo(methodOn(ClienteController.class).buscarTodosClientes()).withSelfRel();
		return CollectionModel.of(list, link);
	}

	@Operation(summary = "Buscar Cliente", description = "Buscar o registro do Cliente pelo nome do cliente ", method = "GET", tags = {
			"Cliente" })
	@Parameter(name = "nome", example = "nome cliente", required = true, description = "Nome do Cliente", in = ParameterIn.PATH)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do cliente não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/clienteData/{nome}")
	public EntityModel<ClienteDto> getClienteByClienteNumber(@PathVariable(name = "nome") String nome) {
		ClienteDto dto = service.buscarClientePeloNome(nome);
		Link link = linkTo(methodOn(ClienteController.class).getClienteByClienteNumber(nome)).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	// Http post method - Operação de criar novo
	@Operation(summary = "Criar Cliente", description = "Crie o registro do Cliente ", method = "POST", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro de cliente criado com sucesso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<ClienteDto> createCliente(
			@Parameter(name = "clienteDto", description = "Cliente Dto no corpo de requisição", required = true, content = @Content(schema = @Schema(implementation = ClienteDto.class)), in = ParameterIn.DEFAULT) 
			@RequestBody @Valid ClienteDto clienteDto) {

		ClienteDto dto = service.criarCliente(clienteDto);
		Link link = linkTo(methodOn(ClienteController.class).buscarCliente(dto.getCodigo())).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	// Http put method - Operação de actualização dos dados do cliente
	@Operation(summary = "Update Cliente", description = "Update the Cliente record", method = "PUT", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro de cliente actualizado com sucesso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do cliente não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<ClienteDto> updateCliente(
			@Parameter(name = "clienteDto", description = "Cliente Dto no corpo de requisição", required = true, content = @Content(schema = @Schema(implementation = ClienteDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid ClienteDto lienteDto) {

		ClienteDto dto = service.actualizarCliente(lienteDto);

		Link link = linkTo(methodOn(ClienteController.class).buscarCliente(dto.getCodigo())).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

}
