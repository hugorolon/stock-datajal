package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ProcesoCobroVentas;
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
    
    public ProcesoPagoCompras findByPcoCompra(Integer id) {
    	return repository.findByPcoCompra(id);
    }
    
    public ProcesoPagoCompras findByPcoCompraAndPcoProceso(Integer id, Integer idProceso) {
    	return repository.findByPcoCompraAndPcoProceso(id, idProceso);
    }

    
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