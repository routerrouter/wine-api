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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import wine.com.br.domain.dto.CepDto;
import wine.com.br.exceptions.ApiErrorResponse;
import wine.com.br.services.CepService;

@RestController
@RequestMapping("/api/cep/v1")
public class CepController {

	@Autowired
	private CepService service;

	@Operation(summary = "Buscar todos Ceps", description = "Obter todos os registros do Cep", method = "GET", tags = {
			"Cep" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = CepDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do Cep não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public CollectionModel<CepDto> buscarTodosCeps() {

		List<CepDto> list = service.listarTodasFaxas();
		Link link = linkTo(methodOn(CepController.class).buscarTodosCeps()).withSelfRel();
		return CollectionModel.of(list, link);
	}

	@Operation(summary = "Buscar todos Ceps de uma Loja", description = "Obter todos os registros do Cep de uma loja", method = "GET", tags = {
			"Cep" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = CepDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do Cep não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/loja/{loja}")
	public CollectionModel<CepDto> buscarCepsLoja(
			@Parameter(name = "loja", example = "10", required = true, description = "Loja codigo", in = ParameterIn.PATH) @PathVariable(name = "loja") @Positive Integer loja) {

		List<CepDto> list = service.listarFaxasPorLoja(loja);
		Link link = linkTo(methodOn(CepController.class).buscarTodosCeps()).withSelfRel();
		return CollectionModel.of(list, link);
	}

	@Operation(summary = "Buscar Loja pelo Cep", description = "Obter a loja física que atende determinado CEP", method = "GET", tags = {
			"Cep" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = CepDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do Cep não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/lojaCep/{faixaInicio}/{faixaFim}")
	public EntityModel<CepDto> buscarLoja(
			@PathVariable(name = "faixaInicio") @Positive Long faixaInicio,
			@PathVariable(name = "faixaFim") @Positive Long faixaFim) {

		CepDto dto = service.buscarLojaPelaFaixaDoCep(faixaInicio, faixaFim);

		return EntityModel.of(dto);
	}

	// Http post method - Operação de criar novo
	@Operation(summary = "Criar Cep", description = "Cria o registro do Cep ", method = "POST", tags = { "Cep" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro de Cep criado com sucesso", content = @Content(schema = @Schema(implementation = CepDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<CepDto> createCep(
			@Parameter(name = "CepDto", description = "Cep Dto no corpo de requisição", required = true, content = @Content(schema = @Schema(implementation = CepDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid CepDto CepDto) {

		CepDto dto = service.criarCep(CepDto);
		Link link = linkTo(methodOn(CepController.class).buscarTodosCeps()).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	// Http delete method - delete operation
	@Operation(summary = "Delete Cep", description = "Exclua o registro do Cep", method = "DELETE", tags = { "Cep" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro Cep excluido com sucesso", content = @Content()),
			@ApiResponse(responseCode = "404", description = "Cep informado não existe", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),

			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@DeleteMapping(path = "/{codigo}")
	public void deleteCep(
			@Parameter(name = "codigo", example = "10", required = true, description = "Cep codigo", in = ParameterIn.PATH) @PathVariable(name = "codigo") @Positive Integer codigo) {
		service.excluirFaxaCep(codigo);
	}

	// Http put method - Operação de actualização dos dados do loja
	@Operation(summary = "Editar Cep", description = "Update the Cep record", method = "PUT", tags = { "Cep" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro de Cep actualizado com sucesso", content = @Content(schema = @Schema(implementation = CepDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do Cep não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<CepDto> updateCep(
			@Parameter(name = "CepDto", description = "Cep Dto no corpo de requisição", required = true, content = @Content(schema = @Schema(implementation = CepDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid CepDto lienteDto) {

		CepDto dto = service.actualizarCep(lienteDto);

		Link link = linkTo(methodOn(CepController.class).buscarTodosCeps()).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

}
