package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.ProcesoCobroClientes;
import py.com.prestosoftware.data.models.ProcesoCobroVentas;

@Repository
public interface ProcesoCobroVentasRepository extends JpaRepository<ProcesoCobroVentas, Long> {

//	List<ProcesoCobroVentas> findByFechaAndCajaAndSituacionOrderByIdAsc(Date fecha, Caja caja, String situacion);
//
	ProcesoCobroVentas findByPveVenta(Integer id);

	@Query("SELECT coalesce(max(id), 0) FROM ProcesoCobroVentas e")
	Long getMaxId();

//	@Query(value = "SELECT id, SUM(nota_valor) AS nota_valor, SUM(valor_m01) AS valor_m01, SUM(valor_m02) AS valor_m02, "
//			+ "SUM(valor_m03) AS valor_m03, SUM(valor_m04) AS valor_m04, SUM(valor_m05) AS valor_m05 "
//			+ "FROM movimiento_cajas " + "WHERE caja_id = ?1 AND fecha = ?2 AND situacion = ?3", nativeQuery = true)
//	Optional<ProcesoCobroVentas> getTotalsEntradaCaja(Caja caja, Date fecha, String situacion);
//
//	@Query(value = "SELECT  mc "
//			+ "FROM ProcesoCobroVentas mc " + "WHERE mc.notaNro = ?1", nativeQuery = false)
//	Optional <ProcesoCobroVentas> getProcesoCobroVentasPorNota(String nroNota);

}