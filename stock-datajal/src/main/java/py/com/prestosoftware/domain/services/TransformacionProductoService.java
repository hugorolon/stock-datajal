package py.com.prestosoftware.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.TransformacionProducto;
import py.com.prestosoftware.data.repository.TransformacionProductoRepository;

@Service
public class TransformacionProductoService {

    private TransformacionProductoRepository repository;

    @Autowired
    public TransformacionProductoService(TransformacionProductoRepository repository) {
        this.repository = repository;
    }

    public List<TransformacionProducto> findAll() {
        return repository.findAll();
    }

    public TransformacionProducto save(TransformacionProducto t) {
        return repository.save(t);
    }

    public void remove(TransformacionProducto t) {
        repository.delete(t);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}