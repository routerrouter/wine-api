package wine.com.br.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import wine.com.br.domain.entity.Cep;

@Repository
public interface CepRepository extends JpaRepository<Cep, Integer> {

	
	@Query("SELECT c FROM Cep c where c.loja.codigo =:loja")
	List<Cep> findByLoja(@Param("loja") Integer loja);

	@Query(value = "SELECT * FROM Cep c where :faixaInicio <= faixa_fim AND  :faixaInicio >= faixa_inicio", nativeQuery = true)
	Optional<Cep> findByFaixaInicio(Long faixaInicio);
	
	@Query(value = "SELECT * FROM Cep c inner join Loja l on l.codigo=c.codigo_loja where :faixaInicio >= c.faixa_inicio AND  :faixaFim <= c.faixa_fim", nativeQuery = true)
	Optional<Cep> findLojaByFaixaInicioAndFaixaFim(Long faixaInicio, Long faixaFim);
	
	Cep findByCodigo(Integer codigo);
	
	
}
