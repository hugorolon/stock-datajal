package py.com.prestosoftware.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	List<Usuario> findByUsuario(String name);

	Optional<Usuario> findByUsuarioAndClave(String user, String pass);
	
	@Query("SELECT coalesce(max(id), 0) FROM Usuario e")
	Long getMaxId();

}