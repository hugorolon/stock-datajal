package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.repository.GrupoRepository;
import java.util.List;

@Service
public class GrupoService {

    private GrupoRepository repository;

    @Autowired
    public GrupoService(GrupoRepository repository) {
        this.repository = repository;
    }

    public List<Grupo> findAll() {
        return repository.findAll();
    }

    public void save(Grupo grupo) {
        repository.save(grupo);
        repository.flush();
    }

    public void remove(Grupo grupo) {
        repository.delete(grupo);
    }

	public List<Grupo> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}
