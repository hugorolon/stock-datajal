package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.repository.CondicionPagoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CondicionPagoService {

    private CondicionPagoRepository repository;

    @Autowired
    public CondicionPagoService(CondicionPagoRepository repository) {
        this.repository = repository;
    }

    public List<CondicionPago> findAll() {
        return repository.findAll();
    }
    
    public Optional<CondicionPago> findByCantDia(int cantDia) {
    	return repository.findByCantDia(cantDia);
    }

    public void save(CondicionPago cat) {
        repository.save(cat);
    }

    public void remove(CondicionPago cat) {
        repository.delete(cat);
    }

	public long getRoxMax() {
		return repository.getMaxId();
	}

	public List<CondicionPago> findByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

}
