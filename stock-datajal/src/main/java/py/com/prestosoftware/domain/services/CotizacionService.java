package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Cotizacion;
import py.com.prestosoftware.data.repository.CotizacionRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {

    private CotizacionRepository repository;

    @Autowired
    public CotizacionService(CotizacionRepository repository) {
        this.repository = repository;
    }

    public List<Cotizacion> findAll() {
        return repository.findAll();
    }
    
    public List<Cotizacion> findAllByFecha(Date fecha) {
    	return repository.findByFecha(fecha);
    }
    
    public Optional<Cotizacion> findCotizacionByMonedaAndFecha(Long monedaId, String fecha) {
    	return repository.getCotizacionByMonedaAndId(monedaId, fecha);
    }
    
    public Optional<Cotizacion> findCotizacionByMoneda(Long monedaId) {
    	return repository.getCotizacionByMoneda(monedaId);
    }
    
    public Optional<Cotizacion> findById(Long id) {
    	return repository.findById(id);
    }

    public void save(Cotizacion cot) {
        repository.save(cot);
        repository.flush();
    }

    public void remove(Cotizacion cot) {
        repository.delete(cot);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
