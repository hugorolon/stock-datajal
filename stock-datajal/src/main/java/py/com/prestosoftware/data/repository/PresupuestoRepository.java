package py.com.prestosoftware.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Presupuesto;;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM Presupuesto e")
	Long getMaxId();

}