package py.com.prestosoftware.domain.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.VentaTemporal;
import py.com.prestosoftware.data.repository.ClienteRepository;
import py.com.prestosoftware.data.repository.VentaTemporalRepository;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Service
public class VentaTemporalService {

	private VentaTemporalRepository repository;
	private ClienteRepository repositoryCliente;
	@Autowired
	public VentaTemporalService(VentaTemporalRepository repository, ClienteRepository repositoryCliente) {
		this.repository = repository;
		this.repositoryCliente = repositoryCliente;
	}

	public List<VentaTemporal> findAll() {
		return repository.findAll();
	}

	public List<VentaTemporal> findByFilter(String filter) {
		return repository.findByClienteNombre(filter);
	}

	public List<VentaTemporal> getNotasPorClienteAndFecha(Cliente cliente, Date fechaIni, Date fechaFin) {
		return repository.findByClienteAndFechaBetween(cliente, fechaIni, fechaFin);
	}

	public List<VentaTemporal> getNotasPorFechas(Date fechaIni, Date fechaFin) {
		return repository.findByFechaBetween(fechaIni, fechaFin);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public VentaTemporal save(VentaTemporal venta, Cliente clienteNuevo, String condicion) throws RuntimeException{
		VentaTemporal v=new VentaTemporal();
			if (clienteNuevo != null && clienteNuevo.getId()!=null && clienteNuevo.getId().intValue() == Long.valueOf(999999).intValue()) {
				clienteNuevo.setId(null);
				Cliente n = this.repositoryCliente.save(clienteNuevo);
				venta.setCliente(n);
			}
			v = repository.save(venta);
			
			return v;
	}

	@Transactional
	public VentaTemporal save(VentaTemporal venta) {
		VentaTemporal v = repository.save(venta);
		return v;
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public void saveTimbrado(String ventaId,  String nroTimbrado) throws RuntimeException{
		try {
			Long venta= Long.valueOf(ventaId);
			long timbrado =Long.valueOf(nroTimbrado);
			repository.saveTimbrado(venta, timbrado);
			repository.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Long getNroTimbrado() {
		return repository.getMaxNroTimbrado();
	}
	

	public void remove(VentaTemporal venta) {
		repository.delete(venta);
	}

	public Optional<VentaTemporal> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<VentaTemporal> findNota(Long id, String situacion) {
		return repository.findByIdAndSituacion(id, situacion);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}
	
	public List<VentaTemporal> findByClienteId(Long id) {
		return repository.findByCliente(new Cliente(id));
	}

	public List<ConsultaNota> retriveVentaTemporalsByProductoId(Long productoId) {
		return repository.getVentaTemporalsByProductoId(productoId);
	}

	public List<Object[]> retriveVentaTemporalDetalleByIdVentaTemporal(Long ventaId) {
		return repository.getVentaTemporalDetallesByVentaTemporalId(ventaId);
	}

	public Optional<VentaTemporal> findByVentaTemporalIdAndClienteId(Long id, Long clienteId) {
		return repository.findByIdAndCliente(id, new Cliente(clienteId));
	}

	public List<ConsultaNota> retrieveVentaTemporalsDelDia(Date fecha) {
		return repository.getVentaTemporalsDelDia(fecha);
	}

}