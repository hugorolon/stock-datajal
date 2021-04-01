package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.ClientePais;
import py.com.prestosoftware.data.repository.ClientePaisRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientePaisService {

    private ClientePaisRepository repository;

    @Autowired
    public ClientePaisService(ClientePaisRepository clientRepository) {
        this.repository = clientRepository;
    }

    public List<ClientePais> findAll() {
        return repository.findAll();
    }
    
    public Optional<ClientePais> findById(Long id) {
    	return repository.findById(id);
    }
    
    public ClientePais findByRuc(String ciruc) {
    	return repository.findByCiruc(ciruc);
    }
    
    public List<ClientePais> findByNombre(String name) {
    	return repository.findByNombreContaining(name);
    }

    public ClientePais save(ClientePais client) {
        return repository.save(client);
    }

    public void remove(ClientePais client) {
        repository.delete(client);
    }

	public long addNewClient() {
		return repository.getMaxId();
	}

}
