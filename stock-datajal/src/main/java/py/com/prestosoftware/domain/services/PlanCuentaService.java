package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.data.repository.PlanCuentaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PlanCuentaService {

    private PlanCuentaRepository repository;

    @Autowired
    public PlanCuentaService(PlanCuentaRepository repository) {
        this.repository = repository;
    }

    public List<PlanCuenta> findAll() {
        return repository.findAll();
    }
    
    public Optional<PlanCuenta> findById(Long id) {
    	return repository.findById(id);
    }
    
    public List<PlanCuenta> findByNombre(String name) {
		return repository.findByNombre(name);
	}

    public void save(PlanCuenta plan) {
        repository.save(plan);
        repository.flush();
    }

    public void remove(PlanCuenta plan) {
        repository.delete(plan);
    }

	public long getRoxMax() {
		return repository.getMaxId();
	}

}
