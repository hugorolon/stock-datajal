package py.com.prestosoftware.ui.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.ClientePaisService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.CondicionPagoService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.CuentaAPagarService;
import py.com.prestosoftware.domain.services.CuentaARecibirService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ItemCuentaAPagarService;
import py.com.prestosoftware.domain.services.ItemCuentaARecibirService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.MovimientoEgresoService;
import py.com.prestosoftware.domain.services.MovimientoIngresoService;
import py.com.prestosoftware.domain.services.MovimientoItemEgresoService;
import py.com.prestosoftware.domain.services.MovimientoItemIngresoService;
import py.com.prestosoftware.domain.services.ProcesoCobroVentasService;
import py.com.prestosoftware.domain.services.ProcesoPagoComprasService;
import py.com.prestosoftware.domain.services.ProcesoPagoProveedoresService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.services.UsuarioRolService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.domain.services.VentaTemporalService;
import py.com.prestosoftware.domain.validations.CompraValidator;
import py.com.prestosoftware.domain.validations.VentaTemporalValidator;
import py.com.prestosoftware.domain.validations.VentaValidator;
import py.com.prestosoftware.ui.controllers.CajaController;
import py.com.prestosoftware.ui.controllers.CategoriaController;
import py.com.prestosoftware.ui.controllers.CiudadController;
import py.com.prestosoftware.ui.controllers.ClienteController;
import py.com.prestosoftware.ui.controllers.ColorController;
import py.com.prestosoftware.ui.controllers.CondicionPagoController;
import py.com.prestosoftware.ui.controllers.CotizacionController;
import py.com.prestosoftware.ui.controllers.DepartamentoController;
import py.com.prestosoftware.ui.controllers.DepositoController;
import py.com.prestosoftware.ui.controllers.EmpaqueController;
import py.com.prestosoftware.ui.controllers.EmpresaController;
import py.com.prestosoftware.ui.controllers.GrupoController;
import py.com.prestosoftware.ui.controllers.ImpuestoController;
import py.com.prestosoftware.ui.controllers.ListaPrecioController;
import py.com.prestosoftware.ui.controllers.MarcaController;
import py.com.prestosoftware.ui.controllers.MonedaController;
import py.com.prestosoftware.ui.controllers.NcmController;
import py.com.prestosoftware.ui.controllers.PaisController;
import py.com.prestosoftware.ui.controllers.PlanCuentaController;
import py.com.prestosoftware.ui.controllers.ProductoController;
import py.com.prestosoftware.ui.controllers.ProveedorController;
import py.com.prestosoftware.ui.controllers.RolController;
import py.com.prestosoftware.ui.controllers.SubgrupoController;
import py.com.prestosoftware.ui.controllers.TamanhoController;
import py.com.prestosoftware.ui.controllers.UnidadMedidaController;
import py.com.prestosoftware.ui.controllers.UsuarioController;
import py.com.prestosoftware.ui.controllers.UsuarioRolController;
import py.com.prestosoftware.ui.forms.ClienteAddPanel;
import py.com.prestosoftware.ui.forms.ProveedorAddPanel;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.reports.InformeAgrupadoIngresoEgresoCajaDialog;
import py.com.prestosoftware.ui.reports.InformeResumenCajaDialog;
import py.com.prestosoftware.ui.reports.InformeStockDeposito;
import py.com.prestosoftware.ui.reports.UtilidadProductoDialog;
import py.com.prestosoftware.ui.search.CompraDialog;
import py.com.prestosoftware.ui.search.CondicionPagoDialog;
import py.com.prestosoftware.ui.search.ConsultaBoletaDialog;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaFacturacionDelDiaDialog;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.ConsultaSaldoDeposito;
import py.com.prestosoftware.ui.search.ConsultaVentasDelDiaDialog;
import py.com.prestosoftware.ui.search.CuentaPagarDialog;
import py.com.prestosoftware.ui.search.CuentaRecibirDialog;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.HistoricoCompraDialog;
import py.com.prestosoftware.ui.search.HistoricoVentaDialog;
import py.com.prestosoftware.ui.search.MovimientoEgresoDialog;
import py.com.prestosoftware.ui.search.ProductoVistaDialog;
import py.com.prestosoftware.ui.search.VendedorDialog;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.table.ClienteConsultaTableModel;
//import py.com.prestosoftware.ui.shared.CompraPanel;
import py.com.prestosoftware.ui.table.CompraItemTableModel;
import py.com.prestosoftware.ui.table.CompraTableModel;
import py.com.prestosoftware.ui.table.VentaItemTableModel;
import py.com.prestosoftware.ui.table.VentaItemTemporalTableModel;
import py.com.prestosoftware.ui.table.VentaTemporalTableModel;
import py.com.prestosoftware.ui.transactions.AjusteCuentaCreditoPanel;
import py.com.prestosoftware.ui.transactions.AjusteCuentaDebitoPanel;
import py.com.prestosoftware.ui.transactions.AjusteStockPanel;
import py.com.prestosoftware.ui.transactions.AnulacionBoletaPanel;
import py.com.prestosoftware.ui.transactions.AperturaCierrePanel;
import py.com.prestosoftware.ui.transactions.CobroClientePanel;
import py.com.prestosoftware.ui.transactions.CompraLocalPanel;
import py.com.prestosoftware.ui.transactions.ConfiguracionPanel;
import py.com.prestosoftware.ui.transactions.DevolucionPanel;
import py.com.prestosoftware.ui.transactions.EntregaBoletaPanel;
import py.com.prestosoftware.ui.transactions.FacturaLegalPanel;
import py.com.prestosoftware.ui.transactions.LanzamientoCaja;
import py.com.prestosoftware.ui.transactions.MovimientoEgresoPanel;
import py.com.prestosoftware.ui.transactions.MovimientoIngresoPanel;
import py.com.prestosoftware.ui.transactions.PDV;
import py.com.prestosoftware.ui.transactions.PagarProveedorPanel;
import py.com.prestosoftware.ui.transactions.PresupuestoPanel;
import py.com.prestosoftware.ui.transactions.TransferenciaPanel;
import py.com.prestosoftware.ui.transactions.TransformacionPanel;
import py.com.prestosoftware.ui.transactions.VentaPanel;

