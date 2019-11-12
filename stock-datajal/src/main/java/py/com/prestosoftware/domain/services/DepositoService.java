package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.repository.DepositoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class DepositoService {

    private DepositoRepository repository;

    @Autowired
    public DepositoService(DepositoRepository repository) {
        this.repository = repository;
    }

    public List<Deposito> findAll() {
        return repository.findAll();
    }

    public void save(Deposito dep) {
        repository.save(dep);
        repository.flush();
    }

    public void remove(Deposito dep) {
        repository.delete(dep);
    }

	public List<Deposito> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public Optional<Deposito> findById(Long id) {
		return repository.findById(id);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
