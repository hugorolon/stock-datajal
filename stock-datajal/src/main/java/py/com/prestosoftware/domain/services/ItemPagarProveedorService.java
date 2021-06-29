package py.com.prestosoftware.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.ItemPagarProveedor;
import py.com.prestosoftware.data.repository.ItemPagarProveedorRepository;

@Service
public class ItemPagarProveedorService {

    private ItemPagarProveedorRepository repository;

    @Autowired
    public ItemPagarProveedorService(ItemPagarProveedorRepository repository) {
        this.repository = repository;
    }

    public List<ItemPagarProveedor> findAll() {
        return repository.findAll();
    }
    
    public List<ItemPagarProveedor> findByIppNumero(Integer idCab) {
    	return repository.findByIppNumero(idCab);
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
    public ItemPagarProveedor save(ItemPagarProveedor itemCobroCliente) {
        return repository.save(itemCobroCliente);
    }
    
    @Transactional
    public List<ItemPagarProveedor> save(List<ItemPagarProveedor> itemCobroClientes) {
        return repository.saveAll(itemCobroClientes);
    }

    public void remove(ItemPagarProveedor itemCobroCliente) {
        repository.delete(itemCobroCliente);
    }

}