@Controller
public class MainController extends AbstractFrameController {

	// Controllers
	@Autowired
	private EmpresaController empresaController;
	@Autowired
	private ClienteController clientController;
	@Autowired
	private CiudadController ciudadController;
	@Autowired
	private DepartamentoController departamentoControler;
	@Autowired
	private PaisController paisController;
	@Autowired
	private ProveedorController proveedorController;
	@Autowired
	private ProductoController productController;
	@Autowired
	private ListaPrecioController listaPrecioController;
	@Autowired
	private CategoriaController categoriaController;
	@Autowired
	private GrupoController grupoController;
	@Autowired
	private SubgrupoController subgrupoController;
	@Autowired
	private NcmController ncmController;
	@Autowired
	private ImpuestoController impuestoController;
	@Autowired
	private MarcaController marcaController;
	@Autowired
	private UnidadMedidaController umedidaController;
	@Autowired
	private ColorController colorController;
	@Autowired
	private TamanhoController tamanhoController;
	@Autowired
	private DepositoController depositoController;
	@Autowired
	private UsuarioController usuarioController;
	@Autowired
	private UsuarioRolController usuarioRolController;
	@Autowired
	private CajaController cajaController;
	@Autowired
	private MonedaController monedaController;
	@Autowired
	private CotizacionController cotizacionController;
	@Autowired
	private PlanCuentaController planCuentaController;
	@Autowired
	private RolController rolController;
	@Autowired
	private EmpaqueController empaqueController;
	@Autowired
	private EntregaBoletaPanel entregaBoletaPanel;
	@Autowired
	private CondicionPagoController condicionPagoController;

	// MainForm
	@Autowired
	private MainFrame mainMenuFrame;
	@Autowired
	private LoginForm loginForm;

	// Forms
	@Autowired
	private AperturaCierrePanel movCaja;

	// Transactions
	@Autowired
	private LanzamientoCaja lanzamientoCaja;
	@Autowired
	private PresupuestoPanel presupuestoPanel;
	@Autowired
	private TransferenciaPanel transferenciaPanel;
	@Autowired
	private TransformacionPanel transformacionPanel;
	@Autowired
	private DevolucionPanel devolucionPanel;
	@Autowired
	private AjusteStockPanel ajusteStockPanel;
//	@Autowired
//	private CompraPanel panelCompra;

	@Autowired
	private VentaPanel ventaPanel;
	@Autowired
	private FacturaLegalPanel facturaLegaPanel;
	@Autowired
	private CompraLocalPanel compraLocalPanel;
	@Autowired
	private AjusteCuentaDebitoPanel ajusteCuentaDebitoPanel;
	@Autowired
	private AjusteCuentaCreditoPanel ajusteCuentaCreditoPanel;
	@Autowired
	private AnulacionBoletaPanel anulacionBoletaPanel;
	@Autowired
	private ConfiguracionPanel configPanel;
	@Autowired
	private PDV pdv;

