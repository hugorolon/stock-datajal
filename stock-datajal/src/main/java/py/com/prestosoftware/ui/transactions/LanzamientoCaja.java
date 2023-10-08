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

import javax.swing.JButton;
import javax.swing.JDialog;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.Cotizacion;
import py.com.prestosoftware.data.models.CuentaProveedor;
import py.com.prestosoftware.data.models.Devolucion;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.CotizacionService;
import py.com.prestosoftware.domain.services.CuentaProveedorService;
import py.com.prestosoftware.domain.services.DevolucionService;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.PlanCuentaService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.CellRendererOthers;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.CreditoDebitoDialog;
//import py.com.prestosoftware.ui.search.CuentaClienteInterfaz;
import py.com.prestosoftware.ui.search.CuentaProveedorInterfaz;
import py.com.prestosoftware.ui.search.PlanCuentaDialog;
import py.com.prestosoftware.ui.search.PlanCuentaInterfaz;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.table.CotizacionTableModel;
import py.com.prestosoftware.ui.table.EntradaCajaTableModel;
import py.com.prestosoftware.ui.table.MonedaValorTableModel;
import py.com.prestosoftware.ui.table.MovimientoCajaTableModel;
import py.com.prestosoftware.ui.table.NotaLanzadaTableModel;
import py.com.prestosoftware.ui.table.NotaTableModel;
import py.com.prestosoftware.ui.viewmodel.MonedaValor;
import py.com.prestosoftware.ui.viewmodel.Nota;
import py.com.prestosoftware.util.Notifications;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@Component
public class LanzamientoCaja extends JFrame implements PlanCuentaInterfaz, ClienteInterfaz, 
	ProveedorInterfaz, CuentaProveedorInterfaz  {

	private static final long serialVersionUID = 1L;

	private static final int OPERACION_VENTA = 1;
	private static final int OPERACION_COMPRA = 2;
	private static final int OPERACION_COBRO_CLI = 3;
	private static final int OPERACION_DEVOLUCION_CLI = 4;
	private static final int OPERACION_DEVOLUCION_PROV = 5;
	private static final int OPERACION_PAGO_PROV = 6;

	private JLabel tfTotalGs;
	private JLabel tfTotalRs, tfTotalUs;
	private JTextField tfVueltoGs, tfVueltoRs, tfVueltoUs, tfCajaID, tfOperacionID, tfNotaNombre, tfNotaValor, tfNotaNro;
	private JFormattedTextField tfFecha;
	private JLabel lblOperacionNombre;
	private JTable tbLanzamientos, tbNotasPendientes, tbCotizacion, tbNotasLanzadas;
	private JButton btnAdd, btnRemove, btnGuardar;
	private JTextField tfTotalNota, tfRecibidoGs, tfRecibidoRs, tfRecibidoUs;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollNotasLanzadas;
	private JPanel pnlEntradaPago, pnlNotasPendientes, pnlNotasLanzadas;

	private MovimientoCajaTableModel tableModel;
	private NotaTableModel notaModel;
	private EntradaCajaTableModel entradaCajaModel;
	private NotaLanzadaTableModel notasLanzadasModel;
	private CotizacionTableModel cotizacionModel;
	private MonedaValorTableModel monedaValorModel;
	
	private PlanCuentaDialog cuentaDialog;
	private CreditoDebitoDialog creditoDebitoDialog;

	private VentaService ventaService;
	private CompraService compraService;
	private DevolucionService devolucionService;
	private MovimientoCajaService pagoService;
	//private CuentaClienteService cuentaClienteService;
	private CuentaProveedorService cuentaProveedorService;
	private ProductoService productoService;
	private PlanCuentaService planService;
	private MonedaService monedaService;
	private CajaService cajaService;
	private CotizacionService cotizacionService;
	private AperturaCierreCajaService movCajaService;
	private ClienteService clienteService;
	private ProveedorService proveedorService;

	@Autowired
	public LanzamientoCaja(VentaService ventaService, CompraService compraService, CajaService cajaService,
			PlanCuentaService planService, MovimientoCajaService pagoService, CotizacionService cotizacionService,
			MovimientoCajaTableModel pagoTableModel, NotaTableModel notaModel, MonedaService monedaService,
			//CuentaClienteService cuentaClienteService, 
			CuentaProveedorService cuentaProveedorService,
			AperturaCierreCajaService movCajaService, DevolucionService devolucionService,
			CotizacionTableModel cotizacionModel, NotaLanzadaTableModel notasLanzadasModel,
			PlanCuentaDialog cuentaDialog, ProductoService productoService, EntradaCajaTableModel entradaCajaModel,
			MonedaValorTableModel monedaValorModel, ClienteService clienteService, ProveedorService proveedorService,
			CreditoDebitoDialog creditoDebitoDialog) {
		this.ventaService = ventaService;
		this.compraService = compraService;
		this.cajaService = cajaService;
		this.planService = planService;
		this.pagoService = pagoService;
		this.cotizacionService = cotizacionService;
		this.tableModel = pagoTableModel;
		this.notaModel = notaModel;
		this.monedaService = monedaService;
		//this.cuentaClienteService = cuentaClienteService;
		this.cuentaProveedorService = cuentaProveedorService;
		this.movCajaService = movCajaService;
		this.devolucionService = devolucionService;
		this.notasLanzadasModel = notasLanzadasModel;
		this.cotizacionModel = cotizacionModel;
		this.cuentaDialog = cuentaDialog;
		this.productoService = productoService;
		this.entradaCajaModel = entradaCajaModel;
		this.monedaValorModel = monedaValorModel;
		this.clienteService = clienteService;
		this.proveedorService = proveedorService;
		this.creditoDebitoDialog = creditoDebitoDialog;

		initProperties();
		initComponents();
	}
	
	private void initProperties() {
		setSize(1009, 663);
		setTitle("LANZAMIENTO DE CAJA");
		setLocationRelativeTo(null);
		setResizable(false);
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		getContentPane().setLayout(null);
		pnlEntradaPago = new JPanel();
		pnlEntradaPago.setBounds(6, 81, 968, 498);
		getContentPane().add(pnlEntradaPago);
		pnlEntradaPago.setLayout(null);

		JLabel lblOperacion = new JLabel("OPER.");
		lblOperacion.setBounds(6, 169, 63, 16);
		pnlEntradaPago.add(lblOperacion);

		JLabel lblReferencia = new JLabel("REFERENCIA");
		lblReferencia.setBounds(152, 169, 182, 16);
		pnlEntradaPago.add(lblReferencia);

		JLabel lblValor = new JLabel("VALOR");
		lblValor.setBounds(645, 169, 85, 16);
		pnlEntradaPago.add(lblValor);

		tfNotaNombre = new JTextField();
		tfNotaNombre.setBounds(152, 186, 481, 30);
		tfNotaNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfNotaValor.setEditable(true);
					tfNotaValor.requestFocus();
				}
			}
		});
		tfNotaNombre.setEditable(false);
		tfNotaNombre.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNotaNombre.setColumns(10);
		pnlEntradaPago.add(tfNotaNombre);

		tfNotaValor = new JTextField();
		tfNotaValor.setBounds(643, 186, 103, 30);
		tfNotaValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAdd.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfNotaValor.setEditable(false);
		tfNotaValor.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNotaValor.setColumns(10);
		pnlEntradaPago.add(tfNotaValor);

		tfOperacionID = new JTextField();
		tfOperacionID.setBounds(6, 186, 63, 30);
		tfOperacionID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfOperacionID.selectAll();
			}
		});
		tfOperacionID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tfOperacionID.getText()!=""&&!tfOperacionID.getText().isEmpty()) {
						findOperacionById(tfOperacionID.getText());
					} else {
						openPopup(0);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F3) {
					openPopup(0);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfRecibidoUs.requestFocus();
					tfRecibidoUs.selectAll();
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					getNotasPendientes();

					if (!tfCajaID.getText().isEmpty()) {
						getNotasLanzadas(Long.valueOf(tfCajaID.getText()));
					}

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfNotaNro.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfOperacionID.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlEntradaPago.add(tfOperacionID);
		tfOperacionID.setColumns(10);

		btnRemove = new JButton(" - ");
		btnRemove.setBounds(902, 186, 48, 30);
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
		pnlEntradaPago.add(btnRemove);

		JScrollPane scrollLanzamiento = new JScrollPane();
		scrollLanzamiento.setBounds(6, 218, 952, 104);
		pnlEntradaPago.add(scrollLanzamiento);

		tbLanzamientos = new JTable(tableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		tbLanzamientos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getSelectedItem();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfRecibidoGs.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});
		tbLanzamientos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getSelectedItem();
			}
		});

		Util.ocultarColumna(tbLanzamientos, 6);

		tbLanzamientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbLanzamientos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
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
		tbLanzamientos.getColumnModel().getColumn(0).setHeaderRenderer(alignRendererHeaderCenter);
		tbLanzamientos.getColumnModel().getColumn(0).setPreferredWidth(50);
		tbLanzamientos.getColumnModel().getColumn(0).setCellRenderer(alignRendererLeft);
		tbLanzamientos.getColumnModel().getColumn(1).setHeaderRenderer(alignRendererHeaderCenter);
		tbLanzamientos.getColumnModel().getColumn(1).setPreferredWidth(50);
		tbLanzamientos.getColumnModel().getColumn(1).setCellRenderer(alignRendererRight);
		tbLanzamientos.getColumnModel().getColumn(2).setHeaderRenderer(alignRendererHeaderCenter);
		tbLanzamientos.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbLanzamientos.getColumnModel().getColumn(2).setCellRenderer(alignRendererLeft);
		tbLanzamientos.getColumnModel().getColumn(3).setHeaderRenderer(alignRendererHeaderCenter);
		tbLanzamientos.getColumnModel().getColumn(3).setPreferredWidth(280);
		tbLanzamientos.getColumnModel().getColumn(3).setCellRenderer(alignRendererRight);
		tbLanzamientos.getColumnModel().getColumn(4).setHeaderRenderer(alignRendererHeaderCenter);
		tbLanzamientos.getColumnModel().getColumn(4).setPreferredWidth(50);
		tbLanzamientos.getColumnModel().getColumn(4).setCellRenderer(alignRendererRight);
		tbLanzamientos.getColumnModel().getColumn(5).setHeaderRenderer(alignRendererHeaderCenter);
		tbLanzamientos.getColumnModel().getColumn(5).setPreferredWidth(50);
		tbLanzamientos.getColumnModel().getColumn(5).setCellRenderer(alignRendererRight);
		
		
		
		scrollLanzamiento.setViewportView(tbLanzamientos);

		tfNotaNro = new JTextField();
		tfNotaNro.setBounds(68, 186, 79, 30);
		tfNotaNro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfNotaNro.selectAll();
			}
		});
		tfNotaNro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfNotaNro.getText().isEmpty()) {
						if (!tfOperacionID.getText().isEmpty()) {
							findNotaById(Integer.parseInt(tfOperacionID.getText()), Long.valueOf(tfNotaNro.getText()));
							tfObs.requestFocus();
						} else {
							Notifications.showAlert("Debes digitar el codigo de la OPER.");
							tfOperacionID.requestFocus();
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfOperacionID.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfNotaNro.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNotaNro.setColumns(10);
		pnlEntradaPago.add(tfNotaNro);

		JLabel lblDocumento = new JLabel("DOC./NOTA");
		lblDocumento.setBounds(68, 169, 85, 16);
		pnlEntradaPago.add(lblDocumento);

		btnAdd = new JButton(" + ");
		btnAdd.setBounds(844, 186, 48, 30);
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
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		pnlEntradaPago.add(btnAdd);

		lblOperacionNombre = new JLabel("");
		lblOperacionNombre.setBounds(41, 301, 97, 30);
		lblOperacionNombre.setForeground(Color.RED);
		pnlEntradaPago.add(lblOperacionNombre);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 325, 738, 171);
		pnlEntradaPago.add(scrollPane);

		pnlTotales = new JPanel();
		pnlTotales.setLocation(0, 330);
		scrollPane.setViewportView(pnlTotales);
		pnlTotales.setLayout(null);

		JLabel lblRs = new JLabel("RS");
		lblRs.setFont(new Font("Verdana", Font.BOLD, 14));
		lblRs.setBounds(6, 97, 35, 30);
		pnlTotales.add(lblRs);

		tfTotalRs = new JLabel();
		tfTotalRs.setBounds(43, 97, 121, 30);
		tfTotalRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalRs.setText("0");
		pnlTotales.add(tfTotalRs);
		tfTotalRs.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblUs = new JLabel("US");
		lblUs.setFont(new Font("Verdana", Font.BOLD, 14));
		lblUs.setBounds(6, 63, 35, 30);
		pnlTotales.add(lblUs);

		tfTotalUs = new JLabel();
		tfTotalUs.setBounds(43, 63, 121, 30);
		tfTotalUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalUs.setText("0");
		pnlTotales.add(tfTotalUs);
		tfTotalUs.setFont(new Font("Arial", Font.PLAIN, 20));

		tfRecibidoRs = new JTextField();
		tfRecibidoRs.setBounds(331, 97, 121, 30);
		tfRecibidoRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoRs.setText("0");
		tfRecibidoRs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoRs.selectAll();
			}
		});
		tfRecibidoRs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfRecibidoRs.getText().isEmpty()) {
						if (!tfRecibidoRs.getText().equals("0")) {
							Double total = FormatearValor.stringADouble(tfTotalRs.getText());
							Double recibido = FormatearValor.stringADouble(tfRecibidoRs.getText());
							calculateVuelto(total, recibido, mRs);

							tfRecibidoPs.selectAll();
							tfRecibidoPs.requestFocus();
						} else {
							tfRecibidoPs.requestFocus();
						}
					} else {
						Notifications.showAlert("Debes digitar un valor.!");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
//    			if (tfRecibidoRs.getText().length() > 3) {
//    				tfRecibidoRs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoRs.getText())));
//    			}
			}
		});
		tfRecibidoRs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfRecibidoRs.setColumns(10);
		pnlTotales.add(tfRecibidoRs);

		tfRecibidoUs = new JTextField();
		tfRecibidoUs.setBounds(331, 63, 121, 30);
		tfRecibidoUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoUs.setText("0");
		tfRecibidoUs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoUs.selectAll();
			}
		});
		tfRecibidoUs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfRecibidoUs.getText().isEmpty()) {
						if (!tfRecibidoUs.getText().equals("0")) {
							Double total = FormatearValor.stringADouble(tfTotalUs.getText());
							Double recibido = FormatearValor.stringADouble(tfRecibidoUs.getText());
							calculateVuelto(total, recibido, mDolar);

							tfRecibidoRs.selectAll();
							tfRecibidoRs.requestFocus();
						} else {
							tfRecibidoRs.requestFocus();
						}

						calcularValores();

					} else {
						Notifications.showAlert("Debes digitar un valor");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
//    			if (tfRecibidoUs.getText().length() > 3) {
//    				tfRecibidoUs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoUs.getText())));
//    			}
			}
		});
		tfRecibidoUs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfRecibidoUs.setColumns(10);
		pnlTotales.add(tfRecibidoUs);

		tfVueltoRs = new JTextField();
		tfVueltoRs.setBounds(464, 97, 121, 30);
		tfVueltoRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoRs.setText("0");
		tfVueltoRs.setEditable(false);
		tfVueltoRs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfVueltoRs.setColumns(10);
		pnlTotales.add(tfVueltoRs);

		tfVueltoUs = new JTextField();
		tfVueltoUs.setBounds(464, 63, 121, 30);
		tfVueltoUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoUs.setText("0");
		tfVueltoUs.setEditable(false);
		tfVueltoUs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfVueltoUs.setColumns(10);
		pnlTotales.add(tfVueltoUs);

		JLabel lblTotales = new JLabel("A PAGAR");
		lblTotales.setBounds(43, 4, 81, 21);
		pnlTotales.add(lblTotales);

		JLabel lblRecibido = new JLabel("VALOR RECIBIDO");
		lblRecibido.setBounds(331, 4, 121, 21);
		pnlTotales.add(lblRecibido);

		JLabel lblVuelto = new JLabel("VUELTO");
		lblVuelto.setBounds(464, 4, 81, 21);
		pnlTotales.add(lblVuelto);

		lblPs = new JLabel("PS");
		lblPs.setFont(new Font("Verdana", Font.BOLD, 14));
		lblPs.setBounds(6, 131, 35, 30);
		pnlTotales.add(lblPs);

		tfTotalPs = new JLabel();
		tfTotalPs.setBounds(43, 131, 121, 30);
		tfTotalPs.setText("0");
		tfTotalPs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalPs.setFont(new Font("Arial", Font.PLAIN, 20));
		pnlTotales.add(tfTotalPs);

		tfRecibidoPs = new JTextField();
		tfRecibidoPs.setBounds(331, 131, 121, 30);
		tfRecibidoPs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoPs.selectAll();
			}
		});
		tfRecibidoPs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfRecibidoPs.getText().isEmpty()) {
						if (!tfRecibidoPs.getText().equals("0")) {
							Double totalPs = FormatearValor.stringADouble(tfTotalPs.getText());
							Double recibidoPs = FormatearValor.stringADouble(tfRecibidoPs.getText());
							calculateVuelto(totalPs, recibidoPs, mPs);

							tfRecibidoGs.selectAll();
							tfRecibidoGs.requestFocus();
						} else {
							tfRecibidoGs.requestFocus();
						}
					} else {
						Notifications.showAlert("Debes digitar un valor.!");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfRecibidoPs.setText("0");
		tfRecibidoPs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoPs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfRecibidoPs.setColumns(10);
		pnlTotales.add(tfRecibidoPs);

		tfVueltoPs = new JTextField();
		tfVueltoPs.setBounds(464, 131, 121, 30);
		tfVueltoPs.setText("0");
		tfVueltoPs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoPs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfVueltoPs.setEditable(false);
		tfVueltoPs.setColumns(10);
		pnlTotales.add(tfVueltoPs);

		lblValorFaltanteUs = new JLabel("");
		lblValorFaltanteUs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorFaltanteUs.setBounds(181, 63, 142, 30);
		pnlTotales.add(lblValorFaltanteUs);

		lblValorFaltantePs = new JLabel("");
		lblValorFaltantePs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorFaltantePs.setBounds(181, 131, 142, 30);
		pnlTotales.add(lblValorFaltantePs);

		lblValorFaltanteRs = new JLabel("");
		lblValorFaltanteRs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorFaltanteRs.setBounds(181, 97, 142, 30);
		pnlTotales.add(lblValorFaltanteRs);

		JLabel lblGs = new JLabel("GS");
		lblGs.setFont(new Font("Verdana", Font.BOLD, 14));
		lblGs.setBounds(6, 29, 35, 30);
		pnlTotales.add(lblGs);

		tfTotalGs = new JLabel();
		tfTotalGs.setBounds(43, 29, 121, 30);
		pnlTotales.add(tfTotalGs);
		tfTotalGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalGs.setText("0");
		tfTotalGs.setFont(new Font("Arial", Font.PLAIN, 20));

		lblValorFaltanteGs = new JLabel("");
		lblValorFaltanteGs.setBounds(181, 27, 142, 30);
		pnlTotales.add(lblValorFaltanteGs);
		lblValorFaltanteGs.setHorizontalAlignment(SwingConstants.RIGHT);

		tfRecibidoGs = new JTextField();
		tfRecibidoGs.setBounds(331, 29, 121, 30);
		pnlTotales.add(tfRecibidoGs);
		tfRecibidoGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoGs.setText("0");
		tfRecibidoGs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoGs.selectAll();
			}
		});
		tfRecibidoGs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfRecibidoGs.getText().isEmpty()) {
						if (!tfRecibidoGs.getText().equals("0")) {
							Double total = FormatearValor.stringADouble(tfTotalGs.getText());
							Double recibido = FormatearValor.stringADouble(tfRecibidoGs.getText());
							calculateVuelto(total, recibido, mGs);
							btnGuardar.requestFocus();
							
							calcularValores();
						} else {
							btnGuardar.requestFocus();
						}
					} else {
						Notifications.showAlert("Debes digitar un valor.!");
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
//    			if (tfRecibidoGs.getText().length() > 3) {
//    				tfRecibidoGs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoGs.getText())));
//    			}
			}
		});
		tfRecibidoGs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfRecibidoGs.setColumns(10);

		tfVueltoGs = new JTextField();
		tfVueltoGs.setBounds(464, 29, 121, 30);
		pnlTotales.add(tfVueltoGs);
		tfVueltoGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoGs.setText("0");
		tfVueltoGs.setEditable(false);
		tfVueltoGs.setFont(new Font("Arial", Font.PLAIN, 20));
		tfVueltoGs.setColumns(10);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 12, 952, 152);
		pnlEntradaPago.add(tabbedPane);

