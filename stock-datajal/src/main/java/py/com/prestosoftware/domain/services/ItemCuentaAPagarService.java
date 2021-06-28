package py.com.prestosoftware.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ItemCuentaAPagar;
import py.com.prestosoftware.data.repository.ItemCuentaAPagarRepository;

@Service
public class ItemCuentaAPagarService {

	private ItemCuentaAPagarRepository repository;

	@Autowired
	public ItemCuentaAPagarService(ItemCuentaAPagarRepository repository) {
		this.repository = repository;
	}

	public List<ItemCuentaAPagar> findAll() {
		return repository.findAll();
	}

	public void cambiaEstadoSituacion(int estado, Long icpSecuencia) {
		if (estado == 0)
			repository.cambiaEstadoSituacionActivo(icpSecuencia);
		else
			repository.cambiaEstadoSituacionInactivo(icpSecuencia);
	}

	@Transactional
	public ItemCuentaAPagar save(ItemCuentaAPagar itemCuentaAPagar) {
		return repository.save(itemCuentaAPagar);
	}

	@Transactional
	public List<ItemCuentaAPagar> save(List<ItemCuentaAPagar> itemCuentaAPagars) {
		return repository.saveAll(itemCuentaAPagars);
	}

	public void remove(ItemCuentaAPagar itemCuentaAPagar) {
		repository.delete(itemCuentaAPagar);
	}

}