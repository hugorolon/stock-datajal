package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.ConfigCompra;
import py.com.prestosoftware.data.repository.ConfCompraRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigCompraService {

    private ConfCompraRepository repository;

    @Autowired
    public ConfigCompraService(ConfCompraRepository repository) {
        this.repository = repository;
    }

    public List<ConfigCompra> findAll() {
        return repository.findAll();
    }
    
    public Optional<ConfigCompra> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(ConfigCompra c) {
        repository.save(c);
    }

    public void remove(ConfigCompra c) {
        repository.delete(c);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
