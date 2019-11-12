package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.repository.ListaPrecioRepository;
import java.util.List;

@Service
public class ListaPrecioService {

    private ListaPrecioRepository repository;

    @Autowired
    public ListaPrecioService(ListaPrecioRepository repository) {
        this.repository = repository;
    }

    public List<ListaPrecio> findAll() {
        return repository.findAll();
    }

    public void save(ListaPrecio listaPrecio) {
        repository.save(listaPrecio);
        repository.flush();
    }

    public void remove(ListaPrecio listaPrecio) {
        repository.delete(listaPrecio);
    }

	public List<ListaPrecio> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowMax() {
		return repository.getMaxId();
	}

}
