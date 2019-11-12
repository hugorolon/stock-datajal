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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Cotizacion;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.CotizacionService;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.controllers.ClienteController;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.VentaInterfaz;
import py.com.prestosoftware.ui.table.CotPDVTableModel;
import py.com.prestosoftware.ui.table.PDVTableModel;
import py.com.prestosoftware.ui.viewmodel.MonedaValor;
import py.com.prestosoftware.util.Notifications;

@Component
public class PDV extends JFrame implements ProductoInterfaz, VentaInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int PRODUCTO_CODE = 4;
	private static final int SALDO_PRODUCTO_CODE = 5;

	private JLabel lblBuscadorDeVentas;
	private JTextField tfDescripcion, tfVentaId;
	private JTextField tfPrecio, tfCajaID;
	private JTextField tfCantidad;
	private JTextField tfProductoID;
	private JButton btnRemove;
	private JTable tbProductos;
	private JLabel label;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JPanel pnlCotizaciones;
	private JTable tbCotizaciones;
	private JScrollPane scrollPane;
	private JPanel pnlReferencia;
	private Long depositoId = 0L;
	private Long monedaBaseCodigo = 0L;
	private Double mUs, mGs, mRs, mPs = 0d;
	private JLabel lblTotalGs;
	private JLabel lblTotalUs;
	private JLabel lblTotalRs;
	private JLabel lblTotalPs;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel tfTotalItems;
	private JLabel lblBusca;
	private JLabel lblFQuitar;
	private JLabel lblF;
	private JLabel lblEscLimpiar;
	private JLabel lblTotalItems;
	private JLabel lblF_1;
	private JLabel lblF_2;
	private JLabel lblF_3;
	private JLabel lblEsc;
	private JPanel panel_3;

	private ProductoDialog productoDialog;
	private PDVTableModel itemTableModel;
	private CotPDVTableModel cotizacionModel;
	private PDVCliente pdvCliente;
	private ProductoService productoService;
	private MonedaService monedaService;
	private CotizacionService cotizacionService;
	private ConfiguracionService configService;
	private VentaService ventaService;

	@Autowired
	public PDV(PDVTableModel itemTableModel, ProductoDialog productoDialog, ProductoService productoService,
			ConfiguracionService configService, MonedaService monedaService, CotizacionService cotizacionService,
			ClienteController clientController, CotPDVTableModel cotizacionModel, PDVCliente pdvCliente,
			VentaService ventaService) {
		this.itemTableModel = itemTableModel;
		this.productoDialog = productoDialog;
		this.productoService = productoService;
		this.configService = configService;
		this.monedaService = monedaService;
		this.cotizacionService = cotizacionService;
		this.cotizacionModel = cotizacionModel;
		this.pdvCliente = pdvCliente;
		this.ventaService = ventaService;

		setSize(1009, 594);

		setTitle("PDV - DATAJAL");

		initComponents();

		Util.setupScreen(this);

		getConfig();

		getCotizaciones();

		newVenta();
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);

		pnlCotizaciones = new JPanel();
		pnlCotizaciones
				.setBorder(new TitledBorder(null, "COTIZACIONES", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCotizaciones.setBounds(746, 117, 251, 125);
		getContentPane().add(pnlCotizaciones);
		pnlCotizaciones.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 22, 227, 96);
		pnlCotizaciones.add(scrollPane);

		tbCotizaciones = new JTable(cotizacionModel);
		tbCotizaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbCotizaciones.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPane.setViewportView(tbCotizaciones);

		pnlReferencia = new JPanel();
		pnlReferencia
				.setBorder(new TitledBorder(null, "REFERENCIA", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlReferencia.setBounds(747, 0, 250, 105);
		getContentPane().add(pnlReferencia);
		pnlReferencia.setLayout(null);

		tfVentaId = new JTextField();
		tfVentaId.setBounds(65, 25, 155, 30);
		pnlReferencia.add(tfVentaId);
		tfVentaId.setEditable(false);
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

		lblBuscadorDeVentas = new JLabel("NOTA:");
		lblBuscadorDeVentas.setBounds(10, 25, 57, 30);
		pnlReferencia.add(lblBuscadorDeVentas);

		JLabel lblVendedor = new JLabel("CAJA:");
		lblVendedor.setBounds(10, 59, 57, 30);
		pnlReferencia.add(lblVendedor);

		tfCajaID = new JTextField();
		tfCajaID.setEditable(false);
		tfCajaID.setBounds(65, 59, 155, 30);
		pnlReferencia.add(tfCajaID);
		tfCajaID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCajaID.selectAll();
			}
		});
		tfCajaID.setColumns(10);

		panel = new JPanel();
		panel.setBounds(6, 6, 738, 558);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblCodigo = new JLabel("CODIGO");
		lblCodigo.setBounds(12, 10, 120, 16);
		panel.add(lblCodigo);
		lblCodigo.setFont(new Font("Verdana", Font.BOLD, 14));
		lblCodigo.setForeground(Color.RED);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setBounds(144, 10, 170, 16);
		panel.add(lblDescripcion);
		lblDescripcion.setFont(new Font("Verdana", Font.BOLD, 14));
		lblDescripcion.setForeground(Color.RED);

		JLabel lblPrecio = new JLabel("PRECIO UNIT.");
		lblPrecio.setBounds(351, 10, 124, 16);
		panel.add(lblPrecio);
		lblPrecio.setFont(new Font("Verdana", Font.BOLD, 14));
		lblPrecio.setForeground(Color.RED);

		JLabel lblCantidad = new JLabel("CANTIDAD");
		lblCantidad.setBounds(478, 10, 124, 16);
		panel.add(lblCantidad);
		lblCantidad.setFont(new Font("Verdana", Font.BOLD, 14));
		lblCantidad.setForeground(Color.RED);

		tfProductoID = new JTextField();
		tfProductoID.setHorizontalAlignment(SwingConstants.RIGHT);
		tfProductoID.setBounds(12, 38, 128, 30);
		panel.add(tfProductoID);
		tfProductoID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfProductoID.selectAll();
			}
		});
		tfProductoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F3) {
					showDialog(SALDO_PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F10) {
					openClientePDV();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					abandonarNota();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProductoByFilter(tfProductoID.getText());
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
		tfProductoID.setColumns(10);

		tfDescripcion = new JTextField();
		tfDescripcion.setBounds(144, 38, 202, 30);
		panel.add(tfDescripcion);
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);

		tfPrecio = new JTextField();
		tfPrecio.setBounds(351, 38, 124, 30);
		panel.add(tfPrecio);
		tfPrecio.setEditable(false);
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
						// calculatePrecioTotal();

					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfCantidad.requestFocus();
				}
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//					btnAdd.requestFocus();
//				}
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

		tfCantidad = new JTextField();
		tfCantidad.setBounds(478, 38, 124, 30);
		panel.add(tfCantidad);
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
//							Long productoId = Long.valueOf(tfProductoID.getText());
//							Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());
//							
//							if (validateCantidad(productoId, cantidad)) {
//								tfPrecio.requestFocus();
//							}
							if (isValidItem()) {
								addItem();
							}
						} else {
							Notifications.showAlert("Debes informar el Código del Producto.!");
						}
					} else {
						Notifications.showAlert("Verifique la cantidad");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					showDialog(SALDO_PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F10) {
					// consulta de saldo stock
					openClientePDV();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					// tfPrecio.requestFocus();
					btnRemove.requestFocus();
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

		btnRemove = new JButton("Quitar [F3]");
		btnRemove.setBounds(602, 38, 124, 30);
		panel.add(btnRemove);
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 14));

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(12, 71, 714, 487);
		panel.add(scrollProducto);

		tbProductos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
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
					// btnGuardar.requestFocus();
				}
			}
		});

		// setTableSize();

		scrollProducto.setViewportView(tbProductos);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "TOTALES", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(746, 243, 251, 169);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBounds(5, 17, 241, 144);
		panel_2.add(panel_1);
		panel_1.setLayout(null);

		label = new JLabel();
		label.setBounds(12, 6, 38, 28);
		panel_1.add(label);
		label.setText("GS");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Verdana", Font.BOLD, 14));

		lblTotalGs = new JLabel("0");
		lblTotalGs.setBounds(73, 6, 156, 28);
		panel_1.add(lblTotalGs);
		lblTotalGs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalGs.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTotalGs.setForeground(Color.RED);

		label_6 = new JLabel();
		label_6.setBounds(12, 40, 38, 28);
		panel_1.add(label_6);
		label_6.setText("US");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("Verdana", Font.BOLD, 14));

		lblTotalUs = new JLabel("0.0");
		lblTotalUs.setBounds(73, 40, 156, 28);
		panel_1.add(lblTotalUs);
		lblTotalUs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalUs.setForeground(Color.RED);
		lblTotalUs.setFont(new Font("Dialog", Font.BOLD, 14));

		label_7 = new JLabel();
		label_7.setBounds(12, 74, 38, 28);
		panel_1.add(label_7);
		label_7.setText("RS");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setFont(new Font("Verdana", Font.BOLD, 14));

		lblTotalRs = new JLabel("0.0");
		lblTotalRs.setBounds(73, 74, 156, 28);
		panel_1.add(lblTotalRs);
		lblTotalRs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalRs.setForeground(Color.RED);
		lblTotalRs.setFont(new Font("Dialog", Font.BOLD, 14));

		label_8 = new JLabel();
		label_8.setBounds(12, 108, 38, 28);
		panel_1.add(label_8);
		label_8.setText("PS");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setFont(new Font("Verdana", Font.BOLD, 14));

		lblTotalPs = new JLabel("0.0");
		lblTotalPs.setBounds(73, 108, 156, 28);
		panel_1.add(lblTotalPs);
		lblTotalPs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPs.setForeground(Color.RED);
		lblTotalPs.setFont(new Font("Dialog", Font.BOLD, 14));

		panel_3 = new JPanel();
		panel_3.setBounds(751, 415, 246, 149);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		lblTotalItems = new JLabel("TOTAL ITEMS:");
		lblTotalItems.setBounds(12, 5, 101, 23);
		panel_3.add(lblTotalItems);

		tfTotalItems = new JLabel("0");
		tfTotalItems.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalItems.setFont(new Font("Verdana", Font.BOLD, 14));
		tfTotalItems.setBounds(133, 5, 101, 23);
		panel_3.add(tfTotalItems);
		tfTotalItems.setForeground(Color.BLACK);

		lblF_1 = new JLabel("F3");
		lblF_1.setForeground(Color.BLUE);
		lblF_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblF_1.setBounds(12, 33, 34, 23);
		panel_3.add(lblF_1);

		lblFQuitar = new JLabel("Quitar Producto");
		lblFQuitar.setFont(new Font("Verdana", Font.BOLD, 14));
		lblFQuitar.setBounds(64, 33, 148, 23);
		panel_3.add(lblFQuitar);

		lblBusca = new JLabel("Buscar Producto");
		lblBusca.setFont(new Font("Verdana", Font.BOLD, 14));
		lblBusca.setBounds(64, 61, 148, 23);
		panel_3.add(lblBusca);

		lblF_2 = new JLabel("F4");
		lblF_2.setForeground(Color.BLUE);
		lblF_2.setFont(new Font("Verdana", Font.BOLD, 14));
		lblF_2.setBounds(12, 61, 34, 23);
		panel_3.add(lblF_2);

		lblF = new JLabel("Pago");
		lblF.setFont(new Font("Verdana", Font.BOLD, 14));
		lblF.setBounds(64, 89, 148, 23);
		panel_3.add(lblF);

		lblF_3 = new JLabel("F10");
		lblF_3.setForeground(Color.BLUE);
		lblF_3.setFont(new Font("Verdana", Font.BOLD, 14));
		lblF_3.setBounds(12, 89, 34, 23);
		panel_3.add(lblF_3);

		lblEscLimpiar = new JLabel("Limpiar");
		lblEscLimpiar.setFont(new Font("Verdana", Font.BOLD, 14));
		lblEscLimpiar.setBounds(64, 117, 148, 23);
		panel_3.add(lblEscLimpiar);

		lblEsc = new JLabel("ESC");
		lblEsc.setFont(new Font("Verdana", Font.BOLD, 14));
		lblEsc.setForeground(Color.RED);
		lblEsc.setBounds(12, 117, 34, 23);
		panel_3.add(lblEsc);
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
	}

	private void getConfig() {
		Optional<Configuracion> c = configService.findByUserId(new Usuario(GlobalVars.USER_ID));

		if (c.isPresent()) {
			Configuracion config = c.get();
			tfCajaID.setText(String.valueOf(config.getCajaIdPDV()));
			depositoId = Long.valueOf(String.valueOf(config.getDepositoIdPDV()));
		} else {
			tfCajaID.setText("1");
			depositoId = 1L;
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
			// tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
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

	private void getCotizaciones() {
		cotizacionModel.clear();
		// monedaValorModel.clear();

		List<Cotizacion> cotizaciones = new ArrayList<>();
		List<MonedaValor> valores = new ArrayList<>();
		List<Moneda> monedas = monedaService.findAll();

		if (!monedas.isEmpty()) {
			for (Moneda e : monedas) {
				Optional<Cotizacion> cot = cotizacionService.findCotizacionByMoneda(e.getId());

				valores.add(new MonedaValor(e.getId(), e.getSigla(), e.getOperacion()));

				if (cot.isPresent()) {
					cotizaciones.add(cot.get());

					if (e.getEsBase() == 1) {
						monedaBaseCodigo = e.getId();
						// monedaBaseOperacion = e.getOperacion();
						// monedaBaseValor = cot.get().getValorVenta();
					}

					if (e.getId() == 1) { // DS
						mUs = cot.get().getValorVenta();
					} else if (e.getId() == 2) { // RS
						mRs = cot.get().getValorVenta();
					} else if (e.getId() == 3) { // PS
						mPs = cot.get().getValorVenta();
					} else if (e.getId() == 4) { // GS
						mGs = cot.get().getValorVenta();
					}
				}
			}

			cotizacionModel.addEntities(cotizaciones);
		}
	}

	private void abandonarNota() {
//		Integer respuesta = JOptionPane.showConfirmDialog(null, "Abandonar nota.?");
//
//		if (respuesta == 0) {
		clearForm();
//		} else {
//			tfProductoID.requestFocus();
//		}
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
		venta.setVencimiento(new Date());
		venta.setComprobante("SIN COMPROBANTE");
		venta.setCondicion(0);
		venta.setVendedor(new Usuario(GlobalVars.USER_ID));
		venta.setCliente(new Cliente(Long.valueOf(0)));
		venta.setClienteNombre("CLIENTE OCASIONAL");
		venta.setClienteRuc("44444401");
		venta.setClienteDireccion("SIN DIRECCION");
		venta.setDeposito(new Deposito(depositoId));
		venta.setSituacion("PROCESADO");
		venta.setObs("PDV");
		venta.setSituacion("PROCESADO");
		venta.setCaja(new Caja(Long.valueOf(tfCajaID.getText())));
		venta.setCantItem(tfTotalItems.getText().isEmpty() ? 1 : Integer.parseInt(tfTotalItems.getText()));
		venta.setTotalGravada10(
				lblTotalGs.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(lblTotalGs.getText())); // SUBTOTAL
		venta.setTotalGeneral(lblTotalGs.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(lblTotalGs.getText())); // TOTAL
																															// //
																															// GENERAL
		venta.setItems(itemTableModel.getEntities());

		return venta;
	}

	private VentaDetalle getItem() {
		Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
		Double precio = FormatearValor.stringToDouble(tfPrecio.getText());

		VentaDetalle item = new VentaDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(cantidad);
		item.setPrecio(precio);
		item.setSubtotal(cantidad * precio);

		return item;
	}

	private void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfProductoID.requestFocus();
	}

	private void setTotals(Double cantItem, Double total) {
		if (monedaBaseCodigo == 1) { // Us
			lblTotalGs.setText(FormatearValor.doubleAString(total * mGs));
			lblTotalUs.setText(FormatearValor.doubleAString(total / mUs));
			lblTotalRs.setText(FormatearValor.doubleAString(total * mRs));
			lblTotalPs.setText(FormatearValor.doubleAString(total * mPs));
		} else if (monedaBaseCodigo == 2) { // RS
			lblTotalGs.setText(FormatearValor.doubleAString(total * mGs));
			lblTotalUs.setText(FormatearValor.doubleAString(total * mUs));
			lblTotalRs.setText(FormatearValor.doubleAString(total / mRs));
			lblTotalPs.setText(FormatearValor.doubleAString(total * mPs));
		} else if (monedaBaseCodigo == 3) { // PS
			lblTotalGs.setText(FormatearValor.doubleAString(total * mGs));
			lblTotalUs.setText(FormatearValor.doubleAString(total * mUs));
			lblTotalRs.setText(FormatearValor.doubleAString(total * mRs));
			lblTotalPs.setText(FormatearValor.doubleAString(total / mPs));
		} else if (monedaBaseCodigo == 4) { // GS
			lblTotalGs.setText(FormatearValor.doubleAString(total / mGs));
			lblTotalUs.setText(FormatearValor.doubleAString(total * mUs));
			lblTotalRs.setText(FormatearValor.doubleAString(total * mRs));
			lblTotalPs.setText(FormatearValor.doubleAString(total * mPs));
		}

		if (cantItem != 0) {
			tfTotalItems.setText(FormatearValor.doubleAString(cantItem));
		}
	}

	private void clearForm() {
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");

		lblTotalGs.setText("");
		lblTotalUs.setText("");
		lblTotalRs.setText("");
		lblTotalPs.setText("");

		tfTotalItems.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}
	}

	private void openClientePDV() {
		pdvCliente.setVisible(true);
		pdvCliente.cargarCliente(getVentaFrom(), tfCajaID.getText(), lblTotalGs.getText(), lblTotalUs.getText(),
				lblTotalRs.getText(), lblTotalRs.getText(), tfVentaId.getText(), mGs, mUs, mRs, mPs);

		// clearForm();
	}

	private void showDialog(int code) {
		switch (code) {
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.getProductos();
			productoDialog.setVisible(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void getEntity(Producto producto) {
		if (producto != null) {
			Double precioUnit = producto.getPrecioVentaA() != null ? producto.getPrecioVentaA() : 0d;
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfPrecio.setText(FormatearValor.doubleAString(precioUnit));
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();
		}
	}

	@Override
	public void getEntity(Venta v) {
		if (v != null) {
			setVenta(v);
		}
	}

	public void setVenta(Venta v) {
		itemTableModel.addEntities(v.getItems());
	}

	public void newVenta() {
		long max = ventaService.getRowCount();
		tfVentaId.setText(String.valueOf(max + 1));
	}

	private void findProductoByFilter(String filter) {
		Optional<Producto> producto = productoService.findByFilter(filter);

		if (producto.isPresent()) {
			String nombre = producto.get().getDescripcion();
			tfDescripcion.setText(nombre);

			tfPrecio.setText(producto.get().getPrecioVentaA() != null
					? FormatearValor.doubleAString(producto.get().getPrecioVentaA())
					: "0");
			// cantidad default
			tfCantidad.setText("1");
			// tfCantidad.requestFocus();

			addItem();

			// Set si el producto es fraccionado
			// if (producto.get().getEsFraccionado() == 1) {
			// isFraccionado = true;
			// }
		} else {
			Notifications.showAlert("Producto no existe con este codigo.!");
			tfProductoID.selectAll();
			tfProductoID.requestFocus();
		}
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
		if (isValidItem()) {
			int fila = getDuplicateItemIndex();
//			Long productoId = tfProductoID.getText().isEmpty() ? 1:Long.valueOf(tfProductoID.getText());
//			int depositoId = tfDepositoID.getText().isEmpty() ? 1:Integer.parseInt(tfDepositoID.getText());
//			Double cantidad = tfCantidad.getText().isEmpty() ? 0:FormatearValor.stringADouble(tfCantidad.getText());

			// verifica si el Item ya existe en la grilla
			if (fila != -1) {
//				Integer respuesta = JOptionPane.showConfirmDialog(null, "Registro ya existe en la grilla, desea actualizar los datos?");

//				if (respuesta == 0) {
				// Double cantAnterior =
				// Double.valueOf(String.valueOf(tbProductos.getValueAt(fila, 1)));

				itemTableModel.removeRow(fila);
				itemTableModel.addEntity(getItem());

				calculateItem();
//					addItemCantBloq(productoId, cantidad, cantAnterior, depositoId);
//				} else {
//					tfProductoID.requestFocus();
//				}
			} else {
				// sin duplicados
				itemTableModel.addEntity(getItem());
				calculateItem();
				// addItemCantBloq(productoId, cantidad, 0d, depositoId);
			}
		}

		clearItem();
	}

	private void removeItem() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			// VentaDetalle item = itemTableModel.getEntityByRow(selectedRow);
			itemTableModel.removeRow(selectedRow);

			// removeItemDepBloq(item.getCantidad(), item.getProductoId(),
			// Integer.parseInt(tfDepositoID.getText()));
			clearItem();
			calculateItem();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}
	}

	private void calculateItem() {
		Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
		Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotal()).sum();
		setTotals(cantItem, total);
	}
}
