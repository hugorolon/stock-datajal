package py.com.prestosoftware.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.CuentaAPagar;
import py.com.prestosoftware.data.models.ItemCuentaAPagar;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoEgreso;
import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.data.models.MovimientoItemEgreso;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.data.models.ProcesoPagoCompras;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.repository.CompraRepository;
import py.com.prestosoftware.data.repository.CuentaAPagarRepository;
import py.com.prestosoftware.data.repository.ItemCuentaAPagarRepository;
import py.com.prestosoftware.data.repository.MovimientoCajaRepository;
import py.com.prestosoftware.data.repository.MovimientoEgresoRepository;
import py.com.prestosoftware.data.repository.MovimientoIngresoRepository;
import py.com.prestosoftware.data.repository.MovimientoItemEgresoRepository;
import py.com.prestosoftware.data.repository.MovimientoItemIngresoRepository;
import py.com.prestosoftware.data.repository.ProcesoPagoComprasRepository;
import py.com.prestosoftware.data.repository.ProductoRepository;
import py.com.prestosoftware.ui.helpers.GlobalVars;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    private CompraRepository repository;
	private ProductoRepository repositoryProducto;
	private MovimientoCajaRepository repositoryPago;
	private MovimientoIngresoRepository repositoryMovimientoIngreso;
	private MovimientoItemIngresoRepository repositoryMovimientoItemIngreso;
	private ProcesoPagoComprasRepository repositoryProcesoPagoCompras;
	private CuentaAPagarRepository repositoryCuentaAPagar;
	private ItemCuentaAPagarRepository repositoryItemCuentaAPagar;
	private MovimientoEgresoRepository repositoryMovimientoEgreso;
	private MovimientoItemEgresoRepository repositoryMovimientoItemEgreso;


    @Autowired
    public CompraService(CompraRepository repository, ProductoRepository repositoryProducto, MovimientoCajaRepository repositoryPago, MovimientoIngresoRepository repositoryMovimientoIngreso, 
			MovimientoItemIngresoRepository repositoryMovimientoItemIngreso, ProcesoPagoComprasRepository repositoryProcesoPagoCompras,CuentaAPagarRepository repositoryCuentaAPagar,
			ItemCuentaAPagarRepository repositoryItemCuentaAPagar, MovimientoEgresoRepository repositoryMovimientoEgreso, MovimientoItemEgresoRepository repositoryMovimientoItemEgreso) {
        this.repository = repository;
        this.repositoryProducto =repositoryProducto;
		this.repositoryPago=  repositoryPago;
		this.repositoryMovimientoIngreso= repositoryMovimientoIngreso;
		this.repositoryMovimientoItemIngreso=repositoryMovimientoItemIngreso;
		this.repositoryProcesoPagoCompras= repositoryProcesoPagoCompras;
		this.repositoryCuentaAPagar=repositoryCuentaAPagar;
		this.repositoryItemCuentaAPagar=repositoryItemCuentaAPagar;
		this.repositoryMovimientoEgreso=repositoryMovimientoEgreso;
		this.repositoryMovimientoItemEgreso=repositoryMovimientoItemEgreso;
    }

    public List<Compra> findAll() {
        return repository.findAll();
    }
    
    public List<Compra> getNotasPendientes(String situacion) {
    	return repository.findBySituacionOrderByIdAsc(situacion);
    }
    
    public List<Compra> findByFilter(String filter) {
    	return repository.findByProveedorNombre(filter);
    }
    
    public List<Compra> getNotasPorFechas(Date fechaIni, Date fechaFin) {
    	return repository.findByFechaBetween(fechaIni, fechaFin);
    }
    
    public List<Compra> getComprasFiltro(Date fechaIni, Date fechaFin, String situacion, int forma) {
    	if(situacion.length()>0 && forma > 0)
    		return repository.getComprasFiltro(fechaIni, fechaFin, situacion, forma);
    	else
    		if(situacion.length()>0 && forma == 0)
    			return repository.getComprasFiltroSinForma(fechaIni, fechaFin, situacion);
    		else
    			if(situacion.length()==0 && forma > 0)
    				return repository.getComprasFiltroSinSituacion(fechaIni, fechaFin, forma);
    			else
    				return repository.getComprasFiltroSinSituacionSinForma(fechaIni, fechaFin);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Compra save(int lanzamientoCaja, Compra compra, String condicion) {
    	Compra c = new Compra();
    	if(compra.getId()==999999)
    		compra.setId(null);
    	c= repository.save(compra);
    	repository.flush();
    	if (lanzamientoCaja == 0) {
			updateStockProduct(c.getItems(),lanzamientoCaja,1);
			openMovCaja(c, condicion);
			openMovimientoEgresoProcesoPagoCompras(c);
			if (condicion.equalsIgnoreCase("30 días")) {
				CuentaAPagar cuentaAPagar = new CuentaAPagar();
				cuentaAPagar = cuentaAPagarProcesoPagoCompras(c, condicion);
				movimientoIngresoProcesoPagoCompras(c, cuentaAPagar);
			}
		}

    	return c;
    }
    
    @Transactional(rollbackFor = RuntimeException.class)
    public Compra save(Compra compra) {
    	return repository.save(compra);
    }

    public void remove(Compra compra) {
        repository.delete(compra);
    }

	public Optional<Compra> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Compra> findByProveedorAndFacturaNro(Proveedor p, String factura) {
		return repository.findByProveedorAndFactura(p, factura);
	}
	
	public Optional<Compra> findNota(Long id, String situacion) {
		return repository.findByIdAndSituacion(id, situacion);
	}
	
	public long getRowCount() {
		return repository.getMaxId();
	}

	public List<Compra> findByProveedorId(Long id) {
		return repository.findByProveedor(new Proveedor(id));
	}
	
	public Optional<Compra> findByCompraIdAndProveedorId(Long id, Long proveedorId) {
		return repository.findByIdAndProveedor(id, new Proveedor(proveedorId));
	}

	public List<Compra> getNotasPendientesByFechaAndSituacion(Date fecha, String situacion) {
		return repository.findByFechaAndSituacionOrderByIdAsc(fecha, situacion);
	}
	
	public List<Compra> getNotasPendientesByProveedorAndSituacion(Proveedor p, String situacion) {
		return repository.findByProveedorAndSituacionOrderByIdAsc(p, situacion);
	}
	
	public List<Compra> getNotasPendientesBySituacion(String situacion) {
		return repository.findBySituacionOrderByIdAsc(situacion);
	}

	private void updateStockProduct(List<CompraDetalle> items, int habilitaLanzamientoCaja, int depesitoId) {
		List<Producto> productos = new ArrayList<>();
		
		for (CompraDetalle e : items) {
			Optional<Producto> pOptional = repositoryProducto.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();
				Double cantCompra = e.getCantidad() != null ? e.getCantidad() : 0;
				Double entPendiente = p.getEntPendiente() != null ? p.getEntPendiente() : 0;
				Double costoAnt = p.getPrecioCostoPromedio() != null ? p.getPrecioCostoPromedio() : 0;
				Double calcPromedioPrecio = (costoAnt == 0 ? e.getPrecio()
						: (costoAnt.intValue() + e.getPrecio().intValue()) / 2);
				//p.setPrecioCosto(e.getPrecio());
				p.setPrecioCostoPromedio(calcPromedioPrecio);
				if (habilitaLanzamientoCaja == 1)
					p.setEntPendiente(entPendiente + cantCompra);
				else {
					Double cantDep = 0d;
					switch (GlobalVars.DEPOSITO_ID.intValue()) {
					case 1:
						cantDep = p.getDepO1() != null ? p.getDepO1() : 0; 
						p.setDepO1(cantDep + cantCompra);
						break;
					case 2:
						cantDep = p.getDepO2()!=null ? p.getDepO2() : 0;
						p.setDepO2(cantDep + cantCompra);
						break;
					case 3:
						cantDep = p.getDepO3()!=null ? p.getDepO3() : 0;
						p.setDepO3(cantDep + cantCompra);
						break;
					case 4:
						cantDep = p.getDepO4() != null ? p.getDepO4() : 0;
						p.setDepO4(cantDep + cantCompra);
						break;
					case 5:
						cantDep = p.getDepO5() != null ? p.getDepO5():0;
						p.setDepO5(cantDep + cantCompra);
						break;
					default:
						break;
					}

				}

				productos.add(p);
			}
		}

		repositoryProducto.saveAll(productos);
		repositoryProducto.flush();
	}
	
	private void openMovCaja(Compra compra, String condicion) {
		// cierre de caja del dia anterio
		MovimientoCaja movCaja = new MovimientoCaja();
		movCaja.setCaja(new Caja(1L));
		movCaja.setFecha(new Date());
		movCaja.setMoneda(new Moneda(1l));
		movCaja.setNotaNro(compra.getId().toString());
		movCaja.setNotaReferencia(compra.getProveedorNombre());
		movCaja.setNotaValor(compra.getTotalGeneral());
		movCaja.setPlanCuentaId(2);
		movCaja.setTipoOperacion("S");
		movCaja.setUsuario(GlobalVars.USER_ID);
		movCaja.setValorM01(compra.getTotalGeneral());
		if (condicion.equalsIgnoreCase("contado")) {
			movCaja.setObs("Pagado al proveedor ");
			movCaja.setSituacion("PAGADO");
		} else if (condicion.equalsIgnoreCase("30 días")) {
			//cant = 1;// Integer.valueOf(tfCuotaCant.getText());
			movCaja.setObs("Credito a cuotas :" + 1);
			movCaja.setSituacion("PROCESADO");
		}
		repositoryPago.save(movCaja);
		repositoryPago.flush();
	}
	
	

	private void movimientoIngresoProcesoPagoCompras(Compra compra, CuentaAPagar cuentaAPagar) {
		MovimientoIngreso m = new MovimientoIngreso();
		m.setFecha(new Date());
		m.setHora(new Date());
		m.setMinCaja(1);
		m.setMinDocumento(cuentaAPagar.getNroBoleta().toString());
		m.setMinEntidad(cuentaAPagar.getIdEntidad().toString());
		m.setMinProceso(cuentaAPagar.getCapNumero());
		m.setMinTipoProceso(30);
		m.setMinTipoEntidad(3);
		m.setMinSituacion(0);
		m = repositoryMovimientoIngreso.save(m);

		MovimientoItemIngreso mii = new MovimientoItemIngreso();
		mii.setMiiNumero(m.getMinNumero());
		mii.setMiiIngreso(30);
		mii.setMiiMonto(cuentaAPagar.getMonto());
		repositoryMovimientoItemIngreso.save(mii);

		ProcesoPagoCompras ppc = new ProcesoPagoCompras();
		ppc.setPcoCompra(compra.getId().intValue());
		ppc.setPcoIngresoEgreso(1);
		ppc.setPcoTipoproceso(31);
		ppc.setPcoProceso(m.getMinNumero());
		ppc.setPcoFlag(1);
		repositoryProcesoPagoCompras.save(ppc);
		repositoryMovimientoIngreso.flush();
		repositoryMovimientoItemIngreso.flush();
		repositoryProcesoPagoCompras.flush();
		
	}

	private CuentaAPagar cuentaAPagarProcesoPagoCompras(Compra compra, String condicion) {
		CuentaAPagar cuentaAPagar = new CuentaAPagar();

		cuentaAPagar.setFecha(new Date());
		cuentaAPagar.setHora(new Date());
		cuentaAPagar.setNroBoleta(compra.getComprobante());
		cuentaAPagar.setIdEntidad(compra.getProveedor().getId());
		cuentaAPagar.setTipoEntidad(3);
		cuentaAPagar.setMonto(compra.getTotalGeneral());
		cuentaAPagar.setCapProceso(compra.getId().intValue());
		cuentaAPagar.setCapSituacion(0);

		cuentaAPagar = repositoryCuentaAPagar.save(cuentaAPagar);

		List<ItemCuentaAPagar> listaItemCuentaAPagar = new ArrayList<ItemCuentaAPagar>();

		int cant = 0;
		int cantDias = 0;
		Calendar cal = Calendar.getInstance();
		if (condicion.equalsIgnoreCase("30 días")) {
			cant =1;// Integer.valueOf(tfCuotaCant.getText());
			cantDias = 30;
			//cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(30));
		} 

		Double valorTotal = compra.getTotalGeneral() / cant;
		for (int i = 0; i < cant; i++) {
			long secuencia = repositoryItemCuentaAPagar.getSecuencia();
			ItemCuentaAPagar itemCuentaAPagar = new ItemCuentaAPagar();
			cal.add(Calendar.MONTH, 1);
			itemCuentaAPagar.setIcpCuenta(cuentaAPagar.getCapNumero());
			itemCuentaAPagar.setIcpMonto(valorTotal);
			itemCuentaAPagar.setIcpSituacion(0);
			itemCuentaAPagar.setIcpDocumento(cant + "/" + (i + 1));
			itemCuentaAPagar.setIcpSecuencia(Integer.valueOf((int) (secuencia+1)));
			itemCuentaAPagar.setIcpVencimiento(cal.getTime());
			itemCuentaAPagar.setIcpDias(cantDias);

			listaItemCuentaAPagar.add(itemCuentaAPagar);
		}
		repositoryItemCuentaAPagar.saveAll(listaItemCuentaAPagar);
		// Proceso pago compras
		ProcesoPagoCompras ppc = new ProcesoPagoCompras();
		ppc.setPcoCompra(compra.getId().intValue());
		ppc.setPcoIngresoEgreso(1);
		ppc.setPcoTipoproceso(30);
		ppc.setPcoProceso(cuentaAPagar.getCapNumero());
		ppc.setPcoFlag(1);
		repositoryProcesoPagoCompras.save(ppc);
		repositoryCuentaAPagar.flush();
		repositoryItemCuentaAPagar.flush();
		repositoryProcesoPagoCompras.flush();

		return cuentaAPagar;
	}

	private void openMovimientoEgresoProcesoPagoCompras(Compra c) {
		MovimientoEgreso movEgreso = new MovimientoEgreso();
		movEgreso.setFecha(new Date());
		movEgreso.setHora(new Date());
		movEgreso.setMegProceso(c.getId().intValue());
		movEgreso.setMegTipoProceso(1);
		movEgreso.setMegEntidad(c.getProveedor().getId().toString());
		movEgreso.setMegSituacion(0);
		movEgreso.setMegDocumento(c.getComprobante());
		movEgreso = repositoryMovimientoEgreso.save(movEgreso);

		MovimientoItemEgreso movItemEgreso = new MovimientoItemEgreso();
		movItemEgreso.setMieNumero(movEgreso.getMegNumero());
		movItemEgreso.setMieEgreso(1);
		double monto = (double) Math.round(c.getTotalGeneral() / 11);
		movItemEgreso.setMieMonto(monto);
		movItemEgreso.setMieDescripcion("Egreso  - Compra Crédito IVA");
		repositoryMovimientoItemEgreso.save(movItemEgreso);

		MovimientoItemEgreso movIE = new MovimientoItemEgreso();
		movIE.setMieNumero(movEgreso.getMegNumero());
		movIE.setMieEgreso(4);
		movIE.setMieMonto(c.getTotalGeneral() - monto);
		movIE.setMieDescripcion("Egreso  - Compra Crédito IMPONIBLE");
		repositoryMovimientoItemEgreso.save(movIE);

		ProcesoPagoCompras ppc = new ProcesoPagoCompras();
		ppc.setPcoCompra(c.getId().intValue());
		ppc.setPcoIngresoEgreso(2);
		ppc.setPcoTipoproceso(32);
		ppc.setPcoProceso(movEgreso.getMegNumero());
		ppc.setPcoFlag(1);
		repositoryProcesoPagoCompras.save(ppc);
		
		repositoryMovimientoEgreso.flush();
		repositoryMovimientoItemEgreso.flush();
		repositoryProcesoPagoCompras.flush();
		
	}
}