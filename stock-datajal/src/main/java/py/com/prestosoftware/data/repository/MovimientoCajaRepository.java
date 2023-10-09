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

	@Query(value = "SELECT  SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
	Optional<Object[]> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
	
	@Query(value = "select max(min_fecha) "
			+ "			 FROM public.movimiento_ingresos "
			+ "			  where  min_fecha < date(?1) ", nativeQuery = true)
	Date findLastDateMov(Date fecha);
	
	@Query(value = "select coalesce(sum(total_general - total_descuento),0) AS INGRESO "
			+ "			 FROM public.ventas v  "
			+ "			 where v.condicion=1 and situacion<>'ANULADO' "
			+ "			  and fecha= date(?1) ", nativeQuery = true)
	Optional<Double> getTotalsVentaContado(Date fecha);

	@Query(value = "select coalesce(sum(MII_MONTO),0) AS INGRESO "
			+ "			 FROM public.movimiento_ingresos , item_movimientoingresos imi "
			+ "			 where min_numero=mii_numero and min_caja= ?1 and min_situacion=0 "
			+ "			 and (mii_ingreso <> 1 and mii_ingreso <> 11 and  mii_ingreso <> 30) "
			+ "			  and min_fecha= date(?2) ", nativeQuery = true)
	Optional<Double> getTotalsOtrosIngresos(Caja caja, Date fecha);
	
	
	@Query(value = "select coalesce(sum(total_general),0) AS INGRESO "
			+ "			 FROM public.compras c  "
			+ "			 where c.condicion=1 and situacion<>'ANULADO' "
			+ " and fecha = ?1 ", nativeQuery = true)
	Optional<Double> getTotalsCompraContado(Date fecha);
	
	@Query(value = "SELECT  coalesce(sum(MIE_MONTO),0) AS EGRESO "
			+ "			 FROM public.movimiento_Egresos , item_movimientoEgresos imi where meg_numero=mie_numero "
			+ "			 and (mie_egreso <> 1 and mie_egreso<>4 and mie_egreso<>31) "
			+ "and meg_caja=?1 and meg_situacion=0 and meg_fecha= ?2 ", nativeQuery = true)
	Optional<Double> getTotalsOtrosEgresos(Caja caja, Date fecha);
	
	@Query(value = "select SUM(VALOR_M01) "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha >= ?2 and fecha <= ?3 and tipo_operacion = 'E' and situacion='PAGADO'", nativeQuery = true)
	Optional<Double> getTotalsEntrada(Caja caja, Date fechaInicial, Date fechaFinal);
	
	@Query(value = "select SUM(VALOR_M01) "
			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha >= ?2 and fecha <= ?3 and tipo_operacion = 'S' and situacion='PAGADO'", nativeQuery = true)
	Optional<Double> getTotalsSalida(Caja caja, Date fechaInicial, Date fechaFinal);

	@Query(value = "SELECT  mc "
			+ "FROM MovimientoCaja mc " + "WHERE mc.notaNro = ?1", nativeQuery = false)
	Optional <MovimientoCaja> getMovimientoCajaPorNota(String nroNota);
	
//	@Query(value = "SELECT  mc "
//			+ "FROM Movimiento_Cajas mc " + "WHERE mc.fecha = ?1 and mc.caja_id= ?2 and (mc.situacion = ?3 or mc.situacion = ?4)", nativeQuery = false)
//	List<MovimientoCaja> getMovimientosLanzamiento(Date fecha, Long caja, String situacion, String situacion2);

}