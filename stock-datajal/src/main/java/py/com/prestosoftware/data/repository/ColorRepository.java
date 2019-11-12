package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

	List<Color> findByNombre(String name);
	
	@Query("SELECT coalesce(max(id), 0) FROM Color e")
	Long getMaxId();

}