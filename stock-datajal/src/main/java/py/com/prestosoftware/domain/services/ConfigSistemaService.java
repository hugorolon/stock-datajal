package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.ConfigSistema;
import py.com.prestosoftware.data.repository.ConfSistemaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigSistemaService {

    private ConfSistemaRepository repository;

    @Autowired
    public ConfigSistemaService(ConfSistemaRepository repository) {
        this.repository = repository;
    }

    public List<ConfigSistema> findAll() {
        return repository.findAll();
    }
    
    public Optional<ConfigSistema> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(ConfigSistema c) {
        repository.save(c);
    }

    public void remove(ConfigSistema c) {
        repository.delete(c);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}