package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	List<Empresa> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Empresa e")
	Long getMaxId();

}