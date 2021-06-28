package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ProcesoCobroClientes;
import py.com.prestosoftware.data.repository.ProcesoCobroClientesRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProcesoCobroClientesService {

    private ProcesoCobroClientesRepository repository;

    @Autowired
    public ProcesoCobroClientesService(ProcesoCobroClientesRepository repository) {
        this.repository = repository;
    }

    public List<ProcesoCobroClientes> findAll() {
        return repository.findAll();
    }
    
    public ProcesoCobroClientes findByPccCobro(Integer id) {
    	return repository.findByPccCobro(id);
    }
    
//    public Optional<ProcesoCobroClientes> findByIdVenta(String idVenta) {
//    	return repository.getProcesoCobroClientesPorNota(idVenta);
//    }
//    
//    public Optional<ProcesoCobroClientes> getTotalsMovEgreso(Egreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaEgreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(ProcesoCobroClientes procesoCobroClientes) {
//        repository.save(procesoCobroClientes);
//        repository.flush();
//    }
    
    @Transactional
    public ProcesoCobroClientes save(ProcesoCobroClientes procesoCobroClientes) {
        return repository.save(procesoCobroClientes);
    }
    
    @Transactional
    public List<ProcesoCobroClientes> save(List<ProcesoCobroClientes> procesoCobroClientess) {
        return repository.saveAll(procesoCobroClientess);
    }

    public void remove(ProcesoCobroClientes procesoCobroClientes) {
        repository.delete(procesoCobroClientes);
    }

}