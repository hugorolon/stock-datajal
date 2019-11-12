package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.repository.EmpresaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private EmpresaRepository repository;

    @Autowired
    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }
    
    public Optional<Empresa> findById(Long id) {
    	return  repository.findById(id);
    }

    public List<Empresa> findAll() {
        return repository.findAll();
    }

    public void save(Empresa emp) {
        repository.save(emp);
        repository.flush();
    }

    public void remove(Empresa emp) {
        repository.delete(emp);
    }

	public List<Empresa> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}