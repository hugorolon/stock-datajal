package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.repository.ConfiguracionRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracionService {

    private ConfiguracionRepository repository;

    @Autowired
    public ConfiguracionService(ConfiguracionRepository repository) {
        this.repository = repository;
    }

    public List<Configuracion> findAll() {
        return repository.findAll();
    }
    
    public Optional<Configuracion> findById(Long id) {
    	return repository.findById(id);
    }
    
    public Optional<Configuracion> findByUserId(Usuario usuario) {
    	return repository.findByUsuarioId(usuario);
    }
    
    public Optional<Configuracion> findByEmpresaId(Empresa empresa) {
    	return repository.findByEmpresaId(empresa);
    }

    public Configuracion save(Configuracion c) {
        return repository.save(c);
    }

    public void remove(Configuracion c) {
        repository.delete(c);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
