package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.repository.ProveedorRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private ProveedorRepository repository;

    @Autowired
    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public List<Proveedor> findAll() {
        return repository.findAllByOrderByIdAsc();
    }
    
    public List<Proveedor> findByNombre(String nombre) {
    	return repository.findByNombreContaining(nombre);
    }
    
    public Optional<Proveedor> findById(Long id) {
		return repository.findById(id);
	}

    public void save(Proveedor prov) {
        repository.save(prov);
        repository.flush();
    }

    public void remove(Proveedor prov) {
        repository.delete(prov);
    }

	public long addNewProveedor() {
		return repository.getMaxId();
	}

}