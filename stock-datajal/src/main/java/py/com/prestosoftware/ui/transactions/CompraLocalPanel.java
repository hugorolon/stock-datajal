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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.CuentaAPagar;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
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
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.CondicionPagoService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.CuentaAPagarService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ItemCuentaAPagarService;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.MovimientoEgresoService;
import py.com.prestosoftware.domain.services.MovimientoIngresoService;
import py.com.prestosoftware.domain.services.MovimientoItemEgresoService;
import py.com.prestosoftware.domain.services.MovimientoItemIngresoService;
import py.com.prestosoftware.domain.services.ProcesoPagoComprasService;
import py.com.prestosoftware.domain.services.ProcesoPagoProveedoresService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.validations.CompraValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.CompraDialog;
import py.com.prestosoftware.ui.search.CompraInterfaz;
import py.com.prestosoftware.ui.search.CondicionPagoDialog;
import py.com.prestosoftware.ui.search.CondicionPagoInterfaz;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.MonedaDialog;
import py.com.prestosoftware.ui.search.MonedaInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.shared.PanelCompraInterfaz;
import py.com.prestosoftware.ui.table.CompraItemTableModel;
import py.com.prestosoftware.util.Notifications;

@Component("compraLocal")
@Primary
public class CompraLocalPanel extends JFrame implements ProveedorInterfaz,
		ProductoInterfaz, PanelCompraInterfaz, CompraInterfaz, CondicionPagoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int PROVEEDOR_CODE = 1;
	private static final int MONEDA_CODE = 2;
	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4;
	private static final int CONDICION_PAGO_CODE = 5;
	private static final int COMPRA_CODE = 6;

	private JTextField tfNombre, tfDescripcion, tfProductoID, tfProveedorID, tfPrecioTotal, tfCompraId;
	private JTextField tfPrecio, tfCantidad, tfCantItem;
	private JTextField tfCondicion;
	private JTextField tfObs, tfTotalGeneral, tfCuotaCant;
	private JTextField tfRuc, tfDireccion;
	private JTextField tfFactura;
	private JFormattedTextField tfFechaCompra;
	private JButton btnAdd, btnRemove;
	private JButton btnGuardar, btnCancelar, btnCerrar, btnReimpresion, btnAnular;
	private JPanel panel;
	private JTable tbProductos;
	private JLabel lblBuscadorDeCompra;
	private JLabel label;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	protected Action addAction;

	private CompraService compraService;
	private CompraValidator compraValidator;
	private CompraItemTableModel itemTableModel;

	private ProveedorService proveedorService;
	private ProductoService productoService;
	private AperturaCierreCajaService movCajaService;
	private CajaService cajaService;
	private MovimientoCajaService pagoService;

	private CompraDialog compraDialog;
	private ConsultaProveedor proveedorDialog;
	private CondicionPagoDialog condicionPagoDialog;
	private ProductoDialog productoDialog;
	private CondicionPagoService condicionPagoService;
	private ConfiguracionService configService;
	
	private MovimientoIngresoService movimientoIngresoService;
	private MovimientoItemIngresoService movimientoItemIngresoService;
	private MovimientoEgresoService movimientoEgresoService;
	private MovimientoItemEgresoService movimientoItemEgresoService;
	private ProcesoPagoComprasService procesoPagoComprasService;
	private CuentaAPagarService cuentaAPagarService;
	private ItemCuentaAPagarService itemCuentaAPagarService;
	private int cant;
	private Proveedor proveedorSeleccionado;
	private Compra compraSeleccionado;

	public CompraLocalPanel(CompraItemTableModel itemTableModel, ConsultaProveedor proveedorDialog,
			CompraDialog compraDialog, ProductoDialog productoDialog,
			CompraService compraService, ProveedorService proveedorService, MonedaService monedaService,
			DepositoService depositoService, CompraValidator compraValidator, ProductoService productoService,
			CondicionPagoDialog condicionPagoDialog, CondicionPagoService condicionPagoService,
			ConfiguracionService configService, AperturaCierreCajaService movCajaService, CajaService cajaService,
			MovimientoCajaService pagoService, MovimientoIngresoService movimientoIngresoService,
			MovimientoItemIngresoService movimientoItemIngresoService, MovimientoEgresoService movimientoEgresoService,
			MovimientoItemEgresoService movimientoItemEgresoService,
			ProcesoPagoComprasService procesoPagoComprasService,
			ProcesoPagoProveedoresService procesoPagoProveedoresService, CuentaAPagarService cuentaAPagarService,
			ItemCuentaAPagarService itemCuentaAPagarService) {
		this.itemTableModel = itemTableModel;
		this.proveedorDialog = proveedorDialog;
		this.productoDialog = productoDialog;
		this.compraService = compraService;
		this.compraDialog=compraDialog;
		this.proveedorService = proveedorService;
		this.productoService = productoService;
		this.compraValidator = compraValidator;
		this.condicionPagoDialog = condicionPagoDialog;
		this.condicionPagoService = condicionPagoService;
		this.configService = configService;
		this.movCajaService = movCajaService;
		this.cajaService = cajaService;
		this.pagoService = pagoService;
		this.movimientoIngresoService = movimientoIngresoService;
		this.movimientoItemIngresoService = movimientoItemIngresoService;
		this.movimientoEgresoService = movimientoEgresoService;
		this.movimientoItemEgresoService = movimientoItemEgresoService;
		this.procesoPagoComprasService = procesoPagoComprasService;
		this.cuentaAPagarService = cuentaAPagarService;
		this.itemCuentaAPagarService = itemCuentaAPagarService;

		setSize(920, 650);
		setTitle("REGISTRO DE COMPRAS");

		initComponents();
		Util.setupScreen(this);
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		setDefaultCloseOperation(0);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 122, 896, 322);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("Productos", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblCodigo.text")); //$NON-NLS-1$
		lblCodigo.setBounds(6, 6, 73, 18);
		pnlProducto.add(lblCodigo);

		JLabel lblDescripcion = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblDescripcion.text")); //$NON-NLS-1$
		lblDescripcion.setBounds(176, 6, 242, 18);
		pnlProducto.add(lblDescripcion);

		JLabel lblSubtotal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblSubtotal.text")); //$NON-NLS-1$
		lblSubtotal.setBounds(629, 6, 138, 18);
		pnlProducto.add(lblSubtotal);

		JLabel lblPrecio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblPrecio.text")); //$NON-NLS-1$
		lblPrecio.setBounds(504, 6, 115, 18);
		pnlProducto.add(lblPrecio);

		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(153, 30, 341, 30);
		pnlProducto.add(tfDescripcion);

		tfPrecioTotal = new JTextField();
		tfPrecioTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrecioTotal.setEditable(false);
		tfPrecioTotal.setFont(new Font("Arial", Font.PLAIN, 14));
		tfPrecioTotal.setColumns(10);
		tfPrecioTotal.setBounds(629, 30, 138, 30);
		pnlProducto.add(tfPrecioTotal);

		tfPrecio = new JTextField();
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
						Notifications.showAlert("Digite un numero valido.!");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfCantidad.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnAdd.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
		tfPrecio.setColumns(10);
		tfPrecio.setBounds(504, 30, 115, 30);
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
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProductoById(Long.valueOf(tfProductoID.getText()));
					} else {
						showDialog(PRODUCTO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfCondicion.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_F12) {
					abandonarNota();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfCantidad.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfProductoID.setFont(new Font("Arial", Font.BOLD, 14));
		tfProductoID.setBounds(6, 30, 73, 30);
		pnlProducto.add(tfProductoID);
		tfProductoID.setColumns(10);

		btnRemove = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.btnRemove.text")); //$NON-NLS-1$
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 18));
		btnRemove.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					removeItem();
				}
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItem();
			}
		});
		btnRemove.setBounds(826, 30, 51, 30);
		pnlProducto.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(6, 63, 871, 220);
		pnlProducto.add(scrollProducto);

		tbProductos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		Util.ocultarColumna(tbProductos, 5);
		Util.ocultarColumna(tbProductos, 6);
		Util.ocultarColumna(tbProductos, 7);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getSelectedItem();
			}
		});
		tbProductos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getSelectedItem();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});
		scrollProducto.setViewportView(tbProductos);

		tfCantidad = new JTextField();
		tfCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCantidad.setText("");
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
						tfPrecio.requestFocus();
					} else {
						Notifications.showAlert("Debes digitar cantidad.!");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfPrecio.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCantidad.setFont(new Font("Arial", Font.BOLD, 14));
		tfCantidad.setColumns(10);
		tfCantidad.setBounds(80, 30, 73, 30);
		pnlProducto.add(tfCantidad);

		JLabel lblCantidad = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblCantidad.text")); //$NON-NLS-1$
		lblCantidad.setBounds(87, 6, 77, 18);
		pnlProducto.add(lblCantidad);

		btnAdd = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.btnAdd.text")); //$NON-NLS-1$
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 18));
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (isValidItem()) {
						addItem();
					}
				}
			}
		});
		btnAdd.setBounds(767, 30, 57, 30);
		pnlProducto.add(btnAdd);

		label_7 = new JLabel("*");
		label_7.setToolTipText("Campos obligatorios");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setForeground(Color.RED);
		label_7.setFont(new Font("Dialog", Font.BOLD, 20));
		label_7.setBounds(67, 6, 14, 18);
		pnlProducto.add(label_7);

		JPanel pnlCabezera = new JPanel();
		pnlCabezera.setBounds(12, 12, 896, 105);
		pnlCabezera.setBorder(
				new TitledBorder(null, "COMPRA LOCALES", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCabezera.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 876, 78);
		pnlCabezera.add(pnlCliente);
		pnlCliente.setLayout(null);

		JLabel lblProveedor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblProveedor.text")); //$NON-NLS-1$
		lblProveedor.setBounds(214, 4, 61, 30);
		pnlCliente.add(lblProveedor);

		tfProveedorID = new JTextField();
		tfProveedorID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfProveedorID.selectAll();
			}
		});
		tfProveedorID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProveedorID.getText().isEmpty()) {
						findProveedorById(tfProveedorID.getText(), false);
						tfFactura.requestFocus();
					} else {
						showDialog(PROVEEDOR_CODE);
					}
					
				} else if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PROVEEDOR_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfProveedorID.setText("");

		tfProveedorID.setBounds(292, 4, 51, 30);
		pnlCliente.add(tfProveedorID);
		tfProveedorID.setColumns(10);

		tfNombre = new JTextField();
		tfNombre.setEditable(false);
		tfNombre.setBounds(344, 4, 209, 30);
		pnlCliente.add(tfNombre);
		tfNombre.setColumns(10);

		tfRuc = new JTextField();
		tfRuc.setEditable(false);
		tfRuc.setColumns(10);
		tfRuc.setBounds(784, 4, 86, 30);
		pnlCliente.add(tfRuc);

		tfDireccion = new JTextField();
		tfDireccion.setEditable(false);
		tfDireccion.setColumns(10);
		tfDireccion.setBounds(556, 4, 224, 30);
		pnlCliente.add(tfDireccion);

		tfFactura = new JTextField();
		tfFactura.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfFactura.selectAll();
			}
		});
		tfFactura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProveedorID.getText().isEmpty() && !tfFactura.getText().isEmpty()) {
						if (validateFactura(tfProveedorID.getText(), tfFactura.getText())) {
							// Notifications.showAlert("Proveedor y Nro. de Factura ya han sido cargados.");
							if (conf != null) {
								tfProductoID.requestFocus();
							}
						}
					} else {
						Notifications.showAlert("Debes ingresar Proveedor y/o Factura Nro.");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});

		tfFactura.setColumns(10);
		tfFactura.setBounds(292, 38, 76, 30);
		pnlCliente.add(tfFactura);

		JLabel lblFactura = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblFactura.text")); //$NON-NLS-1$
		lblFactura.setBounds(214, 38, 64, 30);
		pnlCliente.add(lblFactura);

		tfFechaCompra = new JFormattedTextField(getFormatoFecha());
		tfFechaCompra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfFechaCompra.selectAll();
			}
		});
		tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
		tfFechaCompra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfFechaCompra.getText().isEmpty()) {
						tfFactura.requestFocus();
					} else {
						getFecha();
						Notifications.showAlert("Debes digital la fecha");
						tfFactura.requestFocus();
					}
				}
			}
		});

		tfFechaCompra.setColumns(8);
		tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
		tfFechaCompra.setBounds(73, 38, 102, 30);
		pnlCliente.add(tfFechaCompra);

		JLabel lblFCompra = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblFCompra.text")); //$NON-NLS-1$

		lblFCompra.setBounds(6, 38, 68, 30);
		pnlCliente.add(lblFCompra);

		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(12, 554, 896, 41);

		btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.btnGuardar.text")); //$NON-NLS-1$
		btnGuardar.setMnemonic('G');
		//btnGuardar.setBounds(169, 5, 110, 34);
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					save();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		//pnlBotonera.setLayout(null);
		pnlBotonera.add(btnGuardar);
		getContentPane().setLayout(null);
		getContentPane().add(tabbedPane);
		getContentPane().add(pnlCabezera);
		getContentPane().add(pnlBotonera);
		
		btnAnular = new JButton("Anular"); //$NON-NLS-1$ //$NON-NLS-2$
		//btnAnular.setBounds(400, 5, 110, 34);
		btnAnular.setVisible(false);
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
		
		pnlBotonera.add(btnAnular);
		
		btnReimpresion = new JButton("Re Impresión"); //$NON-NLS-1$ //$NON-NLS-2$
		btnReimpresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReimpresion.setBounds(289, 5, 101, 34);
		pnlBotonera.add(btnReimpresion);
		
				btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
						.getString("CompraPanel.btnCancelar.text")); //$NON-NLS-1$
				btnCancelar.setMnemonic('C');
				//btnCancelar.setBounds(520, 5, 110, 34);
				btnCancelar.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							cancelar();
						}
					}
				});
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				pnlBotonera.add(btnCancelar);
				

				btnCerrar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
						.getString("CompraPanel.btnCerrar.text")); //$NON-NLS-1$
				btnCerrar.setMnemonic('E');
				//btnCerrar.setBounds(640, 5, 110, 34);
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCerrar.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							dispose();
						}
					}
				});
				pnlBotonera.add(btnCerrar);

		panel = new JPanel();
		//panel.setBounds(12, 468, 896, 85);
		getContentPane().add(panel);
		panel.setLayout(null);

