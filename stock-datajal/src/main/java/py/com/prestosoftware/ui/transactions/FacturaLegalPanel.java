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
import javax.swing.text.AbstractDocument;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.ClientePais;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalleTemporal;
import py.com.prestosoftware.data.models.VentaTemporal;
import py.com.prestosoftware.domain.services.ClientePaisService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.VentaTemporalService;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.domain.validations.VentaTemporalValidator;
import py.com.prestosoftware.ui.forms.ClienteAddPanel;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
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
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaSaldoDeposito;
import py.com.prestosoftware.ui.search.ConsultaFacturacionDelDiaDialog;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.ProductoVistaDialog;
import py.com.prestosoftware.ui.search.VentaTemporalInterfaz;
import py.com.prestosoftware.ui.table.VentaItemTemporalTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class FacturaLegalPanel extends JFrame
		implements ClienteInterfaz, ClientePaisInterfaz, ProductoInterfaz,
		VentaTemporalInterfaz,  ImpresionPanelInterfaz, ReImpresionPanelInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int CLIENTE_CODE = 1;
	private static final int VENDEDOR_CODE = 2;
	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4;
	private static final int SALDO_PRODUCTO_CODE = 5;
	private static final int CONDICION_PAGO_CODE = 6;
	private static final int VENTA_CODE = 7;
	private static final int CLIENTE_ADD_CODE = 8;

	private JLabel lblRuc, lblDireccion, lblBuscadorDeVentas;
	private JTextField tfClienteNombre, tfDescripcion, tfVentaId;
	private JTextField tfClienteID, tfPrecioTotal, tfPrecio;
	private JTextField tfCantidad, tfTotalItems;
	private JTextField tfTotal, tfClienteRuc; // tfSubtotal,
	private JTextField tfClienteDireccion, tfProductoID, tfStock;
	private JButton btnAdd, btnRemove, btnGuardar, btnCancelar, btnCerrar, btnVer;
	private JPanel pnlTotales;
	private JTable tbProductos;
	private JLabel label;
	private JLabel label_4;
	private JLabel lblCamposObligatorios;
	private JLabel label_5;
	private JLabel lblSituacion;
	private JTextField tfDvRuc;
	private JButton btnReimpresion;
	private JComboBox<String> tfCondicionPago;

	private ConsultaCliente clientDialog;
	private ProductoVistaDialog productoDialog;
	private ClienteAddPanel clienteAddPanel;
	private ConsultaFacturacionDelDiaDialog consultaVentasDelDiaDialog;

	private VentaTemporalService ventaService;
	private VentaTemporalValidator ventaValidator;
	private VentaItemTemporalTableModel itemTableModel;

	private ClienteService clienteService;
	private ClientePaisService clientePaisService;
	private ProductoService productoService;


	
	private boolean isProductService;
	private String nivelPrecio;
	private Producto productoSeleccionado;
	private Double precioA;
	private Double precioB;
	private Double precioC;
	private int impuesto;
	private Double precioCompra;
	private VentaTemporal ventaSeleccionado;
	private Cliente clienteSeleccionado;
	private Double precioInicial;
	private Date fechaImpresion;
	private int nroTimbrado;
	private int cant;

	public FacturaLegalPanel(VentaItemTemporalTableModel itemTableModel, ConsultaCliente clientDialog,
			DepositoDialog depositoDialog, ProductoVistaDialog productoDialog, VentaTemporalValidator ventaValidator,
			VentaTemporalService ventaService, ClienteService clienteService, ClientePaisService clientePaisService,
			ProductoService productoService,  ConsultaSaldoDeposito saldoDeposito,
			ConsultaFacturacionDelDiaDialog consultaVentasDelDiaDialog, ClienteAddPanel clienteAddPanel) {
		this.itemTableModel = itemTableModel;
		this.clientDialog = clientDialog;
		this.productoDialog = productoDialog;
		this.ventaValidator = ventaValidator;
		this.ventaService = ventaService;
		this.clienteService = clienteService;
		this.clientePaisService = clientePaisService;
		this.productoService = productoService;
		this.consultaVentasDelDiaDialog = consultaVentasDelDiaDialog;
		this.clienteAddPanel = clienteAddPanel;

		setSize(1079, 672);
		setTitle("FACTURACION");

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
		tabbedPane.setBounds(12, 106, 1043, 373);
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
		lblDescripcion.setBounds(143, 10, 180, 30);
		pnlProducto.add(lblDescripcion);

		JLabel lblSubtotal = new JLabel("TOTAL");
		lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubtotal.setBounds(708, 10, 76, 30);
		pnlProducto.add(lblSubtotal);

		JLabel lblPrecio = new JLabel("PRECIO");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(582, 10, 100, 30);
		pnlProducto.add(lblPrecio);

		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(143, 39, 429, 30);
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
		tfPrecioTotal.setBounds(708, 39, 122, 30);
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
		tfPrecio.setBounds(582, 39, 116, 30);
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
					btnCancelar.requestFocus();
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
		tfProductoID.setBounds(6, 39, 61, 30);
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
		btnRemove.setBounds(972, 39, 56, 30);
		pnlProducto.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(6, 81, 1022, 251);
		pnlProducto.add(scrollProducto);

		tbProductos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		tbProductos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Util.ocultarColumna(tbProductos, 5);
		Util.ocultarColumna(tbProductos, 6);
		Util.ocultarColumna(tbProductos, 7);
		Util.ocultarColumna(tbProductos, 8);
		Util.ocultarColumna(tbProductos, 9);
		Util.ocultarColumna(tbProductos, 10);
		Util.ocultarColumna(tbProductos, 11);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
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
									tfPrecio.requestFocus();
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
		tfCantidad.setBounds(77, 39, 56, 30);
		pnlProducto.add(tfCantidad);

		JLabel lblCantidad = new JLabel("CANT.");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidad.setBounds(77, 10, 86, 30);
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
		panel_3.setBounds(12, 0, 1006, 105);
		panel_3.setBorder(
				new TitledBorder(null, "SELECCIONE CLIENTE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 990, 79);
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

		btnAdd.setBounds(913, 39, 56, 30);
		pnlProducto.add(btnAdd);
		
		JLabel lblStock = new JLabel("STOCK");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStock.setBounds(840, 10, 45, 30);
		pnlProducto.add(lblStock);
		
		tfStock = new JTextField();
		tfStock.setFont(new Font("Arial", Font.PLAIN, 14));
		tfStock.setEditable(false);
		tfStock.setColumns(10);
		tfStock.setBounds(840, 39, 63, 30);
		pnlProducto.add(tfStock);
		
		lblDescripcionFiscal = new JLabel("");
		lblDescripcionFiscal.setBounds(252, 10, 320, 24);
		pnlProducto.add(lblDescripcionFiscal);

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
		tfClienteNombre.setBounds(448, 6, 273, 30);
		pnlCliente.add(tfClienteNombre);
		tfClienteNombre.setColumns(10);

		lblRuc = new JLabel("RUC:");
		lblRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRuc.setBounds(0, 42, 42, 30);
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

							tfProductoID.requestFocus();
				}
			}
		});
		tfClienteRuc.setColumns(10);
		tfClienteRuc.setBounds(43, 42, 150, 30);
		pnlCliente.add(tfClienteRuc);

		lblDireccion = new JLabel("DIR:");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDireccion.setBounds(731, 6, 45, 30);
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
		tfClienteDireccion.setBounds(801, 6, 179, 30);
		pnlCliente.add(tfClienteDireccion);

		lblBuscadorDeVentas = new JLabel("NOTA:");
		lblBuscadorDeVentas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBuscadorDeVentas.setBounds(0, 6, 42, 30);
		pnlCliente.add(lblBuscadorDeVentas);

		tfVentaId = new JTextField();
		tfVentaId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfVentaId.setEditable(true);
		tfVentaId.setBounds(43, 6, 67, 30);
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

		tfDvRuc = new JTextField();
		tfDvRuc.setFont(new Font("Tahoma", Font.ITALIC, 14));
		tfDvRuc.setEnabled(false);
		tfDvRuc.setBounds(203, 42, 27, 30);
		pnlCliente.add(tfDvRuc);
		tfDvRuc.setColumns(10);

		JButton btnAddProveedor = new JButton("+");
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

		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(9, 578, 1046, 35);
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
		pnlTotales.setBounds(12, 478, 1043, 90);
		getContentPane().add(pnlTotales);
		pnlTotales.setLayout(null);

		JLabel lblCantItem = new JLabel("Cant. Item:");
		lblCantItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantItem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCantItem.setBounds(0, 12, 74, 30);
		pnlTotales.add(lblCantItem);

		tfTotalItems = new JTextField();
		tfTotalItems.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfTotalItems.setBounds(105, 12, 121, 30);
		pnlTotales.add(tfTotalItems);
		tfTotalItems.setEditable(false);
		tfTotalItems.setColumns(10);

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
		lblTotal.setBounds(639, 12, 51, 30);
		pnlTotales.add(lblTotal);

		tfTotal = new JTextField();
		tfTotal.setBounds(701, 12, 130, 30);
		pnlTotales.add(tfTotal);
		tfTotal.setEditable(false);
		tfTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfTotal.setForeground(Color.RED);
		tfTotal.setColumns(10);
		
		JLabel lblCondicin = new JLabel("Cond. Pag:");
		lblCondicin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCondicin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCondicin.setBounds(0, 52, 84, 30);
		pnlTotales.add(lblCondicin);
		
		JLabel label_2 = new JLabel("*");
		label_2.setVerticalAlignment(SwingConstants.BOTTOM);
		label_2.setToolTipText("Campos obligatorios");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Dialog", Font.BOLD, 20));
		label_2.setBounds(87, 52, 14, 30);
		pnlTotales.add(label_2);
		
		tfCondicionPago = new JComboBox<String>();
		tfCondicionPago.setModel(new DefaultComboBoxModel(new String[] {"Contado", "30 días"}));
		tfCondicionPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfCondicionPago.setBounds(105, 52, 121, 30);
		pnlTotales.add(tfCondicionPago);

		label_4 = new JLabel("*");
		label_4.setBounds(649, 52, 14, 25);
		pnlTotales.add(label_4);
		label_4.setVerticalAlignment(SwingConstants.BOTTOM);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setToolTipText("Campos obligatorios");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));

		lblCamposObligatorios = new JLabel("Campos Obligatorios");
		lblCamposObligatorios.setBounds(669, 52, 299, 25);
		pnlTotales.add(lblCamposObligatorios);
		lblCamposObligatorios.setFont(new Font("Dialog", Font.BOLD, 20));
		// vistaDescuentoTotal();
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

	

	private void getItemSelected() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			VentaDetalleTemporal item = itemTableModel.getEntityByRow(selectedRow);
			
			this.precioCompra= item.getPrecioCosto()==null?0:item.getPrecioCosto();
			tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfPrecio.setText(FormatearValor.doubleAString(item.getPrecio()));
			tfStock.setText(FormatearValor.doubleAString(item.getStock()));
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




	private void abandonarNota() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "ABANDONAR NOTA.?", "AVISO",
				JOptionPane.OK_CANCEL_OPTION);

		if (respuesta == 0) {
			// removeItemBloq();
			clearForm();
			newVenta();
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

	private VentaTemporal getVentaFrom() {
		VentaTemporal venta = new VentaTemporal();
		venta.setFecha(new Date());
		this.setFechaImpresion(new Date());
		venta.setHora(new Date());

		if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("30 días")) {
			venta.setCondicion(2);
		} else {
			venta.setCondicion(1);
		}

		venta.setComprobante("SIN COMPROBANTE");

		

		venta.setCliente(new Cliente(Long.valueOf(tfClienteID.getText())));
		venta.setClienteNombre(tfClienteNombre.getText());
		venta.setClienteRuc(tfClienteRuc.getText());
		venta.setClienteDireccion(tfClienteDireccion.getText());

			if (tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("Contado")) {
				venta.setSituacion("PAGADO");
				venta.setTotalPagado(
						tfTotal.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotal.getText()));
			} else
				venta.setSituacion("PROCESADO");
		
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
		venta.setTotalGeneral(tfTotal.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotal.getText())); // TOTAL

		List<VentaDetalleTemporal> detalles = new ArrayList<>();
			for (VentaDetalleTemporal item : itemTableModel.getEntities()) {
				detalles.add(item);
			}

			venta.setItems(detalles);
		
		return venta;
	}

	private VentaDetalleTemporal getItem() {
		VentaDetalleTemporal item = new VentaDetalleTemporal();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(FormatearValor.stringToDoubleFormat(tfCantidad.getText()));
		item.setPrecio(FormatearValor.stringToDouble(tfPrecio.getText()));
		//item.setSubtotal(FormatearValor.stringToDoubleFormat(tfPrecioTotal.getText()));
		item.setStock(FormatearValor.stringToDouble(tfStock.getText()));
		item.setDescripcionFiscal(lblDescripcionFiscal.getText());
		item.setPrecioCosto(this.precioCompra);
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
		// tfDescuentoItem.setText("");
		tfProductoID.requestFocus();
	}

	private void setTotals(Double cantItem, Double total) {
		Double totalGeneral = (total) ; // (total + flete)

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
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfTotal.setText("0");
		tfTotalItems.setText("0");
		tfStock.setText("");
		tfClienteNombre.setEnabled(false);
		tfClienteDireccion.setEnabled(false);
		//tfCondicionPago.setEnabled(true);
		tfClienteID.requestFocus();
		lblDescripcionFiscal.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		newVenta();
	}

	public JTextField getTfClienteID() {
		return tfClienteID;
	}

	public JTextField getTfVentaId() {
		return tfVentaId;
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
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.getProductos();
			productoDialog.limpiaDatosComplementarios();
			productoDialog.setVisible(true);
			break;
		case VENTA_CODE:
			consultaVentasDelDiaDialog.setInterfaz(this);
			consultaVentasDelDiaDialog.setVentasTemporalDelDia();
			consultaVentasDelDiaDialog.setVisible(true);
			break;
		case CLIENTE_ADD_CODE:
			clienteAddPanel.setInterfaz(this);
			clienteAddPanel.loadCiudades();
			clienteAddPanel.loadEmpresas();
			clienteAddPanel.addNewCliente();
			clienteAddPanel.setVisible(true);
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
	public void getEntity(VentaTemporal v) {
		try {
			if (v != null) {
				btnGuardar.setVisible(false);
				btnReimpresion.setVisible(true);
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(false);
				setVenta(v);
				tbProductos.disable();
				//tfCondicionPago.setEnabled(false);
			} else {
				Notifications.showAlert("Hubo problemas con el Producto, intente nuevamente!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con la venta, intente nuevamente!");
			// TODO: handle exception
		}
	}

	public void setVenta(VentaTemporal v) {
		try {
			tfVentaId.setText(v.getId().toString());
			tfClienteID.setText(String.valueOf(v.getCliente().getId()));
			tfClienteNombre.setText(v.getCliente().getNombre());
			tfClienteRuc.setText(v.getCliente().getCiruc());
			tfDvRuc.setText(v.getCliente().getDvruc());
			tfClienteDireccion.setText(v.getCliente().getDireccion());
			tfTotalItems.setText(String.valueOf(v.getCantItem()));
//			if (v.getCondicion() == 0)
//				tfCondicionPago.setSelectedIndex(0);
//			else
//				tfCondicionPago.setSelectedIndex(1);

			tfTotal.setText(FormatearValor.doubleAString((v.getTotalGeneral())));
			if(v.getSituacion().equalsIgnoreCase("0")||v.getSituacion().equalsIgnoreCase("1")) {
				lblSituacion.setText(v.getSituacion().equalsIgnoreCase("0")?"VIGENTE":"ANULADO");					
			}else {
				lblSituacion.setText(v.getSituacion());				
			}
			List<VentaDetalleTemporal> listaDetalles = new ArrayList<VentaDetalleTemporal>();
			List<Object[]> listaItems = ventaService.retriveVentaTemporalDetalleByIdVentaTemporal(v.getId());
			// venta_id, cantidad, precio, producto, producto_id, subtotal, id, iva
			Double cantItem = 0d;
			Double total = 0d;
			for (Object[] object : listaItems) {
				VentaDetalleTemporal det = new VentaDetalleTemporal();
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
			v.setItems(new ArrayList<VentaDetalleTemporal>());
			v.setItems(listaDetalles);
			itemTableModel.clear();
			itemTableModel.addEntities(listaDetalles);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(this,
				"CONFIRMAR FACTURACIÓN " ,
				"AVISO - AGROPROGRESO", JOptionPane.OK_CANCEL_OPTION);
		Integer print=null;
		if (respuesta == 0) {
			if (validateCabezera()) { // && validateItems(itemTableModel.getEntities())
				VentaTemporal venta = getVentaFrom();
				
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
					
						try {
						VentaTemporal v = ventaService.save(venta, clienteNuevo, tfCondicionPago.getSelectedItem().toString());
							Notifications.showAlert("Venta registrado con exito.!");
							print = JOptionPane.showConfirmDialog(this, "IMPRIMIR", "AVISO - AGROPROGRESO",
									JOptionPane.OK_CANCEL_OPTION);
						} catch (Exception e) {
							Notifications.showAlert("Ocurrió un error en VentaTemporal!, intente nuevamente");
							
							// TODO Auto-generated catch block
							e.printStackTrace();
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



	private ImpresionPanel panel = null;

	private void imprimirDialogo() {
		Long nro=ventaService.getNroTimbrado()+1;
		if (this.panel == null) {
			panel = new ImpresionPanel();
			panel.setPanelInterfaz(this);
		}
		panel.getTfNumeroTimbrado().setText(nro+"");

		panel.setVisible(true);
	}

	private ReImpresionPanel panelReImpresion = null;
	private JLabel lblDescripcionFiscal;

	private void imprimirDialogoReimpresion() {
		Long nro=ventaService.getNroTimbrado()+1;
		if (this.panelReImpresion == null) {
			panelReImpresion = new ReImpresionPanel();
			panelReImpresion.setPanelInterfaz(this);
		}
		panelReImpresion.getTfNumeroTimbrado().setText(nro+"");
		panelReImpresion.setVisible(true);
	}

	

	private boolean validateCabezera() {
		if (tfClienteID.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Cliente es obligatorio");
			tfClienteID.requestFocus();
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

		
		return true;
	}


	public void newVenta() {
		long max = ventaService.getRowCount();
		tfVentaId.setText(String.valueOf(max + 1));
		resetCliente();
		resetVenta();
		btnGuardar.setVisible(true);
		btnReimpresion.setVisible(false);
		btnAdd.setEnabled(true);
		btnRemove.setEnabled(true);
		tfClienteID.requestFocus();
		tbProductos.enable();
		//tfCondicionPago.setEnabled(true);
	}

	private void resetVenta() {
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfTotal.setText("0");
		tfTotalItems.setText("0");
		// tfDescuentoItem.setText("0");
		lblSituacion.setText("VIGENTE");
		tbProductos.enable();
		tfClienteNombre.setEnabled(false);
		tfStock.setText("");

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
				setClienteSET(clientePais);
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas en la busqueda, intente nuevamente!");
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
	
			if (cliente.getId() == 0) {
				// habilitar nombre, ruc, direccion
				tfClienteNombre.setEnabled(false);
				tfClienteRuc.setEnabled(true);
				tfClienteDireccion.setEnabled(true);
				tfProductoID.requestFocus();

//				tfCondicionPago.setEnabled(false);
//				tfCondicionPago.setSelectedIndex(0);

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
			tfClienteNombre.setText(clientePai.get().getRazonSocial());
			tfClienteRuc.setText(clientePai.get().getCiruc());
			tfClienteDireccion.setText("");
			tfDvRuc.setText(clientePai.get().getDvruc());
			nivelPrecio = "Precio B";
//			tfCondicionPago.setEnabled(false);
//			tfCondicionPago.setSelectedIndex(0);
			// tfClienteID.setEnabled(false);
			tfClienteNombre.setEnabled(false);
		}
	}


	private void findProducto(String id) {
		try {
			Optional<Producto> producto = null;
			producto = productoService.findById(Long.valueOf(id.trim()));
			if (!producto.isPresent()) {
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
			Optional<VentaTemporal> venta = null;
			venta = ventaService.findById(Long.valueOf(id.trim()));
			if (venta.isPresent()) {
				setVenta(venta.get());
				btnGuardar.setVisible(false);
				btnReimpresion.setVisible(true);
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(false);
				tbProductos.disable();
				//tfCondicionPago.setEnabled(false);
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

				precioInicial =  producto.getPrecioVentaC();
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
					Double stockDep01 = p.get().getDepO1() != null ? p.get().getDepO1() : 0;
					// Double stockDepBloq = p.get().getDepO1Bloq() != null ? p.get().getDepO1Bloq()
					// : 0;

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
			if (isValidItem()) {
				Long productoId = tfProductoID.getText().isEmpty() ? 1 : Long.valueOf(tfProductoID.getText());
				Double cantidad = tfCantidad.getText().isEmpty() ? 0
						: FormatearValor.stringToDoubleFormat(tfCantidad.getText());

				
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
						} else {
							tfProductoID.requestFocus();
						}
					} else {
						itemTableModel.addEntity(getItem());
						calculateItem();
						tfProductoID.requestFocus();
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
				// VentaDetalleTemporalTemporal item = itemTableModel.getEntityByRow(selectedRow);
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


	public Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(Producto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public VentaTemporal getVentaSeleccionado() {
		return ventaSeleccionado;
	}

	public void setVentaSeleccionado(VentaTemporal ventaSeleccionado) {
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
		List<VentaDetalleTemporal> listVentaDetalleTemporal = itemTableModel.getEntities();
		for (VentaDetalleTemporal ventaDetalle : listVentaDetalleTemporal) {
			ventaDetalle.setSubtotal(ventaDetalle.getCantidad() * ventaDetalle.getPrecio());
			cantItem += ventaDetalle.getCantidad();
			total += ventaDetalle.getSubtotal();
		}

		setTotals(cantItem, total);
	}

	
	


	@Override
	public void imprimirFactura(boolean impresora, boolean timbrado, String nroTimbrado) {

		ImpresionUtil.performFacturaTemporal(tfClienteNombre.getText(), tfClienteRuc.getText() + "-" + tfDvRuc.getText(),
				"(0983) 518 217", tfClienteDireccion.getText(), tfVentaId.getText(),
				tfCondicionPago.getSelectedItem().toString(),
				GlobalVars.USER , tfTotal.getText(),
				itemTableModel.getEntities(), this.fechaImpresion, false, timbrado, nroTimbrado);
		ventaService.saveTimbrado(tfVentaId.getText(), nroTimbrado);
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

	public int getNroTimbrado() {
		return nroTimbrado;
	}

	public void setNroTimbrado(int nroTimbrado) {
		this.nroTimbrado = nroTimbrado;
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

	@Override
	public void imprimirTicket() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imprimirNota(boolean impresora) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargaNumeroTimbrado(int numeroTimbrado) {
		setNroTimbrado(numeroTimbrado);
	}
	
}
