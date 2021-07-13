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

import wine.com.br.domain.dto.CepDto;
import wine.com.br.domain.entity.Cep;
import wine.com.br.repository.CepRepository;
import wine.com.br.service.impl.CepServiceImpl;

@Service
public class CepService implements CepServiceImpl {

	@Autowired
	private CepRepository repository;

	@Override
	public CepDto criarCep(CepDto cepDto) {
		Cep cep = new Cep();

		if (this.validarFaxa(cepDto.getFaixaInicio(), cepDto.getFaixaFim())) {
			BeanUtils.copyProperties(cepDto, cep);

			Optional<Cep> faxa_exist = repository.findByFaixaInicio(cepDto.getFaixaInicio());

			if (!faxa_exist.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,
						"erro! essa faixa de CEP conflita com a faixa de CEP da loja de "
								+ faxa_exist.get().getLoja().getNome());
			}
			BeanUtils.copyProperties(repository.save(cep), cepDto);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"erro! Faixa incorrecta, insira uma faixa válida! Faixa de inicio deve ser menor que a Faixa de fim ");
		}

		return cepDto;
	}

	@Override
	public CepDto actualizarCep(CepDto cepDto) {
		Cep cep = new Cep();
		BeanUtils.copyProperties(cepDto, cep);
		Optional<Cep> cep_exist = repository.findById(cepDto.getCodigo());

		if (cep_exist.isPresent()) {
			Optional<Cep> faxa_exist = repository.findByFaixaInicio(cepDto.getFaixaInicio());

			if (faxa_exist.isPresent()) {
				throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,
						"erro! essa faixa de CEP conflita com a faixa de CEP da loja de "
								+ faxa_exist.get().getLoja().getNome());
			}
			BeanUtils.copyProperties(repository.save(cep), cepDto);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cep informado não existe!");
		}

		return cepDto;
	}

	@Override
	public List<CepDto> listarTodasFaxas() {
		Iterable<Cep> itreable = repository.findAll();

		List<CepDto> ceps = StreamSupport.stream(itreable.spliterator(), false).map(cep -> {
			CepDto dto = new CepDto();
			BeanUtils.copyProperties(cep, dto);
			return dto;
		}).collect(Collectors.toList());

		return ceps;
	}

	@Override
	public List<CepDto> listarFaxasPorLoja(Integer loja) {
		Iterable<Cep> itreable = repository.findByLoja(loja);

		List<CepDto> ceps = StreamSupport.stream(itreable.spliterator(), false).map(cep -> {
			CepDto dto = new CepDto();
			BeanUtils.copyProperties(cep, dto);
			return dto;
		}).collect(Collectors.toList());

		return ceps;
	}

	@Override
	public void excluirFaxaCep(Integer codigo) {
		repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faxa informada não existe"));

		repository.deleteById(codigo);

	}

	public boolean validarFaxa(Long fi, Long ff) {

		if (fi > ff) {
			return false;
		}

		return true;
	}

	@Override
	public CepDto buscarLojaPelaFaixaDoCep(Long faixaInicio, Long faixaFim) {
		
		CepDto dto = new CepDto();
		if (this.validarFaxa(faixaInicio, faixaFim)) {
			Cep cep_model = repository.findLojaByFaixaInicioAndFaixaFim(faixaInicio, faixaFim)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Faxa informada não pertence a nenhuma Loja!"));

			BeanUtils.copyProperties(cep_model, dto);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"erro! Faixa incorrecta, insira uma faixa válida! Faixa de inicio deve ser menor que a Faixa de fim ");
		}

		return dto;
	}

}
