package py.com.prestosoftware.domain.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.CobroCliente;
import py.com.prestosoftware.data.repository.CobroClienteRepository;

@Service
public class CobroClienteService {

    private CobroClienteRepository repository;

    @Autowired
    public CobroClienteService(CobroClienteRepository clientRepository) {
        this.repository = clientRepository;
    }

    public List<CobroCliente> findAll() {
        return repository.findAll();
    }
    
    public List<CobroCliente> findByFecha() {
    	java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
        return repository.findByFecha(date);
    }
    
    public List<Object[]> findDetalleCobroClienteView(Long idCliente) {
        return repository.getDetalleCobroClienteView(idCliente);
    }
    
    public List<Object[]> findClienteCobradoView(Long idCliente, Integer idCobro) {
        return repository.getClienteCobradoView(idCliente, idCobro);
    }
    
    public Optional<CobroCliente> findById(Integer id) {
    	return repository.findById(id);
    }
  
    public Integer getRowCount() {
		return repository.getMaxId();
	}
  
    public CobroCliente save(CobroCliente client) {
        return repository.save(client);
    }

    public void remove(CobroCliente client) {
        repository.delete(client);
    }

	public long addNewClient() {
		return repository.getMaxId();
	}

}
