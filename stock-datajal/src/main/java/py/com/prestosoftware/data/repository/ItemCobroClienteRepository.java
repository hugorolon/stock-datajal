package py.com.prestosoftware.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.ItemCobroCliente;


@Repository
public interface ItemCobroClienteRepository extends JpaRepository<ItemCobroCliente, Integer> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ItemCobroCliente e")
	Long getMaxId();
	
	List<ItemCobroCliente> findByIclNumero(Integer idCab);

//	@Query(value = "SELECT * FROM cuentas_a_recibir car WHERE car.descripcion) LIKE %:filter% OR upper(p.referencia) LIKE %:filter% ORDER BY p.id", nativeQuery = true)
//	List<CuentaARecibir> findProductsByFilter(String filter);
}