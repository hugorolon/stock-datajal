package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Ingreso;
import py.com.prestosoftware.data.repository.IngresoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class IngresoService {

    private IngresoRepository repository;

    @Autowired
    public IngresoService(IngresoRepository repository) {
        this.repository = repository;
    }

    public List<Ingreso> findAll() {
        return repository.findAll();
    }

    public void save(Ingreso dep) {
        repository.save(dep);
        repository.flush();
    }

    public void remove(Ingreso dep) {
        repository.delete(dep);
    }

//	public List<Ingreso> findByNombre(String name) {
//		return repository.findByNombre(name);
//	}

	public Optional<Ingreso> findById(Long id) {
		return repository.findById(id);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
