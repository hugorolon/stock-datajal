package py.com.prestosoftware.ui.transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.ClientePais;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.CuentaARecibir;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.ItemCuentaARecibir;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoEgreso;
import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.data.models.MovimientoItemEgreso;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.data.models.ProcesoCobroVentas;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.ClientePaisService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CondicionPagoService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.CuentaARecibirService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ItemCuentaARecibirService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.MovimientoEgresoService;
import py.com.prestosoftware.domain.services.MovimientoIngresoService;
import py.com.prestosoftware.domain.services.MovimientoItemEgresoService;
import py.com.prestosoftware.domain.services.MovimientoItemIngresoService;
import py.com.prestosoftware.domain.services.ProcesoCobroVentasService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.UsuarioRolService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.domain.validations.VentaValidator;
import py.com.prestosoftware.ui.controllers.ProductoController;
import py.com.prestosoftware.ui.forms.ClienteAddPanel;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.CellRendererOthers;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.reports.ImpresionPanel;
import py.com.prestosoftware.ui.reports.ImpresionPanelInterfaz;
import py.com.prestosoftware.ui.reports.ImpresionUtil;
import py.com.prestosoftware.ui.reports.ReImpresionPanel;
import py.com.prestosoftware.ui.reports.ReImpresionPanelInterfaz;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.ClientePaisInterfaz;
import py.com.prestosoftware.ui.search.CondicionPagoDialog;
import py.com.prestosoftware.ui.search.CondicionPagoInterfaz;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaSaldoDeposito;
import py.com.prestosoftware.ui.search.ConsultaVentasDelDiaDialog;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.ProductoVistaDialog;
import py.com.prestosoftware.ui.search.VendedorDialog;
import py.com.prestosoftware.ui.search.VendedorInterfaz;
import py.com.prestosoftware.ui.search.VentaInterfaz;
import py.com.prestosoftware.ui.table.VentaItemTableModel;
import py.com.prestosoftware.util.Notifications;
import javax.swing.JFormattedTextField;

