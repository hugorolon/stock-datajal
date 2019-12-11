package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.UsuarioRol;
import py.com.prestosoftware.data.repository.UsuarioRolRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioRolService {

    private UsuarioRolRepository repository;

    @Autowired
    public UsuarioRolService(UsuarioRolRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioRol> findAll() {
        return repository.findAll();
    }
    
    public Optional<UsuarioRol> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(UsuarioRol usuarioRol) {
        repository.save(usuarioRol);
    }

    public void remove(UsuarioRol usuarioRol) {
        repository.delete(usuarioRol);
    }

	public List<UsuarioRol> findByUsuarioRol(String name) {
		return repository.findByUsuarioRol(name);
	}

	public Boolean hasRole(Long idUsuario, String name) {
		return repository.hasRole(idUsuario, name);
	}
	
	public long getRowCount() {
		return repository.getMaxId();
	}
	

}