	// Dialogs
	@Autowired
	private CompraDialog compraDialog;
	@Autowired
	private ConsultaBoletaDialog boletaPanel;
	@Autowired
	private ConsultaSaldoDeposito saldoDepositoPanel;
	@Autowired
	private HistoricoCompraDialog historicoCompraDialog;
	@Autowired
	private HistoricoVentaDialog historicoVentaDialog;
	@Autowired
	private CuentaRecibirDialog cuentaRecibirDialog;
	@Autowired
	private CuentaPagarDialog cuentaPagarDialog;
	@Autowired
	private ConsultaProveedor consultaProveedor;
	@Autowired
	private ConsultaCliente consultaCliente;
	@Autowired
	private ProductoVistaDialog productoDialog;
	@Autowired
	private InformeStockDeposito informeStockDeposito;
	@Autowired
	private UtilidadProductoDialog utilidadProductoDialog;
	@Autowired
	private InformeResumenCajaDialog informeResumenCajaDialog;
	@Autowired
	private InformeAgrupadoIngresoEgresoCajaDialog informeAgrupadoIngresoEgresoCajaDialog;
	@Autowired
	private MovimientoIngresoPanel movimientoIngresoPanel;
	@Autowired
	private MovimientoEgresoPanel movimientoEgresoPanel;
	@Autowired
	private MovimientoEgresoDialog movimientoEgresoDialog;
	@Autowired
	private CobroClientePanel cobroClientePanel;
	@Autowired
	private PagarProveedorPanel pagarProveedorPanel;
	@Autowired
	private ProductoVistaDialog productoDialogV;
	@Autowired
	private VentaService ventaService;
	@Autowired
	private VentaTemporalService ventaTemporalService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private VentaItemTableModel itemTableModel;
	@Autowired
	private VentaItemTemporalTableModel itemTableModelFacturacion;
	@Autowired
	private ClienteConsultaTableModel consultaClienteTableModel;
	@Autowired
	private VentaTemporalTableModel tableModelVentaTemporal;
	@Autowired
	private CompraItemTableModel compraItemTableModel;
	@Autowired
	private ConsultaProveedor proveedorDialog;
	@Autowired
	private CompraTableModel compraTableModel;
	
	@Autowired
	private ProveedorAddPanel proveedorAddPanel;
	@Autowired
	private CompraService compraService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private CondicionPagoDialog condicionPagoDialog;
	@Autowired
	private CompraValidator compraValidator;
	
	@Autowired
	private ConsultaCliente clientDialog;
	@Autowired
	private ConsultaFacturacionDelDiaDialog consultaFacturacionDelDiaDialog;
	@Autowired
	private VendedorDialog vendedorDialog;
	@Autowired
	private DepositoDialog depositoDialog;
	@Autowired
	private VentaValidator ventaValidator;
	@Autowired
	private VentaTemporalValidator ventaTemporalValidator;
	@Autowired
	private ClientePaisService clientePaisService;
	@Autowired
	private UsuarioService vendedorService;
	@Autowired
	private UsuarioRolService usuarioRolService;
	@Autowired
	private DepositoService depositoService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private CondicionPagoDialog condicionDialog;
	@Autowired
	private ConsultaSaldoDeposito saldoDeposito;
	@Autowired
	private CondicionPagoService condicionPagoService;
	@Autowired
	private ConfiguracionService configService;
	@Autowired
	private AperturaCierreCajaService movCajaService;
	@Autowired
	private CajaService cajaService;
	@Autowired
	private MovimientoCajaService pagoService;
	@Autowired
	private ConsultaVentasDelDiaDialog consultaVentasDelDiaDialog;
	@Autowired
	private MovimientoIngresoService movimientoIngresoService;
	@Autowired
	private MovimientoEgresoService movimientoEgresoService;
	@Autowired
	private MovimientoItemIngresoService movimientoItemIngresoService;
	@Autowired
	private MovimientoItemEgresoService movimientoItemEgresoService;
	@Autowired
	private ProcesoCobroVentasService procesoCobroVentasService;
	@Autowired
	private CuentaARecibirService cuentaARecibirService;
	@Autowired
	private ItemCuentaARecibirService itemCuentaARecibirService;
	@Autowired
	private ClienteAddPanel clienteAddPanel;
	@Autowired
	private ProcesoPagoComprasService procesoPagoComprasService;
	@Autowired
	private ProcesoPagoProveedoresService procesoPagoProveedoresService;
	@Autowired
	private CuentaAPagarService cuentaAPagarService;
	@Autowired
	private ItemCuentaAPagarService itemCuentaAPagarService;
	@Autowired
	private ProductoController productoController;


