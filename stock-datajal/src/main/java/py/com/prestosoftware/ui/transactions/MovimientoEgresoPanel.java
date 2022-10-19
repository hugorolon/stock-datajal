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
import py.com.prestosoftware.data.models.Egreso;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.MovimientoEgreso;
import py.com.prestosoftware.data.models.MovimientoItemEgreso;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.EgresoService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.MovimientoEgresoService;
import py.com.prestosoftware.domain.services.MovimientoItemEgresoService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.EgresoDialog;
import py.com.prestosoftware.ui.search.EgresoInterfaz;
import py.com.prestosoftware.ui.search.MovimientoEgresoDialog;
import py.com.prestosoftware.ui.search.MovimientoEgresoInterfaz;
import py.com.prestosoftware.ui.table.MovimientoItemEgresoTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class MovimientoEgresoPanel extends JDialog implements MovimientoEgresoInterfaz, EgresoInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int ENTIDAD_CODE = 1;
	private static final int INGRESO_CODE = 2;
	private static final int MOVIMIENTOINGRESO_CODE = 3;	
	
	private JTextField tfNombreCaja;
	private JTextField tfDescripcionEgreso;
	private JTextField tfEgresoID;
	private JTextField tfCajaId;
	private JTable tbEgresos;
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

	private MovimientoItemEgresoTableModel itemTableModel;
	private EgresoDialog egresoDialog;
	private EgresoService egresoService;
	private CajaService cajaService;
	private AperturaCierreCajaService movCajaService;
	private UsuarioService usuarioService;
	private MovimientoEgresoService movimientoEgresoService;
	private MovimientoItemEgresoService movimientoItemEgresoService;
	private MovimientoCajaService pagoService;
	private MovimientoEgreso movimientoEgresoSeleccionado;
	private Double totalCalculado; 
	private Egreso egreso;
	private MovimientoEgresoDialog movimientoEgresoDialog;
	private boolean bandAgregar=true;

	@Autowired
	public MovimientoEgresoPanel(MovimientoEgresoService movimientoEgresoService, MovimientoItemEgresoService movimientoItemEgresoService, MovimientoItemEgresoTableModel itemTableModel, EgresoService egresoService,
			CajaService cajaService, UsuarioService usuarioService, EgresoDialog egresoDialog, MovimientoEgresoDialog movimientoEgresoDialog, MovimientoCajaService pagoService, AperturaCierreCajaService movCajaService) {
		this.itemTableModel = itemTableModel;
		this.egresoService = egresoService;
		this.movimientoEgresoService = movimientoEgresoService;
		this.movimientoItemEgresoService = movimientoItemEgresoService;
		this.cajaService =cajaService;
		this.usuarioService =usuarioService;
		this.egresoDialog =egresoDialog;
		this.movimientoEgresoDialog =movimientoEgresoDialog;
		this.pagoService = pagoService;
		this.movCajaService =movCajaService;

		setSize(750, 483);
		setTitle("MOVIMIENTO EGRESO");
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
		tabbedPane.addTab("SALIDA DE DINERO EN CAJA", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 11, 717, 64);
		pnlProducto.add(panel_1);
		panel_1.setLayout(null);
		
		tfFecha = new JXDatePicker();
		tfFecha.setFormats("dd/MM/yyyy");
		//tfFecha.setEditable(false);
		tfFecha.setBounds(10, 32, 111, 30);
		panel_1.add(tfFecha);
		//tfFecha.setColumns(10);
		
		tfDocumento = new JTextField();
		tfDocumento.setBounds(131, 32, 86, 30);
		panel_1.add(tfDocumento);
		tfDocumento.setColumns(10);

		tfEntidad = new JTextField();
		tfEntidad.setEditable(false);
		tfEntidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEntidad.setBounds(229, 32, 53, 30);
		panel_1.add(tfEntidad);
		tfEntidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfEntidad.setColumns(10);

		JLabel lblEntidad = new JLabel("ENTIDAD");
		lblEntidad.setBounds(229, 0, 63, 30);
		panel_1.add(lblEntidad);

		lblDescripcionProductoOrigen = new JLabel("DOCUMENTO");
		lblDescripcionProductoOrigen.setBounds(131, 0, 86, 30);
		panel_1.add(lblDescripcionProductoOrigen);

		lblFecha = new JLabel("FECHA");
		lblFecha.setBounds(10, 0, 63, 30);
		panel_1.add(lblFecha);
		
		tfNombreEntidad = new JTextField();
		tfNombreEntidad.setEditable(false);
		tfNombreEntidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNombreEntidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNombreEntidad.setColumns(10);
		tfNombreEntidad.setBounds(292, 32, 384, 30);
		panel_1.add(tfNombreEntidad);

		panel_2 = new JPanel();
		panel_2.setBounds(6, 97, 717, 176);
		pnlProducto.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblCodigoEgreso = new JLabel("CODIGO");
		lblCodigoEgreso.setBounds(10, 0, 63, 30);
		panel_2.add(lblCodigoEgreso);

		JLabel lblDescripcionEgreso = new JLabel("DESCRIPCIÓN INGRESO");
		lblDescripcionEgreso.setBounds(77, 0, 364, 30);
		panel_2.add(lblDescripcionEgreso);

		tfEgresoID = new JTextField();
		tfEgresoID.setBounds(10, 26, 63, 30);
		panel_2.add(tfEgresoID);
		tfEgresoID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfEgresoID.selectAll();
			}
		});
		tfEgresoID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfEgresoID.getText().isEmpty()) {
						findEgresoById(Long.valueOf(tfEgresoID.getText()),INGRESO_CODE);
					} else {
						showDialog(INGRESO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfMontoEgreso.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Integer respuesta = JOptionPane.showConfirmDialog(null, "Abandonar Movimiento.?");
					if (respuesta == 0) {
						clearForm();
						newMov();
					} else {
						tfEgresoID.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfEgresoID.setFont(new Font("Arial", Font.PLAIN, 14));
		tfEgresoID.setColumns(10);

		tfDescripcionEgreso = new JTextField();
		tfDescripcionEgreso.setBounds(77, 26, 419, 30);
		panel_2.add(tfDescripcionEgreso);
		tfDescripcionEgreso.setEditable(false);
		tfDescripcionEgreso.setFont(new Font("Arial", Font.PLAIN, 14));
		tfDescripcionEgreso.setColumns(10);

		btnAdd = new JButton(" + ");
		btnAdd.setBounds(656, 67, 51, 30);
		panel_2.add(btnAdd);

		btnRemove = new JButton(" - ");
		btnRemove.setBounds(656, 101, 51, 30);
		panel_2.add(btnRemove);

		JScrollPane scrollMovimientoEgreso = new JScrollPane();
		scrollMovimientoEgreso.setBounds(10, 67, 630, 78);
		panel_2.add(scrollMovimientoEgreso);

		tbEgresos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};

		tbEgresos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbEgresos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbEgresos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getItemSelected();
			}
		});
		tbEgresos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getItemSelected();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfEgresoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});
		scrollMovimientoEgreso.setViewportView(tbEgresos);

		lblMontoEgreso = new JLabel("MONTO DEL INGRESO");
		lblMontoEgreso.setBounds(506, 0, 134, 30);
		panel_2.add(lblMontoEgreso);

		tfMontoEgreso = new JTextField();
		tfMontoEgreso.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMontoEgreso.setBounds(506, 26, 134, 30);
		panel_2.add(tfMontoEgreso);
		tfMontoEgreso.setColumns(10);
		tfMontoEgreso.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfMontoEgreso.selectAll();
			}
			
		});
		tfMontoEgreso.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				bandAgregar=true;
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAdd.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfEgresoID.requestFocus();
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
		
		JLabel lblTotalMontoEgreso = new JLabel("TOTAL  INGRESO");
		lblTotalMontoEgreso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalMontoEgreso.setBounds(339, 146, 134, 30);
		panel_2.add(lblTotalMontoEgreso);
		
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
					tfEgresoID.requestFocus();
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

		btnGuardar = new JButton("Guardar");
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				}
				else {
					if (e.getKeyCode() == KeyEvent.VK_F4) {
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
		this.getMovimientoEgresoSeleccionado().setMegSituacion(1);
		eliminaMovimientoEgreso();
		clearForm();
		newMov();
	}

	public void setCliente(Optional<Cliente> cliente) {
		if (cliente.isPresent()) {
			String nombre = cliente.get().getRazonSocial();
			tfNombreCaja.setText(nombre);
			tfEgresoID.requestFocus();
		}
	}

	private void findEgresoById(Long id, int desde) {
		Optional<Egreso> egreso = egresoService.findById(id);

		if (egreso.isPresent()) {
			setEgreso(egreso);
		}
	}

	public void setEgreso(Optional<Egreso> egreso) {
		if (egreso.isPresent()) {
			String nombre = egreso.get().getEgrDescripcion();
				tfDescripcionEgreso.setText(nombre);
				tfMontoEgreso.requestFocus();
		}
	}

	private void getItemSelected() {
		int selectedRow = tbEgresos.getSelectedRow();

		if (selectedRow != -1) {
			MovimientoItemEgreso item = itemTableModel.getEntityByRow(selectedRow);
			tfEgresoID.setText(String.valueOf(item.getMieEgreso()));
			tfMontoEgreso.setText(FormatearValor.doubleAString(item.getMieMonto()));
			Optional<Egreso> egresoSeleccionado =egresoService.findById(Long.valueOf(item.getMieEgreso()));
			tfDescripcionEgreso.setText(String.valueOf(egresoSeleccionado.get().getEgrDescripcion()));
		}
	}

	private void addItem() {
		Boolean esDuplicado = false;
		Integer fila = -1;
		for (Integer i = 0; i < tbEgresos.getRowCount(); i++) {
			String itemId = String.valueOf(tbEgresos.getValueAt(i, 0));
			if (tfEgresoID.getText().trim().equals(itemId) && !itemId.equals("")) {
				esDuplicado = true;
				fila = i;
			}
		}
		if (esDuplicado) {
			Integer respuesta = JOptionPane.showConfirmDialog(null,
					"Registro ya existe en la grilla, desea actualizar los datos?");
			if (respuesta == 0) {
				if (isValidItem()) {
					actualizarRegristroGrilla(fila, String.valueOf(tbEgresos.getValueAt(fila, 2)));
					tfEgresoID.requestFocus();
				}
			} else {
				tfEgresoID.requestFocus();
			}
		} else {
			Long egresoId = Long.valueOf(tfEgresoID.getText());
			Optional<Egreso> p = egresoService.findById(egresoId);
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
		totalCalculado = itemTableModel.getEntities().stream().mapToDouble(i -> i.getMieMonto()).sum();
		tfTotal.setText(FormatearValor.doubleAString(totalCalculado));
	}

	private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
		itemTableModel.removeRow(fila);

		Long egresoId = Long.valueOf(tfEgresoID.getText());

		Optional<Egreso> p = egresoService.findById(egresoId);

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
	private JLabel lblMontoEgreso;
	private JTextField tfMontoEgreso;
	private JButton btnAnula;
	private JTextField tfNombreEntidad;
	private JTextField tfTotal;

	public MovimientoEgreso getFormValue() {
		MovimientoEgreso t = new MovimientoEgreso();
		t.setMegCaja(Integer.valueOf(tfCajaId.getText()));
		t.setFecha(tfFecha.getDate());
		t.setHora(new Date());
		t.setMegSituacion(0);
		t.setMegDocumento(tfDocumento.getText());
		t.setMegTipoEntidad(8);
		t.setMegEntidad(tfEntidad.getText());
		t.setMegNumero(Integer.valueOf(tfNota.getText()));
		t.setMegTipoProceso(0);
		t.setMegProceso(0);
		//t.setMegEntidad("1");
		return t;
	}

	public MovimientoItemEgreso getItem() {
		MovimientoItemEgreso item = new MovimientoItemEgreso();
		item.setMieEgreso(Integer.valueOf(tfEgresoID.getText()));
		item.setMieDescripcion(tfDescripcionEgreso.getText());
		item.setMieMonto(Double.valueOf(tfMontoEgreso.getText()));
		return item;
	}

	public void clearItem() {
		tfEgresoID.setText("");
		tfDescripcionEgreso.setText("");
		tfMontoEgreso.setText("");
		tfEgresoID.requestFocus();
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
		
		tfEgresoID.setText("");
		tfMontoEgreso.setText("");
		tfDescripcionEgreso.setText("");
		tfObs.setText("");

		while (itemTableModel.getRowCount() > 0) {
			itemTableModel.removeRow(0);
		}

		tfCajaId.requestFocus();
	}

	public int removeItem() {
		int row[] = tbEgresos.getSelectedRows();

		if (tbEgresos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				itemTableModel.removeRow(row[i - 1]);
			}

			if (tbEgresos.getRowCount() == 0) {
				btnRemove.setEnabled(false);
			}
			Double total= itemTableModel.getEntities().stream().mapToDouble(i -> i.getMieMonto()).sum();
			tfTotal.setText(FormatearValor.doubleAString(total));
			tfDescripcionEgreso.setText("");
			tfEgresoID.setText("");
			tfMontoEgreso.setText("");
			tfEgresoID.requestFocus();
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
			egresoDialog.setInterfaz(this);
			egresoDialog.loadEgresos("");;
			egresoDialog.setVisible(true);
			break;
		case MOVIMIENTOINGRESO_CODE:
			movimientoEgresoDialog.setInterfaz(this);
			movimientoEgresoDialog.setVisible(true);
			movimientoEgresoDialog.loadMovimientoEgresos("");
			break;	
		default:
			break;
		}
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (validateCabezera() && validateItems(itemTableModel.getEntities())) {
				MovimientoEgreso t = getFormValue();
					MovimientoEgreso v = movimientoEgresoService.save(t);
					if (v != null) {
						for (MovimientoItemEgreso mie : itemTableModel.getEntities()) {
							mie.setMieNumero(v.getMegNumero());
						}
						movimientoItemEgresoService.save(itemTableModel.getEntities());
					}
					//Apertura de caja caso no este abierto
					lanzamientoCaja();
					//actualiza egreso en movimiento Caja
					Optional<Caja> caja = cajaService.findById(1L);
					MovimientoCaja movCaja = new MovimientoCaja();
					movCaja.setCaja(caja.get());
					movCaja.setFecha(tfFecha.getDate());
					movCaja.setMoneda(new Moneda(1l));
					movCaja.setNotaNro(v.getMegNumero().toString());
					movCaja.setNotaReferencia(v.getMegNumero().toString());
					movCaja.setNotaValor(totalCalculado);
					movCaja.setPlanCuentaId(2);
					movCaja.setTipoOperacion("S");
					movCaja.setUsuario(GlobalVars.USER_ID);
					movCaja.setValorM01(totalCalculado);
					movCaja.setObs("Egreso en caja 01 ");
					movCaja.setSituacion("PAGADO");
					pagoService.save(movCaja);

					Notifications.showAlert("Movimiento de Egreso registrado con exito.!");
					clearForm();
					newMov();
			}
		} else {
			tfEgresoID.requestFocus();
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
	
	private void eliminaMovimientoEgreso() {
		Optional<MovimientoEgreso> pOptional = movimientoEgresoService.findById(Integer.valueOf(this.getMovimientoEgresoSeleccionado().getMegNumero().toString()));
			if (pOptional.isPresent()) {
				MovimientoEgreso p = pOptional.get();
				movimientoEgresoService.remove(p);
				List<MovimientoItemEgreso> listaMovItemEgreso= movimientoItemEgresoService.findByCabId(p.getMegNumero());
				for (MovimientoItemEgreso movimientoItemEgreso : listaMovItemEgreso) {
					movimientoItemEgresoService.remove(movimientoItemEgreso);	
				}
				Optional<MovimientoCaja> movCaja = pagoService.findByIdVenta(p.getMegNumero().toString());
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

	private boolean validateItems(List<MovimientoItemEgreso> items) {
		if(items.isEmpty()) {
			Notifications.showAlert("Ningun Item Egreso encontrado !");
			return false;
		}
		for (MovimientoItemEgreso e : items) {
			Optional<Egreso> egreso = egresoService.findById(Long.valueOf(e.getMieEgreso()));

			if (!egreso.isPresent()) {
				// verificar la cantidad
					// sinStock = true;
				Notifications.showAlert("Ningun Egreso válido encontrado !");
					return false;
			}
		}
		return true;
	}

	private Boolean isValidItem() {
		if (tfEgresoID.getText().isEmpty()) {
			Notifications.showAlert("Digite el codigo del Producto");
			tfEgresoID.requestFocus();
			return false;
		} else if (tfMontoEgreso.getText().isEmpty()) {
			Notifications.showAlert("Digite el monto");
			tfMontoEgreso.requestFocus();
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
		tfFecha.setDate(new Date());
		//Optional<Usuario> usuario= usuarioService.findById(GlobalVars.USER_ID);
		tfEntidad.setText(GlobalVars.USER_ID.toString());
		tfNombreEntidad.setText(GlobalVars.USER);
		long max = movimientoEgresoService.getRowCount();
		tfNota.setText(String.valueOf(max + 1));
		lblSituacion.setText("NUEVO");
		btnGuardar.setVisible(true);
		btnAnula.setVisible(false);
		tfEgresoID.requestFocus();
	}


	@Override
	public void getEntity(MovimientoEgreso movimientoEgreso) {
		if (movimientoEgreso != null) {
			setMovimientoEgresoSeleccionado(movimientoEgreso);
				if(movimientoEgreso.getMegSituacion()==0) {
					btnAnula.setVisible(true);	
				}else {
					btnAnula.setVisible(false);
				}
					btnGuardar.setVisible(false);
				tfNota.setText(movimientoEgreso.getMegNumero().toString());
				lblSituacion.setText(movimientoEgreso.getMegSituacion()==0?"VIGENTE":"ELIMINADO");
				tfCajaId.setText(movimientoEgreso.getMegCaja().toString());
				Optional<Caja> caja = cajaService.findById(Long.valueOf(movimientoEgreso.getMegCaja()));
				tfNombreCaja.setText(caja.get().getNombre());
				tfCajeroId.setText(GlobalVars.USER_ID.toString());
				tfNombreCajero.setText(GlobalVars.USER);
				tfFecha.setDate(movimientoEgreso.getFecha());
				tfDocumento.setText(movimientoEgreso.getMegDocumento());
				tfEntidad.setText(GlobalVars.USER);
				itemTableModel.clear();
				List<MovimientoItemEgreso> listMII = movimientoItemEgresoService.findByCabId(movimientoEgreso.getMegNumero());
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
			tfEgresoID.requestFocus();
		}
	}
	
	@Override
	public void getEntity(Egreso egreso) {
		if (egreso != null) {
			setEgreso(egreso);
			tfEgresoID.setText(egreso.getId().toString());
			tfDescripcionEgreso.setText(egreso.getEgrDescripcion());
			tfMontoEgreso.requestFocus();
		}
		
	}

	public void setEgreso(Egreso egreso) {
		this.egreso = egreso;
	}
	
	public Egreso getEgreso() {
		return egreso;
	}

	public MovimientoEgreso getMovimientoEgresoSeleccionado() {
		return movimientoEgresoSeleccionado;
	}

	public void setMovimientoEgresoSeleccionado(MovimientoEgreso movimientoEgresoSeleccionado) {
		this.movimientoEgresoSeleccionado = movimientoEgresoSeleccionado;
	}

	public Double getTotalCalculado() {
		return totalCalculado;
	}

	public void setTotalCalculado(Double totalCalculado) {
		this.totalCalculado = totalCalculado;
	}
}

