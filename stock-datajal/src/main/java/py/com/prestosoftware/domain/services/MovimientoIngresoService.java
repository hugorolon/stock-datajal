package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.data.repository.MovimientoIngresoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoIngresoService {

    private MovimientoIngresoRepository repository;

    @Autowired
    public MovimientoIngresoService(MovimientoIngresoRepository repository) {
        this.repository = repository;
    }

    public List<MovimientoIngreso> findAll() {
        return repository.findAll();
    }
    
    
    public Optional<MovimientoIngreso> findById(Integer id) {
    	return repository.findById(id);
    }
    
    public MovimientoIngreso findByMinProceso(Integer id) {
    	return repository.findByMinProceso(id);
    }
    
    
    public long getRowCount() {
		return repository.getMaxId();
	}
    
    @Transactional
    public MovimientoIngreso save(MovimientoIngreso movimientoIngreso) {
        return repository.save(movimientoIngreso);
    }
    
    @Transactional
    public List<MovimientoIngreso> save(List<MovimientoIngreso> movimientoIngresos) {
        return repository.saveAll(movimientoIngresos);
    }

    public void remove(MovimientoIngreso movimientoIngreso) {
        repository.delete(movimientoIngreso);
    }

}