	public MainController() {
	}

	public void prepareAndOpenFrame() {
//		registerAction(mainMenuFrame.getBtnClientes(), (e) -> openConsultaCliente());
//		registerAction(mainMenuFrame.getBtnProveedores(), (e) -> openConsultaProveedor());
//		registerAction(mainMenuFrame.getBtnProductos(), (e) -> openSaldoDeposito());	
		registerAction(mainMenuFrame.getBtnVentas(), (e) -> openVenta());
		registerAction(mainMenuFrame.getBtnCompras(), (e) -> openCompraLocal());
	//	registerAction(mainMenuFrame.getBtnPDV(), (e) -> openPDV());
	//	registerAction(mainMenuFrame.getBtnLanzamientos(), (e) -> openLanzamientoCaja());

//		registerOpenMenu(mainMenuFrame.getMnuConsultaClientes(), (e) -> openConsultaCliente());
//		registerOpenMenu(mainMenuFrame.getMnuConsultaProveedor(), (e) -> openConsultaProveedor());
		registerOpenMenu(mainMenuFrame.getMnuCiudad(), (e) -> openCiudad());
		registerOpenMenu(mainMenuFrame.getMnuDepartamento(), (e) -> openDepartamento());
		registerOpenMenu(mainMenuFrame.getMnuPais(), (e) -> openPais());
		registerOpenMenu(mainMenuFrame.getMnuCliente(), (e) -> openCliente());
		registerOpenMenu(mainMenuFrame.getMnuProveedor(), (e) -> openProveedor());
		registerOpenMenu(mainMenuFrame.getMnuMercaderia(), (e) -> openMercaderia());
		registerOpenMenu(mainMenuFrame.getMnuListaPrecio(), (e) -> openListaPrecio());
		registerOpenMenu(mainMenuFrame.getMnuCategoria(), (e) -> openCategoria());
		registerOpenMenu(mainMenuFrame.getMnuGrupo(), (e) -> openGrupo());
		registerOpenMenu(mainMenuFrame.getMnuNcm(), (e) -> openNcm());
		registerOpenMenu(mainMenuFrame.getMnuImpuesto(), (e) -> openImpuesto());
		registerOpenMenu(mainMenuFrame.getMnuMarca(), (e) -> openMarca());
		registerOpenMenu(mainMenuFrame.getMnuUnidadMedida(), (e) -> openUnidadMedida());
		registerOpenMenu(mainMenuFrame.getMnuTamanhos(), (e) -> openTamanho());
		registerOpenMenu(mainMenuFrame.getMnuColores(), (e) -> openColor());
//		registerOpenMenu(mainMenuFrame.getMnuEmpresa(), (e) -> openEmpresa());
		//registerOpenMenu(mainMenuFrame.getMnuDeposito(), (e) -> openDeposito());
		registerOpenMenu(mainMenuFrame.getMnuUsuario(), (e) -> openUsuario());
		registerOpenMenu(mainMenuFrame.getMnuUsuarioRol(), (e) -> openUsuarioRol());
		//registerOpenMenu(mainMenuFrame.getMnuCaja(), (e) -> openCaja());
		//registerOpenMenu(mainMenuFrame.getMnuAperturaCaja(), (e) -> openAperturaCierreCaja(1));
		//registerOpenMenu(mainMenuFrame.getMnuCierreCaja(), (e) -> openAperturaCierreCaja(2));
		registerOpenMenu(mainMenuFrame.getMnuLanzamiento(), (e) -> openLanzamientoCaja());
		registerOpenMenu(mainMenuFrame.getMnuVenta(), (e) -> openVenta());
		registerOpenMenu(mainMenuFrame.getMnuCompra(), (e) -> openCompraLocal());
		registerOpenMenu(mainMenuFrame.getMnuPresupuesto(), (e) -> openPresupuesto());
//		registerOpenMenu(mainMenuFrame.getMnuTransferencia(), (e) -> openTransferencia());
		registerOpenMenu(mainMenuFrame.getMnuTransformacion(), (e) -> openTransformacion());
		registerOpenMenu(mainMenuFrame.getMnuFacturacion(), (e) -> openFacturacion());
		registerOpenMenu(mainMenuFrame.getMnuDevolucionCompra(), (e) -> openDevolucionCompra());
		registerOpenMenu(mainMenuFrame.getMnuDevolucionVenta(), (e) -> openDevolucionVenta());
//		registerOpenMenu(mainMenuFrame.getMnuMoneda(), (e) -> openMoneda());
//		registerOpenMenu(mainMenuFrame.getMnuCotizacion(), (e) -> openCotizacion());
//		registerOpenMenu(mainMenuFrame.getMnuPlanDeCuenta(), (e) -> openPlanCuenta());
		registerOpenMenu(mainMenuFrame.getMnuSubgrupo(), (e) -> openSubgrupo());
		registerOpenMenu(mainMenuFrame.getMnuRol(), (e) -> openRol());
//		registerOpenMenu(mainMenuFrame.getMnuBoleta(), (e) -> openConsultaBoleta());
//		registerOpenMenu(mainMenuFrame.getMnuSaldoDeposito(), (e) -> openSaldoDeposito());
//		registerOpenMenu(mainMenuFrame.getMnuSaldoStock(), (e) -> openSaldoStock());
		
		registerOpenMenu(mainMenuFrame.getMnuInfStockPorDeposito(), (e) -> openInformeStockDeposito());
		registerOpenMenu(mainMenuFrame.getMnuInfCuentaARecibirVencimientoCliente(), (e) -> openVencimientoCuentaARecibir());
		registerOpenMenu(mainMenuFrame.getMnuInfCuentaAPagarVencimientoProveedor(), (e) -> openVencimientoCuentaAPagar());
		registerOpenMenu(mainMenuFrame.getMnuInfResumenUtilidadProducto(), (e) -> openUtilidadProducto());
		registerOpenMenu(mainMenuFrame.getMnuInfResumenCajas(), (e) -> openInformeResumenCaja());
		registerOpenMenu(mainMenuFrame.getMnuInfResumenAgrupadoCajas(), (e) -> openInformeAgrupadoIngresoEgresoCaja());
		registerOpenMenu(mainMenuFrame.getMnuMovCajaIngreso(), (e) -> openCajaMovimientoIngreso());
		registerOpenMenu(mainMenuFrame.getMnuMovCajaEgreso(), (e) -> openCajaMovimientoEgreso());
		registerOpenMenu(mainMenuFrame.getMnuMovCuentaARecibirCobroCliente(), (e) -> openCobroCliente());
		registerOpenMenu(mainMenuFrame.getMnuMovCuentaAPagarPagoProveedor(), (e) -> openPagarProveedor());
		registerOpenMenu(mainMenuFrame.getMnuComprasPorDeposito(), (e) -> openHistoricoCompras());
		registerOpenMenu(mainMenuFrame.getMnuVentasPorDeposito(), (e) -> openHistoricoVentas());
//		registerOpenMenu(mainMenuFrame.getMnuAjusteEntrada(), (e) -> openAjusteEntrada());
//		registerOpenMenu(mainMenuFrame.getMnuAjusteSalida(), (e) -> openAjusteSalida());
//		registerOpenMenu(mainMenuFrame.getMnuDevolucionVenta(), (e) -> openDevolucionVenta());
//		registerOpenMenu(mainMenuFrame.getMnuDevolucionCompra(), (e) -> openDevolucionCompra());

//		registerOpenMenu(mainMenuFrame.getMnuDebitoCliente(), (e) -> openCuentaDebitoCliente());
//		registerOpenMenu(mainMenuFrame.getMnuDebitoProveedor(), (e) -> openCuentaDebitoProveedor());
//		registerOpenMenu(mainMenuFrame.getMnuCreditoCliente(), (e) -> openCuentaCreditoCliente());
//		registerOpenMenu(mainMenuFrame.getMnuCreditoProveedor(), (e) -> openCuentaCreditoProveedor());

//		registerOpenMenu(mainMenuFrame.getMnuAnularBoleta(), (e) -> openAnulacionBoleta());
//		registerOpenMenu(mainMenuFrame.getMnuEmpaque(), (e) -> openEntregaBoletaPanel());

//		registerOpenMenu(mainMenuFrame.getMnuPDV(), (e) -> openPDV());
		registerOpenMenu(mainMenuFrame.getMnuConfig(), (e) -> openConfigPanel());
//		registerOpenMenu(mainMenuFrame.getMnuEmpaque_2(), (e) -> openEmpaque());

//		registerOpenMenu(mainMenuFrame.getMnuCondicionDePago(), (e) -> openCondicionPago());

		openLoginForm();
	}

