package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
	
	List<Ciudad> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Ciudad e")
	Long getMaxId();

}