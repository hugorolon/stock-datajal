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
    
    public List <MovimientoCaja> findByFechaAndCajaAndSituacion(Date fecha, Caja caja, String situacion) {
    	return repository.findByFechaAndCajaAndSituacionOrderByIdAsc(fecha, caja, situacion);
    }
    public Optional<List<MovimientoCaja>> getMovimientosLanzamiento(Date fecha, Caja caja, String situacion, String situacion2) {
    	return repository.getMovimientosLanzamiento(fecha, caja, situacion, situacion2);
    }
    
    public Date findLastDateMov(Date fecha) {
    	return repository.findLastDateMov(fecha);
    }
    
    public Optional<Double> totalVentaContado(Date fecha) {
    	return repository.getTotalsVentaContado(fecha);
    }
    
    public Optional<Double> totalOtrosIngresos(Date fecha, Caja caja) {
    	return repository.getTotalsOtrosIngresos(caja, fecha);
    }
    
    public Optional<Double> totalCompraContado(Date fecha, Caja caja) {
    	return repository.getTotalsCompraContado(fecha);
    }
 
    public Optional<Double> totalOtrosEgresos(Date fecha, Caja caja) {
    	return repository.getTotalsOtrosEgresos(caja, fecha);
    }
    public Optional<Double> totalEntrada(Date fechaInicial, Date fechaFinal, Caja caja) {
    	return repository.getTotalsEntrada(caja, fechaInicial, fechaFinal);
    }
    
    public Optional<Double> totalSalida(Date fechaInicial, Date fechaFinal, Caja caja) {
    	return repository.getTotalsSalida(caja, fechaInicial, fechaFinal);
    }
    
    public Optional<MovimientoCaja> findByIdVenta(String idVenta) {
    	return repository.getMovimientoCajaPorNota(idVenta);
    }
    
    public Optional<MovimientoCaja> findByNotaNroAndTipoOperacion(String id, String mov) {
    	return repository.findByNotaNroAndTipoOperacion(id, mov);
    }
    
    public MovimientoCaja findByNotaNro(String id) {
    	return repository.findByNotaNro(id);
    }
    
    public Optional<Object[]> getTotalsMovCaja(Caja caja, Date fecha, String situacion) {
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