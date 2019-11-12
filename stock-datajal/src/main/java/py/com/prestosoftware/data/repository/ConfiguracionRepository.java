package py.com.prestosoftware.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Usuario;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM Configuracion e")
	Long getMaxId();
	
	Optional<Configuracion> findByUsuarioId(Usuario usuarioId);
	
	Optional<Configuracion> findByEmpresaId(Empresa empresaId);

}