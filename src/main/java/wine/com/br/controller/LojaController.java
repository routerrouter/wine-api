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
import wine.com.br.domain.dto.LojaDto;
import wine.com.br.exceptions.ApiErrorResponse;
import wine.com.br.services.LojaService;

@RestController
@RequestMapping("/api/loja/v1")
public class LojaController {

	@Autowired
	private LojaService service;

	// Http GET method - Operação de leitura

	@Operation(summary = "Buscar Loja", description = "Buscar o cadastro do Loja pelo código do loja ", method = "GET", tags = {
			"Loja" })
	@Parameter(name = "codigo", example = "1", required = true, description = "Loja codigo", in = ParameterIn.PATH)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LojaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do loja não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<LojaDto> buscarLoja(@PathVariable(name = "codigo") @Positive Integer codigo) {

		Link link = linkTo(methodOn(LojaController.class).buscarLoja(codigo)).withSelfRel();
		LojaDto dto = service.buscarLojaPeloCodigo(codigo);
		dto.add(link);
		Link link_ceps = linkTo(methodOn(CepController.class).buscarCepsLoja(dto.getCodigo())).withRel("Ceps");
		dto.add(link_ceps);

		return EntityModel.of(dto);
	}

	@Operation(summary = "Buscar todos Lojas", description = "Obter todos os registros do Loja", method = "GET", tags = {
			"Loja" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LojaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do loja não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public CollectionModel<LojaDto> buscarTodosLojas() {

		List<LojaDto> list = service.listarTodasLojas();
		for (LojaDto dto : list) {
			Link link = linkTo(methodOn(LojaController.class).buscarLoja(dto.getCodigo())).withSelfRel();
			dto.add(link);
			Link link_ceps = linkTo(methodOn(CepController.class).buscarCepsLoja(dto.getCodigo())).withRel("Ceps");
			dto.add(link_ceps);
		}
		Link link = linkTo(methodOn(LojaController.class).buscarTodosLojas()).withSelfRel();
		return CollectionModel.of(list, link);
	}

	@Operation(summary = "Buscar Loja", description = "Buscar o registro do Loja pelo nome do loja ", method = "GET", tags = {
			"Loja" })
	@Parameter(name = "nome", example = "nome loja", required = true, description = "Nome do Loja", in = ParameterIn.PATH)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LojaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do loja não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/lojaData/{nome}")
	public EntityModel<LojaDto> getLojaByLojaNumber(@PathVariable(name = "nome") String nome) {
		LojaDto dto = service.buscarLojaPeloNome(nome);
		Link link = linkTo(methodOn(LojaController.class).getLojaByLojaNumber(nome)).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	// Http post method - Operação de criar novo
	@Operation(summary = "Criar Loja", description = "Crie o registro do Loja ", method = "POST", tags = {
			"Loja" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro de loja criado com sucesso", content = @Content(schema = @Schema(implementation = LojaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<LojaDto> createLoja(
			@Parameter(name = "lojaDto", description = "Loja Dto no corpo de requisição", required = true, content = @Content(schema = @Schema(implementation = LojaDto.class)), in = ParameterIn.DEFAULT) 
			@RequestBody @Valid LojaDto lojaDto) {

		LojaDto dto = service.criarLoja(lojaDto);
		Link link = linkTo(methodOn(LojaController.class).buscarLoja(dto.getCodigo())).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	// Http put method - Operação de actualização dos dados do loja
	@Operation(summary = "Update Loja", description = "Update the Loja record", method = "PUT", tags = {
			"Loja" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro de loja actualizado com sucesso", content = @Content(schema = @Schema(implementation = LojaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do loja não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<LojaDto> updateLoja(
			@Parameter(name = "lojaDto", description = "Loja Dto no corpo de requisição", required = true, content = @Content(schema = @Schema(implementation = LojaDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid LojaDto lienteDto) {

		LojaDto dto = service.actualizarLoja(lienteDto);

		Link link = linkTo(methodOn(LojaController.class).buscarLoja(dto.getCodigo())).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

}
