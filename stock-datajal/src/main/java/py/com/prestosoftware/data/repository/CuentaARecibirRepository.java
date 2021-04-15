package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.CuentaARecibir;


@Repository
public interface CuentaARecibirRepository extends JpaRepository<CuentaARecibir, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM CuentaARecibir e")
	Long getMaxId();
	
	//@Query(value = "SELECT c FROM cuenta_clientes c GROUP BY c.cliente", nativeQuery = true)
	//List<CuentaARecibir> findByClienteAndSituacionAndCreditoIsNullOrderByVencimientoAsc(Cliente cliente, String situacion);

//	@Query(value = "SELECT * FROM cuentas_a_recibir car WHERE car.descripcion) LIKE %:filter% OR upper(p.referencia) LIKE %:filter% ORDER BY p.id", nativeQuery = true)
//	List<CuentaARecibir> findProductsByFilter(String filter);
}