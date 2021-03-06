package py.com.prestosoftware.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.ListaPrecio;

@Repository
public interface ListaPrecioRepository extends JpaRepository<ListaPrecio, Long> {

	List<ListaPrecio> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM ListaPrecio e")
	Long getMaxId();

}