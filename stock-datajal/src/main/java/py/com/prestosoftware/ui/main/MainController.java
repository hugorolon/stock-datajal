package py.com.prestosoftware.ui.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import py.com.prestosoftware.ui.search.ConsultaBoletaDialog;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.ConsultaSaldoDeposito;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.CompraPanel;
import py.com.prestosoftware.ui.transactions.AjusteCuentaCreditoPanel;
import py.com.prestosoftware.ui.transactions.AjusteCuentaDebitoPanel;
import py.com.prestosoftware.ui.transactions.AjusteStockPanel;
import py.com.prestosoftware.ui.transactions.DevolucionPanel;
import py.com.prestosoftware.ui.transactions.EntregaBoletaPanel;
import py.com.prestosoftware.ui.transactions.LanzamientoCaja;
import py.com.prestosoftware.ui.transactions.PDV;
import py.com.prestosoftware.ui.transactions.AperturaCierrePanel;
import py.com.prestosoftware.ui.transactions.ConfiguracionPanel;
import py.com.prestosoftware.ui.transactions.AnulacionBoletaPanel;
import py.com.prestosoftware.ui.transactions.PresupuestoPanel;
import py.com.prestosoftware.ui.transactions.TransferenciaPanel;
import py.com.prestosoftware.ui.transactions.VentaPanel;

@Controller
public class MainController extends AbstractFrameController {
	
	// Controllers
    @Autowired private EmpresaController empresaController;
    @Autowired private ClienteController clientController;
    @Autowired private CiudadController ciudadController;
    @Autowired private DepartamentoController departamentoControler;
    @Autowired private PaisController paisController;
    @Autowired private ProveedorController proveedorController;
    @Autowired private ProductoController productController;
    @Autowired private ListaPrecioController listaPrecioController;
    @Autowired private CategoriaController categoriaController;
    @Autowired private GrupoController grupoController;
    @Autowired private SubgrupoController subgrupoController;
    @Autowired private NcmController ncmController;
    @Autowired private ImpuestoController impuestoController;
    @Autowired private MarcaController marcaController;
    @Autowired private UnidadMedidaController umedidaController;
    @Autowired private ColorController colorController;
    @Autowired private TamanhoController tamanhoController;
    @Autowired private DepositoController depositoController;
    @Autowired private UsuarioController usuarioController;
    @Autowired private CajaController cajaController;
    @Autowired private MonedaController monedaController;
    @Autowired private CotizacionController cotizacionController;
    @Autowired private PlanCuentaController planCuentaController;
    @Autowired private RolController rolController;
    @Autowired private EmpaqueController empaqueController;
    @Autowired private EntregaBoletaPanel entregaBoletaPanel;
    @Autowired private CondicionPagoController condicionPagoController;
    
    //MainForm
    @Autowired private MainFrame mainMenuFrame;
    @Autowired private LoginForm loginForm;

    //Forms
    @Autowired private AperturaCierrePanel movCaja;
    
    //Transactions
    @Autowired private LanzamientoCaja lanzamientoCaja;
    @Autowired private PresupuestoPanel presupuestoPanel;
    @Autowired private TransferenciaPanel transferenciaPanel;
    @Autowired private DevolucionPanel devolucionPanel;
    @Autowired private AjusteStockPanel ajusteStockPanel;
    @Autowired private CompraPanel panelCompra;
    @Autowired private VentaPanel ventaPanel;
    @Autowired private AjusteCuentaDebitoPanel ajusteCuentaDebitoPanel;
    @Autowired private AjusteCuentaCreditoPanel ajusteCuentaCreditoPanel;
    @Autowired private AnulacionBoletaPanel anulacionBoletaPanel;
    @Autowired private ConfiguracionPanel configPanel;
    @Autowired private PDV pdv;
    
    //Dialogs
    @Autowired private ConsultaBoletaDialog boletaPanel;
    @Autowired private ConsultaSaldoDeposito saldoDepositoPanel;
    @Autowired private ConsultaProveedor consultaProveedor;
    @Autowired private ConsultaCliente consultaCliente;
    @Autowired private ProductoDialog productoDialog;
    
    public MainController() {}

