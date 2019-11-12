package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.data.repository.RolRepository;
import java.util.List;

@Service
public class RolService {

    private RolRepository repository;

    @Autowired
    public RolService(RolRepository repository) {
        this.repository = repository;
    }

    public List<Rol> findAll() {
        return repository.findAll();
    }
    
    public List<Rol> findByNombre(String nombre) {
    	return repository.findByNombre(nombre);
    }

    public void save(Rol rol) {
        repository.save(rol);
        repository.flush();
    }

    public void remove(Rol rol) {
        repository.delete(rol);
    }

	public long getRoxMax() {
		return repository.getMaxId();
	}

}
