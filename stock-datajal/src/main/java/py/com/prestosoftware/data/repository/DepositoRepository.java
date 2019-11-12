package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Deposito;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {

	List<Deposito> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Deposito e")
	Long getMaxId();

}