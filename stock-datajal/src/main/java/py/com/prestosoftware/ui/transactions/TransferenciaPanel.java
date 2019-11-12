package py.com.prestosoftware.ui.transactions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.TransferenciaProductoDetalle;
import py.com.prestosoftware.data.models.TransferenciaProducto;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.TransferenciaProductoService;
import py.com.prestosoftware.domain.validations.TransferenciaProductoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.table.TransferenciaTableModel;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class TransferenciaPanel extends JDialog implements DepositoInterfaz, ProductoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int DEPOSITO_CODE = 1;
	private static final int PRODUCTO_CODE = 2;

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

	private TransferenciaTableModel itemTableModel;
	private DepositoDialog depositoDialog;
	private ProductoDialog productoDialog;
	private DepositoService depositoService;
	private ProductoService productoService;
	private TransferenciaProductoService tService;
	private TransferenciaProductoValidator tValidator;

	@Autowired
	public TransferenciaPanel(TransferenciaTableModel itemTableModel, DepositoDialog depositoDialog,
			ProductoDialog productoDialog, DepositoService depositoService, TransferenciaProductoValidator tValidator,
			ProductoService productoService, TransferenciaProductoService tService) {
		this.itemTableModel = itemTableModel;
		this.depositoDialog = depositoDialog;
		this.productoDialog = productoDialog;
		this.depositoService = depositoService;
		this.tValidator = tValidator;
		this.productoService = productoService;
		this.tService = tService;

		setSize(750, 450);
		setTitle("TRANSFERENCIA INMEDIATA");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		initComponents();
	}

	private void initComponents() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 76, 738, 267);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("Productos", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JLabel lblCodigo = new JLabel("CODIGO");
		lblCodigo.setBounds(6, 10, 98, 30);
		pnlProducto.add(lblCodigo);

		JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
		lblDescripcion.setBounds(112, 10, 400, 30);
		pnlProducto.add(lblDescripcion);

		tfDescripcion = new JTextField();
		tfDescripcion.setEditable(false);
		tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcion.setColumns(10);
		tfDescripcion.setBounds(112, 40, 400, 30);
		pnlProducto.add(tfDescripcion);

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
					if (!tfOrigenId.getText().isEmpty()) {
						if (!tfProductoID.getText().isEmpty()) {
							findProductoById(Long.valueOf(tfProductoID.getText()));
						} else {
							showDialog(PRODUCTO_CODE);
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
		tfProductoID.setBounds(6, 40, 103, 30);
		pnlProducto.add(tfProductoID);
		tfProductoID.setColumns(10);

		btnRemove = new JButton(" - ");
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
		btnRemove.setBounds(676, 40, 51, 30);
		pnlProducto.add(btnRemove);

		JScrollPane scrollProducto = new JScrollPane();
		scrollProducto.setBounds(6, 81, 721, 151);
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
		tfCantidad.setBounds(513, 40, 109, 30);
		pnlProducto.add(tfCantidad);

		JLabel lblCantidad = new JLabel("CANTIDAD");
		lblCantidad.setBounds(513, 10, 109, 30);
		pnlProducto.add(lblCantidad);

		btnAdd = new JButton(" + ");
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
		btnAdd.setBounds(621, 40, 57, 30);
		pnlProducto.add(btnAdd);

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
		lblDepositoOrigen.setBounds(147, 7, 82, 30);
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
		tfOrigenId.setBounds(232, 7, 41, 30);
		pnlCliente.add(tfOrigenId);
		tfOrigenId.setColumns(10);

		tfNombreOrigen = new JTextField();
		tfNombreOrigen.setEditable(false);
		tfNombreOrigen.setBounds(273, 7, 150, 30);
		pnlCliente.add(tfNombreOrigen);
		tfNombreOrigen.setColumns(10);

		JLabel lblDepositoDestino = new JLabel("Dep. Destino:");
		lblDepositoDestino.setBounds(431, 7, 90, 30);
		pnlCliente.add(lblDepositoDestino);

		tfDestinoId = new JTextField();
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
					if (tfOrigenId.getText().equals(tfDestinoId.getText())) {
						Notifications.showAlert("El codigo de Deposito debe ser diferente.!");
					} else {
						if (!tfDestinoId.getText().isEmpty()) {
							findDepositoById(Long.valueOf(tfDestinoId.getText()));
							tfProductoID.requestFocus();
						} else {
							showDialog(DEPOSITO_CODE);
							tfProductoID.requestFocus();
						}
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Integer respuesta = JOptionPane.showConfirmDialog(null, "Desea abandonar.?");
					if (respuesta == 0) {
						dispose();
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
		tfDestinoId.setBounds(524, 7, 41, 30);
		pnlCliente.add(tfDestinoId);

		tfNombreDestino = new JTextField();
		tfNombreDestino.setEditable(false);
		tfNombreDestino.setColumns(10);
		tfNombreDestino.setBounds(566, 7, 150, 30);
		pnlCliente.add(tfNombreDestino);

		lblNota = new JLabel("Nota:");
		lblNota.setBounds(6, 7, 51, 30);
		pnlCliente.add(lblNota);

		tfNota = new JTextField();
		tfNota.setEditable(false);
		tfNota.setColumns(10);
		tfNota.setBounds(54, 7, 90, 30);
		pnlCliente.add(tfNota);

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
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
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

		tfObs = new JTextField();

		tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
		((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfObs.setBounds(55, 350, 689, 30);
		getContentPane().add(tfObs);
		tfObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfProductoID.requestFocus();
				}
			}
		});
		tfObs.setColumns(10);

		JLabel lblObs = new JLabel("Obs.:");
		lblObs.setBounds(6, 350, 46, 30);
		getContentPane().add(lblObs);
	}

	public void setCliente(Optional<Cliente> cliente) {
		if (cliente.isPresent()) {
			String nombre = cliente.get().getRazonSocial();
			tfNombreOrigen.setText(nombre);
			tfProductoID.requestFocus();
		}
	}

	private void findProductoById(Long id) {
		Optional<Producto> producto = productoService.findById(id);

		if (producto.isPresent()) {
			setProducto(producto);
		}
	}

	public void setProducto(Optional<Producto> producto) {
		if (producto.isPresent()) {
			String nombre = producto.get().getDescripcion();
			tfDescripcion.setText(nombre);
			tfCantidad.setText("1");
			tfCantidad.requestFocus();
		}
	}

	private void getItemSelected() {
		int selectedRow = tbProductos.getSelectedRow();

		if (selectedRow != -1) {
			TransferenciaProductoDetalle item = itemTableModel.getEntityByRow(selectedRow);

			tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
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
			Long productoId = Long.valueOf(tfProductoID.getText());
			Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());

			Optional<Producto> p = productoService.findById(productoId);

			if (p.isPresent()) {
				Double stock = p.get().getStock() != null ? p.get().getStock() : 0;

				if (cantidad <= stock) {
					addItemToList();
					calculateItem();
				} else {
					tfProductoID.requestFocus();
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
			Double stock = p.get().getStock() != null ? p.get().getStock() : 0;

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

	public TransferenciaProducto getFormValue() {
		TransferenciaProducto t = new TransferenciaProducto();

		t.setComprobante("SIN COMPROBANTE");
		t.setDepositoOrigen(new Deposito(Long.valueOf(tfOrigenId.getText())));
		t.setDepositoDestino(new Deposito(Long.valueOf(tfDestinoId.getText())));
		t.setFecha(new Date());
		t.setSituacion("ACTIVO");
		t.setObs(tfObs.getText());
		return t;
	}

	public TransferenciaProductoDetalle getItem() {
		TransferenciaProductoDetalle item = new TransferenciaProductoDetalle();
		item.setProductoId(Long.valueOf(tfProductoID.getText()));
		item.setProducto(tfDescripcion.getText());
		item.setCantidad(Double.valueOf(tfCantidad.getText()));

		return item;
	}

	public void clearItem() {
		tfProductoID.setText("");
		tfDescripcion.setText("");
		tfCantidad.setText("");

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
		tfOrigenId.setText("");
		tfNombreOrigen.setText("");
		tfDestinoId.setText("");
		tfNombreDestino.setText("");
		tfProductoID.setText("");
		tfCantidad.setText("");
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
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.setVisible(true);
			break;
		default:
			break;
		}
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (validateCabezera() && validateItems(itemTableModel.getEntities())) {
				TransferenciaProducto t = getFormValue();
				t.setItems(itemTableModel.getEntities());

				Optional<ValidationError> errors = tValidator.validate(t);

				if (errors.isPresent()) {
					ValidationError validationError = errors.get();
					Notifications.showFormValidationAlert(validationError.getMessage());
				} else {
					TransferenciaProducto v = tService.save(t);

					if (v != null) {
						updateStockProduct(v.getItems());
					}

					Notifications.showAlert("Transferencia de Producto registrado con exito.!");
					clearForm();
					// newTransferenciaProducto();
				}
			}
		} else {
			tfProductoID.requestFocus();
		}
	}

	private void updateStockProduct(List<TransferenciaProductoDetalle> items) {
		List<Producto> productos = new ArrayList<>();

		for (TransferenciaProductoDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();
				Double newQt = p.getStock() - e.getCantidad();
				p.setStock(newQt);
				productos.add(p);
			}
		}

		productoService.updateStock(productos);
	}

//    public void newTransferenciaProducto() {
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
		} else if (tfOrigenId.getText().equals(tfDestinoId.getText())) {
			Notifications.showAlert("El codigo de Deposito Origen y Destino debe ser diferentes para la transferencia");
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

	private boolean validateItems(List<TransferenciaProductoDetalle> items) {
		for (TransferenciaProductoDetalle e : items) {
			Optional<Producto> producto = productoService.findById(e.getProductoId());

			if (producto.isPresent()) {
				// verificar la cantidad
				Double stock = producto.get().getStock();

				if (stock < e.getCantidad()) {
					// sinStock = true;
					Notifications.showAlert("Insuficiente Stock para el Item: " + producto.get().getDescripcion()
							+ ". Stock Actual: " + producto.get().getStock());
					return false;
				}
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
		long max = tService.getRowCount();
		tfNota.setText(String.valueOf(max + 1));
		tfOrigenId.requestFocus();
	}

	@Override
	public void getEntity(Producto producto) {
		if (producto != null) {
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();
		}
	}
}
