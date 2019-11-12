package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.data.repository.SubgrupoRepository;
import java.util.List;

@Service
public class SubgrupoService {

    private SubgrupoRepository repository;

    @Autowired
    public SubgrupoService(SubgrupoRepository repository) {
        this.repository = repository;
    }

    public List<Subgrupo> findAll() {
        return repository.findAll();
    }
    
    public List<Subgrupo> findByGrupo(Grupo grupo) {
        return repository.findByGrupo(grupo);
    }

    public void save(Subgrupo subgrupo) {
        repository.save(subgrupo);
        repository.flush();
    }

    public void remove(Subgrupo subgrupo) {
        repository.delete(subgrupo);
    }

	public List<Subgrupo> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}