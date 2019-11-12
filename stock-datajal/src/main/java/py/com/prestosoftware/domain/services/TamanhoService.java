package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.data.repository.TamanhoRepository;
import java.util.List;

@Service
public class TamanhoService {

    private TamanhoRepository repository;

    @Autowired
    public TamanhoService(TamanhoRepository repository) {
        this.repository = repository;
    }

    public List<Tamanho> findAll() {
        return repository.findAll();
    }

    public void save(Tamanho t) {
        repository.save(t);
        repository.flush();
    }

    public void remove(Tamanho t) {
        repository.delete(t);
    }

	public List<Tamanho> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}