//        btnBuscar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnBuscar.text")); //$NON-NLS-1$ //$NON-NLS-2$
//        btnBuscar.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		showDialog();
//        	}
//        });
//        btnBuscar.setBounds(316, 2, 150, 32);
//        pnlBuscador.add(btnBuscar);

		tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));

		lblBuscadorDeCompra = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblBuscadorDeCompra.text"));
		lblBuscadorDeCompra.setBounds(6, 4, 68, 30);
		pnlCliente.add(lblBuscadorDeCompra);

		tfCompraId = new JTextField();
		tfCompraId.setEditable(false);
		tfCompraId.setBounds(73, 3, 76, 32);
		pnlCliente.add(tfCompraId);
		tfCompraId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findCompraById(Long.valueOf(tfCompraId.getText()));
				} else if (e.getKeyCode() == KeyEvent.VK_F3) {
					// DIALOGO -> OP/DOCUMENTO/PROVEEDOR
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCompraId.setToolTipText((String) null);
		tfCompraId.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.tfCompraId.text")); //$NON-NLS-1$
		tfCompraId.setColumns(10);

		label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setToolTipText("Campos obligatorios");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		label.setBounds(276, 4, 14, 30);
		pnlCliente.add(label);
		
		JButton btnVer = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraLocalPanel.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnVer.setBounds(152, 8, 23, 23);
		pnlCliente.add(btnVer);
		btnVer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showDialog(COMPRA_CODE);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(COMPRA_CODE);
			}
		});

		label_5 = new JLabel("Campos Obligatorios");
		label_5.setFont(new Font("Dialog", Font.BOLD, 20));
		label_5.setBounds(32, 443, 299, 25);
		getContentPane().add(label_5);

		label_6 = new JLabel("*");
		label_6.setVerticalAlignment(SwingConstants.BOTTOM);
		label_6.setToolTipText("Campos obligatorios");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setForeground(Color.RED);
		label_6.setFont(new Font("Dialog", Font.BOLD, 20));
		label_6.setBounds(12, 443, 14, 25);
		getContentPane().add(label_6);
		
				tfCondicion = new JTextField();
				tfCondicion.setBounds(96, 474, 51, 30);
				getContentPane().add(tfCondicion);
				tfCondicion.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							if (!tfCondicion.getText().isEmpty()) {
								findCondicionPago(Integer.parseInt(tfCondicion.getText()));
								if (tfCondicion.getText().equalsIgnoreCase("100")) {
									tfCuotaCant.setText("0");
									tfCuotaCant.setEnabled(true);
									tfCuotaCant.requestFocus();
									tfCuotaCant.selectAll();
								}
							} else
								showDialog(CONDICION_PAGO_CODE);
						}
					}

					@Override
					public void keyTyped(KeyEvent e) {
						Util.validateNumero(e);
					}
				});
				tfCondicion.setFont(new Font("Dialog", Font.PLAIN, 14));
				
						JLabel lblCondicin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
								.getString("CompraPanel.lblCondicin.text"));
						lblCondicin.setBounds(12, 474, 74, 30);
						getContentPane().add(lblCondicin);
						
								tfCuotaCant = new JTextField();
								tfCuotaCant.setBounds(350, 508, 101, 30);
								getContentPane().add(tfCuotaCant);
								tfCuotaCant.setHorizontalAlignment(SwingConstants.RIGHT);
								// tfCuotaCant.setEditable(false);
								tfCuotaCant.setFont(new Font("Dialog", Font.BOLD, 14));
								tfCuotaCant.setColumns(10);
								
										JLabel lblCantItem = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
												.getString("CompraPanel.lblCantItem.text"));
										lblCantItem.setBounds(10, 508, 74, 30);
										getContentPane().add(lblCantItem);
										
												tfCantItem = new JTextField();
												tfCantItem.setBounds(96, 508, 51, 30);
												getContentPane().add(tfCantItem);
												tfCantItem.setFont(new Font("Dialog", Font.PLAIN, 14));
												tfCantItem.setEditable(false);
												tfCantItem.setColumns(10);
																				
																						JLabel lblSubTotal = new JLabel("Total : ");
																						lblSubTotal.setBounds(463, 508, 74, 30);
																						getContentPane().add(lblSubTotal);
																						
																								JLabel lblObs = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
																										.getString("CompraPanel.lblObs.text"));
																								lblObs.setBounds(677, 508, 74, 30);
																								getContentPane().add(lblObs);
																								
																										tfTotalGeneral = new JTextField();
																										tfTotalGeneral.setBounds(539, 508, 129, 30);
																										getContentPane().add(tfTotalGeneral);
																										tfTotalGeneral.setFont(new Font("Dialog", Font.BOLD, 20));
																										tfTotalGeneral.setHorizontalAlignment(SwingConstants.RIGHT);
																										tfTotalGeneral.setForeground(Color.RED);
																										tfTotalGeneral.setEditable(false);
																										tfTotalGeneral.setColumns(10);
																										
																												JLabel lblTotal = new JLabel("Cuota");
																												lblTotal.setBounds(295, 508, 51, 30);
																												getContentPane().add(lblTotal);
																												
																														tfObs = new JTextField();
																														tfObs.setBounds(712, 508, 182, 30);
																														getContentPane().add(tfObs);
																														tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
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
																														tfObs.setColumns(10);
																														
																																label_4 = new JLabel("*");
																																label_4.setBounds(80, 508, 14, 30);
																																getContentPane().add(label_4);
																																label_4.setVerticalAlignment(SwingConstants.BOTTOM);
																																label_4.setToolTipText("Campos obligatorios");
																																label_4.setHorizontalAlignment(SwingConstants.CENTER);
																																label_4.setForeground(Color.RED);
																																label_4.setFont(new Font("Dialog", Font.BOLD, 20));
																																
																																		lblVence = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
																																				.getString("CompraLocalPanel.lblVence.text"));
																																		lblVence.setBounds(148, 507, 51, 30);
																																		getContentPane().add(lblVence);
																																		
																																				tfVence = new JTextField();
																																				tfVence.setBounds(203, 508, 84, 30);
																																				getContentPane().add(tfVence);
																																				tfVence.setFont(new Font("Dialog", Font.PLAIN, 14));
																																				tfVence.setEditable(false);
																																				tfVence.setColumns(10);
								tfCuotaCant.addKeyListener(new KeyAdapter() {
									@Override
									public void keyPressed(KeyEvent e) {
										if (e.getKeyCode() == KeyEvent.VK_ENTER) {
											btnGuardar.requestFocus();
										}
									}
								});
	}

	private MaskFormatter formatoFecha;
	private JLabel label_7;

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

	private void getSelectedItem() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			CompraDetalle item = itemTableModel.getEntityByRow(selectedRow);

			tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfPrecio.setText(FormatearValor.doubleAString(item.getPrecio()));
			tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
		}
	}

	private void abandonarNota() {
		tfProveedorID.requestFocus();
	}

	private Compra getCompra() {
		Compra compra = new Compra();
		compra.setComprobante("SIN COMPROBANTE");
		compra.setFecha(new Date());
		compra.setTipoCompra("LOCAL");
		compra.setFactura(tfFactura.getText());
		compra.setFechaCompra(Fechas.stringToDate(tfFechaCompra.getText()));
		compra.setVencimiento(
				Fechas.sumarFecha(Integer.parseInt(tfCondicion.getText()), 0, 0, tfFechaCompra.getText()));
		compra.setCondicion(tfCondicion.getText().isEmpty() ? 0 : Integer.parseInt(tfCondicion.getText()));
		compra.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID));
		compra.setCaja(new Caja(1L));
		compra.setProveedor(new Proveedor(Long.valueOf(tfProveedorID.getText())));
		compra.setProveedorNombre(tfNombre.getText());
		compra.setDeposito(new Deposito(Long.valueOf("1")));
		compra.setUsuario(new Usuario(GlobalVars.USER_ID));
		compra.setTotalItem(tfCantItem.getText().isEmpty() ? 1 : FormatearValor.stringToDouble(tfCantItem.getText()));
		if (conf != null && conf.getHabilitaLanzamientoCaja() == 1)
			compra.setSituacion("PENDIENTE");
		else {
			if (!tfCondicion.getText().isEmpty() && tfCondicion.getText().equalsIgnoreCase("0")) {
				compra.setSituacion("PAGADO");
				compra.setTotalPagado(tfTotalGeneral.getText().isEmpty() ? 0
						: FormatearValor.stringToDouble(tfTotalGeneral.getText()));
			} else
				compra.setSituacion("PROCESADO");
		}
		compra.setObs(tfObs.getText());