@Component
public class VentaPanel extends JFrame
		implements ClienteInterfaz, ClientePaisInterfaz, VendedorInterfaz, DepositoInterfaz, ProductoInterfaz,
		VentaInterfaz, CondicionPagoInterfaz, ImpresionPanelInterfaz, ReImpresionPanelInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int CLIENTE_CODE = 1;
	private static final int VENDEDOR_CODE = 2;
	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4;
	private static final int SALDO_PRODUCTO_CODE = 5;
	private static final int CONDICION_PAGO_CODE = 6;
	private static final int VENTA_CODE = 7;
	private static final int CLIENTE_ADD_CODE = 8;
	private static final int PRODUCTO_ADD_CODE = 9;
	private static final DecimalFormat decfor = new DecimalFormat("0.00");
	private JLabel lblRuc, lblDireccion, lblBuscadorDeVentas, lblDesc, lblCosto;
	private JTextField tfClienteNombre, tfVendedor, tfDescripcion, tfVentaId;
	private JTextField tfClienteID, tfPrecioTotal, tfPrecio, tfVendedorID;
	private JTextField tfCantidad, tfTotalItems, tfVence, tfDescuento, tfObs;
	private JTextField tfTotal, tfDepositoID, tfDeposito, tfClienteRuc; // tfSubtotal,
	private JTextField tfClienteDireccion, tfCuotaCant, tfProductoID, tfStock;
	private JButton btnAdd, btnRemove, btnGuardar, btnAnular, btnCancelar, btnCerrar, btnVer, btnAddProveedor, btnAddProducto;
	private JComboBox<String> tfCondicionPago;
	private JFormattedTextField tfFechaVenta;
	private JPanel pnlTotales;
	private JTable tbProductos;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel lblCamposObligatorios;
	private JLabel label_5;
	private JLabel lblSituacion;
	private JLabel lblHora;
	private JTextField tfDvRuc;
	private JButton btnReimpresion;

	private ConsultaCliente clientDialog;
	private VendedorDialog vendedorDialog;
	private DepositoDialog depositoDialog;
	private CondicionPagoDialog condicionDialog;
	private ProductoVistaDialog productoDialog;
	private ConsultaSaldoDeposito saldoDeposito;
	private ClienteAddPanel clienteAddPanel;
	private ConsultaVentasDelDiaDialog consultaVentasDelDiaDialog;

	private VentaService ventaService;
	private UsuarioRolService usuarioRolService;
	private VentaValidator ventaValidator;
	private VentaItemTableModel itemTableModel;

	private ClienteService clienteService;
	private ClientePaisService clientePaisService;
	private UsuarioService vendedorService;
	private DepositoService depositoService;
	private ProductoService productoService;
	private CondicionPagoService condicionPagoService;
	private ConfiguracionService configService;

	private AperturaCierreCajaService movCajaService;
	private CajaService cajaService;
	private MovimientoCajaService pagoService;
	private MovimientoIngresoService movimientoIngresoService;
	private MovimientoEgresoService movimientoEgresoService;
	private MovimientoItemIngresoService movimientoItemIngresoService;
	private MovimientoItemEgresoService movimientoItemEgresoService;
	private ProcesoCobroVentasService procesoCobroVentasService;
	private CuentaARecibirService cuentaARecibirService;
	private ItemCuentaARecibirService itemCuentaARecibirService;
	private ProductoController productoController;

	private boolean isProductService;
	private String nivelPrecio;
	private Producto productoSeleccionado;
	private Double precioA;
	private Double precioB;
	private Double precioC;
	private int impuesto;
	private Double precioCompra;
	private Venta ventaSeleccionado;
	private Cliente clienteSeleccionado;
	private Double precioInicial;
	private Date fechaImpresion;
	private int cant;

	public VentaPanel(VentaItemTableModel itemTableModel, ConsultaCliente clientDialog, VendedorDialog vendedorDialog,
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
			ItemCuentaARecibirService itemCuentaARecibirService, ClienteAddPanel clienteAddPanel, 
			ProductoController productoController) {
		this.itemTableModel = itemTableModel;
		this.clientDialog = clientDialog;
		this.vendedorDialog = vendedorDialog;
		this.depositoDialog = depositoDialog;
		this.productoDialog = productoDialog;
		this.ventaValidator = ventaValidator;
		this.ventaService = ventaService;
		this.clienteService = clienteService;
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
		this.productoController = productoController;

		setSize(1155, 714);
		setTitle("REGISTRO DE VENTAS");

		initComponents();
		Util.setupScreen(this);
		// setTableSize();
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		setDefaultCloseOperation(0);

		getContentPane().setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBounds(12, 106, 1119, 391);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("PRODUCTOS", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JLabel lblCodigo = new JLabel("COD");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigo.setBounds(6, 10, 35, 30);
		pnlProducto.add(lblCodigo);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcion.setBounds(166, 10, 169, 30);
		pnlProducto.add(lblDescripcion);

		JLabel lblSubtotal = new JLabel("TOTAL");
		lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubtotal.setBounds(835, 10, 50, 30);
		pnlProducto.add(lblSubtotal);

		JLabel lblPrecio = new JLabel("PRECIO");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(726, 10, 88, 30);
		pnlProducto.add(lblPrecio);

		tfDescripcion = new JTextField();
		tfDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(166, 39, 550, 30);
		pnlProducto.add(tfDescripcion);

		tfPrecioTotal = new JTextField();
		tfPrecioTotal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioTotal.setEditable(false);
		tfPrecioTotal.setFont(new Font("Arial", Font.PLAIN, 14));
		tfPrecioTotal.setColumns(10);
		tfPrecioTotal.setBounds(835, 39, 106, 30);
		pnlProducto.add(tfPrecioTotal);

		tfPrecio = new JTextField();
		tfPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		tfPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrecio.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecio.selectAll();
			}
		});
		tfPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfPrecio.getText().isEmpty()) {
						calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfCantidad.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnAdd.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					abandonarNota();
				} else if (e.getKeyCode() == KeyEvent.VK_F1) {
					if (!tfPrecio.getText().isEmpty()) {
						tfPrecio.setText(FormatearValor.doubleAString(precioA));
						calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					if (!tfPrecio.getText().isEmpty()) {
						tfPrecio.setText(FormatearValor.doubleAString(precioB));
						calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F3) {
					if (!tfPrecio.getText().isEmpty()) {
						tfPrecio.setText(FormatearValor.doubleAString(precioC));
						calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		tfPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
		tfPrecio.setColumns(10);
		tfPrecio.setBounds(726, 39, 100, 30);
		pnlProducto.add(tfPrecio);

		tfProductoID = new JTextField();
		tfProductoID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfProductoID.selectAll();
			}
		});
		tfProductoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

					if (tfCondicionPago.isEnabled())
						tfCondicionPago.requestFocus();
					else if (tfCuotaCant.isEnabled())
						tfCuotaCant.requestFocus();
					else if (tfDescuento.isEnabled())
						tfDescuento.requestFocus();

				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					abandonarNota();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProducto(tfProductoID.getText());
					} else {
						showDialog(PRODUCTO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfCantidad.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Util.validateNumero(e);
			}
		});
		tfProductoID.setFont(new Font("Arial", Font.PLAIN, 14));
		tfProductoID.setBounds(6, 39, 50, 30);
		pnlProducto.add(tfProductoID);
		tfProductoID.setColumns(10);

		btnRemove = new JButton("-");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 18));
		btnRemove.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					removeItem();
				}
			}
		});

		btnRemove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) {
					removeItem();
				}
			}
		});
		btnRemove.setBounds(1068, 36, 45, 30);
		pnlProducto.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(6, 81, 1098, 269);
		pnlProducto.add(scrollProducto);

		tbProductos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		tbProductos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tbProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Util.ocultarColumna(tbProductos, 5);
		Util.ocultarColumna(tbProductos, 6);
		Util.ocultarColumna(tbProductos, 7);
		Util.ocultarColumna(tbProductos, 8);
		Util.ocultarColumna(tbProductos, 9);
		Util.ocultarColumna(tbProductos, 10);
		Util.ocultarColumna(tbProductos, 11);
		DefaultTableCellRenderer alignRendererHeaderCenter= new CellRendererOthers();
		alignRendererHeaderCenter.setBackground(getBackground());
		alignRendererHeaderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer alignRendererHeaderLeft= new CellRendererOthers();
		alignRendererHeaderLeft.setHorizontalAlignment(SwingConstants.LEFT);
		DefaultTableCellRenderer alignRendererLeft= new CellRendererOthers();
		alignRendererLeft.setBackground(getBackground());
		alignRendererLeft.setHorizontalAlignment(SwingConstants.LEFT);
		DefaultTableCellRenderer alignRendererRight= new CellRendererOthers();
		alignRendererRight.setBackground(getBackground());
		alignRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbProductos.getTableHeader().setOpaque(false);
		tbProductos.getTableHeader().setBackground(new Color(225, 251, 234));
		tbProductos.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 18));
		tbProductos.getColumnModel().getColumn(0).setHeaderRenderer(alignRendererHeaderCenter);
		tbProductos.getColumnModel().getColumn(0).setPreferredWidth(105);
		tbProductos.getColumnModel().getColumn(0).setCellRenderer(alignRendererLeft);
		tbProductos.getColumnModel().getColumn(1).setHeaderRenderer(alignRendererHeaderCenter);
		tbProductos.getColumnModel().getColumn(1).setPreferredWidth(105);
		tbProductos.getColumnModel().getColumn(1).setCellRenderer(alignRendererLeft);
		tbProductos.getColumnModel().getColumn(2).setHeaderRenderer(alignRendererHeaderLeft);
		tbProductos.getColumnModel().getColumn(2).setPreferredWidth(585);
		tbProductos.getColumnModel().getColumn(2).setCellRenderer(alignRendererLeft);
		tbProductos.getColumnModel().getColumn(3).setHeaderRenderer(alignRendererHeaderCenter);
		tbProductos.getColumnModel().getColumn(3).setPreferredWidth(150);
		tbProductos.getColumnModel().getColumn(3).setCellRenderer(alignRendererRight);
		tbProductos.getColumnModel().getColumn(4).setHeaderRenderer(alignRendererHeaderCenter);
		tbProductos.getColumnModel().getColumn(4).setPreferredWidth(150);
		tbProductos.getColumnModel().getColumn(4).setCellRenderer(alignRendererRight);
		
		tbProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getItemSelected();
			}
		});
		tbProductos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getItemSelected();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});

		// setTableSize();

		scrollProducto.setViewportView(tbProductos);

		tfCantidad = new JTextField();
		tfCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCantidad.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCantidad.selectAll();
			}
		});
		tfCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCantidad.getText().isEmpty()) {
						if (!tfProductoID.getText().isEmpty()) {
							if (!isProductService) {
								if (validateCantidad()) {
									tfPrecio.requestFocus();
								}
							} else {
								tfPrecio.requestFocus();
							}
						} else {
							Notifications.showAlert("Debes informar el Código del Producto.!");
						}
					} else {
						Notifications.showAlert("Debes ingresar la cantidad");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					showDialog(SALDO_PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F6) {
					// consulta de saldo stock
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					abandonarNota();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfPrecio.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Util.validateNumero(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
//				if (tfCantidad.getText().length() > 3) {
//					tfCantidad.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfCantidad.getText())));
//    			}
			}
		});
		tfCantidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfCantidad.setColumns(10);
		tfCantidad.setBounds(112, 39, 45, 30);
		pnlProducto.add(tfCantidad);

		JLabel lblCantidad = new JLabel("CANT.");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidad.setBounds(114, 10, 61, 30);
		pnlProducto.add(lblCantidad);

		label_5 = new JLabel("*");
		label_5.setVerticalAlignment(SwingConstants.BOTTOM);
		label_5.setToolTipText("Campos obligatorios");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Dialog", Font.BOLD, 20));
		label_5.setBounds(40, 10, 14, 30);
		pnlProducto.add(label_5);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(12, 0, 1119, 105);
		panel_3.setBorder(
				new TitledBorder(null, "SELECCIONE CLIENTE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 1103, 79);
		panel_3.add(pnlCliente);
		pnlCliente.setLayout(null);

		JLabel lblClienteID = new JLabel("CLIENTE:");
		lblClienteID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblClienteID.setBounds(253, 6, 79, 30);
		pnlCliente.add(lblClienteID);

		btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 18));
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addItem();
				}
			}
		});
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) {
					addItem();
				}
			}
		});

		btnAdd.setBounds(1006, 36, 45, 30);
		pnlProducto.add(btnAdd);
		
		JLabel lblStock = new JLabel("STOCK");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStock.setBounds(951, 10, 45, 30);
		pnlProducto.add(lblStock);
		
		tfStock = new JTextField();
		tfStock.setFont(new Font("Arial", Font.PLAIN, 14));
		tfStock.setEditable(false);
		tfStock.setColumns(10);
		tfStock.setBounds(951, 39, 45, 30);
		pnlProducto.add(tfStock);
		
		lblDescripcionFiscal = new JLabel("");
		lblDescripcionFiscal.setBounds(252, 10, 320, 24);
		pnlProducto.add(lblDescripcionFiscal);
		
		lblCosto = new JLabel("");
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCosto.setBounds(990, 10, 114, 30);
		pnlProducto.add(lblCosto);
		
		btnAddProducto = new JButton("+");
		btnAddProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(PRODUCTO_ADD_CODE);
			}
		});
		btnAddProducto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAddProducto.setBounds(62, 39, 45, 30);
		pnlProducto.add(btnAddProducto);

		tfClienteID = new JTextField();
		tfClienteID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfClienteID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteID.selectAll();
			}
		});
		tfClienteID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(CLIENTE_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfClienteID.getText().isEmpty()) {
						findClientById(Long.parseLong(tfClienteID.getText()));
						tfDvRuc.setText(String.valueOf(Util.calculateRucDV(tfClienteRuc.getText())));
						tfProductoID.requestFocus();
					} else {
						showDialog(CLIENTE_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					showDialog(CLIENTE_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfClienteID.setText("0");
		tfClienteID.setBounds(398, 6, 47, 30);
		pnlCliente.add(tfClienteID);
		tfClienteID.setColumns(10);

		tfClienteNombre = new JTextField();
		tfClienteNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfClienteNombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteNombre.selectAll();
			}
		});
		((AbstractDocument) tfClienteNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfClienteNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClienteDireccion.requestFocus();
				}
			}
		});
		tfClienteNombre.setBounds(448, 6, 372, 30);
		pnlCliente.add(tfClienteNombre);
		tfClienteNombre.setColumns(10);

		JLabel lblVendedor = new JLabel("VENDEDOR");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVendedor.setBounds(483, 44, 79, 30);
		pnlCliente.add(lblVendedor);

		tfVendedorID = new JTextField();
		tfVendedorID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfVendedorID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfVendedorID.selectAll();
			}
		});
		tfVendedorID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(VENDEDOR_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfVendedorID.getText().isEmpty()) {
						findVendedorById(Long.valueOf(tfVendedorID.getText()));
					} else {
						showDialog(VENDEDOR_CODE);
					}
					if (tfVendedorID.getText().isEmpty()) {
						tfVendedorID.requestFocus();
					} else {
						tfProductoID.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVendedorID.setBounds(568, 45, 47, 30);
		pnlCliente.add(tfVendedorID);
		tfVendedorID.setColumns(10);

		tfVendedor = new JTextField();
		tfVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfVendedor.setEditable(false);
		tfVendedor.setBounds(618, 45, 202, 30);
		pnlCliente.add(tfVendedor);
		tfVendedor.setToolTipText("Nombre del Vendedor");
		tfVendedor.setColumns(10);

		JLabel lblDeposito = new JLabel("DEP.:");
		lblDeposito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDeposito.setBounds(830, 45, 40, 30);
		pnlCliente.add(lblDeposito);

		tfDepositoID = new JTextField();
		tfDepositoID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfDepositoID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDepositoID.selectAll();
			}
		});
		tfDepositoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(DEPOSITO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfDepositoID.getText().isEmpty()) {
						findDepositoById(Long.parseLong(tfDepositoID.getText()));
					} else {
						showDialog(DEPOSITO_CODE);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfDepositoID.setColumns(10);
		tfDepositoID.setBounds(903, 42, 27, 30);
		pnlCliente.add(tfDepositoID);

		tfDeposito = new JTextField();
		tfDeposito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfDeposito.setEditable(false);
		tfDeposito.setToolTipText("Nombre del Vendedor");
		tfDeposito.setColumns(10);
		tfDeposito.setBounds(940, 42, 153, 30);
		pnlCliente.add(tfDeposito);

		lblRuc = new JLabel("RUC:");
		lblRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRuc.setBounds(253, 44, 55, 30);
		pnlCliente.add(lblRuc);

		tfClienteRuc = new JTextField();
		tfClienteRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		((AbstractDocument) tfClienteRuc.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfClienteRuc.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteRuc.selectAll();
			}
		});
		// tfClienteRuc.setEnabled(false);
		tfClienteRuc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfClienteRuc.getText().isEmpty())
						findClientByRuc(tfClienteRuc.getText());
					// tfDvRuc.setText(String.valueOf(Util.calculateRucDV(tfClienteRuc.getText())));

					if (conf != null) {
						if (tfVendedorID.getText().isEmpty() && conf.getPideVendedor() == 1)
							tfVendedorID.requestFocus();
						else if (!tfDepositoID.getText().isEmpty() && conf.getPideDeposito() == 1)
							tfDepositoID.requestFocus();
						else
							tfProductoID.requestFocus();
					}
				}
			}
		});
		tfClienteRuc.setColumns(10);
		tfClienteRuc.setBounds(318, 44, 118, 30);
		pnlCliente.add(tfClienteRuc);

		lblDireccion = new JLabel("DIR:");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDireccion.setBounds(830, 6, 45, 30);
		pnlCliente.add(lblDireccion);

		tfClienteDireccion = new JTextField();
		tfClienteDireccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		((AbstractDocument) tfClienteDireccion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfClienteDireccion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteDireccion.selectAll();
			}
		});
		tfClienteDireccion.setEnabled(false);
		tfClienteDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClienteRuc.requestFocus();
				}
			}
		});
		tfClienteDireccion.setColumns(10);
		tfClienteDireccion.setBounds(900, 6, 193, 30);
		pnlCliente.add(tfClienteDireccion);

		lblBuscadorDeVentas = new JLabel("NOTA:");
		lblBuscadorDeVentas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBuscadorDeVentas.setBounds(0, 6, 42, 30);
		pnlCliente.add(lblBuscadorDeVentas);

		tfVentaId = new JTextField();
		tfVentaId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfVentaId.setEditable(true);
		tfVentaId.setBounds(55, 6, 55, 30);
		pnlCliente.add(tfVentaId);
		tfVentaId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfVentaId.getText().isEmpty()) {
						findVenta(tfVentaId.getText());
					} else {
						showDialog(PRODUCTO_CODE);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});

		tfVentaId.setColumns(10);

		btnVer = new JButton("VER");
		btnVer.setFont(new Font("Dialog", Font.BOLD, 18));
		btnVer.setBounds(203, 6, 27, 30);
		pnlCliente.add(btnVer);
		btnVer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showDialog(VENTA_CODE);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(VENTA_CODE);
			}
		});

		label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		label.setToolTipText("Campos obligatorios");
		label.setForeground(Color.RED);
		label.setBounds(378, 6, 14, 30);
		pnlCliente.add(label);

		label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3.setToolTipText("Campos obligatorios");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Dialog", Font.BOLD, 20));
		label_3.setBounds(867, 42, 14, 30);
		pnlCliente.add(label_3);

		tfDvRuc = new JTextField();
		tfDvRuc.setFont(new Font("Tahoma", Font.ITALIC, 14));
		tfDvRuc.setEnabled(false);
		tfDvRuc.setBounds(446, 44, 27, 30);
		pnlCliente.add(tfDvRuc);
		tfDvRuc.setColumns(10);

		btnAddProveedor = new JButton("+");
		btnAddProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(CLIENTE_ADD_CODE);
			}
		});
		btnAddProveedor.setFont(new Font("Dialog", Font.BOLD, 18));
		btnAddProveedor.setBounds(318, 6, 58, 30);
		pnlCliente.add(btnAddProveedor);

		lblSituacion = new JLabel("Situación");
		lblSituacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSituacion.setBounds(118, 10, 85, 20);
		pnlCliente.add(lblSituacion);
		
		JLabel lblFecha = new JLabel("FECHA");
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFecha.setBounds(0, 54, 55, 14);
		pnlCliente.add(lblFecha);
		
		tfFechaVenta = new JFormattedTextField(getFormatoFecha());
		tfFechaVenta.setEditable(false);
		tfFechaVenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfFechaVenta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfFechaVenta.selectAll();
			}
		});
		//tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
		tfFechaVenta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfFechaVenta.getText().isEmpty()) {
						tfClienteRuc.requestFocus();
						
					} else {
						//getFecha();
						Notifications.showAlert("Debes digital la fecha");
						tfClienteRuc.requestFocus();
					}
				}
			}
		});

		tfFechaVenta.setColumns(8);
		
		tfFechaVenta.setBounds(55, 44, 90, 27);
		pnlCliente.add(tfFechaVenta);
		
		lblHora = new JLabel("HORA");
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHora.setBounds(153, 54, 45, 14);
		pnlCliente.add(lblHora);

		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(21, 590, 1110, 35);
		getContentPane().add(pnlBotonera);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGuardar.setMnemonic('G');
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				}
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					save();
				}
			}
		});
		pnlBotonera.add(btnGuardar);

		btnAnular = new JButton("Anular");
		btnAnular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAnular.setVisible(false);
		btnAnular.setMnemonic('A');
		btnAnular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anular();
			}
		});
		btnAnular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					anular();
				}
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					anular();
				}
			}

		});

		btnReimpresion = new JButton("Re Impresión");
		btnReimpresion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReimpresion.setMnemonic('R');
		btnReimpresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirDialogoReimpresion();
			}
		});
		btnReimpresion.setVisible(false);
		pnlBotonera.add(btnReimpresion);
		pnlBotonera.add(btnAnular);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setMnemonic('C');
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					abandonarNota();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abandonarNota();
			}
		});
		pnlBotonera.add(btnCancelar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCerrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					abandonarNota();
					dispose();
				}
			}
		});
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abandonarNota();
				dispose();
			}
		});
		pnlBotonera.add(btnCerrar);

		pnlTotales = new JPanel();
		pnlTotales.setBounds(22, 497, 1109, 88);
		getContentPane().add(pnlTotales);
		pnlTotales.setLayout(null);

		JLabel lblCantItem = new JLabel("Cant. Item:");
		lblCantItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantItem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCantItem.setBounds(0, 12, 74, 30);
		pnlTotales.add(lblCantItem);

		tfTotalItems = new JTextField();
		tfTotalItems.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfTotalItems.setBounds(95, 12, 55, 30);
		pnlTotales.add(tfTotalItems);
		tfTotalItems.setEditable(false);
		tfTotalItems.setColumns(10);

		JLabel lblCondicin = new JLabel("Cond. Pag:");
		lblCondicin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCondicin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCondicin.setBounds(0, 46, 74, 30);
		pnlTotales.add(lblCondicin);

		tfCondicionPago = new JComboBox<String>();
		tfCondicionPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfCondicionPago.setModel(new DefaultComboBoxModel(new String[] { "Contado", "30 días" }));
		tfCondicionPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date today = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(today);
				if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("Contado")) {
					findCondicionPago(Integer.valueOf("0"));
					btnGuardar.requestFocus();
				} else if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("30 días")) {
					findCondicionPago(Integer.valueOf(30));
					tfCuotaCant.setText("1");
					tfCuotaCant.setEnabled(false);
				}
			}
		});

		tfCondicionPago.setBounds(95, 46, 121, 30);
		pnlTotales.add(tfCondicionPago);

		JLabel lblCuotaCant = new JLabel("CuotaCant:");
		lblCuotaCant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCuotaCant.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuotaCant.setBounds(399, 46, 86, 30);
		pnlTotales.add(lblCuotaCant);

		JLabel lblVence = new JLabel("Vence:");
		lblVence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVence.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVence.setBounds(215, 45, 50, 30);
		pnlTotales.add(lblVence);

		tfCuotaCant = new JTextField();
		tfCuotaCant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfCuotaCant.setText("0");
		tfCuotaCant.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCuotaCant.selectAll();
			}
		});
		tfCuotaCant.setBounds(495, 46, 98, 30);
		pnlTotales.add(tfCuotaCant);
		tfCuotaCant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCuotaCant.getText().isEmpty() && Integer.valueOf(tfCuotaCant.getText()).intValue() >= 0) {
						btnGuardar.requestFocus();
					} else {
						Notifications.showAlert("Debes digital valor valido para la cuota.!");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}

		});
		tfCuotaCant.setColumns(10);

		tfVence = new JTextField();
		tfVence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfVence.setEditable(false);
		tfVence.setBounds(275, 45, 113, 30);
		pnlTotales.add(tfVence);
		tfVence.setColumns(10);

		lblDesc = new JLabel("Desc.:");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesc.setBounds(412, 12, 74, 30);
		pnlTotales.add(lblDesc);

		JLabel lblObs = new JLabel("Obs.:");
		lblObs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblObs.setBounds(603, 46, 50, 30);
		pnlTotales.add(lblObs);

		tfDescuento = new JTextField();
		tfDescuento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfDescuento.setText("0");
		tfDescuento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescuento.selectAll();
			}
		});
		tfDescuento.setBounds(496, 12, 98, 30);
		pnlTotales.add(tfDescuento);
		tfDescuento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					if (!tfDescuento.getText().isEmpty()) {
//						Double subtotal = tfSubtotal.getText().isEmpty() ? 0
//								: FormatearValor.stringToDouble(tfSubtotal.getText());
//						setTotals(0d, subtotal);
//						tfObs.requestFocus();
//					} else {
//						Notifications.showAlert("Debes digitar descuento.!");
//					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Util.validateNumero(e);
			}
		});
		tfDescuento.setColumns(10);

		tfObs = new JTextField();
		tfObs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfObs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfObs.selectAll();
			}
		});
		tfObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});
		tfObs.setBounds(656, 46, 340, 30);
		pnlTotales.add(tfObs);
		tfObs.setColumns(10);

