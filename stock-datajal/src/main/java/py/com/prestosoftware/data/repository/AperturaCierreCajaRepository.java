package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.AperturaCierreCaja;

@Repository
public interface AperturaCierreCajaRepository extends JpaRepository<AperturaCierreCaja, Long> {
	
	Optional<AperturaCierreCaja> findFirstOptionalByCajaIdAndFechaAperturaOrderByFechaAperturaAsc(Long cajaId, Date fechaApertura);
	
	Optional<AperturaCierreCaja> findOptionalByCajaAndFechaApertura(Caja caja, Date fechaApertura);
	
	@Query(value = "SELECT * FROM apertura_cierre_cajas ORDER BY id DESC LIMIT 1 ", nativeQuery = true)
	Optional<AperturaCierreCaja> getUltAperturaCierre();
	
	@Query("SELECT coalesce(max(id), 0) FROM AperturaCierreCaja e")
	Long getMaxId();

}