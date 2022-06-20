package py.com.prestosoftware.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.CuentaARecibir;


@Repository
public interface CuentaARecibirRepository extends JpaRepository<CuentaARecibir, Long> {
	
	CuentaARecibir findByCarProceso(Integer id);
	
	@Query("SELECT coalesce(max(id), 0) FROM CuentaARecibir e")
	Long getMaxId();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.cuenta_a_recibir SET car_situacion=1 WHERE car_numero=?1", nativeQuery = true)
	void cambiaEstadoSituacionInactivo(Long cclNumero);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.cuenta_a_recibir SET car_situacion=0 WHERE car_numero=?1", nativeQuery = true)
	void cambiaEstadoSituacionActivo(Long cclNumero);
	
	//@Query(value = "SELECT c FROM cuenta_clientes c GROUP BY c.cliente", nativeQuery = true)
	//List<CuentaARecibir> findByClienteAndSituacionAndCreditoIsNullOrderByVencimientoAsc(Cliente cliente, String situacion);

//	@Query(value = "SELECT * FROM cuentas_a_recibir car WHERE car.descripcion) LIKE %:filter% OR upper(p.referencia) LIKE %:filter% ORDER BY p.id", nativeQuery = true)
//	List<CuentaARecibir> findProductsByFilter(String filter);
}