package py.com.prestosoftware.ui.transactions;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.services.PlanCuentaService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.CajaComboBoxModel;
import py.com.prestosoftware.ui.table.ConfigCajaComboBoxModel;
import py.com.prestosoftware.ui.table.ConfigDepositoComboBoxModel;
import py.com.prestosoftware.ui.table.ConfigEmpresaDepositoCbModel;
import py.com.prestosoftware.ui.table.ConfigVisualizaDepositoComboBoxModel;
import py.com.prestosoftware.ui.table.DepositoComboBoxModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.PlanCuentaComboBoxModel;
import py.com.prestosoftware.ui.table.UsuarioComboBoxModel;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@Component
public class ConfiguracionPanel extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JCheckBox chPideVendedor;
	private JCheckBox chPideDeposito;
	private JCheckBox chVentaPorReferencia;
	private JCheckBox chPideDescuento;
	private JCheckBox chPideFlete;
	private JCheckBox chImprimeMoneda;
	private JCheckBox chImprimeNotaVentaOrdenAlf;
	private JComboBox<String> cbPrecioNota;
	private JLabel lblNewLabel;
	private JCheckBox chNoRepiteProducto;
	private JCheckBox chBloqueaVentaProdNegativo;
	private JCheckBox chImprimeNombreVendedor;
	private JPanel pnlCompra;
	private JPanel pnlPDV;
	private JComboBox<Deposito> cbDeposito;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JComboBox<Caja> cbCaja;
	private JButton btnGuardar;
	private JButton btnCerrar;
	
	private ConfiguracionService configService;
	private DepositoService depositoService;
	private CajaService cajaService;
	private EmpresaService empresaService;
	private UsuarioService usuarioService;
	private PlanCuentaService planCuentaService;
	
	private CajaComboBoxModel cajaComboBoxModel;
	private ConfigCajaComboBoxModel configCajaComboBoxModel;
	private DepositoComboBoxModel depositoComboBoxModel;
	private ConfigVisualizaDepositoComboBoxModel configVisualizaDepositoComboBoxModel;
	private ConfigEmpresaDepositoCbModel configEmpresadepositoComboBoxModel;
	private ConfigDepositoComboBoxModel configDepositoComboBoxModel;
	private EmpresaComboBoxModel empresaComboBoxModel;
	private UsuarioComboBoxModel usuarioComboBoxModel;
	private PlanCuentaComboBoxModel planCuentaComboBoxModel;
	
	private JTabbedPane tabbedPane_1;
	private JPanel panel;
	private JPanel panel_1;
	private JCheckBox chPideMoneda;
	private JCheckBox chPideDepositoCompra;
	private JCheckBox chPideGastoItem;
	private JCheckBox chPideFleteCompraLocal;
	private JCheckBox chImprimeNotaCompraResumida;
	private JCheckBox chActualizAutoCompra;
	private JPanel panel_2;
	private JLabel lblPrecioPCalculo;
	private JComboBox<String> cbCalculoPrecioVenta;
	private JLabel lblNewLabel_3;
	private JComboBox<String> cbProductoConIva;
	private JLabel lblUsuario;
	private JComboBox<Usuario> cbUsuarios;
	private JTabbedPane tabbedPane_2;
	private JPanel panel_3;
	private JLabel lblTipoDeComision;
	private JComboBox<String> cbTipoComisionVenta;
	private JCheckBox chNivelPrecioCliente;
	private JCheckBox chPideDispNotaVenta;
	private JCheckBox chPideDispNotaCompra;
	private JCheckBox chPideDispNotaPresupuesto;
	private JCheckBox chPideNotaTransferencia;
	private JCheckBox chPideDispAjusteEntrada;
	private JCheckBox chPideDispDevCompra;
	private JCheckBox chPideDispDevVenta;
	private JCheckBox chPideDispAjusteSalida;
	private JCheckBox chPideDispAjusteCuenta;
	private JLabel lblEmpresa;
	private JComboBox<Empresa> cbEmpresas;
	private JComboBox<Deposito> cbDepVentaUsuario;
	private JLabel lblDefineDeposito;
	private JComboBox<Caja> cbCajaUsuario;
	private JLabel lblAbreCaja;
	private JLabel lblDefineDepositoP;
	private JComboBox<Deposito> cbDepCompraUsuario;
	private JLabel lblOperacion;
	private JComboBox<PlanCuenta> cbOpeBloqCaja;
	private JLabel lblCajaTransferencia;
	private JComboBox<Caja> cbCajaTransfUsuario;
	private JLabel lblNewLabel_4;
	private JCheckBox chAutorizaVentaPrecioDUsuario;
	private JCheckBox chAutorizaVentaPrecioEUsuario;
	private JCheckBox chAutorizaVentaHastaCostoUsuario;
	private JCheckBox chAutorizaVentaBajoCostoUsuario;
	private JCheckBox chAutorizaPlazoPagoUsuario;
	private JLabel lblAutorizacionPieNota;
	private JCheckBox chAutorizaLimiteCreditoUsuario;
	private JCheckBox chDescNotaUsuario;
	private JPanel panel_4;
	private JLabel lblVisualizarDeposito;
	private JComboBox<Deposito> cbVisualizarDepositoStockUsuario;
	private JLabel lblVisualizarPrecios;
	private JCheckBox chPuedeVerPrecioA;
	private JCheckBox chPuedeVerPrecioB;
	private JCheckBox chPuedeVerPrecioC;
	private JCheckBox chPuedeVerPrecioD;
	private JCheckBox chPuedeVerPrecioE;
	private JCheckBox chVisualizaTodoPreciosUsuario;
	private JLabel lblVisualizarCostos;
	private JCheckBox chPuedeVerCostoFOB;
	private JCheckBox chPuedeVerCostoCIF;
	private JPanel panel_5;
	private JLabel lblLimiteDeDescuento;
	private JComboBox<String> cbTipoDescNota;
	private JLabel lblPdv;
	private JCheckBox chPuedeVentaDirContado;
	private JCheckBox chPuedeMsjFinalizaVenta;
	private JCheckBox chNotasRecibidaF3;
	private JCheckBox chMonitorDeCajaF4;
	private JCheckBox cHCodConReferencia;
	private JCheckBox chAlteraPrecioVenta;
	private JCheckBox chHabilitaDesc;
	private JTextField tfLimiteDescNotaValor;

	/**
	 * Create the dialog.
	 */
	@Autowired
	public ConfiguracionPanel(ConfiguracionService configService, DepositoService depositoService, 
			CajaService cajaService, CajaComboBoxModel cajaComboBoxModel, UsuarioService usuarioService, 
			DepositoComboBoxModel depositoComboBoxModel, EmpresaComboBoxModel empresaComboBoxModel,
			EmpresaService empresaService, UsuarioComboBoxModel usuarioComboBoxModel,
			PlanCuentaService planCuentaService, PlanCuentaComboBoxModel planCuentaComboBoxModel,
			ConfigCajaComboBoxModel configCajaComboBoxModel, ConfigDepositoComboBoxModel configDepositoComboBoxModel,
			ConfigEmpresaDepositoCbModel configEmpresadepositoComboBoxModel,
			ConfigVisualizaDepositoComboBoxModel configVisualizaDepositoComboBoxModel) {
		
		this.configService = configService;
		this.depositoService = depositoService;
		this.cajaService = cajaService;
		this.cajaComboBoxModel = cajaComboBoxModel;
		this.usuarioService = usuarioService;
		this.depositoComboBoxModel = depositoComboBoxModel;
		this.empresaComboBoxModel = empresaComboBoxModel;
		this.empresaService = empresaService;
		this.usuarioComboBoxModel = usuarioComboBoxModel;
		this.planCuentaService = planCuentaService;
		this.planCuentaComboBoxModel = planCuentaComboBoxModel;
		this.configCajaComboBoxModel = configCajaComboBoxModel;
		this.configDepositoComboBoxModel = configDepositoComboBoxModel;
		this.configEmpresadepositoComboBoxModel = configEmpresadepositoComboBoxModel;
		this.configVisualizaDepositoComboBoxModel = configVisualizaDepositoComboBoxModel;
		
		setupInit();
		setupUI();
	}
	
	private void setupInit() {
		setBounds(100, 100, 865, 567);
		getContentPane().setLayout(null);
		setTitle("Configuración del Sistema");

		Util.setupScreen(this);
	}
	
	private void setupUI() {
		btnGuardar = new JButton("Guardar");
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				}
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnGuardar.setBounds(595, 478, 117, 38);
		getContentPane().add(btnGuardar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				dispose();
			}
		});
		btnCerrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(717, 478, 117, 38);
		getContentPane().add(btnCerrar);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(6, 6, 828, 470);
		getContentPane().add(tabbedPane_1);
		
		panel = new JPanel();
		tabbedPane_1.addTab("Por Empresa", null, panel, null);
		panel.setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 51, 790, 383);
		panel.add(tabbedPane);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Basico", null, panel_2, null);
		panel_2.setLayout(null);
		
		lblPrecioPCalculo = new JLabel("Precio p/ calculo de Venta");
		lblPrecioPCalculo.setBounds(6, 6, 172, 22);
		panel_2.add(lblPrecioPCalculo);
		
		cbCalculoPrecioVenta = new JComboBox<String>();
		cbCalculoPrecioVenta.setModel(new DefaultComboBoxModel<String>(new String[] {"FIJO", "%"}));
		cbCalculoPrecioVenta.setBounds(179, 5, 100, 27);
		panel_2.add(cbCalculoPrecioVenta);
		
		lblNewLabel_3 = new JLabel("Registro Producto con IVA:");
		lblNewLabel_3.setBounds(6, 44, 179, 16);
		panel_2.add(lblNewLabel_3);
		
		cbProductoConIva = new JComboBox<String>();
		cbProductoConIva.setModel(new DefaultComboBoxModel<String>(new String[] {"IMPUESTOS"}));
		cbProductoConIva.setBounds(179, 37, 144, 27);
		panel_2.add(cbProductoConIva);
		
		lblTipoDeComision = new JLabel("Tipo de Comision Venta");
		lblTipoDeComision.setBounds(6, 72, 172, 16);
		panel_2.add(lblTipoDeComision);
		
		cbTipoComisionVenta = new JComboBox<String>();
		cbTipoComisionVenta.setModel(new DefaultComboBoxModel<String>(new String[] {"POR VENDEDOR", "POR PRODUCTO"}));
		cbTipoComisionVenta.setBounds(179, 68, 144, 27);
		panel_2.add(cbTipoComisionVenta);
		
		chPideDispNotaPresupuesto = new JCheckBox("Pide dispositivo nota de Presupuesto");
		chPideDispNotaPresupuesto.setBounds(6, 107, 273, 23);
		panel_2.add(chPideDispNotaPresupuesto);
		
		chPideNotaTransferencia = new JCheckBox("Pide dispositivo nota de Transferencia");
		chPideNotaTransferencia.setBounds(6, 142, 273, 23);
		panel_2.add(chPideNotaTransferencia);
		
		chPideDispAjusteEntrada = new JCheckBox("Pide dispositivo en Ajustes Entrada");
		chPideDispAjusteEntrada.setBounds(6, 177, 273, 23);
		panel_2.add(chPideDispAjusteEntrada);
		
		chPideDispDevCompra = new JCheckBox("Pide dispositivo en Dev. Compra");
		chPideDispDevCompra.setBounds(6, 245, 273, 23);
		panel_2.add(chPideDispDevCompra);
		
		chPideDispDevVenta = new JCheckBox("Pide dispositivo en Dev. de Venta");
		chPideDispDevVenta.setBounds(6, 280, 273, 23);
		panel_2.add(chPideDispDevVenta);
		
		chPideDispAjusteSalida = new JCheckBox("Pide dispositivo en Ajustes Salida");
		chPideDispAjusteSalida.setBounds(6, 212, 273, 23);
		panel_2.add(chPideDispAjusteSalida);
		
		chPideDispAjusteCuenta = new JCheckBox("Pide dispositivo en Ajustes de Cuenta");
		chPideDispAjusteCuenta.setBounds(6, 316, 273, 23);
		panel_2.add(chPideDispAjusteCuenta);
		
		JPanel pnlVenta = new JPanel();
		pnlVenta.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Opciones de Ventas", null, pnlVenta, null);
		tabbedPane.setEnabledAt(1, true);
		pnlVenta.setLayout(null);
		
		chPideVendedor = new JCheckBox("Pide Vendedor");
		chPideVendedor.setBounds(6, 6, 157, 23);
		pnlVenta.add(chPideVendedor);
		
		chPideDeposito = new JCheckBox("Pide Deposito");
		chPideDeposito.setBounds(6, 45, 157, 23);
		pnlVenta.add(chPideDeposito);
		
		chVentaPorReferencia = new JCheckBox("Venta por Referencia");
		chVentaPorReferencia.setBounds(6, 80, 195, 23);
		pnlVenta.add(chVentaPorReferencia);
		
		chPideDescuento = new JCheckBox("Pide Descuento");
		chPideDescuento.setBounds(6, 115, 195, 23);
		pnlVenta.add(chPideDescuento);
		
		chPideFlete = new JCheckBox("Pide Flete");
		chPideFlete.setBounds(6, 150, 195, 23);
		pnlVenta.add(chPideFlete);
		
		chImprimeMoneda = new JCheckBox("Imprime Moneda en la Nota");
		chImprimeMoneda.setBounds(6, 185, 216, 23);
		pnlVenta.add(chImprimeMoneda);
		
		chImprimeNotaVentaOrdenAlf = new JCheckBox("Imprime Venta por Orden Alf.");
		chImprimeNotaVentaOrdenAlf.setBounds(6, 220, 216, 23);
		pnlVenta.add(chImprimeNotaVentaOrdenAlf);
		
		cbPrecioNota = new JComboBox<String>();
		cbPrecioNota.setModel(new DefaultComboBoxModel<String>(new String[] {"Precio A", "Precio B", "Precio C", "Precio D", "Precio E"}));
		cbPrecioNota.setBounds(280, 143, 216, 23);
		pnlVenta.add(cbPrecioNota);
		
		lblNewLabel = new JLabel("Copia F3 traer precio");
		lblNewLabel.setBounds(280, 115, 181, 16);
		pnlVenta.add(lblNewLabel);
		
		chNoRepiteProducto = new JCheckBox("No repite Producto en la Nota Venta");
		chNoRepiteProducto.setBounds(280, 6, 269, 23);
		pnlVenta.add(chNoRepiteProducto);
		
		chBloqueaVentaProdNegativo = new JCheckBox("Bloquea venta prod. negativo");
		chBloqueaVentaProdNegativo.setBounds(280, 45, 269, 23);
		pnlVenta.add(chBloqueaVentaProdNegativo);
		
		chImprimeNombreVendedor = new JCheckBox("Imprime nombre del vendedor en la Nota");
		chImprimeNombreVendedor.setBounds(280, 80, 340, 23);
		pnlVenta.add(chImprimeNombreVendedor);
		
		chNivelPrecioCliente = new JCheckBox("Nivel de Precio por Cliente");
		chNivelPrecioCliente.setBounds(280, 220, 216, 23);
		pnlVenta.add(chNivelPrecioCliente);
		
		chPideDispNotaVenta = new JCheckBox("Pide dispositivo nota de Venta");
		chPideDispNotaVenta.setBounds(6, 255, 245, 23);
		pnlVenta.add(chPideDispNotaVenta);
		
		pnlCompra = new JPanel();
		tabbedPane.addTab("Opciones de Compra", null, pnlCompra, null);
		pnlCompra.setLayout(null);
		
		chPideMoneda = new JCheckBox("Pide Moneda");
		chPideMoneda.setBounds(6, 6, 128, 23);
		pnlCompra.add(chPideMoneda);
		
		chPideDepositoCompra = new JCheckBox("Pide Deposito");
		chPideDepositoCompra.setBounds(6, 41, 128, 23);
		pnlCompra.add(chPideDepositoCompra);
		
		chPideGastoItem = new JCheckBox("Pide Gasto en el Item");
		chPideGastoItem.setBounds(6, 76, 187, 23);
		pnlCompra.add(chPideGastoItem);
		
		chPideFleteCompraLocal = new JCheckBox("Pide Flete en Compra Local");
		chPideFleteCompraLocal.setBounds(6, 111, 201, 23);
		pnlCompra.add(chPideFleteCompraLocal);
		
		chImprimeNotaCompraResumida = new JCheckBox("Imprime Nota de Compra Resumida");
		chImprimeNotaCompraResumida.setBounds(6, 141, 267, 23);
		pnlCompra.add(chImprimeNotaCompraResumida);
		
		chActualizAutoCompra = new JCheckBox("Actualiza Autom. precio compra");
		chActualizAutoCompra.setBounds(6, 176, 267, 23);
		pnlCompra.add(chActualizAutoCompra);
		
		chPideDispNotaCompra = new JCheckBox("Pide dispositivo nota de Compra");
		chPideDispNotaCompra.setBounds(6, 212, 245, 23);
		pnlCompra.add(chPideDispNotaCompra);
		
		pnlPDV = new JPanel();
		tabbedPane.addTab("Opciones de PDV", null, pnlPDV, null);
		pnlPDV.setLayout(null);
		
		cbDeposito = new JComboBox<Deposito>(configEmpresadepositoComboBoxModel);
		cbDeposito.setBounds(89, 6, 190, 27);
		pnlPDV.add(cbDeposito);
		
		lblNewLabel_1 = new JLabel("Deposito:");
		lblNewLabel_1.setBounds(6, 10, 83, 16);
		pnlPDV.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Caja:");
		lblNewLabel_2.setBounds(6, 40, 61, 16);
		pnlPDV.add(lblNewLabel_2);
		
		cbCaja = new JComboBox<Caja>(cajaComboBoxModel);
		cbCaja.setBounds(89, 38, 190, 27);
		pnlPDV.add(cbCaja);
		
		panel_5 = new JPanel();
		tabbedPane.addTab("Impresion", null, panel_5, null);
		panel_5.setLayout(null);
		
		lblEmpresa = new JLabel("Seleccione Empresa:");
		lblEmpresa.setBounds(12, 12, 128, 27);
		panel.add(lblEmpresa);
		
		cbEmpresas = new JComboBox<Empresa>(empresaComboBoxModel);
		cbEmpresas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getConfigByEmpresa((Empresa)cbEmpresas.getSelectedItem(), true);
			}
		});
		cbEmpresas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//getConfigByEmpresa((Empresa)cbEmpresas.getSelectedItem(), true);
			}
		});
		cbEmpresas.setBounds(152, 12, 166, 27);
		panel.add(cbEmpresas);
		
		panel_1 = new JPanel();
		tabbedPane_1.addTab("Por Usuario", null, panel_1, null);
		panel_1.setLayout(null);
		
		lblUsuario = new JLabel("Seleccione Usuario:");
		lblUsuario.setBounds(12, 16, 128, 16);
		panel_1.add(lblUsuario);
		
		cbUsuarios = new JComboBox<Usuario>(usuarioComboBoxModel);
		cbUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getConfigByUsuario((Usuario) cbUsuarios.getSelectedItem());
			}
		});
		cbUsuarios.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//getConfigByUsuario((Usuario) cbUsuarios.getSelectedItem());
			}
		});
		cbUsuarios.setBounds(156, 12, 166, 27);
		panel_1.add(cbUsuarios);
		
		tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(16, 53, 795, 378);
		panel_1.add(tabbedPane_2);
		
		panel_3 = new JPanel();
		tabbedPane_2.addTab("General", null, panel_3, null);
		panel_3.setLayout(null);
		
		cbDepVentaUsuario = new JComboBox<Deposito>(depositoComboBoxModel);
		cbDepVentaUsuario.setBounds(204, 12, 166, 27);
		panel_3.add(cbDepVentaUsuario);
		
		lblDefineDeposito = new JLabel("Define Deposito p/ Venta:");
		lblDefineDeposito.setBounds(12, 12, 177, 27);
		panel_3.add(lblDefineDeposito);
		
		cbCajaUsuario = new JComboBox<Caja>(cajaComboBoxModel);
		cbCajaUsuario.setBounds(204, 89, 166, 27);
		panel_3.add(cbCajaUsuario);
		
		lblAbreCaja = new JLabel("Abre Caja:");
		lblAbreCaja.setBounds(12, 89, 83, 27);
		panel_3.add(lblAbreCaja);
		
		lblDefineDepositoP = new JLabel("Define Deposito p/ Compra:");
		lblDefineDepositoP.setBounds(12, 51, 177, 27);
		panel_3.add(lblDefineDepositoP);
		
		cbDepCompraUsuario = new JComboBox<Deposito>(configDepositoComboBoxModel);
		cbDepCompraUsuario.setBounds(204, 51, 166, 27);
		panel_3.add(cbDepCompraUsuario);
		
		lblOperacion = new JLabel("Operacion bloq. caja");
		lblOperacion.setBounds(12, 128, 177, 27);
		panel_3.add(lblOperacion);
		
		cbOpeBloqCaja = new JComboBox<PlanCuenta>(planCuentaComboBoxModel);
		cbOpeBloqCaja.setBounds(204, 128, 166, 27);
		panel_3.add(cbOpeBloqCaja);
		
		lblCajaTransferencia = new JLabel("Caja Transferencia");
		lblCajaTransferencia.setBounds(12, 167, 177, 27);
		panel_3.add(lblCajaTransferencia);
		
		cbCajaTransfUsuario = new JComboBox<Caja>(configCajaComboBoxModel);
		cbCajaTransfUsuario.setBounds(204, 167, 166, 27);
		panel_3.add(cbCajaTransfUsuario);
		
		lblNewLabel_4 = new JLabel("Autorizacion CUERPO NOTA:");
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setBounds(377, 20, 199, 15);
		panel_3.add(lblNewLabel_4);
		
		chAutorizaVentaPrecioDUsuario = new JCheckBox("Autoriza venta precio D");
		chAutorizaVentaPrecioDUsuario.setBounds(377, 51, 199, 27);
		panel_3.add(chAutorizaVentaPrecioDUsuario);
		
		chAutorizaVentaPrecioEUsuario = new JCheckBox("Autoriza venta precio E");
		chAutorizaVentaPrecioEUsuario.setBounds(377, 91, 199, 27);
		panel_3.add(chAutorizaVentaPrecioEUsuario);
		
		chAutorizaVentaHastaCostoUsuario = new JCheckBox("Autoriza venta hasta costo");
		chAutorizaVentaHastaCostoUsuario.setBounds(377, 130, 199, 27);
		panel_3.add(chAutorizaVentaHastaCostoUsuario);
		
		chAutorizaVentaBajoCostoUsuario = new JCheckBox("Autoriza venta bajo costo");
		chAutorizaVentaBajoCostoUsuario.setBounds(377, 167, 199, 27);
		panel_3.add(chAutorizaVentaBajoCostoUsuario);
		
		chAutorizaPlazoPagoUsuario = new JCheckBox("Autoriza Plazo de Pago");
		chAutorizaPlazoPagoUsuario.setBounds(582, 51, 196, 27);
		panel_3.add(chAutorizaPlazoPagoUsuario);
		
		lblAutorizacionPieNota = new JLabel("Autorizacion PIE DE NOTA:");
		lblAutorizacionPieNota.setForeground(Color.RED);
		lblAutorizacionPieNota.setFont(new Font("Dialog", Font.BOLD, 13));
		lblAutorizacionPieNota.setBounds(582, 18, 196, 15);
		panel_3.add(lblAutorizacionPieNota);
		
		chAutorizaLimiteCreditoUsuario = new JCheckBox("Autoriza Limite de Credito");
		chAutorizaLimiteCreditoUsuario.setBounds(582, 91, 200, 27);
		panel_3.add(chAutorizaLimiteCreditoUsuario);
		
		chDescNotaUsuario = new JCheckBox("Descuento Nota");
		chDescNotaUsuario.setBounds(582, 130, 196, 27);
		panel_3.add(chDescNotaUsuario);
		
		lblLimiteDeDescuento = new JLabel("Valor Desc.:");
		lblLimiteDeDescuento.setBounds(12, 249, 177, 27);
		panel_3.add(lblLimiteDeDescuento);
		
		cbTipoDescNota = new JComboBox<String>();
		cbTipoDescNota.setEnabled(false);
		cbTipoDescNota.setModel(new DefaultComboBoxModel<String>(new String[] {"VALOR", "% PORC."}));
		cbTipoDescNota.setBounds(204, 211, 166, 27);
		panel_3.add(cbTipoDescNota);
		
		chHabilitaDesc = new JCheckBox("Habilita Desc.:");
		chHabilitaDesc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarDescValor();
			}
		});
		chHabilitaDesc.setBounds(12, 211, 177, 27);
		panel_3.add(chHabilitaDesc);
		
		tfLimiteDescNotaValor = new JTextField();
		tfLimiteDescNotaValor.setText("0");
		tfLimiteDescNotaValor.setEnabled(false);
		tfLimiteDescNotaValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					verificarValores();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfLimiteDescNotaValor.setBounds(204, 249, 166, 27);
		panel_3.add(tfLimiteDescNotaValor);
		tfLimiteDescNotaValor.setColumns(10);
		
		panel_4 = new JPanel();
		tabbedPane_2.addTab("Stock", null, panel_4, null);
		panel_4.setLayout(null);
		
		lblVisualizarDeposito = new JLabel("Visualizar Deposito:");
		lblVisualizarDeposito.setBounds(12, 6, 138, 27);
		panel_4.add(lblVisualizarDeposito);
		
		cbVisualizarDepositoStockUsuario = new JComboBox<Deposito>(configVisualizaDepositoComboBoxModel);
		cbVisualizarDepositoStockUsuario.setBounds(153, 6, 166, 27);
		panel_4.add(cbVisualizarDepositoStockUsuario);
		
		lblVisualizarPrecios = new JLabel("Visualizar Precios:");
		lblVisualizarPrecios.setForeground(Color.RED);
		lblVisualizarPrecios.setFont(new Font("Dialog", Font.BOLD, 13));
		lblVisualizarPrecios.setBounds(12, 50, 154, 15);
		panel_4.add(lblVisualizarPrecios);
		
		chPuedeVerPrecioA = new JCheckBox("Precio A");
		chPuedeVerPrecioA.setBounds(12, 104, 138, 27);
		panel_4.add(chPuedeVerPrecioA);
		
		chPuedeVerPrecioB = new JCheckBox("Precio B");
		chPuedeVerPrecioB.setBounds(12, 137, 138, 27);
		panel_4.add(chPuedeVerPrecioB);
		
		chPuedeVerPrecioC = new JCheckBox("Precio C");
		chPuedeVerPrecioC.setBounds(12, 170, 138, 27);
		panel_4.add(chPuedeVerPrecioC);
		
		chPuedeVerPrecioD = new JCheckBox("Precio D");
		chPuedeVerPrecioD.setBounds(12, 203, 138, 27);
		panel_4.add(chPuedeVerPrecioD);
		
		chPuedeVerPrecioE = new JCheckBox("Precio E");
		chPuedeVerPrecioE.setBounds(12, 236, 138, 27);
		panel_4.add(chPuedeVerPrecioE);
		
		chVisualizaTodoPreciosUsuario = new JCheckBox("Todos los precios");
		chVisualizaTodoPreciosUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarPreciosAll();
			}
		});
		chVisualizaTodoPreciosUsuario.setBounds(12, 71, 143, 27);
		panel_4.add(chVisualizaTodoPreciosUsuario);
		
		lblVisualizarCostos = new JLabel("Visualizar Costos:");
		lblVisualizarCostos.setForeground(Color.RED);
		lblVisualizarCostos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblVisualizarCostos.setBounds(165, 50, 154, 15);
		panel_4.add(lblVisualizarCostos);
		
		chPuedeVerCostoFOB = new JCheckBox("Costo FOB");
		chPuedeVerCostoFOB.setBounds(165, 71, 143, 27);
		panel_4.add(chPuedeVerCostoFOB);
		
		chPuedeVerCostoCIF = new JCheckBox("Costo CIF");
		chPuedeVerCostoCIF.setBounds(165, 104, 154, 27);
		panel_4.add(chPuedeVerCostoCIF);
		
		lblPdv = new JLabel("PDV");
		lblPdv.setHorizontalAlignment(SwingConstants.CENTER);
		lblPdv.setForeground(Color.RED);
		lblPdv.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPdv.setBounds(337, 50, 154, 15);
		panel_4.add(lblPdv);
		
		chPuedeVentaDirContado = new JCheckBox("Venta directo contado");
		chPuedeVentaDirContado.setBounds(337, 71, 183, 27);
		panel_4.add(chPuedeVentaDirContado);
		
		chPuedeMsjFinalizaVenta = new JCheckBox("Mensaje finaliza venta");
		chPuedeMsjFinalizaVenta.setBounds(337, 104, 183, 27);
		panel_4.add(chPuedeMsjFinalizaVenta);
		
		chNotasRecibidaF3 = new JCheckBox("Notas Recibida F3");
		chNotasRecibidaF3.setBounds(337, 137, 183, 27);
		panel_4.add(chNotasRecibidaF3);
		
		chMonitorDeCajaF4 = new JCheckBox("Monitor de Caja F4");
		chMonitorDeCajaF4.setBounds(337, 170, 183, 27);
		panel_4.add(chMonitorDeCajaF4);
		
		cHCodConReferencia = new JCheckBox("Codigo con Referencia");
		cHCodConReferencia.setBounds(337, 203, 183, 27);
		panel_4.add(cHCodConReferencia);
		
		chAlteraPrecioVenta = new JCheckBox("Altera Precio Venta");
		chAlteraPrecioVenta.setBounds(337, 238, 183, 27);
		panel_4.add(chAlteraPrecioVenta);
	}
	
	private void habilitarDescValor() {
		if (chHabilitaDesc.isSelected()) {
			cbTipoDescNota.setEnabled(true);
			tfLimiteDescNotaValor.setEnabled(true);
		} else {
			cbTipoDescNota.setEnabled(false);
			tfLimiteDescNotaValor.setEnabled(false);
			tfLimiteDescNotaValor.setText("0");
		}
		
		cbTipoDescNota.requestFocus();
	}
	
	private void habilitarPreciosAll() {
		chPuedeVerPrecioA.setSelected(true);
		chPuedeVerPrecioB.setSelected(true);
		chPuedeVerPrecioC.setSelected(true);
		chPuedeVerPrecioD.setSelected(true);
		chPuedeVerPrecioE.setSelected(true);
	}
	
	private void verificarValores() {
		if (cbTipoDescNota.getSelectedIndex() == 0) { //valores
			
		} else { //porcentaje
			Double valor = FormatearValor.stringADouble(tfLimiteDescNotaValor.getText());
			
			if (valor > 100) {
				Notifications.showAlert("El porcentaje valor no debe ser mayor a 100%");
				tfLimiteDescNotaValor.selectAll();
				tfLimiteDescNotaValor.requestFocus();
			}
		}
	}
	
	public void loadInitial() {
		getDepositos();
		getCajas();
		loadEmpresas();
		loadUsuarios();
		loadPlanCuentas();
	}
	
	private void loadPlanCuentas() {
		List<PlanCuenta> planes = planCuentaService.findAll();
        planCuentaComboBoxModel.clear();
        planCuentaComboBoxModel.addElement(new PlanCuenta(0L, "NINGUNO"));
        planCuentaComboBoxModel.addElements(planes);
	}
	
	private void getDepositos() {
		List<Deposito> depositos = depositoService.findAll();
        depositoComboBoxModel.clear();
        depositoComboBoxModel.addElement(new Deposito(0L, "TODOS"));
        depositoComboBoxModel.addElements(depositos);
        
        //deposito empresa PDV
        configEmpresadepositoComboBoxModel.clear();
        configEmpresadepositoComboBoxModel.addElement(new Deposito(0L, "TODOS"));
        configEmpresadepositoComboBoxModel.addElements(depositos);
        
        //deposito usuario
        configDepositoComboBoxModel.clear();
        configDepositoComboBoxModel.addElement(new Deposito(0L, "TODOS"));
        configDepositoComboBoxModel.addElements(depositos);
        
        //deposito visualiza
        configVisualizaDepositoComboBoxModel.clear();
        configVisualizaDepositoComboBoxModel.addElement(new Deposito(0L, "TODOS"));
        configVisualizaDepositoComboBoxModel.addElements(depositos);
	}
	
	private void getCajas() {
		List<Caja> cajas = cajaService.findAll();
		cajaComboBoxModel.clear();
		cajaComboBoxModel.addElement(new Caja(0L, "TODOS"));
        cajaComboBoxModel.addElements(cajas);
        
        //otra caja
        configCajaComboBoxModel.clear();
        configCajaComboBoxModel.addElement(new Caja(0L, "TODOS"));
        configCajaComboBoxModel.addElements(cajas);
	}
	
	private void loadEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        empresaComboBoxModel.clear();
        empresaComboBoxModel.addElements(empresas);
    }
	
	private void loadUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        usuarioComboBoxModel.clear();
        usuarioComboBoxModel.addElements(usuarios);
    }
	
	private Configuracion getConfigByEmpresa(Empresa empresa, boolean isModify) {
		Optional<Configuracion> config = configService.findByEmpresaId(empresa);
		
		if (config.isPresent()) {
			if (isModify)
				setConfig(config.get());
			else
				return config.get();
		}
		
		return null;
	}
	
	private void getConfigByUsuario(Usuario usuario) {
		Optional<Configuracion> config = configService.findByUserId(usuario);
		
		if (config.isPresent()) 
			setConfig(config.get());
	}
	
	private void setConfig(Configuracion c) {
		//opciones generales
		cbEmpresas.setSelectedItem((Empresa)c.getEmpresaId());
		cbUsuarios.setSelectedItem((Usuario)c.getUsuarioId());
		
		//opciones basico
		if (c.getCalculoPrecioVenta() == 0)
			cbCalculoPrecioVenta.setSelectedIndex(0);
		else
			cbCalculoPrecioVenta.setSelectedIndex(1);
			
		if (c.getRegistroProductoIVA() == 0)
			cbProductoConIva.setSelectedIndex(0);
		else
			cbProductoConIva.setSelectedIndex(1);
		
		if (c.getTipoComisionVenta() == 0)
			cbTipoComisionVenta.setSelectedIndex(0);
		else
			cbTipoComisionVenta.setSelectedIndex(1);
		
		chPideDispNotaPresupuesto.setSelected(c.getPideDispNotaPresu() == 1 ? true:false);
		chPideNotaTransferencia.setSelected(c.getPideDispNotaTrans() == 1 ? true:false);
		chPideDispAjusteEntrada.setSelected(c.getPideDispAjusteEntrada() == 1 ? true:false);
		chPideDispAjusteSalida.setSelected(c.getPideDispNotaAjusteSalida() == 1 ? true:false);
		chPideDispDevCompra.setSelected(c.getPideDispNotaDevolCompra() == 1 ? true:false);
		chPideDispDevVenta.setSelected(c.getPideDispNotaDevolVenta() == 1 ? true:false);
		chPideDispAjusteCuenta.setSelected(c.getPideDispNotaAjusteCuenta() == 1 ? true:false);
		
		//opciones de venta
		chPideVendedor.setSelected(c.getPideVendedor() == 1 ? true:false);
		chPideDeposito.setSelected(c.getPideDeposito() == 1 ? true:false); 
		chVentaPorReferencia.setSelected(c.getPermiteVentaPorReferencia() == 1 ? true:false);
		chPideDescuento.setSelected(c.getPideDescuento() == 1 ? true:false);
		chPideFlete.setSelected(c.getPideFlete() == 1 ? true:false);
		chImprimeMoneda.setSelected(c.getImprimeMoneda() == 1 ? true:false);
		chImprimeNotaVentaOrdenAlf.setSelected(c.getImprimePorOrdenAlfabetico() == 1 ? true:false);
		chPideDispNotaVenta.setSelected(c.getPideDispositivoVenta() == 1 ? true:false);
		chNoRepiteProducto.setSelected(c.getPermiteItemDuplicado() == 1 ? true:false);
		chBloqueaVentaProdNegativo.setSelected(c.getBloqueaProductoNegativo() == 1 ? true:false);
		chImprimeNombreVendedor.setSelected(c.getImprimeVendedor() == 1 ? true:false);
		
		//opciones de compra
		chPideMoneda.setSelected(c.getPideMoneda() == 1 ? true:false); 
		chPideDepositoCompra.setSelected(c.getPideDepositoCompra() == 1 ? true:false);
		chPideGastoItem.setSelected(c.getPideGastoItemCompra() == 1 ? true:false);
		chImprimeNotaCompraResumida.setSelected(c.getImprimeNotaResCompra() == 1 ? true:false);
		chActualizAutoCompra.setSelected(c.getPermiteActualizacionAutoCompra() == 1 ? true:false);
		chPideDispNotaCompra.setSelected(c.getPideDispCompra() == 1 ? true:false);
		cbPrecioNota.setSelectedItem(c.getPrecioDefinido());
		//opciones de PDV
		//cbCaja.setSelectedIndex(c.getCajaIdPDV());
		//cbDeposito.setSelectedIndex(c.getDepositoIdPDV());
			
		//opciones de usuario
		cbUsuarios.setSelectedItem(c.getUsuarioId());
		cbDepVentaUsuario.setSelectedItem(c.getDefineDepositoVenta());
		cbDepCompraUsuario.setSelectedItem(c.getDefineDepositoCompra());
		cbCajaUsuario.setSelectedItem(c.getDefineAperCajaCodigo());
		cbOpeBloqCaja.setSelectedItem(c.getOperacionBloqCaja());
		cbCajaTransfUsuario.setSelectedItem(c.getDefineCajaTransferencia());
		chHabilitaDesc.setSelected(c.getHabilitaDescUsuario() == 1 ? true:false);
		cbTipoDescNota.setSelectedIndex(c.getTipoComisionVenta());
		tfLimiteDescNotaValor.setText(c.getLimiteDescNotaValor() != null ? 
				FormatearValor.doubleAString(c.getLimiteDescNotaValor()): "0");
		cbVisualizarDepositoStockUsuario.setSelectedItem(c.getVisualizaDepositoUsuario());
		
		//Autorizacion CUERPO NOTA
		chAutorizaVentaPrecioDUsuario.setSelected(c.getAutorizaVentaPrecioD() == 1 ? true:false);
		chAutorizaVentaPrecioEUsuario.setSelected(c.getAutorizaVentaPrecioE() == 1 ? true:false);
		chAutorizaVentaHastaCostoUsuario.setSelected(c.getAutorizaVentaHastaCosto() == 1 ? true:false);
		chAutorizaVentaBajoCostoUsuario.setSelected(c.getAutorizaVentaBajoCosto() == 1 ? true:false);
		
		//Autorizacion PIE DE NOTA
		chAutorizaPlazoPagoUsuario.setSelected(c.getAutorizaPlazoPago() == 1 ? true:false);
		chAutorizaLimiteCreditoUsuario.setSelected(c.getAutorizaLimiteCredito() == 1 ? true:false);
		chDescNotaUsuario.setSelected(c.getPuedeDescNota() == 1 ? true:false);
		
		//Visualizar Precios
		chVisualizaTodoPreciosUsuario.setSelected(c.getPuedeVerTodoPventa() == 1 ? true:false);
		chPuedeVerPrecioA.setSelected(c.getPuedeVerPrecioA() == 1 ? true:false);
		chPuedeVerPrecioB.setSelected(c.getPuedeVerPrecioB() == 1 ? true:false);
		chPuedeVerPrecioC.setSelected(c.getPuedeVerPrecioC() == 1 ? true:false);
		chPuedeVerPrecioD.setSelected(c.getPuedeVerPrecioD() == 1 ? true:false);
		chPuedeVerPrecioE.setSelected(c.getPuedeVerPrecioE() == 1 ? true:false);
		
		//Visualizar Costos
		chPuedeVerCostoFOB.setSelected(c.getPuedeVerCostoFob() == 1 ? true:false);
		chPuedeVerCostoCIF.setSelected(c.getPuedeVerCostoCif() == 1 ? true:false);
		
		//PDV
		chPuedeVentaDirContado.setSelected(c.getVentaDirectoContado() == 1 ? true:false);
		chPuedeMsjFinalizaVenta.setSelected(c.getMensajeFinalizaVenta() == 1 ? true:false);
		chNotasRecibidaF3.setSelected(c.getNotaRecibidaF3() == 1 ? true:false);
		chMonitorDeCajaF4.setSelected(c.getMonitorCajaF4() == 1 ? true:false);
		cHCodConReferencia.setSelected(c.getCodigoReferencia() == 1 ? true:false);
		chAlteraPrecioVenta.setSelected(c.getAlteraPrecioVenta() == 1 ? true:false);
	}
	
	private void save() {
		Configuracion c = getConfigByEmpresa((Empresa)cbEmpresas.getSelectedItem(), false);
		
		if (c != null) { //update
			//opciones generales
			c.setEmpresaId((Empresa)cbEmpresas.getSelectedItem());
			c.setUsuarioId((Usuario)cbUsuarios.getSelectedItem());
			
			//opciones basico
			c.setCalculoPrecioVenta(cbCalculoPrecioVenta.getSelectedIndex());
			c.setRegistroProductoIVA(cbProductoConIva.getSelectedIndex());
			c.setTipoComisionVenta(cbTipoComisionVenta.getSelectedIndex());
			c.setPideDispNotaPresu(chPideDispNotaPresupuesto.isSelected() ? 1:0);
			c.setPideDispNotaTrans(chPideNotaTransferencia.isSelected() ? 1:0);
			c.setPideDispAjusteEntrada(chPideDispAjusteEntrada.isSelected() ? 1:0);
			c.setPideDispNotaAjusteSalida(chPideDispAjusteSalida.isSelected() ? 1:0);
			c.setPideDispNotaDevolCompra(chPideDispDevCompra.isSelected() ? 1:0);
			c.setPideDispNotaDevolVenta(chPideDispDevVenta.isSelected() ? 1:0);
			c.setPideDispNotaAjusteCuenta(chPideDispAjusteCuenta.isSelected() ? 1:0);
			
			//opciones de venta
			c.setPideVendedor(chPideVendedor.isSelected() ? 1:0);
			c.setPideDeposito(chPideDeposito.isSelected() ? 1:0);
			c.setPermiteVentaPorReferencia(chVentaPorReferencia.isSelected() ? 1:0);
			c.setPideDescuento(chPideDescuento.isSelected() ? 1:0);
			c.setPideFlete(chPideFlete.isSelected() ? 1:0);
			c.setImprimeMoneda(chImprimeMoneda.isSelected() ? 1:0);
			c.setImprimePorOrdenAlfabetico(chImprimeNotaVentaOrdenAlf.isSelected() ? 1:0);
			c.setPideDispositivoVenta(chPideDispNotaVenta.isSelected() ? 1:0);
			c.setPermiteItemDuplicado(chNoRepiteProducto.isSelected() ? 1:0);
			c.setBloqueaProductoNegativo(chBloqueaVentaProdNegativo.isSelected() ? 1:0);
			c.setImprimeVendedor(chImprimeNombreVendedor.isSelected() ? 1:0);
		    c.setPrecioDefinido(cbPrecioNota.getSelectedItem().toString());
			
			//opciones de compra
			c.setPideMoneda(chPideMoneda.isSelected() ? 1:0);
			c.setPideDepositoCompra(chPideDepositoCompra.isSelected() ? 1:0);
			c.setPideGastoItemCompra(chPideGastoItem.isSelected() ? 1:0);
			c.setImprimeNotaResCompra(chImprimeNotaCompraResumida.isSelected() ? 1:0);
			c.setPermiteActualizacionAutoCompra(chActualizAutoCompra.isSelected() ? 1:0);
			c.setPideDispCompra(chPideDispNotaCompra.isSelected() ? 1:0);
			
			//opciones de PDV
			c.setCajaIdPDV(cbCaja.getSelectedIndex()); //caja
			c.setDepositoIdPDV(cbDeposito.getSelectedIndex()); //deposito
					
			//opciones de usuario
			c.setUsuarioId((Usuario)cbUsuarios.getSelectedItem());
			c.setDefineDepositoVenta(((Deposito)cbDepVentaUsuario.getSelectedItem()).getId());
			c.setDefineDepositoCompra(((Deposito)cbDepCompraUsuario.getSelectedItem()).getId());
			c.setDefineAperCajaCodigo(((Caja)cbCajaUsuario.getSelectedItem()).getId());
			c.setOperacionBloqCaja(((PlanCuenta)cbOpeBloqCaja.getSelectedItem()).getId());
			c.setDefineCajaTransferencia(((Caja)cbCajaTransfUsuario.getSelectedItem()).getId());
			c.setHabilitaDescUsuario(chHabilitaDesc.isSelected() ? 1:0);
			c.setTipoComisionVenta(cbTipoDescNota.getSelectedIndex());
			c.setLimiteDescNotaValor(FormatearValor.stringADouble(tfLimiteDescNotaValor.getText()));
			
			c.setVisualizaDepositoUsuario(((Deposito)cbVisualizarDepositoStockUsuario.getSelectedItem()).getId());
			
			//Autorizacion CUERPO NOTA
			c.setAutorizaVentaPrecioD(chAutorizaVentaPrecioDUsuario.isSelected() ? 1:0);
			c.setAutorizaVentaPrecioE(chAutorizaVentaPrecioEUsuario.isSelected() ? 1:0);
			c.setAutorizaVentaHastaCosto(chAutorizaVentaHastaCostoUsuario.isSelected() ? 1:0);
			c.setAutorizaVentaBajoCosto(chAutorizaVentaBajoCostoUsuario.isSelected() ? 1:0);
			
			//Autorizacion PIE DE NOTA
			c.setAutorizaPlazoPago(chAutorizaPlazoPagoUsuario.isSelected() ? 1:0);
			c.setAutorizaLimiteCredito(chAutorizaLimiteCreditoUsuario.isSelected() ? 1:0);
			c.setPuedeDescNota(chDescNotaUsuario.isSelected() ? 1:0);
			
			//Visualizar Precios
			c.setPuedeVerTodoPventa(chVisualizaTodoPreciosUsuario.isSelected() ? 1:0);
			c.setPuedeVerPrecioA(chPuedeVerPrecioA.isSelected() ? 1:0);
			c.setPuedeVerPrecioB(chPuedeVerPrecioB.isSelected() ? 1:0);
			c.setPuedeVerPrecioC(chPuedeVerPrecioC.isSelected() ? 1:0);
			c.setPuedeVerPrecioD(chPuedeVerPrecioD.isSelected() ? 1:0);
			c.setPuedeVerPrecioE(chPuedeVerPrecioE.isSelected() ? 1:0);
			
			//Visualizar Costos
			c.setPuedeVerCostoFob(chPuedeVerCostoFOB.isSelected() ? 1:0);
			c.setPuedeVerCostoCif(chPuedeVerCostoCIF.isSelected() ? 1:0);
			
			//PDV
			c.setVentaDirectoContado(chPuedeVentaDirContado.isSelected() ? 1:0);
			c.setMensajeFinalizaVenta(chPuedeMsjFinalizaVenta.isSelected() ? 1:0);
			c.setNotaRecibidaF3(chNotasRecibidaF3.isSelected() ? 1:0);
			c.setMonitorCajaF4(chMonitorDeCajaF4.isSelected() ? 1:0);
			c.setCodigoReferencia(cHCodConReferencia.isSelected() ? 1:0);
			c.setAlteraPrecioVenta(chAlteraPrecioVenta.isSelected() ? 1:0);
			
			Configuracion config = configService.save(c);
			
			if (config != null) {
				Notifications.showAlert("Configuracion actualizado con exito.!");
			}
		} else { //create
			//opciones generales
			Configuracion c1 = new Configuracion();
			
			c1.setEmpresaId((Empresa)cbEmpresas.getSelectedItem());
			c1.setUsuarioId((Usuario)cbUsuarios.getSelectedItem());
			
			//opciones basico
			c1.setCalculoPrecioVenta(cbCalculoPrecioVenta.getSelectedIndex());
			c1.setRegistroProductoIVA(cbProductoConIva.getSelectedIndex());
			c1.setTipoComisionVenta(cbTipoComisionVenta.getSelectedIndex());
			c1.setPideDispNotaPresu(chPideDispNotaPresupuesto.isSelected() ? 1:0);
			c1.setPideDispNotaTrans(chPideNotaTransferencia.isSelected() ? 1:0);
			c1.setPideDispAjusteEntrada(chPideDispAjusteEntrada.isSelected() ? 1:0);
			c1.setPideDispNotaAjusteSalida(chPideDispAjusteSalida.isSelected() ? 1:0);
			c1.setPideDispNotaDevolCompra(chPideDispDevCompra.isSelected() ? 1:0);
			c1.setPideDispNotaDevolVenta(chPideDispDevVenta.isSelected() ? 1:0);
			c1.setPideDispNotaAjusteCuenta(chPideDispAjusteCuenta.isSelected() ? 1:0);
			
			//opciones de venta
			c1.setPideVendedor(chPideVendedor.isSelected() ? 1:0);
			c1.setPideDeposito(chPideDeposito.isSelected() ? 1:0);
			c1.setPermiteVentaPorReferencia(chVentaPorReferencia.isSelected() ? 1:0);
			c1.setPideDescuento(chPideDescuento.isSelected() ? 1:0);
			c1.setPideFlete(chPideFlete.isSelected() ? 1:0);
			c1.setImprimeMoneda(chImprimeMoneda.isSelected() ? 1:0);
			c1.setImprimePorOrdenAlfabetico(chImprimeNotaVentaOrdenAlf.isSelected() ? 1:0);
			c1.setPideDispositivoVenta(chPideDispNotaVenta.isSelected() ? 1:0);
			c1.setPermiteItemDuplicado(chNoRepiteProducto.isSelected() ? 1:0);
			c1.setBloqueaProductoNegativo(chBloqueaVentaProdNegativo.isSelected() ? 1:0);
			c1.setImprimeVendedor(chImprimeNombreVendedor.isSelected() ? 1:0);
			
			//opciones de compra
			c1.setPideMoneda(chPideMoneda.isSelected() ? 1:0);
			c1.setPideDepositoCompra(chPideDepositoCompra.isSelected() ? 1:0);
			c1.setPideGastoItemCompra(chPideGastoItem.isSelected() ? 1:0);
			c1.setImprimeNotaResCompra(chImprimeNotaCompraResumida.isSelected() ? 1:0);
			c1.setPermiteActualizacionAutoCompra(chActualizAutoCompra.isSelected() ? 1:0);
			c1.setPideDispCompra(chPideDispNotaCompra.isSelected() ? 1:0);
			
			//opciones de PDV
			c1.setCajaIdPDV(cbCaja.getSelectedIndex()); 		//caja
			c1.setDepositoIdPDV(cbDeposito.getSelectedIndex()); //deposito
					
			//**** opciones de usuario  ****\\
			c1.setUsuarioId((Usuario)cbUsuarios.getSelectedItem());
			c1.setDefineDepositoVenta(((Deposito)cbDepVentaUsuario.getSelectedItem()).getId());
			c1.setDefineDepositoCompra(((Deposito)cbDepCompraUsuario.getSelectedItem()).getId());
			c1.setDefineAperCajaCodigo(((Caja)cbCajaUsuario.getSelectedItem()).getId());
			c1.setOperacionBloqCaja(((PlanCuenta)cbOpeBloqCaja.getSelectedItem()).getId());
			c1.setDefineCajaTransferencia(((Caja)cbCajaTransfUsuario.getSelectedItem()).getId());
			c1.setHabilitaDescUsuario(chHabilitaDesc.isSelected() ? 1:0);
			c1.setTipoComisionVenta(cbTipoDescNota.getSelectedIndex());
			c1.setLimiteDescNotaValor(tfLimiteDescNotaValor.getText().isEmpty() ? 0 : FormatearValor.stringADouble(tfLimiteDescNotaValor.getText()));
			
			c1.setVisualizaDepositoUsuario(((Deposito)cbVisualizarDepositoStockUsuario.getSelectedItem()).getId());
			
			//Autorizacion CUERPO NOTA
			c1.setAutorizaVentaPrecioD(chAutorizaVentaPrecioDUsuario.isSelected() ? 1:0);
			c1.setAutorizaVentaPrecioE(chAutorizaVentaPrecioEUsuario.isSelected() ? 1:0);
			c1.setAutorizaVentaHastaCosto(chAutorizaVentaHastaCostoUsuario.isSelected() ? 1:0);
			c1.setAutorizaVentaBajoCosto(chAutorizaVentaBajoCostoUsuario.isSelected() ? 1:0);
			
			//Autorizacion PIE DE NOTA
			c1.setAutorizaPlazoPago(chAutorizaPlazoPagoUsuario.isSelected() ? 1:0);
			c1.setAutorizaLimiteCredito(chAutorizaLimiteCreditoUsuario.isSelected() ? 1:0);
			c1.setPuedeDescNota(chDescNotaUsuario.isSelected() ? 1:0);
			
			//Visualizar Precios
			c1.setPuedeVerTodoPventa(chVisualizaTodoPreciosUsuario.isSelected() ? 1:0);
			c1.setPuedeVerPrecioA(chPuedeVerPrecioA.isSelected() ? 1:0);
			c1.setPuedeVerPrecioB(chPuedeVerPrecioB.isSelected() ? 1:0);
			c1.setPuedeVerPrecioC(chPuedeVerPrecioC.isSelected() ? 1:0);
			c1.setPuedeVerPrecioD(chPuedeVerPrecioD.isSelected() ? 1:0);
			c1.setPuedeVerPrecioE(chPuedeVerPrecioE.isSelected() ? 1:0);
			
			//Visualizar Costos
			c1.setPuedeVerCostoFob(chPuedeVerCostoFOB.isSelected() ? 1:0);
			c1.setPuedeVerCostoCif(chPuedeVerCostoCIF.isSelected() ? 1:0);
			
			//PDV
			c1.setVentaDirectoContado(chPuedeVentaDirContado.isSelected() ? 1:0);
			c1.setMensajeFinalizaVenta(chPuedeMsjFinalizaVenta.isSelected() ? 1:0);
			c1.setNotaRecibidaF3(chNotasRecibidaF3.isSelected() ? 1:0);
			c1.setMonitorCajaF4(chMonitorDeCajaF4.isSelected() ? 1:0);
			c1.setCodigoReferencia(cHCodConReferencia.isSelected() ? 1:0);
			c1.setAlteraPrecioVenta(chAlteraPrecioVenta.isSelected() ? 1:0);
			
			Configuracion config1 = configService.save(c1);
			
			if (config1 != null) {
				Notifications.showAlert("Configuracion guardado con exito.!");
			}
			
			clearForm();
		}
	}
	
	private void clearForm() {
		//opciones generales
		cbEmpresas.setSelectedIndex(0);
		cbUsuarios.setSelectedIndex(0);
		
		//opciones basico
		cbCalculoPrecioVenta.setSelectedIndex(0);
		cbProductoConIva.setSelectedIndex(0);
		cbTipoComisionVenta.setSelectedIndex(0);
		
		chPideDispNotaPresupuesto.setSelected(false);
		chPideNotaTransferencia.setSelected(false);
		chPideDispAjusteEntrada.setSelected(false);
		chPideDispAjusteSalida.setSelected(false);
		chPideDispDevCompra.setSelected(false);
		chPideDispDevVenta.setSelected(false);
		chPideDispAjusteCuenta.setSelected(false);
		
		//opciones de venta
		chPideVendedor.setSelected(false);
		chPideDeposito.setSelected(false); 
		chVentaPorReferencia.setSelected(false);
		chPideDescuento.setSelected(false);
		chPideFlete.setSelected(false);
		chImprimeMoneda.setSelected(false);
		chImprimeNotaVentaOrdenAlf.setSelected(false);
		chPideDispNotaVenta.setSelected(false);
		chNoRepiteProducto.setSelected(false);
		chBloqueaVentaProdNegativo.setSelected(false);
		chImprimeNombreVendedor.setSelected(false);
		
		//opciones de compra
		chPideMoneda.setSelected(false); 
		chPideDepositoCompra.setSelected(false);
		chPideGastoItem.setSelected(false);
		chImprimeNotaCompraResumida.setSelected(false);
		chActualizAutoCompra.setSelected(false);
		chPideDispNotaCompra.setSelected(false);
		
		//opciones de PDV
		cbCaja.setSelectedIndex(0);
		cbDeposito.setSelectedIndex(0);
			
		//opciones de usuario
		cbUsuarios.setSelectedIndex(0);
		cbDepVentaUsuario.setSelectedIndex(0);
		cbDepCompraUsuario.setSelectedIndex(0);
		cbOpeBloqCaja.setSelectedIndex(0);
		cbCajaTransfUsuario.setSelectedIndex(0);
		chHabilitaDesc.setSelected(false);
		cbTipoDescNota.setSelectedIndex(0);
		tfLimiteDescNotaValor.setText("0");
	
		cbVisualizarDepositoStockUsuario.setSelectedIndex(0);
		
		//Autorizacion CUERPO NOTA
		chAutorizaVentaPrecioDUsuario.setSelected(true);
		chAutorizaVentaPrecioEUsuario.setSelected(true);
		chAutorizaVentaHastaCostoUsuario.setSelected(true);
		chAutorizaVentaBajoCostoUsuario.setSelected(true);
		
		//Autorizacion PIE DE NOTA
		chAutorizaPlazoPagoUsuario.setSelected(true);
		chAutorizaLimiteCreditoUsuario.setSelected(true);
		chDescNotaUsuario.setSelected(true);
		
		//Visualizar Precios
		chVisualizaTodoPreciosUsuario.setSelected(true);
		chPuedeVerPrecioA.setSelected(true);
		chPuedeVerPrecioB.setSelected(true);
		chPuedeVerPrecioC.setSelected(true);
		chPuedeVerPrecioD.setSelected(true);
		chPuedeVerPrecioE.setSelected(true);
		
		//Visualizar Costos
		chPuedeVerCostoFOB.setSelected(true);
		chPuedeVerCostoCIF.setSelected(true);
		
		//PDV
		chPuedeVentaDirContado.setSelected(true);
		chPuedeMsjFinalizaVenta.setSelected(true);
		chNotasRecibidaF3.setSelected(true);
		chMonitorDeCajaF4.setSelected(true);
		cHCodConReferencia.setSelected(true);
		chAlteraPrecioVenta.setSelected(true);
	}
}
