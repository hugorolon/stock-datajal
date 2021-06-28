package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.CuentaAPagar;
import py.com.prestosoftware.data.repository.CuentaAPagarRepository;
import java.util.List;

@Service
public class CuentaAPagarService {

    private CuentaAPagarRepository repository;

    @Autowired
    public CuentaAPagarService(CuentaAPagarRepository repository) {
        this.repository = repository;
    }

    public List<CuentaAPagar> findAll() {
        return repository.findAll();
    }
    
//    public List<CuentaAPagar> findByFechaAndCajaAndSituacion(Date fecha, Caja caja, String situacion) {
//    	return repository.findByFechaAndCajaAndSituacionOrderByIdAsc(fecha, caja, situacion);
//    }
//    
//    public Optional<CuentaAPagar> findByIdVenta(String idVenta) {
//    	return repository.getCuentaAPagarPorNota(idVenta);
//    }
//    
//    public Optional<CuentaAPagar> getTotalsMovCaja(Caja caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaCaja(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(CuentaAPagar cuentaAPagar) {
//        repository.save(cuentaAPagar);
//        repository.flush();
//    }
    
    @Transactional
    public CuentaAPagar save(CuentaAPagar cuentaAPagar) {
        return repository.save(cuentaAPagar);
    }
    
    @Transactional
    public List<CuentaAPagar> save(List<CuentaAPagar> cuentaAPagars) {
        return repository.saveAll(cuentaAPagars);
    }

    public void remove(CuentaAPagar cuentaAPagar) {
        repository.delete(cuentaAPagar);
    }

}