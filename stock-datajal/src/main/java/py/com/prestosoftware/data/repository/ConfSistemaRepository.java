package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.ConfigSistema;

@Repository
public interface ConfSistemaRepository extends JpaRepository<ConfigSistema, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ConfigSistema e")
	Long getMaxId();

}