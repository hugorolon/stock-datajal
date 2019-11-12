package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

	List<Marca> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Marca e")
	Long getMaxId();

}