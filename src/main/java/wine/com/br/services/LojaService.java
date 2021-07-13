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

import wine.com.br.domain.dto.LojaDto;
import wine.com.br.domain.entity.Loja;
import wine.com.br.repository.LojaRepository;
import wine.com.br.service.impl.LojaServiceImpl;

@Service
public class LojaService implements LojaServiceImpl{

	@Autowired
	private LojaRepository repository;
	
	@Override
	public LojaDto criarLoja(LojaDto lojaDto) {
		Loja loja = new Loja();
		BeanUtils.copyProperties(lojaDto, loja);
		Optional<Loja> existente = repository.findByNome(lojaDto.getNome());
		if (existente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,
					"Já existe uma loja com este nome!");
		}
		
		BeanUtils.copyProperties(repository.save(loja), lojaDto);

		return lojaDto;
	}

	@Override
	public LojaDto buscarLojaPeloCodigo(Integer codigo) {
		Loja loja = repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loja não informada existe."));
		LojaDto dto = new LojaDto();
		BeanUtils.copyProperties(loja, dto);

		return dto;
	}

	@Override
	public LojaDto actualizarLoja(LojaDto lojaDto) {
		Loja loja = repository.findById(lojaDto.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loja informada não existe."));
		
		BeanUtils.copyProperties(lojaDto, loja);
		loja = repository.save(loja);
		BeanUtils.copyProperties(loja, lojaDto);
		return lojaDto;
	}

	@Override
	public LojaDto buscarLojaPeloNome(String nome) {
		Loja loja = repository.findByNome(nome)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe uma loja com este nome"));
		LojaDto dto = new LojaDto();
		BeanUtils.copyProperties(loja, dto);

		return dto;
	}

	@Override
	public List<LojaDto> listarTodasLojas() {
		Iterable<Loja> itreable = repository.findAll();

		List<LojaDto> lojas = StreamSupport.stream(itreable.spliterator(), false).map(loja -> {
			LojaDto dto = new LojaDto();
			BeanUtils.copyProperties(loja, dto);
			return dto;
		}).collect(Collectors.toList());

		return lojas;
	}

}
