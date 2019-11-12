package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.AjusteStock;

@Repository
public interface AjusteStockRepository extends JpaRepository<AjusteStock, Long> {

	@Query("SELECT coalesce(max(id), 0) FROM AjusteStock e")
	Long getMaxId();
	
}