package py.com.prestosoftware.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.CuentaAPagar;
import py.com.prestosoftware.data.models.ProcesoCobroVentas;


@Repository
public interface CuentaAPagarRepository extends JpaRepository<CuentaAPagar, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM CuentaAPagar e")
	Long getMaxId();
	
	CuentaAPagar findByCapProceso(Integer id);
	
	CuentaAPagar findByCapProcesoAndIdEntidad(Integer id,Long idEntidad);
	
	//@Query(value = "SELECT c FROM cuenta_clientes c GROUP BY c.cliente", nativeQuery = true)
	//List<CuentaAPagar> findByClienteAndSituacionAndCreditoIsNullOrderByVencimientoAsc(Cliente cliente, String situacion);

//	@Query(value = "SELECT * FROM cuentas_a_recibir car WHERE car.descripcion) LIKE %:filter% OR upper(p.referencia) LIKE %:filter% ORDER BY p.id", nativeQuery = true)
//	List<CuentaAPagar> findProductsByFilter(String filter);
}