//		panel = new JPanel();
//		tabbedPane.addTab("Pagos", null, panel, null);
//		panel.setLayout(null);

//		lblMoneda = new JLabel("Moneda:");
//		lblMoneda.setBounds(12, 12, 69, 32);
//		panel.add(lblMoneda);

//		tfMonedaId = new JTextField();
//		tfMonedaId.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					findMoneda();
//				}
//			}
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//				Util.validateNumero(e);
//			}
//		});
//		tfMonedaId.setFont(new Font("Arial", Font.PLAIN, 14));
//		tfMonedaId.setColumns(10);
//		tfMonedaId.setBounds(84, 12, 124, 32);
		//panel.add(tfMonedaId);

//		lblNombreMoneda = new JLabel("");
//		lblNombreMoneda.setFont(new Font("Verdana", Font.BOLD, 14));
//		lblNombreMoneda.setForeground(Color.RED);
//		lblNombreMoneda.setBounds(12, 66, 196, 32);
//		panel.add(lblNombreMoneda);

		pnlNotasPendientes = new JPanel();
		tabbedPane.addTab("Notas Pendientes", null, pnlNotasPendientes, null);
		pnlNotasPendientes.setLayout(null);

		JScrollPane scrollNotasPendientes = new JScrollPane();
		scrollNotasPendientes.setBounds(6, 6, 931, 115);
		pnlNotasPendientes.add(scrollNotasPendientes);

		tbNotasPendientes = new JTable(notaModel);
		tbNotasPendientes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// if (e.getKeyCode() == KeyEvent.VK_F10) {
