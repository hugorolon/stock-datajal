package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Ingreso;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

	//List<Ingreso> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Ingreso e")
	Long getMaxId();

}