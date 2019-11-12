package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Ncm;

@Repository
public interface NcmRepository extends JpaRepository<Ncm, Long> {

	List<Ncm> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Ncm e")
	Long getMaxId();

}