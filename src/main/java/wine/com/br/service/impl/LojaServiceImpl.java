package wine.com.br.service.impl;

import java.util.List;

import wine.com.br.domain.dto.LojaDto;

public interface LojaServiceImpl {

	public LojaDto criarLoja(LojaDto lojaDto);
	public LojaDto buscarLojaPeloCodigo(Integer codigo);
	public LojaDto actualizarLoja(LojaDto lojaDto);
	public LojaDto buscarLojaPeloNome(String nome);
	public List<LojaDto> listarTodasLojas();
}
