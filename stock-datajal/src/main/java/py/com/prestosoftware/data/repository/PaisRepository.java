package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {

	List<Pais> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Pais e")
	Long getMaxId();

}