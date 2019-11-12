package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Cotizacion;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
	
	List<Cotizacion> findByFecha(Date fecha);

	@Query(value = "SELECT * FROM cotizaciones WHERE moneda_id = ?1 AND fecha = ?2 ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Optional<Cotizacion> getCotizacionByMonedaAndId(Long monedaId, String fecha);
	
	@Query(value = "SELECT * FROM cotizaciones WHERE moneda_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Optional<Cotizacion> getCotizacionByMoneda(Long monedaId);
	
	@Query("SELECT coalesce(max(id), 0) FROM Cotizacion e")
	Long getMaxId();

}