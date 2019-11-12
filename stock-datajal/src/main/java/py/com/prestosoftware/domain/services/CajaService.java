package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.repository.CajaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CajaService {

    private CajaRepository repository;

    @Autowired
    public CajaService(CajaRepository repository) {
        this.repository = repository;
    }

    public List<Caja> findAll() {
        return repository.findAll();
    }
    
    public Optional<Caja> findById(Long id) {
    	return repository.findById(id);
    }
    
    public List<Caja> findByNombre(String name) {
		return repository.findByNombre(name);
	}

    public void save(Caja caja) {
        repository.save(caja);
    }

    public void remove(Caja caja) {
        repository.delete(caja);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
