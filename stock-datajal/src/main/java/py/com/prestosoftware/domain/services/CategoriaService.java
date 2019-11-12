package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.repository.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    private CategoriaRepository repository;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }
    
    public List<Categoria> findByNombre(String nombre) {
    	return repository.findByNombre(nombre);
    }

    public void save(Categoria cat) {
        repository.save(cat);
    }

    public void remove(Categoria cat) {
        repository.delete(cat);
    }

	public long getRoxMax() {
		return repository.getMaxId();
	}

}
