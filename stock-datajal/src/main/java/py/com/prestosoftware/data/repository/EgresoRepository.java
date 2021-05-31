package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Egreso;

@Repository
public interface EgresoRepository extends JpaRepository<Egreso, Long> {

	//List<Egreso> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Ingreso e")
	Long getMaxId();

}