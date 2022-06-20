package py.com.prestosoftware.domain.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.CobroCliente;
import py.com.prestosoftware.data.models.ItemCobroCliente;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.data.models.ProcesoCobroClientes;
import py.com.prestosoftware.data.repository.CobroClienteRepository;
import py.com.prestosoftware.data.repository.CuentaARecibirRepository;
import py.com.prestosoftware.data.repository.ItemCobroClienteRepository;
import py.com.prestosoftware.data.repository.ItemCuentaARecibirRepository;
import py.com.prestosoftware.data.repository.MovimientoIngresoRepository;
import py.com.prestosoftware.data.repository.MovimientoItemIngresoRepository;
import py.com.prestosoftware.data.repository.ProcesoCobroClientesRepository;
import py.com.prestosoftware.data.repository.ProcesoCobroVentasRepository;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.viewmodel.DetalleCobroClienteView;

@Service
public class CobroClienteService {

    private CobroClienteRepository repository;
    private ItemCobroClienteRepository itemCobroClienterepository;
    private MovimientoIngresoRepository repositoryMovimientoIngreso;
	private MovimientoItemIngresoRepository repositoryMovimientoItemIngreso;
	private CuentaARecibirRepository repositoryCuentaARecibir;
	private ItemCuentaARecibirRepository repositoryItemCuentaARecibir;
	private ProcesoCobroClientesRepository repositoryProcesoCobroClientes;
	
    @Autowired
    public CobroClienteService(CobroClienteRepository clientRepository, MovimientoIngresoRepository repositoryMovimientoIngreso, MovimientoItemIngresoRepository repositoryMovimientoItemIngreso, ItemCobroClienteRepository itemCobroClienterepository, CuentaARecibirRepository repositoryCuentaARecibir, ItemCuentaARecibirRepository repositoryItemCuentaARecibir, ProcesoCobroClientesRepository repositoryProcesoCobroClientes) {
        this.repository = clientRepository;
        this.repositoryMovimientoIngreso=repositoryMovimientoIngreso;
        this.repositoryMovimientoItemIngreso= repositoryMovimientoItemIngreso;
        this.itemCobroClienterepository=itemCobroClienterepository;
        this.repositoryCuentaARecibir=repositoryCuentaARecibir;
        this.repositoryItemCuentaARecibir=repositoryItemCuentaARecibir;
        this.repositoryProcesoCobroClientes=repositoryProcesoCobroClientes;
    }

    public List<CobroCliente> findAll() {
        return repository.findAll();
    }
    
    public List<CobroCliente> findByFecha() {
    	java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
        return repository.findByFecha(date);
    }
    
    public List<Object[]> findDetalleCobroClienteView(Long idCliente) {
        return repository.getDetalleCobroClienteView(idCliente);
    }
    
    public List<Object[]> findClienteCobradoView(Long idCliente, Integer idCobro) {
        return repository.getClienteCobradoView(idCliente, idCobro);
    }
    
    public Optional<CobroCliente> findById(Integer id) {
    	return repository.findById(id);
    }
  
    public Integer getRowCount() {
		return repository.getMaxId();
	}
  
    @Transactional(rollbackFor = RuntimeException.class)
    public CobroCliente save(CobroCliente client, List<DetalleCobroClienteView> listaDetalleCobroCliente, Double montoACobrar) {
    	
    	CobroCliente cc = repository.save(client);
    	//detalle cobro cliente
		List<ItemCobroCliente> detalles=new ArrayList<ItemCobroCliente>();
		for (DetalleCobroClienteView item : listaDetalleCobroCliente) {
			if(item.getCobro().doubleValue()>0) {
				ItemCobroCliente itemCobroCliente =new ItemCobroCliente();
				itemCobroCliente.setIclNumero(cc.getCclNumero());
				itemCobroCliente.setIclMonto(item.getCobro());
				itemCobroCliente.setIclSecuenciaCuenta(item.getIca_Secuencia());
				if(item.getCobro().doubleValue()+item.getPagado().doubleValue()==item.getCar_monto1().doubleValue()) {
					repositoryItemCuentaARecibir.cambiaEstadoSituacionInactivo(Long.valueOf(item.getIca_Secuencia()));
					repositoryCuentaARecibir.cambiaEstadoSituacionInactivo(Long.valueOf(item.getCar_numero()));
				}
				detalles.add(itemCobroCliente);
			}
		}
		itemCobroClienterepository.saveAll(detalles);
		//MovimientoIngreso
		MovimientoIngreso movimientoIngreso = new MovimientoIngreso();
		movimientoIngreso.setFecha(new Date());
		movimientoIngreso.setHora(new Date());
		movimientoIngreso.setMinCaja(1);
		movimientoIngreso.setMinDocumento(cc.getCclDocumento());
		movimientoIngreso.setMinEntidad(client.getCclEntidad().toString());
		movimientoIngreso.setMinProceso(cc.getCclNumero());
		movimientoIngreso.setMinTipoProceso(2);
		movimientoIngreso.setMinSituacion(0);
		movimientoIngreso.setMinTipoEntidad(8);
		MovimientoIngreso movNew = repositoryMovimientoIngreso.save(movimientoIngreso);
		
		MovimientoItemIngreso movimientoItemIngreso= new MovimientoItemIngreso();
		movimientoItemIngreso.setMiiNumero(movNew.getMinNumero());
		movimientoItemIngreso.setMiiMonto(montoACobrar);
		movimientoItemIngreso.setMiiIngreso(2);
		movimientoItemIngreso.setMiiDescripcion("Cobro a Clientes");
		repositoryMovimientoItemIngreso.save(movimientoItemIngreso);
		
		//Proceso cobro cliente
		ProcesoCobroClientes p= new ProcesoCobroClientes();
		p.setPccCobro(cc.getCclNumero());
		p.setPccIngreso(1);
		p.setPccTipoproceso(31);
		p.setPccFlag(1);
		p.setPccProceso(movNew.getMinNumero());
		repositoryProcesoCobroClientes.save(p);
		
		
        return cc;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void remove(CobroCliente client, List<ItemCobroCliente> listaItemCobro, List<DetalleCobroClienteView> listaDetalleCobroView) {
    	for (ItemCobroCliente itemCobroCliente : listaItemCobro) {
    		repositoryItemCuentaARecibir.cambiaEstadoSituacionActivo(Long.valueOf(itemCobroCliente.getIclSecuenciaCuenta()));
    		itemCobroClienterepository.delete(itemCobroCliente);
		}
		for (DetalleCobroClienteView detalleCobroCliente : listaDetalleCobroView) {
			repositoryCuentaARecibir.cambiaEstadoSituacionActivo(Long.valueOf(detalleCobroCliente.getCar_numero()));
		}
        repository.delete(client);
        
        MovimientoIngreso movimientoIngreso = repositoryMovimientoIngreso.findByMinProceso(client.getCclNumero());
		
		List<MovimientoItemIngreso> listaMovItemIngreso= repositoryMovimientoItemIngreso.findByCabId(movimientoIngreso.getMinNumero());
		for (MovimientoItemIngreso movimientoItemIngreso : listaMovItemIngreso) {
			repositoryMovimientoItemIngreso.delete(movimientoItemIngreso);	
		}
		repositoryMovimientoIngreso.delete(movimientoIngreso);
		
		ProcesoCobroClientes procesoCobroCliente=repositoryProcesoCobroClientes.findByPccProceso(movimientoIngreso.getMinNumero());
		repositoryProcesoCobroClientes.delete(procesoCobroCliente);

    }

	public long addNewClient() {
		return repository.getMaxId();
	}

}
