package py.com.prestosoftware.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ItemCuentaAPagar;


@Repository
public interface ItemCuentaAPagarRepository extends JpaRepository<ItemCuentaAPagar, Integer> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ItemCuentaAPagar e")
	Long getMaxId();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.item_cuenta_a_pagar SET icp_situacion=1 WHERE icp_secuencia=?1", nativeQuery = true)
	void cambiaEstadoSituacionInactivo(Long icaSecuencia);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.item_cuenta_a_pagar SET icp_situacion=0 WHERE icp_secuencia=?1", nativeQuery = true)
	void cambiaEstadoSituacionActivo(Long icaSecuencia);
	
	@Query(value = "SELECT  mc "
			+ "FROM ItemCuentaAPagar mc WHERE mc.icpCuenta = ?1", nativeQuery = false)
	List <ItemCuentaAPagar> findByCabId(Integer cabId);
}