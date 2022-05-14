package py.com.prestosoftware.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.Presupuesto;
import py.com.prestosoftware.data.models.PresupuestoDetalle;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	
	@Query("SELECT coalesce(max(id), 0) FROM Presupuesto e")
	Long getMaxId();

	@Query(value =  "SELECT p "
			+ " FROM Presupuesto p WHERE p.id = ?1 ")
	Presupuesto getPresupuesto(long id);
	
	@Query(value="SELECT cantidad, precio, producto, producto_id, subtotal FROM presupuesto_detalles d WHERE d.presupuesto_id = ?1 ", nativeQuery = true)
	List<Object[]> getPresupuestoDetalleByIdPresupuesto(Long id);
	

}