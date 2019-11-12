package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Empaque;
import py.com.prestosoftware.data.repository.EmpaqueRepository;
import java.util.List;

@Service
public class EmpaqueService {

    private EmpaqueRepository repository;

    @Autowired
    public EmpaqueService(EmpaqueRepository repository) {
        this.repository = repository;
    }

    public List<Empaque> findAll() {
        return repository.findAll();
    }

    public void save(Empaque emp) {
        repository.save(emp);
        repository.flush();
    }

    public void remove(Empaque empaque) {
        repository.delete(empaque);
    }

	public List<Empaque> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long addNewPais() {
		return repository.getMaxId();
	}

}
