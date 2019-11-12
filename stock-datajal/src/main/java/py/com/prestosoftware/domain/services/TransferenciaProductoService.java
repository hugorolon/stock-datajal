package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.TransferenciaProducto;
import py.com.prestosoftware.data.repository.TransferenciaProductoRepository;
import java.util.List;

@Service
public class TransferenciaProductoService {

    private TransferenciaProductoRepository repository;

    @Autowired
    public TransferenciaProductoService(TransferenciaProductoRepository repository) {
        this.repository = repository;
    }

    public List<TransferenciaProducto> findAll() {
        return repository.findAll();
    }

    public TransferenciaProducto save(TransferenciaProducto t) {
        return repository.save(t);
    }

    public void remove(TransferenciaProducto t) {
        repository.delete(t);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}