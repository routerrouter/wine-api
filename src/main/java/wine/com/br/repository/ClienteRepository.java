package wine.com.br.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wine.com.br.domain.entity.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer>{
	
	
	Optional<Cliente> findByNome(String nome);

}
