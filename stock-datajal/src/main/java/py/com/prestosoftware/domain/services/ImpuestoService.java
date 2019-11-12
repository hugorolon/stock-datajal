package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.repository.ImpuestoRepository;
import java.util.List;

@Service
public class ImpuestoService {

    private ImpuestoRepository repository;

    @Autowired
    public ImpuestoService(ImpuestoRepository repository) {
        this.repository = repository;
    }

    public List<Impuesto> findAll() {
        return repository.findAll();
    }

    public void save(Impuesto imp) {
        repository.save(imp);
        repository.flush();
    }

    public void remove(Impuesto imp) {
        repository.delete(imp);
    }

	public List<Impuesto> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
