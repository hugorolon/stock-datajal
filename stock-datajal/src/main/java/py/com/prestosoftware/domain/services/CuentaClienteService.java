package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.CuentaCliente;
import py.com.prestosoftware.data.repository.CuentaClienteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaClienteService {

	@Autowired
    private CuentaClienteRepository repository;

    
    public CuentaClienteService(CuentaClienteRepository repository) {
        this.repository = repository;
    }

    public List<CuentaCliente> findAll() {
        return repository.findAll();
    }
    
    public List<CuentaCliente> findPendientes(Cliente cliente, String situacion) {
        return repository.findByClienteAndSituacionAndCreditoIsNullOrderByVencimientoAsc(cliente, situacion);
    }
    
    public Optional<CuentaCliente> findById(Long id) {
    	return repository.findById(id);
    }
   
    public void save(CuentaCliente c) {
        repository.save(c);
    }

    public void remove(CuentaCliente c) {
        repository.delete(c);
    }

	public long addNew() {
		return repository.getMaxId();
	}

}
