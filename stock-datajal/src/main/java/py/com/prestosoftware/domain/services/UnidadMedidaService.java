package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.data.repository.UnidadMedidaRepository;

import java.util.List;

@Service
public class UnidadMedidaService {

    private UnidadMedidaRepository repository;

    @Autowired
    public UnidadMedidaService(UnidadMedidaRepository repository) {
        this.repository = repository;
    }

    public List<UnidadMedida> findAll() {
        return repository.findAll();
    }

    public void save(UnidadMedida u) {
        repository.save(u);
    }

    public void remove(UnidadMedida u) {
        repository.delete(u);
        repository.flush();
    }

	public List<UnidadMedida> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
