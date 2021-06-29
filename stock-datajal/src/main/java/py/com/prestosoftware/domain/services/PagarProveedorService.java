package py.com.prestosoftware.domain.services;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.PagarProveedor;
import py.com.prestosoftware.data.repository.PagarProveedorRepository;

@Service
public class PagarProveedorService {

    private PagarProveedorRepository repository;

    @Autowired
    public PagarProveedorService(PagarProveedorRepository clientRepository) {
        this.repository = clientRepository;
    }

    public List<PagarProveedor> findAll() {
        return repository.findAll();
    }
    
    public List<PagarProveedor> findByFecha() {
    	java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
        return repository.findByFecha(date);
    }
    
    public List<Object[]> findDetallePagarProveedorView(Long idCliente) {
        return repository.getDetallePagarProveedorView(idCliente);
    }
    
    public List<Object[]> findProveedorPagadoView(Long idCliente, Integer idCobro) {
        return repository.getProveedorPagadoView(idCliente, idCobro);
    }
    
    public Optional<PagarProveedor> findById(Integer id) {
    	return repository.findById(id);
    }
  
    public Integer getRowCount() {
		return repository.getMaxId();
	}
  
    public PagarProveedor save(PagarProveedor client) {
        return repository.save(client);
    }

    public void remove(PagarProveedor client) {
        repository.delete(client);
    }

	public long addNewClient() {
		return repository.getMaxId();
	}

}
