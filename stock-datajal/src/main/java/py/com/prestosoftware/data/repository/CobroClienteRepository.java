package py.com.prestosoftware.data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import py.com.prestosoftware.data.models.CobroCliente;

@Repository
public interface CobroClienteRepository extends JpaRepository<CobroCliente, Integer> {

	@Query("SELECT e FROM CobroCliente e where e.fecha =?1")
	List<CobroCliente> findByFecha(Date fecha);
	
	@Query("SELECT coalesce(max(id), 0) FROM CobroCliente e")
	Integer getMaxId();
	
	@Query(value =  "SELECT car_numero , car.car_fecha as car_fecha1, v.comprobante as car_boleta, car.car_entidad, cli.nombre,  car.car_monto as car_monto1, \n"
			+ "car_proceso, ica_vencimiento as ica_vencimiento1, \n"
			+ "ica_monto as ica_monto1, ica_documento \n"
			+ ",coalesce((select SUM(icl_monto) FROM item_cobro_clientes where icl_secuencia_cuenta = icr.ica_secuencia), 0) AS pagado,\n"
			+ "('Venta # '|| car_proceso) AS nombre_ingreso, ica_secuencia  \n"
			+ " FROM cuenta_a_recibir car, item_cuenta_a_recibir ICR, clientes cli, ventas v \n"
			+ "	  WHERE car_numero=ica_cuenta AND car_situacion=0  and ica_situacion = 0 and car.car_entidad=cli.id \n"
			+ " and car_monto > coalesce((select SUM(icl_monto) FROM item_cobro_clientes where icl_secuencia_cuenta = icr.ica_secuencia),0) \n"
			+ "		and v.id=car_proceso and v.situacion != 'ANULADO' and car_entidad=?1 \n"
			+ "ORDER BY 1,8 asc", nativeQuery = true)
	List<Object[]> getDetalleCobroClienteView(Long clienteId);
	
	@Query(value =  "SELECT car_numero , car.car_fecha as car_fecha1, car.car_boleta, car.car_entidad, cli.nombre,  car.car_monto as car_monto1, \n"
			+ "car_proceso, ica_vencimiento as ica_vencimiento1, \n"
			+ "ica_monto as ica_monto1, ica_documento \n"
			+ ",coalesce((select SUM(icl_monto) FROM item_cobro_clientes where icl_secuencia_cuenta = icr.ica_secuencia), 0) AS pagado,\n"
			+ "('Venta # '|| car_proceso) AS nombre_ingreso, ica_secuencia,  icl.icl_monto \n"
			+ " FROM cuenta_a_recibir car, item_cuenta_a_recibir ICR, clientes cli, public.cobro_clientes cc, public.item_cobro_clientes icl \n"
			+ "	  WHERE car_numero=ica_cuenta and car.car_entidad=cli.id \n"
			+ "	and ccl_numero=icl.icl_numero and icl.icl_secuencia_cuenta=ica_secuencia and car_entidad=?1 and ccl_numero=?2 \n"
			+ "ORDER BY 1,8 asc", nativeQuery = true)
	List<Object[]> getClienteCobradoView(Long clienteId, Integer idCobro);
}