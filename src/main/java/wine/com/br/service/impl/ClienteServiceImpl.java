package wine.com.br.service.impl;

import java.util.List;

import wine.com.br.domain.dto.ClienteDto;

public interface ClienteServiceImpl {

	public ClienteDto criarCliente(ClienteDto clienteDto);

	public ClienteDto buscarClientePeloCodigo(Integer codigo);

	public ClienteDto actualizarCliente(ClienteDto clienteDto);

	public List<ClienteDto> listarTodosClientes();

	public ClienteDto buscarClientePeloNome(String nome);
}
