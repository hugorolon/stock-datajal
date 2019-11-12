package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	List<Grupo> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Grupo e")
	Long getMaxId();

}