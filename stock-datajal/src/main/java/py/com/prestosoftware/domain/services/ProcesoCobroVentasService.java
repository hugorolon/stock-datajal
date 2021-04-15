package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ProcesoCobroVentas;
import py.com.prestosoftware.data.repository.ProcesoCobroVentasRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProcesoCobroVentasService {

    private ProcesoCobroVentasRepository repository;

    @Autowired
    public ProcesoCobroVentasService(ProcesoCobroVentasRepository repository) {
        this.repository = repository;
    }

    public List<ProcesoCobroVentas> findAll() {
        return repository.findAll();
    }
    
//    public List<ProcesoCobroVentas> findByFechaAndEgresoAndSituacion(Date fecha, Egreso ingreso, String situacion) {
//    	return repository.findByFechaAndEgresoAndSituacionOrderByIdAsc(fecha, caja, situacion);
//    }
    
//    public Optional<ProcesoCobroVentas> findByIdVenta(String idVenta) {
//    	return repository.getProcesoCobroVentasPorNota(idVenta);
//    }
    
//    public Optional<ProcesoCobroVentas> getTotalsMovEgreso(Egreso caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaEgreso(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(ProcesoCobroVentas procesoCobroVentas) {
//        repository.save(procesoCobroVentas);
//        repository.flush();
//    }
    
    @Transactional
    public ProcesoCobroVentas save(ProcesoCobroVentas procesoCobroVentas) {
        return repository.save(procesoCobroVentas);
    }
    
    @Transactional
    public List<ProcesoCobroVentas> save(List<ProcesoCobroVentas> procesoCobroVentass) {
        return repository.saveAll(procesoCobroVentass);
    }

    public void remove(ProcesoCobroVentas procesoCobroVentas) {
        repository.delete(procesoCobroVentas);
    }

}