//		JLabel lblSubTotal = new JLabel("Sub Total:");
//		lblSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblSubTotal.setBounds(476, 12, 74, 30);
//		pnlTotales.add(lblSubTotal);

//		tfSubtotal = new JTextField();
//		tfSubtotal.setBounds(548, 12, 130, 30);
//		pnlTotales.add(tfSubtotal);
//		tfSubtotal.setEditable(false);
//		tfSubtotal.setColumns(10);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(604, 12, 42, 30);
		pnlTotales.add(lblTotal);

		tfTotal = new JTextField();
		tfTotal.setBounds(656, 12, 98, 30);
		pnlTotales.add(tfTotal);
		tfTotal.setEditable(false);
		tfTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfTotal.setForeground(Color.RED);
		tfTotal.setColumns(10);

		label_2 = new JLabel("*");
		label_2.setBounds(77, 46, 14, 30);
		pnlTotales.add(label_2);
		label_2.setVerticalAlignment(SwingConstants.BOTTOM);
		label_2.setToolTipText("Campos obligatorios");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Dialog", Font.BOLD, 20));
		
				lblCamposObligatorios = new JLabel("Campos Obligatorios");
				lblCamposObligatorios.setBounds(791, 10, 205, 25);
				pnlTotales.add(lblCamposObligatorios);
				lblCamposObligatorios.setFont(new Font("Dialog", Font.BOLD, 20));

		label_4 = new JLabel("*");
		label_4.setBounds(767, 13, 14, 25);
		pnlTotales.add(label_4);
		label_4.setVerticalAlignment(SwingConstants.BOTTOM);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setToolTipText("Campos obligatorios");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));
		vistaPrecioCompra();
	}

	/** Este metodo se ejecuta cuando se suelta una tecla */
//	@Override
//	public void keyReleased(KeyEvent e) {
//		System.out.println("Soltó la tecla:  " + e.getKeyText(e.getKeyCode()));
//			if (e.VK_ESCAPE == e.getKeyCode()) {
//				int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea salir?", "Confirmación",
//						JOptionPane.YES_NO_OPTION);
//				if (respuesta == JOptionPane.YES_NO_OPTION) {
//					System.exit(0);
//				}
//			}
//	}

	/**
	 * Este metodo funcionará solo cuando se presionan caracteres, si se presionan
	 * teclas como F1, F2, inicio etc no ejecutará ningun evento
	 */
//	@Override
//	public void keyTyped(KeyEvent e) {
//		if (KeyEvent.VK_F5 == e.getKeyCode()) {
//			System.out.println("presionó escape "+e.getKeyCode());
//			int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea salir?", "Confirmación",
//					JOptionPane.YES_NO_OPTION);
//			if (respuesta == JOptionPane.YES_NO_OPTION) {
//				System.exit(0);
//			}
//			
//		}
//		
//
//	}

	public void vistaPrecioCompra() {
		if (!usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "PUEDE VER PRECIO DE COMPRAS")) {
			lblCosto.setVisible(false);
			btnAddProveedor.setVisible(false);
			btnAddProducto.setVisible(false);
			//lblDesc.setVisible(false);
		}
	}

	public void vistaDescuentoItem() {
		if (!usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "VENTAS CON DESC. ITEM")) {
//			tfDescuentoItem.setVisible(false);
//			lblDescItem.setVisible(false);
		}
	}
	
	private MaskFormatter formatoFecha;
	
	private MaskFormatter getFormatoFecha() {
		try {
			if (formatoFecha == null) {
				formatoFecha = new MaskFormatter("##/##/####");
				formatoFecha.setPlaceholderCharacter('_');
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return formatoFecha;
	}

	private void getItemSelected() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			VentaDetalle item = itemTableModel.getEntityByRow(selectedRow);
			
			this.precioCompra= item.getPrecioCosto()==null?0:item.getPrecioCosto();
			tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfPrecio.setText(FormatearValor.doubleAString(item.getPrecio()));
			tfStock.setText(FormatearValor.doubleAString(item.getStock()));
			lblCosto.setText("");
			tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
			// tfDescuentoItem.setText(item.getDescuento().toString());
		}
	}

//	private void setTableSize() {
//		tbProductos.getColumnModel().getColumn(0).setMinWidth(120);
//		tbProductos.getColumnModel().getColumn(0).setMaxWidth(120);
//		tbProductos.getColumnModel().getColumn(1).setMinWidth(120);
//		tbProductos.getColumnModel().getColumn(1).setMaxWidth(120);
//		tbProductos.getColumnModel().getColumn(2).setMinWidth(300);
//		tbProductos.getColumnModel().getColumn(2).setMaxWidth(300);
//		tbProductos.getColumnModel().getColumn(3).setMinWidth(150);
//		tbProductos.getColumnModel().getColumn(3).setMaxWidth(150);
//		tbProductos.getColumnModel().getColumn(4).setMinWidth(160);
//		tbProductos.getColumnModel().getColumn(4).setMaxWidth(160);
//	}

	private Configuracion conf;

	public void getConfig() {
		Optional<Configuracion> config = configService.findByEmpresaId(new Empresa(GlobalVars.EMPRESA_ID));

		if (config.isPresent()) {
			this.conf = config.get();

			if (conf.getPideVendedor() == 0) {
				Optional<Usuario> usuario = vendedorService.findById(GlobalVars.USER_ID);
				tfVendedorID.setText(usuario.get().getId().toString());
				tfVendedor.setText(usuario.get().getUsuario());
				tfVendedorID.setEnabled(false);
				tfVendedor.setEnabled(false);
			}

//			if (conf.getPideFlete() == 0)
//				tfFlete.setEnabled(false);

			if (conf.getPideDescuento() == 0)
				tfDescuento.setEnabled(false);

//			if (conf.getDefineDepositoVenta() != 0) {
//				configService.findByUserId(new Usuario(GlobalVars.USER_ID));
//				tfDepositoID.setEnabled(false);
//				findDepositoById(conf.getDefineDepositoVenta());
//			}

			if (conf.getPideDeposito() == 0) {
				Optional<Deposito> deposito = depositoService.findById(GlobalVars.DEPOSITO_ID);
				tfDepositoID.setText(String.valueOf(deposito.get().getId()));
				tfDeposito.setText(deposito.get().getNombre());
				tfDepositoID.setEnabled(false);
				tfDeposito.setEnabled(false);
			}

		}
	}

	private boolean getStockDisp(Double cantDep, Double cant) {
		boolean result = false;

		if (cantDep != null) {
			if (cantDep < cant) {
				Notifications.showAlert(
						"Disponibilidad de " + FormatearValor.doubleAString(cantDep) + " piezas en el stock");
				result = false;
			} else {
				result = true;
			}
		}

		return result;
	}

