package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
	
	List<Venta> findByCajaAndFechaAndSituacionOrderByIdAsc(Caja caja, Date fecha, String situacion);
	
	List<Venta> findByFechaAndSituacionOrderByIdAsc(Date fecha, String situacion);

	List<Venta> findBySituacionOrderByVencimientoDesc(String situacion);
	
	List<Venta> findByClienteAndSituacionOrderByVencimientoDesc(Cliente c, String situacion);
	
	List<Venta> findByClienteNombre(String filter);
	
	List<Venta> findByCliente(Cliente cliente);
	
	List<Venta> findByClienteAndFechaBetween(Cliente cliente, Date fechaIni, Date fechaFin);
	
	List<Venta> findByFechaBetween(Date fechaIni, Date fechaFin);
	
	Optional<Venta> findByIdAndSituacion(Long id, String situacion);

	Optional<Venta> findByIdAndCliente(Long id, Cliente cliente);
	
	@Query("SELECT coalesce(max(id), 0) FROM Venta e")
	Long getMaxId();
	
	@Query(value =  "SELECT v.operacion, v.id, v.fecha, v.cliente_id, v.cliente_nombre, v.vendedor_id, v.deposito_id, vd.precio"
			+ " FROM ventas v JOIN venta_detalles vd ON v.id = vd.venta_id WHERE vd.producto_id = ?1 ORDER BY v.fecha DESC", nativeQuery = true)
	List<ConsultaNota> getVentasByProductoId(Long productoId);
	
	@Query(value =  "SELECT v.operacion, v.id, v.fecha, v.cliente_id, v.cliente_nombre, v.vendedor_id, v.deposito_id, 0 AS precio"
			+ " FROM ventas v WHERE v.fecha = ?1 ORDER BY v.id ASC", nativeQuery = true)
	List<ConsultaNota> getVentasDelDia(Date fecha);
	
	@Query(value =  "SELECT * "
			+ " FROM ventas v WHERE v.fecha between ?1 and ?2 and v.situacion= ?3 and condicion= ?4 ORDER BY v.id ASC", nativeQuery = true)
	List<Venta> getVentasFiltro(Date fechaIni, Date fechaFin, String situacion, int forma);
	
	@Query(value =  "SELECT venta_id, cantidad, precio, producto, producto_id, subtotal, v.id,  iva, descripcion_fiscal "
			+ "	FROM public.venta_detalles v, productos p WHERE p.id=v.producto_id and v.venta_id = ?1 ORDER BY v.id ASC", nativeQuery = true)
	List<Object[]> getVentaDetallesByVentaId(Long ventaId);

}