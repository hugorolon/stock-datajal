package py.com.prestosoftware.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.MovimientoItemIngreso;

@Repository
public interface MovimientoItemIngresoRepository extends JpaRepository<MovimientoItemIngreso, Long> {

//	List<MovimientoItemIngreso> findByFechaAndCajaAndSituacionOrderByIdAsc(Date fecha, Caja caja, String situacion);
//
	Optional<MovimientoItemIngreso> findById(Long id);

	@Query("SELECT coalesce(max(id), 0) FROM MovimientoItemIngreso e")
	Long getMaxId();

//	@Query(value = "SELECT id, SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
//			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
//			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
//	Optional<MovimientoItemIngreso> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
//
	@Query(value = "SELECT  mc "
			+ "FROM MovimientoItemIngreso mc WHERE mc.miiNumero = ?1", nativeQuery = false)
	List <MovimientoItemIngreso> findByCabId(Integer cabId);

}