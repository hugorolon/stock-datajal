package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Pedido;
import py.com.prestosoftware.data.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository repository;

    @Autowired
    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Pedido save(Pedido pedido) {
        return repository.saveAndFlush(pedido);
    }

    public void remove(Pedido pedido) {
        repository.delete(pedido);
    }
    
    public Long getMax() {
    	return repository.getMaxId();
    }

	public Optional<Pedido> findById(Long id) {
		return repository.findById(id);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
