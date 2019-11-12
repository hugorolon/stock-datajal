package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Impuesto;

@Repository
public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

	List<Impuesto> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Impuesto e")
	Long getMaxId();

}