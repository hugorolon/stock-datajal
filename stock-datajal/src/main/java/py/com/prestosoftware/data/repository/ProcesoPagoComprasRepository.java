package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.ProcesoCobroVentas;
import py.com.prestosoftware.data.models.ProcesoPagoCompras;

@Repository
public interface ProcesoPagoComprasRepository extends JpaRepository<ProcesoPagoCompras, Long> {

//	List<ProcesoPagoCompras> findByFechaAndCajaAndSituacionOrderByIdAsc(Date fecha, Caja caja, String situacion);
//
//	Optional<ProcesoPagoCompras> findById(Long id);
	ProcesoPagoCompras findByPcoCompraAndPcoProceso(Integer id, Integer idProceso);
	ProcesoPagoCompras findByPcoCompra(Integer id);

	@Query("SELECT coalesce(max(id), 0) FROM ProcesoPagoCompras e")
	Long getMaxId();

//	@Query(value = "SELECT id, SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
//			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
//			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
//	Optional<ProcesoPagoCompras> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
//
//	@Query(value = "SELECT  mc "
//			+ "FROM ProcesoPagoCompras mc " + "WHERE mc.notaNro = ?1", nativeQuery = false)
//	Optional <ProcesoPagoCompras> getProcesoPagoComprasPorNota(String nroNota);

}