package py.com.prestosoftware.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.repository.ClienteRepository;


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

    // Utilice el atributo rollbackFor de la anotación @Transactional para especificar una excepción específica, los datos se retrotraen.
    @Transactional
    // A Proxy is Created that wraps the function insert
	// BeginTransaction
    public Cliente save(Cliente client) throws Exception {
    	Cliente c=repository.save(client);
    	return c;  
    }
    // Commit Transaction

    public void remove(Cliente client) {
        repository.delete(client);
    }

	public long addNewClient() {
		return repository.getMaxId()+1;
	}

}
