package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.repository.MovimientoCajaRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoCajaService {

    private MovimientoCajaRepository repository;

    @Autowired
    public MovimientoCajaService(MovimientoCajaRepository repository) {
        this.repository = repository;
    }

    public List<MovimientoCaja> findAll() {
        return repository.findAll();
    }
    
    public List<MovimientoCaja> findByFechaAndCajaAndSituacion(Date fecha, Caja caja, String situacion) {
    	return repository.findByFechaAndCajaAndSituacionOrderByIdAsc(fecha, caja, situacion);
    }
    
    public Optional<MovimientoCaja> getTotalsMovCaja(Caja caja, Date fecha, String situacion) {
    	return repository.getTotalsEntradaCaja(caja, fecha, situacion);
    }

//    @Transactional
//    public void save(MovimientoCaja pago) {
//        repository.save(pago);
//        repository.flush();
//    }
    
    @Transactional
    public MovimientoCaja save(MovimientoCaja pago) {
        return repository.save(pago);
    }
    
    @Transactional
    public List<MovimientoCaja> save(List<MovimientoCaja> pagos) {
        return repository.saveAll(pagos);
    }

    public void remove(MovimientoCaja pago) {
        repository.delete(pago);
    }

}