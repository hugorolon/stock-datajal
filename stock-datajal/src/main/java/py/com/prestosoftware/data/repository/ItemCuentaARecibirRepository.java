package py.com.prestosoftware.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ItemCuentaARecibir;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;


@Repository
public interface ItemCuentaARecibirRepository extends JpaRepository<ItemCuentaARecibir, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ItemCuentaARecibir e")
	Long getMaxId();
	
	//@Query(value = "SELECT c FROM cuenta_clientes c GROUP BY c.cliente", nativeQuery = true)
	//List<CuentaARecibir> findByClienteAndSituacionAndCreditoIsNullOrderByVencimientoAsc(Cliente cliente, String situacion);

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.item_cuenta_a_recibir SET ica_situacion=1 WHERE ica_secuencia=?1", nativeQuery = true)
	void cambiaEstadoSituacionInactivo(Long icaSecuencia);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.item_cuenta_a_recibir SET ica_situacion=0 WHERE ica_secuencia=?1", nativeQuery = true)
	void cambiaEstadoSituacionActivo(Long icaSecuencia);
	
	@Query(value = "SELECT  mc "
			+ "FROM ItemCuentaARecibir mc WHERE mc.icaCuenta = ?1", nativeQuery = false)
	List <ItemCuentaARecibir> findByCabId(Integer cabId);
}