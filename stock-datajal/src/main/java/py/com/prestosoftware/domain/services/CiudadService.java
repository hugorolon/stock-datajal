package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.repository.CiudadRepository;
import java.util.List;

@Service
public class CiudadService {

    private CiudadRepository repository;

    @Autowired
    public CiudadService(CiudadRepository repository) {
        this.repository = repository;
    }

    public List<Ciudad> findAll() {
        return repository.findAll();
    }
    
    public List<Ciudad> findByNombre(String name) {
    	return repository.findByNombre(name);
    }

    public void save(Ciudad ciudad) {
        repository.save(ciudad);
    }

    public void remove(Ciudad ciudad) {
        repository.delete(ciudad);
    }

	public long addNewCiudad() {
		return repository.getMaxId();
	}

}
