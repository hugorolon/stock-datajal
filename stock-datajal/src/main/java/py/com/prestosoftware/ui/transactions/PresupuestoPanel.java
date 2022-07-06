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

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.ClientePais;
import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Presupuesto;
import py.com.prestosoftware.data.models.PresupuestoDetalle;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.ClientePaisService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.PresupuestoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.validations.PresupuestoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.DigitoVerificador;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.reports.ImpresionUtil;
import py.com.prestosoftware.ui.search.ClienteDialog;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.PresupuestoInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.VendedorDialog;
import py.com.prestosoftware.ui.search.VendedorInterfaz;
import py.com.prestosoftware.ui.table.PresupuestoTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class PresupuestoPanel extends JFrame
		implements ClienteInterfaz, VendedorInterfaz, ProductoInterfaz, PresupuestoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int CLIENTE_CODE = 1;
	private static final int VENDEDOR_CODE = 2;
//	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4;

	private JLabel lblRuc, lblDireccion, lblBuscadorDeVentas;
	private JTextField tfClienteNombre, tfVendedor, tfDescripcion, tfVentaId;
	private JTextField tfClienteID, tfPrecioTotal, tfPrecio, tfVendedorID;
	private JTextField tfCantidad, tfTotalItems, tfDescuento;
	private JTextField tfTotal, tfDepositoID,  tfClienteRuc;
	private JTextField tfClienteDireccion, tfFlete, tfCondPago, tfProductoID;
	private JButton btnAdd, btnRemove, btnGuardar, btnReImpresion, btnCancelar, btnCerrar;
	private JPanel pnlTotales;
	private JTable tbProductos;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_4;
	private JLabel lblCamposObligatorios;
	private JLabel label_5;
	private JTextField tfDvRuc;

	private PresupuestoTableModel itemTableModel;
	private ClienteDialog clientDialog;
	private VendedorDialog vendedorDialog;
	private ProductoDialog productoDialog;
	private PresupuestoService presupuestoService;
	private PresupuestoValidator presupuestoValidator;
	private ClienteService clienteService;
	private ClientePaisService clientePaisService;
	private UsuarioService vendedorService;
	private ProductoService productoService;
	private ConfiguracionService configService;

	public PresupuestoPanel(PresupuestoTableModel itemTableModel, ClienteDialog clientDialog,
			VendedorDialog vendedorDialog, ProductoDialog productoDialog, PresupuestoValidator ventaValidator,
			PresupuestoService pService, ClienteService clienteService, UsuarioService vendedorService,
			DepositoService depositoService, ProductoService productoService, ClientePaisService clientePaisService, ConfiguracionService configService) {
		this.itemTableModel = itemTableModel;
		this.clientDialog = clientDialog;
		this.vendedorDialog = vendedorDialog;
		this.productoDialog = productoDialog;
		this.presupuestoValidator = ventaValidator;
		this.presupuestoService = pService;
		this.clienteService = clienteService;
		this.clientePaisService=clientePaisService;
		this.vendedorService = vendedorService;
		this.productoService = productoService;
		this.configService = configService;
		
		setSize(921, 649);
		setTitle("PRESUPUESTOS");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();

		setTableSize();
//        calculateVencimiento();
	}

	private void initComponents() {
		getContentPane().setLayout(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(9, 122, 902, 346);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("ITEMS", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JLabel lblCodigo = new JLabel("COD");
		lblCodigo.setBounds(6, 10, 35, 30);
		pnlProducto.add(lblCodigo);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setBounds(164, 10, 337, 30);
		pnlProducto.add(lblDescripcion);

		JLabel lblSubtotal = new JLabel("PRECIO TOTAL");
		lblSubtotal.setBounds(617, 10, 115, 30);
		pnlProducto.add(lblSubtotal);

		JLabel lblPrecio = new JLabel("PRECIO UNIT.");
		lblPrecio.setBounds(502, 10, 91, 30);
		pnlProducto.add(lblPrecio);

		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(164, 39, 337, 30);
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
		tfPrecioTotal.setBounds(617, 39, 146, 30);
		pnlProducto.add(tfPrecioTotal);

		tfPrecio = new JTextField();
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
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
		tfPrecio.setColumns(10);
		tfPrecio.setBounds(502, 39, 115, 30);
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
					// DEPOSITO

				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearForm();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProductoById(Long.parseLong(tfProductoID.getText()));
					} else {
						// Notifications.showAlert("Digite el codigo del producto o consulta -> F4");
						showDialog(PRODUCTO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfCantidad.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
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

		btnRemove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) {
					removeItem();
				}
			}
		});
		btnRemove.setBounds(826, 39, 56, 30);
		pnlProducto.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(6, 81, 876, 228);
		pnlProducto.add(scrollProducto);

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
					btnGuardar.requestFocus();
				}
			}
		});
		scrollProducto.setViewportView(tbProductos);

		tfCantidad = new JTextField();
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
						Notifications.showAlert("Verifique la cantidad");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F6) {
					// consulta de saldo stock
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
		tfCantidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfCantidad.setColumns(10);
		tfCantidad.setBounds(77, 39, 86, 30);
		pnlProducto.add(tfCantidad);

		JLabel lblCantidad = new JLabel("CANT.");
		lblCantidad.setBounds(77, 10, 86, 30);
		pnlProducto.add(lblCantidad);

		btnAdd = new JButton("+");
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
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) {
					addItem();
				}
			}
		});
		btnAdd.setBounds(767, 39, 57, 30);
		pnlProducto.add(btnAdd);

