package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Empaque;

@Repository
public interface EmpaqueRepository extends JpaRepository<Empaque, Long> {

	List<Empaque> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Empaque e")
	Long getMaxId();

}