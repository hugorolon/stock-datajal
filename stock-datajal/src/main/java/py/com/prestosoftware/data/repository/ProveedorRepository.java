package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
	
	List<Proveedor> findByNombreContaining(String nombre);
	
	List<Proveedor> findAllByOrderByIdAsc();
	
	
	@Query("SELECT coalesce(max(id), 0) FROM Proveedor e")
	Long getMaxId();

}