	private void openHistoricoCompras() {
		historicoCompraDialog.setVisible(true);
	}

	private void openHistoricoVentas() {
		historicoVentaDialog.setVisible(true);
	}
	
	private void openCondicionPago() {
		condicionPagoController.prepareAndOpenFrame();
	}

	private void openConfigPanel() {
		configPanel.loadInitial();
		configPanel.setVisible(true);
	}

	private void openPDV() {
		pdv.setVisible(true);
		pdv.getAperturaCaja();
	}

	public void openEntregaBoletaPanel() {
		entregaBoletaPanel.setVisible(true);
		entregaBoletaPanel.getNotasPagadas();
	}

	public void openAnulacionBoleta() {
		anulacionBoletaPanel.setVisible(true);
		anulacionBoletaPanel.getNotasPendientes();
	}

	public void openCuentaDebitoCliente() {
		ajusteCuentaDebitoPanel.setVisible(true);
		ajusteCuentaDebitoPanel.setTipoCuenta("CLIENTE");
		ajusteCuentaDebitoPanel.newAjusteCuenta();
	}

	public void openCuentaDebitoProveedor() {
		ajusteCuentaDebitoPanel.setVisible(true);
		ajusteCuentaDebitoPanel.setTipoCuenta("PROVEEDOR");
		ajusteCuentaDebitoPanel.newAjusteCuenta();
	}

