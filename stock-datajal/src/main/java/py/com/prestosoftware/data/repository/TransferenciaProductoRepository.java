package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.TransferenciaProducto;

@Repository
public interface TransferenciaProductoRepository extends JpaRepository<TransferenciaProducto, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM TransferenciaProducto e")
	Long getMaxId();

}