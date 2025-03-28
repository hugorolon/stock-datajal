package py.com.prestosoftware.domain.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.CuentaARecibir;
import py.com.prestosoftware.data.models.ItemCuentaARecibir;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoEgreso;
import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.data.models.MovimientoItemEgreso;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.data.models.ProcesoCobroVentas;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.data.repository.ClienteRepository;
import py.com.prestosoftware.data.repository.CuentaARecibirRepository;
import py.com.prestosoftware.data.repository.ItemCuentaARecibirRepository;
import py.com.prestosoftware.data.repository.MovimientoCajaRepository;
import py.com.prestosoftware.data.repository.MovimientoEgresoRepository;
import py.com.prestosoftware.data.repository.MovimientoIngresoRepository;
import py.com.prestosoftware.data.repository.MovimientoItemEgresoRepository;
import py.com.prestosoftware.data.repository.MovimientoItemIngresoRepository;
import py.com.prestosoftware.data.repository.ProcesoCobroVentasRepository;
import py.com.prestosoftware.data.repository.ProductoRepository;
import py.com.prestosoftware.data.repository.VentaRepository;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;
import py.com.prestosoftware.util.Notifications;

@Service
public class VentaService {

	private VentaRepository repository;
	private ClienteRepository repositoryCliente;
	private ProductoRepository repositoryProducto;
	private MovimientoCajaRepository repositoryPago;
	private MovimientoIngresoRepository repositoryMovimientoIngreso;
	private MovimientoItemIngresoRepository repositoryMovimientoItemIngreso;
	private ProcesoCobroVentasRepository repositoryProcesoCobroVentas;
	private CuentaARecibirRepository repositoryCuentaARecibir;
	private ItemCuentaARecibirRepository repositoryItemCuentaARecibir;
	private MovimientoEgresoRepository repositoryMovimientoEgreso;
	private MovimientoItemEgresoRepository repositoryMovimientoItemEgreso;

	@Autowired
	public VentaService(VentaRepository repository, ClienteRepository repositoryCliente, ProductoRepository repositoryProducto, MovimientoCajaRepository repositoryPago, MovimientoIngresoRepository repositoryMovimientoIngreso, 
				MovimientoItemIngresoRepository repositoryMovimientoItemIngreso, ProcesoCobroVentasRepository repositoryProcesoCobroVentas,CuentaARecibirRepository repositoryCuentaARecibir,
				ItemCuentaARecibirRepository repositoryItemCuentaARecibir, MovimientoEgresoRepository repositoryMovimientoEgreso, MovimientoItemEgresoRepository repositoryMovimientoItemEgreso) {
		this.repository = repository;
		this.repositoryCliente = repositoryCliente;
		this.repositoryProducto =repositoryProducto;
		this.repositoryPago=  repositoryPago;
		this.repositoryMovimientoIngreso= repositoryMovimientoIngreso;
		this.repositoryMovimientoItemIngreso=repositoryMovimientoItemIngreso;
		this.repositoryProcesoCobroVentas=repositoryProcesoCobroVentas;
		this.repositoryCuentaARecibir=repositoryCuentaARecibir;
		this.repositoryItemCuentaARecibir=repositoryItemCuentaARecibir;
		this.repositoryMovimientoEgreso=repositoryMovimientoEgreso;
		this.repositoryMovimientoItemEgreso=repositoryMovimientoItemEgreso;
	}

	public List<Venta> findAll() {
		return repository.findAll();
	}

	public List<Venta> getNotasPendientesBySituacion(String situacion) {
		return repository.findBySituacionOrderByVencimientoDesc(situacion);
	}

	public List<Venta> getNotasPendientesByFechaAndSituacion(Date fecha, String situacion) {
		return repository.findByFechaAndSituacionOrderByIdAsc(fecha, situacion);
	}

	public List<Venta> getNotasPendientesByClienteAndSituacion(Cliente c, String situacion) {
		return repository.findByClienteAndSituacionOrderByVencimientoDesc(c, situacion);
	}

