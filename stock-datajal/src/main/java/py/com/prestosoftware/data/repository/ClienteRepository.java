package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findAllByOrderByNombreAsc();
	
	List<Cliente> findByNombreContaining(String name);
	
	Cliente findByCiruc(String ciruc);
	
	List<Cliente> findAllByOrderByIdAsc();
	
	
	@Query("SELECT coalesce(max(id), 0) FROM Cliente e")
	Long getMaxId();
	
}