//					Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA CANCELACIÓN");
//					if (respuesta == 0) {
				cancelarNota();
//					}
				// }
			}
		});
		tbNotasPendientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbNotasPendientes.setDefaultRenderer(Object.class, new CellRendererOperaciones());
//		DefaultTableCellRenderer alignRendererHeaderCenter= new CellRendererOthers();
//		alignRendererHeaderCenter.setBackground(getBackground());
//		alignRendererHeaderCenter.setHorizontalAlignment(SwingConstants.CENTER);
//		DefaultTableCellRenderer alignRendererHeaderLeft= new CellRendererOthers();
//		alignRendererHeaderLeft.setHorizontalAlignment(SwingConstants.LEFT);
//		DefaultTableCellRenderer alignRendererLeft= new CellRendererOthers();
//		alignRendererLeft.setBackground(getBackground());
//		alignRendererLeft.setHorizontalAlignment(SwingConstants.LEFT);
//		DefaultTableCellRenderer alignRendererRight= new CellRendererOthers();
//		alignRendererRight.setBackground(getBackground());
//		alignRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		tbNotasPendientes.getTableHeader().setOpaque(false);
		tbNotasPendientes.getTableHeader().setBackground(new Color(225, 251, 234));
		tbNotasPendientes.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 18));
		tbNotasPendientes.getColumnModel().getColumn(0).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasPendientes.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbNotasPendientes.getColumnModel().getColumn(0).setCellRenderer(alignRendererLeft);
		tbNotasPendientes.getColumnModel().getColumn(1).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasPendientes.getColumnModel().getColumn(1).setPreferredWidth(105);
		tbNotasPendientes.getColumnModel().getColumn(1).setCellRenderer(alignRendererRight);
		tbNotasPendientes.getColumnModel().getColumn(2).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasPendientes.getColumnModel().getColumn(2).setPreferredWidth(385);
		tbNotasPendientes.getColumnModel().getColumn(2).setCellRenderer(alignRendererLeft);
		tbNotasPendientes.getColumnModel().getColumn(3).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasPendientes.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbNotasPendientes.getColumnModel().getColumn(3).setCellRenderer(alignRendererRight);
		
		scrollNotasPendientes.setViewportView(tbNotasPendientes);

		pnlNotasLanzadas = new JPanel();
		tabbedPane.addTab("Notas Lanzadas", null, pnlNotasLanzadas, null);
		pnlNotasLanzadas.setLayout(null);

		scrollNotasLanzadas = new JScrollPane();
		scrollNotasLanzadas.setBounds(6, 6, 931, 115);
		pnlNotasLanzadas.add(scrollNotasLanzadas);

		tbNotasLanzadas = new JTable(notasLanzadasModel);
		tbNotasLanzadas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F10) {
//					Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA CANCELACIÓN");
//					if (respuesta == 0) {
					System.out.println("paso aqui. en F10");
					cancelarNota(); // verificar este codigo
					getNotasLanzadas(Long.valueOf(tfCajaID.getText()));
					// getNotasPendientes();
//					}
				}
			}
		});
		tbNotasLanzadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbNotasLanzadas.setDefaultRenderer(Object.class, new CellRendererOperaciones());
//		DefaultTableCellRenderer alignRendererHeaderCenter= new CellRendererOthers();
//		alignRendererHeaderCenter.setBackground(getBackground());
//		alignRendererHeaderCenter.setHorizontalAlignment(SwingConstants.CENTER);
//		DefaultTableCellRenderer alignRendererHeaderLeft= new CellRendererOthers();
//		alignRendererHeaderLeft.setHorizontalAlignment(SwingConstants.LEFT);
//		DefaultTableCellRenderer alignRendererLeft= new CellRendererOthers();
//		alignRendererLeft.setBackground(getBackground());
//		alignRendererLeft.setHorizontalAlignment(SwingConstants.LEFT);
//		DefaultTableCellRenderer alignRendererRight= new CellRendererOthers();
//		alignRendererRight.setBackground(getBackground());
//		alignRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		tbNotasLanzadas.getTableHeader().setOpaque(false);
		tbNotasLanzadas.getTableHeader().setBackground(new Color(225, 251, 234));
		tbNotasLanzadas.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 18));
		tbNotasLanzadas.getColumnModel().getColumn(0).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasLanzadas.getColumnModel().getColumn(0).setPreferredWidth(100);
		tbNotasLanzadas.getColumnModel().getColumn(0).setCellRenderer(alignRendererLeft);
		tbNotasLanzadas.getColumnModel().getColumn(1).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasLanzadas.getColumnModel().getColumn(1).setPreferredWidth(55);
		tbNotasLanzadas.getColumnModel().getColumn(1).setCellRenderer(alignRendererLeft);
		tbNotasLanzadas.getColumnModel().getColumn(2).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasLanzadas.getColumnModel().getColumn(2).setPreferredWidth(55);
		tbNotasLanzadas.getColumnModel().getColumn(2).setCellRenderer(alignRendererRight);
		tbNotasLanzadas.getColumnModel().getColumn(3).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasLanzadas.getColumnModel().getColumn(3).setPreferredWidth(300);
		tbNotasLanzadas.getColumnModel().getColumn(3).setCellRenderer(alignRendererLeft);
		tbNotasLanzadas.getColumnModel().getColumn(4).setHeaderRenderer(alignRendererHeaderCenter);
		tbNotasLanzadas.getColumnModel().getColumn(4).setPreferredWidth(55);
		tbNotasLanzadas.getColumnModel().getColumn(4).setCellRenderer(alignRendererLeft);
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(tabbedPane.getSelectedIndex()==0) {
	            	getNotasPendientes();
	            }	            	
	            else {
	            	getNotasLanzadas(Long.valueOf(tfCajaID.getText()));
	            }
	        }
	    });
		scrollNotasLanzadas.setViewportView(tbNotasLanzadas);

		//panelValorCaja = new JPanel();
		//tabbedPane.addTab("Valores en Caja", null, panelValorCaja, null);
		//panelValorCaja.setLayout(null);