//		compra.setGastos(tfFlete.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfFlete.getText()));
//		compra.setDescuento(tfDescuento.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfDescuento.getText()));
		compra.setTotalFob(
				tfTotalGeneral.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalGeneral.getText()));
		compra.setCuotaCant(tfCuotaCant.getText().isEmpty() ? 0 : Integer.valueOf(tfCuotaCant.getText()));
		compra.setTotalGeneral(
				tfTotalGeneral.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalGeneral.getText()));

//		List<CompraDetalle> detalles = new ArrayList<>();
//		
//		if (!tfFlete.getText().isEmpty() || !tfDescuento.getText().isEmpty()) {
//			//update precio fob
//			Double desc = tfDescuento.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfDescuento.getText());
//			Double gastos = tfFlete.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfFlete.getText());
//			Double total = tfTotalGeneral.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalGeneral.getText());
//			
//			Double costoCif = ((total + gastos) - (-desc)) / total;
//			
//			for (CompraDetalle item : itemTableModel.getEntities()) {
//				item.setPrecioFob(item.getPrecio());
//				item.setPrecio(item.getPrecio() + costoCif);
//				
//				detalles.add(item);
//			}
//			
//			compra.setItems(detalles);
//		} else {
		compra.setItems(itemTableModel.getEntities());
