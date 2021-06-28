package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.data.repository.MovimientoItemIngresoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoItemIngresoService {

    private MovimientoItemIngresoRepository repository;

    @Autowired
    public MovimientoItemIngresoService(MovimientoItemIngresoRepository repository) {
        this.repository = repository;
    }

    public List<MovimientoItemIngreso> findAll() {
        return repository.findAll();
    }
    
    public List<MovimientoItemIngreso> findByCabId(Integer cabId) {
    	return repository.findByCabId(cabId);
    }
    
    public Optional<MovimientoItemIngreso> findById(Long id) {
    	return repository.findById(id);
    }
    
//    public Optional<MovimientoItemIngreso> getTotalsMovIngreso(Ingreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaIngreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(MovimientoItemIngreso movimientoItemIngreso) {
//        repository.save(movimientoItemIngreso);
//        repository.flush();
//    }
    
    @Transactional
    public MovimientoItemIngreso save(MovimientoItemIngreso movimientoItemIngreso) {
        return repository.save(movimientoItemIngreso);
    }
    
    @Transactional
    public List<MovimientoItemIngreso> save(List<MovimientoItemIngreso> movimientoItemIngresos) {
        return repository.saveAll(movimientoItemIngresos);
    }

    public void remove(MovimientoItemIngreso movimientoItemIngreso) {
        repository.delete(movimientoItemIngreso);
    }

}