	public List<Venta> findByFilter(String filter) {
		return repository.findByClienteNombre(filter);
	}

	public List<Venta> getNotasPorClienteAndFecha(Cliente cliente, Date fechaIni, Date fechaFin) {
		return repository.findByClienteAndFechaBetween(cliente, fechaIni, fechaFin);
	}

	public List<Venta> getVentasFiltro(Date fechaIni, Date fechaFin, String situacion, int forma) {
		if(situacion.length()>0 && forma > 0)
    		return repository.getVentasFiltro(fechaIni, fechaFin, situacion, forma);
    	else
    		if(situacion.length()>0 && forma == 0)
    			return repository.getVentasFiltroSinForma(fechaIni, fechaFin, situacion);
    		else
    			if(situacion.length()==0 && forma > 0)
    				return repository.getVentasFiltroSinSituacion(fechaIni, fechaFin, forma);
    			else
    				return repository.getVentasFiltroSinSituacionSinForma(fechaIni, fechaFin);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public Venta save(int lanzamientoCaja, Venta venta, Cliente clienteNuevo, String condicion) throws RuntimeException{
		Venta v=new Venta();
		venta.setId(getRowCount()+1);
			if (clienteNuevo != null && clienteNuevo.getId()!=null && clienteNuevo.getId().intValue() == Long.valueOf(999999).intValue()) {
				clienteNuevo.setId(null);
				Cliente n = this.repositoryCliente.save(clienteNuevo);
				venta.setCliente(n);
			}
			try {
				v = repository.save(venta);			;
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if (lanzamientoCaja == 0) {
				try {
					updateStockProduct(v.getItems(),lanzamientoCaja,1);
					openMovCaja(v, condicion);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					movimientoIngresoProcesoCobroVenta(v);					
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (!condicion.equalsIgnoreCase("Contado")) {
					CuentaARecibir cuentaARecibir = new CuentaARecibir();
					cuentaARecibir = cuentaARecibirProcesoCobroVenta(v, condicion);
					openMovimientoEgreso(cuentaARecibir);
				}
			}
			repository.flush();
			return v;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public Venta saveRemoved(int lanzamientoCaja, Venta venta, List<VentaDetalle> items,  String condicion) throws RuntimeException{
		Venta v = repository.save(venta);
		if (lanzamientoCaja == 0) {
			try {
				updateStockProductRemoved(items, lanzamientoCaja,1);
				removeMovCaja(v);
				removeMovimientoIngresoProcesoCobroVenta(v);
			
				if (!condicion.equalsIgnoreCase("Contado")) {
					Integer cuentaARecibir = removeCuentaARecibirProcesoCobroVenta(v);
					removeMovimientoEgreso(cuentaARecibir);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
//			updateStockProduct(v.getItems(),lanzamientoCaja,1);
//			openMovCaja(v, condicion);
//			movimientoIngresoProcesoCobroVenta(v);
//			if (!condicion.equalsIgnoreCase("Contado")) {
//				CuentaARecibir cuentaARecibir = new CuentaARecibir();
//				cuentaARecibir = cuentaARecibirProcesoCobroVenta(v, condicion);
//				openMovimientoEgreso(cuentaARecibir);
//			}
		}
		return v;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public void saveTimbrado(String ventaId,  String nroTimbrado) throws RuntimeException{
		Long venta= Long.valueOf(ventaId);
		long timbrado =Long.valueOf(nroTimbrado);
		repository.saveTimbrado(venta, timbrado);
		repository.flush();
	}
	
	
	public Venta save(Venta venta) {
		Venta v = repository.save(venta);
		return v;
	}
	private void removeMovCaja(Venta venta) {
		// cierre de caja del dia anterio
		Optional<MovimientoCaja> movimientoCaja = repositoryPago.getMovimientoCajaPorNota(venta.getId().toString());
		if (movimientoCaja.isPresent()) {
			MovimientoCaja mc = movimientoCaja.get();
			mc.setObs("VENTA ANULADO");
			mc.setSituacion("ANULADO");
			repositoryPago.save(mc);
			repositoryPago.flush();
		}
		// remover los otros movimientos insertados
	}

	private void removeMovimientoIngresoProcesoCobroVenta(Venta venta) {
		try {
			MovimientoIngreso m = repositoryMovimientoIngreso.findByMinProceso(Integer.valueOf(venta.getId().toString()));
			Integer cabId = m.getMinNumero();
			
			List<MovimientoItemIngreso> mii = repositoryMovimientoItemIngreso.findByCabId(cabId);
			for (MovimientoItemIngreso movimientoItemIngreso : mii) {
				repositoryMovimientoItemIngreso.delete(movimientoItemIngreso);
			}
			repositoryMovimientoIngreso.delete(m);
			ProcesoCobroVentas pcv = repositoryProcesoCobroVentas.findByPveVenta(Integer.valueOf(venta.getId().toString()));
			repositoryProcesoCobroVentas.delete(pcv);
			
			repositoryMovimientoItemIngreso.flush();
			repositoryMovimientoIngreso.flush();
			repositoryProcesoCobroVentas.flush();

		} catch (Exception e) {

		}
	}
	
	private Integer removeCuentaARecibirProcesoCobroVenta(Venta venta) {
		CuentaARecibir cuentaARecibir = repositoryCuentaARecibir
				.findByCarProceso(Integer.valueOf(venta.getId().toString()));
		Integer cabId = cuentaARecibir.getCarNumero();
		
		List<ItemCuentaARecibir> listaItemCuentaARecibir = repositoryItemCuentaARecibir.findByCabId(cabId);
		for (ItemCuentaARecibir itemCuentaARecibir : listaItemCuentaARecibir) {
			repositoryItemCuentaARecibir.delete(itemCuentaARecibir);
		}
		repositoryCuentaARecibir.delete(cuentaARecibir);
		
		repositoryItemCuentaARecibir.flush();
		repositoryCuentaARecibir.flush();

		// Proceso Cobro ventas
		ProcesoCobroVentas pcv = repositoryProcesoCobroVentas.findByPveVenta(Integer.valueOf(venta.getId().toString()));
		repositoryProcesoCobroVentas.delete(pcv);
		repositoryProcesoCobroVentas.flush();

		return cabId;
	}
	
	private void removeMovimientoEgreso(Integer carNumero) {
		MovimientoEgreso movEgreso = repositoryMovimientoEgreso.findByMegProceso(carNumero);
		List<MovimientoItemEgreso> movItemEgreso = repositoryMovimientoItemEgreso.findByCabId(movEgreso.getMegNumero());
		for (MovimientoItemEgreso movimientoItemEgreso : movItemEgreso) {
			repositoryMovimientoItemEgreso.delete(movimientoItemEgreso);
		}
		repositoryMovimientoEgreso.delete(movEgreso);
	}
	
	public void remove(Venta venta) {
		repository.delete(venta);
	}

	public Optional<Venta> findById(Long id) {
		return repository.findById(id);
	}

	public Optional<Venta> findNota(Long id, String situacion) {
		return repository.findByIdAndSituacion(id, situacion);
	}

	public long getRowCount() {
		return repository.getMaxId();
	}

	public Long getNroTimbrado() {
		return repository.getMaxNroTimbrado();
	}
	
	public List<Venta> findByClienteId(Long id) {
		return repository.findByCliente(new Cliente(id));
	}

	public List<ConsultaNota> retriveVentasByProductoId(Long productoId) {
		return repository.getVentasByProductoId(productoId);
	}

	public List<Object[]> retriveVentaDetalleByIdVenta(Long ventaId) {
		return repository.getVentaDetallesByVentaId(ventaId);
	}

	public Optional<Venta> findByVentaIdAndClienteId(Long id, Long clienteId) {
		return repository.findByIdAndCliente(id, new Cliente(clienteId));
	}

	public List<ConsultaNota> retrieveVentasDelDia(Date fecha) {
		return repository.getVentasDelDia(fecha);
	}

	private void updateStockProduct(List<VentaDetalle> items, int habilitaLanzamientoCaja, int depesitoId) {
		List<Producto> productos = new ArrayList<>();
		for (VentaDetalle e : items) {
			Optional<Producto> pOptional = repositoryProducto.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				//Double salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
				Double cantItem = e.getCantidad();
				if (habilitaLanzamientoCaja == 1) {
					/*switch (depesitoId) {
					case 1:
						Double depBloq = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
						p.setDepO1Bloq(depBloq - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 2:
						Double depBloq02 = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
						p.setDepO2Bloq(depBloq02 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 3:
						Double depBloq03 = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
						p.setDepO3Bloq(depBloq03 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 4:
						Double depBloq04 = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
						p.setDepO4Bloq(depBloq04 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 5:
						Double depBloq05 = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
						p.setDepO5Bloq(depBloq05 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					default:
						break;
					}
					*/
				} else {
					switch (depesitoId) {
					case 1:
						//Double depBloq = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
						Double dep01 = p.getDepO1() != null ? p.getDepO1() : 0;
						//p.setDepO1Bloq(depBloq - cantItem);
						p.setDepO1(dep01 - cantItem);
						break;
					case 2:
						//Double depBloq02 = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
						Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
						//p.setDepO2Bloq(depBloq02 - cantItem);
						p.setDepO2(dep02 - cantItem);
						break;
					case 3:
						//Double depBloq03 = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
						Double dep03 = p.getDepO3() != null ? p.getDepO3() : 0;
						//p.setDepO3Bloq(depBloq03 - cantItem);
						p.setDepO3(dep03 - cantItem);
						break;
					case 4:
						//Double depBloq04 = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
						Double dep04 = p.getDepO4() != null ? p.getDepO4() : 0;
						//p.setDepO4Bloq(depBloq04 - cantItem);
						p.setDepO4(dep04 - cantItem);
						break;
					case 5:
						//Double depBloq05 = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
						Double dep05 = p.getDepO5() != null ? p.getDepO5() : 0;
						//p.setDepO5Bloq(depBloq05 - cantItem);
						p.setDepO5(dep05 - cantItem);
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
	
	private void updateStockProductRemoved(List<VentaDetalle> items, int habilitaLanzamientoCaja, int depesitoId) {
		List<Producto> productos = new ArrayList<>();
		for (VentaDetalle e : items) {
			Optional<Producto> pOptional = repositoryProducto.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				//Double salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
				Double cantItem = e.getCantidad();
				if (habilitaLanzamientoCaja == 1) {
					/*switch (depesitoId) {
					case 1:
						Double depBloq = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
						p.setDepO1Bloq(depBloq - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 2:
						Double depBloq02 = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
						p.setDepO2Bloq(depBloq02 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 3:
						Double depBloq03 = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
						p.setDepO3Bloq(depBloq03 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 4:
						Double depBloq04 = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
						p.setDepO4Bloq(depBloq04 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					case 5:
						Double depBloq05 = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
						p.setDepO5Bloq(depBloq05 - cantItem);
						p.setSalidaPend(salPend + cantItem);
						break;
					default:
						break;
					}
					*/
				} else {
					switch (depesitoId) {
					case 1:
						//Double depBloq = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
						Double dep01 = p.getDepO1() != null ? p.getDepO1() : 0;
						//p.setDepO1Bloq(depBloq - cantItem);
						p.setDepO1(dep01 + cantItem);
						break;
					case 2:
						//Double depBloq02 = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
						Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
						//p.setDepO2Bloq(depBloq02 - cantItem);
						p.setDepO2(dep02 + cantItem);
						break;
					case 3:
						//Double depBloq03 = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
						Double dep03 = p.getDepO3() != null ? p.getDepO3() : 0;
						//p.setDepO3Bloq(depBloq03 - cantItem);
						p.setDepO3(dep03 + cantItem);
						break;
					case 4:
						//Double depBloq04 = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
						Double dep04 = p.getDepO4() != null ? p.getDepO4() : 0;
						//p.setDepO4Bloq(depBloq04 - cantItem);
						p.setDepO4(dep04 + cantItem);
						break;
					case 5:
						//Double depBloq05 = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
						Double dep05 = p.getDepO5() != null ? p.getDepO5() : 0;
						//p.setDepO5Bloq(depBloq05 - cantItem);
						p.setDepO5(dep05 + cantItem);
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
	
	private void openMovCaja(Venta venta, String condicion) {
		int cant = 1;
		// cierre de caja del dia anterio
		MovimientoCaja movCaja = new MovimientoCaja();
		movCaja.setCaja(new Caja(1l));
		movCaja.setFecha(new Date());
		movCaja.setMoneda(new Moneda(1l));
		movCaja.setNotaNro(venta.getId().toString());
		movCaja.setNotaReferencia(venta.getClienteNombre());
		movCaja.setNotaValor(venta.getTotalGeneral());
		movCaja.setPlanCuentaId(1);
		movCaja.setTipoOperacion("E");
		movCaja.setUsuario(GlobalVars.USER_ID);
		movCaja.setValorM01(venta.getTotalGeneral());
		if (condicion.toString().equalsIgnoreCase("Contado")) {
			movCaja.setObs("Pagado en caja 01 ");
			movCaja.setSituacion("PAGADO");
		} else if (condicion.toString().equalsIgnoreCase("30 días")) {
			cant = 1;// Integer.valueOf(tfCuotaCant.getText());
			movCaja.setObs("Crédito a cuotas :" + cant);
			movCaja.setSituacion("PROCESADO");
		} 
		repositoryPago.save(movCaja);
		repositoryPago.flush();
	}
	
	private void movimientoIngresoProcesoCobroVenta(Venta venta) {
		int tipoEntidadCliente=0;
		MovimientoIngreso m = new MovimientoIngreso();
		m.setFecha(new Date());
		m.setHora(new Date());
		m.setMinCaja(1);
		m.setMinDocumento(venta.getId().toString());
		m.setMinEntidad(venta.getCliente().getId().toString());
		m.setMinProceso(Integer.valueOf(venta.getId().toString()));
		m.setMinTipoProceso(1);
		try {
			tipoEntidadCliente =venta.getCliente().getTipoEntidad();
		} catch (Exception e) {
			// TODO: handle exception
		}
		m.setMinTipoEntidad(tipoEntidadCliente);
		m.setMinSituacion(0);
		m = repositoryMovimientoIngreso.save(m);
		repositoryMovimientoIngreso.flush();

		MovimientoItemIngreso mii = new MovimientoItemIngreso();
		mii.setMiiNumero(m.getMinNumero());
		mii.setMiiIngreso(1);
		double monto = (double) Math.round(venta.getTotalGeneral() / 11);
		mii.setMiiMonto(monto);
		repositoryMovimientoItemIngreso.save(mii);

		MovimientoItemIngreso miiva = new MovimientoItemIngreso();
		miiva.setMiiNumero(m.getMinNumero());
		miiva.setMiiIngreso(11);
		miiva.setMiiMonto(venta.getTotalGeneral() - monto);
		repositoryMovimientoItemIngreso.save(miiva);
		repositoryMovimientoIngreso.flush();
		repositoryMovimientoItemIngreso.flush();

		ProcesoCobroVentas pcv = new ProcesoCobroVentas();
		pcv.setPveVenta(venta.getId().intValue());
		pcv.setPveIngresoegreso(1);
		pcv.setPveTipoproceso(31);
		pcv.setPveProceso(m.getMinNumero());
		pcv.setPveFlag(1);
		repositoryProcesoCobroVentas.save(pcv);
		repositoryProcesoCobroVentas.flush();
	}
	
	private CuentaARecibir cuentaARecibirProcesoCobroVenta(Venta venta, String condicion) {
		CuentaARecibir cuentaARecibir = new CuentaARecibir();

		cuentaARecibir.setFecha(new Date());
		cuentaARecibir.setHora(new Date());
		cuentaARecibir.setNroBoleta(venta.getComprobante());
		cuentaARecibir.setIdEntidad(venta.getCliente().getId());
		cuentaARecibir.setTipoEntidad(2);
		cuentaARecibir.setMonto(venta.getTotalGeneral());
		cuentaARecibir.setCarProceso(venta.getId().intValue());
		cuentaARecibir.setCarSituacion(0);
		cuentaARecibir = repositoryCuentaARecibir.save(cuentaARecibir);
		

		List<ItemCuentaARecibir> listaItemCuentaARecibir = new ArrayList<ItemCuentaARecibir>();

		int cant = 0;
		int cantDias = 0;
		Date fechaVencimiento = new Date();
		Calendar cal = Calendar.getInstance();
		if (condicion.toString().equalsIgnoreCase("30 días")) {
			cant = Integer.valueOf(1);
			cantDias = 30;
		}
		Double valorTotal = venta.getTotalGeneral() / cant;
		for (int i = 0; i < cant; i++) {
			ItemCuentaARecibir itemCuentaARecibir = new ItemCuentaARecibir();
			cal.add(Calendar.MONTH, 1);
			itemCuentaARecibir.setIcaCuenta(cuentaARecibir.getCarNumero());
			itemCuentaARecibir.setIcaMonto(valorTotal);
			itemCuentaARecibir.setIcaSituacion(0);
			itemCuentaARecibir.setIcaDocumento(cant + "/" + (i + 1));
			itemCuentaARecibir.setIcaIva(0d);
//			if (!tfCondicionPago.getText().equalsIgnoreCase("100"))
//				itemCuentaARecibir.setIcaVencimiento(fechaVencimiento);
//			else
			itemCuentaARecibir.setIcaVencimiento(cal.getTime());
			itemCuentaARecibir.setIcaDias(cantDias);

			listaItemCuentaARecibir.add(itemCuentaARecibir);
		}
		repositoryItemCuentaARecibir.saveAll(listaItemCuentaARecibir);
		repositoryItemCuentaARecibir.flush();
		// Proceso Cobro ventas
		ProcesoCobroVentas pcv = new ProcesoCobroVentas();
		pcv.setPveVenta(venta.getId().intValue());
		pcv.setPveIngresoegreso(2);
		pcv.setPveTipoproceso(31);
		pcv.setPveProceso(cuentaARecibir.getCarNumero());
		pcv.setPveFlag(1);
		repositoryProcesoCobroVentas.save(pcv);
		repositoryCuentaARecibir.flush();
		repositoryProcesoCobroVentas.flush();

		return cuentaARecibir;
	}
	
	private void openMovimientoEgreso(CuentaARecibir c) {
		MovimientoEgreso movEgreso = new MovimientoEgreso();
		movEgreso.setFecha(new Date());
		movEgreso.setHora(new Date());
		movEgreso.setMegProceso(c.getCarNumero());
		movEgreso.setMegTipoProceso(31);
		movEgreso.setMegEntidad(c.getIdEntidad().toString());
		movEgreso.setMegSituacion(0);
		movEgreso.setMegDocumento(c.getNroBoleta());
		movEgreso = repositoryMovimientoEgreso.save(movEgreso);
		MovimientoItemEgreso movItemEgreso = new MovimientoItemEgreso();
		movItemEgreso.setMieNumero(movEgreso.getMegNumero());
		movItemEgreso.setMieEgreso(31);
		movItemEgreso.setMieMonto(c.getMonto());
		movItemEgreso.setMieDescripcion("Egreso de efectivo - Venta Crédito");
		repositoryMovimientoItemEgreso.save(movItemEgreso);
		repositoryMovimientoEgreso.flush();
		repositoryMovimientoItemEgreso.flush();
	}
}