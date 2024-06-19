package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Lotes;
import py.com.prestosoftware.data.repository.LoteRepository;
import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    private LoteRepository repository;

    @Autowired
    public LoteService(LoteRepository repository) {
        this.repository = repository;
    }

    public List<Lotes> findAll() {
        return repository.findAll();
    }
    
    public Optional<Lotes> findById(Long id) {
    	return repository.findById(id);
    }
    
    public List<Lotes> findLotesByProductoId(Long productoId) {
		return repository.findLotesByProductoId(productoId);
	}
//    
//    public List<Lotes> findByNombre(String name) {
//		return repository.findByNombre(name);
//	}

    public void save(Lotes lote) {
        repository.save(lote);
    }

    public void remove(Lotes lote) {
        repository.delete(lote);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
