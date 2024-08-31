package py.com.prestosoftware.ui.transactions;

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
import javax.swing.JDialog;
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

import org.eclipse.swt.widgets.MessageBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.TransformacionProducto;
import py.com.prestosoftware.data.models.TransformacionProductoDetalle;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.TransformacionProductoService;
import py.com.prestosoftware.domain.validations.TransformacionProductoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.controllers.ProductoController;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.TransformacionProductoDialog;
import py.com.prestosoftware.ui.search.TransformacionProductoInterfaz;
import py.com.prestosoftware.ui.table.TransformacionTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class TransformacionPanel extends JDialog implements DepositoInterfaz, TransformacionProductoInterfaz, ProductoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int DEPOSITO_CODE = 1;
	private static final int PRODUCTO_CODE_ORIGEN = 2;
	private static final int PRODUCTO_CODE_DESTINO = 3;
	private static final int TRANSFORMACION_CODE = 4;
	private static final int PRODUCTO_ADD_CODE = 8;

	private JTextField tfNombreOrigen;
	private JTextField tfDescripcion;
	private JTextField tfProductoID;
	private JTextField tfOrigenId;
	private JTable tbProductos;
	private JTextField tfCantidad;
	private JTextField tfObs;
	private JTextField tfDestinoId;
	private JTextField tfNombreDestino;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnCerrar;
	private JButton btnVer;
	private JLabel lblSituacion;

	private TransformacionTableModel itemTableModel;
	private DepositoDialog depositoDialog;
	private ProductoDialog productoDialog;
	private TransformacionProductoDialog transformacionProductoDialog;
	private DepositoService depositoService;
	private ProductoService productoService;
	private TransformacionProductoService tService;
	private TransformacionProductoValidator tValidator;
	private Producto productoOrigen;
	private Producto productoDestino;
	private ProductoController productoController;
	private int origen;
	private TransformacionProducto transformacionProductoSeleccionado;

	@Autowired
	public TransformacionPanel(TransformacionTableModel itemTableModel, DepositoDialog depositoDialog,
			ProductoDialog productoDialog, DepositoService depositoService, TransformacionProductoValidator tValidator,
			ProductoService productoService, TransformacionProductoDialog transformacionProductoDialog, TransformacionProductoService tService, ProductoController productoController) {
		this.itemTableModel = itemTableModel;
		this.depositoDialog = depositoDialog;
		this.productoDialog = productoDialog;
		this.transformacionProductoDialog=transformacionProductoDialog;
		this.depositoService = depositoService;
		this.tValidator = tValidator;
		this.productoService = productoService;
		this.tService = tService;
		this.productoController= productoController;

		setSize(758, 481);
		setTitle("TRANSFORMACION DE PRODUCTO");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		initComponents();
	}

	private void initComponents() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 76, 738, 312);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("Transformación de Productos", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JLabel lblProductoTransformar = new JLabel("PRODUCTO A TRANSFORMAR");
		lblProductoTransformar.setBounds(6, 11, 219, 14);
		pnlProducto.add(lblProductoTransformar);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 31, 717, 79);
		pnlProducto.add(panel_1);
		panel_1.setLayout(null);
		
		setOrigen(2);
		
		
		tfCodigoProductoOrigen = new JTextField();
		tfCodigoProductoOrigen.setBounds(10, 32, 63, 30);
		panel_1.add(tfCodigoProductoOrigen);
		tfCodigoProductoOrigen.setColumns(10);
		tfCodigoProductoOrigen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE_ORIGEN);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCodigoProductoOrigen.getText().isEmpty()) {
						findProductoById(Long.valueOf(tfCodigoProductoOrigen.getText()),PRODUCTO_CODE_ORIGEN);
					} else {
						showDialog(PRODUCTO_CODE_ORIGEN);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfCantidadOrigen.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Integer respuesta = JOptionPane.showConfirmDialog(null, "Abandonar Nota.?");

					if (respuesta == 0) {
						clearForm();
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

		tfDescripcionProductoOrigen = new JTextField();
		tfDescripcionProductoOrigen.setBounds(76, 32, 367, 30);
		panel_1.add(tfDescripcionProductoOrigen);
		tfDescripcionProductoOrigen.setEditable(false);
		tfDescripcionProductoOrigen.setColumns(10);

		tfStockOrigen = new JTextField();
		tfStockOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		tfStockOrigen.setBounds(444, 32, 63, 30);
		panel_1.add(tfStockOrigen);
		tfStockOrigen.setFont(new Font("Arial", Font.PLAIN, 14));
		tfStockOrigen.setColumns(10);

		tfCantidadOrigen = new JTextField();
		tfCantidadOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCantidadOrigen.setBounds(517, 32, 63, 30);
		panel_1.add(tfCantidadOrigen);
		tfCantidadOrigen.setFont(new Font("Arial", Font.PLAIN, 14));
		tfCantidadOrigen.setColumns(10);

		JLabel lblCantidadOrigen = new JLabel("CANTIDAD");
		lblCantidadOrigen.setBounds(517, 0, 63, 30);
		panel_1.add(lblCantidadOrigen);

		JLabel lblStockOrigen = new JLabel("STOCK");
		lblStockOrigen.setBounds(444, 0, 63, 30);
		panel_1.add(lblStockOrigen);

		lblDescripcionProductoOrigen = new JLabel("DESCRIPCIÓN");
		lblDescripcionProductoOrigen.setBounds(76, 0, 110, 30);
		panel_1.add(lblDescripcionProductoOrigen);

		lblCdigoOrigen = new JLabel("CÓDIGO");
		lblCdigoOrigen.setBounds(10, 0, 63, 30);
		panel_1.add(lblCdigoOrigen);

		panel_2 = new JPanel();
		panel_2.setBounds(6, 132, 727, 141);
		pnlProducto.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblCodigo = new JLabel("CODIGO");
		lblCodigo.setBounds(10, 0, 63, 30);
		panel_2.add(lblCodigo);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setBounds(122, 0, 364, 30);
		panel_2.add(lblDescripcion);

		tfProductoID = new JTextField();
		tfProductoID.setBounds(10, 26, 60, 30);
		panel_2.add(tfProductoID);
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
					showDialog(PRODUCTO_CODE_DESTINO);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfOrigenId.getText().isEmpty()) {
						if (!tfProductoID.getText().isEmpty()) {
							findProductoById(Long.valueOf(tfProductoID.getText()),PRODUCTO_CODE_DESTINO);
						} else {
							showDialog(PRODUCTO_CODE_DESTINO);
						}
					} else {
						Notifications.showAlert("El campo Deposito Origen es Obligatorio.!");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfCantidad.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Integer respuesta = JOptionPane.showConfirmDialog(null, "Abandonar Nota.?");

					if (respuesta == 0) {
						clearForm();
						newTransf();
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
		tfProductoID.setFont(new Font("Arial", Font.PLAIN, 14));
		tfProductoID.setColumns(10);

		tfDescripcion = new JTextField();
		tfDescripcion.setBounds(122, 26, 367, 30);
		panel_2.add(tfDescripcion);
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);

		JLabel lblStockDestino = new JLabel("STOCK");
		lblStockDestino.setBounds(492, 0, 63, 30);
		panel_2.add(lblStockDestino);

		JLabel lblCantidad = new JLabel("CANTIDAD");
		lblCantidad.setBounds(558, 0, 63, 30);
		panel_2.add(lblCantidad);

		tfStockDestino = new JTextField();
		tfStockDestino.setHorizontalAlignment(SwingConstants.RIGHT);
		tfStockDestino.setBounds(492, 26, 63, 30);
		panel_2.add(tfStockDestino);
		tfStockDestino.setFont(new Font("Arial", Font.PLAIN, 14));
		tfStockDestino.setColumns(10);
		tfCantidad = new JTextField();
		tfCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfCantidad.setBounds(558, 26, 63, 30);
		panel_2.add(tfCantidad);
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
						btnAdd.requestFocus();
					} else {
						Notifications.showAlert("Debes digitar cantidad.!");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnAdd.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfProductoID.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCantidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfCantidad.setColumns(10);

		btnAdd = new JButton(" + ");
		btnAdd.setBounds(650, 67, 57, 30);
		panel_2.add(btnAdd);

		btnRemove = new JButton(" - ");
		btnRemove.setBounds(650, 101, 57, 30);
		panel_2.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(10, 67, 630, 64);
		panel_2.add(scrollProducto);

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

		lblPrecio = new JLabel("PRECIO");
		lblPrecio.setBounds(631, 0, 63, 30);
		panel_2.add(lblPrecio);

		tfPrecio = new JTextField();
		tfPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrecio.setBounds(631, 26, 86, 30);
		panel_2.add(tfPrecio);
		tfPrecio.setColumns(10);

		lblNewLabel = new JLabel("TRANSFORMAR A");
		lblNewLabel.setBounds(6, 108, 145, 27);
		pnlProducto.add(lblNewLabel);
		btnRemove.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					removeItem();
				}
			}
		});
		
		//iniico
		btnRemove.setFont(new Font("Dialog", Font.BOLD, 18));
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) {
					removeItem();
				}
			}
		});
		
		//
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 18));
		
		JButton btnNewProducto = new JButton("+");
		btnNewProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(PRODUCTO_ADD_CODE);
			}
		});
		btnNewProducto.setBounds(75, 26, 44, 30);
		panel_2.add(btnNewProducto);
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

		

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Seleccione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(6, 6, 738, 69);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 726, 45);
		panel_3.add(pnlCliente);
		pnlCliente.setLayout(null);

		JLabel lblDepositoOrigen = new JLabel("Dep. Origen:");
		lblDepositoOrigen.setBounds(177, 7, 82, 30);
		pnlCliente.add(lblDepositoOrigen);

		tfOrigenId = new JTextField();
		tfOrigenId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfOrigenId.selectAll();
			}
		});
		tfOrigenId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(DEPOSITO_CODE);
					tfDestinoId.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfOrigenId.getText().isEmpty()) {
						findDepositoById(Long.valueOf(tfOrigenId.getText()));
						tfDestinoId.requestFocus();
					} else {
						showDialog(DEPOSITO_CODE);
						tfDestinoId.requestFocus();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		tfOrigenId.setText("0");
		tfOrigenId.setToolTipText("");
		tfOrigenId.setEditable(false);
		tfOrigenId.setBounds(244, 7, 41, 30);
		pnlCliente.add(tfOrigenId);
		tfOrigenId.setColumns(10);

		tfNombreOrigen = new JTextField();
		tfNombreOrigen.setEditable(false);
		tfNombreOrigen.setBounds(285, 7, 82, 30);
		pnlCliente.add(tfNombreOrigen);
		tfNombreOrigen.setColumns(10);

		JLabel lblDepositoDestino = new JLabel("Dep. Destino:");
		lblDepositoDestino.setBounds(377, 7, 71, 30);
		pnlCliente.add(lblDepositoDestino);

		tfDestinoId = new JTextField();
		tfDestinoId.setEditable(false);
		tfDestinoId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDestinoId.selectAll();
			}
		});
		tfDestinoId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(DEPOSITO_CODE);
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfDestinoId.getText().isEmpty()) {
						findDepositoById(Long.valueOf(tfDestinoId.getText()));
						tfProductoID.requestFocus();
					} else {
						showDialog(DEPOSITO_CODE);
						tfProductoID.requestFocus();
					}

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfDestinoId.setToolTipText("");
		tfDestinoId.setText("0");
		tfDestinoId.setColumns(10);
		tfDestinoId.setBounds(449, 7, 41, 30);
		pnlCliente.add(tfDestinoId);

		tfNombreDestino = new JTextField();
		tfNombreDestino.setEditable(false);
		tfNombreDestino.setColumns(10);
		tfNombreDestino.setBounds(491, 7, 82, 30);
		pnlCliente.add(tfNombreDestino);

		lblNota = new JLabel("Nota:");
		lblNota.setBounds(6, 7, 51, 30);
		pnlCliente.add(lblNota);

		tfNota = new JTextField();
		tfNota.setEditable(false);
		tfNota.setColumns(10);
		tfNota.setBounds(33, 7, 51, 30);
		pnlCliente.add(tfNota);

		btnVer = new JButton("VER");
		btnVer.setFont(new Font("Dialog", Font.BOLD, 18));
		btnVer.setBounds(90, 7, 21, 30);
		pnlCliente.add(btnVer);

		JLabel lblObs = new JLabel("Obs.:");
		lblObs.setBounds(576, 7, 41, 30);
		pnlCliente.add(lblObs);

		tfObs = new JTextField();
		tfObs.setBounds(606, 6, 103, 30);
		pnlCliente.add(tfObs);

		tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
		((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfProductoID.requestFocus();
				}
			}
		});
		tfObs.setColumns(10);
		
		lblSituacion = new JLabel("Nuevo");
		lblSituacion.setBounds(113, 15, 59, 14);
		pnlCliente.add(lblSituacion);
		btnVer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showDialog(4);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(4);
			}
		});

		JPanel panel = new JPanel();
		panel.setBounds(6, 388, 738, 35);
		getContentPane().add(panel);

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
		panel.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					clearForm();
					newTransf();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				newTransf();
			}
		});
		
		btnAnula = new JButton("Elimina");
		btnAnula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				elimina();
			}
		});
		panel.add(btnAnula);
		panel.add(btnCancelar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dispose();
			}
		});
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCerrar);
		btnAnula.setVisible(false);	
		btnGuardar.setVisible(true);
	}

	protected void elimina() {
		btnAnula.setVisible(false);	
		btnGuardar.setVisible(true);
		this.getTransformacionProductoSeleccionado().setSituacion(0);
		tService.save(this.getTransformacionProductoSeleccionado());
		revertUpdateStockProduct();
		clearForm();
		newTransf();
	}

	public void setCliente(Optional<Cliente> cliente) {
		if (cliente.isPresent()) {
			String nombre = cliente.get().getRazonSocial();
			tfNombreOrigen.setText(nombre);
			tfProductoID.requestFocus();
		}
	}

	private void findProductoById(Long id, int desde) {
		Optional<Producto> producto = productoService.findById(id);

		if (producto.isPresent()) {
			setProducto(producto, desde);
		}
	}

	public void setProducto(Optional<Producto> producto, int desde) {
		if (producto.isPresent()) {
			String nombre = producto.get().getDescripcion();
			if (desde == 3) {
				tfDescripcion.setText(nombre);
				tfCantidad.setText("1");
				tfStockDestino.setText(FormatearValor.doubleAString(producto.get().getDepO1()));
				tfPrecio.setText(FormatearValor.doubleAString(producto.get().getPrecioVentaA()));
				setProductoDestino(producto.get());
				tfCantidad.requestFocus();
			} else {
				tfCantidadOrigen.setText("1");
				tfDescripcionProductoOrigen.setText(nombre);
				tfStockOrigen.setText(FormatearValor.doubleAString(producto.get().getDepO1()));
				setProductoOrigen(producto.get());
			}
		}
	}

	private void getItemSelected() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			TransformacionProductoDetalle item = itemTableModel.getEntityByRow(selectedRow);
			tfProductoID.setText(String.valueOf(item.getProductoDestino().getId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProductoDestino().getDescripcion()));
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
					actualizarRegristroGrilla(fila, String.valueOf(tbProductos.getValueAt(fila, 2)));
					tfProductoID.requestFocus();
				}
			} else {
				tfProductoID.requestFocus();
			}
		} else {
			Long productoId = Long.valueOf(tfCodigoProductoOrigen.getText());
			Double cantidad = FormatearValor.stringToDouble(tfCantidadOrigen.getText());

			Optional<Producto> p = productoService.findById(productoId);

			if (p.isPresent()) {
				Double stock = p.get().getDepO1() != null ? p.get().getDepO1() : 0;

				if (cantidad <= stock) {
					addItemToList();
					calculateItem();
				} else {
					tfCodigoProductoOrigen.requestFocus();
					Notifications.showAlert("No tiene suficiente Stock para este Producto. Stock actual es de: "
							+ FormatearValor.doubleAString(stock));
				}
			}
		}

		clearItem();
	}

	private void addItemToList() {
		itemTableModel.addEntity(getItem());
	}

	private void calculateItem() {
		Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
		// Double total = itemTableModel.getEntities().stream().mapToDouble(i ->
		// i.getSubtotal()).sum();
		setTotals(cantItem, 0D);
	}

	private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
		itemTableModel.removeRow(fila);

		Long productoId = Long.valueOf(tfProductoID.getText());
		Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());

		Optional<Producto> p = productoService.findById(productoId);

		if (p.isPresent()) {
			Double stock = p.get().getDepO1() != null ? p.get().getDepO1() : 0;

			if (cantidad <= stock) {
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

	private JLabel lblNota;
	private JTextField tfNota;
	private JTextField tfCodigoProductoOrigen;
	private JTextField tfDescripcionProductoOrigen;
	private JTextField tfStockDestino;
	private JTextField tfStockOrigen;
	private JTextField tfCantidadOrigen;
	private JLabel lblDescripcionProductoOrigen;
	private JLabel lblCdigoOrigen;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel lblPrecio;
	private JTextField tfPrecio;
	private JButton btnAnula;

	public TransformacionProducto getFormValue() {
		TransformacionProducto t = new TransformacionProducto();
		t.setDepositoOrigen(new Deposito(Long.valueOf(tfOrigenId.getText())));
		t.setDepositoDestino(new Deposito(Long.valueOf(tfDestinoId.getText())));
		t.setFecha(new Date());
		t.setSituacion(1);
		t.setObs(tfObs.getText());
		t.setProductoOrigen(productoOrigen);
		t.setCantidad(Integer.valueOf(tfCantidadOrigen.getText()));
		t.setUsuario(GlobalVars.USER);
		return t;
	}

	public TransformacionProductoDetalle getItem() {
		if(this.getProductoDestino()!=null&&this.getProductoDestino().getPrecioVentaA()!=null) {
		TransformacionProductoDetalle item = new TransformacionProductoDetalle();
		item.setProductoDestino(this.getProductoDestino());
		item.setCantidad(Double.valueOf(tfCantidad.getText()));
		item.setPrecio(this.getProductoDestino().getPrecioVentaA());
		return item;
		}else {
			Notifications.showAlert("El producto no tiene precio");
			return null;
		}
	}

	public void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfStockDestino.setText("");
		tfProductoID.requestFocus();
	}

	public void setTotals(Double cantItem, Double total) {
//    	Double descuento = tfDescuento.getText().isEmpty() ? 0d : Double.valueOf(tfDescuento.getText());
//    	Double flete = tfFlete.getText().isEmpty() ? 0d : Double.valueOf(tfFlete.getText());
//    	Double totalGeneral = (total + flete) - descuento;

//    	tfSubtotal.setText(String.valueOf(total));
		// tfTotal.setText(String.valueOf(total));

//    	if (cantItem != 0) {
//    		tfTotalItems.setText(String.valueOf(cantItem));
//		}
	}

	public void clearForm() {
		tfCodigoProductoOrigen.setText("");
		tfDescripcionProductoOrigen.setText("");
		tfStockOrigen.setText("");
		tfCantidadOrigen.setText("");
		tfOrigenId.setText("");
		tfNombreOrigen.setText("");
		tfDestinoId.setText("");
		tfNombreDestino.setText("");
		
		tfProductoID.setText("");
		tfStockDestino.setText("");
		tfCantidad.setText("");
		tfPrecio.setText("");
		tfDescripcion.setText("");
		tfObs.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		tfOrigenId.requestFocus();
	}

	public int removeItem() {
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

		return row[0];
	}

	private void findDepositoById(Long id) {
		Optional<Deposito> deposito = depositoService.findById(id);

		if (deposito.isPresent()) {
			setDeposito(deposito);
		}
	}
	

	private void setDeposito(Optional<Deposito> deposito) {
		if (deposito.isPresent()) {
			String nombre = deposito.get().getNombre();

			if (tfNombreOrigen.getText().isEmpty()) {
				tfNombreOrigen.setText(nombre);
			} else {
				tfNombreDestino.setText(nombre);
			}
		}
	}

	private void showDialog(int code) {
		switch (code) {
		case DEPOSITO_CODE:
			depositoDialog.setInterfaz(this);
			depositoDialog.setVisible(true);
			break;
		case PRODUCTO_CODE_ORIGEN:
			setOrigen(PRODUCTO_CODE_ORIGEN);
			productoDialog.setInterfaz(this);
			productoDialog.loadProductos("");
			productoDialog.setVisible(true);
			break;
		case PRODUCTO_CODE_DESTINO:
			setOrigen(PRODUCTO_CODE_DESTINO);
			productoDialog.setInterfaz(this);
			productoDialog.loadProductos("");
			productoDialog.setVisible(true);
			break;
		case TRANSFORMACION_CODE:
			transformacionProductoDialog.setInterfaz(this);
			transformacionProductoDialog.loadTransformacionProductos("");
			transformacionProductoDialog.setVisible(true);
			break;	
		case PRODUCTO_ADD_CODE:
			productoController.setInterfaz(this);
			setOrigen(PRODUCTO_CODE_DESTINO);
			//productoController.addNewProduct();
			productoController.prepareAndOpenFrame();
			productoController.setOrigen("TRANSFORMACION");
			break;
		default:
			break;
		}
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (validateCabezera() && validateItems(itemTableModel.getEntities())) {
				TransformacionProducto t = getFormValue();
				t.setItems(itemTableModel.getEntities());

				Optional<ValidationError> errors = tValidator.validate(t);

				if (errors.isPresent()) {
					ValidationError validationError = errors.get();
					Notifications.showFormValidationAlert(validationError.getMessage());
				} else {
					TransformacionProducto v = tService.save(t);

					if (v != null) {
						updateStockProduct(v);
					}

					Notifications.showAlert("Transformacion de Producto registrado con exito.!");
					clearForm();
					newTransf();
					// newTransformacionProducto();
				}
			}
		} else {
			tfProductoID.requestFocus();
		}
	}

	private void updateStockProduct(TransformacionProducto item) {
		Optional<Producto> pOptional = productoService.findById(productoOrigen.getId());
			if (pOptional.isPresent()) {
				Producto p = pOptional.get();
				Double newQt = p.getDepO1() - Double.valueOf(tfCantidadOrigen.getText());
				p.setDepO1(newQt);
				productoService.updateStock(p);
			}
			List<Producto> productos = new ArrayList<>();
			for (TransformacionProductoDetalle e : item.getItems()) {
				Optional<Producto> pOptionalPdto = productoService.findById(e.getProductoDestino().getId());
				if (pOptionalPdto.isPresent()) {
					Producto p = pOptionalPdto.get();
					Double newQt = p.getDepO1() + e.getCantidad();
					p.setDepO1(newQt);
					productos.add(p);
				}
			}

			productoService.updateStock(productos);
	}
	
	private void revertUpdateStockProduct() {
		Optional<Producto> pOptional = productoService.findById(this.getTransformacionProductoSeleccionado().getProductoOrigen().getId());
			if (pOptional.isPresent()) {
				Producto p = pOptional.get();
				Double newQt = p.getDepO1() + Double.valueOf(this.getTransformacionProductoSeleccionado().getCantidad());
				p.setDepO1(newQt);
				productoService.updateStock(p);
			}
			List<Producto> productos = new ArrayList<>();
			for (TransformacionProductoDetalle e : this.getTransformacionProductoSeleccionado().getItems()) {
				Optional<Producto> pOptionalPdto = productoService.findById(e.getProductoDestino().getId());
				if (pOptionalPdto.isPresent()) {
					Producto p = pOptionalPdto.get();
					Double newQt = p.getDepO1() - e.getCantidad();
					p.setDepO1(newQt);
					productos.add(p);
				}
			}

			productoService.updateStock(productos);
	}

