package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.repository.AperturaCierreCajaRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AperturaCierreCajaService {

    private AperturaCierreCajaRepository repository;

    @Autowired
    public AperturaCierreCajaService(AperturaCierreCajaRepository repository) {
        this.repository = repository;
    }

    public List<AperturaCierreCaja> findAll() {
        return repository.findAll();
    }
    
    public Optional<AperturaCierreCaja> findByCajaAndFechaApertura(Caja caja, Date fecha) {
    	return repository.findOptionalByCajaAndFechaApertura(caja, fecha);
    }
    
    public Optional<AperturaCierreCaja> findOptionalByCajaAndFechaAperturaUsuario(Caja caja, Date fecha, Long usuario) {
    	return repository.findOptionalByCajaAndFechaAperturaAndUsuario(caja, fecha, usuario);
    }
    
    public Optional<AperturaCierreCaja> getInfoAperturaCierreCaja() {
    	return repository.getUltAperturaCierre();
    }

    public AperturaCierreCaja save(AperturaCierreCaja mov) {
        return repository.save(mov);
    }

    public void remove(AperturaCierreCaja mov) {
        repository.delete(mov);
    }

}
