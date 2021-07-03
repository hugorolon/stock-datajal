package py.com.prestosoftware.data.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.PagarProveedor;

@Repository
public interface PagarProveedorRepository extends JpaRepository<PagarProveedor, Integer> {

	@Query("SELECT e FROM PagarProveedor e where e.fecha =?1")
	List<PagarProveedor> findByFecha(Date fecha);
	
	@Query("SELECT coalesce(max(id), 0) FROM PagarProveedor e")
	Integer getMaxId();
	
	@Query(value =  "SELECT cap_numero , cap.cap_fecha as cap_fecha1, cap.cap_boleta, cap.id_proveedor as cap_entidad, pro.nombre,  cap.cap_monto as cap_monto1, cap_proceso, icp_vencimiento as icp_vencimiento1, icp_monto as icp_monto1, icp_documento, coalesce((select SUM(ipp_monto) FROM item_pago_proveedores where ipp_secuencia_cuenta = icp.icp_secuencia), 0) AS pagado, ('Compra # '|| cap_proceso) AS nombre_Egreso, icp_secuencia  \n"
			+ "FROM cuenta_a_pagar cap, item_cuenta_a_pagar icp, proveedores pro \n"
			+ "WHERE cap_numero=icp_cuenta and id_proveedor=pro.id AND cap_situacion=0 AND icp_situacion= 0 AND id_proveedor=?1 "
			+ "ORDER BY 1,8 asc", nativeQuery = true)
	List<Object[]> getDetallePagarProveedorView(Long clienteId);
	
	@Query(value =  "SELECT cap_numero , cap.cap_fecha as cap_fecha1, cap.cap_boleta, cap.id_proveedor, pro.nombre,  cap.cap_monto as cap_monto1, \n"
			+ "		 cap_proceso, icp_vencimiento as icp_vencimiento1, \n"
			+ "		 icp_monto as icp_monto1, icp_documento \n"
			+ "		 ,coalesce((select SUM(ipp_monto) FROM item_pago_proveedores where ipp_secuencia_cuenta = icp.icp_secuencia), 0) AS pagado,\n"
			+ "		 ('Compra # '|| cap_proceso) AS nombre_egreso, icp_secuencia,  ipp.ipp_monto \n"
			+ "		 FROM cuenta_a_pagar cap, item_cuenta_a_pagar icp, proveedores pro, public.pago_proveedores pp, public.item_pago_proveedores ipp \n"
			+ "		 WHERE cap_numero=icp_cuenta and cap.id_proveedor=pro.id \n"
			+ "		 and ppr_numero=ipp.ipp_numero and ipp.ipp_secuencia_cuenta=icp_secuencia \n"
			+ "		 and cap.id_proveedor=?1 and ppr_numero=?2 \n"
			+ "		 ORDER BY 1,8 asc", nativeQuery = true)
	List<Object[]> getProveedorPagadoView(Long clienteId, Integer idCobro);
}