//		scrollPaneEntradaCaja = new JScrollPane();
//		scrollPaneEntradaCaja.setBounds(6, 6, 557, 80);
//		panelValorCaja.add(scrollPaneEntradaCaja);

//		tbEntradaCaja = new JTable(entradaCajaModel);
//		scrollPaneEntradaCaja.setViewportView(tbEntradaCaja);
//
//		lblConsolidado = new JLabel("CONSOLIDADO");
//		lblConsolidado.setBounds(321, 88, 111, 29);
//		panelValorCaja.add(lblConsolidado);

//		tfConsolidado = new JTextField();
//		tfConsolidado.setFont(new Font("Arial", Font.PLAIN, 14));
//		tfConsolidado.setEditable(false);
//		tfConsolidado.setColumns(10);
//		tfConsolidado.setBounds(433, 88, 130, 29);
//		panelValorCaja.add(tfConsolidado);

//		JScrollPane scrollCotizacion = new JScrollPane();
//		tabbedPane.addTab("Cotizaciones", null, scrollCotizacion, null);
//
//		tbCotizacion = new JTable(cotizacionModel);
//		tbCotizacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		tbCotizacion.setDefaultRenderer(Object.class, new CellRendererOperaciones());
//		scrollCotizacion.setViewportView(tbCotizacion);

//		lblTipoOperacion = new JLabel("");
//		lblTipoOperacion.setBounds(6, 301, 23, 30);
//		lblTipoOperacion.setForeground(Color.RED);
//		pnlEntradaPago.add(lblTipoOperacion);
//
//		lblTipo = new JLabel("HISTORICO");
//		lblTipo.setBounds(427, 169, 134, 16);
//		lblTipo.setHorizontalAlignment(SwingConstants.LEFT);
//		pnlEntradaPago.add(lblTipo);

		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setBounds(804, 332, 112, 47);
		pnlEntradaPago.add(btnGuardar);

		JButton btnEscCancelar = new JButton("SALIR");
		btnEscCancelar.setBounds(804, 442, 112, 47);
		pnlEntradaPago.add(btnEscCancelar);

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(804, 391, 112, 47);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				clearForm();
			}
		});
		pnlEntradaPago.add(btnCancelar);

		tfObs = new JTextField();
		tfObs.setBounds(759, 186, 79, 30);
		((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAdd.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfNotaNro.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnAdd.requestFocus();
				}
			}
		});
		tfObs.setFont(new Font("Arial", Font.PLAIN, 14));
		tfObs.setColumns(10);
		pnlEntradaPago.add(tfObs);

		lbIdRef = new JLabel("Obs.");
		lbIdRef.setBounds(759, 169, 91, 16);
		lbIdRef.setEnabled(false);
		pnlEntradaPago.add(lbIdRef);
		btnEscCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cancel();
				}
			}
		});
		btnEscCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
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

		JPanel pnlCabecera = new JPanel();
		pnlCabecera.setBounds(6, 0, 968, 75);
		getContentPane().add(pnlCabecera);

		JLabel lblCaja = new JLabel("CAJA:");
		lblCaja.setBounds(12, 4, 51, 30);

		tfCajaID = new JTextField();
		tfCajaID.setBounds(75, 4, 115, 30);
		tfCajaID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCajaID.selectAll();
			}
		});
		tfCajaID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCajaID.getText().isEmpty()) {
						findCajaById(Long.parseLong(tfCajaID.getText()));
					} else {
						// show dialog caja
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// loadInit();
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCajaID.setFont(new Font("Arial", Font.PLAIN, 14));
		tfCajaID.setColumns(10);

		JLabel lblFecha = new JLabel("FECHA:");
		lblFecha.setBounds(12, 38, 51, 30);
		// lblFecha.setVisible(false);

		tfFecha = new JFormattedTextField(getFormatoFecha());
		tfFecha.setEditable(false);
		tfFecha.setBounds(75, 38, 115, 30);
		tfFecha.setText(Fechas.formatoDDMMAAAA(new Date()));
		tfFecha.setVisible(false);

		JLabel lblTotal = new JLabel("TOTAL:");
		lblTotal.setBounds(566, 16, 91, 49);
		lblTotal.setFont(new Font("Dialog", Font.BOLD, 20));

		tfTotalNota = new JTextField();
		tfTotalNota.setBounds(676, 16, 206, 49);
		tfTotalNota.setText("0,00");
		tfTotalNota.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalNota.setEditable(false);
		tfTotalNota.setForeground(Color.RED);
		tfTotalNota.setFont(new Font("Dialog", Font.BOLD, 25));
		tfTotalNota.setColumns(10);

		lblTotalEntrada = new JLabel("TOTAL ENTRADA");
		lblTotalEntrada.setBounds(239, 4, 115, 30);

		tfTotalEntrada = new JTextField();
		tfTotalEntrada.setBounds(361, 4, 166, 30);
		tfTotalEntrada.setText("0,00");
		tfTotalEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalEntrada.setForeground(Color.RED);
		tfTotalEntrada.setFont(new Font("Arial", Font.BOLD, 20));
		tfTotalEntrada.setEditable(false);
		tfTotalEntrada.setColumns(10);

		lblTotalSalida = new JLabel("TOTAL SALIDA");
		lblTotalSalida.setBounds(239, 38, 115, 30);

		tfTotalSalida = new JTextField();
		tfTotalSalida.setBounds(361, 38, 166, 30);
		tfTotalSalida.setText("0,00");
		tfTotalSalida.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalSalida.setForeground(Color.RED);
		tfTotalSalida.setFont(new Font("Arial", Font.BOLD, 20));
		tfTotalSalida.setEditable(false);
		tfTotalSalida.setColumns(10);
		pnlCabecera.setLayout(null);
		pnlCabecera.add(lblCaja);
		pnlCabecera.add(lblFecha);
		pnlCabecera.add(tfCajaID);
		pnlCabecera.add(tfFecha);
		pnlCabecera.add(lblTotalEntrada);
		pnlCabecera.add(lblTotalSalida);
		pnlCabecera.add(tfTotalEntrada);
		pnlCabecera.add(tfTotalSalida);
		pnlCabecera.add(lblTotal);
		pnlCabecera.add(tfTotalNota);
		// tipoColumna();
	}

//    private void setMonedaValor() {
//    	Double total = FormatearValor.stringToDouble(tfTotalNota.getText());
//    	Double totalConv = 0d; 
//    	
//    	switch (monedaBaseOperacion) {
//			case "*":
//				totalConv = total * monedaBaseValor;
//				break;
//			case "/":
//				totalConv = total / monedaBaseValor;
//				break;
//			default:
//				Notifications.showAlert("No se ha definido operación de Moneda.!");
//				break;
//		}
//		
//		tbMoneda.setValueAt(totalConv, 0, 4);
//    }

