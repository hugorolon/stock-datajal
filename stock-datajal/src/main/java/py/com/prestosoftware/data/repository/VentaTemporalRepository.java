package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.VentaTemporal;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Repository
public interface VentaTemporalRepository extends JpaRepository<VentaTemporal, Long> {
	
	List<VentaTemporal> findByClienteNombre(String filter);
	
	List<VentaTemporal> findByCliente(Cliente cliente);
	
	List<VentaTemporal> findByClienteAndFechaBetween(Cliente cliente, Date fechaIni, Date fechaFin);
	
	List<VentaTemporal> findByFechaBetween(Date fechaIni, Date fechaFin);
	
	Optional<VentaTemporal> findByIdAndSituacion(Long id, String situacion);

	Optional<VentaTemporal> findByIdAndCliente(Long id, Cliente cliente);
	
	@Query("SELECT coalesce(max(id), 0) FROM VentaTemporal e")
	Long getMaxId();
	
	@Query(value =  "SELECT v.operacion, v.id, v.fecha, v.cliente_id, v.cliente_nombre, v.vendedor_id, v.deposito_id, vd.precio"
			+ " FROM ventas_temporales v JOIN venta_detalles_temporales vd ON v.id = vd.venta_id WHERE vd.producto_id = ?1 ORDER BY v.fecha DESC", nativeQuery = true)
	List<ConsultaNota> getVentaTemporalsByProductoId(Long productoId);
	
	@Query(value =  "SELECT v.operacion, v.id, v.fecha, v.cliente_id, v.cliente_nombre, v.vendedor_id, v.deposito_id, 0 AS precio"
			+ " FROM ventas_temporales v WHERE v.fecha = ?1 ORDER BY v.id ASC", nativeQuery = true)
	List<ConsultaNota> getVentaTemporalsDelDia(Date fecha);
	
	@Query(value =  "SELECT venta_id, cantidad, precio, producto, producto_id, subtotal, v.id,  iva, descripcion_fiscal "
			+ "	FROM public.venta_detalles_temporales v, productos p WHERE p.id=v.producto_id and v.venta_id = ?1 ORDER BY v.id ASC", nativeQuery = true)
	List<Object[]> getVentaTemporalDetallesByVentaTemporalId(Long ventaId);

}