package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }
    
    public Optional<Usuario> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(Usuario usuario) {
        repository.save(usuario);
    }

    public void remove(Usuario usuario) {
        repository.delete(usuario);
    }

	public List<Usuario> findByUsuario(String name) {
		return repository.findByUsuario(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}
	
	public Optional<Usuario> login(String user, String pass) {
		return repository.findByUsuarioAndClave(user, pass);
	}

}