//		}

		return compra;
	}

	private void updateStockProduct(List<CompraDetalle> items, Deposito deposito) {
		List<Producto> productos = new ArrayList<>();
		int habilitaLanzamientoCaja = 0;
		if (conf != null && conf.getHabilitaLanzamientoCaja() != 0)
			habilitaLanzamientoCaja = conf.getHabilitaLanzamientoCaja();

		for (CompraDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				Double cantCompra = e.getCantidad() != null ? e.getCantidad() : 0;
				Double entPendiente = p.getEntPendiente() != null ? p.getEntPendiente() : 0;
				Double costoAnt = p.getPrecioCostoPromedio() != null ? p.getPrecioCostoPromedio() : 0;
				Double calcPromedioPrecio = (costoAnt == 0 ? e.getPrecio()
						: (costoAnt.intValue() + e.getPrecio().intValue()) / 2);
				p.setPrecioCosto(e.getPrecio());
				p.setPrecioCostoPromedio(calcPromedioPrecio);
				if (habilitaLanzamientoCaja == 1)
					p.setEntPendiente(entPendiente + cantCompra);
				else {
					Double cantDep = 0d;
					switch (GlobalVars.DEPOSITO_ID.intValue()) {
					case 1:
						cantDep = p.getDepO1();
						p.setDepO1(cantDep + cantCompra);
						break;
					case 2:
						cantDep = p.getDepO2();
						p.setDepO2(cantDep + cantCompra);
						break;
					case 3:
						cantDep = p.getDepO3();
						p.setDepO3(cantDep + cantCompra);
						break;
					case 4:
						cantDep = p.getDepO4();
						p.setDepO4(cantDep + cantCompra);
						break;
					case 5:
						cantDep = p.getDepO5();
						p.setDepO5(cantDep + cantCompra);
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

	private boolean validateFactura(String proveedorId, String factura) {
		boolean result = false;

		Optional<Compra> compra = compraService.findByProveedorAndFacturaNro(new Proveedor(Long.valueOf(proveedorId)),
				factura);

		if (compra.isPresent()) {
			result = true;
		}

		return result;
	}

	private void findCondicionPago(int cantDia) {
		Optional<CondicionPago> condicion = condicionPagoService.findByCantDia(cantDia);

		if (condicion.isPresent()) {
			tfCondicion.setText(String.valueOf(cantDia));
			tfVence.setText(Fechas.formatoDDMMAAAA(
					Fechas.sumarFecha(Integer.parseInt(tfCondicion.getText()), 0, 0, tfFechaCompra.getText())));
		} else {
			showDialog(CONDICION_PAGO_CODE);
		}
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "CONFIRMA", "AVISO ", JOptionPane.OK_CANCEL_OPTION);
		if (respuesta == 0) {
			if (validateCabezera()) {
				Compra compra = getCompra();

				Optional<ValidationError> errors = compraValidator.validate(compra);

				if (errors.isPresent()) {
					ValidationError validationError = errors.get();
					Notifications.showFormValidationAlert(validationError.getMessage());
				} else {
					Compra c = compraService.save(compra);

					if (c != null) {
						updateStockProduct(c.getItems(), c.getDeposito());
						if (conf != null && conf.getHabilitaLanzamientoCaja() == 0) {
							lanzamientoCaja(c);
							openMovCaja(c);
							openMovimientoEgresoProcesoPagoCompras(c);
							if (!tfCondicion.getText().equalsIgnoreCase("0")) {
								CuentaAPagar cuentaAPagar = new CuentaAPagar();
								cuentaAPagar = cuentaAPagarProcesoPagoCompras(c);
								movimientoIngresoProcesoPagoCompras(c, cuentaAPagar);
							}
						}
					}

					Notifications.showAlert("Compra registrado correctamente.!");
					clearForm();
					newCompra();
				}
			}
		} else {
			tfProductoID.requestFocus();
		}
	}

	private void lanzamientoCaja(Compra c) {
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

	private void openMovCaja(Compra compra) {
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
		if (tfCondicion.getText().equalsIgnoreCase("0")) {
			movCaja.setObs("Pagado al proveedor ");
			movCaja.setSituacion("PAGADO");
		} else if (tfCondicion.getText().equalsIgnoreCase("100") && !tfCuotaCant.getText().isEmpty()) {
			cant = Integer.valueOf(tfCuotaCant.getText());
			movCaja.setObs("Credito a cuotas :" + cant);
			movCaja.setSituacion("PROCESADO");
		} else {
			cant = 1;
			movCaja.setObs("Credito a " + tfCondicion.getText() + " días");
			movCaja.setSituacion("PROCESADO");
		}
		pagoService.save(movCaja);
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
		m = movimientoIngresoService.save(m);

		MovimientoItemIngreso mii = new MovimientoItemIngreso();
		mii.setMiiNumero(m.getMinNumero());
		mii.setMiiIngreso(30);
		mii.setMiiMonto(cuentaAPagar.getMonto());
		movimientoItemIngresoService.save(mii);

		ProcesoPagoCompras ppc = new ProcesoPagoCompras();
		ppc.setPcoCompra(compra.getId().intValue());
		ppc.setPcoIngresoEgreso(1);
		ppc.setPcoTipoproceso(31);
		ppc.setPcoProceso(m.getMinNumero());
		ppc.setPcoFlag(1);
		procesoPagoComprasService.save(ppc);
	}

	private CuentaAPagar cuentaAPagarProcesoPagoCompras(Compra compra) {
		CuentaAPagar cuentaAPagar = new CuentaAPagar();

		cuentaAPagar.setFecha(new Date());
		cuentaAPagar.setHora(new Date());
		cuentaAPagar.setNroBoleta(compra.getComprobante());
		cuentaAPagar.setIdEntidad(compra.getProveedor().getId());
		cuentaAPagar.setTipoEntidad(3);
		cuentaAPagar.setMonto(compra.getTotalGeneral());
		cuentaAPagar.setCapProceso(compra.getId().intValue());
		cuentaAPagar.setCapSituacion(0);

		cuentaAPagar = cuentaAPagarService.save(cuentaAPagar);

		List<ItemCuentaAPagar> listaItemCuentaAPagar = new ArrayList<ItemCuentaAPagar>();

		int cant = 0;
		int cantDias = 0;
		Date fechaVencimiento = new Date();
		Calendar cal = Calendar.getInstance();
		if (tfCondicion.getText().equalsIgnoreCase("100")) {
			cant = Integer.valueOf(tfCuotaCant.getText());
			cantDias = 30;
		} else {
			cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(tfCondicion.getText()));
			fechaVencimiento = cal.getTime();
			cantDias = Integer.valueOf(tfCondicion.getText());
			cant = 1;
		}
		Double valorTotal = compra.getTotalGeneral() / cant;
		for (int i = 0; i < cant; i++) {
			ItemCuentaAPagar itemCuentaAPagar = new ItemCuentaAPagar();
			cal.add(Calendar.MONTH, 1);
			itemCuentaAPagar.setIcpCuenta(cuentaAPagar.getCapNumero());
			itemCuentaAPagar.setIcpMonto(valorTotal);
			itemCuentaAPagar.setIcpSituacion(0);
			itemCuentaAPagar.setIcpDocumento(cant + "/" + (i + 1));
			if (!tfCondicion.getText().equalsIgnoreCase("100"))
				itemCuentaAPagar.setIcpVencimiento(fechaVencimiento);
			else
				itemCuentaAPagar.setIcpVencimiento(cal.getTime());
			itemCuentaAPagar.setIcpDias(cantDias);

			listaItemCuentaAPagar.add(itemCuentaAPagar);
		}
		itemCuentaAPagarService.save(listaItemCuentaAPagar);
		// Proceso pago compras
		ProcesoPagoCompras ppc = new ProcesoPagoCompras();
		ppc.setPcoCompra(compra.getId().intValue());
		ppc.setPcoIngresoEgreso(1);
		ppc.setPcoTipoproceso(30);
		ppc.setPcoProceso(cuentaAPagar.getCapNumero());
		ppc.setPcoFlag(1);
		procesoPagoComprasService.save(ppc);

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
		movEgreso = movimientoEgresoService.save(movEgreso);

		MovimientoItemEgreso movItemEgreso = new MovimientoItemEgreso();
		movItemEgreso.setMieNumero(movEgreso.getMegNumero());
		movItemEgreso.setMieEgreso(1);
		double monto = (double) Math.round(c.getTotalGeneral() / 11);
		movItemEgreso.setMieMonto(monto);
		movItemEgreso.setMieDescripcion("Egreso  - Compra Crédito IVA");
		movimientoItemEgresoService.save(movItemEgreso);

		MovimientoItemEgreso movIE = new MovimientoItemEgreso();
		movIE.setMieNumero(movEgreso.getMegNumero());
		movIE.setMieEgreso(4);
		movIE.setMieMonto(c.getTotalGeneral() - monto);
		movIE.setMieDescripcion("Egreso  - Compra Crédito IMPONIBLE");
		movimientoItemEgresoService.save(movIE);

		ProcesoPagoCompras ppc = new ProcesoPagoCompras();
		ppc.setPcoCompra(c.getId().intValue());
		ppc.setPcoIngresoEgreso(2);
		ppc.setPcoTipoproceso(32);
		ppc.setPcoProceso(movEgreso.getMegNumero());
		ppc.setPcoFlag(1);
		procesoPagoComprasService.save(ppc);
	}

	private void anular() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "CONFIRMAR", "AVISO - AGROPROGRESO",
				JOptionPane.OK_CANCEL_OPTION);
		if (respuesta == 0) {
			if (!compraSeleccionado.getSituacion().equalsIgnoreCase("ANULADO")) {
				updateStockProductRemoved(compraSeleccionado.getItems());
				compraSeleccionado.setSituacion("ANULADO");
				compraService.save(compraSeleccionado);

				if (conf != null && conf.getHabilitaLanzamientoCaja() == 0) {
					removeMovCaja(compraSeleccionado);
					//removeMovimientoIngresoProcesoCobroVenta(compraSeleccionado);
					removeMovimientoEgresoProcesoPagoCompras(compraSeleccionado);
					if (!tfCondicion.getText().equalsIgnoreCase("0")) {
						Integer cuentaAPagar=removeCuentaAPagarProcesoPagoCompras(compraSeleccionado);
						removeMovimientoIngresoProcesoPagoCompras(compraSeleccionado, cuentaAPagar);
					}
				}

				newCompra();
			}
		}
	}

	private void updateStockProductRemoved(List<CompraDetalle> items) {
		List<Producto> productos = new ArrayList<>();
		int habilitaLanzamientoCaja = 0;
		if (conf != null && conf.getHabilitaLanzamientoCaja() != 0)
			habilitaLanzamientoCaja = conf.getHabilitaLanzamientoCaja();
		for (CompraDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				int depesitoId = GlobalVars.DEPOSITO_ID.intValue();
				Double entPend = p.getEntPendiente() != null ? p.getEntPendiente() : 0;
				Double cantItem = e.getCantidad();
				if (habilitaLanzamientoCaja == 1) {
					p.setEntPendiente(entPend - cantItem);
				} else {
					switch (depesitoId) {
					case 1:
						Double dep01 = p.getDepO1() != null ? p.getDepO1() : 0;
						p.setDepO1(dep01 - cantItem);
						break;
					case 2:
						Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
						p.setDepO2(dep02 - cantItem);
						break;
					case 3:
						Double dep03 = p.getDepO3() != null ? p.getDepO3() : 0;
						p.setDepO3(dep03 - cantItem);
						break;
					case 4:
						Double dep04 = p.getDepO4() != null ? p.getDepO4() : 0;
						p.setDepO4(dep04 - cantItem);
						break;
					case 5:
						Double dep05 = p.getDepO5() != null ? p.getDepO5() : 0;
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

	private void removeMovCaja(Compra compra) {
		// cierre de caja del dia anterio
		Optional<MovimientoCaja> movimientoCaja = pagoService.findByNotaNroAndTipoOperacion(compra.getId().toString(),"S");
		if (movimientoCaja.isPresent()) {
			MovimientoCaja mc = movimientoCaja.get();
			mc.setObs("COMPRA ANULADO");
			mc.setSituacion("ANULADO");
			pagoService.save(mc);
		}
		// remover los otros movimientos insertados
	}
	
	private void removeMovimientoIngresoProcesoPagoCompras(Compra compra, Integer cuentaAPagar) {
		MovimientoIngreso m = movimientoIngresoService.findByMinProceso(cuentaAPagar);
		Integer cabId = m.getMinNumero();
		
		List<MovimientoItemIngreso> mii = movimientoItemIngresoService.findByCabId(cabId);
		for (MovimientoItemIngreso movimientoItemIngreso : mii) {
			movimientoItemIngresoService.remove(movimientoItemIngreso);
		}
		movimientoIngresoService.remove(m);
		
		ProcesoPagoCompras ppc = procesoPagoComprasService.findByPcoCompraAndPcoProceso(Integer.valueOf(compra.getId().toString()),cabId);
		procesoPagoComprasService.remove(ppc);
	}
	
	private void removeMovimientoEgresoProcesoPagoCompras(Compra compra) {
		MovimientoEgreso movEgreso = movimientoEgresoService.findByMegProceso(Integer.valueOf(compra.getId().toString()));
		Integer idEgreso= movEgreso.getMegNumero();
		List<MovimientoItemEgreso> movItemEgreso = movimientoItemEgresoService.findByCabId(movEgreso.getMegNumero());
		for (MovimientoItemEgreso movimientoItemEgreso : movItemEgreso) {
			movimientoItemEgresoService.remove(movimientoItemEgreso);
		}
		movimientoEgresoService.remove(movEgreso);
		
		ProcesoPagoCompras ppc = procesoPagoComprasService.findByPcoCompraAndPcoProceso(Integer.valueOf(compra.getId().toString()),idEgreso);
		procesoPagoComprasService.remove(ppc);
	}
	
	private Integer removeCuentaAPagarProcesoPagoCompras(Compra compra) {
		CuentaAPagar cuentaAPagar = cuentaAPagarService.findByCapProcesoAndIdEntidad(Integer.valueOf(compra.getId().toString()),compra.getProveedor().getId());
		Integer cabId=cuentaAPagar.getCapNumero();
		cuentaAPagarService.remove(cuentaAPagar);

		List<ItemCuentaAPagar> listaItemCuentaAPagar = itemCuentaAPagarService.findByCabId(cabId);
		for (ItemCuentaAPagar itemCuentaAPagar : listaItemCuentaAPagar) {
			itemCuentaAPagarService.remove(itemCuentaAPagar);
		}

		// Proceso Pago Compras
		ProcesoPagoCompras pcv = procesoPagoComprasService.findByPcoCompraAndPcoProceso(Integer.valueOf(compra.getId().toString()), cabId);
		procesoPagoComprasService.remove(pcv);
				
		return cabId;
	}
	
	private Configuracion conf;
	private JLabel lblVence;
	private JTextField tfVence;

	public void getConfig() {
		Optional<Configuracion> config = configService.findByEmpresaId(new Empresa(GlobalVars.EMPRESA_ID));

		if (config.isPresent()) {
			this.conf = config.get();

//			if (conf.getPideFleteCompraLocal() == 0)
//				tfFlete.setEnabled(false);
//
//			if (conf.getPideGastoItemCompra() == 0)
//				tfGasto.setEnabled(false);

//			if (conf.getDefineDepositoCompra() != 0) {
//				tfDepositoID.setEnabled(true);
//				tfDeposito.setEnabled(true);
//			}
		}
	}

	private boolean validateCabezera() {
		// validar cliente, deposito, vendedor, cond. de pago
		if (tfProveedorID.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Proveedor es obligatorio");
			tfProveedorID.requestFocus();
			return false;
		}
//		else if (tfDepositoID.getText().isEmpty() && (conf != null && conf.getPideDeposito() == 1)) { // si esta vacio
//			Notifications.showAlert("El codigo del Deposito es obligatorio");
//			tfDepositoID.requestFocus();
//			return false;
//		} else if (tfDepositoID.getText().isEmpty() && (conf != null && conf.getPideDepositoCompra() == 1)) {
//			Notifications.showAlert("El codigo del Deposito es obligatorio");
//			tfDepositoID.requestFocus();
//			return false;
//		} else if (tfMonedaID.getText().isEmpty() && (conf != null && conf.getPideMoneda() == 1)) {
//			Notifications.showAlert("El codigo de Moneda es obligatorio");
//			tfMonedaID.requestFocus();
//			return false;
//		} 
		else if (tfCondicion.getText().equalsIgnoreCase("100")) {
			if (tfCuotaCant.getText().isEmpty() || Integer.valueOf(tfCuotaCant.getText()) <= 0) {
				Notifications.showAlert("La cantidad de cuota debe ser mayor a 0(cero) !");
				return false;
			} else {
				cant = Integer.valueOf(tfCuotaCant.getText());
			}

		}

		Optional<Proveedor> proveedor = proveedorService.findById(Long.valueOf(tfProveedorID.getText()));

		if (!proveedor.isPresent()) {
			Notifications.showAlert("No codigo del Proveedor no existe.!");
			tfProveedorID.requestFocus();
			return false;
		}

		if (!tfCondicion.getText().isEmpty() && tfCondicion.getText().equalsIgnoreCase("100")
				&& tfCuotaCant.getText().equalsIgnoreCase("0")) {
			Notifications.showAlert("La cantidad de cuota debe ser mayor a 0 (cero)");
			tfCuotaCant.requestFocus();
			tfCuotaCant.selectAll();
			return false;
		}

		return true;
	}

	private CompraDetalle getItem() {
		CompraDetalle item = new CompraDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(FormatearValor.stringToDouble(tfCantidad.getText()));
		item.setPrecio(FormatearValor.stringToDouble(tfPrecio.getText()));
		item.setSubtotal(FormatearValor.stringToDouble(tfPrecioTotal.getText()));
		item.setFecha(new Date());
		item.setPrecioFob(FormatearValor.stringToDouble(tfPrecio.getText()));

		return item;
	}

	private void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		//tfGasto.setText("");

		tfProductoID.requestFocus();
	}

	public void setTotals(Double cantItem, Double total) {
//		Double descuento = tfDescuento.getText().isEmpty() ? 0d : Double.valueOf(tfDescuento.getText());
//		Double flete = tfFlete.getText().isEmpty() ? 0d : Double.valueOf(tfFlete.getText());
		Double totalGeneral = total; //(total + flete) - descuento;
		// Double precioFob = total - descuento;

		tfTotalGeneral.setText(FormatearValor.doubleAString(totalGeneral));

		if (cantItem != 0)
			tfCantItem.setText(FormatearValor.doubleAString(cantItem));
	}

	public void clearForm() {
		tfFactura.setText("");
		tfFechaCompra.setText("");
		tfProveedorID.setText("");
		tfNombre.setText("");
		tfRuc.setText("");
		tfDireccion.setText("");
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfObs.setText("");
		tfCuotaCant.setText("");
		tfTotalGeneral.setText("");
		tfCantItem.setText("");
//		tfDescuento.setText("");
//		tfFlete.setText("");
		tfCondicion.setText("0");
		tfVence.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		getFecha();

		tfProveedorID.requestFocus();
	}

	private void getFecha() {
		tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
	}

	private void removeItem() {
		int row[] = tbProductos.getSelectedRows();

		if (tbProductos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				itemTableModel.removeRow(row[i - 1]);
			}

			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}

		clearItem();

		calculateItem();
	}

	private void calculatePrecioTotal() {
		Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
		Double precioUnit = FormatearValor.stringToDouble(tfPrecio.getText());
		//Double gastoUnit = FormatearValor.stringToDouble(!tfGasto.getText().isEmpty() ? tfGasto.getText() : "0");
		Double precioTotal = (cantidad * precioUnit);// + gastoUnit;

		tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));

		btnAdd.requestFocus();
		
	}

	private void cancelar() {
		clearForm();
		newCompra();
	}

	private void showDialog(int code) {
		switch (code) {
		case PROVEEDOR_CODE:
			proveedorDialog.setInterfaz(this);
			proveedorDialog.setVisible(true);
			break;
		case MONEDA_CODE:
			//monedaDialog.setInterfaz(this);
			//monedaDialog.setVisible(true);
			break;
		case DEPOSITO_CODE:
			//depositoDialog.setInterfaz(this);
			//depositoDialog.setVisible(true);
			break;
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.setVisible(true);
			break;
		case CONDICION_PAGO_CODE:
			condicionPagoDialog.setInterfaz(this);
			condicionPagoDialog.setVisible(true);
			break;
		case COMPRA_CODE:
			compraDialog.setInterfaz(this);
			compraDialog.loadCompras();
			compraDialog.setVisible(true);
			break;	
		default:
			break;
		}
	}

	private void findProveedorById(String proveedorId, boolean isImportacion) {
		if (proveedorId.isEmpty()) {
			Notifications.showAlert("Debes ingresar Proveedor.!");
			return;
		}

		Optional<Proveedor> proveedor = proveedorService.findById(Long.valueOf(proveedorId));

		if (proveedor.isPresent()) {
			if (!isImportacion) {
				tfProveedorID.setText(String.valueOf(proveedor.get().getId()));
				tfNombre.setText(proveedor.get().getRazonSocial());
				tfRuc.setText(proveedor.get().getRuc());
				tfDireccion.setText(proveedor.get().getDireccion());
				tfFechaCompra.requestFocus();
			}
//			else {
//				tfProveedorImport.setText(proveedor.get().getRazonSocial());
//				tfValorImport.requestFocus();
//			}

		} else {
			Notifications.showAlert("No existe Proveedor con ese codigo.!");
			tfProveedorID.requestFocus();
		}
	}

	
	private void findProductoById(Long id) {
		Optional<Producto> producto = productoService.findById(id);

		if (producto.isPresent()) {
			if (producto.get().getSubgrupo().getTipo().equals("S")) {
				Notifications.showAlert("Este Producto es un servicio, no se puede comprar.!");
				clearItem();
			} else {
				setProducto(producto.get());
			}
		} else {
			Notifications.showAlert("No existe Producto con ese codigo.!");
			tfProductoID.requestFocus();
		}
	}

	private void addItem() {
		Boolean esDuplicado = false;
		Integer fila = -1;

		for (Integer i = 0; i < tbProductos.getRowCount(); i++) {
			String itemId = String.valueOf(tbProductos.getValueAt(i, 0));

			if (tfProductoID.getText().trim().equals(itemId) && !itemId.equals("")) {
				esDuplicado = true;
				fila = i;
			}
		}

		if (esDuplicado) {
			Integer respuesta = JOptionPane.showConfirmDialog(null,
					"Registro ya existe en la grilla, desea actualizar los datos?");
			if (respuesta == 0) {
				if (isValidItem()) {
					actualizarRegristroGrilla(fila, String.valueOf(tbProductos.getValueAt(fila, 1)));
				}
			}
		} else {
			itemTableModel.addEntity(getItem());
		}

		calculateItem();
		clearItem();
	}

	private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
		itemTableModel.removeRow(fila);

		Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());
		Double cantidadTotal = Double.valueOf(cantAnteriorItem) + cantidad;
		Double precio = FormatearValor.stringADouble(tfPrecio.getText());
		tfCantidad.setText(FormatearValor.doubleAString(cantidadTotal));
		tfPrecioTotal.setText(FormatearValor.doubleAString(cantidadTotal * precio));

		itemTableModel.addEntity(getItem());
	}

	private Boolean isValidItem() {
		if (tfProductoID.getText().isEmpty()) {
			Notifications.showAlert("Codigo es un campo obligatorio.!");
			tfProductoID.requestFocus();
			return false;
		} else if (tfCantidad.getText().isEmpty() || FormatearValor.stringADouble(tfCantidad.getText()) <= 0) {
			Notifications.showAlert("La cantidad es un campo obligatorio.!");
			tfCantidad.requestFocus();
			return false;
		} else if (tfPrecio.getText().isEmpty() || FormatearValor.stringADouble(tfPrecio.getText()) <= 0) {
			Notifications.showAlert("El precio es un campo obligatorio.!");
			tfPrecio.requestFocus();
			return false;
		}

		return true;
	}

