package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.MovimientoIngreso;

@Repository
public interface MovimientoIngresoRepository extends JpaRepository<MovimientoIngreso, Integer> {

	MovimientoIngreso findByMinProceso(Integer id);
//
//	Optional<MovimientoIngreso> findById(Long id);

	@Query("SELECT coalesce(max(id), 0) FROM MovimientoIngreso e")
	Long getMaxId();
	@Query(value =  "SELECT mi "
			+ " FROM MovimientoIngreso mi WHERE mi.fecha = ?1 ORDER BY mi.minNumero DESC", nativeQuery = false)
	List<MovimientoIngreso> getIngresosDelDia(Date fecha);

	@Query(value =  "SELECT mi.min_numero, mi.min_fecha, mi.min_caja, mi.min_documento, mi.min_entidad, sum(mii_monto), min_situacion "
			+ " FROM movimiento_ingresos mi, item_movimientoingresos ie  WHERE ie.mii_numero=mi.min_numero and mi.min_fecha = ?1 group by mi.min_numero ORDER BY mi.min_numero DESC", nativeQuery = true)
	List<Object[]> getIngresosDelDiaObjects(Date fecha);
	
//	@Query(value = "SELECT id, SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
//			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
//			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
//	Optional<MovimientoIngreso> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
//
//	@Query(value = "SELECT  mc "
//			+ "FROM MovimientoIngreso mc " + "WHERE mc.notaNro = ?1", nativeQuery = false)
//	Optional <MovimientoIngreso> getMovimientoIngresoPorNota(String nroNota);

}