//    private void ocultarColumna(JTable table, Integer columna) {
//	}
//    
//    private void tipoColumna() {
//	}

	private boolean existItemInTable() {
		try {
			Boolean esDuplicado = false;
			for (Integer i = 0; i < tbLanzamientos.getRowCount(); i++) {
				String operacionId = String.valueOf(tbLanzamientos.getValueAt(i, 0));
				String operacionTipo = String.valueOf(tbLanzamientos.getValueAt(i, 1));
				String notaNro = String.valueOf(tbLanzamientos.getValueAt(i, 2));

				if (tfOperacionID.getText().trim().equals(operacionId)
						&& tipo.trim().equals(operacionTipo)
						&& tfNotaNro.getText().trim().equals(notaNro)) {
					esDuplicado = true;
				}
			}
			if (esDuplicado) {
				Integer respuesta = JOptionPane.showConfirmDialog(null,
						"Registro ya existe en la grilla, desea actualizar los datos?");
				if (respuesta == 0) {
					if (isValidItem()) {
						// actualizarRegristroGrilla(fila, String.valueOf(tbNotas.getValueAt(fila, 1)));
						tfOperacionID.requestFocus();
					}
				} else {
					tfOperacionID.requestFocus();
				}
			}

			return esDuplicado;
			
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isValidItem() {
		if (tfOperacionID.getText().equals("")) {
			Notifications.showAlert("Digite el codigo de la Operacion");
			tfOperacionID.requestFocus();
			return false;
		} else if (tfNotaNro.getText().equals("")) {
			Notifications.showAlert("Digite el numero de la NOTA");
			tfNotaNro.requestFocus();
			return false;
		}

		return true;
	}

	private void calcularValores() {
		// Us -> Gs ->
	}

//	private void findMoneda() {
//		if (!tfMonedaId.getText().isEmpty()) {
//			Long monedaId = Long.valueOf(tfMonedaId.getText());
//			Optional<Moneda> moneda = monedaService.findById(monedaId);
//
//			if (moneda.isPresent()) {
//				lblNombreMoneda.setText(moneda.get().getSigla() + " - " + moneda.get().getNombre());
//				calculateItem();
//				irParaPagar();
//			} else {
//				Notifications.showAlert("No existe Moneda con este codigo.!");
//			}
//		} else {
//			Notifications.showAlert("Debes digital el codigo de la moneda.!");
//		}
//	}

//	private void irParaPagar() {
//		int monedaId = Integer.valueOf(tfMonedaId.getText());
//
//		switch (monedaId) {
//		case 1:
//			tfRecibidoUs.requestFocus();
//			tfRecibidoUs.selectAll();
//			break;
//		case 2:
//			tfRecibidoGs.requestFocus();
//			tfRecibidoGs.selectAll();
//			break;
//		case 3:
//			tfRecibidoRs.requestFocus();
//			tfRecibidoRs.selectAll();
//			break;
//		case 4:
//			tfRecibidoPs.requestFocus();
//			tfRecibidoPs.selectAll();
//			break;
//		default:
//			break;
//		}
//
//	}

	public void findOperacionById(String id) {
		try {
			int operacionId = Integer.parseInt(id);
			
			if (operacionId == OPERACION_COBRO_CLI) {
				openPopup(OPERACION_COBRO_CLI);
				
			} else if (operacionId == OPERACION_PAGO_PROV) {
				openPopup(OPERACION_PAGO_PROV);
				
			} else {
				Optional<PlanCuenta> cuenta = planService.findById(Long.valueOf(id));

				if (cuenta.isPresent()) {
					String nombre = cuenta.get().getNombre();
					lblOperacionNombre.setText(nombre);
					tipo = cuenta.get().getTipo();
					//lblTipoOperacion.setText(cuenta.get().getTipo());
					tfNotaNro.requestFocus();
				} else {
					Notifications.showAlert("No se encuentra Operacion con ese codigo.!");
				}
			}
		} catch (Exception e) {
			Notifications.showAlert("Ingrese correctamente la Operacion deseada, desde 1 a 7 enteros!");
		}
		
	}

	public void findCajaById(Long id) {
		Optional<Caja> caja = cajaService.findById(id);

		if (caja.isPresent()) {
			Caja c = caja.get();

			getNotasPendientes();

			getNotasLanzadas(c.getId());

			getCotizaciones();

			Optional<AperturaCierreCaja> movCaja = movCajaService.findByCajaAndFechaApertura(c, new Date());

			if (movCaja.isPresent()) {
				pnlEntradaPago.setVisible(true);
				tfFecha.setVisible(true);
				tfFecha.setText(Fechas.formatoDDMMAAAA(movCaja.get().getFechaApertura()));
				tfOperacionID.requestFocus();
				tfCajaID.setEditable(false);
			} else {
				Integer respuesta = JOptionPane.showConfirmDialog(null, "La caja esta cerrada. Desea abrirla.?");
				if (respuesta == 0) {
					openMovCaja(c);
				} else {
					tfCajaID.requestFocus();
				}
			}
		} else {
			Notifications.showAlert("No existe CAJA con este codigo.!");
		}
	}

	private void openMovCaja(Caja caja) {
		// cierre de caja del dia anterio
		AperturaCierreCaja newMov = movCajaService.save(new AperturaCierreCaja(caja, new Date(), 0d));
		String msg = "";

		if (newMov != null) {
			msg = "Apertura de Caja correctamente.!";
			findCajaById(newMov.getCaja().getId());
		} else {
			msg = "Lo sentimos. No se pudo abrir la caja correctamente.!";
		}

		Notifications.showAlert(msg);
	}

	public void findNotaById(int operacionId, Long notaId) {
		String notaReferencia = "", situacion = "PENDIENTE";
		Double notaValor = 0d;
		Long idRef = 0L;
		boolean operacionValida = false;

		switch (operacionId) {
		case OPERACION_VENTA:
			Optional<Venta> venta = ventaService.findNota(notaId, situacion);

			if (venta.isPresent()) {
				notaReferencia = venta.get().getClienteNombre();
				notaValor = venta.get().getTotalGeneral();
				idRef = venta.get().getCliente().getId();
				operacionValida = true;
			}
			break;
		case OPERACION_COMPRA:
			Optional<Compra> compra = compraService.findNota(notaId, situacion);

			if (compra.isPresent()) {
				notaReferencia = compra.get().getProveedorNombre();
				notaValor = compra.get().getTotalGeneral();
				idRef = compra.get().getProveedor().getId();
				operacionValida = true;
			}
			break;
		case OPERACION_DEVOLUCION_PROV:
			Optional<Devolucion> dCompra = devolucionService.findNota(notaId, situacion, "COMPRA");

			if (dCompra.isPresent()) {
				notaReferencia = dCompra.get().getReferencia();
				notaValor = dCompra.get().getTotalGeneral();
				idRef = dCompra.get().getRefId();
				operacionValida = true;
			}
			break;
		case OPERACION_DEVOLUCION_CLI:
			Optional<Devolucion> dVenta = devolucionService.findNota(notaId, situacion, "VENTA");

			if (dVenta.isPresent()) {
				notaReferencia = dVenta.get().getReferencia();
				notaValor = dVenta.get().getTotalGeneral();
				idRef = dVenta.get().getRefId();
				operacionValida = true;
			}
			break;
		default:
			tfNotaNombre.setEditable(true);
			tfNotaNombre.requestFocus();
			break;
		}

		if (operacionValida) {
			tfNotaNombre.setText(notaReferencia);
			tfNotaValor.setText(FormatearValor.doubleAString(notaValor));
			lbIdRef.setText(String.valueOf(idRef));
			btnAdd.requestFocus();
		} else {
			tfNotaNro.requestFocus();
			tfNotaNro.selectAll();
			Notifications.showAlert("La Nota no existe con este numero.!");
		}
	}

	private void addItem() {
		addItemToList();
		calculateItem();
		clearItem();
	}

	private void removeItem() {
		int row[] = tbLanzamientos.getSelectedRows();

		if (tbLanzamientos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				tableModel.removeRow(row[i - 1]);
			}

			tfOperacionID.requestFocus();

			clearItem();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}

		calculateItem();
	}

	private void getSelectedItem() {
		int selectedRow = tbLanzamientos.getSelectedRow();

		if (selectedRow != -1) {
			MovimientoCaja item = tableModel.getEntityByRow(selectedRow);

			tfOperacionID.setText(String.valueOf(item.getPlanCuentaId()));
			tipo= item.getTipoOperacion();
			//lblTipoOperacion.setText(item.getTipoOperacion());
			lblOperacionNombre.setText(item.getNotaReferencia());
			tfNotaNro.setText(item.getNotaNro());
			tfNotaValor.setText(FormatearValor.doubleAString(item.getNotaValor()));
		}
	}

	private void addItemToList() {
		if (!existItemInTable()) {
			tableModel.addEntity(getItem());
		}
	}

	private void calculateItem() {
		try {
			Double totalEntrada = tableModel.getEntities().stream().filter(e -> e.getTipoOperacion().equals("E"))
					.mapToDouble(i -> i.getNotaValor()).sum();

			Double totalSalida = tableModel.getEntities().stream().filter(e -> e.getTipoOperacion().equals("S"))
					.mapToDouble(i -> i.getNotaValor()).sum();

			Double totalGeneral = totalEntrada - totalSalida;

			setTotals(totalGeneral, totalEntrada, totalSalida);

			setTotalMoneda(totalGeneral);	
		} catch (Exception e2) {
			Notifications.showAlert("Problemas para calcular totales");
		}
	}

	private void setTotalMoneda(Double total) {
		if (monedaBaseCodigo == 1) { // guaranies 
			mGs = 1d;
			tfTotalGs.setText(FormatearValor.doubleAString(total * mGs));
			tfTotalUs.setText(FormatearValor.doubleAString(total / mDolar));
			tfTotalRs.setText(FormatearValor.doubleAString(total / mRs));
			tfTotalPs.setText(FormatearValor.doubleAString(total / mPs));
		} else if (monedaBaseCodigo == 2) { // dolares
			mDolar = 1d;
			tfTotalUs.setText(FormatearValor.doubleAString(total / mDolar));
			tfTotalRs.setText(FormatearValor.doubleAString(total * mRs));
			tfTotalPs.setText(FormatearValor.doubleAString(total * mPs));
			tfTotalGs.setText(FormatearValor.doubleAString(total * mGs));
		} else if (monedaBaseCodigo == 3) { // real brasilero
			mRs = 1d;
			tfTotalRs.setText(FormatearValor.doubleAString(total / mRs));
			tfTotalUs.setText(FormatearValor.doubleAString(total * mDolar));
			tfTotalPs.setText(FormatearValor.doubleAString(total * mPs));
			tfTotalGs.setText(FormatearValor.doubleAString(total * mGs));
		} else if (monedaBaseCodigo == 4) { // peso argentino
			mPs = 1d;
			tfTotalPs.setText(FormatearValor.doubleAString(total / mPs));
			tfTotalRs.setText(FormatearValor.doubleAString(total * mRs));
			tfTotalUs.setText(FormatearValor.doubleAString(total * mDolar));
			tfTotalGs.setText(FormatearValor.doubleAString(total * mGs));
		} 
	}

	private Caja cajaPdv;
	private boolean isPDV = false;

	public void isPDV(Caja caja, Long notaId) {
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
		tabbedPane.setEnabledAt(4, false);
		
		isPDV = true;

		cajaPdv = caja;

		tfCajaID.setText(String.valueOf(cajaPdv.getId()));
		tfOperacionID.setText("1");
		tfNotaNro.setText(String.valueOf(notaId));
		tfObs.setText("Punto de Venta");

		//findOperacionById(1L);
		findNotaById(1, notaId);

		addItem();
		seleccionarMoneda();
		tfFecha.setText(Fechas.formatoDDMMAAAA(new Date()));
	}

	private void seleccionarMoneda() {
		tfMonedaId.requestFocus();
		tfMonedaId.selectAll();
	}

	private MovimientoCaja getItem() {
		MovimientoCaja entPago = new MovimientoCaja();
		entPago.setFecha(new Date());
		entPago.setUsuario(GlobalVars.USER_ID);
		entPago.setIdReferencia(Long.valueOf(lbIdRef.getText()));
		// entPago.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID));

		entPago.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID)); // modificar

		// modificar
		if (cajaPdv != null) {
			entPago.setCaja(cajaPdv);
		} else {
			entPago.setCaja(new Caja(Long.parseLong(tfCajaID.getText())));
		}
		if(tfOperacionID.getText()!=""&&!tfOperacionID.getText().isEmpty())
			entPago.setPlanCuentaId(Integer.parseInt(tfOperacionID.getText().toString()));
		
		entPago.setTipoOperacion(tipo);
		entPago.setNotaNro(tfNotaNro.getText());
		entPago.setNotaReferencia(tfNotaNombre.getText());
		entPago.setNotaValor(FormatearValor.stringADouble(tfNotaValor.getText()));
		entPago.setObs(tfObs.getText());
		//entPago.setSituacion("PAGADO");

		return entPago;
	}

	private void clearItem() {
		tfOperacionID.setText("");
		lblOperacionNombre.setText("");
//		lblTipoOperacion.setText("");
		tfNotaNro.setText("");
		tfNotaNombre.setText("");
		tfNotaValor.setText("");
		tfObs.setText("");
		tfOperacionID.requestFocus();
	}

	private void cancelarNota() {
		// notasLanzadasModel.getEntityByRow(rowIndex)

		int selectedRow = tbNotasLanzadas.getSelectedRow();

		System.out.println("cancelarNota: " + selectedRow);

		if (selectedRow != -1) {
			MovimientoCaja mov = notasLanzadasModel.getEntityByRow(selectedRow);

			System.out.println("paso EN NOTA: " + mov.toString());

			switch (mov.getPlanCuentaId()) {
			case 1:
				System.out.println("paso en VENTAS");
				Optional<Venta> venta = ventaService.findById(Long.valueOf(mov.getNotaNro()));
				if (venta.isPresent()) {
					System.out.println("esta PRESENTE");
					Venta v = venta.get();
					v.setSituacion("CANCELADO");
					ventaService.save(v);

					updateTransactionStock(venta.get(), true);
				}
				break;
			case 2:
				Optional<Compra> compra = compraService.findById(Long.valueOf(mov.getNotaNro()));
				if (compra.isPresent()) {
					Compra c = compra.get();
					c.setSituacion("CANCELADO");
					compraService.save(c);
				}
				break;
			case 3:
				Optional<Devolucion> devolucion = devolucionService.findById(Long.valueOf(mov.getNotaNro()));
				if (devolucion.isPresent()) {
					Devolucion d = devolucion.get();
					d.setSituacion("CANCELADO");
					devolucionService.save(d);
				}
				break;
			case 4:
				Optional<Devolucion> dev = devolucionService.findById(Long.valueOf(mov.getNotaNro()));
				if (dev.isPresent()) {
					Devolucion d = dev.get();
					d.setSituacion("CANCELADO");
					devolucionService.save(d);
				}
				break;
			default:
				break;
			}

			getNotasPendientes();
		}
	}

	private String monedaBaseOperacion = "";
	private Long monedaBaseCodigo = 0L;
	// private Double monedaBaseValor = 0d;
	private Double mDolar, mGs, mRs, mPs = 0d;
	private String tipo="";

	public void getCotizaciones() {
		cotizacionModel.clear();
		monedaValorModel.clear();

		List<Cotizacion> cotizaciones = new ArrayList<>();
		List<MonedaValor> valores = new ArrayList<>();
		List<Moneda> monedas = monedaService.findAll();

		if (!monedas.isEmpty()) {
			for (Moneda moneda : monedas) {
				Optional<Cotizacion> cot = cotizacionService.findCotizacionByMoneda(moneda.getId());

				valores.add(new MonedaValor(moneda.getId(), moneda.getSigla(), moneda.getOperacion()));

				if (cot.isPresent()) {
					cotizaciones.add(cot.get());

					if (moneda.getEsBase() == 1) {
						monedaBaseCodigo = moneda.getId();
						monedaBaseOperacion = moneda.getOperacion();
						// monedaBaseValor = cot.get().getValorVenta();
					}

					if (moneda.getId() == 1) { // GS
						mGs = cot.get().getValorVenta();
					} else if (moneda.getId() == 2) { // DS
						mDolar = cot.get().getValorVenta();
					} else if (moneda.getId() == 3) { // RS
						mRs = cot.get().getValorVenta();
					} else if (moneda.getId() == 4) { // PS
						mPs = cot.get().getValorVenta();
					} 
				}
			}
			

			monedaValorModel.addEntities(valores);
			cotizacionModel.addEntities(cotizaciones);
		}
	}

	private MaskFormatter formatoFecha;
	private JLabel lblTotalEntrada;
	private JTextField tfTotalEntrada;
	private JLabel lblTotalSalida;
	private JTextField tfTotalSalida;
	//private JLabel lblTipoOperacion;
	private JLabel lblTipo;
	private JLabel lblPs;
	private JLabel tfTotalPs;
	private JTextField tfRecibidoPs;
	private JTextField tfVueltoPs;
	private JPanel panelValorCaja;
	private JTable tbEntradaCaja;
	private JScrollPane scrollPaneEntradaCaja;
	private JLabel lblConsolidado;
	private JTextField tfConsolidado;
	private JScrollPane scrollPane;
	private JButton btnCancelar;
	private JTextField tfObs;
	private JLabel lbIdRef;
	private JPanel pnlTotales;
