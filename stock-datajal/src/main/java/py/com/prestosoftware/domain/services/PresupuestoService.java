package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Presupuesto;
import py.com.prestosoftware.data.repository.PresupuestoRepository;

import java.util.List;

@Service
public class PresupuestoService {

    private PresupuestoRepository repository;

    @Autowired
    public PresupuestoService(PresupuestoRepository repository) {
        this.repository = repository;
    }

    public List<Presupuesto> findAll() {
        return repository.findAll();
    }

    public Presupuesto save(Presupuesto presu) {
        return repository.save(presu);
    }

    public void remove(Presupuesto presu) {
        repository.delete(presu);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}