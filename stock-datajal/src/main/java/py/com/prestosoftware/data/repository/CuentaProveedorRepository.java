package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.CuentaProveedor;
import py.com.prestosoftware.data.models.Proveedor;

@Repository
public interface CuentaProveedorRepository extends JpaRepository<CuentaProveedor, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM CuentaProveedor e")
	Long getMaxId();
	
	List<CuentaProveedor> findByProveedorAndSituacionAndDebitoIsNullOrderByVencimientoAsc(Proveedor p, String situacion);
	
}