	public void openCuentaCreditoCliente() {
		ajusteCuentaCreditoPanel.setVisible(true);
		ajusteCuentaCreditoPanel.setTipoCuenta("CLIENTE");
		ajusteCuentaCreditoPanel.newAjuste();
	}

	public void openCuentaCreditoProveedor() {
		ajusteCuentaCreditoPanel.setVisible(true);
		ajusteCuentaCreditoPanel.setTipoCuenta("PROVEEDOR");
		ajusteCuentaCreditoPanel.newAjuste();
	}

	public void openAjusteEntrada() {
		ajusteStockPanel.setVisible(true);
		ajusteStockPanel.newAjuste();
		ajusteStockPanel.validatePrecioCosto("ENTRADA");
		ajusteStockPanel.setTipoAjuste("ENTRADA");
	}

	public void openAjusteSalida() {
		ajusteStockPanel.setVisible(true);
		ajusteStockPanel.newAjuste();
		ajusteStockPanel.validatePrecioCosto("SALIDA");
		ajusteStockPanel.setTipoAjuste("SALIDA");
	}

	private void openConsultaCliente() {
		consultaCliente.getClientes("");
		consultaCliente.setVisible(true);
	}

	private void openConsultaProveedor() {
		consultaProveedor.setVisible(true);
	}

	private void openSaldoStock() {
		productoDialog.setVisible(true);
	}

	private void openLoginForm() {
		loginForm.setVisible(true);
	}

	private void openSaldoDeposito() {
		saldoDepositoPanel.setVisible(true);

	}
	
	private void openInformeStockDeposito() {
		informeStockDeposito.setVisible(true);
	}
	
	private void openVencimientoCuentaARecibir() {
		cuentaRecibirDialog.setVisible(true);
	}
	
	private void openVencimientoCuentaAPagar() {
		cuentaPagarDialog.setVisible(true);
	}

	private void openUtilidadProducto() {
		utilidadProductoDialog.setVisible(true);
	}
	
	private void openInformeResumenCaja() {
		informeResumenCajaDialog.setVisible(true);
	}
	
	private void openInformeAgrupadoIngresoEgresoCaja() {
		informeAgrupadoIngresoEgresoCajaDialog.setVisible(true);
	}
	
	private void openCajaMovimientoIngreso() {
		movimientoIngresoPanel.setVisible(true);
		movimientoIngresoPanel.newMov();
	}
	
	private void openCajaMovimientoEgreso() {
		movimientoEgresoPanel.setVisible(true);
		movimientoEgresoPanel.newMov();
	}
	
	private void openConsultaBoleta() {
		boletaPanel.setVisible(true);
	}

	private void openRol() {
		rolController.prepareAndOpenFrame();
	}

	private void openPlanCuenta() {
		planCuentaController.prepareAndOpenFrame();
	}

	private void openEmpaque() {
		empaqueController.prepareAndOpenFrame();
	}

	private void openSubgrupo() {
		subgrupoController.prepareAndOpenFrame();
	}

	private void openCotizacion() {
		cotizacionController.prepareAndOpenFrame();
	}

	private void openMoneda() {
		monedaController.prepareAndOpenFrame();
	}

