package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ProcesoPagoProveedores;
import py.com.prestosoftware.data.repository.ProcesoPagoProveedoresRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProcesoPagoProveedoresService {

    private ProcesoPagoProveedoresRepository repository;

    @Autowired
    public ProcesoPagoProveedoresService(ProcesoPagoProveedoresRepository repository) {
        this.repository = repository;
    }

    public List<ProcesoPagoProveedores> findAll() {
        return repository.findAll();
    }
    
    public ProcesoPagoProveedores findByPppPago(Integer id) {
    	return repository.findByPppPago(id);
    }
    
//    public Optional<ProcesoPagoProveedores> findByIdVenta(String idVenta) {
//    	return repository.getProcesoPagoProveedoresPorNota(idVenta);
//    }
//    
//    public Optional<ProcesoPagoProveedores> getTotalsMovEgreso(Egreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaEgreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(ProcesoPagoProveedores procesoPagoProveedores) {
//        repository.save(procesoPagoProveedores);
//        repository.flush();
//    }
    
    @Transactional
    public ProcesoPagoProveedores save(ProcesoPagoProveedores procesoPagoProveedores) {
        return repository.save(procesoPagoProveedores);
    }
    
    @Transactional
    public List<ProcesoPagoProveedores> save(List<ProcesoPagoProveedores> procesoPagoProveedoress) {
        return repository.saveAll(procesoPagoProveedoress);
    }

    public void remove(ProcesoPagoProveedores procesoPagoProveedores) {
        repository.delete(procesoPagoProveedores);
    }

}