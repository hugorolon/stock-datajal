package py.com.prestosoftware.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.ItemPagarProveedor;


@Repository
public interface ItemPagarProveedorRepository extends JpaRepository<ItemPagarProveedor, Integer> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ItemPagarProveedor e")
	Long getMaxId();
	
	List<ItemPagarProveedor> findByIppNumero(Integer idCab);

//	@Query(value = "SELECT * FROM cuentas_a_recibir car WHERE car.descripcion) LIKE %:filter% OR upper(p.referencia) LIKE %:filter% ORDER BY p.id", nativeQuery = true)
//	List<CuentaARecibir> findProductsByFilter(String filter);
}