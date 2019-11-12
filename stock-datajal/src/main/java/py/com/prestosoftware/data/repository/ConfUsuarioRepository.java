package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.ConfigUsuario;

@Repository
public interface ConfUsuarioRepository extends JpaRepository<ConfigUsuario, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ConfigUsuario e")
	Long getMaxId();

}