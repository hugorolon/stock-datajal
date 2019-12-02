package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.UsuarioRol;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

	List<UsuarioRol> findByUsuarioRol(String name);

	
	
	@Query("SELECT coalesce(max(id), 0) FROM UsuarioRol e")
	Long getMaxId();

}