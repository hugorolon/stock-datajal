package py.com.prestosoftware.data.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.ProcesoPagoProveedores;

@Repository
public interface ProcesoPagoProveedoresRepository extends JpaRepository<ProcesoPagoProveedores, Long> {

//	List<ProcesoPagoProveedores> findByFechaAndCajaAndSituacionOrderByIdAsc(Date fecha, Caja caja, String situacion);
//
	ProcesoPagoProveedores findByPppPago(Integer id);

	@Query("SELECT coalesce(max(id), 0) FROM ProcesoPagoProveedores e")
	Long getMaxId();

//	@Query(value = "SELECT id, SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
//			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
//			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
//	Optional<ProcesoPagoProveedores> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
//
//	@Query(value = "SELECT  mc "
//			+ "FROM ProcesoPagoProveedores mc " + "WHERE mc.notaNro = ?1", nativeQuery = false)
//	Optional <ProcesoPagoProveedores> getProcesoPagoProveedoresPorNota(String nroNota);

}