//	
//	public Optional<Producto> getProducto() {
//		return producto;
//	}
//
//	public void setProducto(Optional<Producto> producto) {
//		this.producto = producto;
//	}

	private void findCondicionPago(int cantDia) {
		Optional<CondicionPago> condicionPago = condicionPagoService.findByCantDia(cantDia);
		if (condicionPago.isPresent()) {
			// tfCondicionPago.setText(String.valueOf(cantDia));
			if (cantDia > 0)
				calculateVencimiento();

			if (conf != null) {
				if (conf.getPideDescuento() == 1) {
					tfDescuento.requestFocus();
				} else {
					tfObs.requestFocus();
				}
			}
		} else {
			showDialog(CONDICION_PAGO_CODE);
		}
	}

	private boolean validateCantidad() {
		boolean result = false;

		if (tfDepositoID.getText().isEmpty()) {
			Notifications.showAlert("Debes ingresar deposito");
			return false;
		}

		Long productoId = Long.valueOf(tfProductoID.getText());
		Double cantidad = FormatearValor.stringToDoubleFormat(tfCantidad.getText());
		//String valor= tfCantidad.getText();
		//tfCantidad.setText(valor.replace(".", ","));

		Producto p = productoService.getStockDepositoByProductoId(productoId);
		int depositoId = Integer.parseInt(tfDepositoID.getText());
		Double salPend = 0d;
		Double depBlo = 0d;
		if (conf != null && conf.getHabilitaLanzamientoCaja() == 1)
			salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;

		switch (depositoId) {
		case 1:
			String d = decfor.format(p.getDepO1().doubleValue());
			Double dep01 = p.getDepO1() != null ?  FormatearValor.stringADouble(d) : 0;
			depBlo = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
			// result = getStockDisp(dep01 - salPend - depBlo, cantidad);
			result = getStockDisp(dep01, cantidad);
			break;
		case 2:

			Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
			depBlo = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
			result = getStockDisp(dep02, cantidad);
			break;
		case 3:
			Double dep03 = p.getDepO3() != null ? p.getDepO3() : 0;
			depBlo = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
			result = getStockDisp(dep03 - salPend - depBlo, cantidad);
			break;
		case 4:
			Double dep04 = p.getDepO4() != null ? p.getDepO4() : 0;
			depBlo = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
			result = getStockDisp(dep04 - salPend - depBlo, cantidad);
			break;
		case 5:
			Double dep05 = p.getDepO5() != null ? p.getDepO5() : 0;
			depBlo = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
			result = getStockDisp(dep05 - salPend - depBlo, cantidad);
			break;
		default:
			break;
		}

		return result;
	}

	private void abandonarNota() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "ABANDONAR NOTA.?", "AVISO",
				JOptionPane.OK_CANCEL_OPTION);

		if (respuesta == 0) {
			// removeItemBloq();
			clearForm();
			//newVenta();
		} else {
			tfProductoID.requestFocus();
		}
	}

	private Boolean isValidItem() {
		if (tfProductoID.getText().isEmpty()) {
			Notifications.showAlert("Digite el codigo del Producto");
			tfProductoID.requestFocus();
			return false;
		} else if (tfCantidad.getText().isEmpty()) {
			Notifications.showAlert("Digite la cantidad");
			tfCantidad.requestFocus();
			return false;
		} else if (!tfCantidad.getText().isEmpty() && FormatearValor.stringToDoubleFormat(tfCantidad.getText()) <= 0.00d) {
			Notifications.showAlert("La cantidad debe ser mayor a cero punto 00");
			tfCantidad.requestFocus();
			return false;
		} else if (tfPrecio.getText().isEmpty()) {
			Notifications.showAlert("Digite el precio");
			tfPrecio.requestFocus();
			return false;
		} else if (!tfPrecio.getText().isEmpty() && FormatearValor.stringADouble(tfPrecio.getText()) <= 0) {
			Notifications.showAlert("El precio debe ser mayor a cero");
			tfPrecio.requestFocus();
			return false;
		}

		return true;
	}

	private Venta getVentaFrom() {
		Venta venta = new Venta();
		venta.setFecha(new Date());
		this.setFechaImpresion(new Date());
		venta.setHora(new Date());

		if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("30 días")) {
			venta.setVencimiento(
					Fechas.sumarFecha(Integer.valueOf(30), 0, 0, Fechas.dateUtilAStringDDMMAAAA(new Date())));
			venta.setCondicion(2);
		} else {
			venta.setVencimiento(new Date());
			venta.setCondicion(1);
		}

		venta.setComprobante("SIN COMPROBANTE");

		if (!tfVendedorID.getText().isEmpty())
			venta.setVendedor(new Usuario(Long.valueOf(tfVendedorID.getText())));
		else
			venta.setVendedor(new Usuario(GlobalVars.USER_ID));

		if (!tfDepositoID.getText().isEmpty())
			venta.setDeposito(new Deposito(Long.valueOf(tfDepositoID.getText())));
		else
			venta.setDeposito(new Deposito(GlobalVars.DEPOSITO_ID));

		venta.setCliente(new Cliente(Long.valueOf(tfClienteID.getText())));
		venta.setClienteNombre(tfClienteNombre.getText());
		venta.setClienteRuc(tfClienteRuc.getText());
		venta.setClienteDireccion(tfClienteDireccion.getText());

		if (conf != null && conf.getHabilitaLanzamientoCaja() == 1)
			venta.setSituacion("PENDIENTE");
		else {
			if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("Contado")) {
				venta.setSituacion("PAGADO");
				venta.setTotalPagado(
						tfTotal.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotal.getText()));
			} else
				venta.setSituacion("PROCESADO");
		}

		venta.setObs(tfObs.getText());
		String valorItem = tfTotalItems.getText().isEmpty() ? "1"
				: FormatearValor.sinSeparadorDeMiles(tfTotalItems.getText().toString());
		//NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		//Double d=format.pa(valorItem);
		try {
			venta.setCantItem(FormatearValor.parseDecimal(valorItem));
		} catch (ParseException e) {
			venta.setCantItem(null);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// totales
		venta.setTotalGravada10(tfTotal.getText().isEmpty() ? 0
				: Double.valueOf(Math.round(FormatearValor.stringToDouble(tfTotal.getText()) / 11))); // SUBTOTAL
		venta.setTotalDescuento(
				tfDescuento.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfDescuento.getText())); // DESCUENTO
		venta.setTotalGeneral(tfTotal.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotal.getText())); // TOTAL
																													// GENERAL
		// venta.setTotalFlete(tfFlete.getText().isEmpty() ? 0 :
		// FormatearValor.stringToDouble(tfFlete.getText())); // FLETE

		List<VentaDetalle> detalles = new ArrayList<>();

		if (!tfDescuento.getText().isEmpty()) { // !tfFlete.getText().isEmpty() ||
			// update precio fob
			// Double desc = tfDescuento.getText().isEmpty() ? 0 :
			// FormatearValor.stringToDouble(tfDescuento.getText());
			// Double gastos = tfFlete.getText().isEmpty() ? 0 :
			// FormatearValor.stringToDouble(tfFlete.getText());
			// Double total = tfTotal.getText().isEmpty() ? 0 :
			// FormatearValor.stringToDouble(tfTotal.getText());

			// Double costoCif = ((total + gastos) - (-desc)) / total;

			for (VentaDetalle item : itemTableModel.getEntities()) {
				item.setPrecioFob(item.getPrecio());
				// item.setPrecio(item.getPrecio() + costoCif);

				detalles.add(item);
			}

			venta.setItems(detalles);
		} else {
			venta.setItems(itemTableModel.getEntities());
		}

		return venta;
	}

	private VentaDetalle getItem() {
		Double cant = FormatearValor.stringToDoubleFormat(tfCantidad.getText());
		Double precio = FormatearValor.stringToDouble(tfPrecio.getText());
		VentaDetalle item = new VentaDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(cant);
		item.setPrecio(precio);
		item.setSubtotal(cant*precio);
		item.setStock(FormatearValor.stringToDouble(tfStock.getText()));
		item.setDescripcionFiscal(lblDescripcionFiscal.getText());
		item.setPrecioCosto(this.precioCompra);
		item.setCostoCif(this.precioCompra);
		item.setCostoFob(this.precioCompra);
		// item.setDescuento(FormatearValor.stringToDouble(tfDescuentoItem.getText()));
		Integer iva = impuesto;
		item.setIva(iva);

		return item;
	}

	private void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfStock.setText("");
		lblCosto.setText("");
		tfProductoID.requestFocus();
	}

	private void setTotals(Double cantItem, Double total) {
		Double descuento = tfDescuento.getText().isEmpty() ? 0d : FormatearValor.stringToDouble(tfDescuento.getText());
		// Double flete = tfFlete.getText().isEmpty() ? 0d :
		// FormatearValor.stringToDouble(tfFlete.getText());
		Double totalGeneral = (total) - descuento; // (total + flete)

		// tfSubtotal.setText(FormatearValor.doubleAString(total));
		tfTotal.setText(FormatearValor.doubleAString(totalGeneral));

		if (cantItem != 0) {
			tfTotalItems.setText(FormatearValor.doubleAString(cantItem));
		}
	}

	public void clearForm() {
		tfClienteID.setText("");
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfDvRuc.setText("");
		tfClienteDireccion.setText("");
		// tfVendedorID.setText("");
		// tfVendedor.setText("");
		// tfDeposito.setText("");
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfObs.setText("");
		tfTotal.setText("0");
		tfTotalItems.setText("0");
		tfDescuento.setText("0");
		// tfDescuentoItem.setText("0");
		tfCuotaCant.setText("0");
		tfVence.setText("");
		tfCondicionPago.setSelectedIndex(0);
		tfStock.setText("");
		lblCosto.setText("");
		tfClienteNombre.setEnabled(false);
		tfClienteDireccion.setEnabled(false);
		tfCondicionPago.setEnabled(true);
		tfClienteID.requestFocus();
		tfCuotaCant.setEnabled(true);
		lblDescripcionFiscal.setText("");

		/*while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}*/

		// tfDepositoID.setEditable(true);
		// tfDepositoID.setText("");

		calculateVencimiento();

		newVenta();
	}

	public JTextField getTfClienteID() {
		return tfClienteID;
	}

	public JTextField getTfVentaId() {
		return tfVentaId;
	}

	public JTextField getTfVendedorID() {
		return tfVendedorID;
	}

	public JTextField getTfProductoID() {
		return tfProductoID;
	}

	public JTextField getTfCantidad() {
		return tfCantidad;
	}

	public JTextField getTfPrecio() {
		return tfPrecio;
	}

	public JTextField getTfDepositoID() {
		return tfDepositoID;
	}

	public JTextField getTfDescuento() {
		return tfDescuento;
	}

	public JTextField getTfCuotaCant() {
		return tfCuotaCant;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnRemove() {
		return btnRemove;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public JButton getBtnAnular() {
		return btnAnular;
	}

	private void calculateVencimiento() {
		// if (!cbCondPago.getText().isEmpty()) {
		String fecha = Fechas.dateUtilAStringDDMMAAAA(new Date());
		if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("30 días")) {
			Date fechaVenc = Fechas.sumarFecha(Integer.valueOf(30), 0, 0, fecha);
			tfVence.setText(Fechas.formatoDDMMAAAA(fechaVenc));
		}
	}

	private void calculatePrecioTotal() {
		try {
			Double cantidad = FormatearValor.stringToDoubleFormat(tfCantidad.getText());
			Double precioUnit = FormatearValor.stringToDouble(tfPrecio.getText());
			Double precioTotal = cantidad * precioUnit;
			tfPrecio.setText(FormatearValor.doubleAString(precioUnit));
			if (this.precioCompra>0 && this.precioCompra> precioUnit) {
				Notifications.showAlert("No puede vender a bajo del precio de compra!");
				tfPrecio.requestFocus();
			}else {
				if(this.precioCompra==0) {
					Notifications.showAlert("Verificar el precio de compra, esta en 0!");
					tfProductoID.requestFocus();
				}else {
					tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));
					btnAdd.requestFocus();
				}
			}
		} catch (Exception e) {
			Notifications.showAlert("Verficar datos de producto, cantidad o precio!");
			tfProductoID.requestFocus();
		}
		
	}

	private void showDialog(int code) {
		switch (code) {
		case CLIENTE_CODE:
			clientDialog.getClientes("");
			clientDialog.setInterfaz(this);
			clientDialog.setVisible(true);
			break;
		case VENDEDOR_CODE:
			vendedorDialog.setInterfaz(this);
			vendedorDialog.setVisible(true);
			break;
		case DEPOSITO_CODE:
			depositoDialog.setInterfaz(this);
			depositoDialog.setVisible(true);
			break;
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.getProductos();
			productoDialog.limpiaDatosComplementarios();
			productoDialog.setVisible(true);
			break;
		case SALDO_PRODUCTO_CODE:
			saldoDeposito.loadProductos("");
			saldoDeposito.setVisible(true);
			break;
		case CONDICION_PAGO_CODE:
			condicionDialog.setInterfaz(this);
			condicionDialog.setVisible(true);
			break;
		case VENTA_CODE:
			consultaVentasDelDiaDialog.setInterfaz(this);
			consultaVentasDelDiaDialog.setVentasDelDia();
			consultaVentasDelDiaDialog.setVisible(true);
			break;
		case CLIENTE_ADD_CODE:
			clienteAddPanel.setInterfaz(this);
			clienteAddPanel.loadCiudades();
			clienteAddPanel.loadEmpresas();
			clienteAddPanel.addNewCliente();
			clienteAddPanel.setVisible(true);
			break;
		case PRODUCTO_ADD_CODE:
			productoController.setInterfaz(this);
			//productoController.addNewProduct();
			productoController.prepareAndOpenFrame();
			productoController.setOrigen("PRODUCTO");
			break;

		default:
			break;
		}
	}

	@Override
	public void getEntity(Cliente cliente) {
		try {
			setCliente(cliente);
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el cliente seleccionado, intente nuevamente!");
			// TODO: handle exception
		}
	}

	@Override
	public void getEntity(ClientePais clientePais) {
		try {
			setClientePais(clientePais);
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el cliente importado desde datos libres, intente nuevamente!");
			// TODO: handle exception
		}

	}

	@Override
	public void getEntity(Usuario usuario) {
		try {
			if (usuario != null) {
				tfVendedorID.setText(String.valueOf(usuario.getId()));
				tfVendedor.setText(usuario.getUsuario());
				tfDepositoID.requestFocus();
			}
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el usuario, intente nuevamente!");
			// TODO: handle exception
		}

	}

	@Override
	public void getEntity(Deposito deposito) {
		try {
			if (deposito != null) {
				tfDepositoID.setText(String.valueOf(deposito.getId()));
				tfDeposito.setText(deposito.getNombre());
				tfProductoID.requestFocus();
			}
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el Deposito, intente nuevamente!");
			// TODO: handle exception
		}
	}

	@Override
	public void getEntity(Producto producto) {
		try {
			setProducto(producto);
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el Producto, intente nuevamente!");
			// TODO: handle exception
		}
	}

	@Override
	@Transactional
	public void getEntity(Venta v) {
		try {
			if (v != null) {
				btnGuardar.setVisible(false);
				btnReimpresion.setVisible(true);
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(false);
				setVenta(v);
				tbProductos.disable();
				tfCondicionPago.setEnabled(false);
			} else {
				Notifications.showAlert("Hubo problemas con el Producto, intente nuevamente!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con la venta, intente nuevamente!");
			// TODO: handle exception
		}
	}

	public void setVenta(Venta v) {
		try {
			tfVentaId.setText(v.getId().toString());
			tfClienteID.setText(String.valueOf(v.getCliente().getId()));
			tfClienteNombre.setText(v.getCliente().getNombre());
			tfFechaVenta.setText(v.getFecha()== null ? "" : Fechas.formatoDDMMAAAA(v.getFecha()));
			//tfFechaCompra.setText(c.getFechaCompra() == null ? "" : String.valueOf(c.getFechaCompra()));
					//Fechas.stringToDate(tfFechaCompra.getText()));
			tfClienteRuc.setText(v.getCliente().getCiruc());
			tfDvRuc.setText(v.getCliente().getDvruc());
			tfClienteDireccion.setText(v.getCliente().getDireccion());
			tfTotalItems.setText(String.valueOf(v.getCantItem()));
			if (v.getCondicion() == 1)
				tfCondicionPago.setSelectedIndex(0);
			else
				tfCondicionPago.setSelectedIndex(1);

			if (v.getSituacion().equalsIgnoreCase("1")||v.getSituacion().contentEquals("ANULADO")||v.getSituacion().contentEquals("DEVOLUCION"))
				btnAnular.setVisible(false);
			else
				btnAnular.setVisible(true);

			tfDepositoID.setText(String.valueOf(v.getDeposito().getId()));
			tfDeposito.setText(v.getDeposito().getNombre());
			tfDescuento.setText(String.valueOf(v.getTotalDescuento()==null?0:v.getTotalDescuento()));
			// tfSubtotal.setText(String.valueOf(v.getTotalGravada10()));
			tfTotal.setText(FormatearValor.doubleAString((v.getTotalGeneral())));
			tfObs.setText(String.valueOf(v.getObs()==null?"":v.getObs()));
			if(v.getSituacion().equalsIgnoreCase("0")||v.getSituacion().equalsIgnoreCase("1")) {
				lblSituacion.setText(v.getSituacion().equalsIgnoreCase("0")?"VIGENTE":"ANULADO");					
			}else {
				lblSituacion.setText(v.getSituacion());				
			}
			
			lblHora.setText(Fechas.formatoHHmm(v.getHora()));
			List<VentaDetalle> listaDetalles = new ArrayList<VentaDetalle>();
			List<Object[]> listaItems = ventaService.retriveVentaDetalleByIdVenta(v.getId());
			// venta_id, cantidad, precio, producto, producto_id, subtotal, id, iva
			Double cantItem = 0d;
			Double total = 0d;
			for (Object[] object : listaItems) {
				VentaDetalle det = new VentaDetalle();
				det.setCantidad(Double.valueOf(object[1].toString()));
				det.setPrecio(Double.valueOf(object[2].toString()));
				if(object[3]==null) {
					Producto p= findProductoReturn(object[4].toString());
					det.setProducto(p.getDescripcion());
					det.setSubtotal(det.getCantidad()*det.getPrecio());
				}else {
					det.setProducto(object[3].toString());
					det.setSubtotal(Double.valueOf(object[5].toString()));
				}
				det.setProductoId(Long.valueOf(object[4].toString()));
				det.setIva(Integer.valueOf(object[7].toString()));
				det.setDescripcionFiscal(object[8].toString());
				cantItem+=det.getCantidad();
				total+=det.getSubtotal();

				listaDetalles.add(det);
			}
			setTotals(cantItem, total);
			v.setItems(new ArrayList<VentaDetalle>());
			v.setItems(listaDetalles);
			itemTableModel.clear();
			itemTableModel.addEntities(listaDetalles);
			setVentaSeleccionado(v);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Transactional
	private void updateStockProduct(List<VentaDetalle> items) {
		List<Producto> productos = new ArrayList<>();
		int habilitaLanzamientoCaja = 0;
		if (conf != null && conf.getHabilitaLanzamientoCaja() != 0)
			habilitaLanzamientoCaja = conf.getHabilitaLanzamientoCaja();
		for (VentaDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				int depesitoId = tfDepositoID.getText().isEmpty() ? 0 : Integer.parseInt(tfDepositoID.getText());
				Double salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
				Double cantItem = e.getCantidad();
				if (habilitaLanzamientoCaja == 1) {
					switch (depesitoId) {
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
				} else {
					switch (depesitoId) {
					case 1:
						Double depBloq = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
						Double dep01 = p.getDepO1() != null ? p.getDepO1() : 0;
						p.setDepO1Bloq(depBloq - cantItem);
						p.setDepO1(dep01 - cantItem);
						break;
					case 2:
						Double depBloq02 = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
						Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
						p.setDepO2Bloq(depBloq02 - cantItem);
						p.setDepO2(dep02 - cantItem);
						break;
					case 3:
						Double depBloq03 = p.getDepO3Bloq() != null ? p.getDepO3Bloq() : 0;
						Double dep03 = p.getDepO3() != null ? p.getDepO3() : 0;
						p.setDepO3Bloq(depBloq03 - cantItem);
						p.setDepO3(dep03 - cantItem);
						break;
					case 4:
						Double depBloq04 = p.getDepO4Bloq() != null ? p.getDepO4Bloq() : 0;
						Double dep04 = p.getDepO4() != null ? p.getDepO4() : 0;
						p.setDepO4Bloq(depBloq04 - cantItem);
						p.setDepO4(dep04 - cantItem);
						break;
					case 5:
						Double depBloq05 = p.getDepO5Bloq() != null ? p.getDepO5Bloq() : 0;
						Double dep05 = p.getDepO5() != null ? p.getDepO5() : 0;
						p.setDepO5Bloq(depBloq05 - cantItem);
						p.setDepO5(dep05 - cantItem);
						break;
					default:
						break;
					}
				}

				productos.add(p);
			}
		}

		productoService.updateStock(productos);
	}

	private void updateStockProductRemoved(List<VentaDetalle> items) {
		List<Producto> productos = new ArrayList<>();
		int habilitaLanzamientoCaja = 0;
		if (conf != null && conf.getHabilitaLanzamientoCaja() != 0)
			habilitaLanzamientoCaja = conf.getHabilitaLanzamientoCaja();
		for (VentaDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				int depesitoId = tfDepositoID.getText().isEmpty() ? 0 : Integer.parseInt(tfDepositoID.getText());
				Double salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
				Double cantItem = e.getCantidad();
				if (habilitaLanzamientoCaja == 1) {
					p.setSalidaPend(salPend - cantItem);
				} else {
					switch (depesitoId) {
					case 1:
						Double dep01 = p.getDepO1() != null ? p.getDepO1() : 0;
						p.setDepO1(dep01 + cantItem);
						break;
					case 2:
						Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
						p.setDepO2(dep02 + cantItem);
						break;
					case 3:
						Double dep03 = p.getDepO3() != null ? p.getDepO3() : 0;
						p.setDepO3(dep03 + cantItem);
						break;
					case 4:
						Double dep04 = p.getDepO4() != null ? p.getDepO4() : 0;
						p.setDepO4(dep04 + cantItem);
						break;
					case 5:
						Double dep05 = p.getDepO5() != null ? p.getDepO5() : 0;
						p.setDepO5(dep05 + cantItem);
						break;
					default:
						break;
					}
				}

				productos.add(p);
			}
		}

		productoService.updateStock(productos);
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(this,
				"CONFIRMAR SI ESTA SEGURO LA CONDICIÓN DE PAGO " + tfCondicionPago.getSelectedItem().toString(),
				"AVISO - AGROPROGRESO", JOptionPane.OK_CANCEL_OPTION);
		Integer print=null;
		if (respuesta == 0) {
			if (validateCabezera()) { // && validateItems(itemTableModel.getEntities())
				lanzamientoCaja();
				Venta venta = getVentaFrom();
				venta.setCaja(new Caja(Long.valueOf(1)));

				Cliente clienteNuevo = new Cliente();
				Optional<ValidationError> errors = ventaValidator.validate(venta);

				if (errors.isPresent()) {
					ValidationError validationError = errors.get();
					Notifications.showFormValidationAlert(validationError.getMessage());
				} else {
					if (tfClienteID.getText().equalsIgnoreCase("999999")) {
						clienteNuevo.setId(999999l);
						clienteNuevo.setActivo(1);
						clienteNuevo.setCiruc(tfClienteRuc.getText());
						clienteNuevo.setCiudad(new Ciudad(Long.valueOf(1)));
						clienteNuevo.setDepartamento(new Departamento(Long.valueOf(1)));
						clienteNuevo.setDvruc(tfDvRuc.getText().toString());
						clienteNuevo.setListaPrecio(new ListaPrecio(Long.valueOf(1)));
						clienteNuevo.setNombre(tfClienteNombre.getText());
						clienteNuevo.setPais(new Pais(Long.valueOf(1)));
						clienteNuevo.setRazonSocial(tfClienteNombre.getText());
						String vTipo = (tfClienteRuc.getText().contains("800") ? "JURIDICO" : "FISICO");
						clienteNuevo.setTipo(vTipo);
						venta.setCliente(clienteNuevo);
					}
					
					if (conf != null) {
						int lanzCaja = conf.getHabilitaLanzamientoCaja();
						try {
						Venta v = ventaService.save(lanzCaja, venta, clienteNuevo, tfCondicionPago.getSelectedItem().toString());
						tfVentaId.setText(v.getId()+"");
							Notifications.showAlert("Venta registrado con exito.!");
							print = JOptionPane.showConfirmDialog(this, "IMPRIMIR", "AVISO - AGROPROGRESO",
									JOptionPane.OK_CANCEL_OPTION);
						} catch (Exception e) {
							Notifications.showAlert("Ocurrió un error en Venta!, intente nuevamente");
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					

					if (print == 0)
						imprimirDialogo();
					// else
					newVenta();
					productoDialog.inicializaProductos();
					productoDialog.getProductos();
				}
			}
		} else {
			tfProductoID.requestFocus();
		}
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
		movEgreso = movimientoEgresoService.save(movEgreso);
		MovimientoItemEgreso movItemEgreso = new MovimientoItemEgreso();
		movItemEgreso.setMieNumero(movEgreso.getMegNumero());
		movItemEgreso.setMieEgreso(31);
		movItemEgreso.setMieMonto(c.getMonto());
		movItemEgreso.setMieDescripcion("Egreso de efectivo - Venta Crédito");
		movimientoItemEgresoService.save(movItemEgreso);
	}

	private void movimientoIngresoProcesoCobroVenta(Venta venta) {
		MovimientoIngreso m = new MovimientoIngreso();
		m.setFecha(new Date());
		m.setHora(new Date());
		m.setMinCaja(1);
		m.setMinDocumento(venta.getId().toString());
		m.setMinEntidad(venta.getCliente().getId().toString());
		m.setMinProceso(Integer.valueOf(venta.getId().toString()));
		m.setMinTipoProceso(1);
		m.setMinTipoEntidad(Integer.valueOf(clienteSeleccionado.getTipoEntidad()));
		m.setMinSituacion(0);
		m = movimientoIngresoService.save(m);

		MovimientoItemIngreso mii = new MovimientoItemIngreso();
		mii.setMiiNumero(m.getMinNumero());
		mii.setMiiIngreso(1);
		double monto = (double) Math.round(venta.getTotalGeneral() / 11);
		mii.setMiiMonto(monto);
		movimientoItemIngresoService.save(mii);

		MovimientoItemIngreso miiva = new MovimientoItemIngreso();
		miiva.setMiiNumero(m.getMinNumero());
		miiva.setMiiIngreso(11);
		miiva.setMiiMonto(venta.getTotalGeneral() - monto);
		movimientoItemIngresoService.save(miiva);

		ProcesoCobroVentas pcv = new ProcesoCobroVentas();
		pcv.setPveVenta(venta.getId().intValue());
		pcv.setPveIngresoegreso(1);
		pcv.setPveTipoproceso(31);
		pcv.setPveProceso(m.getMinNumero());
		pcv.setPveFlag(1);
		procesoCobroVentasService.save(pcv);
	}

	private void removeMovimientoIngresoProcesoCobroVenta(Venta venta) {
		try {
			MovimientoIngreso m = movimientoIngresoService.findByMinProceso(Integer.valueOf(venta.getId().toString()));
			Integer cabId = m.getMinNumero();
			movimientoIngresoService.remove(m);

			List<MovimientoItemIngreso> mii = movimientoItemIngresoService.findByCabId(cabId);
			for (MovimientoItemIngreso movimientoItemIngreso : mii) {
				movimientoItemIngresoService.remove(movimientoItemIngreso);
			}
			ProcesoCobroVentas pcv = procesoCobroVentasService.findByPveVenta(cabId);
			procesoCobroVentasService.remove(pcv);

		} catch (Exception e) {

		}
	}

	private CuentaARecibir cuentaARecibirProcesoCobroVenta(Venta venta) {
		CuentaARecibir cuentaARecibir = new CuentaARecibir();

		cuentaARecibir.setFecha(new Date());
		cuentaARecibir.setHora(new Date());
		cuentaARecibir.setNroBoleta(venta.getComprobante());
		cuentaARecibir.setIdEntidad(venta.getCliente().getId());
		cuentaARecibir.setTipoEntidad(2);
		cuentaARecibir.setMonto(venta.getTotalGeneral());
		cuentaARecibir.setCarProceso(venta.getId().intValue());
		cuentaARecibir.setCarSituacion(0);
		cuentaARecibir = cuentaARecibirService.save(cuentaARecibir);

		List<ItemCuentaARecibir> listaItemCuentaARecibir = new ArrayList<ItemCuentaARecibir>();

		int cant = 0;
		int cantDias = 0;
		Date fechaVencimiento = new Date();
		Calendar cal = Calendar.getInstance();
		if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("30 días")) {
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
		itemCuentaARecibirService.save(listaItemCuentaARecibir);
		// Proceso Cobro ventas
		ProcesoCobroVentas pcv = new ProcesoCobroVentas();
		pcv.setPveVenta(venta.getId().intValue());
		pcv.setPveIngresoegreso(2);
		pcv.setPveTipoproceso(31);
		pcv.setPveProceso(cuentaARecibir.getCarNumero());
		pcv.setPveFlag(1);
		procesoCobroVentasService.save(pcv);

		return cuentaARecibir;
	}

	private void anular() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "CONFIRMAR", "AVISO - AGROPROGRESO",
				JOptionPane.OK_CANCEL_OPTION);
		if (respuesta == 0) {
			if (conf != null) {
				int lanzCaja = conf.getHabilitaLanzamientoCaja();
				try {
					if (!ventaSeleccionado.getSituacion().equalsIgnoreCase("ANULADO")) {
						//updateStockProductRemoved(ventaSeleccionado.getItems());
						ventaSeleccionado.setSituacion("ANULADO");
						Venta v=ventaService.saveRemoved(lanzCaja, ventaSeleccionado, ventaSeleccionado.getItems(), tfCondicionPago.getSelectedItem().toString());
						if(v!=null) {
							newVenta();
							Notifications.showAlert("Venta ELIMINADO con exito.!");
						}
					}		
				}catch (Exception e) {
					Notifications.showAlert("Venta no pudo ser ELIMINADO.!");
					// TODO: handle exception
				}
			}
		}
		tfProductoID.requestFocus();
	}

	private void lanzamientoCaja() {
		Optional<Caja> caja = cajaService.findById(1l);
		if (caja.isPresent()) {
			Caja ca = caja.get();
			Optional<AperturaCierreCaja> movCaja = movCajaService.findByCajaAndFechaApertura(ca, new Date());
			if (!movCaja.isPresent()) {
				AperturaCierreCaja newMov = movCajaService.save(new AperturaCierreCaja(ca, new Date(), 0d));
				String msg = "";
				if (newMov != null) {
					msg = "Apertura de Caja correctamente.!";
				} else {
					msg = "Lo sentimos. No se pudo abrir la caja correctamente.!";
				}
				Notifications.showAlert(msg);
			}
		}
	}

	private void openMovCaja(Venta venta) {
		cant = 0;
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
		if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("Contado")) {
			movCaja.setObs("Pagado en caja 01 ");
			movCaja.setSituacion("PAGADO");
		} else if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("30 días")) {
			cant = 1;// Integer.valueOf(tfCuotaCant.getText());
			movCaja.setObs("Crédito a cuotas :" + cant);
			movCaja.setSituacion("PROCESADO");
		} else {
			movCaja.setObs("Crédito a " + tfCondicionPago.getSelectedItem().toString() + " días");
			movCaja.setSituacion("PROCESADO");
			cant = 1;
		}
		pagoService.save(movCaja);
	}

	private void removeMovCaja(Venta venta) {
		// cierre de caja del dia anterio
		Optional<MovimientoCaja> movimientoCaja = pagoService.findByIdVenta(venta.getId().toString());
		if (movimientoCaja.isPresent()) {
			MovimientoCaja mc = movimientoCaja.get();
			mc.setObs("VENTA ANULADO");
			mc.setSituacion("ANULADO");
			pagoService.save(mc);
		}
		// remover los otros movimientos insertados
	}

	private Integer removeCuentaARecibirProcesoCobroVenta(Venta venta) {
		CuentaARecibir cuentaARecibir = cuentaARecibirService
				.findByCarProceso(Integer.valueOf(venta.getId().toString()));
		Integer cabId = cuentaARecibir.getCarNumero();
		cuentaARecibirService.remove(cuentaARecibir);

		List<ItemCuentaARecibir> listaItemCuentaARecibir = itemCuentaARecibirService.findByCabId(cabId);
		for (ItemCuentaARecibir itemCuentaARecibir : listaItemCuentaARecibir) {
			itemCuentaARecibirService.remove(itemCuentaARecibir);
		}

		// Proceso Cobro ventas
		ProcesoCobroVentas pcv = procesoCobroVentasService.findByPveVenta(Integer.valueOf(venta.getId().toString()));
		procesoCobroVentasService.remove(pcv);

		return cabId;
	}

	private void removeMovimientoEgreso(Integer carNumero) {
		MovimientoEgreso movEgreso = movimientoEgresoService.findByMegProceso(carNumero);
		List<MovimientoItemEgreso> movItemEgreso = movimientoItemEgresoService.findByCabId(movEgreso.getMegNumero());
		for (MovimientoItemEgreso movimientoItemEgreso : movItemEgreso) {
			movimientoItemEgresoService.remove(movimientoItemEgreso);
		}
		movimientoEgresoService.remove(movEgreso);
	}

	private ImpresionPanel panel = null;

	private void imprimirDialogo() {
		if (this.panel == null) {
			panel = new ImpresionPanel();
			panel.setPanelInterfaz(this);
		}

		panel.setVisible(true);
	}

	private ReImpresionPanel panelReImpresion = null;
	private JLabel lblDescripcionFiscal;

	private void imprimirDialogoReimpresion() {
		if (this.panelReImpresion == null) {
			panelReImpresion = new ReImpresionPanel();
			panelReImpresion.setPanelInterfaz(this);
		}

		panelReImpresion.setVisible(true);
	}

	private void removeItemBloq() {
		// remueve los Items bloqueados
//		for (VentaDetalle item : itemTableModel.getEntities()) {
//			int depositoId = Integer.parseInt(tfDepositoID.getText());
		// removeItemDepBloq(item.getCantidad(), item.getProductoId(), depositoId);
//		}
	}

	private boolean validateCabezera() {
		if (tfClienteID.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Cliente es obligatorio");
			tfClienteID.requestFocus();
			return false;
		} else if (tfVendedorID.getText().isEmpty() && conf != null && conf.getPideVendedor() == 1) {
			Notifications.showAlert("El codigo del Vendedor es obligatorio");
			tfVendedorID.requestFocus();
			return false;
		} else if (tfDepositoID.getText().isEmpty() && conf != null && conf.getPideDeposito() == 1) {
			Notifications.showAlert("El codigo del Deposito es obligatorio");
			tfDepositoID.requestFocus();
			return false;
		}

		if (!tfClienteID.getText().equalsIgnoreCase("999999")) {
			Optional<Cliente> cliente = clienteService.findById(Long.valueOf(tfClienteID.getText()));

			if (!cliente.isPresent()) {
				Notifications.showAlert("El codigo del Cliente es obligatorio");
				tfClienteID.requestFocus();
				return false;
			}
		}

		Optional<Usuario> usuairo;

		if (!tfVendedorID.getText().isEmpty())
			usuairo = vendedorService.findById(Long.valueOf(tfVendedorID.getText()));
		else
			usuairo = vendedorService.findById(GlobalVars.USER_ID);

		if (!usuairo.isPresent()) {
			Notifications.showAlert("El codigo del Vendedor no existe.!");
			tfVendedorID.requestFocus();
			return false;
		}

		Optional<Deposito> deposito;

		if (!tfDepositoID.getText().isEmpty())
			deposito = depositoService.findById(Long.valueOf(tfDepositoID.getText()));
		else
			deposito = depositoService.findById(GlobalVars.DEPOSITO_ID);

		if (!deposito.isPresent()) {
			Notifications.showAlert("El codigo del Deposito no existe.!");
			tfDepositoID.requestFocus();
			return false;
		}
//		if (tfCondicionPago.getText().equalsIgnoreCase("1" + "00")) {
//			if (tfCuotaCant.getText().isEmpty() || Integer.valueOf(tfCuotaCant.getText()) <= 0) {
//				Notifications.showAlert("La cantidad de cuota debe ser mayor a 0(cero) !");
//				return false;
//			}
//		}

		return true;
	}

