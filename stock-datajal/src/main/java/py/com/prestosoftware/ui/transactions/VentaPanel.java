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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
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

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.ClientePais;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.CuentaCliente;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoIngreso;
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
import py.com.prestosoftware.domain.services.CuentaClienteService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ItemCuentaARecibirService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.MovimientoEgresoService;
import py.com.prestosoftware.domain.services.MovimientoIngresoService;
import py.com.prestosoftware.domain.services.MovimientoItemEgresoService;
import py.com.prestosoftware.domain.services.MovimientoItemIngresoService;
import py.com.prestosoftware.domain.services.ProcesoCobroClientesService;
import py.com.prestosoftware.domain.services.ProcesoCobroVentasService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.UsuarioRolService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.domain.validations.VentaValidator;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.reports.ImpresionPanel;
import py.com.prestosoftware.ui.reports.ImpresionPanelInterfaz;
import py.com.prestosoftware.ui.reports.ImpresionUtil;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.ClientePaisInterfaz;
import py.com.prestosoftware.ui.search.CondicionPagoDialog;
import py.com.prestosoftware.ui.search.CondicionPagoInterfaz;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaSaldoDeposito;
import py.com.prestosoftware.ui.search.ConsultaVentasDelDiaDialog;
import py.com.prestosoftware.ui.search.ConsultaVentasDialog;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.VendedorDialog;
import py.com.prestosoftware.ui.search.VendedorInterfaz;
import py.com.prestosoftware.ui.search.VentaInterfaz;
import py.com.prestosoftware.ui.table.VentaItemTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class VentaPanel extends JFrame implements ClienteInterfaz, ClientePaisInterfaz, VendedorInterfaz,
		DepositoInterfaz, ProductoInterfaz, VentaInterfaz, CondicionPagoInterfaz, ImpresionPanelInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int CLIENTE_CODE = 1;
	private static final int VENDEDOR_CODE = 2;
	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4;
	private static final int SALDO_PRODUCTO_CODE = 5;
	private static final int CONDICION_PAGO_CODE = 6;
	private static final int VENTA_CODE = 7;

	private JLabel lblRuc, lblDireccion, lblBuscadorDeVentas, lblDesc, lblDescItem;
	private JTextField tfClienteNombre, tfVendedor, tfDescripcion, tfVentaId, tfDescuentoItem;
	private JTextField tfClienteID, tfPrecioTotal, tfPrecio, tfVendedorID;
	private JTextField tfCantidad, tfTotalItems, tfVence, tfDescuento, tfObs;
	private JTextField tfTotal, tfDepositoID, tfDeposito, tfClienteRuc; // tfSubtotal,
	private JTextField tfClienteDireccion, tfCuotaCant, tfProductoID;
	private JButton btnAdd, btnRemove, btnGuardar, btnAnular, btnCancelar, btnCerrar, btnVer;
	private JTextField tfCondicionPago;
	private JPanel pnlTotales;
	private JTable tbProductos;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel lblCamposObligatorios;
	private JLabel label_5;
	private JTextField tfDvRuc;

	private ConsultaCliente clientDialog;
	private VendedorDialog vendedorDialog;
	private DepositoDialog depositoDialog;
	private CondicionPagoDialog condicionDialog;
	private ProductoDialog productoDialog;
	private ConsultaSaldoDeposito saldoDeposito;
	private ConsultaVentasDialog ventasDialog;
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
	private CuentaClienteService cuentaClienteService;
	private MovimientoIngresoService movimientoIngresoService;
	private MovimientoEgresoService movimientoEgresoService;
	private MovimientoItemIngresoService movimientoItemIngresoService;
	private MovimientoItemEgresoService movimientoItemEgresoService;
	private ProcesoCobroVentasService procesoCobroVentasService;
	private ProcesoCobroClientesService procesoCobroClientesService;
	private CuentaARecibirService cuentaARecibirService;
	private ItemCuentaARecibirService itemCuentaARecibirService;

	private boolean isProductService;
	private String nivelPrecio;
	private Producto productoSeleccionado;
	private Venta ventaSeleccionado;
	private Cliente clienteSeleccionado;
	private Double precioInicial;

	public VentaPanel(VentaItemTableModel itemTableModel, ConsultaCliente clientDialog, VendedorDialog vendedorDialog,
			DepositoDialog depositoDialog, ProductoDialog productoDialog, VentaValidator ventaValidator,
			VentaService ventaService, ClienteService clienteService, ClientePaisService clientePaisService,
			UsuarioService vendedorService, UsuarioRolService usuarioRolService, DepositoService depositoService,
			ProductoService productoService, CondicionPagoDialog condicionDialog, ConsultaSaldoDeposito saldoDeposito,
			CondicionPagoService condicionPagoService, ConfiguracionService configService,
			AperturaCierreCajaService movCajaService, CajaService cajaService, MovimientoCajaService pagoService,
			CuentaClienteService cuentaClienteService, ConsultaVentasDialog ventasDialog,
			ConsultaVentasDelDiaDialog consultaVentasDelDiaDialog, MovimientoIngresoService movimientoIngresoService,
			MovimientoEgresoService movimientoEgresoService, MovimientoItemIngresoService movimientoItemIngresoService,
			MovimientoItemEgresoService movimientoItemEgresoService, ProcesoCobroVentasService procesoCobroVentasService,
			ProcesoCobroClientesService procesoCobroClientesService, CuentaARecibirService cuentaARecibirService,
			ItemCuentaARecibirService itemCuentaARecibirService) {
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
		this.cuentaClienteService = cuentaClienteService;
		this.ventasDialog = ventasDialog;
		this.consultaVentasDelDiaDialog = consultaVentasDelDiaDialog;
		this.movimientoIngresoService=movimientoIngresoService;
		this.movimientoEgresoService=movimientoEgresoService;
		this.movimientoItemIngresoService=movimientoItemIngresoService;
		this.movimientoItemEgresoService=movimientoItemEgresoService;
		this.procesoCobroVentasService=procesoCobroVentasService;
		this.procesoCobroClientesService=procesoCobroClientesService;
		this.cuentaARecibirService=cuentaARecibirService;
		this.itemCuentaARecibirService=itemCuentaARecibirService;

		setSize(915, 660);
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
		tabbedPane.setBounds(12, 106, 891, 325);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("PRODUCTOS", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JLabel lblCodigo = new JLabel("COD");
		lblCodigo.setBounds(6, 10, 35, 30);
		pnlProducto.add(lblCodigo);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setBounds(152, 10, 171, 30);
		pnlProducto.add(lblDescripcion);

		JLabel lblSubtotal = new JLabel("TOTAL");
		lblSubtotal.setBounds(559, 10, 100, 30);
		pnlProducto.add(lblSubtotal);

		JLabel lblPrecio = new JLabel("PRECIO");
		lblPrecio.setBounds(460, 10, 100, 30);
		pnlProducto.add(lblPrecio);

		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(152, 39, 300, 30);
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
		tfPrecioTotal.setBounds(559, 39, 100, 30);
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
						tfPrecio.setText(FormatearValor.doubleAString(productoSeleccionado.getPrecioVentaA()));
						calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					if (!tfPrecio.getText().isEmpty()) {
						tfPrecio.setText(FormatearValor.doubleAString(productoSeleccionado.getPrecioVentaB()));
						calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F3) {
					if (!tfPrecio.getText().isEmpty()) {
						tfPrecio.setText(FormatearValor.doubleAString(productoSeleccionado.getPrecioVentaC()));
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
		tfPrecio.setBounds(460, 39, 100, 30);
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
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					showDialog(SALDO_PRODUCTO_CODE);
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
		tfProductoID.setBounds(6, 39, 71, 30);
		pnlProducto.add(tfProductoID);
		tfProductoID.setColumns(10);

		btnRemove = new JButton("-");
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
		btnRemove.setBounds(826, 39, 56, 30);
		pnlProducto.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(6, 81, 880, 205);
		pnlProducto.add(scrollProducto);

		tbProductos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
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
		tfCantidad.setBounds(77, 39, 71, 30);
		pnlProducto.add(tfCantidad);

		JLabel lblCantidad = new JLabel("CANT.");
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

		lblDescItem = new JLabel("DESCUENTO %");
		lblDescItem.setBounds(657, 10, 100, 30);
		pnlProducto.add(lblDescItem);

		tfDescuentoItem = new JTextField();
		tfDescuentoItem.setEditable(false);
		tfDescuentoItem.setHorizontalAlignment(SwingConstants.RIGHT);
		tfDescuentoItem.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescuentoItem.setColumns(10);
		tfDescuentoItem.setBounds(657, 39, 100, 30);
		pnlProducto.add(tfDescuentoItem);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(12, 0, 891, 105);
		panel_3.setBorder(
				new TitledBorder(null, "SELECCIONE CLIENTE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 876, 79);
		panel_3.add(pnlCliente);
		pnlCliente.setLayout(null);

		JLabel lblClienteID = new JLabel("CLIENTE:");
		lblClienteID.setBounds(156, 6, 57, 30);
		pnlCliente.add(lblClienteID);

		btnAdd = new JButton("+");
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 18));
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addItem();
				}
			}
		});

		btnAdd.setBounds(767, 39, 56, 30);
		pnlProducto.add(btnAdd);

		tfClienteID = new JTextField();
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
		tfClienteID.setBounds(225, 7, 67, 30);
		pnlCliente.add(tfClienteID);
		tfClienteID.setColumns(10);

		tfClienteNombre = new JTextField();
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
		tfClienteNombre.setBounds(295, 7, 240, 30);
		pnlCliente.add(tfClienteNombre);
		tfClienteNombre.setColumns(10);

		JLabel lblVendedor = new JLabel("VEND.");
		lblVendedor.setBounds(156, 41, 57, 30);
		pnlCliente.add(lblVendedor);

		tfVendedorID = new JTextField();
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
		tfVendedorID.setBounds(225, 41, 67, 30);
		pnlCliente.add(tfVendedorID);
		tfVendedorID.setColumns(10);

		tfVendedor = new JTextField();
		tfVendedor.setEditable(false);
		tfVendedor.setBounds(295, 41, 240, 30);
		pnlCliente.add(tfVendedor);
		tfVendedor.setToolTipText("Nombre del Vendedor");
		tfVendedor.setColumns(10);

		JLabel lblDeposito = new JLabel("DEP.:");
		lblDeposito.setBounds(544, 41, 40, 30);
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
		tfDepositoID.setBounds(596, 42, 58, 30);
		pnlCliente.add(tfDepositoID);

		tfDeposito = new JTextField();
		tfDeposito.setEditable(false);
		tfDeposito.setToolTipText("Nombre del Vendedor");
		tfDeposito.setColumns(10);
		tfDeposito.setBounds(657, 41, 213, 30);
		pnlCliente.add(tfDeposito);

		lblRuc = new JLabel("RUC:");
		lblRuc.setBounds(0, 41, 42, 30);
		pnlCliente.add(lblRuc);

		tfClienteRuc = new JTextField();
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
		tfClienteRuc.setBounds(43, 42, 75, 30);
		pnlCliente.add(tfClienteRuc);

		lblDireccion = new JLabel("DIR:");
		lblDireccion.setBounds(544, 6, 45, 30);
		pnlCliente.add(lblDireccion);

		tfClienteDireccion = new JTextField();
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
		tfClienteDireccion.setBounds(596, 6, 274, 30);
		pnlCliente.add(tfClienteDireccion);

		lblBuscadorDeVentas = new JLabel("NOTA:");
		lblBuscadorDeVentas.setBounds(0, 6, 42, 30);
		pnlCliente.add(lblBuscadorDeVentas);

		tfVentaId = new JTextField();
		tfVentaId.setEditable(false);
		tfVentaId.setBounds(43, 6, 75, 30);
		pnlCliente.add(tfVentaId);
		tfVentaId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVentaId.setColumns(10);

		btnVer = new JButton("VER");
		btnVer.setFont(new Font("Dialog", Font.BOLD, 18));
		btnVer.setBounds(123, 6, 27, 30);
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
		label.setBounds(209, 6, 14, 30);
		pnlCliente.add(label);

		label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_1.setToolTipText("Campos obligatorios");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Dialog", Font.BOLD, 20));
		label_1.setBounds(209, 41, 14, 30);
		pnlCliente.add(label_1);

		label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3.setToolTipText("Campos obligatorios");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Dialog", Font.BOLD, 20));
		label_3.setBounds(581, 42, 14, 30);
		pnlCliente.add(label_3);

		tfDvRuc = new JTextField();
		tfDvRuc.setEnabled(false);
		tfDvRuc.setBounds(123, 42, 27, 30);
		pnlCliente.add(tfDvRuc);
		tfDvRuc.setColumns(10);

		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(9, 578, 894, 35);
		getContentPane().add(pnlBotonera);

		btnGuardar = new JButton("Guardar(F4)");
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

		btnAnular = new JButton("Anular(F5)");
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

		btnCancelar = new JButton("Cancelar");
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
		pnlTotales.setBounds(12, 468, 888, 88);
		getContentPane().add(pnlTotales);
		pnlTotales.setLayout(null);

		JLabel lblCantItem = new JLabel("Cant. Item:");
		lblCantItem.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCantItem.setBounds(0, 12, 74, 30);
		pnlTotales.add(lblCantItem);

		tfTotalItems = new JTextField();
		tfTotalItems.setBounds(95, 12, 55, 30);
		pnlTotales.add(tfTotalItems);
		tfTotalItems.setEditable(false);
		tfTotalItems.setColumns(10);

		JLabel lblCondicin = new JLabel("Cond. Pag:");
		lblCondicin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCondicin.setBounds(0, 46, 74, 30);
		pnlTotales.add(lblCondicin);

		tfCondicionPago = new JTextField();
		tfCondicionPago.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCondicionPago.selectAll();
			}
		});
		tfCondicionPago.setText("0");
		tfCondicionPago.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCondicionPago.getText().isEmpty() && tfCondicionPago.getText().equalsIgnoreCase("100")) {
						findCondicionPago(Integer.valueOf(tfCondicionPago.getText()));
						tfCuotaCant.setEnabled(true);
						tfCuotaCant.requestFocus();
						tfCuotaCant.selectAll();

					} else if (!tfCondicionPago.getText().isEmpty()
							&& !tfCondicionPago.getText().equalsIgnoreCase("100")) {
						findCondicionPago(Integer.valueOf(tfCondicionPago.getText()));
						btnGuardar.requestFocus();
					} else {
						showDialog(CONDICION_PAGO_CODE);
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCondicionPago.setBounds(95, 46, 55, 30);
		pnlTotales.add(tfCondicionPago);

		JLabel lblCuotaCant = new JLabel("CuotaCant:");
		lblCuotaCant.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuotaCant.setBounds(311, 46, 41, 30);
		pnlTotales.add(lblCuotaCant);

		JLabel lblVence = new JLabel("Vence:");
		lblVence.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVence.setBounds(172, 45, 41, 30);
		pnlTotales.add(lblVence);

		tfCuotaCant = new JTextField();
		tfCuotaCant.setText("0");
		tfCuotaCant.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCuotaCant.selectAll();
			}
		});
		tfCuotaCant.setBounds(362, 46, 97, 30);
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
		tfVence.setEditable(false);
		tfVence.setBounds(215, 45, 97, 30);
		pnlTotales.add(tfVence);
		tfVence.setColumns(10);

		lblDesc = new JLabel("Desc.:");
		lblDesc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesc.setBounds(311, 12, 51, 30);
		pnlTotales.add(lblDesc);

		JLabel lblObs = new JLabel("Obs.:");
		lblObs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblObs.setBounds(490, 46, 51, 30);
		pnlTotales.add(lblObs);

		tfDescuento = new JTextField();
		tfDescuento.setText("0");
		tfDescuento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescuento.selectAll();
			}
		});
		tfDescuento.setBounds(362, 12, 105, 30);
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
		tfObs.setBounds(552, 46, 250, 30);
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
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(490, 12, 51, 30);
		pnlTotales.add(lblTotal);

		tfTotal = new JTextField();
		tfTotal.setBounds(552, 12, 130, 30);
		pnlTotales.add(tfTotal);
		tfTotal.setEditable(false);
		tfTotal.setFont(new Font("Arial", Font.BOLD, 16));
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

		label_4 = new JLabel("*");
		label_4.setVerticalAlignment(SwingConstants.BOTTOM);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setToolTipText("Campos obligatorios");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));
		label_4.setBounds(12, 443, 14, 25);
		getContentPane().add(label_4);

		lblCamposObligatorios = new JLabel("Campos Obligatorios");
		lblCamposObligatorios.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCamposObligatorios.setBounds(32, 443, 299, 25);
		getContentPane().add(lblCamposObligatorios);
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

	public void vistaDescuentoTotal() {
		if (!usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "VENTAS CON DESCUENTO TOTAL")) {
			tfDescuento.setVisible(false);
			lblDesc.setVisible(false);
		}
	}

	public void vistaDescuentoItem() {
		if (!usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "VENTAS CON DESC. ITEM")) {
			tfDescuentoItem.setVisible(false);
			lblDescItem.setVisible(false);
		}
	}

	private void getItemSelected() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			VentaDetalle item = itemTableModel.getEntityByRow(selectedRow);

			tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfPrecio.setText(FormatearValor.doubleAString(item.getPrecio()));
			tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
			tfDescuentoItem.setText(item.getDescuento().toString());
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
				tfVendedorID.setEnabled(false);
				tfVendedor.setEnabled(false);
			}

			if (conf.getPideDeposito() == 0) {
				tfDepositoID.setEnabled(false);
				tfDeposito.setEnabled(false);
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
			tfCondicionPago.setText(String.valueOf(cantDia));
			calculateVencimiento();

			if (conf != null) {
//				if (conf.getPideFlete() == 1) {
//					tfFlete.requestFocus();
//				} else 
				if (conf.getPideDescuento() == 1) {
					tfDescuento.requestFocus();
				} else {
					tfObs.requestFocus();
				}
//			} else {
//				tfFlete.requestFocus();
//			}
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
		Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());

		Producto p = productoService.getStockDepositoByProductoId(productoId);
		int depositoId = Integer.parseInt(tfDepositoID.getText());
		Double salPend = 0d;
		Double depBlo = 0d;
		if (conf != null && conf.getHabilitaLanzamientoCaja() == 1)
			salPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;

		switch (depositoId) {
		case 1:
			Double dep01 = p.getDepO1() != null ? p.getDepO1() : 0;
			depBlo = p.getDepO1Bloq() != null ? p.getDepO1Bloq() : 0;
			result = getStockDisp(dep01 - salPend - depBlo, cantidad);
			break;
		case 2:

			Double dep02 = p.getDepO2() != null ? p.getDepO2() : 0;
			depBlo = p.getDepO2Bloq() != null ? p.getDepO2Bloq() : 0;
			result = getStockDisp(dep02 - salPend - depBlo, cantidad);
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
			removeItemBloq();
			clearForm();
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
		} else if (!tfCantidad.getText().isEmpty() && FormatearValor.stringADouble(tfCantidad.getText()) <= 0) {
			Notifications.showAlert("La cantidad debe ser mayor a cero");
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
		venta.setHora(new Date());

		if (!tfVence.getText().isEmpty())
			venta.setVencimiento(Fechas.sumarFecha(Integer.valueOf(tfCondicionPago.getText()), 0, 0,
					Fechas.dateUtilAStringDDMMAAAA(new Date())));
		else
			venta.setVencimiento(new Date());

		venta.setComprobante("SIN COMPROBANTE");
		venta.setCondicion(Integer.valueOf(tfCondicionPago.getText()));

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
			if (!tfCondicionPago.getText().isEmpty() && tfCondicionPago.getText().equalsIgnoreCase("0")) {
				venta.setSituacion("PAGADO");
				venta.setTotalPagado(
						tfTotal.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotal.getText()));
			} else
				venta.setSituacion("PROCESADO");
		}

		venta.setObs(tfObs.getText());
		String valorItem = tfTotalItems.getText().isEmpty() ? "1"
				: FormatearValor.sinSeparadorDeMiles(tfTotalItems.getText().toString());
		venta.setCantItem(Integer.valueOf(valorItem));

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
		VentaDetalle item = new VentaDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(FormatearValor.stringToDouble(tfCantidad.getText()));
		item.setPrecio(FormatearValor.stringToDouble(tfPrecio.getText()));
		item.setSubtotal(FormatearValor.stringToDouble(tfPrecioTotal.getText()));
		item.setDescuento(FormatearValor.stringToDouble(tfDescuentoItem.getText()));
		Integer iva = this.getProductoSeleccionado().getImpuesto().getPorcentaje().intValue();
		item.setIva(iva);

		return item;
	}

	private void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfDescuentoItem.setText("");
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
		tfVendedorID.setText("");
		tfVendedor.setText("");
		tfDeposito.setText("");
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfObs.setText("");
		tfTotal.setText("0");
		tfTotalItems.setText("0");
		tfDescuento.setText("0");
		tfDescuentoItem.setText("0");
		tfCuotaCant.setText("0");
		tfVence.setText("");
		// tfSubtotal.setText("0");
		tfCondicionPago.setText("0");
		tfClienteNombre.setEnabled(false);
		// tfClienteRuc.setEnabled(false);

		tfClienteDireccion.setEnabled(false);

		tfCondicionPago.setEnabled(true);
		tfClienteID.requestFocus();
		tfCuotaCant.setEnabled(true);

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		tfDepositoID.setEditable(true);
		tfDepositoID.setText("");

		calculateVencimiento();

		// newVenta();
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
		Date fechaVenc = Fechas.sumarFecha(Integer.valueOf(tfCondicionPago.getText()), 0, 0, fecha);
		tfVence.setText(Fechas.formatoDDMMAAAA(fechaVenc));
		// }
	}

	private void calculatePrecioTotal() {
		Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
		Double precioUnit = FormatearValor.stringToDouble(tfPrecio.getText());
		Double precioTotal = cantidad * precioUnit;

		tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));
		if (usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "VENTAS CON DESC. ITEM")) {
			Double desc = (((precioUnit - this.getPrecioInicial()) / this.getPrecioInicial()) * 100);
			tfDescuentoItem.setText(FormatearValor.doubleAString(desc));
		}

		btnAdd.requestFocus();
	}

	private void showDialog(int code) {
		switch (code) {
		case CLIENTE_CODE:
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
			consultaVentasDelDiaDialog.setVisible(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void getEntity(Cliente cliente) {
		setCliente(cliente);
	}

	@Override
	public void getEntity(ClientePais clientePais) {
		setClientePais(clientePais);
	}

	@Override
	public void getEntity(Usuario usuario) {
		if (usuario != null) {
			tfVendedorID.setText(String.valueOf(usuario.getId()));
			tfVendedor.setText(usuario.getUsuario());
			tfDepositoID.requestFocus();
		}
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
	public void getEntity(Venta v) {
		if (v != null) {
			setVenta(v);
		}
	}

	public void setVenta(Venta v) {
		tfClienteID.setText(String.valueOf(v.getCliente().getId()));
		tfClienteNombre.setText(v.getClienteNombre());
		tfClienteRuc.setText(v.getClienteRuc());
		tfDvRuc.setText(v.getCliente().getDvruc());
		tfClienteDireccion.setText(v.getCliente().getDireccion());
		tfTotalItems.setText(String.valueOf(v.getCantItem()));
		tfCondicionPago.setText(String.valueOf(v.getCondicion()));
		tfDepositoID.setText(String.valueOf(v.getDeposito().getId()));
		tfDeposito.setText("");
		tfDescuento.setText(String.valueOf(v.getTotalDescuento()));
		// tfSubtotal.setText(String.valueOf(v.getTotalGravada10()));
		tfTotal.setText(FormatearValor.doubleAString((v.getTotalGeneral())));
		tfObs.setText(String.valueOf(v.getObs()));
		itemTableModel.addEntities(v.getItems());
		setVentaSeleccionado(v);
	}

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
		Integer respuesta = JOptionPane.showConfirmDialog(this, "CONFIRMAR", "AVISO - AGROPROGRESO",
				JOptionPane.OK_CANCEL_OPTION);
		if (respuesta == 0) {
			if (validateCabezera()) { // && validateItems(itemTableModel.getEntities())
				Venta venta = getVentaFrom();
				venta.setCaja(new Caja(Long.valueOf(1)));

				Optional<ValidationError> errors = ventaValidator.validate(venta);

				if (errors.isPresent()) {
					ValidationError validationError = errors.get();
					Notifications.showFormValidationAlert(validationError.getMessage());
				} else {
					if (tfClienteID.getText().equalsIgnoreCase("999")) {
						Cliente clienteNuevo = new Cliente();
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
						clienteNuevo = clienteService.save(clienteNuevo);
						venta.setCliente(clienteNuevo);
					}

					Venta v = ventaService.save(venta);

					if (v != null) {
						updateStockProduct(v.getItems());
						if (conf != null && conf.getHabilitaLanzamientoCaja() == 0) {
							lanzamientoCaja(v);
							movimientoIngresoProcesoCobroVenta(v);
							
							//cuentaCliente(v);
						}
						Notifications.showAlert("Venta registrado con exito.!");
					}

					Integer print = JOptionPane.showConfirmDialog(this, "IMPRIMIR", "AVISO - AGROPROGRESO",
							JOptionPane.OK_CANCEL_OPTION);

					if (print == 0)
						imprimirDialogo();
					else
						newVenta();
				}
			}
		} else {
			tfProductoID.requestFocus();
		}
	}
	
	private void movimientoIngresoProcesoCobroVenta(Venta venta) {
		MovimientoIngreso m=new MovimientoIngreso();
		m.setFecha(new Date());;
		m.setHora(new Date());
		m.setMinCaja(1);
		m.setMinDocumento(venta.getId().toString());
		m.setMinEntidad(venta.getCliente().getId().toString());
		m.setMinProceso(Integer.valueOf(venta.getId().toString()));
		m.setMinTipoProceso(1);
		m.setMinTipoEntidad(Integer.valueOf(clienteSeleccionado.getTipoEntidad()));
		m.setMinSituacion(0);
		m= movimientoIngresoService.save(m);
		
		MovimientoItemIngreso mii=new MovimientoItemIngreso();
		mii.setMiiNumero(m.getMinNumero());
		mii.setMiiIngreso(1);
		double monto=(double) Math.round(venta.getTotalGeneral()/11);
		mii.setMiiMonto(monto);
		movimientoItemIngresoService.save(mii);
		
		MovimientoItemIngreso miiva=new MovimientoItemIngreso();
		miiva.setMiiNumero(m.getMinNumero());
		miiva.setMiiIngreso(11);
		miiva.setMiiMonto(venta.getTotalGeneral()-monto);
		movimientoItemIngresoService.save(miiva);
		
		ProcesoCobroVentas pcv=new ProcesoCobroVentas();
		pcv.setPveVenta(venta.getId().intValue());
		pcv.setPveIngresoegreso(1);
		pcv.setPveTipoproceso(31);
		pcv.setPveProceso(m.getMinNumero());
		pcv.setPveFlag(1);
		procesoCobroVentasService.save(pcv);
	}
	
	

	private void cuentaCliente(Venta venta) {
		if (!tfCondicionPago.getText().equalsIgnoreCase("0")) {
			CuentaCliente cuentaCliente = new CuentaCliente();
			List<CuentaCliente> listaCuentaClientes = new ArrayList<CuentaCliente>();
			int cant=Integer.valueOf(tfCuotaCant.getText());
			if (cant > 0) {
				Double valorTotal = venta.getTotalGeneral() / cant;
				Calendar cal = Calendar.getInstance();
				for (int i = 0; i < cant; i++) {
					cal.add(Calendar.MONTH, 1);
					cuentaCliente = new CuentaCliente();
					cuentaCliente.setCliente(venta.getCliente());
					cuentaCliente.setClienteNombre(venta.getClienteNombre());
					cuentaCliente.setCredito(valorTotal);
					cuentaCliente.setDebito(0d);
					cuentaCliente.setDocumento("Venta crédito cuota " + (i + 1) + "/" + cant);
					cuentaCliente.setMoneda(new Moneda(1l));
					cuentaCliente.setSituacion("CREDITO");
					cuentaCliente.setTipo("E");
					cuentaCliente.setValorPagado(0d);
					cuentaCliente.setValorTotal(venta.getTotalGeneral());
					cuentaCliente.setVencimiento(cal.getTime());
					cuentaCliente.setFecha(new Date());
					cuentaCliente.setObs("Venta crédito de la Factura " + venta.getId());
					cuentaCliente.setUsuarioId(GlobalVars.USER_ID);
					listaCuentaClientes.add(cuentaCliente);
				}
			} else {
				cuentaCliente = new CuentaCliente();
				cuentaCliente.setCliente(venta.getCliente());
				cuentaCliente.setClienteNombre(venta.getClienteNombre());
				cuentaCliente.setCredito(venta.getTotalGeneral());
				cuentaCliente.setDebito(0d);
				cuentaCliente.setDocumento("Venta crédito a " + tfCondicionPago.getText() + " días");
				cuentaCliente.setMoneda(new Moneda(1l));
				cuentaCliente.setSituacion("CREDITO");
				cuentaCliente.setTipo("S");
				cuentaCliente.setValorPagado(0d);
				cuentaCliente.setValorTotal(venta.getTotalGeneral());
				cuentaCliente.setVencimiento(new Date("" + tfVence.getText().toString()));
				cuentaCliente.setFecha(new Date());
				cuentaCliente.setClienteNombre(venta.getClienteNombre());
				cuentaCliente.setObs("Venta crédito de la Factura " + venta.getId());
				cuentaCliente.setUsuarioId(GlobalVars.USER_ID);
				listaCuentaClientes.add(cuentaCliente);
			}
			for (CuentaCliente cuentaP : listaCuentaClientes) {
				cuentaClienteService.save(cuentaP);
			}
		}
		
	}

	private void anular() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "CONFIRMAR", "AVISO - AGROPROGRESO",
				JOptionPane.OK_CANCEL_OPTION);
		if (respuesta == 0) {
			if(!ventaSeleccionado.getSituacion().equalsIgnoreCase("ANULADO")) {
				updateStockProductRemoved(ventaSeleccionado.getItems());

				if (conf != null && conf.getHabilitaLanzamientoCaja() == 0)
					removeMovCajaCuentaCliente(ventaSeleccionado);

				ventaSeleccionado.setSituacion("ANULADO");
				ventaService.save(ventaSeleccionado);
				newVenta();
			}
		}
		tfProductoID.requestFocus();
	}

	private void lanzamientoCaja(Venta v) {
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
			openMovCajaCuentaCliente(ca, v);

		}
	}

	private void openMovCajaCuentaCliente(Caja caja, Venta venta) {
		int cant = 0;
		// cierre de caja del dia anterio
		MovimientoCaja movCaja = new MovimientoCaja();
		movCaja.setCaja(caja);
		movCaja.setFecha(new Date());
		movCaja.setMoneda(new Moneda(1l));
		movCaja.setNotaNro(venta.getId().toString());
		movCaja.setNotaReferencia(venta.getClienteNombre());
		movCaja.setNotaValor(venta.getTotalGeneral());
		movCaja.setPlanCuentaId(1);
		movCaja.setTipoOperacion("E");
		movCaja.setUsuario(GlobalVars.USER_ID);
		movCaja.setValorM01(venta.getTotalGeneral());
		if (tfCondicionPago.getText().equalsIgnoreCase("0")) {
			movCaja.setObs("Pagado en caja 01 ");
			movCaja.setSituacion("PAGADO");
		} else if (tfCondicionPago.getText().equalsIgnoreCase("100") && !tfCuotaCant.getText().isEmpty()) {
			cant = Integer.valueOf(tfCuotaCant.getText());
			movCaja.setObs("Crédito a cuotas :" + cant);
			movCaja.setSituacion("PROCESADO");
		} else {
			movCaja.setObs("Crédito a " + tfCondicionPago.getText() + " días");
			movCaja.setSituacion("PROCESADO");
		}
		pagoService.save(movCaja);
	}

	private void removeMovCajaCuentaCliente(Venta venta) {
		int cant = 0;
		// cierre de caja del dia anterio
		Optional <MovimientoCaja> movimientoCaja= pagoService.findByIdVenta(venta.getId().toString());
		if(movimientoCaja.isPresent()) {
			MovimientoCaja mc= movimientoCaja.get();
			mc.setObs("VENTA ANULADO");
			mc.setSituacion("ANULADO");
			pagoService.save(mc);
		}

		CuentaCliente cuentaCliente = new CuentaCliente();
		List<CuentaCliente> listaCuentaClientes = new ArrayList<CuentaCliente>();
		if (cant > 0) {
			Double valorTotal = venta.getTotalGeneral() / cant;
			Calendar cal = Calendar.getInstance();
			for (int i = 0; i < cant; i++) {
				cal.add(Calendar.MONTH, 1);
				cuentaCliente = new CuentaCliente();
				cuentaCliente.setCliente(venta.getCliente());
				cuentaCliente.setClienteNombre(venta.getClienteNombre());
				cuentaCliente.setCredito(valorTotal);
				cuentaCliente.setDebito(0d);
				cuentaCliente.setDocumento("Venta crédito cuota " + (i + 1) + "/" + cant);
				cuentaCliente.setMoneda(new Moneda(1l));
				cuentaCliente.setSituacion("CREDITO");
				cuentaCliente.setTipo("E");
				cuentaCliente.setValorPagado(0d);
				cuentaCliente.setValorTotal(venta.getTotalGeneral());
				cuentaCliente.setVencimiento(cal.getTime());
				cuentaCliente.setFecha(new Date());
				cuentaCliente.setObs("Venta crédito de la Factura " + venta.getId());
				cuentaCliente.setUsuarioId(GlobalVars.USER_ID);
				listaCuentaClientes.add(cuentaCliente);
			}
		} else {
			cuentaCliente = new CuentaCliente();
			cuentaCliente.setCliente(venta.getCliente());
			cuentaCliente.setClienteNombre(venta.getClienteNombre());
			cuentaCliente.setCredito(venta.getTotalGeneral());
			cuentaCliente.setDebito(0d);
			cuentaCliente.setDocumento("Venta crédito a " + tfCondicionPago.getText() + " días");
			cuentaCliente.setMoneda(new Moneda(1l));
			cuentaCliente.setSituacion("CREDITO");
			cuentaCliente.setTipo("S");
			cuentaCliente.setValorPagado(0d);
			cuentaCliente.setValorTotal(venta.getTotalGeneral());
			cuentaCliente.setVencimiento(new Date("" + tfVence.getText().toString()));
			cuentaCliente.setFecha(new Date());
			cuentaCliente.setClienteNombre(venta.getClienteNombre());
			cuentaCliente.setObs("Venta crédito de la Factura " + venta.getId());
			cuentaCliente.setUsuarioId(GlobalVars.USER_ID);
			listaCuentaClientes.add(cuentaCliente);
		}
		for (CuentaCliente cuentaP : listaCuentaClientes) {
			cuentaClienteService.save(cuentaP);
		}
	}

	private ImpresionPanel panel = null;

	private void imprimirDialogo() {
		if (this.panel == null) {
			panel = new ImpresionPanel();
			panel.setPanelInterfaz(this);
		}

		panel.setVisible(true);
	}

	private void removeItemBloq() {
		// remueve los Items bloqueados
		for (VentaDetalle item : itemTableModel.getEntities()) {
			int depositoId = Integer.parseInt(tfDepositoID.getText());
			removeItemDepBloq(item.getCantidad(), item.getProductoId(), depositoId);
		}
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

		if (!tfClienteID.getText().equalsIgnoreCase("999")) {
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
		if (tfCondicionPago.getText().equalsIgnoreCase("100")) {
			if (tfCuotaCant.getText().isEmpty() || Integer.valueOf(tfCuotaCant.getText()) <= 0) {
				Notifications.showAlert("La cantidad de cuota debe ser mayor a 0(cero) !");
				return false;
			}
		}

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
		long max = ventaService.getRowCount();
		tfVentaId.setText(String.valueOf(max + 1));
		Optional<Configuracion> config = configService.findByEmpresaId(new Empresa(GlobalVars.EMPRESA_ID));
		if (config.isPresent())
			this.conf = config.get();

		if (conf.getPideVendedor() == 0) {
			Optional<Usuario> usuario = vendedorService.findById(GlobalVars.USER_ID);
			tfVendedorID.setText(usuario.get().getId().toString());
			tfVendedor.setText(usuario.get().getUsuario());
		}
		if (conf.getPideDeposito() == 0) {
			Optional<Deposito> deposito = depositoService.findById(GlobalVars.DEPOSITO_ID);
			tfDepositoID.setText(String.valueOf(deposito.get().getId()));
			tfDeposito.setText(deposito.get().getNombre());
		}
		resetCliente();
		tfClienteID.requestFocus();
		tfCuotaCant.setEnabled(false);
	}

	private void resetCliente() {
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfDvRuc.setText("");
		tfClienteDireccion.setText("");
		tfClienteID.setText("");
	}

	private void findClientById(Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);
		if (cliente.isPresent()) {
			setCliente(cliente.get());
		} else {
			Notifications.showAlert("No existe Cliente con el codigo informado.!");
		}
	}

	private void findClientByRuc(String ciRuc) {
		Optional<Cliente> cliente = Optional.ofNullable(clienteService.findByRuc(ciRuc));
		if (cliente.isPresent()) {
			setCliente(cliente.get());
		} else {
			Notifications.showAlert("Cliente no registrado, al confirmar la venta estará registrado!");
			Optional<ClientePais> clientePais = Optional.of(clientePaisService.findByRuc(ciRuc));
			setClienteSET(clientePais);
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
			if(cliente.getListaPrecio()!=null)
				nivelPrecio = cliente.getListaPrecio().getNombre();
			else
				nivelPrecio = "A";
			if (cliente.getId() == 0) {
				// habilitar nombre, ruc, direccion
				tfClienteNombre.setEnabled(false);
				tfClienteRuc.setEnabled(true);
				tfClienteDireccion.setEnabled(true);
				tfProductoID.requestFocus();

				tfCondicionPago.setEnabled(false);
				tfCondicionPago.setText("0");

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
			tfClienteID.setText("999");
			tfClienteNombre.setText(clientePai.get().getRazonSocial());
			tfClienteRuc.setText(clientePai.get().getCiruc());
			tfClienteDireccion.setText("");
			tfDvRuc.setText(clientePai.get().getDvruc());
			nivelPrecio = "A";
			tfCondicionPago.setEnabled(false);
			tfCondicionPago.setText("0");
			// tfClienteID.setEnabled(false);
			tfClienteNombre.setEnabled(false);
		}
	}

	private void findVendedorById(Long id) {
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
	}

	private void findDepositoById(Long id) {
		Optional<Deposito> deposito = depositoService.findById(id);

		if (deposito.isPresent()) {
			tfDepositoID.setEditable(false);
			tfDepositoID.setText(deposito.get().getId().toString());
			tfDeposito.setText(deposito.get().getNombre());
			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("No existe Deposito con este codigo.!");
		}
	}

	private void findProducto(String id) {
		Optional<Producto> producto = null;
		producto = productoService.findById(Long.valueOf(id));
		if (!producto.isPresent()) {
			if (conf != null && conf.getPermiteVentaPorReferencia() == 1)
				producto = productoService.findByReferencia(id);
		}

		if (producto.isPresent()) {
			setProducto(producto.get());
		} else {
			Notifications.showAlert("No existe producto informado. Verifique por favor.!");
		}
	}

	private void setProducto(Producto producto) {
		if (producto != null) {
			if (producto.getSubgrupo().getTipo().equals("S"))
				isProductService = true;

			precioInicial = setPrecioByCliente(nivelPrecio, producto);
			setProductoSeleccionado(producto);

			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfPrecio.setText(FormatearValor.doubleAString(precioInicial));
			tfCantidad.setText("1");
			tfDescuentoItem.setText(FormatearValor.doubleAString(0d));
			tfCantidad.requestFocus();
		}
	}

	private Double setPrecioByCliente(String nivelPrecio, Producto producto) {
		Double precio = 0D;
		switch (nivelPrecio) {
		case "A":
			precio = producto.getPrecioVentaA();
			break;
		case "B":
			precio = producto.getPrecioVentaB();
			break;
		case "C":
			precio = producto.getPrecioVentaC();
			break;
		case "D":
			precio = producto.getPrecioVentaD();
			break;
		case "E":
			precio = producto.getPrecioVentaE();
			break;

		default:
			break;
		}

		return precio;
	}

	private void addItemCantBloq(Long productoId, Double cantidad, Double cantAnterior, int depositoId) {
		Optional<Producto> p = productoService.findById(productoId);

		if (p.isPresent()) {
			Producto producto = p.get();

			switch (depositoId) {
			case 1:
				Double stockDep01 = p.get().getDepO1() != null ? p.get().getDepO1() : 0;
				Double stockDepBloq = p.get().getDepO1Bloq() != null ? p.get().getDepO1Bloq() : 0;

				if (stockDep01 >= cantidad) {
					producto.setDepO1Bloq((stockDepBloq + cantidad) - cantAnterior);
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

			productoService.save(producto);
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
		if (isValidItem() && validateCantidad()) {
			Long productoId = tfProductoID.getText().isEmpty() ? 1 : Long.valueOf(tfProductoID.getText());
			int depositoId = tfDepositoID.getText().isEmpty() ? 1 : Integer.parseInt(tfDepositoID.getText());
			Double cantidad = tfCantidad.getText().isEmpty() ? 0 : FormatearValor.stringADouble(tfCantidad.getText());

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
		}
	}

	private void removeItem() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			VentaDetalle item = itemTableModel.getEntityByRow(selectedRow);
			itemTableModel.removeRow(selectedRow);

			if (!isProductService) {
				removeItemDepBloq(item.getCantidad(), item.getProductoId(), Integer.parseInt(tfDepositoID.getText()));
			}

			clearItem();
			calculateItem();

			isProductService = false;
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
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
			tfCondicionPago.setText(String.valueOf(condicionPago.getCantDia()));

//			if (conf != null && conf.getPideFlete() == 1)
//				tfFlete.requestFocus();
//			else 
			if (conf != null && conf.getPideDescuento() == 1)
				tfDescuento.requestFocus();
			else
				tfObs.requestFocus();
		}
	}

	@Override
	public void imprimirTicket() {
		ImpresionUtil.performTicket(itemTableModel.getEntities(),
				tfCondicionPago.getText().equals("0") ? "CONTADO" : "CREDITO", tfVentaId.getText(), tfTotal.getText());
		clearForm();
	}

	@Override
	public void imprimirNota() {
		ImpresionUtil.performNota(itemTableModel.getEntities(),
				tfCondicionPago.getText().equals("0") ? "CONTADO" : "CREDITO", tfVentaId.getText(), tfTotal.getText());
		clearForm();
	}

	@Override
	public void imprimirFactura() {
		ImpresionUtil.performFactura(tfClienteNombre.getText(), tfClienteRuc.getText() + "-" + tfDvRuc.getText(),
				"0911 111 222", tfClienteDireccion.getText(), tfVentaId.getText(),
				Integer.valueOf(tfCondicionPago.getText()),
				tfVendedor.getText().isEmpty() ? GlobalVars.USER : tfVendedor.getText(), tfTotal.getText(), "0", "0",
				"0", "0", "0", itemTableModel.getEntities());
		clearForm();
	}

	@Override
	public void cancelarImpresion() {
		clearForm();
	}
}