	private void openCompra() {
		//panelCompra.setVisible(true);
	}

	private void openPresupuesto() {
		presupuestoPanel.setVisible(true);
		presupuestoPanel.getConfig();
		presupuestoPanel.newPresupuesto();
	}

	private void openTransferencia() {
		transferenciaPanel.setVisible(true);
		transferenciaPanel.newTransf();
	}
	
	private void openTransformacion() {
		transformacionPanel.setVisible(true);
		transformacionPanel.newTransf();
	}

	private void openFacturacion() {
		itemTableModelFacturacion= new VentaItemTemporalTableModel();
		consultaClienteTableModel = new ClienteConsultaTableModel();
		tableModelVentaTemporal = new VentaTemporalTableModel();
		clientDialog = new ConsultaCliente(clienteService, consultaClienteTableModel);
		consultaFacturacionDelDiaDialog = new ConsultaFacturacionDelDiaDialog(ventaTemporalService, tableModelVentaTemporal);
		facturaLegaPanel = new FacturaLegalPanel(itemTableModelFacturacion, clientDialog, depositoDialog,  productoDialog, ventaTemporalValidator, 
				ventaTemporalService, clienteService, clientePaisService, productoService,   saldoDeposito,
				 consultaFacturacionDelDiaDialog, clienteAddPanel);
		facturaLegaPanel.setVisible(true);
		facturaLegaPanel.newVenta();
	}

	private void openDevolucionVenta() {
		devolucionPanel.setVisible(true);
		devolucionPanel.setTypeDevolucion("VENTA");
		devolucionPanel.updateView();
		devolucionPanel.newDevolucion();
	}

	private void openDevolucionCompra() {
		devolucionPanel.setVisible(true);
		devolucionPanel.setTypeDevolucion("COMPRA");
		devolucionPanel.updateView();
		devolucionPanel.newDevolucion();
	}

