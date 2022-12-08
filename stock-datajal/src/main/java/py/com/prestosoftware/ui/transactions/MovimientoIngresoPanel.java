package py.com.prestosoftware.ui.transactions;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Ingreso;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.IngresoService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.MovimientoIngresoService;
import py.com.prestosoftware.domain.services.MovimientoItemIngresoService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.IngresoDialog;
import py.com.prestosoftware.ui.search.IngresoInterfaz;
import py.com.prestosoftware.ui.search.MovimientoIngresoDialog;
import py.com.prestosoftware.ui.search.MovimientoIngresoInterfaz;
import py.com.prestosoftware.ui.table.MovimientoItemIngresoTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class MovimientoIngresoPanel extends JDialog implements MovimientoIngresoInterfaz, IngresoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int ENTIDAD_CODE = 1;
	private static final int INGRESO_CODE = 2;
	private static final int MOVIMIENTOINGRESO_CODE = 3;	
	
	private JTextField tfNombreCaja;
	private JTextField tfDescripcionIngreso;
	private JTextField tfIngresoID;
	private JTextField tfCajaId;
	private JTable tbIngresos;
	private JTextField tfObs;
	private JTextField tfCajeroId;
	private JTextField tfNombreCajero;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnCerrar;
	private JButton btnVer;
	private JLabel lblSituacion;

	private MovimientoItemIngresoTableModel itemTableModel;
	private IngresoDialog ingresoDialog;
	private IngresoService ingresoService;
	private CajaService cajaService;
	private AperturaCierreCajaService movCajaService;
	private UsuarioService usuarioService;
	private MovimientoIngresoService movimientoIngresoService;
	private MovimientoItemIngresoService movimientoItemIngresoService;
	private MovimientoCajaService pagoService;
	private MovimientoIngreso movimientoIngresoSeleccionado;
	private Double totalCalculado; 
	private Ingreso ingreso;
	private MovimientoIngresoDialog movimientoIngresoDialog;
	private boolean bandAgregar=true;

	@Autowired
	public MovimientoIngresoPanel(MovimientoIngresoService movimientoIngresoService, MovimientoItemIngresoService movimientoItemIngresoService, MovimientoItemIngresoTableModel itemTableModel, IngresoService ingresoService,
			CajaService cajaService, UsuarioService usuarioService, IngresoDialog ingresoDialog, MovimientoIngresoDialog movimientoIngresoDialog, MovimientoCajaService pagoService, AperturaCierreCajaService movCajaService) {
		this.itemTableModel = itemTableModel;
		this.ingresoService = ingresoService;
		this.movimientoIngresoService = movimientoIngresoService;
		this.movimientoItemIngresoService = movimientoItemIngresoService;
		this.cajaService =cajaService;
		this.usuarioService =usuarioService;
		this.ingresoDialog =ingresoDialog;
		this.movimientoIngresoDialog =movimientoIngresoDialog;
		this.pagoService = pagoService;
		this.movCajaService =movCajaService;

		setSize(750, 483);
		setTitle("MOVIMIENTO INGRESO");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		initComponents();
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 76, 738, 312);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("ENTRADA DE DINERO EN CAJA", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 11, 717, 64);
		pnlProducto.add(panel_1);
		panel_1.setLayout(null);
		
		tfFecha = new JXDatePicker();
		tfFecha.setFormats("dd/MM/yyyy");
		//tfFecha.setEditable(false);
		tfFecha.setBounds(20, 24, 124, 30);
		panel_1.add(tfFecha);
		//tfFecha.setColumns(10);
		
		tfDocumento = new JTextField();
		tfDocumento.setBounds(169, 24, 116, 30);
		panel_1.add(tfDocumento);
		tfDocumento.setColumns(10);

		tfEntidad = new JTextField();
		tfEntidad.setEditable(false);
		tfEntidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEntidad.setBounds(295, 24, 85, 30);
		panel_1.add(tfEntidad);
		tfEntidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfEntidad.setColumns(10);

		JLabel lblEntidad = new JLabel("ENTIDAD");
		lblEntidad.setBounds(295, 0, 53, 30);
		panel_1.add(lblEntidad);

		lblDescripcionProductoOrigen = new JLabel("DOCUMENTO");
		lblDescripcionProductoOrigen.setBounds(169, 0, 76, 30);
		panel_1.add(lblDescripcionProductoOrigen);

		lblFecha = new JLabel("FECHA");
		lblFecha.setBounds(20, 0, 53, 30);
		panel_1.add(lblFecha);
		
		tfNombreEntidad = new JTextField();
		tfNombreEntidad.setEditable(false);
		tfNombreEntidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNombreEntidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNombreEntidad.setColumns(10);
		tfNombreEntidad.setBounds(390, 24, 327, 30);
		panel_1.add(tfNombreEntidad);

		panel_2 = new JPanel();
		panel_2.setBounds(6, 97, 717, 176);
		pnlProducto.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblCodigoIngreso = new JLabel("CODIGO");
		lblCodigoIngreso.setBounds(10, 0, 63, 30);
		panel_2.add(lblCodigoIngreso);

		JLabel lblDescripcionIngreso = new JLabel("DESCRIPCIÓN INGRESO");
		lblDescripcionIngreso.setBounds(77, 0, 364, 30);
		panel_2.add(lblDescripcionIngreso);

		tfIngresoID = new JTextField();
		tfIngresoID.setBounds(10, 26, 63, 30);
		panel_2.add(tfIngresoID);
		tfIngresoID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfIngresoID.selectAll();
			}
		});
		tfIngresoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfIngresoID.getText().isEmpty()) {
						findIngresoById(Long.valueOf(tfIngresoID.getText()),INGRESO_CODE);
					} else {
						showDialog(INGRESO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfMontoIngreso.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Integer respuesta = JOptionPane.showConfirmDialog(null, "Abandonar Movimiento.?");
					if (respuesta == 0) {
						clearForm();
						newMov();
					} else {
						tfIngresoID.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfIngresoID.setFont(new Font("Arial", Font.PLAIN, 14));
		tfIngresoID.setColumns(10);

		tfDescripcionIngreso = new JTextField();
		tfDescripcionIngreso.setBounds(77, 26, 419, 30);
		panel_2.add(tfDescripcionIngreso);
		tfDescripcionIngreso.setEditable(false);
		tfDescripcionIngreso.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcionIngreso.setColumns(10);

		btnAdd = new JButton(" + ");
		btnAdd.setBounds(656, 67, 51, 30);
		panel_2.add(btnAdd);

		btnRemove = new JButton(" - ");
		btnRemove.setBounds(656, 101, 51, 30);
		panel_2.add(btnRemove);

		JScrollPane scrollMovimientoIngreso = new JScrollPane();
		scrollMovimientoIngreso.setBounds(10, 67, 630, 78);
		panel_2.add(scrollMovimientoIngreso);

		tbIngresos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};

		tbIngresos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbIngresos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbIngresos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getItemSelected();
			}
		});
		tbIngresos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getItemSelected();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfIngresoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});
		scrollMovimientoIngreso.setViewportView(tbIngresos);

		lblMontoIngreso = new JLabel("MONTO DEL INGRESO");
		lblMontoIngreso.setBounds(506, 0, 134, 30);
		panel_2.add(lblMontoIngreso);

		tfMontoIngreso = new JTextField();
		tfMontoIngreso.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMontoIngreso.setBounds(506, 26, 134, 30);
		panel_2.add(tfMontoIngreso);
		tfMontoIngreso.setColumns(10);
		tfMontoIngreso.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfMontoIngreso.selectAll();
			}
			
		});
		tfMontoIngreso.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				bandAgregar=true;
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAdd.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfIngresoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnAdd.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					abandonarNota();
				} 
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		
		JLabel lblTotalMontoIngreso = new JLabel("TOTAL  INGRESO");
		lblTotalMontoIngreso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalMontoIngreso.setBounds(339, 146, 134, 30);
		panel_2.add(lblTotalMontoIngreso);
		
		tfTotal = new JTextField();
		tfTotal.setEditable(false);
		tfTotal.setText("");
		tfTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotal.setColumns(10);
		tfTotal.setBounds(506, 146, 134, 30);
		panel_2.add(tfTotal);
		

		lblNewLabel = new JLabel("INGRESO");
		lblNewLabel.setBounds(6, 75, 145, 27);
		pnlProducto.add(lblNewLabel);
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
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(bandAgregar) {
					if (isValidItem()) {
						addItem();
					}					
				}
			}
		});
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (isValidItem()) {
						addItem();
						bandAgregar=false;
					}
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

		JLabel lblCaja = new JLabel("Caja:");
		lblCaja.setBounds(177, 7, 41, 30);
		pnlCliente.add(lblCaja);

		tfCajaId = new JTextField();
		tfCajaId.setText("0");
		tfCajaId.setToolTipText("");
		tfCajaId.setEditable(false);
		tfCajaId.setBounds(216, 7, 41, 30);
		pnlCliente.add(tfCajaId);
		tfCajaId.setColumns(10);

		tfNombreCaja = new JTextField();
		tfNombreCaja.setEditable(false);
		tfNombreCaja.setBounds(257, 7, 110, 30);
		pnlCliente.add(tfNombreCaja);
		tfNombreCaja.setColumns(10);

		JLabel lblCajero = new JLabel("Cajero");
		lblCajero.setBounds(377, 7, 41, 30);
		pnlCliente.add(lblCajero);

		tfCajeroId = new JTextField();
		tfCajeroId.setEditable(false);
		tfCajeroId.setToolTipText("");
		tfCajeroId.setText("0");
		tfCajeroId.setColumns(10);
		tfCajeroId.setBounds(418, 7, 41, 30);
		pnlCliente.add(tfCajeroId);

		tfNombreCajero = new JTextField();
		tfNombreCajero.setEditable(false);
		tfNombreCajero.setColumns(10);
		tfNombreCajero.setBounds(460, 7, 82, 30);
		pnlCliente.add(tfNombreCajero);

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
		btnVer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showDialog(MOVIMIENTOINGRESO_CODE);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(MOVIMIENTOINGRESO_CODE);
			}
		});

		JLabel lblObs = new JLabel("Obs.:");
		lblObs.setBounds(555, 7, 41, 30);
		pnlCliente.add(lblObs);

		tfObs = new JTextField();
		tfObs.setBounds(591, 6, 118, 30);
		pnlCliente.add(tfObs);

		tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
		((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfIngresoID.requestFocus();
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
					showDialog(INGRESO_CODE);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(4);
			}
		});

		JPanel panel = new JPanel();
		panel.setBounds(6, 408, 738, 35);
		getContentPane().add(panel);

		btnGuardar = new JButton("Guardar(F5)");
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				}
				else {
					if (e.getKeyCode() == KeyEvent.VK_F5) {
		                save();
		            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		                abandonarNota();
		            }
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
					newMov();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				newMov();
			}
		});
		
		btnAnula = new JButton("Eliminar");
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
		clearForm();
		//newMov();
	}

	protected void elimina() {
		btnAnula.setVisible(false);	
		btnGuardar.setVisible(true);
		this.getMovimientoIngresoSeleccionado().setMinSituacion(1);
		eliminaMovimientoIngreso();
		clearForm();
		newMov();
	}

	public void setCliente(Optional<Cliente> cliente) {
		if (cliente.isPresent()) {
			String nombre = cliente.get().getRazonSocial();
			tfNombreCaja.setText(nombre);
			tfIngresoID.requestFocus();
		}
	}

	private void findIngresoById(Long id, int desde) {
		Optional<Ingreso> ingreso = ingresoService.findById(id);

		if (ingreso.isPresent()) {
			setIngreso(ingreso);
		}
	}

	public void setIngreso(Optional<Ingreso> ingreso) {
		if (ingreso.isPresent()) {
			String nombre = ingreso.get().getIngDescripcion();
				tfDescripcionIngreso.setText(nombre);
				tfMontoIngreso.requestFocus();
		}
	}

	private void getItemSelected() {
		int selectedRow = tbIngresos.getSelectedRow();

		if (selectedRow != -1) {
			MovimientoItemIngreso item = itemTableModel.getEntityByRow(selectedRow);
			tfIngresoID.setText(String.valueOf(item.getMiiIngreso()));
			tfMontoIngreso.setText(FormatearValor.doubleAString(item.getMiiMonto()));
			Optional<Ingreso> ingresoSeleccionado =ingresoService.findById(Long.valueOf(item.getMiiIngreso()));
			tfDescripcionIngreso.setText(String.valueOf(ingresoSeleccionado.get().getIngDescripcion()));
		}
	}

	private void addItem() {
		Boolean esDuplicado = false;
		Integer fila = -1;
		for (Integer i = 0; i < tbIngresos.getRowCount(); i++) {
			String itemId = String.valueOf(tbIngresos.getValueAt(i, 0));
			if (tfIngresoID.getText().trim().equals(itemId) && !itemId.equals("")) {
				esDuplicado = true;
				fila = i;
			}
		}
		if (esDuplicado) {
			Integer respuesta = JOptionPane.showConfirmDialog(null,
					"Registro ya existe en la grilla, desea actualizar los datos?");
			if (respuesta == 0) {
				if (isValidItem()) {
					actualizarRegristroGrilla(fila, String.valueOf(tbIngresos.getValueAt(fila, 2)));
					tfIngresoID.requestFocus();
				}
			} else {
				tfIngresoID.requestFocus();
			}
		} else {
			Long ingresoId = Long.valueOf(tfIngresoID.getText());
			Optional<Ingreso> p = ingresoService.findById(ingresoId);
			if (p.isPresent()) {
					addItemToList();
					calculateItem();
			} 
		}

		clearItem();
	}

	private void addItemToList() {
		itemTableModel.addEntity(getItem());
	}

	private void calculateItem() {
		totalCalculado = itemTableModel.getEntities().stream().mapToDouble(i -> i.getMiiMonto()).sum();
		tfTotal.setText(FormatearValor.doubleAString(totalCalculado));
	}

	private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
		itemTableModel.removeRow(fila);

		Long ingresoId = Long.valueOf(tfIngresoID.getText());

		Optional<Ingreso> p = ingresoService.findById(ingresoId);

		if (p.isPresent()) {
				addItemToList();
				calculateItem();
		}
		clearItem();
	}

	private JLabel lblNota;
	private JTextField tfNota;
	private JXDatePicker tfFecha;
	private JTextField tfDocumento;
	private JTextField tfEntidad;
	private JLabel lblDescripcionProductoOrigen;
	private JLabel lblFecha;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel lblMontoIngreso;
	private JTextField tfMontoIngreso;
	private JButton btnAnula;
	private JTextField tfNombreEntidad;
	private JTextField tfTotal;

	public MovimientoIngreso getFormValue() {
		MovimientoIngreso t = new MovimientoIngreso();
		t.setMinCaja(Integer.valueOf(tfCajaId.getText()));
		t.setFecha(tfFecha.getDate());
		t.setHora(new Date());
		t.setMinSituacion(0);
		t.setMinDocumento(tfDocumento.getText());
		t.setMinTipoEntidad(8);
		t.setMinEntidad(tfEntidad.getText());
		t.setMinNumero(Integer.valueOf(tfNota.getText()));
		t.setMinTipoProceso(0);
		t.setMinProceso(0);
		t.setMinEntidad("1");
		return t;
	}

	public MovimientoItemIngreso getItem() {
		MovimientoItemIngreso item = new MovimientoItemIngreso();
		item.setMiiIngreso(Integer.valueOf(tfIngresoID.getText()));
		item.setMiiDescripcion(tfDescripcionIngreso.getText());
		item.setMiiMonto(Double.valueOf(tfMontoIngreso.getText()));
		return item;
	}

	public void clearItem() {
		tfIngresoID.setText("");
		tfDescripcionIngreso.setText("");
		tfMontoIngreso.setText("");
		tfIngresoID.requestFocus();
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
		tfFecha.setDate(new Date());
		tfDocumento.setText("");
		tfEntidad.setText("");
		tfNombreEntidad.setText("");
		tfCajaId.setText("");
		tfNombreCaja.setText("");
		tfCajeroId.setText("");
		tfNombreCajero.setText("");
		
		tfIngresoID.setText("");
		tfMontoIngreso.setText("");
		tfDescripcionIngreso.setText("");
		tfObs.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		tfCajaId.requestFocus();
	}

	public int removeItem() {
		int row[] = tbIngresos.getSelectedRows();

		if (tbIngresos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				itemTableModel.removeRow(row[i - 1]);
			}

			if (tbIngresos.getRowCount() == 0) {
				btnRemove.setEnabled(false);
			}
			Double total= itemTableModel.getEntities().stream().mapToDouble(i -> i.getMiiMonto()).sum();
			tfTotal.setText(FormatearValor.doubleAString(total));
			tfDescripcionIngreso.setText("");
			tfIngresoID.setText("");
			tfMontoIngreso.setText("");
			tfIngresoID.requestFocus();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}

		return row[0];
	}

		

	
	private void showDialog(int code) {
		switch (code) {
		case ENTIDAD_CODE:
		//	entidadDialog.setInterfaz(this);
//			entidadDialog.setVisible(true);
			break;
		case INGRESO_CODE:
			ingresoDialog.setInterfaz(this);
			ingresoDialog.loadIngresos("");;
			ingresoDialog.setVisible(true);
			break;
		case MOVIMIENTOINGRESO_CODE:
			movimientoIngresoDialog.setInterfaz(this);
			movimientoIngresoDialog.setVisible(true);
			try {
				movimientoIngresoDialog.loadMovimientoIngresos(new Date());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
		default:
			break;
		}
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (validateCabezera() && validateItems(itemTableModel.getEntities())) {
				MovimientoIngreso t = getFormValue();
					MovimientoIngreso v = movimientoIngresoService.save(t);
					if (v != null) {
						for (MovimientoItemIngreso mii : itemTableModel.getEntities()) {
							mii.setMiiNumero(v.getMinNumero());
						}
						movimientoItemIngresoService.save(itemTableModel.getEntities());
					}
					//Apertura de caja caso no este abierto
					lanzamientoCaja();
					//actualiza ingreso en movimiento Caja
					Optional<Caja> caja = cajaService.findById(1L);
					MovimientoCaja movCaja = new MovimientoCaja();
					movCaja.setCaja(caja.get());
					movCaja.setFecha(tfFecha.getDate());
					movCaja.setMoneda(new Moneda(1l));
					movCaja.setNotaNro(v.getMinNumero().toString());
					movCaja.setNotaReferencia(v.getMinNumero().toString());
					movCaja.setNotaValor(totalCalculado);
					movCaja.setPlanCuentaId(1);
					movCaja.setTipoOperacion("E");
					movCaja.setUsuario(GlobalVars.USER_ID);
					movCaja.setValorM01(totalCalculado);
					movCaja.setObs("Ingreso en caja 01 ");
					movCaja.setSituacion("PAGADO");
					pagoService.save(movCaja);

					Notifications.showAlert("Movimiento de Ingreso registrado con exito.!");
					clearForm();
					newMov();
			}
		} else {
			tfIngresoID.requestFocus();
		}
	}

	private void lanzamientoCaja() {
		Optional<Caja> caja = cajaService.findById(1l);
		if (caja.isPresent()) {
			Caja ca = caja.get();
			Optional<AperturaCierreCaja> movCaja = movCajaService.findByCajaAndFechaApertura(ca, new Date());
			if (!movCaja.isPresent()) {
				AperturaCierreCaja newMov = movCajaService.save(new AperturaCierreCaja(ca, new Date(), 0d));
			}
		}
	}
	
	private void eliminaMovimientoIngreso() {
		Optional<MovimientoIngreso> pOptional = movimientoIngresoService.findById(Integer.valueOf(this.getMovimientoIngresoSeleccionado().getMinNumero().toString()));
			if (pOptional.isPresent()) {
				MovimientoIngreso p = pOptional.get();
				movimientoIngresoService.remove(p);
				List<MovimientoItemIngreso> listaMovItemIngreso= movimientoItemIngresoService.findByCabId(p.getMinNumero());
				for (MovimientoItemIngreso movimientoItemIngreso : listaMovItemIngreso) {
					movimientoItemIngresoService.remove(movimientoItemIngreso);	
				}
				Optional<MovimientoCaja> movCaja = pagoService.findByIdVenta(p.getMinNumero().toString());
				pagoService.remove(movCaja.get());
				
			}
	}


	private boolean validateCabezera() {
		// validar deposito
		if (tfCajeroId.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Cajero es obligatorio");
			tfCajeroId.requestFocus();
			return false;
		} else if (tfCajaId.getText().isEmpty()) {
			Notifications.showAlert("El codigo de la caja es obligatorio");
			tfCajaId.requestFocus();
			return false;
		} 
		
		return true;
	}

	private boolean validateItems(List<MovimientoItemIngreso> items) {
		if(items.isEmpty()) {
			Notifications.showAlert("Ningun Item Ingreso encontrado !");
			return false;
		}
		for (MovimientoItemIngreso e : items) {
			Optional<Ingreso> ingreso = ingresoService.findById(Long.valueOf(e.getMiiIngreso()));

			if (!ingreso.isPresent()) {
				// verificar la cantidad
					// sinStock = true;
				Notifications.showAlert("Ningun Ingreso válido encontrado !");
					return false;
			}
		}
		return true;
	}

	private Boolean isValidItem() {
		if (tfIngresoID.getText().isEmpty()) {
			Notifications.showAlert("Digite el codigo del Producto");
			tfIngresoID.requestFocus();
			return false;
		} else if (tfMontoIngreso.getText().isEmpty()) {
			Notifications.showAlert("Digite el monto");
			tfMontoIngreso.requestFocus();
			return false;
		} 
		return true;
	}

	

	public void newMov() {
		clearForm();
		Optional<Caja> caja = cajaService.findById(1L);
		
		tfNombreCaja.setText(caja.get().getNombre());
		
		tfCajaId.setText("1");
		tfCajeroId.setText(GlobalVars.USER_ID.toString());
		tfNombreCajero.setText(GlobalVars.USER);
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		tfFecha.setDate(new Date());
		//Optional<Usuario> usuario= usuarioService.findById(GlobalVars.USER_ID);
		tfEntidad.setText(GlobalVars.USER_ID.toString());
		tfNombreEntidad.setText(GlobalVars.USER);
		long max = movimientoIngresoService.getRowCount();
		tfNota.setText(String.valueOf(max + 1));
		lblSituacion.setText("NUEVO");
		btnGuardar.setVisible(true);
		btnAnula.setVisible(false);
		tfIngresoID.requestFocus();
	}


	@Override
	public void getEntity(MovimientoIngreso movimientoIngreso) {
		if (movimientoIngreso != null) {
			setMovimientoIngresoSeleccionado(movimientoIngreso);
				if(movimientoIngreso.getMinSituacion()==0) {
					btnAnula.setVisible(true);	
				}else {
					btnAnula.setVisible(false);
				}
					btnGuardar.setVisible(false);
				tfNota.setText(movimientoIngreso.getMinNumero().toString());
				lblSituacion.setText(movimientoIngreso.getMinSituacion()==0?"VIGENTE":"ELIMINADO");
				tfCajaId.setText(movimientoIngreso.getMinCaja().toString());
				Optional<Caja> caja = cajaService.findById(Long.valueOf(movimientoIngreso.getMinCaja()));
				tfNombreCaja.setText(caja.get().getNombre());
				tfCajeroId.setText(GlobalVars.USER_ID.toString());
				tfNombreCajero.setText(GlobalVars.USER);
				tfFecha.setDate(movimientoIngreso.getFecha());
				tfDocumento.setText(movimientoIngreso.getMinDocumento());
				tfEntidad.setText(GlobalVars.USER);
				itemTableModel.clear();
				List<MovimientoItemIngreso> listMII = movimientoItemIngresoService.findByCabId(movimientoIngreso.getMinNumero());
				itemTableModel.addEntities(listMII);
				calculateItem();
		}
		
	}
	
	private void abandonarNota() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "ABANDONAR MOVIMIENTO.?", "AVISO",
				JOptionPane.OK_CANCEL_OPTION);

		if (respuesta == 0) {
			clearForm();
		} else {
			tfIngresoID.requestFocus();
		}
	}
	
	@Override
	public void getEntity(Ingreso ingreso) {
		if (ingreso != null) {
			setIngreso(ingreso);
			tfIngresoID.setText(ingreso.getId().toString());
			tfDescripcionIngreso.setText(ingreso.getIngDescripcion());
			tfMontoIngreso.requestFocus();
		}
		
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}
	
	public Ingreso getIngreso() {
		return ingreso;
	}

	public MovimientoIngreso getMovimientoIngresoSeleccionado() {
		return movimientoIngresoSeleccionado;
	}

	public void setMovimientoIngresoSeleccionado(MovimientoIngreso movimientoIngresoSeleccionado) {
		this.movimientoIngresoSeleccionado = movimientoIngresoSeleccionado;
	}

	public Double getTotalCalculado() {
		return totalCalculado;
	}

	public void setTotalCalculado(Double totalCalculado) {
		this.totalCalculado = totalCalculado;
	}
}