//	private Boolean isValidItemImport() {
//		if (tfProveedorImport.getText().isEmpty()) {
//			Notifications.showAlert("Codigo es un campo obligatorio.!");
//			tfProductoID.requestFocus();
//			return false;
//		} else if (tfValorImport.getText().isEmpty()) {
//			Notifications.showAlert("Valor es un campo obligatorio");
//			tfCantidad.requestFocus();
//			return false;
//		}
//
//		return true;
//	}

	private void calculateItem() {
		Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
		Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotal()).sum();
		setTotals(cantItem, total);
	}

	

	@Override
	public void getEntity(Producto producto) {
		setProducto(producto);
	}

	

	@Override
	public void getEntity(Proveedor p) {
		if (p != null) {
			tfProveedorID.setText(String.valueOf(p.getId()));
			tfRuc.setText(p.getRuc());
			tfDireccion.setText(p.getDireccion());
		}
	}

	

	@Override
	public void goToPedidoCompra() {
		tfFactura.setEditable(false);
		tfFechaCompra.setEditable(false);
	}

	@Override
	public void getEntity(Compra c) {
		if (c != null) {
			setCompraSeleccionado(c);
			btnAnular.setVisible(true);
			btnReimpresion.setVisible(true);
			btnGuardar.setVisible(false);
			loadCompra(c);
		}
	}

	private void setProducto(Producto producto) {
		if (producto.getEsServicio() == 1) {
			tfProductoID.setText("");
			tfDescripcion.setText("");
			tfCantidad.setText("");
			tfPrecio.setText("");
			tfProductoID.requestFocus();
			Notifications.showAlert("Este Producto es de tipo Servicio. No se puede comprar");
		} else {
			Double costo = producto.getPrecioCosto() != null ? producto.getPrecioCosto() : 0;
			tfProductoID.setText(producto.getId().toString());
			tfDescripcion.setText(producto.getDescripcion());
			tfPrecio.setText(FormatearValor.doubleAString(costo));
			tfCantidad.setText("1");
			tfCantidad.requestFocus();
		}
	}

	private void findCompraById(Long id) {
		Optional<Compra> compra = compraService.findById(id);

		if (compra.isPresent()) {
			loadCompra(compra.get());
		} else {
			tfProveedorID.requestFocus();
		}

	}

	private void loadCompra(Compra c) {
		tfProveedorID.setText(String.valueOf(c.getProveedor().getId()));
		tfFactura.setText(c.getFactura());
		tfFechaCompra.setText(c.getFechaCompra() == null ? "" : String.valueOf(c.getFechaCompra()));
		tfCondicion.setText(String.valueOf(c.getCondicion()));
		tfCantItem.setText(FormatearValor.doubleAString(c.getTotalItem()));
		tfObs.setText(c.getObs());
		//tfFlete.setText(FormatearValor.doubleAString(c.getGastos()));
		//tfDescuento.setText(FormatearValor.doubleAString(c.getDescuento()));
		tfTotalGeneral.setText(FormatearValor.doubleAString(c.getTotalFob()));
		tfCuotaCant.setText(c.getCuotaCant().toString());

		findProveedorById(c.getProveedor().getId() != null ? String.valueOf(c.getProveedor().getId()) : "", false);
		itemTableModel.addEntities(c.getItems());
	}

	public void newCompra() {
		long max = compraService.getRowCount();
		long newId = max + 1;
		resetProveedor();
		resetCompra();
		tfCompraId.setText(String.valueOf(newId));
		tfCuotaCant.setEnabled(false);
		btnAnular.setVisible(false);
		btnReimpresion.setVisible(false);
		tfProveedorID.requestFocus();
		
//		if (conf.getDefineDepositoCompra() != 0) {
//			findDepositoById(String.valueOf(conf.getDefineDepositoCompra()));
//		}
	}
	
	private void resetProveedor() {
		tfProveedorID.setText("");
		tfNombre.setText("");
		tfRuc.setText("");
		tfDireccion.setText("");
	}

	private void resetCompra() {
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfObs.setText("");
		tfTotalGeneral.setText("0");
		tfCantItem.setText("0");
		//tfDescuento.setText("0");
		tfCuotaCant.setText("0");
		tfVence.setText("");
		tfCondicion.setText("0");
		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}
	}
	
	@Override
	public void getEntity(CondicionPago condicionPago) {
		if (condicionPago != null) {
			tfCondicion.setText(String.valueOf(condicionPago.getCantDia()));
			//tfFlete.requestFocus();
		}
	}

	public Proveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
	}

	public Compra getCompraSeleccionado() {
		return compraSeleccionado;
	}

	public void setCompraSeleccionado(Compra compraSeleccionado) {
		this.compraSeleccionado = compraSeleccionado;
	}

	@Override
	public void goToCompraLocal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goToCompraImportacion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goToCompraConsiganada() {
		// TODO Auto-generated method stub
		
	}
}