package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.data.repository.NcmRepository;

import java.util.List;

@Service
public class NcmService {

    private NcmRepository repository;

    @Autowired
    public NcmService(NcmRepository repository) {
        this.repository = repository;
    }

    public List<Ncm> findAll() {
        return repository.findAll();
    }

    public void save(Ncm ncm) {
        repository.save(ncm);
        repository.flush();
    }

    public void remove(Ncm ncm) {
        repository.delete(ncm);
    }

	public List<Ncm> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRoxMax() {
		return repository.getMaxId();
	}

}
