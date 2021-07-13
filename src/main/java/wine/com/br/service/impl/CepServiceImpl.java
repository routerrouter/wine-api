package wine.com.br.service.impl;

import java.util.List;

import wine.com.br.domain.dto.CepDto;

public interface CepServiceImpl {

	public CepDto criarCep(CepDto cepDto);
	public CepDto actualizarCep(CepDto cepDto);
	public List<CepDto> listarTodasFaxas();
	public List<CepDto> listarFaxasPorLoja(Integer loja);
	public void excluirFaxaCep(Integer codigo);
	public CepDto buscarLojaPelaFaixaDoCep(Long faixaInicio, Long faixaFim);
}
