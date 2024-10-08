package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

	List<Rol> findByNombre(String nombre);
	
	@Query("SELECT coalesce(max(id), 0) FROM Rol e")
	Long getMaxId();
	
}