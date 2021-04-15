package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.MovimientoItemEgreso;
import py.com.prestosoftware.data.repository.MovimientoItemEgresoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoItemEgresoService {

    private MovimientoItemEgresoRepository repository;

    @Autowired
    public MovimientoItemEgresoService(MovimientoItemEgresoRepository repository) {
        this.repository = repository;
    }

    public List<MovimientoItemEgreso> findAll() {
        return repository.findAll();
    }
    
//    public List<MovimientoItemEgreso> findByFechaAndEgresoAndSituacion(Date fecha, Egreso ingreso, String situacion) {
//    	return repository.findByFechaAndEgresoAndSituacionOrderByIdAsc(fecha, caja, situacion);
//    }
    
//    public Optional<MovimientoItemEgreso> findByIdVenta(String idVenta) {
//    	return repository.getMovimientoItemEgresoPorNota(idVenta);
//    }
    
//    public Optional<MovimientoItemEgreso> getTotalsMovEgreso(Egreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaEgreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(MovimientoItemEgreso movimientoItemEgreso) {
//        repository.save(movimientoItemEgreso);
//        repository.flush();
//    }
    
    @Transactional
    public MovimientoItemEgreso save(MovimientoItemEgreso movimientoItemEgreso) {
        return repository.save(movimientoItemEgreso);
    }
    
    @Transactional
    public List<MovimientoItemEgreso> save(List<MovimientoItemEgreso> movimientoItemEgresos) {
        return repository.saveAll(movimientoItemEgresos);
    }

    public void remove(MovimientoItemEgreso movimientoItemEgreso) {
        repository.delete(movimientoItemEgreso);
    }

}