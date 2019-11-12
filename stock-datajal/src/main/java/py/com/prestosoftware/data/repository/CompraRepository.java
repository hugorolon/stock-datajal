package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.Proveedor;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
	
	List<Compra> findByCajaAndFechaAndSituacionOrderByIdAsc(Caja caja, Date fecha, String situacion);
	
	List<Compra> findBySituacionOrderByIdAsc(String situacion);
	
	List<Compra> findByProveedorNombre(String filter);
	
	List<Compra> findByFechaAndSituacionOrderByIdAsc(Date fecha, String situacion);
	
	List<Compra> findByProveedorAndSituacionOrderByIdAsc(Proveedor p, String situacion);
	
	List<Compra> findByProveedor(Proveedor proveedor);
	
	Optional<Compra> findByIdAndSituacion(Long id, String situacion);
	
	Optional<Compra> findByProveedorAndFactura(Proveedor proveedor, String factura);
	
	Optional<Compra> findByIdAndProveedor(Long id, Proveedor proveedor);
	
	@Query("SELECT coalesce(max(id), 0) FROM Compra c")
	Long getMaxId();

}