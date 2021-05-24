package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.TransformacionProducto;

@Repository
public interface TransformacionProductoRepository extends JpaRepository<TransformacionProducto, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM TransformacionProducto e")
	Long getMaxId();

}