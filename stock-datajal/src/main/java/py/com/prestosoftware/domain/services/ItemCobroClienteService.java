package py.com.prestosoftware.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ItemCobroCliente;
import py.com.prestosoftware.data.repository.ItemCobroClienteRepository;

@Service
public class ItemCobroClienteService {

    private ItemCobroClienteRepository repository;

    @Autowired
    public ItemCobroClienteService(ItemCobroClienteRepository repository) {
        this.repository = repository;
    }

    public List<ItemCobroCliente> findAll() {
        return repository.findAll();
    }
    
    public List<ItemCobroCliente> findByIclNumero(Integer idCab) {
    	return repository.findByIclNumero(idCab);
    }
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
    public ItemCobroCliente save(ItemCobroCliente itemCobroCliente) {
        return repository.save(itemCobroCliente);
    }
    
    @Transactional
    public List<ItemCobroCliente> save(List<ItemCobroCliente> itemCobroClientes) {
        return repository.saveAll(itemCobroClientes);
    }

    public void remove(ItemCobroCliente itemCobroCliente) {
        repository.delete(itemCobroCliente);
    }

}