package py.com.prestosoftware.data.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Lotes;

@Repository
public interface LoteRepository extends JpaRepository<Lotes, Long> {

	//List<Lotes> findByFilter(Date fecha, Long idProducto);
	
	@Query(value = "SELECT * FROM Lotes p WHERE p.id_Producto = ?1 ORDER BY p.id desc", nativeQuery = true)
	List<Lotes> findLotesByProductoId(Long productoId);
	
	@Query("SELECT coalesce(max(id), 0) FROM Lotes e")
	Long getMaxId();

}