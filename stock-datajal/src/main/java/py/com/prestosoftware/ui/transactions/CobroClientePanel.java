package py.com.prestosoftware.ui.transactions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.CobroCliente;
import py.com.prestosoftware.data.models.ItemCobroCliente;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CobroClienteService;
import py.com.prestosoftware.domain.services.ItemCobroClienteService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.CobroClienteDialog;
import py.com.prestosoftware.ui.search.CobroClienteInterfaz;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.table.DetalleCuentaClienteTableModel;
import py.com.prestosoftware.ui.viewmodel.DetalleCobroClienteView;
import py.com.prestosoftware.util.Notifications;

@Service
public class CobroClientePanel extends JDialog implements CobroClienteInterfaz, ClienteInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int COBRO_CLIENTE_CODE = 1;
	private static final int ENTIDAD_CODE = 2;
	private JTable tbDetalleCobroCliente;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnCerrar;
	private JButton btnVer;
	private JLabel lblSituacion;
	private JLabel lblNota;
	private JTextField tfNota;
	private JTextField tfFecha;
	private JTextField tfDocumento;
	private JTextField tfEntidad;
	private JLabel lblNroRecibo;
	private JLabel lblFecha;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JButton btnAnula;
	private JTextField tfNombreEntidad;
	private JCheckBox chkCobraTodos;
	private JTextField tfSaldo;
	private JTextField tfDescuentos;
	private JTextField tfRecargos;
	private JTextField tfMontoACobrar;
	private JTextField tfObs;
	private JTextField tfTotalACobrar;
	private JLabel lblMontoACobrar;

	private DetalleCuentaClienteTableModel itemTableModel;
	// private DetalleCobroClienteTableModel itemTableModel;
	@Autowired
	private CajaService cajaService;
	@Autowired
	private AperturaCierreCajaService movCajaService;
	@Autowired
	private CobroClienteService cobroClienteService;
	@Autowired
	private ItemCobroClienteService itemCobroClienteService;

	@Autowired
	private ClienteService clienteService;
	@Autowired
	private MovimientoCajaService pagoService;
	private CobroCliente cobroClienteSeleccionado;
	private Double totalCalculado;
	private CobroClienteDialog cobroClienteDialog;
	private ConsultaCliente clienteDialog;

	@Autowired
	public CobroClientePanel(CobroClienteService cobroClienteService, DetalleCuentaClienteTableModel itemTableModel,
			ItemCobroClienteService itemCobroClienteService, ClienteService clienteService, CajaService cajaService,
			CobroClienteDialog cobroClienteDialog, ConsultaCliente clienteDialog, MovimientoCajaService pagoService,
			AperturaCierreCajaService movCajaService) {
		this.cobroClienteService = cobroClienteService;
		this.itemTableModel = itemTableModel;
		this.cajaService = cajaService;
		this.cobroClienteDialog = cobroClienteDialog;
		this.clienteService = clienteService;
		this.pagoService = pagoService;
		this.movCajaService = movCajaService;
		this.clienteDialog = clienteDialog;
		this.itemCobroClienteService = itemCobroClienteService;
		setSize(914, 811);
		setTitle("COBRO CLIENTE");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		initComponents();
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 76, 887, 635);
		getContentPane().add(tabbedPane);

		JPanel pnlProducto = new JPanel();
		tabbedPane.addTab("COBRO DE CUENTAS", null, pnlProducto, null);
		pnlProducto.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 11, 836, 64);
		pnlProducto.add(panel_1);
		panel_1.setLayout(null);

		tfFecha = new JTextField();
		tfFecha.setEditable(false);
		tfFecha.setBounds(10, 32, 77, 30);
		panel_1.add(tfFecha);
		tfFecha.setColumns(10);

		tfDocumento = new JTextField();
		tfDocumento.setBounds(97, 32, 86, 30);
		panel_1.add(tfDocumento);
		tfDocumento.setColumns(10);

		tfEntidad = new JTextField();
		tfEntidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfEntidad.setBounds(193, 32, 63, 30);
		panel_1.add(tfEntidad);
		tfEntidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfEntidad.setColumns(10);
		tfEntidad.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfEntidad.selectAll();
			}
		});
		tfEntidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(ENTIDAD_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					abandonarNota();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfEntidad.getText().isEmpty()) {
						findEntidad(tfEntidad.getText());
					} else {
						showDialog(ENTIDAD_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					chkCobraTodos.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Util.validateNumero(e);
			}
		});

		JLabel lblEntidad = new JLabel("CLIENTE");
		lblEntidad.setBounds(193, 0, 63, 30);
		panel_1.add(lblEntidad);

		lblNroRecibo = new JLabel("NRO. RECIBO");
		lblNroRecibo.setBounds(97, 0, 110, 30);
		panel_1.add(lblNroRecibo);

		lblFecha = new JLabel("FECHA");
		lblFecha.setBounds(10, 0, 63, 30);
		panel_1.add(lblFecha);

		tfNombreEntidad = new JTextField();
		tfNombreEntidad.setEditable(false);
		tfNombreEntidad.setHorizontalAlignment(SwingConstants.RIGHT);
		tfNombreEntidad.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNombreEntidad.setColumns(10);
		tfNombreEntidad.setBounds(261, 32, 435, 30);
		panel_1.add(tfNombreEntidad);

		chkCobraTodos = new JCheckBox("COBRAR TODOS");
		chkCobraTodos.setBounds(702, 35, 128, 23);
		chkCobraTodos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chkCobraTodos.isSelected()) {
					cargaSaldo();
					calculateItem();
					itemTableModel.fireTableDataChanged();
					tfSaldo.setText(tfTotalACobrar.getText());
					tfMontoACobrar.setText(tfTotalACobrar.getText());
				} else {
					descargaSaldo();
					itemTableModel.fireTableDataChanged();
					tfSaldo.setText("0");
					tfMontoACobrar.setText("0");
				}

			}

			private void cargaSaldo() {
				for (DetalleCobroClienteView ie : itemTableModel.getEntities()) {
					ie.setCobro(ie.getIca_monto1());
				}
			}
			private void descargaSaldo() {
				for (DetalleCobroClienteView ie : itemTableModel.getEntities()) {
					ie.setCobro(0d);
				}
			}
		});
		panel_1.add(chkCobraTodos);

		panel_2 = new JPanel();
		panel_2.setBounds(6, 97, 866, 501);
		pnlProducto.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollDetalleCobroClientes = new JScrollPane();
		scrollDetalleCobroClientes.setBounds(10, 11, 835, 395);
		panel_2.add(scrollDetalleCobroClientes);

		tbDetalleCobroCliente = new JTable(itemTableModel) {//new SelectingTables(itemTableModel);//
			public boolean isCellEditable(int fila, int columna) {
				if (columna == 8)
					return true;
				return false;
			}
			public void changeSelection(int row, int column, boolean toggle, boolean extend)
			{
				super.changeSelection(row, column, toggle, extend);

				if (editCellAt(row, column))
				{
					Component editor = getEditorComponent();
					editor.requestFocusInWindow();
					((JTextComponent)editor).selectAll();
				}
			}
		};

		tbDetalleCobroCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbDetalleCobroCliente.setCellSelectionEnabled(true);
		tbDetalleCobroCliente.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbDetalleCobroCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getItemSelected();
				calculateItem();
			}
			
			public void mouseEntered(MouseEvent event) {
		        // Request the focus (if don't already have it)
		        if(!hasFocus()) { 
		        	requestFocus(); 
		        }
		    }
		});
		tbDetalleCobroCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getItemSelected();
					calculateItem();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					calculateItem();
					//btnGuardar.requestFocus();
				} else if(e.getKeyCode()== KeyEvent.VK_TAB) {
					calculateItem();
				}
			}
		});
		tbDetalleCobroCliente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
   			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
   				calculateItem();
			}  
        });  
		
		scrollDetalleCobroClientes.setViewportView(tbDetalleCobroCliente);

		JLabel lblTotalMontoIngreso = new JLabel("TOTALES");
		lblTotalMontoIngreso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalMontoIngreso.setBounds(426, 406, 91, 30);
		panel_2.add(lblTotalMontoIngreso);

		tfSaldo = new JTextField();
		tfSaldo.setEditable(false);
		tfSaldo.setText("");
		tfSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		tfSaldo.setColumns(10);
		tfSaldo.setBounds(711, 406, 134, 30);
		panel_2.add(tfSaldo);

		JLabel lblDescuentos = new JLabel("DESCUENTOS");
		lblDescuentos.setBounds(148, 446, 79, 14);
		panel_2.add(lblDescuentos);

		tfDescuentos = new JTextField();
		tfDescuentos.setHorizontalAlignment(SwingConstants.RIGHT);
		tfDescuentos.setBounds(148, 461, 163, 30);
		tfDescuentos.addFocusListener(new FocusAdapter() {

	@Override
			public void focusGained(FocusEvent e) {
				tfDescuentos.selectAll();
			}
		});
		tfDescuentos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfDescuentos.getText().isEmpty()&&!tfDescuentos.getText().toString().equalsIgnoreCase("0")) {
						tfRecargos.setText("0");
						calculateMontoTotal(0);
					} else {
						Notifications.showAlert("Digite el monto a descontar");
						tfDescuentos.requestFocus();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfRecargos.requestFocus();
				} 
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		panel_2.add(tfDescuentos);
		tfDescuentos.setColumns(10);

		tfRecargos = new JTextField();
		tfRecargos.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecargos.setColumns(10);
		tfRecargos.setBounds(321, 461, 163, 30);
		tfRecargos.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecargos.selectAll();
			}
		});
		tfRecargos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfRecargos.getText().isEmpty()&&!tfRecargos.getText().toString().equalsIgnoreCase("0")) {
						calculateMontoTotal(1);
						tfDescuentos.setText("0");
					} else {
						Notifications.showAlert("Digite el monto a recargar");
						tfRecargos.requestFocus();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfRecargos.requestFocus();
				} 
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		panel_2.add(tfRecargos);

		JLabel lblRecargos = new JLabel("RECARGOS");
		lblRecargos.setBounds(321, 446, 79, 14);
		panel_2.add(lblRecargos);

		tfMontoACobrar = new JTextField();
		tfMontoACobrar.setEditable(false);
		tfMontoACobrar.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMontoACobrar.setColumns(10);
		tfMontoACobrar.setBounds(492, 461, 163, 30);
		panel_2.add(tfMontoACobrar);

		lblMontoACobrar = new JLabel("MONTO A COBRAR");
		lblMontoACobrar.setBounds(492, 446, 125, 14);
		panel_2.add(lblMontoACobrar);

		tfObs = new JTextField();
		tfObs.setHorizontalAlignment(SwingConstants.LEFT);
		tfObs.setColumns(10);
		tfObs.setBounds(665, 461, 180, 30);
		panel_2.add(tfObs);

		JLabel lblOBS = new JLabel("OBS.");
		lblOBS.setBounds(665, 446, 79, 14);
		panel_2.add(lblOBS);

		tfTotalACobrar = new JTextField();
		tfTotalACobrar.setText("");
		tfTotalACobrar.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalACobrar.setEditable(false);
		tfTotalACobrar.setColumns(10);
		tfTotalACobrar.setBounds(576, 406, 134, 30);
		panel_2.add(tfTotalACobrar);

		lblNewLabel = new JLabel("DETALLES CUENTA CLIENTE");
		lblNewLabel.setBounds(6, 75, 145, 27);
		pnlProducto.add(lblNewLabel);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Seleccione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(6, 6, 876, 69);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JPanel pnlCliente = new JPanel();
		pnlCliente.setBounds(6, 18, 860, 45);
		panel_3.add(pnlCliente);
		pnlCliente.setLayout(null);

		lblNota = new JLabel("Nota:");
		lblNota.setBounds(6, 7, 27, 30);
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
					showDialog(COBRO_CLIENTE_CODE);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(COBRO_CLIENTE_CODE);
			}
		});

		lblSituacion = new JLabel("Nuevo");
		lblSituacion.setBounds(113, 15, 59, 14);
		pnlCliente.add(lblSituacion);

		JPanel panel = new JPanel();
		panel.setBounds(10, 721, 883, 35);
		getContentPane().add(panel);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				} else {
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
		
		TableCellRenderer render = new TableCellRenderer() {

	        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,

	                boolean hasFocus, int row, int column) {

	        	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            	DecimalFormat formatea = new DecimalFormat("###,###");
            	String valor="";
            	if(column==2 || column ==4)
            		valor=sdf.format(value);
            	else if(column==7)
            		valor= formatea.format(value);
            	else
            		valor=value.toString();	
        
                JLabel lbl = new JLabel(valor);

                //lbl.setHorizontalAlignment(SwingConstants.CENTER); //alina a laizquierda
                if(column == 2 || column == 4){ //color de fondo
                    //lbl.setOpaque(true);
                	lbl.setHorizontalAlignment(SwingConstants.CENTER); //alina a laizquierda
                }

                if(column == 7){ //color de fondo
                    //lbl.setOpaque(true);
                	lbl.setHorizontalAlignment(SwingConstants.RIGHT); //alina a laizquierda
                	lbl.setForeground(Color.BLUE);  //fuente azul
                    lbl.setBackground(Color.GRAY);
                }
                return lbl;
	        }

	    };
	    tbDetalleCobroCliente.getColumnModel().getColumn(0).setPreferredWidth(100);
	    tbDetalleCobroCliente.getColumnModel().getColumn(2).setCellRenderer(render);
	    tbDetalleCobroCliente.getColumnModel().getColumn(4).setCellRenderer(render);
	    //tbDetalleCobroCliente.getColumnModel().getColumn(7).setCellRenderer(render);
		clearForm();
		// newMov();
	}

	private void calculateMontoTotal(int origen) {
		try {
			Double aCobrar = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCobro()).sum();
			if (aCobrar > 0) {
				Double totalCta = FormatearValor.stringToDouble(tfSaldo.getText());
				Double descuento = 0d;
				Double recargo = 0d;
				if (!tfDescuentos.getText().isEmpty() && !tfDescuentos.getText().toString().equalsIgnoreCase("0")) {
					descuento = FormatearValor.stringToDouble(tfDescuentos.getText());
					tfDescuentos.setText(FormatearValor.doubleAString(descuento));
					btnGuardar.requestFocus();
				}
				if (!tfRecargos.getText().isEmpty() && !tfRecargos.getText().toString().equalsIgnoreCase("0")) {
					recargo = FormatearValor.stringToDouble(tfRecargos.getText());
					tfRecargos.setText(FormatearValor.doubleAString(recargo));
					btnGuardar.requestFocus();
				}
				totalCalculado = totalCta - descuento + recargo;
				tfMontoACobrar.setText(FormatearValor.doubleAString(totalCalculado));
				if (origen == 0)
					tfRecargos.requestFocus();
				else
					tfMontoACobrar.requestFocus();

			} else {
				tfRecargos.setText("0");
				tfDescuentos.setText("0");
				Notifications.showAlert("Debe cargar primero los datos a pagar.!");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Transactional
	protected void elimina() {
		btnAnula.setVisible(false);
		btnGuardar.setVisible(true);
		this.getCobroClienteSeleccionado().setCclSituacion(1);
		eliminaCobroCliente();
		clearForm();
		newMov();
	}

	public void setCliente(Optional<Cliente> cliente) {
		if (cliente.isPresent()) {
			tfNombreEntidad.setText(cliente.get().getNombre());
		}
	}

	private void getItemSelected() {
		int selectedRow = tbDetalleCobroCliente.getSelectedRow();

		if (selectedRow != -1) {
			/*
			 * DetalleCobroClienteView item = itemTableModel.g.getEntityByRow(selectedRow);
			 * tfIngresoID.setText(String.valueOf(item.getMiiIngreso()));
			 * tfMontoIngreso.setText(FormatearValor.doubleAString(item.getMiiMonto()));
			 * Optional<Ingreso> ingresoSeleccionado
			 * =ingresoService.findById(Long.valueOf(item.getMiiIngreso()));
			 * tfDescripcionIngreso.setText(String.valueOf(ingresoSeleccionado.get().
			 * getIngDescripcion()));
			 */
		}
	}

	public CobroCliente getFormValue() {
		try {
			CobroCliente t = new CobroCliente();
			t.setFecha(new Date());
			t.setHora(new Date());
			t.setCclSituacion(0);
			t.setCclDocumento(tfDocumento.getText());
			t.setCclTipoEntidad(8);
			t.setCclEntidad(Integer.valueOf(tfEntidad.getText()));
			t.setCclNumero(Integer.valueOf(tfNota.getText()));
			t.setCclValor(totalCalculado);
			Double descuento = FormatearValor.desformatearValor(tfDescuentos.getText());
			Double interes = FormatearValor.desformatearValor(tfRecargos.getText());
			t.setCclMonto(totalCalculado - descuento + interes);
			t.setCclDescuento(descuento);
			t.setCclRecargo(interes);
			t.setCclFormaPago(1);
			t.setCclMoneda(1);
			return t;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public void clearItem() {

	}

	public void clearForm() {
		tfFecha.setText("");
		tfDocumento.setText("");
		tfEntidad.setText("");
		tfNombreEntidad.setText("");
		tfTotalACobrar.setText("0");
		tfMontoACobrar.setText("0");
		tfDescuentos.setText("0");
		tfRecargos.setText("0");
		tfSaldo.setText("0");
		tfObs.setText("");
		itemTableModel.clear();
		itemTableModel.fireTableDataChanged();
	}

	private void showDialog(int code) {
		switch (code) {
		case COBRO_CLIENTE_CODE:
			cobroClienteDialog.setInterfaz(this);
			cobroClienteDialog.loadCobroClientes("");
			cobroClienteDialog.setVisible(true);
			break;
		case ENTIDAD_CODE:
			clienteDialog.setInterfaz(this);
			clienteDialog.getClientes("");
			clienteDialog.setVisible(true);
			break;
		default:
			break;
		}
	}

	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			Double aPagar=itemTableModel.getEntities().stream().mapToDouble(i -> i.getCar_monto1()).sum();
			if (validateCabezera() && validateItems(itemTableModel.getEntities()) && aPagar > 0) {
				//Cobro clientes
					try {
						CobroCliente t = getFormValue();
						CobroCliente cc = cobroClienteService.save(t,itemTableModel.getEntities(),FormatearValor.stringADouble(tfMontoACobrar.getText()));
							if (cc != null) {
								//MovimientoCaja
								//Apertura de caja caso no este abierto
								lanzamientoCaja();
								//actualiza ingreso en movimiento Caja
								Optional<Caja> caja = cajaService.findById(1L);
								MovimientoCaja movCaja = new MovimientoCaja();
								movCaja.setCaja(caja.get());
								movCaja.setFecha(new Date());
								movCaja.setMoneda(new Moneda(1l));
								movCaja.setNotaNro(cc.getCclNumero().toString());
								movCaja.setNotaReferencia(cc.getCclNumero().toString());
								movCaja.setNotaValor(totalCalculado);
								movCaja.setPlanCuentaId(1);
								movCaja.setTipoOperacion("E");
								movCaja.setUsuario(GlobalVars.USER_ID);
								movCaja.setValorM01(totalCalculado);
								movCaja.setObs("Ingreso en caja 01 ");
								movCaja.setSituacion("PAGADO");
								pagoService.save(movCaja);
								
								Notifications.showAlert("Cobro a Cliente registrado con exito.!");
								newMov();
							}
								
					} catch (Exception e) {
						Notifications.showAlert("Ocurrió un error en Venta!, intente nuevamente");
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}else {
				Notifications.showAlert("Cargar valores a pagar!");
			}
		} else {
			tfEntidad.requestFocus();
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
	
	
	@Transactional
	private void eliminaCobroCliente() {
		try {
			
			Optional<CobroCliente> pOptional = cobroClienteService.findById(Integer.valueOf(this.getCobroClienteSeleccionado().getCclNumero().toString()));
			if (pOptional.isPresent()) {
				CobroCliente p = pOptional.get();
				List<ItemCobroCliente> listaItemCobro = itemCobroClienteService.findByIclNumero(Integer.valueOf(this.getCobroClienteSeleccionado().getCclNumero().toString()));
				
				cobroClienteService.remove(p, listaItemCobro, itemTableModel.getEntities());
			}
			
		} catch (Exception e) {
			Notifications.showAlert("Ocurrió un error al eliminar el registro de cobro del cliente, verifique documento!!");
		}

	}

	private boolean validateCabezera() {
		if (totalCalculado.intValue()==0) {
			Notifications.showAlert("Debe cargar por lo menos un item del cobro al cliente");
			return false;
		} 
		return true;
	}

	private boolean validateItems(List<DetalleCobroClienteView> items) {
		if (items.isEmpty()) {
			Notifications.showAlert("Ningun Item Ingreso encontrado !");
			return false;
		}
		for (DetalleCobroClienteView e : items) {
		}
		return true;
	}


	public void newMov() {
		clearForm();

		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		tfFecha.setText(sd.format(new Date()));
		// Optional<Usuario> usuario= usuarioService.findById(GlobalVars.USER_ID);
//		tfEntidad.setText(GlobalVars.USER_ID.toString());
//		tfNombreEntidad.setText(GlobalVars.USER);
		long max = cobroClienteService.getRowCount();
		tfNota.setText(String.valueOf(max + 1));
		lblSituacion.setText("NUEVO");
		btnGuardar.setVisible(true);
		btnAnula.setVisible(false);
		itemTableModel.clear();
		tfEntidad.requestFocus();
		habilitaEdicion();
	}

	@Override
	public void getEntity(CobroCliente cobroCliente) {
		try {
			if (cobroCliente != null) {
				setCobroClienteSeleccionado(cobroCliente);
				desabilitaEdicion();
				if (cobroCliente.getCclSituacion() == 0) {
					btnAnula.setVisible(true);
				} else {
					btnAnula.setVisible(false);
				}
				btnGuardar.setVisible(false);
				tfNota.setText(cobroCliente.getCclNumero().toString());
				lblSituacion.setText(cobroCliente.getCclSituacion() == 0 ? "VIGENTE" : "ELIMINADO");
				SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
				tfFecha.setText(sd.format(cobroCliente.getFecha()));
				tfDocumento.setText("" + cobroCliente.getCclDocumento());
				tfEntidad.setText(cobroCliente.getCclEntidad().toString());
				Optional<Cliente> cliente = null;
				cliente = clienteService.findById(Long.valueOf(cobroCliente.getCclEntidad()));
				if (cliente.isPresent()) {
					tfNombreEntidad.setText(cliente.get().getNombre());
				}
				itemTableModel.clear();
				List<Object[]> listaItemCobroCliente= cobroClienteService.findClienteCobradoView(Long.valueOf(cobroCliente.getCclEntidad()),cobroCliente.getCclNumero()); 
				List<DetalleCobroClienteView> listaCasteado = castDetalleCobroCliente(listaItemCobroCliente,1);
				itemTableModel.addEntities(listaCasteado);
				calculateItem();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void desabilitaEdicion() {
		tfEntidad.setEnabled(false);
		chkCobraTodos.setEnabled(false);
		lblMontoACobrar.setText("TOTAL COBRADO");
	}
	
	private void habilitaEdicion() {
		tfEntidad.setEnabled(true);
		chkCobraTodos.setEnabled(true);
		lblMontoACobrar.setText("MONTO A COBRAR");
	}

	@Override
	public void getEntity(Cliente cliente) {
		if (cliente != null) {
			tfEntidad.setText(cliente.getId().toString());
			tfNombreEntidad.setText(cliente.getNombre());
			itemTableModel.clear();
			List<Object[]> listMII = cobroClienteService.findDetalleCobroClienteView(cliente.getId());
			List<DetalleCobroClienteView> listaCasteado = castDetalleCobroCliente(listMII,0);
			itemTableModel.addEntities(listaCasteado);
			calculateItem();
		}

	}

	private void findEntidad(String id) {
		try {
			clearForm();
			Optional<Cliente> cliente = null;
			cliente = clienteService.findById(Long.valueOf(id));
			if (cliente.isPresent()) {
				tfNombreEntidad.setText(cliente.get().getNombre());
				itemTableModel.clear();
				List<Object[]> listMII = cobroClienteService.findDetalleCobroClienteView(cliente.get().getId());
				List<DetalleCobroClienteView> listaCasteado = castDetalleCobroCliente(listMII,0);
				itemTableModel.addEntities(listaCasteado);
				calculateItem();
				chkCobraTodos.requestFocus();
			} else {
				Notifications.showAlert("No existe cliente informado. Verifique por favor.!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void calculateItem() {
		totalCalculado = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCobro()).sum();
		tfSaldo.setText(FormatearValor.doubleAString(totalCalculado));
		Double pagado= itemTableModel.getEntities().stream().mapToDouble(e -> e.getPagado()).sum();
		Double aPagar=itemTableModel.getEntities().stream().mapToDouble(i -> i.getCar_monto1()).sum();
		Double diferencia =(aPagar-pagado);
		Double descuento=0d;
		Double recargo =0d;
		if (!tfDescuentos.getText().isEmpty()&&!tfDescuentos.getText().toString().equalsIgnoreCase("0"))
			descuento = FormatearValor.stringToDouble(tfDescuentos.getText());
		
		if (!tfRecargos.getText().isEmpty()&&!tfRecargos.getText().toString().equalsIgnoreCase("0"))
		    recargo = FormatearValor.stringToDouble(tfRecargos.getText());
		totalCalculado= totalCalculado - descuento +recargo;
		tfMontoACobrar.setText(FormatearValor.doubleAString(totalCalculado));
		//tfSaldo.setText(FormatearValor.doubleAString(pagado));
		tfTotalACobrar.setText(FormatearValor
				.doubleAString(diferencia));
	}

	private List<DetalleCobroClienteView> castDetalleCobroCliente(List<Object[]> listMII, int edicion) {
		List<DetalleCobroClienteView> lista = new ArrayList<DetalleCobroClienteView>();
		for (Object[] objects : listMII) {
			DetalleCobroClienteView det = new DetalleCobroClienteView();
			det.setCar_numero((Integer) objects[0]);
			det.setCar_fecha1((Date) objects[1]);
			det.setCar_boleta(objects[2].toString());
			// det.setCar_entidad((Long)objects[3]);
			det.setNombre(objects[4].toString());
			det.setCar_monto1((Double) objects[5]);
			det.setCar_proceso((Integer) objects[6]);
			det.setIca_vencimiento1((Date) objects[7]);
			det.setIca_monto1((Double) objects[8] - (Double) objects[10]);
			det.setIca_documento((String) objects[9]);
			det.setPagado((Double) objects[10]);
			det.setNombre_ingreso((String) objects[11]);
			if(edicion==0)
				det.setCobro(0d);
			else
				det.setCobro((Double) objects[13]);	
			det.setIca_Secuencia((Integer)objects[12]);

			lista.add(det);
		}

		return lista;

	}
	
	

	private void abandonarNota() {
		Integer respuesta = JOptionPane.showConfirmDialog(this, "ABANDONAR MOVIMIENTO.?", "AVISO",
				JOptionPane.OK_CANCEL_OPTION);

		if (respuesta == 0) {
			clearForm();
		} else {
			tfEntidad.requestFocus();
		}
	}

	public CobroCliente getCobroClienteSeleccionado() {
		return cobroClienteSeleccionado;
	}

	public void setCobroClienteSeleccionado(CobroCliente cobroClienteSeleccionado) {
		this.cobroClienteSeleccionado = cobroClienteSeleccionado;
	}

	public Double getTotalCalculado() {
		return totalCalculado;
	}

	public void setTotalCalculado(Double totalCalculado) {
		this.totalCalculado = totalCalculado;
	}

	public JLabel getLblMontoACobrar() {
		return lblMontoACobrar;
	}

	public void setLblMontoACobrar(JLabel lblMontoACobrar) {
		this.lblMontoACobrar = lblMontoACobrar;
	}
	
	
}
