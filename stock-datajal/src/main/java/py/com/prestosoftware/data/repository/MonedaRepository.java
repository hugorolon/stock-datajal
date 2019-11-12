package py.com.prestosoftware.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.prestosoftware.data.models.Moneda;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda, Long> {

	List<Moneda> findByNombre(String name);

	Optional<Moneda> findByEsBase(int esBase);
	
	@Query("SELECT coalesce(max(id), 0) FROM Moneda e")
	Long getMaxId();

}