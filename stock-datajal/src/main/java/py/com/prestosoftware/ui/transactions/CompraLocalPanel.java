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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javax.swing.Action;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.CondicionPagoService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.MonedaService;
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
public class CompraLocalPanel extends JFrame implements ProveedorInterfaz, DepositoInterfaz, MonedaInterfaz,
		ProductoInterfaz, PanelCompraInterfaz, CompraInterfaz, CondicionPagoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int PROVEEDOR_CODE = 1;
	private static final int MONEDA_CODE = 2;
	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4; 
	private static final int CONDICION_PAGO_CODE = 5; 

	private JTextField tfNombre, tfDescripcion, tfProductoID, tfProveedorID, tfPrecioTotal, tfCompraId;
	private JTextField tfPrecio, tfMonedaID, tfMoneda, tfCantidad, tfCantItem;
	private JTextField tfCondicion;
	private JTextField tfDescuento, tfObs, tfTotalFob, tfTotalCif, tfDepositoID, tfDeposito;
	private JTextField tfRuc, tfDireccion, tfFlete;
	private JTextField tfFactura;
	private JFormattedTextField tfFechaCompra;
	private JButton btnAdd, btnRemove;
	private JButton btnGuardar, btnCancelar, btnCerrar;
	private JPanel panel;
	private JTable tbProductos;
	private JLabel lblBuscadorDeCompra;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	protected Action addAction;
	
	private CompraService compraService;
	private CompraValidator compraValidator;
	private CompraItemTableModel itemTableModel;
	
	private ProveedorService proveedorService;
	private MonedaService monedaService;
	private DepositoService depositoService;
	private ProductoService productoService;
	
	private ConsultaProveedor proveedorDialog;
	private DepositoDialog depositoDialog;
	private CondicionPagoDialog condicionPagoDialog;
	private MonedaDialog monedaDialog;
	private ProductoDialog productoDialog;
	private CondicionPagoService condicionPagoService;
	private ConfiguracionService configService;
	private Deposito depositoDef;
	private Moneda monedaDef;
	
	public CompraLocalPanel(CompraItemTableModel itemTableModel, ConsultaProveedor proveedorDialog,
			DepositoDialog depositoDialog, MonedaDialog monedaDialog, ProductoDialog productoDialog,
			CompraService compraService, ProveedorService proveedorService, MonedaService monedaService,
			DepositoService depositoService, CompraValidator compraValidator, 
			ProductoService productoService, CondicionPagoDialog condicionPagoDialog,
			CondicionPagoService condicionPagoService, ConfiguracionService configService) {
		this.itemTableModel = itemTableModel;
		this.proveedorDialog = proveedorDialog;
		this.depositoDialog = depositoDialog;
		this.monedaDialog = monedaDialog;
		this.productoDialog = productoDialog;
		this.compraService = compraService;
		this.proveedorService = proveedorService;
		this.monedaService = monedaService;
		this.depositoService = depositoService;
		this.productoService = productoService;
		this.compraValidator = compraValidator;
		this.condicionPagoDialog = condicionPagoDialog;
		this.condicionPagoService = condicionPagoService;
		this.configService = configService;

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
		lblPrecio.setBounds(397, 6, 115, 18);
		pnlProducto.add(lblPrecio);

		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(153, 30, 242, 30);
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
		tfPrecio.setBounds(397, 30, 115, 30);
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
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidItem()) {
					addItem();
				}
			}
		});
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
		
		lblGastos = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraLocalPanel.lblGastos.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblGastos.setBounds(512, 6, 115, 18);
		pnlProducto.add(lblGastos);
		
		tfGasto = new JTextField();
		tfGasto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					calculatePrecioTotal();
					btnAdd.requestFocus();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfGasto.setHorizontalAlignment(SwingConstants.RIGHT);
		tfGasto.setFont(new Font("Arial", Font.PLAIN, 14));
		tfGasto.setColumns(10);
		tfGasto.setBounds(512, 30, 115, 30);
		pnlProducto.add(tfGasto);

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
		lblProveedor.setBounds(170, 4, 68, 30);
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
					findProveedorById(tfProveedorID.getText(), false);
				} else if (e.getKeyCode() == KeyEvent.VK_F12) {
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

		tfProveedorID.setBounds(252, 4, 51, 30);
		pnlCliente.add(tfProveedorID);
		tfProveedorID.setColumns(10);

		tfNombre = new JTextField();
		tfNombre.setEditable(false);
		tfNombre.setBounds(304, 4, 209, 30);
		pnlCliente.add(tfNombre);
		tfNombre.setColumns(10);

		JLabel lblVendedor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblVendedor.text")); //$NON-NLS-1$
		lblVendedor.setBounds(380, 39, 68, 30);
		pnlCliente.add(lblVendedor);

		tfMonedaID = new JTextField();
		tfMonedaID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfMonedaID.selectAll();
			}
		});
		tfMonedaID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(MONEDA_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfMonedaID.getText().isEmpty())
						findMonedaById(tfMonedaID.getText());
					else 
						showDialog(MONEDA_CODE);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfMonedaID.setText("");
		tfMonedaID.setBounds(462, 39, 51, 30);
		pnlCliente.add(tfMonedaID);
		tfMonedaID.setToolTipText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.tfMonedaID.toolTipText")); //$NON-NLS-1$
		tfMonedaID.setColumns(10);

		tfMoneda = new JTextField();
		tfMoneda.setEditable(true);
		tfMoneda.setEnabled(true);
		tfMoneda.setBounds(515, 39, 117, 30);
		pnlCliente.add(tfMoneda);
		tfMoneda.setColumns(10);

		JLabel lblDeposito = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblDeposito.text")); //$NON-NLS-1$
		lblDeposito.setBounds(635, 38, 40, 30);
		pnlCliente.add(lblDeposito);

		tfDepositoID = new JTextField();
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
					if (!tfDepositoID.getText().isEmpty())
						findDepositoById(tfDepositoID.getText());
					else
						showDialog(DEPOSITO_CODE);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfDepositoID.setColumns(10);
		tfDepositoID.setBounds(689, 39, 51, 30);
		pnlCliente.add(tfDepositoID);

		tfDeposito = new JTextField();
		tfDeposito.setEditable(false);

		tfDeposito.setColumns(10);
		tfDeposito.setBounds(741, 38, 129, 30);
		pnlCliente.add(tfDeposito);

		tfRuc = new JTextField();
		tfRuc.setEditable(false);
		tfRuc.setColumns(10);
		tfRuc.setBounds(741, 4, 129, 30);
		pnlCliente.add(tfRuc);

		tfDireccion = new JTextField();
		tfDireccion.setEditable(false);
		tfDireccion.setColumns(10);
		tfDireccion.setBounds(516, 4, 224, 30);
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
							//Notifications.showAlert("Proveedor y Nro. de Factura ya han sido cargados.");
							if (conf != null) {
								if (tfMonedaID.getText().isEmpty()&&conf.getPideMoneda() == 1) {
									tfMonedaID.requestFocus();
									Notifications.showAlert("Moneda no ha sido cargado");
								} else if (tfDepositoID.getText().isEmpty()&&conf.getPideDeposito() == 1) {
									tfDepositoID.requestFocus();
									Notifications.showAlert("Deposito no ha sido cargado");
								} else {
									tfProductoID.requestFocus();
								}
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
		tfFactura.setBounds(252, 39, 117, 30);
		pnlCliente.add(tfFactura);

		JLabel lblFactura = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.lblFactura.text")); //$NON-NLS-1$
		lblFactura.setBounds(170, 39, 68, 30);
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
		tfFechaCompra.setBounds(73, 38, 93, 30);
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
		btnGuardar.setBounds(291, 5, 110, 34);
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
		pnlBotonera.setLayout(null);
		pnlBotonera.add(btnGuardar);

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.btnCancelar.text")); //$NON-NLS-1$
		btnCancelar.setMnemonic('C');
		btnCancelar.setBounds(406, 5, 110, 34);
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
		btnCerrar.setBounds(521, 5, 110, 34);
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
		getContentPane().setLayout(null);
		getContentPane().add(tabbedPane);
		getContentPane().add(pnlCabezera);
		getContentPane().add(pnlBotonera);

		panel = new JPanel();
		panel.setBounds(12, 468, 896, 85);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblCantItem = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblCantItem.text"));
		lblCantItem.setBounds(2, 12, 74, 30);
		panel.add(lblCantItem);

		tfCantItem = new JTextField();
		tfCantItem.setFont(new Font("Dialog", Font.PLAIN, 14));
		tfCantItem.setBounds(85, 12, 51, 30);
		panel.add(tfCantItem);
		tfCantItem.setEditable(false);
		tfCantItem.setColumns(10);

		JLabel lblFlete = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraLocalPanel.lblFlete.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblFlete.setBounds(285, 12, 51, 30);
		panel.add(lblFlete);

		tfFlete = new JTextField();
		tfFlete.setFont(new Font("Dialog", Font.BOLD, 18));
		tfFlete.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFlete.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.tfGasto.text")); //$NON-NLS-1$
		tfFlete.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfFlete.selectAll();
			}
		});
		tfFlete.setBounds(340, 12, 101, 30);
		panel.add(tfFlete);
		tfFlete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfFlete.getText().isEmpty()) {
						Double subtotal = tfTotalFob.getText().isEmpty() ? 0
								: FormatearValor.stringADouble(tfTotalFob.getText());
						setTotals(0d, subtotal);
						tfDescuento.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfFlete.setColumns(10);

		JLabel lblCondicin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblCondicin.text"));
		lblCondicin.setBounds(2, 45, 74, 30);
		panel.add(lblCondicin);

		tfCondicion = new JTextField();
		tfCondicion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCondicion.getText().isEmpty())
						findCondicionPago(Integer.parseInt(tfCondicion.getText()));
					else
						showDialog(CONDICION_PAGO_CODE);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCondicion.setFont(new Font("Dialog", Font.PLAIN, 14));
		tfCondicion.setBounds(85, 46, 51, 30);
		panel.add(tfCondicion);

		JLabel lblDesc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblDesc.text"));
		lblDesc.setBounds(285, 46, 51, 30);
		panel.add(lblDesc);

		tfDescuento = new JTextField();
		tfDescuento.setFont(new Font("Dialog", Font.BOLD, 18));
		tfDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		tfDescuento.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages") //$NON-NLS-1$
				.getString("CompraPanel.tfDescuento.text")); //$NON-NLS-1$
		tfDescuento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescuento.selectAll();
			}
		});
		tfDescuento.setBounds(340, 46, 101, 30);
		panel.add(tfDescuento);
		tfDescuento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfDescuento.getText().isEmpty()) {
						Double subtotal = tfTotalFob.getText().isEmpty() ? 0
								: FormatearValor.stringADouble(tfTotalFob.getText());
						setTotals(0d, subtotal);
						tfObs.requestFocus();
					}

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfDescuento.setColumns(10);

		JLabel lblSubTotal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblSubTotal.text"));
		lblSubTotal.setBounds(453, 12, 74, 30);
		panel.add(lblSubTotal);

		JLabel lblObs = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblObs.text"));
		lblObs.setBounds(453, 45, 74, 30);
		panel.add(lblObs);

		tfTotalFob = new JTextField();
		tfTotalFob.setFont(new Font("Dialog", Font.BOLD, 20));
		tfTotalFob.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalFob.setBounds(529, 12, 129, 30);
		panel.add(tfTotalFob);
		tfTotalFob.setForeground(Color.RED);
		tfTotalFob.setEditable(false);
		tfTotalFob.setColumns(10);

		JLabel lblTotal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages")
				.getString("CompraPanel.lblTotal.text"));
		lblTotal.setBounds(667, 12, 84, 30);
		panel.add(lblTotal);

		tfTotalCif = new JTextField();
		tfTotalCif.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalCif.setBounds(755, 12, 129, 30);
		panel.add(tfTotalCif);
		tfTotalCif.setEditable(false);
		tfTotalCif.setFont(new Font("Dialog", Font.BOLD, 20));
		tfTotalCif.setForeground(Color.RED);
		tfTotalCif.setColumns(10);

		tfObs = new JTextField();
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
		tfObs.setBounds(529, 45, 355, 30);
		panel.add(tfObs);
		tfObs.setColumns(10);

		label_4 = new JLabel("*");
		label_4.setVerticalAlignment(SwingConstants.BOTTOM);
		label_4.setToolTipText("Campos obligatorios");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));
		label_4.setBounds(70, 46, 14, 30);
		panel.add(label_4);
		
		lblVence = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraLocalPanel.lblVence.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblVence.setBounds(138, 45, 51, 30);
		panel.add(lblVence);
		
		tfVence = new JTextField();
		tfVence.setFont(new Font("Dialog", Font.PLAIN, 14));
		tfVence.setEditable(false);
		tfVence.setColumns(10);
		tfVence.setBounds(193, 45, 84, 30);
		panel.add(tfVence);

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
		tfCompraId.setBounds(73, 3, 93, 32);
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
		label.setBounds(236, 4, 14, 30);
		pnlCliente.add(label);

		label_2 = new JLabel("*");
		label_2.setVerticalAlignment(SwingConstants.BOTTOM);
		label_2.setToolTipText("Campos obligatorios");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Dialog", Font.BOLD, 20));
		label_2.setBounds(446, 39, 14, 30);
		pnlCliente.add(label_2);

		label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3.setToolTipText("Campos obligatorios");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Dialog", Font.BOLD, 20));
		label_3.setBounds(674, 38, 14, 30);
		pnlCliente.add(label_3);

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
	}

	private MaskFormatter formatoFecha;
	private JLabel label_7;
	private JLabel lblGastos;
	private JTextField tfGasto;

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
		compra.setVencimiento(Fechas.sumarFecha(Integer.parseInt(tfCondicion.getText()), 0, 0, tfFechaCompra.getText()));
		compra.setCondicion(tfCondicion.getText().isEmpty() ? 0:Integer.parseInt(tfCondicion.getText()));
		
		if (tfMonedaID.getText().isEmpty())
			compra.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID));
		else
			compra.setMoneda(new Moneda(Long.valueOf(tfMonedaID.getText())));
		
		compra.setProveedor(new Proveedor(Long.valueOf(tfProveedorID.getText())));
		compra.setProveedorNombre(tfNombre.getText());
		compra.setDeposito(new Deposito(Long.valueOf(tfDepositoID.getText())));
		compra.setUsuario(new Usuario(GlobalVars.USER_ID));
		compra.setTotalItem(tfCantItem.getText().isEmpty() ? 1 : FormatearValor.stringToDouble(tfCantItem.getText()));
		compra.setSituacion("PENDIENTE");
		compra.setObs(tfObs.getText());

		compra.setGastos(tfFlete.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfFlete.getText()));
		compra.setDescuento(tfDescuento.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfDescuento.getText()));
		compra.setTotalFob(tfTotalFob.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalFob.getText()));
		compra.setTotalCif(tfTotalCif.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalCif.getText()));
		compra.setTotalGeneral(tfTotalCif.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalCif.getText()));

