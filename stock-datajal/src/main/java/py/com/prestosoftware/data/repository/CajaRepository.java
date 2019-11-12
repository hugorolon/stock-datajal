package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Caja;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

	List<Caja> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Caja e")
	Long getMaxId();

}