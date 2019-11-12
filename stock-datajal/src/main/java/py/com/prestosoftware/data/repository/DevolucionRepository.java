package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Devolucion;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM Devolucion e")
	Long getMaxId();

	Optional<Devolucion> findByIdAndSituacionAndTipo(Long notaId, String situacion, String tipo);

	List<Devolucion> findByFechaAndSituacion(Date fecha, String situacion);

}