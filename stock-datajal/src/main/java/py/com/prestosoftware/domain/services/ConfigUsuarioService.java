package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.ConfigUsuario;
import py.com.prestosoftware.data.repository.ConfUsuarioRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigUsuarioService {

    private ConfUsuarioRepository repository;

    @Autowired
    public ConfigUsuarioService(ConfUsuarioRepository repository) {
        this.repository = repository;
    }

    public List<ConfigUsuario> findAll() {
        return repository.findAll();
    }
    
    public Optional<ConfigUsuario> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(ConfigUsuario c) {
        repository.save(c);
    }

    public void remove(ConfigUsuario c) {
        repository.delete(c);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
