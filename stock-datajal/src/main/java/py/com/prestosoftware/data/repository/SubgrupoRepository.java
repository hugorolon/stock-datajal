package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Subgrupo;

@Repository
public interface SubgrupoRepository extends JpaRepository<Subgrupo, Long> {

	List<Subgrupo> findByNombre(String name);
	
	List<Subgrupo> findByGrupo(Grupo grupo);
	
	@Query("SELECT coalesce(max(id), 0) FROM Subgrupo e")
	Long getMaxId();

}