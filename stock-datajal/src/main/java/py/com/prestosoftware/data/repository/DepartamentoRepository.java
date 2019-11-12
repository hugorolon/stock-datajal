package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

	List<Departamento> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Departamento e")
	Long getMaxId();

}