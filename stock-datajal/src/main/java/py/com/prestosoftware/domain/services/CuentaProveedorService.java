package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.CuentaProveedor;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.repository.CuentaProveedorRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaProveedorService {

	@Autowired
    private CuentaProveedorRepository repository;

    public CuentaProveedorService(CuentaProveedorRepository repository) {
        this.repository = repository;
    }

    public List<CuentaProveedor> findAll() {
        return repository.findAll();
    }
    
    public List<CuentaProveedor> findPendientes(Proveedor p, String situacion) {
        return repository.findByProveedorAndSituacionAndDebitoIsNullOrderByVencimientoAsc(p, situacion);
    }
    
    public Optional<CuentaProveedor> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(CuentaProveedor c) {
        repository.save(c);
    }

    public void remove(CuentaProveedor c) {
        repository.delete(c);
    }

	public long addNewClient() {
		return repository.getMaxId();
	}

	public long addNew() {
		return repository.getMaxId();
	}

}
