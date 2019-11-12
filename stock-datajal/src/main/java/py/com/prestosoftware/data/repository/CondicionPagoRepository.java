package py.com.prestosoftware.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.CondicionPago;

@Repository
public interface CondicionPagoRepository extends JpaRepository<CondicionPago, Long> {
	
	List<CondicionPago> findByNombre(String nombre);
	
	Optional<CondicionPago> findByCantDia(int cantDia);
	
	@Query("SELECT coalesce(max(id), 0) FROM CondicionPago e")
	Long getMaxId();
	
	
	
}