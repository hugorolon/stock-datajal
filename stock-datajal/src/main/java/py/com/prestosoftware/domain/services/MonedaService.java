package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.repository.MonedaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MonedaService {

    private MonedaRepository repository;

    @Autowired
    public MonedaService(MonedaRepository repository) {
        this.repository = repository;
    }

    public List<Moneda> findAll() {
        return repository.findAll();
    }

    public void save(Moneda mon) {
        repository.save(mon);
        repository.flush();
    }

    public void remove(Moneda mon) {
        repository.delete(mon);
    }

	public List<Moneda> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public Optional<Moneda> findById(Long id) {
		return repository.findById(id);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

	public Optional<Moneda> findMonedaBase(int esBase) {
		return repository.findByEsBase(esBase);
	}

}