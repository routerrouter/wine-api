package wine.com.br.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wine.com.br.domain.entity.Loja;

@Repository
public interface LojaRepository  extends JpaRepository<Loja, Integer>{
	
	
	Optional<Loja> findByNome(String nome);
	
	Loja findNomeByCodigo(Integer nome);

}