//        cbListPrecio = new JComboBox<ProductoPrecio>(precioComboBoxModel);
//        cbListPrecio.addItemListener(new ItemListener() {
//        	public void itemStateChanged(ItemEvent e) {
//        		if (e.getStateChange() == ItemEvent.SELECTED) {
//        			ProductoPrecio item = (ProductoPrecio) e.getItem();
//        			tfPrecio.setText(FormatearValor.doubleAString(item.getValor()));
//        	    }
//        	}
//        });
//        cbListPrecio.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        	}
//        });
//        cbListPrecio.addItemListener(new ItemListener() {
//        	public void itemStateChanged(ItemEvent e) {
//        		if (e.getStateChange() != 0) {
//        			System.out.println("Precio del Producto");
//        			System.out.println(cbListPrecio.getItemAt(ItemEvent.SELECTED).getValor());
//					tfPrecio.setText(FormatearValor.doubleAString(cbListPrecio.getItemAt(ItemEvent.SELECTED).getValor()));
//				}
//        	}
//        });
//        cbListPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
//        cbListPrecio.setBounds(405, 39, 91, 30);
//        pnlProducto.add(cbListPrecio);

		label_5 = new JLabel("*");
		label_5.setVerticalAlignment(SwingConstants.BOTTOM);
		label_5.setToolTipText("Campos obligatorios");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Dialog", Font.BOLD, 20));
		label_5.setBounds(40, 10, 14, 30);
		pnlProducto.add(label_5);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(9, 12, 902, 105);
		panel_3.setBorder(new TitledBorder(null, "SELECCIONE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 886, 79);
		panel_3.add(pnlCliente);
		pnlCliente.setLayout(null);

		JLabel lblClienteID = new JLabel("CLIENTE:");
		lblClienteID.setBounds(156, 6, 57, 30);
		pnlCliente.add(lblClienteID);

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
						Long clienteId = Long.parseLong(tfClienteID.getText());
						findClientById(clienteId);
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

//        JLabel lblDeposito = new JLabel("DEP.:");
//        lblDeposito.setBounds(544, 41, 40, 30);
//        pnlCliente.add(lblDeposito);

//        tfDepositoID = new JTextField();
//        tfDepositoID.addFocusListener(new FocusAdapter() {
//        	@Override
//        	public void focusGained(FocusEvent e) {
//        		tfDepositoID.selectAll();
//        	}
//        });
//        tfDepositoID.addKeyListener(new KeyAdapter() {
//        	@Override
//        	public void keyPressed(KeyEvent e) {
//        		if (e.getKeyCode() == KeyEvent.VK_F4) {
//					showDialog(DEPOSITO_CODE);
//				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					if (!tfDepositoID.getText().isEmpty()) {
//						findDepositoById(Long.parseLong(tfDepositoID.getText()));
//					} else {
//						showDialog(DEPOSITO_CODE);
//					}
//				}
//        	}
//        	@Override
//        	public void keyTyped(KeyEvent e) {
//        		Util.validateNumero(e);
//        	}
//        });
//        tfDepositoID.setColumns(10);
//        tfDepositoID.setBounds(596, 42, 58, 30);
//        pnlCliente.add(tfDepositoID);
//        
//        tfDeposito = new JTextField();
//        tfDeposito.setEditable(false);
//        tfDeposito.setToolTipText("Nombre del Vendedor");
//        tfDeposito.setColumns(10);
//        tfDeposito.setBounds(657, 41, 213, 30);
//        pnlCliente.add(tfDeposito);

		lblRuc = new JLabel("RUC:");
		lblRuc.setVisible(false);
		lblRuc.setEnabled(false);
		lblRuc.setBounds(0, 41, 42, 30);
		pnlCliente.add(lblRuc);

		tfClienteRuc = new JTextField();
		//tfClienteRuc.setEnabled(false);
		tfClienteRuc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					calculateDV();
					tfVendedorID.requestFocus();
				}
			}
		});
		
		
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
							tfProductoID.requestFocus();
					}
			}
		});
		
		//tfClienteRuc.setVisible(false);
		tfClienteRuc.setColumns(10);
		tfClienteRuc.setBounds(43, 42, 75, 30);
		pnlCliente.add(tfClienteRuc);

		lblDireccion = new JLabel("DIR:");
		lblDireccion.setVisible(false);
		lblDireccion.setEnabled(false);
		lblDireccion.setBounds(544, 6, 45, 30);
		pnlCliente.add(lblDireccion);

		tfClienteDireccion = new JTextField();
		tfClienteDireccion.setEnabled(false);
		tfClienteDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClienteRuc.requestFocus();
				}
			}
		});
		tfClienteDireccion.setVisible(false);
		tfClienteDireccion.setColumns(10);
		tfClienteDireccion.setBounds(596, 6, 274, 30);
		pnlCliente.add(tfClienteDireccion);

		lblBuscadorDeVentas = new JLabel("NOTA:");
		lblBuscadorDeVentas.setBounds(0, 6, 42, 30);
		pnlCliente.add(lblBuscadorDeVentas);

		tfVentaId = new JTextField();
		tfVentaId.setBounds(43, 7, 107, 30);
		pnlCliente.add(tfVentaId);
		tfVentaId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfVentaId.getText().isEmpty()) {
						findPresupuesto(tfVentaId.getText());
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVentaId.setColumns(10);

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

//        label_3 = new JLabel("*");
//        label_3.setVerticalAlignment(SwingConstants.BOTTOM);
//        label_3.setToolTipText("Campos obligatorios");
//        label_3.setHorizontalAlignment(SwingConstants.CENTER);
//        label_3.setForeground(Color.RED);
//        label_3.setFont(new Font("Dialog", Font.BOLD, 20));
//        label_3.setBounds(581, 42, 14, 30);
//        pnlCliente.add(label_3);

		tfDvRuc = new JTextField();
		tfDvRuc.setEditable(false);
		tfDvRuc.setBounds(123, 42, 27, 30);
		pnlCliente.add(tfDvRuc);
		tfDvRuc.setColumns(10);

		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(9, 537, 902, 35);
		getContentPane().add(pnlBotonera);

		btnGuardar = new JButton("Guardar");
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
			}
		});
		pnlBotonera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					clearForm();
					newPresupuesto();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		
		btnReImpresion = new JButton("Re Impresión");
		btnReImpresion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirPresupuesto();
			}
		});
		pnlBotonera.add(btnReImpresion);
		pnlBotonera.add(btnCancelar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotonera.add(btnCerrar);

		pnlTotales = new JPanel();
		pnlTotales.setBounds(9, 478, 902, 49);
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


		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(690, 12, 51, 30);
		pnlTotales.add(lblTotal);

		tfTotal = new JTextField();
		tfTotal.setBounds(746, 12, 130, 30);
		pnlTotales.add(tfTotal);
		tfTotal.setEditable(false);
		tfTotal.setFont(new Font("Arial", Font.BOLD, 16));
		tfTotal.setForeground(Color.RED);
		tfTotal.setColumns(10);


		label_4 = new JLabel("*");
		label_4.setBounds(209, 12, 14, 25);
		pnlTotales.add(label_4);
		label_4.setVerticalAlignment(SwingConstants.BOTTOM);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setToolTipText("Campos obligatorios");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));

		lblCamposObligatorios = new JLabel("Campos Obligatorios");
		lblCamposObligatorios.setBounds(229, 12, 299, 25);
		pnlTotales.add(lblCamposObligatorios);
		lblCamposObligatorios.setFont(new Font("Dialog", Font.BOLD, 20));
