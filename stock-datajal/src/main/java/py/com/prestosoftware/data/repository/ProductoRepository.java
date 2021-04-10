package py.com.prestosoftware.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Subgrupo;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	List<Producto> findAllByOrderByDescripcionAsc();
	
	List<Producto> findByDescripcionContaining(String name);
	
	List<Producto> findBySubgrupo(Subgrupo subgrupo); 
	
	List<Producto> findByGrupo(Grupo grupo); 
	
	Optional<Producto> findByReferencia(String referencia);
	//p.id = :filter OR
	@Query(value = "SELECT * FROM productos p WHERE upper(p.descripcion) LIKE %:filter% OR upper(p.referencia) LIKE %:filter% ORDER BY p.id", nativeQuery = true)
	List<Producto> findProductsByFilter(String filter);
	
	@Query(value = "SELECT stock FROM producto_depositos WHERE deposito_id = ?1 AND producto_id = ?2 ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Optional<Producto> getStockByDepositoId(Long depositoId, Long productoId);
	
	@Query(name = "SELECT dep01, dep02, dep03, dep04, dep05 FROM productos WHERE producto_id = ?1", nativeQuery = true)
	Producto getStockById(Long productoId);

	@Query(name = "SELECT precio_a, precio_b, precio_c, precio_d, precio_e FROM productos WHERE producto_id = ?1", nativeQuery = true)
	Producto getPrecioById(Long productoId);
	
	@Query("SELECT coalesce(max(id), 0) FROM Producto e")
	Long getMaxId();
	
	@Query(value = "SELECT * FROM productos p WHERE p.id =:filter OR p.descripcion =:filter OR p.referencia =:filter LIMIT 1", nativeQuery = true)
	Optional<Producto> findByProductByFilter(String filter);

}