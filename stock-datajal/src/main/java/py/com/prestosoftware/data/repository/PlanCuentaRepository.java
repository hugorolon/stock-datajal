package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.PlanCuenta;

@Repository
public interface PlanCuentaRepository extends JpaRepository<PlanCuenta, Long> {

	List<PlanCuenta> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM PlanCuenta e")
	Long getMaxId();

}