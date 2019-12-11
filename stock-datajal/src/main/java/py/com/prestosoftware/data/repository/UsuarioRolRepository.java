package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.UsuarioRol;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

	List<UsuarioRol> findByUsuarioRol(String name);

	
	
	@Query("SELECT coalesce(max(id), 0) FROM UsuarioRol e")
	Long getMaxId();

	@Query(value =  "select CAST(CASE WHEN EXISTS (" + 
					"           SELECT * " + 
					"           FROM usuario_roles ur inner join roles r on ur.id_rol=r.id " + 
					"           WHERE ur.id_usuario=?1 and upper(r.nombre) = ?2 " + 
					"    ) " + 
					"    THEN TRUE " + 
					"    ELSE FALSE " + 
					"    END AS bool) " + 
					"AS hasRole ", nativeQuery=true)
	Boolean hasRole(Long idUsuario, String role);
}