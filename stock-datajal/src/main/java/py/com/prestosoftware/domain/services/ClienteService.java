package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository clientRepository) {
        this.repository = clientRepository;
    }

    public List<Cliente> findAll() {
        return repository.findAllByOrderByIdAsc();
    }
    
    public Optional<Cliente> findById(Long id) {
    	return repository.findById(id);
    }
    
    public Cliente findByRuc(String ciruc) {
    	return repository.findByCiruc(ciruc);
    }
    
    public List<Cliente> findByNombre(String name) {
    	return repository.findByNombreContaining(name);
    }

    public Cliente save(Cliente client) {
        return repository.save(client);
    }

    public void remove(Cliente client) {
        repository.delete(client);
    }

	public long addNewClient() {
		return repository.getMaxId();
	}

}
