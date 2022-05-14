package py.com.prestosoftware.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Presupuesto;
import py.com.prestosoftware.data.models.PresupuestoDetalle;
import py.com.prestosoftware.data.repository.PresupuestoRepository;

@Service
public class PresupuestoService {

    private PresupuestoRepository repository;

    @Autowired
    public PresupuestoService(PresupuestoRepository repository) {
        this.repository = repository;
    }

    public List<Presupuesto> findAll() {
        return repository.findAll();
    }
    
    public Optional<Presupuesto> findById(Long id) {
		return repository.findById(id);
	}
    
    public Presupuesto getPresupuesto(Long id) {
		Presupuesto p= repository.getPresupuesto(id);
		List<Object[]> listaPresupDetalle=repository.getPresupuestoDetalleByIdPresupuesto(id);
		List<PresupuestoDetalle> listaDetalle = new ArrayList<PresupuestoDetalle>();
		for (Object[] objects : listaPresupDetalle) {
			PresupuestoDetalle pd = new PresupuestoDetalle();
			pd.setCantidad(Double.valueOf(objects[0].toString()));
			pd.setPrecio(Double.valueOf(objects[1].toString()));
			pd.setProducto(objects[2].toString());
			pd.setProductoId(Long.valueOf(objects[3].toString()));
			pd.setSubtotal(Double.valueOf(objects[4].toString()));
			listaDetalle.add(pd);
		}
		p.setItems(listaDetalle);
		return p;
	}

    public Presupuesto save(Presupuesto presu) {
        return repository.save(presu);
    }

    public void remove(Presupuesto presu) {
        repository.delete(presu);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}