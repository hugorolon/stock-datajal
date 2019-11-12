package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.ConfigCompra;

@Repository
public interface ConfCompraRepository extends JpaRepository<ConfigCompra, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM ConfigCompra e")
	Long getMaxId();

}