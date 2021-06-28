package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ProcesoPagoCompras;
import py.com.prestosoftware.data.repository.ProcesoPagoComprasRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProcesoPagoComprasService {

    private ProcesoPagoComprasRepository repository;

    @Autowired
    public ProcesoPagoComprasService(ProcesoPagoComprasRepository repository) {
        this.repository = repository;
    }

    public List<ProcesoPagoCompras> findAll() {
        return repository.findAll();
    }
    
//    public List<ProcesoPagoCompras> findByFechaAndEgresoAndSituacion(Date fecha, Egreso ingreso, String situacion) {
//    	return repository.findByFechaAndEgresoAndSituacionOrderByIdAsc(fecha, caja, situacion);
//    }
    
//    public Optional<ProcesoPagoCompras> findByIdVenta(String idVenta) {
//    	return repository.getProcesoPagoComprasPorNota(idVenta);
//    }
    
//    public Optional<ProcesoPagoCompras> getTotalsMovEgreso(Egreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaEgreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(ProcesoPagoCompras procesoPagoCompras) {
//        repository.save(procesoPagoCompras);
//        repository.flush();
//    }
    
    @Transactional
    public ProcesoPagoCompras save(ProcesoPagoCompras procesoPagoCompras) {
        return repository.save(procesoPagoCompras);
    }
    
    @Transactional
    public List<ProcesoPagoCompras> save(List<ProcesoPagoCompras> procesoPagoComprass) {
        return repository.saveAll(procesoPagoComprass);
    }

    public void remove(ProcesoPagoCompras procesoPagoCompras) {
        repository.delete(procesoPagoCompras);
    }

}