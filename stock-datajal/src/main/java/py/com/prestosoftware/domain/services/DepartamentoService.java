package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.repository.DepartamentoRepository;
import java.util.List;

@Service
public class DepartamentoService {

    private DepartamentoRepository repository;

    @Autowired
    public DepartamentoService(DepartamentoRepository repository) {
        this.repository = repository;
    }

    public List<Departamento> findAll() {
        return repository.findAll();
    }
    
    public List<Departamento> findByNombre(String name) {
		return repository.findByNombre(name);
	}

    public void save(Departamento dep) {
        repository.save(dep);
        repository.flush();
    }

    public void remove(Departamento dep) {
        repository.delete(dep);
    }

	public long addNewDep() {
		return repository.getMaxId();
	}

}
