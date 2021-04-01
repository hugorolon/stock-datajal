package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.ClientePais;

@Repository
public interface ClientePaisRepository extends JpaRepository<ClientePais, Long> {

	List<ClientePais> findByNombreContaining(String name);
	
	ClientePais findByCiruc(String ciruc);
	
	@Query("SELECT coalesce(max(id), 0) FROM ClientePais e")
	Long getMaxId();
	
}