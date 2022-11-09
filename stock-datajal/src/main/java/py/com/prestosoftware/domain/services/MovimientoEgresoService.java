package py.com.prestosoftware.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.MovimientoEgreso;
import py.com.prestosoftware.data.repository.MovimientoEgresoRepository;

@Service
public class MovimientoEgresoService {

    private MovimientoEgresoRepository repository;

    @Autowired
    public MovimientoEgresoService(MovimientoEgresoRepository repository) {
        this.repository = repository;
    }

    public List<MovimientoEgreso> findAll() {
        return repository.findAll();
    }
    public List<MovimientoEgreso> findByDate(Date date) {
        return repository.getEgresosDelDia(date);
    }
    
    public Optional<MovimientoEgreso> findById(Integer id) {
    	return repository.findById(id);
    }
    
    public MovimientoEgreso findByMegProceso(Integer id) {
    	return repository.findByMegProceso(id);
    }
    
//    public Optional<MovimientoEgreso> findByIdVenta(String idVenta) {
//    	return repository.getMovimientoEgresoPorNota(idVenta);
//    }
    
//    public Optional<MovimientoEgreso> getTotalsMovEgreso(Egreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaEgreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(MovimientoEgreso movimientoEgreso) {
//        repository.save(movimientoEgreso);
//        repository.flush();
//    }
    
    public long getRowCount() {
		return repository.getMaxId();
	}
    
    @Transactional
    public MovimientoEgreso save(MovimientoEgreso movimientoEgreso) {
        return repository.save(movimientoEgreso);
    }
    
    @Transactional
    public List<MovimientoEgreso> save(List<MovimientoEgreso> movimientoEgresos) {
        return repository.saveAll(movimientoEgresos);
    }

    public void remove(MovimientoEgreso movimientoEgreso) {
        repository.delete(movimientoEgreso);
    }

}