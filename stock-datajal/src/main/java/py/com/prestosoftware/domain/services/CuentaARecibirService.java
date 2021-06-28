package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.CuentaARecibir;
import py.com.prestosoftware.data.repository.CuentaARecibirRepository;
import java.util.List;

@Service
public class CuentaARecibirService {

    private CuentaARecibirRepository repository;

    @Autowired
    public CuentaARecibirService(CuentaARecibirRepository repository) {
        this.repository = repository;
    }

    public List<CuentaARecibir> findAll() {
        return repository.findAll();
    }
    public CuentaARecibir findByCarProceso(Integer carProceso) {
        return repository.findByCarProceso(carProceso);
    }
    
//    public List<CuentaARecibir> findByFechaAndCajaAndSituacion(Date fecha, Caja caja, String situacion) {
//    	return repository.findByFechaAndCajaAndSituacionOrderByIdAsc(fecha, caja, situacion);
//    }
//    
//    public Optional<CuentaARecibir> findByIdVenta(String idVenta) {
//    	return repository.getCuentaARecibirPorNota(idVenta);
//    }
//    
//    public Optional<CuentaARecibir> getTotalsMovCaja(Caja caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaCaja(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(CuentaARecibir cuentaARecibir) {
//        repository.save(cuentaARecibir);
//        repository.flush();
//    }
    
    @Transactional
    public CuentaARecibir save(CuentaARecibir cuentaARecibir) {
        return repository.save(cuentaARecibir);
    }
    
    @Transactional
    public List<CuentaARecibir> save(List<CuentaARecibir> cuentaARecibirs) {
        return repository.saveAll(cuentaARecibirs);
    }

    public void remove(CuentaARecibir cuentaARecibir) {
        repository.delete(cuentaARecibir);
    }

}