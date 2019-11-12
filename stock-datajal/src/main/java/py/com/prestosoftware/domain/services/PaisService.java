package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.data.repository.PaisRepository;

import java.util.List;

@Service
public class PaisService {

    private PaisRepository repository;

    @Autowired
    public PaisService(PaisRepository repository) {
        this.repository = repository;
    }

    public List<Pais> findAll() {
        return repository.findAll();
    }

    public void save(Pais client) {
        repository.save(client);
        repository.flush();
    }

    public void remove(Pais client) {
        repository.delete(client);
    }

	public List<Pais> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long addNewPais() {
		return repository.getMaxId();
	}

}
