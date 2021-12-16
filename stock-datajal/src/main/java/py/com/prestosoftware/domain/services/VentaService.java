package py.com.prestosoftware.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.data.repository.VentaRepository;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Service
public class VentaService {

    private VentaRepository repository;

    @Autowired
    public VentaService(VentaRepository repository) {
        this.repository = repository;
    }

    public List<Venta> findAll() {
        return repository.findAll();
    }
    
    public List<Venta> getNotasPendientesBySituacion(String situacion) {
    	return repository.findBySituacionOrderByVencimientoDesc(situacion);
    }
    
    public List<Venta> getNotasPendientesByFechaAndSituacion(Date fecha, String situacion) {
    	return repository.findByFechaAndSituacionOrderByIdAsc(fecha, situacion);
    }
    
    public List<Venta> getNotasPendientesByClienteAndSituacion(Cliente c, String situacion) {
    	return repository.findByClienteAndSituacionOrderByVencimientoDesc(c, situacion);
    }
    
    public List<Venta> findByFilter(String filter) {
    	return repository.findByClienteNombre(filter);
    } 
    
    public List<Venta> getNotasPorClienteAndFecha(Cliente cliente, Date fechaIni, Date fechaFin) {
    	return repository.findByClienteAndFechaBetween(cliente, fechaIni, fechaFin);
    }
    
    public List<Venta> getNotasPorFechas(Date fechaIni, Date fechaFin) {
    	return repository.findByFechaBetween(fechaIni, fechaFin);
    }

    public Venta save(Venta venta) {
        return repository.save(venta);
    }

    public void remove(Venta venta) {
        repository.delete(venta);
    }

	public Optional<Venta> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Venta> findNota(Long id, String situacion) {
		return repository.findByIdAndSituacion(id, situacion);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

	public List<Venta> findByClienteId(Long id) {
		return repository.findByCliente(new Cliente(id));
	}
	
	public List<ConsultaNota> retriveVentasByProductoId(Long productoId) {
		return repository.getVentasByProductoId(productoId);
	}

	public List<Object[]> retriveVentaDetalleByIdVenta(Long ventaId) {
		return repository.getVentaDetallesByVentaId(ventaId);
	}

	public Optional<Venta> findByVentaIdAndClienteId(Long id, Long clienteId) {
		return repository.findByIdAndCliente(id, new Cliente(clienteId));
	}
	
	public List<ConsultaNota> retrieveVentasDelDia(Date fecha){
		return repository.getVentasDelDia(fecha);
	}

}