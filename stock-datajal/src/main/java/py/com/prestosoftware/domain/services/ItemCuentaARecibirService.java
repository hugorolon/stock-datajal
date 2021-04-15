package py.com.prestosoftware.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.CuentaARecibir;
import py.com.prestosoftware.data.models.ItemCuentaARecibir;
import py.com.prestosoftware.data.repository.ItemCuentaARecibirRepository;

@Service
public class ItemCuentaARecibirService {

    private ItemCuentaARecibirRepository repository;

    @Autowired
    public ItemCuentaARecibirService(ItemCuentaARecibirRepository repository) {
        this.repository = repository;
    }

    public List<ItemCuentaARecibir> findAll() {
        return repository.findAll();
    }
    
//    public List<CuentaARecibir> findByFechaAndCajaAndSituacion(Date fecha, Caja caja, String situacion) {
//    	return repository.findByFechaAndCajaAndSituacionOrderByIdAsc(fecha, caja, situacion);
//    }
//    
//    public Optional<CuentaARecibir> findByIdVenta(String idVenta) {
//    	return repository.getCuentaARecibirPorNota(idVenta);
//    }
//    
//    public Optional<CuentaARecibir> getTotalsMovCaja(Caja caja, Date fecha, String situacion) {
//    	return repository.getTotalsEntradaCaja(caja, fecha, situacion);
//    }

//    @Transactional
//    public void save(CuentaARecibir cuentaARecibir) {
//        repository.save(cuentaARecibir);
//        repository.flush();
//    }
    
    @Transactional
    public ItemCuentaARecibir save(ItemCuentaARecibir itemCuentaARecibir) {
        return repository.save(itemCuentaARecibir);
    }
    
    @Transactional
    public List<ItemCuentaARecibir> save(List<ItemCuentaARecibir> itemCuentaARecibirs) {
        return repository.saveAll(itemCuentaARecibirs);
    }

    public void remove(ItemCuentaARecibir itemCuentaARecibir) {
        repository.delete(itemCuentaARecibir);
    }

}