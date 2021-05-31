package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Egreso;
import py.com.prestosoftware.data.repository.EgresoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EgresoService {

    private EgresoRepository repository;

    @Autowired
    public EgresoService(EgresoRepository repository) {
        this.repository = repository;
    }

    public List<Egreso> findAll() {
        return repository.findAll();
    }

    public void save(Egreso dep) {
        repository.save(dep);
        repository.flush();
    }

    public void remove(Egreso dep) {
        repository.delete(dep);
    }

//	public List<Egreso> findByNombre(String name) {
//		return repository.findByNombre(name);
//	}

	public Optional<Egreso> findById(Long id) {
		return repository.findById(id);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