//	private JPanel panel;
//	private JLabel lblMoneda;
	private JTextField tfMonedaId;
//	private JLabel lblNombreMoneda;
	private JLabel lblValorFaltanteUs;
	private JLabel lblValorFaltanteGs;
	private JLabel lblValorFaltantePs;
	private JLabel lblValorFaltanteRs;

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

	private void getNotasPendientes() {
		String situacion = "PENDIENTE";
		List<Nota> notas = new ArrayList<>();

		if (!tfCajaID.getText().isEmpty()) {
			List<Venta> ventas = ventaService.getNotasPendientesByFechaAndSituacion(new Date(), situacion);
			List<Compra> compras = compraService.getNotasPendientes(situacion);
			List<Devolucion> devoluciones = devolucionService.getNotasPendientes(new Date(), situacion);

			for (Venta venta : ventas) {
				notas.add(new Nota("VENTAS", venta.getId(), venta.getClienteNombre(), venta.getTotalGeneral()));
			}
			for (Compra compra : compras) {
				notas.add(new Nota("COMPRAS", compra.getId(), compra.getProveedorNombre(), compra.getTotalGeneral()));
			}
			for (Devolucion d : devoluciones) {
				notas.add(new Nota("DEVOLUCIONES DE " + d.getTipo(), d.getId(), d.getReferencia(), d.getTotalGeneral()));
			}
			notaModel.clear();
			notaModel.addEntities(notas);
		}
	}

	private void getNotasLanzadas(Long cajaId) {
		String situacion = "PAGADO";
		List<MovimientoCaja> pagos = new ArrayList<>();

		if (!tfCajaID.getText().isEmpty()) {
			pagos = pagoService.findByFechaAndCajaAndSituacion(new Date(), new Caja(cajaId), situacion);

			if (!pagos.isEmpty()) {
				notasLanzadasModel.clear();
				notasLanzadasModel.addEntities(pagos);

				// valores en caja
				loadValoresCaja(pagos);
			}
		}
	}

	private void loadValoresCaja(List<MovimientoCaja> valores) {
		for (MovimientoCaja e : valores) {
			entradaCajaModel.addEntity(e);
		}
	}

	private void setTotals(Double totalGeneral, Double totalEntrada, Double totalSalida) {
		tfTotalNota.setText(FormatearValor.doubleAString(totalGeneral));
		tfTotalEntrada.setText(FormatearValor.doubleAString(totalEntrada));
		tfTotalSalida.setText(FormatearValor.doubleAString(totalSalida));
	}

	private void save() {
		if (validateCabezera() && validateItems()) {
			List<MovimientoCaja> mov = tableModel.getEntities();
			for (MovimientoCaja e : mov) {
				//if (e.getTipoOperacion().equals("E")) {
					e.setValorM01(
							!tfRecibidoUs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoUs.getText())
									: 0);
					e.setValorM02(
							!tfRecibidoRs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoRs.getText())
									: 0);
					e.setValorM03(
							!tfRecibidoPs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoPs.getText())
									: 0);
					e.setValorM04(
							!tfRecibidoGs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoGs.getText())
									: 0);
					e.setValorM05(FormatearValor.stringToDouble("0"));
					e.setCotizacion(mDolar);
					if(e.getPlanCuentaId()==1) {
						Optional<Venta> venta = ventaService.findById(Long.valueOf(e.getNotaNro()));
						if (venta.isPresent()) {
							Venta v = venta.get();
							if(v.getCondicion()==2)
								v.setSituacion("PROCESADO");
							else
								v.setSituacion("PAGADO");
							ventaService.saveFromCaja(v);
						}
					}else {
						if(e.getPlanCuentaId()==2) {
							Optional<Compra> compra= compraService.findById(Long.valueOf(e.getNotaNro()));
							if (compra.isPresent()) {
								Compra c = compra.get();
								if(c.getCondicion()==2)
									c.setSituacion("PROCESADO");
								else
									c.setSituacion("PAGADO");
								compraService.saveFromCaja(c);
							}
						}
						
					}
				//}				
			}

			List<MovimientoCaja> pagos = pagoService.save(mov);
			

