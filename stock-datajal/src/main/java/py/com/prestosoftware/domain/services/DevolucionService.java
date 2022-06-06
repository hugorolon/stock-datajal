package py.com.prestosoftware.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Devolucion;
import py.com.prestosoftware.data.models.DevolucionDetalle;
import py.com.prestosoftware.data.repository.DevolucionRepository;

@Service
public class DevolucionService {

    private DevolucionRepository repository;

    @Autowired
    public DevolucionService(DevolucionRepository repository) {
        this.repository = repository;
    }

    public List<Devolucion> findAll() {
        return repository.findAll();
    }

    public Devolucion save(Devolucion dev) {
        Devolucion devolucion = repository.save(dev);
        repository.flush();
        
        return devolucion;
    }

    public void remove(Devolucion dev) {
        repository.delete(dev);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}
	
	public Optional<Devolucion> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<Devolucion> findNota(Long notaId, String situacion, String tipo) {
		return repository.findByIdAndSituacionAndTipo(notaId, situacion, tipo);
	}

	public List<Devolucion> getNotasPendientes(Date fecha, String situacion) {
		return repository.findByFechaAndSituacion(fecha, situacion);
	}
	
	public List<Object[]> getDetallesDevolucion(Long idDevolucion) {
		return repository.getDevolucionDetallesByDevolucionId(idDevolucion);
	}

}