//		List<CompraDetalle> detalles = new ArrayList<>();
//		
//		if (!tfFlete.getText().isEmpty() || !tfDescuento.getText().isEmpty()) {
//			//update precio fob
//			Double desc = tfDescuento.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfDescuento.getText());
//			Double gastos = tfFlete.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfFlete.getText());
//			Double total = tfTotalFob.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalFob.getText());
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
		int habilitaLanzamientoCaja =0;
		if(conf!=null&&conf.getHabilitaLanzamientoCaja()!=0)
		    habilitaLanzamientoCaja = conf.getHabilitaLanzamientoCaja();
		
		for (CompraDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				Double cantCompra = e.getCantidad() != null ? e.getCantidad():0;
				Double entPendiente = p.getEntPendiente() != null ? p.getEntPendiente():0;
				if(habilitaLanzamientoCaja==1)
					p.setEntPendiente(entPendiente + cantCompra);
				else {
					Double cantDep=0d;
					switch(depositoDef.getId().intValue()) {
						case 1:
							cantDep=p.getDepO1();
							p.setDepO1(cantDep+cantCompra);
							break;
						case 2:	
							cantDep=p.getDepO2();
							p.setDepO2(cantDep+cantCompra);
							break;
						case 3:
							cantDep=p.getDepO3();
							p.setDepO3(cantDep+cantCompra);
							break;
						case 4 :
							cantDep=p.getDepO4();
							p.setDepO4(cantDep+cantCompra);
							break;
						case 5 :
							cantDep=p.getDepO5();
							p.setDepO5(cantDep+cantCompra);
							break;
						default :
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
		
		Optional<Compra> compra =  compraService.findByProveedorAndFacturaNro(new Proveedor(Long.valueOf(proveedorId)), factura);
		
		if (compra.isPresent()) {
			result = true;
		}
		
		return result;
	}
	
	private void findCondicionPago(int cantDia) {
		Optional<CondicionPago> condicion = condicionPagoService.findByCantDia(cantDia);
		
		if (condicion.isPresent()) {
			tfCondicion.setText(String.valueOf(cantDia));
			tfVence.setText(Fechas.formatoDDMMAAAA(Fechas.sumarFecha(Integer.parseInt(tfCondicion.getText()), 
					0, 0, tfFechaCompra.getText())));
			
			if (conf != null) {
				if (conf.getPideFleteCompraLocal() == 1)
					tfFlete.requestFocus();
				else
					tfDescuento.requestFocus();
			} else {
				tfFlete.requestFocus();
			}
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
	
	private Configuracion conf;
	private JLabel lblVence;
	private JTextField tfVence;
	
	public void getConfig() {
		Optional<Configuracion> config = configService.findByEmpresaId(new Empresa(GlobalVars.EMPRESA_ID));
		
		if (config.isPresent()) {
			this.conf = config.get();
			
			if (conf.getPideMoneda() == 0) {
				tfMonedaID.setEnabled(false);
				tfMoneda.setEnabled(false);
			}
			if (conf.getPideDepositoCompra() == 0) {
				tfDepositoID.setEnabled(false);
				tfDeposito.setEnabled(false);
			}
			if (conf.getPideFleteCompraLocal() == 0) 
				tfFlete.setEnabled(false);
			
			if (conf.getPideGastoItemCompra() == 0)
				tfGasto.setEnabled(false);
			
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
		} else if (tfDepositoID.getText().isEmpty() && (conf!=null && conf.getPideDeposito()==1)) { //si esta vacio
			Notifications.showAlert("El codigo del Deposito es obligatorio");
			tfDepositoID.requestFocus();
			return false;
		} else if (tfDepositoID.getText().isEmpty() && (conf != null && conf.getPideDepositoCompra() == 1)) {
			Notifications.showAlert("El codigo del Deposito es obligatorio");
			tfDepositoID.requestFocus();
			return false;
		} else if (tfMonedaID.getText().isEmpty() && (conf != null && conf.getPideMoneda() == 1)) {
			Notifications.showAlert("El codigo de Moneda es obligatorio");
			tfMonedaID.requestFocus();
			return false;
		}

		Optional<Proveedor> proveedor = proveedorService.findById(Long.valueOf(tfProveedorID.getText()));

		if (!proveedor.isPresent()) {
			Notifications.showAlert("No codigo del Proveedor no existe.!");
			tfProveedorID.requestFocus();
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

		return true;
	}

	private CompraDetalle getItem() {
		CompraDetalle item = new CompraDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(FormatearValor.stringToDouble(tfCantidad.getText()));
		item.setPrecio(FormatearValor.stringToDouble(tfPrecio.getText()));
		item.setSubtotal(FormatearValor.stringToDouble(tfPrecioTotal.getText()));
		
		item.setPrecioFob(FormatearValor.stringToDouble(tfPrecio.getText()));

		return item;
	}

	private void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfGasto.setText("");

		tfProductoID.requestFocus();
	}

	public void setTotals(Double cantItem, Double total) {
		Double descuento = tfDescuento.getText().isEmpty() ? 0d : Double.valueOf(tfDescuento.getText());
		Double flete = tfFlete.getText().isEmpty() ? 0d : Double.valueOf(tfFlete.getText());
		Double totalGeneral = (total + flete) - descuento;
		Double precioFob = total - descuento;

		tfTotalFob.setText(FormatearValor.doubleAString(precioFob));
		tfTotalCif.setText(FormatearValor.doubleAString(totalGeneral));

		if (cantItem != 0) tfCantItem.setText(FormatearValor.doubleAString(cantItem));
	}

	public void clearForm() {
		tfFactura.setText("");
		tfFechaCompra.setText("");
		tfProveedorID.setText("");
		tfNombre.setText("");
		tfRuc.setText("");
		tfDireccion.setText("");
		tfMonedaID.setText("");
		tfMoneda.setText("");
		tfDepositoID.setText("");
		tfDeposito.setText("");
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfObs.setText("");
		tfTotalCif.setText("");
		tfTotalFob.setText("");
		tfCantItem.setText("");
		tfDescuento.setText("");
		tfFlete.setText("");
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
		Double gastoUnit = FormatearValor.stringToDouble(!tfGasto.getText().isEmpty() ? tfGasto.getText():"0");
		Double precioTotal = (cantidad * precioUnit) + gastoUnit;

		tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));
		
		if (conf != null && conf.getPideGastoItemCompra() == 0) {
			btnAdd.requestFocus();
		} else if (conf != null && conf.getPideGastoItemCompra() == 1 && tfGasto.getText().isEmpty()) {
			tfGasto.requestFocus();
		} else {
			btnAdd.requestFocus();
		}	
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
			monedaDialog.setInterfaz(this);
			monedaDialog.setVisible(true);
			break;
		case DEPOSITO_CODE:
			depositoDialog.setInterfaz(this);
			depositoDialog.setVisible(true);
			break;
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.setVisible(true);
			break;
		case CONDICION_PAGO_CODE:
			condicionPagoDialog.setInterfaz(this);
			condicionPagoDialog.setVisible(true);
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

	private void findMonedaById(String monedaId) {	
		Optional<Moneda> moneda = monedaService.findById(Long.valueOf(monedaId));

		if (moneda.isPresent()) {
			tfMonedaID.setText(String.valueOf(moneda.get().getId()));
			tfMoneda.setText(moneda.get().getNombre());
			
			if (conf != null) {
				if (conf.getPideDepositoCompra() == 1) {
					tfDepositoID.requestFocus();
				} else {
					tfProductoID.requestFocus();
				}
			} else {
				tfDepositoID.requestFocus();
			}
		} else {
			Notifications.showAlert("No existe Moneda con ese codigo.!");
			tfMonedaID.requestFocus();
		}
	}

	private void findDepositoById(String depositoId) {
		Optional<Deposito> deposito = depositoService.findById(Long.valueOf(depositoId));

		if (deposito.isPresent()) {
			tfDepositoID.setText(String.valueOf(deposito.get().getId()));
			tfDeposito.setText(deposito.get().getNombre());
			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("No existe Deposito con ese codigo.!");
			tfDepositoID.requestFocus();
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
	public void getEntity(Deposito deposito) {
		if (deposito != null) {
			tfDepositoID.setText(String.valueOf(deposito.getId()));
			tfDeposito.setText(deposito.getNombre());
			tfProductoID.requestFocus();
		}
	}

	@Override
	public void getEntity(Producto producto) {
		setProducto(producto);
	}

	@Override
	public void getEntity(Moneda moneda) {
		if (moneda != null) {
			tfMonedaID.setText(String.valueOf(moneda.getId()));
			tfMoneda.setText(moneda.getNombre());
			tfDeposito.requestFocus();
		}
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
	public void goToCompraLocal() {
		tfMonedaID.setEditable(false);
		tfMoneda.setEditable(false);
	}

	@Override
	public void goToCompraImportacion() {
		tfMonedaID.setEditable(true);
		tfMoneda.setEditable(true);
	}

	@Override
	public void goToCompraConsiganada() {
		tfCondicion.setEditable(false);
		//cbCondicion.setText("30");
		tfMonedaID.setEditable(false);
		tfMoneda.setEditable(false);
		tfFactura.setEditable(false);

		// Setear por configuracion general
		tfDepositoID.setText("30");
		tfDeposito.setText("Deposito 30");
		tfDepositoID.setEnabled(false);
		tfDeposito.setEnabled(false);
	}

	@Override
	public void goToPedidoCompra() {
		tfMonedaID.setEditable(false);
		tfMoneda.setEditable(false);
		tfDepositoID.setEditable(false);
		tfDeposito.setEnabled(false);
		tfFactura.setEditable(false);
		tfFechaCompra.setEditable(false);
	}

	@Override
	public void getEntity(Compra c) {
		if (c != null) {
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
			Double costoFob = producto.getCostoFob() != null ? producto.getCostoFob() : 0;
			tfProductoID.setText(producto.getId().toString());
			tfDescripcion.setText(producto.getDescripcion());
			tfPrecio.setText(FormatearValor.doubleAString(costoFob));
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
		tfMonedaID.setText(String.valueOf(c.getMoneda().getId()));
		tfDepositoID.setText(String.valueOf(c.getDeposito().getId()));
		tfFactura.setText(c.getFactura());
		tfFechaCompra.setText(c.getFechaCompra() == null ? "" : String.valueOf(c.getFechaCompra()));
		tfCondicion.setText(String.valueOf(c.getCondicion()));
		tfCantItem.setText(FormatearValor.doubleAString(c.getTotalItem()));
		tfObs.setText(c.getObs());
		tfFlete.setText(FormatearValor.doubleAString(c.getGastos()));
		tfDescuento.setText(FormatearValor.doubleAString(c.getDescuento()));
		tfTotalFob.setText(FormatearValor.doubleAString(c.getTotalFob()));
		tfTotalCif.setText(FormatearValor.doubleAString(c.getTotalCif()));

		findProveedorById(c.getProveedor().getId() 	!= null ? String.valueOf(c.getProveedor().getId()) : "", false);
		findDepositoById(c.getDeposito().getId() 	!= null ? String.valueOf(c.getDeposito().getId()) : "");
		findMonedaById(c.getMoneda().getId() 		!= null ? String.valueOf(c.getMoneda().getId()) : "");

		itemTableModel.addEntities(c.getItems());
	}

	public void newCompra() {
		long max = compraService.getRowCount();
		long newId = max + 1;
		tfCompraId.setText(String.valueOf(newId));
		tfProveedorID.requestFocus();
		if (conf.getPideMoneda() == 0) {
			Optional<Moneda> moneda = monedaService.findById(GlobalVars.BASE_MONEDA_ID);
			setMonedaDef(moneda.get());
			tfMonedaID.setText(moneda.get().getId().toString());
			tfMoneda.setText(moneda.get().getNombre());
		}
		if (conf.getPideDepositoCompra() == 0) {
			Optional<Deposito> deposito = depositoService.findById(GlobalVars.DEPOSITO_ID);
			setDepositoDef(deposito.get());
			tfDepositoID.setText(String.valueOf(deposito.get().getId()));
			tfDeposito.setText(deposito.get().getNombre());
		}
		
//		if (conf.getDefineDepositoCompra() != 0) {
//			findDepositoById(String.valueOf(conf.getDefineDepositoCompra()));
//		}
	}

	@Override
	public void getEntity(CondicionPago condicionPago) {
		if (condicionPago != null) {
			tfCondicion.setText(String.valueOf(condicionPago.getCantDia()));
			tfFlete.requestFocus();
		}
	}

	public Deposito getDepositoDef() {
		return depositoDef;
	}

	public void setDepositoDef(Deposito depositoDef) {
		this.depositoDef = depositoDef;
	}

	public Moneda getMonedaDef() {
		return monedaDef;
	}

	public void setMonedaDef(Moneda monedaDef) {
		this.monedaDef = monedaDef;
	}
	
	
}