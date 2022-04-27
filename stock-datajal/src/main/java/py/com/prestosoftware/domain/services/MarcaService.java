package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.repository.MarcaRepository;
import java.util.List;

@Service
public class MarcaService {

    private MarcaRepository repository;

    @Autowired
    public MarcaService(MarcaRepository repository) {
        this.repository = repository;
    }

    public List<Marca> findAll() {
        return repository.findAllByOrderByNombreAsc();
    }

    public void save(Marca marca) {
        repository.save(marca);
        repository.flush();
    }

    public void remove(Marca marca) {
        repository.delete(marca);
    }

	public List<Marca> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowMax() {
		return repository.getMaxId();
	}

}