//    public void newTransformacionProducto() {
//		long max = tService.getRowCount();
//		tfVentaId.setText(String.valueOf(max + 1));
//		tfClienteID.requestFocus();
//	}

	private boolean validateCabezera() {
		// validar deposito
		if (tfDestinoId.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Deposito Destino es obligatorio");
			tfDestinoId.requestFocus();
			return false;
		} else if (tfOrigenId.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Deposito Origen es obligatorio");
			tfOrigenId.requestFocus();
			return false;
		} 

		Optional<Deposito> deposito = depositoService.findById(Long.valueOf(tfOrigenId.getText()));

		if (!deposito.isPresent()) {
			Notifications.showAlert("El codigo del Deposito Origen no existe.!");
			tfOrigenId.requestFocus();
			return false;
		}

		Optional<Deposito> d = depositoService.findById(Long.valueOf(tfDestinoId.getText()));

		if (!d.isPresent()) {
			Notifications.showAlert("El codigo del Deposito Destino no existe.!");
			tfDestinoId.requestFocus();
			return false;
		}

		return true;
	}

	private boolean validateItems(List<TransformacionProductoDetalle> items) {
		for (TransformacionProductoDetalle e : items) {
			Optional<Producto> producto = productoService.findById(e.getProductoDestino().getId());

			if (!producto.isPresent()) {
				// verificar la cantidad
					// sinStock = true;
				Notifications.showAlert("Producto no encontrado !");
					return false;
			}
		}
		return true;
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
		}

		return true;
	}

	@Override
	public void getEntity(Deposito d) {
		if (d != null) {
			if (tfOrigenId.getText().isEmpty()) {
				tfOrigenId.setText(String.valueOf(d.getId()));
				tfNombreOrigen.setText(d.getNombre());
			} else {
				tfDestinoId.setText(String.valueOf(d.getId()));
				tfNombreDestino.setText(d.getNombre());
			}
		}
	}

	public void newTransf() {
		clearForm();
		Optional<Deposito> deposito = depositoService.findById(1L);
		
		tfNombreOrigen.setText(deposito.get().getNombre());
		tfNombreDestino.setText(deposito.get().getNombre());
		tfOrigenId.setText("1");
		tfDestinoId.setText("1");
		
		long max = tService.getRowCount();
		tfNota.setText(String.valueOf(max + 1));
		lblSituacion.setText("NUEVO");
		btnGuardar.setVisible(true);
		btnAnula.setVisible(false);
		tfCodigoProductoOrigen.requestFocus();
	}

	@Override
	public void getEntity(Producto producto) {
		if (producto != null) {
			if(origen==2) {
				tfCodigoProductoOrigen.setText(String.valueOf(producto.getId()));
				tfDescripcionProductoOrigen.setText(producto.getDescripcion());
				tfStockOrigen.setText(FormatearValor.doubleAString(producto.getDepO1()));
				tfCantidadOrigen.setText("1");
				tfCantidadOrigen.requestFocus();
				setProductoOrigen(producto);
			}else {
				if(origen==3) {
					tfProductoID.setText(String.valueOf(producto.getId()));
					tfDescripcion.setText(producto.getDescripcion());
					tfCantidad.setText("1");
					tfStockDestino.setText(FormatearValor.doubleAString(producto.getDepO1()));
					tfPrecio.setText(FormatearValor.doubleAString(producto.getPrecioVentaA()));
					setProductoDestino(producto);
					tfCantidad.requestFocus();
				}else {
					tfProductoID.setText(String.valueOf(producto.getId()));
					tfDescripcion.setText(producto.getDescripcion());
					tfCantidad.setText(String.valueOf(1));
					tfStockDestino.setText(producto.getDepO1().toString());
					tfPrecio.setText(FormatearValor.doubleAString(producto.getPrecioVentaA()));
					tfCantidad.requestFocus();
					setProductoDestino(producto);
				}
			}
		}
	}

	public Producto getProductoOrigen() {
		return productoOrigen;
	}

	public void setProductoOrigen(Producto productoOrigen) {
		this.productoOrigen = productoOrigen;
	}

	public Producto getProductoDestino() {
		return productoDestino;
	}

	public void setProductoDestino(Producto productoDestino) {
		this.productoDestino = productoDestino;
	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	@Override
	public void getEntity(TransformacionProducto transformacionProducto) {
		if (transformacionProducto != null) {
			setTransformacionProductoSeleccionado(transformacionProducto);
				if(transformacionProducto.getSituacion()==1) {
					btnAnula.setVisible(true);	
				}else {
					btnAnula.setVisible(false);
				}
					btnGuardar.setVisible(false);
				tfNota.setText(transformacionProducto.getId().toString());
				lblSituacion.setText(transformacionProducto.getSituacion()==1?"VIGENTE":"ELIMINADO");
				tfOrigenId.setText(transformacionProducto.getDepositoOrigen().getId().toString());
				tfNombreOrigen.setText(transformacionProducto.getDepositoOrigen().getNombre());
				tfDestinoId.setText(transformacionProducto.getDepositoDestino().getId().toString());
				tfNombreDestino.setText(transformacionProducto.getDepositoDestino().getNombre());
				tfCodigoProductoOrigen.setText(String.valueOf(transformacionProducto.getProductoOrigen().getId()));
				tfDescripcionProductoOrigen.setText(transformacionProducto.getProductoOrigen().getDescripcion());
				tfStockOrigen.setText(FormatearValor.doubleAString(transformacionProducto.getProductoOrigen().getDepO1()));
				tfCantidadOrigen.setText(transformacionProducto.getCantidad().toString());
				itemTableModel.clear();
				itemTableModel.addEntities(transformacionProducto.getItems());
		}
		
	}

	public TransformacionProducto getTransformacionProductoSeleccionado() {
		return transformacionProductoSeleccionado;
	}

	public void setTransformacionProductoSeleccionado(TransformacionProducto transformacionProductoSeleccionado) {
		this.transformacionProductoSeleccionado = transformacionProductoSeleccionado;
	}
	
	
}