	private void openLanzamientoCaja() {
		if(usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "CAJA")) {
			lanzamientoCaja.clearForm();
			lanzamientoCaja.setVisible(true);
		}
	}

	private void openDeposito() {
		depositoController.prepareAndOpenFrame();
	}

	private void openNcm() {
		ncmController.prepareAndOpenFrame();
	}

	private void openUsuario() {
		usuarioController.prepareAndOpenFrame();
	}

	private void openUsuarioRol() {
		usuarioRolController.prepareAndOpenFrame();
	}

	private void openCaja() {
		cajaController.prepareAndOpenFrame();
	}

	private void openMercaderia() {
		productController.addNewProduct();
		productController.prepareAndOpenFrame();
		productController.setOrigen("MENU");
	}

	private void openCliente() {
		clientController.addNewCliente();
		clientController.prepareAndOpenFrame();
	}

	private void openCiudad() {
		ciudadController.prepareAndOpenFrame();
	}

	private void openDepartamento() {
		departamentoControler.prepareAndOpenFrame();
	}

	private void openPais() {
		paisController.prepareAndOpenFrame();
	}

	private void openListaPrecio() {
		listaPrecioController.prepareAndOpenFrame();
	}

	private void openCategoria() {
		categoriaController.prepareAndOpenFrame();
	}

	private void openGrupo() {
		grupoController.prepareAndOpenFrame();
	}

	private void openMarca() {
		marcaController.setOrigen("MENU");
		marcaController.prepareAndOpenFrame();
	}

	private void openUnidadMedida() {
		umedidaController.prepareAndOpenFrame();
	}

	private void openTamanho() {
		tamanhoController.prepareAndOpenFrame();
	}

	private void openImpuesto() {
		impuestoController.prepareAndOpenFrame();
	}

	private void openProveedor() {
		proveedorController.addNewProveedor();
		proveedorController.prepareAndOpenFrame();
	}

	private void openColor() {
		colorController.prepareAndOpenFrame();
	}

	private void openEmpresa() {
		empresaController.prepareAndOpenFrame();
	}

	private void openVenta() {
		//inicializaVenta();
		itemTableModel= new VentaItemTableModel();
		ventaPanel = new VentaPanel(itemTableModel, clientDialog, vendedorDialog, depositoDialog, productoDialogV, ventaValidator, ventaService, clienteService, clientePaisService, vendedorService, usuarioRolService, depositoService, productoService, condicionDialog, saldoDeposito, condicionPagoService, configService, movCajaService, cajaService, pagoService, consultaVentasDelDiaDialog, movimientoIngresoService, movimientoEgresoService, movimientoItemIngresoService, movimientoItemEgresoService, procesoCobroVentasService, cuentaARecibirService, itemCuentaARecibirService, clienteAddPanel, productoController);
		ventaPanel.clearForm();
		ventaPanel.getConfig();
		ventaPanel.setVisible(true);
		ventaPanel.vistaPrecioCompra();
		//ventaPanel.vistaDescuentoItem();
		
		ventaPanel.getTfClienteID().requestFocus();
	}
	/*
	VentaItemTableModel itemTableModel, ConsultaCliente clientDialog, VendedorDialog vendedorDialog,
	DepositoDialog depositoDialog, ProductoVistaDialog productoDialog, VentaValidator ventaValidator,
	VentaService ventaService, ClienteService clienteService, ClientePaisService clientePaisService,
	UsuarioService vendedorService, UsuarioRolService usuarioRolService, DepositoService depositoService,
	ProductoService productoService, CondicionPagoDialog condicionDialog, ConsultaSaldoDeposito saldoDeposito,
	CondicionPagoService condicionPagoService, ConfiguracionService configService,
	AperturaCierreCajaService movCajaService, CajaService cajaService, MovimientoCajaService pagoService,
	ConsultaVentasDelDiaDialog consultaVentasDelDiaDialog, MovimientoIngresoService movimientoIngresoService,
	MovimientoEgresoService movimientoEgresoService, MovimientoItemIngresoService movimientoItemIngresoService,
	MovimientoItemEgresoService movimientoItemEgresoService,
	ProcesoCobroVentasService procesoCobroVentasService, CuentaARecibirService cuentaARecibirService,
	ItemCuentaARecibirService itemCuentaARecibirService, ClienteAddPanel clienteAddPanel
	
	private void inicializaVenta() {
		this.itemTableModel = new VentaItemTableModel();
		this.vendedorDialog = vendedorDialog;
		this.depositoDialog = depositoDialog;
		this.ventaValidator = new VentaValidator();
		//this.ventaService = ventaService;
		this.clienteService = new ClienteService(clienteRepository);
		
		this.clientePaisService = clientePaisService;
		this.vendedorService = vendedorService;
		this.depositoService = depositoService;
		this.productoService = productoService;
		this.saldoDeposito = saldoDeposito;
		this.condicionPagoService = condicionPagoService;
		this.condicionDialog = condicionDialog;
		this.configService = configService;
		this.usuarioRolService = usuarioRolService;
		this.movCajaService = movCajaService;
		this.cajaService = cajaService;
		this.pagoService = pagoService;
		this.cuentaARecibirService = cuentaARecibirService;
		this.consultaVentasDelDiaDialog = consultaVentasDelDiaDialog;
		this.movimientoIngresoService = movimientoIngresoService;
		this.movimientoEgresoService = movimientoEgresoService;
		this.movimientoItemIngresoService = movimientoItemIngresoService;
		this.movimientoItemEgresoService = movimientoItemEgresoService;
		this.procesoCobroVentasService = procesoCobroVentasService;
		this.cuentaARecibirService = cuentaARecibirService;
		this.itemCuentaARecibirService = itemCuentaARecibirService;
		this.clienteAddPanel = clienteAddPanel;
	}*/

	private void openCompraLocal() {
		compraItemTableModel= new CompraItemTableModel();
		compraDialog = new CompraDialog(compraService, compraTableModel);
		compraLocalPanel = new CompraLocalPanel(compraItemTableModel, proveedorDialog, proveedorAddPanel, compraDialog, productoDialog, 
				compraService, proveedorService, compraValidator, productoService, 
				 condicionPagoDialog, condicionPagoService, configService, movCajaService, cajaService, pagoService, 
				 	movimientoIngresoService,	movimientoItemIngresoService, movimientoEgresoService, movimientoItemEgresoService, procesoPagoComprasService,
					procesoPagoProveedoresService, cuentaAPagarService, itemCuentaAPagarService, productoController);
		compraLocalPanel.getConfig();
		compraLocalPanel.clearForm();
		compraLocalPanel.newCompra();
		compraLocalPanel.setVisible(true);
		compraLocalPanel.getTfProveedorID().requestFocus();
	}

	private void openAperturaCierreCaja(int tipo) {
		movCaja.setTipoMov(tipo);
		movCaja.setVisible(true);
	}

	private void openCobroCliente() {
		cobroClientePanel.setVisible(true);
		cobroClientePanel.newMov();
	}
	
	private void openPagarProveedor() {
		pagarProveedorPanel.setVisible(true);
		pagarProveedorPanel.newMov();
	}
	
	
}