    public void prepareAndOpenFrame() {
    	registerAction(mainMenuFrame.getBtnClientes(), (e) -> openConsultaCliente());
    	registerAction(mainMenuFrame.getBtnProveedores(), (e) -> openConsultaProveedor());
    	registerAction(mainMenuFrame.getBtnProductos(), (e) -> openSaldoDeposito());
    	registerAction(mainMenuFrame.getBtnVentas(), (e) -> openVenta());
    	registerAction(mainMenuFrame.getBtnCompras(), (e) -> openCompra());
    	registerAction(mainMenuFrame.getBtnPDV(), (e) -> openPDV());
    	registerAction(mainMenuFrame.getBtnLanzamientos(), (e) -> openLanzamientoCaja());
    	
    	registerOpenMenu(mainMenuFrame.getMnuConsultaClientes(), (e) -> openConsultaCliente());
    	registerOpenMenu(mainMenuFrame.getMnuConsultaProveedor(), (e) -> openConsultaProveedor());
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
    	registerOpenMenu(mainMenuFrame.getMnuEmpresa(), (e) -> openEmpresa());
    	registerOpenMenu(mainMenuFrame.getMnuDeposito(), (e) -> openDeposito());
    	registerOpenMenu(mainMenuFrame.getMnuUsuario(), (e) -> openUsuario());
    	registerOpenMenu(mainMenuFrame.getMnuCaja(), (e) -> openCaja());
    	registerOpenMenu(mainMenuFrame.getMnuAperturaCaja(), (e) -> openAperturaCierreCaja(1));
    	registerOpenMenu(mainMenuFrame.getMnuCierreCaja(), (e) -> openAperturaCierreCaja(2));
    	registerOpenMenu(mainMenuFrame.getMnuLanzamiento(), (e) -> openLanzamientoCaja());
    	registerOpenMenu(mainMenuFrame.getMnuVenta(), (e) -> openVenta());
    	registerOpenMenu(mainMenuFrame.getMnuCompra(), (e) -> openCompra());
    	registerOpenMenu(mainMenuFrame.getMnuPresupuesto(), (e) -> openPresupuesto());
    	registerOpenMenu(mainMenuFrame.getMnuTransferencia(), (e) -> openTransferencia());
    	registerOpenMenu(mainMenuFrame.getMnuDevolucionCompra(), (e) -> openDevolucionCompra());
    	registerOpenMenu(mainMenuFrame.getMnuDevolucionVenta(), (e) -> openDevolucionVenta());
    	registerOpenMenu(mainMenuFrame.getMnuMoneda(), (e) -> openMoneda());
    	registerOpenMenu(mainMenuFrame.getMnuCotizacion(), (e) -> openCotizacion());
    	registerOpenMenu(mainMenuFrame.getMnuPlanDeCuenta(), (e) -> openPlanCuenta());
    	registerOpenMenu(mainMenuFrame.getMnuSubgrupo(), (e) -> openSubgrupo());
    	registerOpenMenu(mainMenuFrame.getMnuRol(), (e) -> openRol());
    	registerOpenMenu(mainMenuFrame.getMnuBoleta(), (e) -> openConsultaBoleta());
    	registerOpenMenu(mainMenuFrame.getMnuSaldoDeposito(), (e) -> openSaldoDeposito());
    	registerOpenMenu(mainMenuFrame.getMnuSaldoStock(), (e) -> openSaldoStock());
    	registerOpenMenu(mainMenuFrame.getMnuAjusteEntrada(), (e) -> openAjusteEntrada());
    	registerOpenMenu(mainMenuFrame.getMnuAjusteSalida(), (e) -> openAjusteSalida());
    	registerOpenMenu(mainMenuFrame.getMnuDevolucionVenta(), (e) -> openDevolucionVenta());
    	registerOpenMenu(mainMenuFrame.getMnuDevolucionCompra(), (e) -> openDevolucionCompra());
    	
    	registerOpenMenu(mainMenuFrame.getMnuDebitoCliente(), (e) -> openCuentaDebitoCliente());
    	registerOpenMenu(mainMenuFrame.getMnuDebitoProveedor(), (e) -> openCuentaDebitoProveedor());
    	registerOpenMenu(mainMenuFrame.getMnuCreditoCliente(), (e) -> openCuentaCreditoCliente());
    	registerOpenMenu(mainMenuFrame.getMnuCreditoProveedor(), (e) -> openCuentaCreditoProveedor());
    	
    	registerOpenMenu(mainMenuFrame.getMnuAnularBoleta(), (e) -> openAnulacionBoleta());
    	registerOpenMenu(mainMenuFrame.getMnuEmpaque(), (e) -> openEntregaBoletaPanel());
    	
    	registerOpenMenu(mainMenuFrame.getMnuPDV(), (e) -> openPDV());
    	registerOpenMenu(mainMenuFrame.getMnuConfig(), (e) -> openConfigPanel());
    	registerOpenMenu(mainMenuFrame.getMnuEmpaque_2(), (e) -> openEmpaque());
    	
    	registerOpenMenu(mainMenuFrame.getMnuCondicionDePago(), (e) -> openCondicionPago());

    	openLoginForm();
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
    
    private void openSaldoStock () {
    	productoDialog.setVisible(true);
    }
    
    private void openLoginForm() {
    	loginForm.setVisible(true);
    }
    
    private void openSaldoDeposito() {
		saldoDepositoPanel.setVisible(true);
		
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
    	panelCompra.setVisible(true);
    }
    
    private void openPresupuesto() {
    	presupuestoPanel.setVisible(true);
    	presupuestoPanel.newPresupuesto();
    }
    
    private void openTransferencia() {
    	transferenciaPanel.setVisible(true);
    	transferenciaPanel.newTransf();
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
    	lanzamientoCaja.clearForm();
    	lanzamientoCaja.setVisible(true);
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
    
    private void openCaja() {
    	cajaController.prepareAndOpenFrame();
    }
    
    private void openMercaderia() {
    	productController.addNewProduct();
    	productController.prepareAndOpenFrame();
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
    	ventaPanel.getConfig();
    	ventaPanel.setVisible(true);
    	ventaPanel.clearForm();
    	ventaPanel.newVenta();
    }
    
    private void openAperturaCierreCaja(int tipo) {
    	movCaja.setTipoMov(tipo);
    	movCaja.setVisible(true);
    }

}