package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.AjusteStock;
import py.com.prestosoftware.data.repository.AjusteStockRepository;
import java.util.List;

@Service
public class AjusteStockService {

    private AjusteStockRepository repository;

    @Autowired
    public AjusteStockService(AjusteStockRepository repository) {
        this.repository = repository;
    }

    public List<AjusteStock> findAll() {
        return repository.findAll();
    }

    public AjusteStock save(AjusteStock ajuste) {
        return repository.save(ajuste);
    }

    public void remove(AjusteStock ajuste) {
        repository.delete(ajuste);
    }

	public long getRowCount() {
		return repository.getMaxId();
	}

}
