package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.repository.CompraRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private CompraRepository repository;

    @Autowired
    public CompraService(CompraRepository repository) {
        this.repository = repository;
    }

    public List<Compra> findAll() {
        return repository.findAll();
    }
    
    public List<Compra> getNotasPendientes(String situacion) {
    	return repository.findBySituacionOrderByIdAsc(situacion);
    }
    
    public List<Compra> findByFilter(String filter) {
    	return repository.findByProveedorNombre(filter);
    }

    public Compra save(Compra compra) {
    	return repository.save(compra);
    }

    public void remove(Compra compra) {
        repository.delete(compra);
    }

	public Optional<Compra> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Compra> findByProveedorAndFacturaNro(Proveedor p, String factura) {
		return repository.findByProveedorAndFactura(p, factura);
	}
	
	public Optional<Compra> findNota(Long id, String situacion) {
		return repository.findByIdAndSituacion(id, situacion);
	}
	
	public long getRowCount() {
		return repository.getMaxId();
	}

	public List<Compra> findByProveedorId(Long id) {
		return repository.findByProveedor(new Proveedor(id));
	}
	
	public Optional<Compra> findByCompraIdAndProveedorId(Long id, Long proveedorId) {
		return repository.findByIdAndProveedor(id, new Proveedor(proveedorId));
	}

	public List<Compra> getNotasPendientesByFechaAndSituacion(Date fecha, String situacion) {
		return repository.findByFechaAndSituacionOrderByIdAsc(fecha, situacion);
	}
	
	public List<Compra> getNotasPendientesByProveedorAndSituacion(Proveedor p, String situacion) {
		return repository.findByProveedorAndSituacionOrderByIdAsc(p, situacion);
	}
	
	public List<Compra> getNotasPendientesBySituacion(String situacion) {
		return repository.findBySituacionOrderByIdAsc(situacion);
	}

}