//		Optional<Usuario> usuario = vendedorService.findById(GlobalVars.USER_ID);
//		tfVendedorID.setText(usuario.get().getId().toString());
//		tfVendedor.setText(usuario.get().getUsuario());
	}

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
		}
	}
	
	private void getItemSelected() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			PresupuestoDetalle item = itemTableModel.getEntityByRow(selectedRow);

			tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfPrecio.setText(FormatearValor.doubleAString(item.getPrecio()));
			tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
		}
	}

	private void setTableSize() {
		tbProductos.getColumnModel().getColumn(0).setMinWidth(120);
		tbProductos.getColumnModel().getColumn(0).setMaxWidth(120);
		tbProductos.getColumnModel().getColumn(1).setMinWidth(120);
		tbProductos.getColumnModel().getColumn(1).setMaxWidth(120);
		tbProductos.getColumnModel().getColumn(2).setMinWidth(300);
		tbProductos.getColumnModel().getColumn(2).setMaxWidth(300);
		tbProductos.getColumnModel().getColumn(3).setMinWidth(150);
		tbProductos.getColumnModel().getColumn(3).setMaxWidth(150);
		tbProductos.getColumnModel().getColumn(4).setMinWidth(160);
		tbProductos.getColumnModel().getColumn(4).setMaxWidth(160);
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

	private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
		itemTableModel.removeRow(fila);

		Long productoId = Long.valueOf(tfProductoID.getText());
		Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());
		Double cantidadTotal = Double.valueOf(cantAnteriorItem) + cantidad;
		Double precio = FormatearValor.stringADouble(tfPrecio.getText());

		tfCantidad.setText(FormatearValor.doubleAString(cantidadTotal));
		tfPrecioTotal.setText(FormatearValor.doubleAString(cantidadTotal * precio));

		Optional<Producto> p = productoService.findById(productoId);

		if (p.isPresent()) {
			Double stock = p.get().getStock() != null ? p.get().getStock() : 0;

			if (cantidadTotal <= stock) {
				addItemToList();
				calculateItem();
			} else {
				tfProductoID.requestFocus();
				Notifications
						.showAlert("No tiene suficiente Stock para este Producto. " + "Stock actual es de: " + stock);
			}
		}

		clearItem();
	}

	private void calculateDV() {
		tfDvRuc.setText(String.valueOf(DigitoVerificador.calcular(tfClienteRuc.getText(), 11)));
	}

	private void findClientByRuc(String ciRuc) {
		try {
			Optional<Cliente> cliente = Optional.ofNullable(clienteService.findByRuc(ciRuc));
			if (cliente.isPresent()) {
				setCliente(cliente);
			} else {
				Notifications.showAlert("Cliente no registrado, al confirmar la venta estará registrado!");
				Optional<ClientePais> clientePais = Optional.of(clientePaisService.findByRuc(ciRuc));
				setClienteSET(clientePais);
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas en la busqueda, intente nuevamente!");
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
			tfClienteNombre.setEnabled(false);
		}
	}
	
	private void setCliente(Optional<Cliente> cliente) {
		if (cliente.isPresent()) {
			String nombre = cliente.get().getRazonSocial();
			String ciruc = cliente.get().getCiruc();
			String direccion = cliente.get().getDireccion();

			lblRuc.setVisible(true);
			lblDireccion.setVisible(true);
			tfClienteRuc.setVisible(true);
			tfClienteDireccion.setVisible(true);

			tfClienteNombre.setText(nombre);
			tfClienteRuc.setText(ciruc);
			tfClienteDireccion.setText(direccion);

			if (cliente.get().getId() == 0) {
				// habilitar nombre, ruc, direccion
				tfClienteNombre.setEnabled(true);
				tfClienteRuc.setEnabled(true);
				tfClienteDireccion.setEnabled(true);
				tfClienteNombre.requestFocus();

				tfCondPago.setEditable(false);
				tfCondPago.setText("0");
				// calculateVencimiento();
			} else {
				tfVendedorID.requestFocus();
			}
		}
	}

	private void setVendedor(Optional<Usuario> vendedor) {
		if (vendedor.isPresent()) {
			String nombre = vendedor.get().getUsuario();
			tfVendedor.setText(nombre);
			tfProductoID.requestFocus();
		}
	}

//    private void setDeposito(Optional<Deposito> deposito) {
//    	if (deposito.isPresent()) {
//    		String nombre = deposito.get().getNombre();
//    		tfDeposito.setText(nombre);
//    		tfProductoID.requestFocus();
//    	}
//    }

	private void setProducto(Optional<Producto> producto) {
		if (producto.isPresent()) {
			String nombre = producto.get().getDescripcion();
			tfDescripcion.setText(nombre);
			// List<ProductoPrecio> productoPrecios = producto.get().getPrecios();
			// precioComboBoxModel.addElements(productoPrecios);
			// tfPrecio.setText(FormatearValor.doubleAString(cbListPrecio.getItemAt(0).getValor()));

			tfPrecio.setText(producto.get().getPrecioVentaC() != null
					? FormatearValor.doubleAString(producto.get().getPrecioVentaC())
					: "0");
			// cantidad default
			tfCantidad.setText("1");
			tfCantidad.requestFocus();
		}
	}

	private Presupuesto getFormValue() {
		Presupuesto p = new Presupuesto();
		p.setComprobante("SIN COMPROBANTE");
//    	p.setCondicion(Integer.valueOf(tfCondPago.getText()));   
		p.setVendedor(new Usuario(Long.valueOf(tfVendedorID.getText())));
		p.setCliente(new Cliente(Long.valueOf(tfClienteID.getText())));
//    	p.setClienteNombre(tfClienteNombre.getText());
//    	p.setDeposito(new Deposito(Long.valueOf(tfDepositoID.getText())));
//    	p.setMoneda(new Moneda(1L)); //REFACTORAR POR MONEDA BASE
		p.setSituacion("ACTIVO");
//    	p.setObs(tfObs.getText());
		p.setSituacion("PENDIENTE");
		p.setCantItem(tfTotalItems.getText().isEmpty() ? 0 : Integer.parseInt(tfTotalItems.getText()));
		// totales
//    	p.setTotalGravada10(tfSubtotal.getText().isEmpty() ? 0 : Double.valueOf(tfSubtotal.getText())); 	//SUBTOTAL
//    	p.setTotalDescuento(tfDescuento.getText().isEmpty() ? 0 : Double.valueOf(tfDescuento.getText())); 	//DESCUENTO
		p.setTotalGeneral(tfTotal.getText().isEmpty() ? 0 : Double.valueOf(tfTotal.getText())); // TOTAL GENERAL
//    	p.setTotalFlete(tfFlete.getText().isEmpty() ? 0 : Double.valueOf(tfFlete.getText()));				//FLETE

		return p;
	}

	private PresupuestoDetalle getItem() {
		PresupuestoDetalle item = new PresupuestoDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(FormatearValor.stringToDouble(tfCantidad.getText()));
		item.setPrecio(FormatearValor.stringToDouble(tfPrecio.getText()));
		item.setSubtotal(FormatearValor.stringToDouble(tfPrecioTotal.getText()));

		return item;
	}

	private void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
		tfProductoID.requestFocus();
	}

	private void setTotals(Double cantItem, Double total) {
		// Double descuento = tfDescuento.getText().isEmpty() ? 0d :
		// FormatearValor.stringToDouble(tfDescuento.getText());
		// Double flete = tfFlete.getText().isEmpty() ? 0d :
		// FormatearValor.stringToDouble(tfFlete.getText());
		// Double totalGeneral = (total + flete) - descuento;

		// tfSubtotal.setText(FormatearValor.doubleAString(total));
		tfTotal.setText(FormatearValor.doubleAString(total));

		if (cantItem != 0) {
			tfTotalItems.setText(FormatearValor.doubleAString(cantItem));
		}
	}

	private void clearForm() {
		tfClienteID.setText("");
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfClienteDireccion.setText("");
		tfVendedorID.setText("");
		tfVendedor.setText("");
//    	tfDepositoID.setText("");
//    	tfDeposito.setText("");
		tfProductoID.setText("");
		tfCantidad.setText("");
		tfDescripcion.setText("");
		tfPrecio.setText("");
		tfPrecioTotal.setText("");
//    	tfObs.setText("");
		tfTotal.setText("");
		tfTotalItems.setText("");
//    	tfDescuento.setText("");
//    	tfFlete.setText("");
//    	tfVence.setText("");
//    	tfSubtotal.setText("");
//    	tfCondPago.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		//lblRuc.setVisible(false);
		//lblDireccion.setVisible(false);
		//tfClienteRuc.setVisible(false);
		//tfClienteDireccion.setVisible(false);

		tfClienteNombre.setEnabled(false);
		//tfClienteRuc.setEnabled(false);
		tfClienteDireccion.setEnabled(false);

//		tfCondPago.setEditable(true);
//		precioComboBoxModel.clear();

		tfClienteID.requestFocus();
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

	public JTextField getTfFlete() {
		return tfFlete;
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

//    private void calculateVencimiento() {
//    	String fecha = Fechas.dateUtilAStringDDMMAAAA(new Date());
//		Date fechaVenc = Fechas.sumarFecha(0, 0, 0, fecha);
//		tfVence.setText(Fechas.formatoDDMMAAAA(fechaVenc));
//    }

	private void calculatePrecioTotal() {
		Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
		Double precioUnit = FormatearValor.stringToDouble(tfPrecio.getText());
		Double precioTotal = cantidad * precioUnit;

		tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));
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
//			case DEPOSITO_CODE:
//				depositoDialog.setInterfaz(this);
//				depositoDialog.setVisible(true);
//				break;
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.setVisible(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void getEntity(Cliente cliente) {
		if (cliente != null) {
			tfClienteID.setText(String.valueOf(cliente.getId()));
			tfClienteNombre.setText(cliente.getRazonSocial());
			tfClienteRuc.setText(cliente.getCiruc());
			tfClienteDireccion.setText(cliente.getDireccion());
			tfVendedorID.requestFocus();
		}
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
	public void getEntity(Producto producto) {
		if (producto != null) {
			Double precioUnit = producto.getPrecioVentaA() != null ? producto.getPrecioVentaA() : 0d;
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfPrecio.setText(FormatearValor.doubleAString(precioUnit));
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();

//			if (producto.getPrecios() != null && !producto.getPrecios().isEmpty()) {
//				precioComboBoxModel.addElements(producto.getPrecios());
//				tfPrecio.setText(FormatearValor.doubleAString(cbListPrecio.getItemAt(0).getValor()));
//			}
		}
	}

	@Override
	public void getEntity(Presupuesto p) {
		if (p != null) {
			setPresupuesto(p);
		}
	}

	public void setPresupuesto(Presupuesto p) {
		tfClienteID.setText(String.valueOf(p.getCliente().getId()));
//		tfClienteNombre.setText(p.getClienteNombre());
		Optional<Cliente> c= clienteService.findById(p.getCliente().getId());
		tfClienteRuc.setText(c.get().getCiruc());
		tfDvRuc.setText(c.get().getDvruc());
		tfClienteNombre.setText(c.get().getNombre());
		tfClienteDireccion.setText(c.get().getDireccion());
		tfTotalItems.setText(String.valueOf(p.getCantItem()));
//		tfCondPago.setText(String.valueOf(p.getCondicion()));
//		tfDepositoID.setText(String.valueOf(p.getDeposito().getId()));
		//tfDeposito.setText("");
//		tfDescuento.setText(String.valueOf(p.getTotalDescuento()));
//		tfFlete.setText(String.valueOf(p.getTotalFlete()));
//		tfSubtotal.setText(String.valueOf(p.getTotalGravada10()));
		tfTotal.setText(String.valueOf(p.getTotalGeneral().doubleValue()));
//		tfObs.setText(String.valueOf(p.getObs()));
		//List<DetallePresupuesto> listaDetalles =presupuestoService.getPresupuesto
		itemTableModel.addEntities(p.getItems());
		calculateItem();
	}

//	private void updateStockProduct(List<VentaDetalle> items) {
//    	List<Producto> productos = new ArrayList<>();
//    	
//    	items.forEach(e -> {
//    		Optional<Producto> pOptional = productoService.findById(e.getProductoId());
//    		
//    		if (pOptional.isPresent()) {
//    			Producto p = pOptional.get();
//    			Double newQt = p.getStock() - e.getCantidad();
//				p.setStock(newQt);
//				productos.add(p);
//    		}	
//    	});
//    	
//    	productoService.updateStock(productos);
//	}

	private void save() {
		Integer print=null;
		if (validateCabezera()) { // && validateItems(itemTableModel.getEntities())
			Presupuesto p = getFormValue();
			p.setItems(itemTableModel.getEntities());

			Optional<ValidationError> errors = presupuestoValidator.validate(p);

			if (errors.isPresent()) {
				ValidationError validationError = errors.get();
				Notifications.showFormValidationAlert(validationError.getMessage());
			} else {
				try {
					Presupuesto pre = presupuestoService.save(p);

					if (pre != null) {
						// updateStockProduct(v.getItems());
						Notifications.showAlert("Presupuesto registrado con exito.!");
						print = JOptionPane.showConfirmDialog(this, "IMPRIMIR", "AVISO - AGROPROGRESO",
								JOptionPane.OK_CANCEL_OPTION);
					}
					if (print == 0)
						imprimirPresupuesto();
				} catch (Exception e) {
					Notifications.showAlert("Ocurrió un error en Venta!, intente nuevamente");

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				clearForm();
				newPresupuesto();
			}
		}
	}

	public void imprimirPresupuesto() {

		ImpresionUtil.performPresupuesto(tfClienteNombre.getText(), tfClienteRuc.getText() + "-" + tfDvRuc.getText(),
				"(0983) 518 217", tfClienteDireccion.getText(), tfVentaId.getText(),
				tfVendedor.getText().isEmpty() ? GlobalVars.USER : tfVendedor.getText(),
				itemTableModel.getEntities());
		clearForm();
		newPresupuesto();
	}
	
	private void setDataVendedor(Optional<Usuario> vendedor) {
		setVendedor(vendedor);
	}

	private void setDataProducto(Optional<Producto> producto) {
		setProducto(producto);
	}

//    private void setDataDeposito(Optional<Deposito> deposito) {
//    	setDeposito(deposito);
//    }

	private boolean validateCabezera() {
		// validar cliente, deposito, vendedor
		if (tfClienteID.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Cliente es obligatorio");
			tfClienteID.requestFocus();
			return false;
		} else if (tfVendedorID.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Vendedor es obligatorio");
			tfVendedorID.requestFocus();
			return false;
//		} else if (tfDepositoID.getText().isEmpty()) {
//			Notifications.showAlert("El codigo del Deposito es obligatorio");
//			tfDepositoID.requestFocus();
//    		return false;
		}

		Optional<Cliente> cliente = clienteService.findById(Long.valueOf(tfClienteID.getText()));

		if (!cliente.isPresent()) {
			Notifications.showAlert("El codigo del Cliente es obligatorio");
			tfClienteID.requestFocus();
			return false;
		}

		Optional<Usuario> usuairo = vendedorService.findById(Long.valueOf(tfVendedorID.getText()));

		if (!usuairo.isPresent()) {
			Notifications.showAlert("El codigo del Vendedor no existe.!");
			tfVendedorID.requestFocus();
			return false;
		}

//    	Optional<Deposito> deposito = depositoService.findById(Long.valueOf(tfDepositoID.getText()));
//    	
//    	if (!deposito.isPresent()) {
//    		Notifications.showAlert("El codigo del Deposito no existe.!");
//			tfDepositoID.requestFocus();
//			return false;
//		}

		return true;
	}

//    private boolean validateItems(List<PresupuestoDetalle> items) {
//    	//validar stock de items
////    	boolean sinStock = itemTableModel.getEntities().stream().filter(e -> 
////    		  e.getCantidad() <= 
////    	);
//    		
//    	items.forEach(e -> {
//    		Optional<Producto> producto = productoService.findById(e.getProductoId());
//    		
//    		if (producto.isPresent()) {
//    			//verificar la cantidad
//    			Double stock = producto.get().getStock();
//    			
//    			if (stock < e.getCantidad() ) {	
//    				//sinStock = true;
//    				Notifications.showAlert("Insuficiente Stock para el Item: " + producto.get().getDescripcion() + ". Stock Actual: " + producto.get().getStock() );
//    				return;
//				}
//			}
//    		
//    	});
//    	
//    	return true;
//    }

	public void newPresupuesto() {
		long max = presupuestoService.getRowCount();
		btnReImpresion.setVisible(false);
		tfVentaId.setText(String.valueOf(max + 1));
		tfClienteID.requestFocus();
		//btnRemove.setVisible(false);
		btnGuardar.setVisible(true);
		btnReImpresion.setVisible(false);
		btnAdd.setEnabled(true);
		btnRemove.setEnabled(true);

	}

	private void findClientById(Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);

		if (cliente.isPresent()) {
			setCliente(cliente);
		}
	}

	private void findVendedorById(Long id) {
		Optional<Usuario> usuario = vendedorService.findById(id);

		if (usuario.isPresent()) {
			setDataVendedor(usuario);
		}
	}

//    private void findDepositoById(Long id) {
//    	Optional<Deposito> deposito = depositoService.findById(id);
//    	
//    	if (deposito.isPresent()) {
//			setDataDeposito(deposito);
//		}
//    }

	private void findProductoById(Long id) {
		Optional<Producto> producto = productoService.findById(id);

		if (producto.isPresent()) {
			setDataProducto(producto);
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
					tfProductoID.requestFocus();
				}
			} else {
				tfProductoID.requestFocus();
			}
		} else {
			Long productoId = Long.valueOf(tfProductoID.getText());
//	    	Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());

			Optional<Producto> p = productoService.findById(productoId);

			if (p.isPresent()) {
//				Double stock = p.get().getStock() != null ? p.get().getStock() : 0;
//				
//				if (cantidad <= stock) {
				addItemToList();
				calculateItem();
//				} else {
//					tfProductoID.requestFocus();
//					Notifications.showAlert("No tiene suficiente Stock para este Producto. Stock actual es de: " + FormatearValor.doubleAString(stock));
//				}
			}
		}

		clearItem();
	}

	private void removeItem() {
		int row[] = tbProductos.getSelectedRows();

		if (tbProductos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				itemTableModel.removeRow(row[i - 1]);
			}

			if (tbProductos.getRowCount() == 0) {
				btnRemove.setEnabled(false);
			}

			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}

		calculateItem();
	}

	private void findPresupuesto(String id) {
		try {
			clearForm();
			Presupuesto presupuesto= null;
			presupuesto = presupuestoService.getPresupuesto(Long.valueOf(id.trim()));
			if (presupuesto!=null) {
				setPresupuesto(presupuesto);
				btnGuardar.setVisible(false);
				btnReImpresion.setVisible(true);
				btnAdd.setEnabled(false);
				btnRemove.setEnabled(false);
				tbProductos.disable();
			} else {
				Notifications.showAlert("No existe presupuesto informado. Verifique por favor.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con el presupuesto, intente nuevamente!");
		}

	}
	
	private void addItemToList() {
		itemTableModel.addEntity(getItem());
	}

	private void calculateItem() {
		Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
		Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotal()).sum();
		setTotals(cantItem, total);
	}
}
