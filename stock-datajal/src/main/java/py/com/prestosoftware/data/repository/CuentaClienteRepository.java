package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.CuentaCliente;

@Repository
public interface CuentaClienteRepository extends JpaRepository<CuentaCliente, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM CuentaCliente e")
	Long getMaxId();
	
	//@Query(value = "SELECT c FROM cuenta_clientes c GROUP BY c.cliente", nativeQuery = true)
	List<CuentaCliente> findByClienteAndSituacionAndCreditoIsNullOrderByVencimientoAsc(Cliente cliente, String situacion);
	
}