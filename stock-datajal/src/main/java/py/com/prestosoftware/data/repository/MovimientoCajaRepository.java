package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.Venta;;

@Repository
public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {

	List<MovimientoCaja> findByFechaAndCajaAndSituacionOrderByIdAsc(Date fecha, Caja caja, String situacion);

	Optional<MovimientoCaja> findById(Long id);
	MovimientoCaja findByNotaNro(String id);
	Optional<MovimientoCaja> findByNotaNroAndTipoOperacion(String id, String mov);
	

	@Query("SELECT coalesce(max(id), 0) FROM MovimientoCaja e")
	Long getMaxId();

	@Query(value = "SELECT id, SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
	Optional<MovimientoCaja> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
	
	@Query(value = "select SUM(VALOR_M01) "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha < ?2 and tipo_operacion = 'E' and situacion='PAGADO'", nativeQuery = true)
	Optional<Double> getTotalsEntradaAnterior(Caja caja, Date fecha);
	
	@Query(value = "select SUM(VALOR_M01) "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha < ?2 and tipo_operacion = 'S' and situacion='PAGADO'", nativeQuery = true)
	Optional<Double> getTotalsSalidaAnterior(Caja caja, Date fecha);
	
	@Query(value = "select SUM(VALOR_M01) "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha >= ?2 and fecha <= ?3 and tipo_operacion = 'E' and situacion='PAGADO'", nativeQuery = true)
	Optional<Double> getTotalsEntrada(Caja caja, Date fechaInicial, Date fechaFinal);
	
	@Query(value = "select SUM(VALOR_M01) "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha >= ?2 and fecha <= ?3 and tipo_operacion = 'S' and situacion='PAGADO'", nativeQuery = true)
	Optional<Double> getTotalsSalida(Caja caja, Date fechaInicial, Date fechaFinal);

	@Query(value = "SELECT  mc "
			+ "FROM MovimientoCaja mc " + "WHERE mc.notaNro = ?1", nativeQuery = false)
	Optional <MovimientoCaja> getMovimientoCajaPorNota(String nroNota);

}