//			if (pagos != null && !pagos.isEmpty()) {
//				updateTransacction(pagos, new Caja(Long.valueOf(tfCajaID.getText())));
//			}

			if (isPDV) {
				//clearForm();
				clearTable();
				clearTotals();
				isPDV = false;
				dispose();
			} else {
				clearTable();
				clearTotals();
				getNotasLanzadas(Long.valueOf(tfCajaID.getText()));
				getNotasPendientes();
			}
		}
	}

	private boolean validateCabezera() {
		if (tfCajaID.getText().isEmpty()) {
			Notifications.showAlert("La caja es obligatorio.!");
			tfCajaID.requestFocus();
			return false;
		} else {
			Optional<Caja> caja = cajaService.findById(Long.valueOf(tfCajaID.getText()));

			if (!caja.isPresent()) {
				Notifications.showAlert("No existe Caja con el codigo informado.!");
				tfCajaID.requestFocus();
				return false;
			}
		}

		return true;
	}

	private boolean validateItems() {
		if(tableModel.getEntities().isEmpty()) {
			Notifications.showAlert("Cargue por lo menos un item!");
			return false;
		}
		return true;
	}

	private void updateTransacction(List<MovimientoCaja> pagos, Caja caja) {
		for (MovimientoCaja movimientoCaja : pagos) {
			if (movimientoCaja.getPlanCuentaId() == OPERACION_VENTA) {
				Optional<Venta> venta = ventaService.findById(Long.valueOf(movimientoCaja.getNotaNro()));

				if (venta.isPresent()) {
					Venta v = venta.get();
					v.setCaja(caja);

					if (v.getCondicion() == 0) {
						v.setSituacion("PAGADO");
					} else {
						v.setSituacion("PROCESADO");

//						updateCuentaCliente("VENTA NRO.: " + v.getId(), v.getCliente(), v.getTotalGeneral(),
//								0d, v.getFecha(), v.getVencimiento(), v.getObs());

						updateSaldoCliente(v.getCliente().getId(), v.getTotalGeneral(), v.getCondicion());
					}

					Venta ven = ventaService.save(v);

					if (ven != null) {
						updateTransactionStock(ven, false);
					}
				}
			} else if (movimientoCaja.getPlanCuentaId() == OPERACION_COMPRA) {
				Optional<Compra> compra = compraService.findById(Long.valueOf(movimientoCaja.getNotaNro()));

				if (compra.isPresent()) {
					Compra c = compra.get();
					c.setCaja(caja);

					if (c.getCondicion() == 0) {
						c.setSituacion("PAGADO");
					} else {
						c.setSituacion("PROCESADO");
						Double totalFob = c.getTotalFob() != null ? c.getTotalFob() : 0;
						Double desc = c.getDescuento() != null ? c.getDescuento() : 0;

						updateCuentaProveedor(c.getProveedor(), (totalFob - desc), 0d, 
								true, "COMPRA NRO.: " + c.getId(), c.getVencimiento(), c.getObs());

						updateSaldoProveedor(c.getProveedor().getId(), c.getTotalFob(), c.getCondicion());
					}

					Compra com = compraService.save(c);

					if (com != null) {
						updateCompraItemDeposito(com);
					}
				}
			} else if (movimientoCaja.getPlanCuentaId() == OPERACION_DEVOLUCION_PROV) {
				Optional<Devolucion> dev = devolucionService.findById(Long.valueOf(movimientoCaja.getNotaNro()));

				if (dev.isPresent()) {
					Devolucion d = dev.get();

					if (d.getTipo().equals("COMPRA")) {
						if (d.getCredito().equals("N")) {
							d.setSituacion("PAGADO");
						} else {
							d.setSituacion("CREDITO A PROVEEDOR");
							updateCuentaProveedor(new Proveedor(d.getRefId()), d.getTotalGeneral(), FormatearValor.stringADouble(tfTotalSalida.getText()),  
								true, "DEV. A PROV. " + d.getId(), d.getVencimiento(), d.getObs());
						}
					}
				}
			} else if (movimientoCaja.getPlanCuentaId() == OPERACION_DEVOLUCION_CLI) {
				Optional<Devolucion> dev = devolucionService.findById(Long.valueOf(movimientoCaja.getNotaNro()));

				if (dev.isPresent()) {
					Devolucion d = dev.get();

					if (d.getTipo().equals("VENTA")) {
						if (d.getCredito().equals("N")) {
							d.setSituacion("PAGADO");
						} else {
							d.setSituacion("CREDITO A CLIENTE");
						}
					}
				}
			}
		}
	}

	private void updateTransactionStock(Venta venta, boolean isCancel) {
		System.out.println("UPDATE TRANSACCION");
		int depID = Integer.parseInt(String.valueOf(venta.getDeposito().getId()));

		List<Producto> productos = new ArrayList<>();
		for (VentaDetalle e : venta.getItems()) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();

				Double salidaPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
				Double cantItem = e.getCantidad() != null ? e.getCantidad() : 0;

				// Stock de Depositos
				Double cant = p.getDepO1() != null ? p.getDepO1() : 0;
				Double cant02 = p.getDepO2() != null ? p.getDepO2() : 0;

				// refactorizar
				switch (depID) {
				case 1:
					if (isCancel) {
						p.setDepO1(cant + cantItem);
					} else {
						p.setDepO1(cant - cantItem);
					}

					break;
				case 2:
					if (isCancel) {
						p.setDepO2(cant02 + cantItem);
					} else {
						p.setDepO2(cant02 - cantItem);
					}

					break;
				default:
					break;
				}

				p.setSalidaPend(salidaPend - cantItem);


				//actualizar el calculo de utilidad asi como ya fue implementado en agroprogreso
				productos.add(p);
			}
		}
		
		// actualiza producto
		productoService.updateStock(productos);
	}

	private void updateSaldoCliente(Long clienteID, Double totalGeneral, int condicion) {
		Optional<Cliente> c = clienteService.findById(clienteID);

		if (c.isPresent()) {
			Cliente cli = c.get();
//			Double totalCredito = cli.getTotalCredito() != null ? cli.getTotalCredito():0;
			Double totalDebito = cli.getTotalDebito() != null ? cli.getTotalDebito() : 0;

//			if (condicion == 0) { //contado
			cli.setTotalDebito(totalDebito + totalGeneral);
//			} else {
//				cli.setTotalCredito(totalCredito + totalGeneral);
//			}

			try {
				clienteService.save(cli);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateSaldoProveedor(Long proveedorID, Double totalGeneral, int condicion) {
		Optional<Proveedor> p = proveedorService.findById(proveedorID);

		if (p.isPresent()) {
			Proveedor pro = p.get();
//			Double totalCredito = pro.getTotalCredito() != null ? pro.getTotalCredito():0;
			Double totalDebito = pro.getTotalDebito() != null ? pro.getTotalDebito() : 0;

//			if (condicion == 0) { //contado
//				pro.setTotalDebito(totalDebito + totalGeneral);
//			} else {
			pro.setTotalDebito(totalDebito + totalGeneral);
//			}

			proveedorService.save(pro);
		}
	}

	private void updateCompraItemDeposito(Compra c) {
		Double total = c.getTotalGeneral() != null ? c.getTotalGeneral() : 0;
		Double desc = c.getDescuento() != null ? c.getDescuento() : 0;

		int depId = Integer.parseInt(String.valueOf(c.getDeposito().getId()));

		List<Producto> productos = new ArrayList<>();

		for (CompraDetalle e : c.getItems()) {
			Optional<Producto> pStock = productoService.findById(e.getProductoId());

			if (pStock.isPresent()) {
				Producto p = pStock.get();

				Double cantidad = e.getCantidad() != null ? e.getCantidad() : 0;
				Double entradaPend = p.getEntPendiente() != null ? p.getEntPendiente() : 0;

				Double stockDep01 = p.getDepO1() != null ? p.getDepO1() : 0;
				Double stockDep02 = p.getDepO2() != null ? p.getDepO2() : 0;
		
				switch (depId) {
					case 1:
						p.setEntPendiente(entradaPend - cantidad);
						p.setDepO1(stockDep01 + cantidad);
						break;
					case 2:
						p.setEntPendiente(entradaPend - cantidad);
						p.setDepO2(stockDep02 + cantidad);
						break;
					default:
						break;
				}

				// Calcular precio promedio
				Double stockTotal = stockDep01 + stockDep02 + cantidad;
				Double precio = p.getPrecioCosto() != null ? p.getPrecioCosto() : 0;
				Double precioPromedioActual = stockTotal * precio;
				p.setPrecioCostoPromedio(precioPromedioActual);

				p.setPrecioCosto(e.getPrecio());

				productos.add(p);
				
				productoService.save(p);
			}
		}
	}

//	private void updateCuentaCliente(String documento, Cliente cliente, Double valorTotal, 
//			Double valorPagado, Date fecha, Date vencimiento, String obs) {
//		CuentaCliente cuentaCliente = new CuentaCliente();
//		cuentaCliente.setCliente(cliente);
//		cuentaCliente.setClienteNombre(cliente.getNombre());
//		cuentaCliente.setTipo("CAJA");
//		cuentaCliente.setUsuarioId(GlobalVars.USER_ID);
//		cuentaCliente.setFecha(fecha);
//		cuentaCliente.setVencimiento(vencimiento);
//		cuentaCliente.setObs(obs);
//		cuentaCliente.setHora(new Date());
//		cuentaCliente.setMoneda(new Moneda(1L));
//		cuentaCliente.setDocumento(documento);
//		cuentaCliente.setDebito(valorTotal);
//		cuentaCliente.setValorTotal(valorTotal);
//		cuentaCliente.setValorPagado(valorPagado);
//		
//		if (valorTotal == valorPagado)
//			cuentaCliente.setSituacion("PROCESADO");
//		else
//			cuentaCliente.setSituacion("PENDIENTE");
//	
//		cuentaClienteService.save(cuentaCliente);
//	}

	private void updateCuentaProveedor(Proveedor proveedor, Double valorTotal, Double valorPagado,
			boolean esCredito, String documento, Date vencimiento, String obs) {
		CuentaProveedor cuentaProveedor = new CuentaProveedor();
		cuentaProveedor.setProveedor(proveedor);
		cuentaProveedor.setProveedorNombre(proveedor.getNombre());
		cuentaProveedor.setTipo("CAJA");
		cuentaProveedor.setFecha(new Date());
		cuentaProveedor.setVencimiento(vencimiento);
		cuentaProveedor.setObs(obs);
		cuentaProveedor.setHora(new Date());
		cuentaProveedor.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID));
		cuentaProveedor.setUsuarioId(GlobalVars.USER_ID);
		cuentaProveedor.setDocumento(documento);
		cuentaProveedor.setValorTotal(valorTotal);
		cuentaProveedor.setValorPagado(valorPagado);
		
		if (valorTotal == valorPagado)
			cuentaProveedor.setSituacion("PROCESADO");
		else
			cuentaProveedor.setSituacion("PENDIENTE");

		if (esCredito) { // LE DEBEMOS AL PROVEEDOR
			cuentaProveedor.setDebito(valorTotal);
		} else {
			cuentaProveedor.setCredito(valorTotal);
		}

		cuentaProveedorService.save(cuentaProveedor);
	}

	private void calculateVuelto(Double total, Double recibido, Double cot) {
		if (recibido > total) {
			Notifications.showAlert("El valor RECIBIDO no puede ser mayor al TOTAL");
		} else {
			Double valorVuelto = total - recibido;

			if (valorVuelto == 0) {
				tfVueltoUs.setText("0");
				tfVueltoRs.setText("0");
				tfVueltoPs.setText("0");
				tfVueltoGs.setText("0");

				tfTotalUs.setText("0");
				tfTotalRs.setText("0");
				tfTotalPs.setText("0");
				tfTotalGs.setText("0");

				btnGuardar.requestFocus();
			} else {
				Double valor = 0d;

				if (monedaBaseOperacion.compareTo("*") == 1) {
					valor = (valorVuelto * cot);
					tfTotalUs.setText(FormatearValor.doubleAString(valor));
					tfTotalRs.setText(FormatearValor.doubleAString(valor / mRs));
					tfTotalPs.setText(FormatearValor.doubleAString(valor / mPs));
					tfTotalGs.setText(FormatearValor.doubleAString(valor / mGs));
				} else {
					valor = valorVuelto / cot;
					tfTotalUs.setText(FormatearValor.doubleAString(valor));
					tfTotalRs.setText(FormatearValor.doubleAString(valor * mRs));
					tfTotalPs.setText(FormatearValor.doubleAString(valor * mPs));
					tfTotalGs.setText(FormatearValor.doubleAString(valor * mGs));
				}
			}
		}
	}

	private void cancel() {
		JOptionPane opConfirmacion = new JOptionPane("Estas saliendo de Lanzamiento de Caja",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
		JDialog dialogo = opConfirmacion.createDialog(this, "Desea salir.?");
		dialogo.setVisible(true);

		if (dialogo.getFocusOwner().hashCode() == opConfirmacion.getComponent(1).getAccessibleContext()
				.getAccessibleChild(0).hashCode()) {
			clearForm();
		} else {
			dialogo.dispose();
		}
	}

	public void clearForm() {
		clearItem();
		clearTable();
		clearTotals();
		tfCajaID.setText("");
		tfCajaID.setEditable(true);
		tfCajaID.requestFocus();
		pnlEntradaPago.setVisible(false);
	}

	private void clearTotals() {
		tfTotalGs.setText("0");
		tfTotalRs.setText("0");
		tfTotalUs.setText("0");
		tfTotalPs.setText("0");
		tfRecibidoGs.setText("0");
		tfRecibidoRs.setText("0");
		tfRecibidoUs.setText("0");
		tfRecibidoPs.setText("0");
		tfVueltoGs.setText("0");
		tfVueltoRs.setText("0");
		tfVueltoUs.setText("0");
		tfVueltoPs.setText("0");
		tfTotalNota.setText("0");
		tfTotalEntrada.setText("0");
		tfTotalSalida.setText("0");
	}

	private void clearTable() {
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}

		tfOperacionID.requestFocus();
	}
	
	private void openPopup(int key) {
		switch (key) {
			case OPERACION_COBRO_CLI:
				//creditoDebitoDialog.setCuentaClienteInterfaz(this);
				creditoDebitoDialog.setVisible(true);
				creditoDebitoDialog.setClient(true);
				creditoDebitoDialog.clearForm();
				break;
			case OPERACION_PAGO_PROV:
				creditoDebitoDialog.setCuentaProveedorInterfaz(this);
				creditoDebitoDialog.setVisible(true);
				creditoDebitoDialog.setClient(false);
				creditoDebitoDialog.clearForm();
				break;
			default:
				cuentaDialog.setInterfaz(this);
				cuentaDialog.setVisible(true);
				break;
		}
	}

	@Override
	public void getEntity(PlanCuenta c) {
		if (c != null) {
			tfOperacionID.setText(String.valueOf(c.getId()));
			lblOperacionNombre.setText(c.getNombre());
			tipo = c.getTipo();
			//lblTipoOperacion.setText(c.getTipo());
			tfNotaNro.requestFocus();
		}
	}

	@Override
	public void getEntity(Proveedor p) {
		setProveedor(p);
	}

	@Override
	public void getEntity(Cliente c) {
		setCliente(c);
	}
	
	private void setCliente(Cliente c) {
		
	}
	
	private void setProveedor(Proveedor p) {
		
	}

//	@Override
//	public void getEntity(CuentaCliente c) {
//		System.out.println(c.toString());
//		if (c != null) {
//			Double total = c.getCuentas().stream().mapToDouble(i -> i.getCredito()).sum();
//			
//			tfNotaNro.setText(c.getCuentas().get(0).getDocumento());
//			tfNotaNombre.setText(c.getCuentas().get(0).getClienteNombre());
//			tfNotaValor.setText(FormatearValor.doubleAString(total));
//			tfObs.requestFocus();
//		}
//	}

	@Override
	public void getEntity(CuentaProveedor c) {
		if (c != null) {
			System.out.println(c.toString());
			if (c != null) {
				Double total = c.getCuentas().stream().mapToDouble(i -> i.getDebito()).sum();
				
				tfNotaNro.setText(c.getCuentas().get(0).getDocumento());
				tfNotaNombre.setText(c.getCuentas().get(0).getProveedorNombre());
				tfNotaValor.setText(FormatearValor.doubleAString(total));
				tfObs.requestFocus();
			}
		}
		
	}
}