//	private boolean validateItems(List<VentaDetalle> items) {
//		items.forEach(e -> {
//			Optional<Producto> producto = productoService.findById(e.getProductoId());
//			
//			boolean isValid = validateCantidad(e.getProductoId(), e.getCantidad());
//
//			if (isValid) {
//				// verificar la cantidad
//				Double stock = producto.get().getStock();
//
//				if (stock < e.getCantidad()) {
//					isValid = true;
//					Notifications.showAlert("Insuficiente Stock para el Item: " + producto.get().getDescripcion()
//							+ ". Stock Actual: " + producto.get().getStock());
//					
//					return;
//				}
//			}
//
//		});
//
//		return true;
//	}

	public void newVenta() {
		Long max = ventaService.getRowCount()+1;
		tfVentaId.setText(String.valueOf(max));
		tfFechaVenta.setText(Fechas.formatoDDMMAAAA(new Date()));
		resetCliente();
		resetVenta();
		btnAnular.setVisible(false);
		btnGuardar.setVisible(true);
		btnReimpresion.setVisible(false);
		btnAdd.setEnabled(true);
		btnRemove.setEnabled(true);
		tfClienteID.requestFocus();
		tfCuotaCant.setEnabled(false);
		tbProductos.enable();
		tfCondicionPago.setEnabled(true);
		tfClienteID.requestFocus();
	}

	private void resetVenta() {
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfObs.setText("");
		tfTotal.setText("0");
		tfTotalItems.setText("0");
		tfDescuento.setText("0");
		// tfDescuentoItem.setText("0");
		lblSituacion.setText("VIGENTE");
		lblHora.setText("");
		tfCuotaCant.setText("0");
		tfVence.setText("");
		tfCondicionPago.setSelectedIndex(0);
		tfCondicionPago.setEnabled(true);
		tbProductos.enable();
		tfClienteNombre.setEnabled(false);
		tfStock.setText("");
		lblCosto.setText("");
		//itemTableModel = new VentaItemTableModel();
		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

	}

	private void resetCliente() {
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfDvRuc.setText("");
		tfClienteDireccion.setText("");
		tfClienteID.setText("");
	}

	private void findClientById(Long id) {
		try {
			Optional<Cliente> cliente = clienteService.findById(id);
			if (cliente.isPresent()) {
				setCliente(cliente.get());
			} else {
				Notifications.showAlert("No existe Cliente con el codigo informado.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas en la busqueda, intente nuevamente!");
		}
	}

	private void findClientByRuc(String ciRuc) {
		try {
			Optional<Cliente> cliente = Optional.ofNullable(clienteService.findByRuc(ciRuc));
			if (cliente.isPresent()) {
				setCliente(cliente.get());
			} else {
				Notifications.showAlert("Cliente no registrado, al confirmar la venta estará registrado!");
				Optional<ClientePais> clientePais = Optional.of(clientePaisService.findByRuc(ciRuc));
				if (clientePais.isPresent())
					setClienteSET(clientePais);
					else
						tfClienteID.requestFocus();
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas en la busqueda, intente nuevamente!");
			clearForm();
			tfClienteRuc.requestFocus();
		}
	}

	private void setCliente(Cliente cliente) {
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfDvRuc.setText("");
		tfClienteDireccion.setText("");
		if (cliente != null) {
			setClienteSeleccionado(cliente);
			tfClienteID.setText(String.valueOf(cliente.getId()));
			tfClienteNombre.setText(cliente.getRazonSocial());
			tfClienteRuc.setText(cliente.getCiruc());
			tfDvRuc.setText(cliente.getDvruc());
			tfClienteDireccion.setText(cliente.getDireccion());
			if (conf.getPermitePrecioPorCliente() == 1 && cliente.getListaPrecio() != null)
				nivelPrecio = cliente.getListaPrecio().getNombre();
			else {
				nivelPrecio = conf.getPrecioDefinido();
			}

			if (cliente.getId() == 0) {
				// habilitar nombre, ruc, direccion
				tfClienteNombre.setEnabled(false);
				tfClienteRuc.setEnabled(true);
				tfClienteDireccion.setEnabled(true);
				tfProductoID.requestFocus();

				tfCondicionPago.setEnabled(false);
				tfCondicionPago.setSelectedIndex(0);

				calculateVencimiento();
			}
		}
	}

	private void setClientePais(ClientePais clientePais) {
		if (clientePais != null) {
			tfClienteID.setText(String.valueOf(clientePais.getId()));
			tfClienteNombre.setText(clientePais.getRazonSocial());
			tfClienteRuc.setText(clientePais.getCiruc());
			tfClienteDireccion.setText("");
		}
	}

	private void setClienteSET(Optional<ClientePais> clientePai) {
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfDvRuc.setText("");
		tfClienteDireccion.setText("");
		if (clientePai.get() != null) {
			tfClienteID.setText("999999");
			String[] razonS=clientePai.get().getRazonSocial().split(",");
			tfClienteNombre.setText(razonS[1]+" "+razonS[0]);
			tfClienteRuc.setText(clientePai.get().getCiruc());
			tfClienteDireccion.setText("");
			tfDvRuc.setText(clientePai.get().getDvruc());
			nivelPrecio = "Precio B";
			tfCondicionPago.setEnabled(false);
			tfCondicionPago.setSelectedIndex(0);
			// tfClienteID.setEnabled(false);
			tfClienteNombre.setEnabled(false);
		}
	}

	private void findVendedorById(Long id) {
		try {
			Optional<Usuario> usuario = vendedorService.findById(id);
			if (usuario.isPresent()) {
				String nombre = usuario.get().getUsuario();
				tfVendedor.setText(nombre);

				if (conf != null) {
					if (conf.getPideDeposito() == 1)
						tfDepositoID.requestFocus();
					else
						tfProductoID.requestFocus();
				} else {
					tfDepositoID.requestFocus();
				}

			} else {
				Notifications.showAlert("No existe Vendedor con este codigo.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con el vendedor!");
		}
	}

	private void findDepositoById(Long id) {
		try {
			Optional<Deposito> deposito = depositoService.findById(id);

			if (deposito.isPresent()) {
				tfDepositoID.setEditable(false);
				tfDepositoID.setText(deposito.get().getId().toString());
				tfDeposito.setText(deposito.get().getNombre());
				tfProductoID.requestFocus();
			} else {
				Notifications.showAlert("No existe Deposito con este codigo.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con el Deposito, intente nuevamente!");
		}
	}

	private void findProducto(String id) {
		try {
			Optional<Producto> producto = null;
			producto = productoService.findById(Long.valueOf(id.trim()));
			if (!producto.isPresent()) {
				if (conf != null && conf.getPermiteVentaPorReferencia() == 1)
					producto = productoService.findByReferencia(id);
			}

			if (producto.isPresent()) {
				setProducto(producto.get());
			} else {
				Notifications.showAlert("No existe producto informado. Verifique por favor.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con el Producto, intente nuevamente!");
		}
	}
	
	private Producto findProductoReturn(String id) {
		Optional<Producto> producto = null;
		try {
			producto = productoService.findById(Long.valueOf(id.trim()));
			if (!producto.isPresent()) {
				Notifications.showAlert("No existe producto informado. Verifique por favor.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con el Producto, intente nuevamente!");
		}
		return producto.get();		
	}

	private void findVenta(String id) {
		try {
			Optional<Venta> venta = null;
			venta = ventaService.findById(Long.valueOf(id.trim()));
			if (venta.isPresent()) {
				setVenta(venta.get());
				btnGuardar.setVisible(false);
				btnReimpresion.setVisible(true);
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(false);
				tbProductos.disable();
				tfCondicionPago.setEnabled(false);
			} else {
				Notifications.showAlert("No existe venta informado. Verifique por favor.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con la venta, intente nuevamente!");
		}

	}

	private void setProducto(Producto producto) {
		try {
			if (producto != null) {
				if (producto.getSubgrupo().getTipo().equals("S"))
					isProductService = true;
				if(nivelPrecio==null)
					nivelPrecio= conf.getPrecioDefinido();
				
				precioInicial = setPrecioByCliente(nivelPrecio, producto);
				this.precioA = producto.getPrecioVentaA();
				this.precioB = producto.getPrecioVentaB();
				this.precioC = producto.getPrecioVentaC();
				this.impuesto = producto.getImpuesto().getPorcentaje().intValue();
				this.precioCompra= producto.getPrecioCosto()==null?0:producto.getPrecioCosto();
				// setProductoSeleccionado(producto);

				tfProductoID.setText(String.valueOf(producto.getId()));
				tfDescripcion.setText(producto.getDescripcion());
				lblDescripcionFiscal.setText(producto.getDescripcionFiscal());
				double d = Math.round(precioInicial);
				tfPrecio.setText(FormatearValor.doubleAString(d));
				tfCantidad.setText("1");
				// tfDescuentoItem.setText(FormatearValor.doubleAString(0d));
				tfCantidad.requestFocus();
				Double cantStock=FormatearValor.stringToDoubleFormat(producto.getDepO1().toString());
				tfStock.setText(FormatearValor.doubleAString(cantStock));
				lblCosto.setText(FormatearValor.doubleAString(this.precioCompra));
			}
		} catch (Exception e) {
			Notifications.showAlert("Producto sin Stock, verifique datos del producto!");
		}
	}

	private Double setPrecioByCliente(String nivelPrecio, Producto producto) {
		Double precio = 0D;
		switch (nivelPrecio) {
		case "Precio A":
			precio = producto.getPrecioVentaA();
			break;
		case "Precio B":
			precio = producto.getPrecioVentaB();
			break;
		case "Precio C":
			precio = producto.getPrecioVentaC();
			break;
		case "Precio D":
			precio = producto.getPrecioVentaD();
			break;
		case "Precio E":
			precio = producto.getPrecioVentaE();
			break;

		default:
			break;
		}

		return precio;
	}

	private void addItemCantBloq(Long productoId, Double cantidad, Double cantAnterior, int depositoId) {
		try {
			Optional<Producto> p = productoService.findById(productoId);

			if (p.isPresent()) {
				Producto producto = p.get();

				switch (depositoId) {
				case 1:
					String s_dep01 = p.get().getDepO1() != null ? decfor.format(p.get().getDepO1()) : "0";
					// Double stockDepBloq = p.get().getDepO1Bloq() != null ? p.get().getDepO1Bloq()
					// : 0;
					Double stockDep01=Double.valueOf(s_dep01);
					if (stockDep01 >= cantidad) {
						// producto.setDepO1Bloq((stockDepBloq + cantidad) - cantAnterior);
					} else {
						Notifications.showAlert("No tiene suficiente Stock para el Item");
					}

					break;
				case 2:
					Double stockDep02 = p.get().getDepO2() != null ? p.get().getDepO2() : 0;
					Double stockDep02Bloq = p.get().getDepO2Bloq() != null ? p.get().getDepO2Bloq() : 0;

					if (stockDep02 >= cantidad) {
						producto.setDepO2Bloq((stockDep02Bloq + cantidad) - cantAnterior);
					} else {
						Notifications.showAlert("No tiene suficiente Stock para el Item");
					}

					break;
				case 3:
					Double stockDep03 = p.get().getDepO3() != null ? p.get().getDepO3() : 0;
					Double stockDep03Bloq = p.get().getDepO3Bloq() != null ? p.get().getDepO3Bloq() : 0;

					if (stockDep03 >= cantidad) {
						producto.setDepO3Bloq((stockDep03Bloq + cantidad) - cantAnterior);
					} else {
						Notifications.showAlert("No tiene suficiente Stock para el Item");
					}

					break;
				case 4:
					Double stockDep04 = p.get().getDepO4() != null ? p.get().getDepO4() : 0;
					Double stockDep04Bloq = p.get().getDepO4Bloq() != null ? p.get().getDepO4Bloq() : 0;

					if (stockDep04 >= cantidad) {
						producto.setDepO4Bloq((stockDep04Bloq + cantidad) - cantAnterior);
					} else {
						Notifications.showAlert("No tiene suficiente Stock para el Item");
					}

					break;
				case 5:
					Double stockDep05 = p.get().getDepO5() != null ? p.get().getDepO5() : 0;
					Double stockDep05Bloq = p.get().getDepO5Bloq() != null ? p.get().getDepO5Bloq() : 0;

					if (stockDep05 >= cantidad) {
						producto.setDepO5Bloq((stockDep05Bloq + cantidad) - cantAnterior);
					} else {
						Notifications.showAlert("No tiene suficiente Stock para el Item");
					}
					break;
				default:
					Notifications.showAlert("Verifique producto y deposito para agregar a la tabla.!");
					break;
				}
				// productoService.save(producto);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		tfProductoID.requestFocus();
	}

	private int getDuplicateItemIndex() {
		int fila = -1;

		for (Integer i = 0; i < tbProductos.getRowCount(); i++) {
			String itemId = String.valueOf(tbProductos.getValueAt(i, 0));

			if (tfProductoID.getText().trim().equals(itemId) && !itemId.equals("")) {
				fila = i;
			}
		}

		return fila;
	}

	private void addItem() {
		try {
			if (isValidItem() && validateCantidad()) {
				Long productoId = tfProductoID.getText().isEmpty() ? 1 : Long.valueOf(tfProductoID.getText());
				int depositoId = tfDepositoID.getText().isEmpty() ? 1 : Integer.parseInt(tfDepositoID.getText());
				Double cantidad = tfCantidad.getText().isEmpty() ? 0
						: FormatearValor.stringToDoubleFormat(tfCantidad.getText());

				if (conf != null && conf.getPermiteItemDuplicado() == 1) {
					itemTableModel.addEntity(getItem());
					calculateItem();
					addItemCantBloq(productoId, cantidad, 0d, depositoId);
					tfProductoID.requestFocus();
				} else {
					int fila = getDuplicateItemIndex();

					if (fila != -1) {
						Integer respuesta = JOptionPane.showConfirmDialog(this,
								"Registro ya existe en la grilla, desea actualizar los datos?", "AVISO",
								JOptionPane.OK_CANCEL_OPTION);

						if (respuesta == 0) {
							Double cantAnterior = Double.valueOf(String.valueOf(tbProductos.getValueAt(fila, 1)));

							itemTableModel.removeRow(fila);
							itemTableModel.addEntity(getItem());

							calculateItem();
							addItemCantBloq(productoId, cantidad, cantAnterior, depositoId);
						} else {
							tfProductoID.requestFocus();
						}
					} else {
						itemTableModel.addEntity(getItem());
						calculateItem();
						addItemCantBloq(productoId, cantidad, 0d, depositoId);
						tfProductoID.requestFocus();
					}
				}

				clearItem();
			} else {
				Notifications.showAlert("Problemas para agregar items!");
				clearItem();
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas para agregar items!");
			clearItem();
		}

	}

	private void removeItem() {
		try {
			int selectedRow = tbProductos.getSelectedRow();

			if (selectedRow != -1) {
				// VentaDetalle item = itemTableModel.getEntityByRow(selectedRow);
				itemTableModel.removeRow(selectedRow);

//				if (!isProductService) {
//					removeItemDepBloq(item.getCantidad(), item.getProductoId(), Integer.parseInt(tfDepositoID.getText()));
//				}

				clearItem();
				calculateItem();

				isProductService = false;
			} else {
				Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void removeItemDepBloq(Double cantBloq, Long productoId, int depositoId) {
		Optional<Producto> p = productoService.findById(productoId);

		if (p.isPresent()) {
			Producto producto = p.get();

			switch (depositoId) {
			case 1:
				Double stockDepBloq = p.get().getDepO1Bloq() != null ? p.get().getDepO1Bloq() : 0;
				producto.setDepO1Bloq(stockDepBloq - cantBloq);
				break;
			case 2:
				Double stockDep02Bloq = p.get().getDepO2Bloq() != null ? p.get().getDepO2Bloq() : 0;
				producto.setDepO2Bloq(stockDep02Bloq - cantBloq);
				break;
			case 3:
				Double stockDep03Bloq = p.get().getDepO3Bloq() != null ? p.get().getDepO3Bloq() : 0;
				producto.setDepO3Bloq(stockDep03Bloq - cantBloq);
				break;
			case 4:
				Double stockDep04Bloq = p.get().getDepO4Bloq() != null ? p.get().getDepO4Bloq() : 0;
				producto.setDepO4Bloq(stockDep04Bloq - cantBloq);
				break;
			case 5:
				Double stockDep05Bloq = p.get().getDepO5Bloq() != null ? p.get().getDepO5Bloq() : 0;
				producto.setDepO5Bloq(stockDep05Bloq - cantBloq);
				break;

			default:
				break;
			}

			productoService.save(producto);
		}
	}

	public Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(Producto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public Venta getVentaSeleccionado() {
		return ventaSeleccionado;
	}

	public void setVentaSeleccionado(Venta ventaSeleccionado) {
		this.ventaSeleccionado = ventaSeleccionado;
	}

	public Cliente getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(Cliente clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

	public Double getPrecioInicial() {
		return precioInicial;
	}

	public void setPrecioInicial(Double precioInicial) {
		this.precioInicial = precioInicial;
	}

	private void calculateItem() {
//		Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
//		Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotal()).sum();
		Double cantItem = 0d;
		Double total = 0d;
		List<VentaDetalle> listVentaDetalle = itemTableModel.getEntities();
		for (VentaDetalle ventaDetalle : listVentaDetalle) {
			ventaDetalle.setSubtotal(ventaDetalle.getCantidad() * ventaDetalle.getPrecio());
			cantItem += ventaDetalle.getCantidad();
			total += ventaDetalle.getSubtotal();
		}

		setTotals(cantItem, total);
	}

	@Override
	public void getEntity(CondicionPago condicionPago) {
		if (condicionPago != null) {
			if (conf != null && conf.getPideDescuento() == 1)
				tfDescuento.requestFocus();
			else
				tfObs.requestFocus();
		} else {
			Notifications.showAlert("Hubo problemas con condicion de pago, intente nuevamente!");
		}
	}

	@Override
	public void imprimirTicket() {
		ImpresionUtil.performTicket(tfClienteNombre.getText(),tfClienteRuc.getText(),tfClienteDireccion.getText(),itemTableModel.getEntities(),
				tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("0") ? "CONTADO" : "CREDITO",
				tfVentaId.getText(), tfTotal.getText());
		clearForm();
	}

	@Override
	public void imprimirNota(boolean impresora) {
		ImpresionUtil.performNota(tfClienteNombre.getText(), tfClienteRuc.getText() + "-" + tfDvRuc.getText(),
				"000", tfClienteDireccion.getText(), tfVentaId.getText(),
				tfCondicionPago.getSelectedItem().toString(),
				tfVendedor.getText().isEmpty() ? GlobalVars.USER : tfVendedor.getText(), tfTotal.getText(),
				itemTableModel.getEntities(), this.fechaImpresion, impresora);
		clearForm();
	}

	@Override
	public void imprimirFactura(boolean impresora) {

		ImpresionUtil.performFactura(tfClienteNombre.getText(), tfClienteRuc.getText() + "-" + tfDvRuc.getText(),
				"(0983) 518 217", tfClienteDireccion.getText(), tfVentaId.getText(),
				tfCondicionPago.getSelectedItem().toString(),
				tfVendedor.getText().isEmpty() ? GlobalVars.USER : tfVendedor.getText(), tfTotal.getText(),
				itemTableModel.getEntities(), this.fechaImpresion, impresora);
		clearForm();
	}

	@Override
	public void cancelarImpresion() {
		clearForm();
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	@Override
	public void cargaFecha(Date fecha) {
		this.setFechaImpresion(fecha);
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		this.cant = cant;
	}

	public Double getPrecioA() {
		return precioA;
	}

	public void setPrecioA(Double precioA) {
		this.precioA = precioA;
	}

	public Double getPrecioB() {
		return precioB;
	}

	public void setPrecioB(Double precioB) {
		this.precioB = precioB;
	}

	public Double getPrecioC() {
		return precioC;
	}

	public void setPrecioC(Double precioC) {
		this.precioC = precioC;
	}
	
	public Double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(Double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public int getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(int impuesto) {
		this.impuesto = impuesto;
	}
}
