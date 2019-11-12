package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.prestosoftware.data.models.Color;
import py.com.prestosoftware.data.repository.ColorRepository;

import java.util.List;

@Service
public class ColorService {

    private ColorRepository repository;

    @Autowired
    public ColorService(ColorRepository repository) {
        this.repository = repository;
    }

    public List<Color> findAll() {
        return repository.findAll();
    }

    public void save(Color color) {
        repository.save(color);
    }

    public void remove(Color color) {
        repository.delete(color);
    }

	public List<Color> findByNombre(String name) {
		return repository.findByNombre(name);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

}