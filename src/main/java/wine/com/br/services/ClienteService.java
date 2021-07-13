package wine.com.br.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import wine.com.br.domain.dto.ClienteDto;
import wine.com.br.domain.entity.Cliente;
import wine.com.br.repository.ClienteRepository;
import wine.com.br.service.impl.ClienteServiceImpl;

@Service
public class ClienteService implements ClienteServiceImpl {

	@Autowired
	private ClienteRepository repository;

	@Override
	public ClienteDto criarCliente(ClienteDto clienteDto) {
		Cliente cliente = new Cliente();
		BeanUtils.copyProperties(clienteDto, cliente);
		Optional<Cliente> existente = repository.findByNome(clienteDto.getNome());
		if (existente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,
					"Já existe um cliente com este nome!");
		}
		
		BeanUtils.copyProperties(repository.save(cliente), clienteDto);

		return clienteDto;
	}

	@Override
	public ClienteDto buscarClientePeloCodigo(Integer codigo) {
		Cliente cliente = repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente Não existe."));

		ClienteDto dto = new ClienteDto();
		BeanUtils.copyProperties(cliente, dto);

		return dto;
	}

	@Override
	public ClienteDto actualizarCliente(ClienteDto clienteDto) {
		Cliente cliente = repository.findById(clienteDto.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente Não existe."));
		BeanUtils.copyProperties(clienteDto, cliente);

		cliente = repository.save(cliente);
		BeanUtils.copyProperties(cliente, clienteDto);

		return clienteDto;
	}

	@Override
	public List<ClienteDto> listarTodosClientes() {
		Iterable<Cliente> itreable = repository.findAll();

		List<ClienteDto> clientes = StreamSupport.stream(itreable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());

		return clientes;
	}

	@Override
	public ClienteDto buscarClientePeloNome(String nome) {
		Cliente cliente = repository.findByNome(nome)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe um cliente com este nome!"));

		ClienteDto dto = new ClienteDto();
		BeanUtils.copyProperties(cliente, dto);

		return dto;
	}

}
