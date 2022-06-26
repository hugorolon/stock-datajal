package py.com.prestosoftware.ui.transactions;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Devolucion;
import py.com.prestosoftware.data.models.DevolucionDetalle;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.DevolucionService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.domain.validations.DevolucionValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ClienteDialog;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.CompraDialog;
import py.com.prestosoftware.ui.search.CompraInterfaz;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.DevolucionDialog;
import py.com.prestosoftware.ui.search.DevolucionInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.ProveedorDialog;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.search.VendedorDialog;
import py.com.prestosoftware.ui.search.VendedorInterfaz;
import py.com.prestosoftware.ui.search.VentaDialog;
import py.com.prestosoftware.ui.search.VentaInterfaz;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.DevolucionTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class DevolucionPanel extends JFrame implements CompraInterfaz, VentaInterfaz, DevolucionInterfaz, 
	ProductoInterfaz, ClienteInterfaz, ProveedorInterfaz, VendedorInterfaz, DepositoInterfaz {

	private static final long serialVersionUID = 1L;

	private final int PRODUCTO_CODE = 1;
	private final int VENTA_CODE = 2;
	private final int COMPRA_CODE = 3;
	private final int CLIENTE_CODE = 4;
	private final int VENDEDOR_CODE = 5;
	private final int DEPOSITO_CODE = 6;
	private final int PROVEEDOR_CODE = 7;
	private final int DEVOLUCION_CODE = 8;
	
	private JLabel lblBuscadorDeVentas;
    private JTextField tfNotaReferencia, tfVendedor, tfDescripcion, tfDevId;
    private JTextField tfRefId, tfPrecioTotal, tfValor, tfVendedorID ;
    private JTextField tfCantidad;
    private JTextField tfDepositoID, tfDeposito;
    private JTextField tfProductoID;
    private JCheckBox chkDevolverTodos;
    private JButton btnGuardar, btnCancelar, btnCerrar, btnVer;
    private JTable tbProductos;
    private JLabel lblNotaNro;
    private JTextField tfNotaNro;
    private JLabel lblDeposito;
    private JLabel lblRef, lblVendedor;
    private String typeDevolucion = "";
    private JTextField tfTotalNota;
    private JLabel lblTotal;
    private JLabel lblObs;
    private JTextField tfObs;
    private JPanel panel;
    private JLabel lblCantItem;
    private JTextField tfCantItem;
    
    private DevolucionTableModel itemTableModel; 
    private ProductoDialog productoDialog;
    private VentaDialog ventaDialog;
    private CompraDialog compraDialog;
    private ClienteDialog clienteDialog;
    private ProveedorDialog proveedorDialog;
    private VendedorDialog vendedorDialog;
    private DevolucionDialog devolucionDialog;
    
    private DevolucionService devolucionService;
    private ProductoService productoService;
    private VentaService ventaService;
    private CompraService compraService;
    private ClienteService clienteService;
    private ProveedorService proveedorService;
    private UsuarioService usuarioService;
    private DepositoService depositoService;
    
    private DevolucionValidator devolucionValidator;
    private JLabel lblCantDev;
    private JTextField tfCantDev;
    private JTextField tfItemDevuelto;
    private JLabel lblItemDevuelto;
    private JTextField tfTotalDevolucion;
    private JLabel lblTotalDev;
    private JLabel lblItemSaldo;
    private JLabel lblSaldoDif;
    private JTextField tfSaldoItem;
    private JTextField tfDiferencia;

    @Autowired
    public DevolucionPanel(DevolucionTableModel itemTableModel, VentaDialog ventaDialog, CompraDialog compraDialog, 
    		ProductoDialog productoDialog, ClienteDialog clienteDialog, ClienteService clienteService, 
    		DevolucionValidator dValidator, DevolucionService devolucionService, VendedorDialog vendedorDialog,
    		ProductoService productoService, DepositoService depositoService, ProveedorDialog proveedorDialog,
    		UsuarioService usuarioService, VentaService ventaService, CompraService compraService, ProveedorService proveedorService, DevolucionDialog devolucionDialog) {
    	this.itemTableModel = itemTableModel;
    	this.ventaDialog = ventaDialog;
    	this.compraDialog = compraDialog;
    	this.productoDialog = productoDialog;
    	this.clienteService = clienteService;
    	this.usuarioService = usuarioService;
    	this.clienteDialog = clienteDialog;
    	this.proveedorDialog = proveedorDialog;
    	this.vendedorDialog = vendedorDialog;
    	this.devolucionValidator = dValidator;
    	this.devolucionService = devolucionService;
        this.productoService = productoService;
        this.depositoService = depositoService;
        this.ventaService = ventaService;
        this.compraService = compraService;
        this.proveedorService = proveedorService;
        this.devolucionDialog=devolucionDialog;
    	    	
    	setSize(914, 571);
    	setTitle("REGISTRO DE DEVOLUCIONES");
    	setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        habilitarItem(false);
    }

    private void initComponents() {
    	getContentPane().setLayout(null);
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    	tabbedPane.setBounds(9, 120, 885, 288);
        getContentPane().add(tabbedPane);
        
        JPanel pnlProducto = new JPanel();
        tabbedPane.addTab("ITEMS", null, pnlProducto, null);
        pnlProducto.setLayout(null);
        
        JLabel lblCodigo = new JLabel("COD");
        lblCodigo.setBounds(6, 10, 71, 30);
        pnlProducto.add(lblCodigo);
        
        JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
        lblDescripcion.setBounds(128, 10, 284, 30);
        pnlProducto.add(lblDescripcion);
        
        JLabel tfTotal = new JLabel("TOTAL");
        tfTotal.setBounds(532, 10, 133, 30);
        pnlProducto.add(tfTotal);
        
        JLabel lblPrecio = new JLabel("VALOR");
        lblPrecio.setBounds(413, 10, 133, 30);
        pnlProducto.add(lblPrecio);
        
        tfDescripcion = new JTextField();
        tfDescripcion.setEditable(false);
        tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        tfDescripcion.setColumns(10);
        tfDescripcion.setBounds(128, 39, 284, 30);
        pnlProducto.add(tfDescripcion);
        
        tfPrecioTotal = new JTextField();
        tfPrecioTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        tfPrecioTotal.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfPrecioTotal.setEditable(false);
        tfPrecioTotal.setFont(new Font("Arial", Font.PLAIN, 14));
        tfPrecioTotal.setColumns(10);
        tfPrecioTotal.setBounds(532, 39, 133, 30);
        pnlProducto.add(tfPrecioTotal);
        
        tfValor = new JTextField();
        tfValor.setEditable(false);
        tfValor.setHorizontalAlignment(SwingConstants.RIGHT);
        tfValor.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfValor.selectAll();
        	}
        });
        tfValor.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfValor.getText().isEmpty()) {
        				calculatePrecioTotal();
					} else {
						Notifications.showAlert("Digite el precio del Producto");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tfCantidad.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfProductoID.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfValor.setFont(new Font("Arial", Font.PLAIN, 14));
        tfValor.setColumns(10);
        tfValor.setBounds(413, 39, 116, 30);
        pnlProducto.add(tfValor);
        
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
        			//DEPOSITO
        			
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearForm();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProductoById(Long.parseLong(tfProductoID.getText()));
					} else {
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
        
        JScrollPane scrollProducto = new JScrollPane();
        scrollProducto.setBounds(6, 81, 852, 155);
        pnlProducto.add(scrollProducto);
        
        tbProductos = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				//if (columna == 5)
					//return true;
				return false;
			}
		};
        tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tbProductos.addKeyListener(new KeyAdapter() {
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
				itemTableModel.fireTableDataChanged();
			}
		});
		
		tbProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getItemSelected();
				calculateItem();
			}
		});
		
		tbProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {  
   			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
   				calculateItem();
			}  
        });  
		tbProductos.addFocusListener(new FocusListener() {
		    public void focusLost(FocusEvent arg0) {
		        calculateItem();    
		    }

		    public void focusGained(FocusEvent arg0) {
		        //tbProductos.selectAll();
		    }
		});

		
        
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
        		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
        			if (!tfCantidad.getText().isEmpty()) {
						tfValor.requestFocus();
					} else {
						Notifications.showAlert("Verifique la cantidad");
					}
        		} else if (e.getKeyCode()==KeyEvent.VK_F5) {
        			showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode()==KeyEvent.VK_F6) {
					//consulta de saldo stock
				} else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
					tfValor.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCantidad.setFont(new Font("Arial", Font.PLAIN, 14));
        tfCantidad.setColumns(10);
        tfCantidad.setBounds(77, 39, 48, 30);
        pnlProducto.add(tfCantidad);
        
        JLabel lblCantidad = new JLabel("CANT.");
        lblCantidad.setBounds(77, 10, 86, 30);
        pnlProducto.add(lblCantidad);
        
        lblCantDev = new JLabel("CANT. DEV.");
        lblCantDev.setBounds(675, 19, 56, 13);
        pnlProducto.add(lblCantDev);
        
        tfCantDev = new JTextField();
        tfCantDev.setHorizontalAlignment(SwingConstants.RIGHT);
        tfCantDev.setFont(new Font("Arial", Font.PLAIN, 14));
        tfCantDev.setBounds(672, 39, 61, 30);
        tfCantDev.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearForm();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCantDev.getText().isEmpty()) {
						addItem();
						calculateItem();
					} 
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        pnlProducto.add(tfCantDev);
        tfCantDev.setColumns(10);
        
        chkDevolverTodos = new JCheckBox("Devolver Todos");
        chkDevolverTodos.setBounds(741, 44, 117, 21);
        chkDevolverTodos.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chkDevolverTodos.isSelected()) {
					cargaSaldo();
					calculateItem();
					itemTableModel.fireTableDataChanged();
					
				} else {
					descargaSaldo();
					calculateItem();
					itemTableModel.fireTableDataChanged();
				}

			}

			private void cargaSaldo() {
				for (DevolucionDetalle ie : itemTableModel.getEntities()) {
					ie.setCantidaddev(ie.getCantidad());
				}
			}
			private void descargaSaldo() {
				for (DevolucionDetalle ie : itemTableModel.getEntities()) {
					ie.setCantidaddev(0d);
				}
			}
		});
        pnlProducto.add(chkDevolverTodos);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBounds(9, 12, 885, 105);
        panel_3.setBorder(new TitledBorder(null, "SELECCIONE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(panel_3);
        panel_3.setLayout(null);
        
        JPanel pnlCliente = new JPanel();
        pnlCliente.setBounds(6, 18, 873, 79);
        panel_3.add(pnlCliente);
        pnlCliente.setLayout(null);
        
        lblRef = new JLabel("CLIENTE:");
        lblRef.setBounds(413, 6, 70, 30);
        pnlCliente.add(lblRef);
        
        tfRefId = new JTextField();
        tfRefId.setEditable(false);
        tfRefId.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfRefId.selectAll();
        	}
        });
        tfRefId.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
        			if (getTypeDevolucion().equals("VENTA")) {
        				showDialog(CLIENTE_CODE);
					} else {
						showDialog(PROVEEDOR_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfRefId.getText().isEmpty() && !tfRefId.getText().equals("0")) {
						findRefById(Long.valueOf(tfRefId.getText()));
						tfVendedorID.requestFocus();
					} else {
						tfRefId.setText("0");
						//cbCondicion.setEditable(false);
						//cbCondicion.setEnabled(false);
						tfVendedorID.requestFocus();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					if (getTypeDevolucion().equals("VENTA")) {
        				showDialog(CLIENTE_CODE);
					} else {
						showDialog(VENDEDOR_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearForm();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfRefId.setBounds(481, 6, 61, 30);
        pnlCliente.add(tfRefId);
        tfRefId.setColumns(10);
        
        tfNotaReferencia = new JTextField();
        tfNotaReferencia.setEditable(false);
        tfNotaReferencia.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					tfClienteDireccion.requestFocus();
				}
        	}
        });
        tfNotaReferencia.setBounds(550, 6, 317, 30);
        pnlCliente.add(tfNotaReferencia);
        tfNotaReferencia.setColumns(10);
        
        lblVendedor = new JLabel("VEND.:");
        lblVendedor.setBounds(413, 43, 70, 30);
        pnlCliente.add(lblVendedor);
        
        tfVendedorID = new JTextField();
        tfVendedorID.setEditable(false);
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
						habilitarItem(true);
					} else {
						Notifications.showAlert("Este campo es obligatorio.!");
					}
					
					//habilitarCondicion(false);
				} 
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfVendedorID.setBounds(481, 42, 61, 30);
        pnlCliente.add(tfVendedorID);
        tfVendedorID.setColumns(10);
        
        tfVendedor = new JTextField();
        tfVendedor.setEditable(false);
        tfVendedor.setBounds(550, 42, 317, 30);
        pnlCliente.add(tfVendedor);
        tfVendedor.setToolTipText("");
        tfVendedor.setColumns(10);
        
        lblBuscadorDeVentas = new JLabel("NOTA:");
        lblBuscadorDeVentas.setBounds(10, 6, 91, 30);
        pnlCliente.add(lblBuscadorDeVentas);
        
        tfDevId = new JTextField();
        tfDevId.setEditable(false);
        tfDevId.setBounds(110, 6, 175, 30);
        pnlCliente.add(tfDevId);
        tfDevId.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
					//showDialog(code);
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfDevId.setColumns(10);
        
        lblNotaNro = new JLabel("VENTA NRO:");
        lblNotaNro.setBounds(10, 43, 91, 30);
        pnlCliente.add(lblNotaNro);
        
        tfNotaNro = new JTextField();
        tfNotaNro.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfNotaNro.getText().isEmpty() && !tfNotaNro.getText().equals("0")) {
						Long notaId = Long.valueOf(tfNotaNro.getText());
						clearForm();
						habilitarCabezera(false);
						habilitarItem(false);
						findNotaById(notaId);
					} else {
						tfNotaNro.setText("0");
						tfNotaNro.setEnabled(false);
						tfRefId.requestFocus();
						habilitarCabezera(true);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F4) {
					if (getTypeDevolucion().equals("VENTA")) {
						showDialog(VENTA_CODE);
					} else {
						showDialog(COMPRA_CODE);
					}	
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfNotaNro.setColumns(10);
        tfNotaNro.setBounds(110, 44, 175, 30);
        pnlCliente.add(tfNotaNro);
        
        btnVer = new JButton("...");
        btnVer.setBounds(311, 11, 30, 21);
        btnVer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showDialog(DEVOLUCION_CODE);
				}
			}
		});
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(DEVOLUCION_CODE);
			}
		});
        pnlCliente.add(btnVer);
        
        JPanel pnlBotonera = new JPanel();
        pnlBotonera.setBounds(9, 487, 885, 35);
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
					newDevolucion();
				}
        	}
        });
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearForm();
        		newDevolucion();
        	}
        });
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
        
        panel = new JPanel();
        panel.setBounds(9, 412, 885, 73);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        lblObs = new JLabel("OBS.:");
        lblObs.setBounds(6, 7, 41, 30);
        panel.add(lblObs);
        
        tfObs = new JTextField();
        ((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfObs.setBounds(75, 6, 146, 30);
        panel.add(tfObs);
        tfObs.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        tfObs.setColumns(10);
        
        lblTotal = new JLabel("TOTAL:");
        lblTotal.setBounds(252, 38, 69, 30);
        panel.add(lblTotal);
        
        tfTotalNota = new JTextField();
        tfTotalNota.setBounds(349, 38, 94, 30);
        panel.add(tfTotalNota);
        tfTotalNota.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        tfTotalNota.setHorizontalAlignment(SwingConstants.RIGHT);
        tfTotalNota.setEditable(false);
        tfTotalNota.setColumns(10);
        
        lblDeposito = new JLabel("DEP.:");
        lblDeposito.setBounds(6, 37, 57, 30);
        panel.add(lblDeposito);
        
        tfDepositoID = new JTextField();
        tfDepositoID.setBounds(75, 37, 35, 30);
        panel.add(tfDepositoID);
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
						tfObs.requestFocus();
					} else {
						Notifications.showAlert("");
					}
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfDepositoID.setColumns(10);
        
        tfDeposito = new JTextField();
        tfDeposito.setBounds(113, 37, 110, 30);
        panel.add(tfDeposito);
        tfDeposito.setEditable(false);
        tfDeposito.setToolTipText("");
        tfDeposito.setColumns(10);
        
        lblCantItem = new JLabel("CANTIDAD ITEM");
        lblCantItem.setBounds(252, 7, 87, 30);
        panel.add(lblCantItem);
        
        tfCantItem = new JTextField();
        tfCantItem.setHorizontalAlignment(SwingConstants.RIGHT);
        tfCantItem.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        tfCantItem.setEditable(false);
        tfCantItem.setColumns(10);
        tfCantItem.setBounds(349, 7, 94, 30);
        panel.add(tfCantItem);
        
        tfItemDevuelto = new JTextField();
        tfItemDevuelto.setHorizontalAlignment(SwingConstants.RIGHT);
        tfItemDevuelto.setFont(new Font("Dialog", Font.BOLD, 14));
        tfItemDevuelto.setEditable(false);
        tfItemDevuelto.setBounds(573, 8, 96, 30);
        panel.add(tfItemDevuelto);
        tfItemDevuelto.setColumns(10);
        
        lblItemDevuelto = new JLabel("CANTIDAD DEVUELTO");
        lblItemDevuelto.setBounds(453, 17, 110, 13);
        panel.add(lblItemDevuelto);
        
        tfTotalDevolucion = new JTextField();
        tfTotalDevolucion.setHorizontalAlignment(SwingConstants.RIGHT);
        tfTotalDevolucion.setFont(new Font("Dialog", Font.BOLD, 14));
        tfTotalDevolucion.setEditable(false);
        tfTotalDevolucion.setBounds(573, 39, 96, 30);
        panel.add(tfTotalDevolucion);
        tfTotalDevolucion.setColumns(10);
        
        lblTotalDev = new JLabel("TOTAL DEVUELTO");
        lblTotalDev.setBounds(453, 48, 116, 13);
        panel.add(lblTotalDev);
        
        lblItemSaldo = new JLabel("ITEM SALDO");
        lblItemSaldo.setBounds(679, 15, 66, 13);
        panel.add(lblItemSaldo);
        
        lblSaldoDif = new JLabel("DIFERENCIA");
        lblSaldoDif.setBounds(679, 46, 66, 13);
        panel.add(lblSaldoDif);
        
        tfSaldoItem = new JTextField();
        tfSaldoItem.setHorizontalAlignment(SwingConstants.RIGHT);
        tfSaldoItem.setFont(new Font("Dialog", Font.BOLD, 14));
        tfSaldoItem.setEditable(false);
        tfSaldoItem.setBounds(755, 6, 120, 30);
        panel.add(tfSaldoItem);
        tfSaldoItem.setColumns(10);
        
        tfDiferencia = new JTextField();
        tfDiferencia.setFont(new Font("Dialog", Font.BOLD, 14));
        tfDiferencia.setHorizontalAlignment(SwingConstants.RIGHT);
        tfDiferencia.setEditable(false);
        tfDiferencia.setBounds(755, 37, 120, 30);
        panel.add(tfDiferencia);
        tfDiferencia.setColumns(10);
    }
    
    private void findRefById(Long refId) {
    	if (getTypeDevolucion().equals("VENTA")) {
    		Optional<Cliente> c = clienteService.findById(refId);
        	
        	if (c.isPresent()) {
    			tfNotaReferencia.setText(c.get().getNombre());
    		}
		} else {
			Optional<Proveedor> p = proveedorService.findById(refId);
			
			if (p.isPresent()) {
				tfNotaReferencia.setText(p.get().getNombre());
			}
		}	
    }
    
    private void findVendedorById(Long id) {
    	Optional<Usuario> u = usuarioService.findById(id);
    	
    	if (u.isPresent()) {
			tfVendedor.setText(u.get().getUsuario());
			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("El codigo del Vendedor no existe.!");
		}
    }
    
    private void getItemSelected() {		
		int selectedRow = tbProductos.getSelectedRow();
        
        if (selectedRow != -1) {
        	DevolucionDetalle item = itemTableModel.getEntityByRow(selectedRow);
        	
        	tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfValor.setText(FormatearValor.doubleAString(item.getCosto()));
			tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
			tfCantDev.setText(FormatearValor.doubleAString(item.getCantidaddev()));
        }
    }
    
//    private void setTableSize() {
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
    
//    private void habilitarCondicion(boolean isEditable) {
//    	cbCondicion.setEditable(false);
//    }
    
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
		} else if (tfValor.getText().isEmpty()) {
			Notifications.showAlert("Digite el precio");
			tfValor.requestFocus();
			return false;
		} else if (!tfValor.getText().isEmpty() && FormatearValor.stringADouble(tfValor.getText()) <= 0) {
			Notifications.showAlert("El precio debe ser mayor a cero");
			tfValor.requestFocus();
			return false;
		}
		
		return true;
	}
    
    private void findDepositoById(Long id) {
    	Optional<Deposito> d = depositoService.findById(id);
    	
    	if (d.isPresent()) {
			tfDeposito.setText(d.get().getNombre());
		}
    }

    private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
    	itemTableModel.removeRow(fila);
    	
    	Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());   	
    	Double precio = FormatearValor.stringADouble(tfValor.getText()); 
    	
    	tfCantidad.setText(FormatearValor.doubleAString(cantidad));
    	tfPrecioTotal.setText(FormatearValor.doubleAString(cantidad * precio));
    	
		addItemToList();
    	clearItem();
	}
    
    private void setDeposito(Deposito d) {
    	tfDepositoID.setText(String.valueOf(d.getId()));
		tfDeposito.setText(d.getNombre());
    }
    
    private void setProducto(Optional<Producto> producto) {
    	if (producto.isPresent()) {
    		String nombre = producto.get().getDescripcion();
    		tfDescripcion.setText(nombre);
    		tfValor.setText(producto.get().getPrecioVentaA() != null ? 
    				FormatearValor.doubleAString(producto.get().getPrecioVentaA()) : "0" );
    		//cantidad default
    		tfCantidad.setText("1");
    		tfCantidad.requestFocus();
    	}
    }
    
    private void findNotaById(Long id) {
    	String situacion = "PENDIENTE";
    	if (getTypeDevolucion().equals("VENTA")) {
			Optional<Venta> venta = ventaService.findById(id);
			
			if (venta.isPresent()) {
				if(venta.get().getSituacion().equalsIgnoreCase("ANULADO")) {
					Notifications.showAlert("Boleta anulado no puede ser devuelto!");
				}else {
					setDataVenta(venta.get());
				}
			} else {
				Notifications.showAlert("No existe Nro. de Venta con este codigo.!");
			}
		} else { // COMPRA
			Optional<Compra> compra = compraService.findById(id);
			
			if (compra.isPresent()) {
				if(compra.get().getSituacion().equalsIgnoreCase("ANULADO")) {
					Notifications.showAlert("Boleta anulado no puede ser devuelto!");
				}else {
					setDataCompra(compra.get());
				}
			} else {
				Notifications.showAlert("No existe Nro. de Compra con este codigo.!");
			}
		}
    }
    
    private void habilitarItem(Boolean isEditable) {
    	tfProductoID.setEditable(isEditable);
    	tfCantidad.setEditable(isEditable);
    }
    
    private void habilitarCabezera(Boolean isEditable) {
    	tfRefId.setEditable(isEditable);
    	tfVendedorID.setEditable(isEditable);
    }
    
    private void habilitarInputs(boolean esDevolucionVenta) {
    	if (esDevolucionVenta) {		
//			lblVendedor.setVisible(true);
//			tfVendedorID.setVisible(true);
//			tfVendedor.setVisible(true);
			
			lblNotaNro.setText("VENTA NRO.:");
			lblRef.setText("CLI.");
		} else {
//			lblDeposito.setVisible(true);
//    		tfDepositoID.setVisible(true);
//			tfDeposito.setVisible(true);
			
			lblVendedor.setText("USUARIO");
			//tfVendedorID.setVisible(false);
			//tfVendedor.setVisible(false);
			
			lblNotaNro.setText("COMPRA NRO.:");
			lblRef.setText("PROV.");
		}
    }
    
    private void setDataVenta(Venta v) {
    	tfNotaNro.setText(String.valueOf(v.getId()));
    	tfCantItem.setText(String.valueOf(v.getCantItem()));
    	tfTotalNota.setText(FormatearValor.doubleAString(v.getTotalGeneral()));
    	
    	setVendedor(v.getVendedor());
    	setCliente(v.getCliente());
    	setDeposito(v.getDeposito());
		List<VentaDetalle> listaDetalles = new ArrayList<VentaDetalle>();
		List<Object[]> listaItems = ventaService.retriveVentaDetalleByIdVenta(v.getId());
		// venta_id, cantidad, precio, producto, producto_id, subtotal, id, iva
		Double cantItem = 0d;
		Double total = 0d;
		for (Object[] object : listaItems) {
			VentaDetalle det = new VentaDetalle();
			det.setCantidad(Double.valueOf(object[1].toString()));
			det.setPrecio(Double.valueOf(object[2].toString()));
			if(object[3]==null) {
				Producto p=  findProductoByIdReturn(Long.valueOf(object[4].toString()));
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
			
			DevolucionDetalle de = new DevolucionDetalle();
    		de.setProductoId(det.getProductoId());
    		de.setProducto(det.getProducto());
    		de.setCantidad(det.getCantidad());
    		de.setCosto(det.getPrecio());
    		de.setSubtotal(det.getSubtotal());
    		de.setCantidaddev(0d);
    		
    		itemTableModel.addEntity(de);
		}
    	
    }
    
    private void setCliente(Cliente c) {
    	tfRefId.setText(String.valueOf(c.getId()));
    	tfNotaReferencia.setText(c.getNombre());
    }
    
    private void setVendedor(Usuario u) {
    	tfVendedorID.setText(String.valueOf(u.getId()));
    	tfVendedor.setText(u.getUsuario());
    }
    
    private void setUsuario(Usuario u) {
    	tfVendedorID.setText(String.valueOf(u.getId()));
    	tfVendedor.setText(u.getUsuario());
    }
    
    private void setProveedor(Proveedor p) {
    	tfRefId.setText(String.valueOf(p.getId()));
    	tfNotaReferencia.setText(p.getNombre());
    }
    
    private void setDataCompra(Compra c) {
    	tfNotaNro.setText(String.valueOf(c.getId()));
    	tfTotalNota.setText(FormatearValor.doubleAString(c.getTotalGeneral()));
    	tfCantItem.setText(String.valueOf(c.getTotalItem()));
    
    	setDeposito(c.getDeposito());
    	setProveedor(c.getProveedor());
    	setUsuario(c.getUsuario());
    
    	if (!c.getItems().isEmpty()) {
    		for (CompraDetalle e : c.getItems()) {
        		DevolucionDetalle de = new DevolucionDetalle();
        		de.setProductoId(e.getProductoId());
        		de.setProducto(e.getProducto());
        		de.setCantidad(e.getCantidad());
        		de.setCosto(e.getPrecio());
        		de.setSubtotal(e.getSubtotal());
        		de.setCantidaddev(0d);
        		itemTableModel.addEntity(de);				
			}
		}	
    }
    
    public void setTypeDevolucion(String typeDevolucion) {
		this.typeDevolucion = typeDevolucion;
	}
    
    public String getTypeDevolucion() {
		return typeDevolucion;
	}
    
    public void updateView() {
    	if (getTypeDevolucion().equals("VENTA")) {
    		setTitle("DEVOLUCIONES DE VENTAS");
    		habilitarInputs(true);
		} else {
			setTitle("DEVOLUCIONES DE COMPRAS");
			habilitarInputs(false);
		}
    }
    
    private DevolucionDetalle getItem() {
    	DevolucionDetalle item = new DevolucionDetalle();
    	item.setProductoId(Long.valueOf(tfProductoID.getText()));
    	item.setProducto(tfDescripcion.getText());
    	item.setCantidad(FormatearValor.stringToDouble(tfCantidad.getText()));
    	item.setCosto(FormatearValor.stringToDouble(tfValor.getText()));
    	item.setSubtotal(FormatearValor.stringToDouble(tfPrecioTotal.getText()));
    	item.setCantidaddev(FormatearValor.stringToDouble(tfCantDev.getText()));
    	return item;
    }
    
    private void clearItem() {
    	tfProductoID.setText("");
    	tfDescripcion.setText("");
    	tfCantidad.setText("");
    	tfValor.setText("");
    	tfPrecioTotal.setText("");
		tfProductoID.requestFocus();
		tfCantDev.setText("");
    }

    private void clearForm() {
    	tfRefId.setText("");
        tfNotaReferencia.setText("");
        tfVendedorID.setText("");
    	tfVendedor.setText("");
    	tfDepositoID.setText("");
    	tfDeposito.setText("");
    	tfProductoID.setText("");
    	tfCantidad.setText("");
    	tfDescripcion.setText("");
    	tfValor.setText("");
    	tfPrecioTotal.setText("");
    	tfNotaNro.setText("");
    	tfObs.setText("");
    	tfTotalNota.setText("");
    	tfCantItem.setText("");
    	tfCantDev.setText("");
    	chkDevolverTodos.setSelected(false);
    	tfItemDevuelto.setText("");
    	tfTotalDevolucion.setText("");
    	tfSaldoItem.setText("");
    	tfDiferencia.setText("");
    	habilitarEdicion();
    	while (itemTableModel.getRowCount() > 0) {
    		itemTableModel.removeRow(0);
		}
    }
    
    public JTextField getTfClienteID() {
		return tfRefId;
	}
    
    public JTextField getTfVentaId() {
		return tfDevId;
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
		return tfValor;
	}
    
    public JTextField getTfDepositoID() {
		return tfDepositoID;
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
    	Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
    	Double precioUnit = FormatearValor.stringToDouble(tfValor.getText());
    	Double precioTotal = cantidad * precioUnit;
    	
    	tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));
    }
    
    private void showDialog(int code) {
    	switch (code) {
			case VENTA_CODE:
				ventaDialog.setInterfaz(this);
				ventaDialog.setVisible(true);	
				break;
			case COMPRA_CODE:
				compraDialog.setInterfaz(this);
				compraDialog.setVisible(true);
				break;
			case PRODUCTO_CODE:
				productoDialog.setInterfaz(this);
				productoDialog.setVisible(true);
				break;
			case CLIENTE_CODE:
				clienteDialog.setInterfaz(this);
				clienteDialog.setVisible(true);
				break;
			case DEPOSITO_CODE:
				clienteDialog.setInterfaz(this);
				clienteDialog.setVisible(true);
				break;
			case VENDEDOR_CODE:
				vendedorDialog.setInterfaz(this);
				vendedorDialog.setVisible(true);
				break;
			case PROVEEDOR_CODE:
				proveedorDialog.setInterfaz(this);
				proveedorDialog.setVisible(true);
				break;
			case DEVOLUCION_CODE:
				devolucionDialog.setInterfaz(this);
				devolucionDialog.setVisible(true);
				break;	
			default:
				break;
		}
    }

	@Override
	public void getEntity(Compra c) {
		if (c != null) {
			findNotaById(c.getId());
		}
	}
	
	@Override
	public void getEntity(Venta v) {
		if (v != null) {
			findNotaById(v.getId());
		}
	}

	@Override
	public void getEntity(Producto producto) {
		if (producto != null) {
			Double precioUnit = producto.getPrecioVentaA() != null ? producto.getPrecioVentaA() : 0d;
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfValor.setText(FormatearValor.doubleAString(precioUnit));
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();
		}
	}
	
	private void updateStockProduct(List<DevolucionDetalle> items) {
		List<Producto> productos = new ArrayList<>();
    	
    	for (DevolucionDetalle e : items) {
    		Optional<Producto> pOptional = productoService.findById(e.getProductoId());
    		
    		if (pOptional.isPresent()) {
    			Producto p = pOptional.get();
    			Double stock = p.getDepO1() != null ? p.getDepO1():0;
    			Double newQty = 0d;
    			
    			newQty = getTypeDevolucion().equals("COMPRA") ? stock - e.getCantidaddev():stock + e.getCantidaddev();
    			
				p.setDepO1(newQty);
				productos.add(p);
    		}				
		}
    	
    	productoService.updateStock(productos);
	}
	
	private void updateTransacction() {
		String situacion = "";
		if(tfTotalNota.getText().equalsIgnoreCase(tfTotalDevolucion.getText())) {
			situacion = "DEVOLUCION";
		}
		
		if (getTypeDevolucion().equals("COMPRA")) { 
			Optional<Compra> c = compraService.findById(Long.valueOf(tfNotaNro.getText()));
			
			if (c.isPresent()&&situacion.equalsIgnoreCase("DEVOLUCION")) {
				Compra compra = c.get();
				compra.setSituacion(situacion);
				compraService.save(compra);
			}
		} else { //venta
			Optional<Venta> v = ventaService.findById(Long.valueOf(tfNotaNro.getText()));
			
			if (v.isPresent()&&situacion.equalsIgnoreCase("DEVOLUCION")) {
				Venta venta = v.get();
				venta.setSituacion(situacion);
				ventaService.save(venta);
			}
		}
	}
	
	private void save() {
		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (validateCabezera() && validateItems(itemTableModel.getEntities())) {
				
				Devolucion d = new Devolucion();
		    	d.setRefId(Long.valueOf(tfRefId.getText()));
		    	d.setReferencia(tfNotaReferencia.getText());
		    	d.setTipo(getTypeDevolucion());
		    	d.setDeposito(new Deposito(Long.valueOf(tfDepositoID.getText())));
		    	d.setVendedor(tfVendedorID.getText().isEmpty() ? new Usuario(1L):new Usuario(Long.valueOf(tfVendedorID.getText())));
		    	d.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID));
		    	//d.setCredito((String)cbCondicion.getSelectedItem());
		    	d.setComprobante(tfNotaNro.getText());
		    	d.setFecha(new Date());
		    	d.setVencimiento(new Date());
		    	d.setSituacion("PENDIENTE");
		    	d.setObs(tfObs.getText());
		    	d.setCantItem(FormatearValor.stringADoubleFormat(tfCantItem.getText()==null?"0":tfCantItem.getText()));
		    	d.setTotalGeneral(FormatearValor.stringADoubleFormat(tfTotalNota.getText()));
		    	//int cantItem = itemTableModel.getEntities().
		    	d.setItems(itemTableModel.getEntities());
				
		        Optional<ValidationError> errors = devolucionValidator.validate(d);
		        
		        if (errors.isPresent()) {
		            ValidationError validationError = errors.get();
		            Notifications.showFormValidationAlert(validationError.getMessage());
		        } else {
		        	Devolucion dev = devolucionService.save(d);
			            
			        if (dev != null) {
			            updateStockProduct(dev.getItems());
			            
			            updateTransacction();
		        	}
			        
		        	clearForm();
		        	newDevolucion();
			        Notifications.showAlert("Devolución de " + getTypeDevolucion() + " registrado con exito.!");
		        }  
			}
		} else {
			tfProductoID.requestFocus();
		}

    }
    
    private boolean validateCabezera() {
    	if (tfRefId.getText().isEmpty()) {
    		Notifications.showAlert("El código de Referencia es obligatorio");
    		tfRefId.requestFocus();
    		return false;
		}
    	
    	if (tfDepositoID.getText().isEmpty()) {
    		Notifications.showAlert("El código del Deposito es obligatorio");
    		tfDepositoID.requestFocus();
    		return false;
		}
    	
    	if (tfVendedorID.getText().isEmpty()) {
    		Notifications.showAlert("El código del Vendedor/Usuario es obligatorio");
    		tfVendedorID.requestFocus();
    		return false;
		}
    	
    	return true;
    }
    
    private boolean validateItems(List<DevolucionDetalle> items) { 
    	String message = "Insuficiente Stock para el/los Item: \n";
    	boolean sinStock = false;
    	if (getTypeDevolucion().equals("COMPRA")) {
    		//SALIDA STOCK
    		for (DevolucionDetalle ditem : items) {
    				Optional<Producto> producto = productoService.findById(ditem.getProductoId());
	    		
	    		if (producto.isPresent()) {
	    			Double stock = producto.get().getDepO1() != null ? producto.get().getDepO1():0;
    			
	    			if (stock < ditem.getCantidaddev()) {	
	    				sinStock = true;
	    				message = message + producto.get().getDescripcion() + " -> stock: " + producto.get().getStock() + " \n";
					}
				}
			}
    	}else {
    		Double cantDevolver =  items.stream().mapToDouble(i -> i.getCantidaddev()).sum();
    		if(cantDevolver==0) {
    			sinStock=true;
    			message="Debe cargar un item de devolución por lo menos!";
    		}
    	}
    	
    	if (sinStock) {
    		Notifications.showAlert(message);
    		return false;
		} else {
			return true;
		}
    }
    
    public void newDevolucion() {
		long max = devolucionService.getRowCount();
		tfDevId.setText(String.valueOf(max + 1));
		tfNotaNro.requestFocus();
	}
    
    private void findProductoById(Long id) {
    	Optional<Producto> producto = productoService.findById(id);
    	
    	if (producto.isPresent()) {
    		setProducto(producto);
		}
    }
    
    private Producto findProductoByIdReturn(Long id) {
    	Optional<Producto> producto = productoService.findById(id);
    	
    	if (producto.isPresent()) {
    		return producto.get();
		}else {
			return null;
		}
    }
    
    private void addItem() {
    	Integer fila = -1;
		for (Integer i = 0; i < tbProductos.getRowCount(); i++) {
			String itemId = String.valueOf(tbProductos.getValueAt(i, 0));
			if (tfProductoID.getText().trim().equals(itemId) && !itemId.equals("")) {
				fila = i;
			}
		}
		if (isValidItem()) {
			actualizarRegristroGrilla(fila, String.valueOf(tbProductos.getValueAt(fila, 1)));
			tfProductoID.requestFocus();
		}else {
			tfProductoID.requestFocus();
		}
		
		/*if (getTypeDevolucion().equals("COMPRA")) { //cuando es compra verificamos si existe cantidad a devolver al proveedor
			Long productoId = Long.valueOf(tfProductoID.getText());
	    	Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
	    	
			Optional<Producto> p = productoService.findById(productoId);
	    	
	    	if (p.isPresent()) {
				Double stock = p.get().getDepO1() != null ? p.get().getDepO1() : 0;
			
				if (cantidad <= stock) {
					addItemToList();
					calculateItem();
				} else {
					tfProductoID.requestFocus();
					Notifications.showAlert("No tiene suficiente Stock para este Producto. Stock actual es de: " + FormatearValor.doubleAString(stock));
				}
	    	} else {
	    		Notifications.showAlert("No existe producto con este codigo.!");
	    	}
		} */
//		else {
//			addItemToList();
//			calculateItem();
//		}	
		calculateItem();
		clearItem();
    }
    
    private void calculateItem() {
    	Double cantItemDevuelto = 0d;//itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidaddev()).sum();
    	Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
    	Double subTotalDev=0d;
    	for (DevolucionDetalle devolucionDetalle : itemTableModel.getEntities()) {
			if(devolucionDetalle.getCantidaddev()!=null && devolucionDetalle.getCantidaddev()>0) {
				subTotalDev+=(devolucionDetalle.getCantidaddev()*devolucionDetalle.getCosto());
				cantItemDevuelto+=devolucionDetalle.getCantidaddev();
			}
		}	
		
		Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotal()).sum();
		setTotals(cantItem, cantItemDevuelto, total, subTotalDev);
	}
    
    private void setTotals(Double cantItem, Double cantidadDevuelto, Double total, Double subTotalDev) {
		tfTotalNota.setText(FormatearValor.doubleAString(total));
		tfDiferencia.setText("0");
		tfTotalDevolucion.setText("0");

		if (cantItem != null) {
			tfCantItem.setText(FormatearValor.doubleAString(cantItem));
		}
		if (cantidadDevuelto != null) {
			tfItemDevuelto.setText(FormatearValor.doubleAString(cantidadDevuelto));
		}
		if (cantItem != 0 && cantidadDevuelto != 0) {
			tfSaldoItem.setText(FormatearValor.doubleAString(cantItem-cantidadDevuelto));
			tfTotalDevolucion.setText(FormatearValor.doubleAString(subTotalDev));
			tfDiferencia.setText(FormatearValor.doubleAString(total-subTotalDev));
		}
	}
    
    
    public JCheckBox getChkDevolverTodos() {
		return chkDevolverTodos;
	}

	public void setChkDevolverTodos(JCheckBox chkDevolverTodos) {
		this.chkDevolverTodos = chkDevolverTodos;
	}

	private void addItemToList() {
    	itemTableModel.addEntity(getItem());
    }

	@Override
	public void getEntity(Usuario usuario) {
		if (usuario != null) {
			//tfVendedorID.setEditable(false);
			tfVendedorID.setText(String.valueOf(usuario.getId()));
			tfVendedor.setText(usuario.getUsuario());
			tfProductoID.requestFocus();
		}
	}

	@Override
	public void getEntity(Cliente c) {
		if (c != null) {
			//tfRefId.setEditable(false);
			tfRefId.setText(String.valueOf(c.getId()));
			tfNotaReferencia.setText(c.getNombre());
			tfProductoID.requestFocus();
		}
	}

	@Override
	public void getEntity(Deposito deposito) {
		if (deposito != null) {
			tfDepositoID.setText(String.valueOf(deposito.getId()));
			tfDeposito.setText(deposito.getNombre());
			tfObs.requestFocus();
		}
	}

	@Override
	public void getEntity(Proveedor p) {
		if (p != null) {
			tfRefId.setText(String.valueOf(p.getId()));
			tfNotaReferencia.setText(p.getNombre());
		}
	}

	@Override
	public void getEntity(Devolucion v) {
		clearForm();
		if(v!=null) {
			tfDevId.setText(v.getId().toString());
			tfRefId.setText(v.getRefId().toString());
			tfNotaReferencia.setText(v.getReferencia());
			tfNotaNro.setText(v.getComprobante());
			tfVendedorID.setText(v.getVendedor().getId().toString());
			tfVendedor.setText(v.getVendedor().getUsuario());
			tfDepositoID.setText(v.getDeposito().getId().toString());
			tfDeposito.setText(v.getDeposito().getNombre());
			
			List<Object[]> listaItems = devolucionService.getDetallesDevolucion(v.getId());
			Double cantItem = 0d;
			Double total = 0d;
			for (Object[] object : listaItems) {
				DevolucionDetalle de = new DevolucionDetalle();
	    		de.setProductoId(Long.valueOf(object[0].toString()));
	    		de.setProducto(object[1].toString());
	    		de.setCantidad(Double.valueOf(object[2].toString()));
	    		de.setCosto(Double.valueOf(object[3].toString()));
	    		de.setSubtotal(Double.valueOf(object[4].toString()));
	    		if (object[5]!=null)
	    			de.setCantidaddev(Double.valueOf(object[5].toString()));
	    		else
	    			de.setCantidaddev(0d);
	    		cantItem+=de.getCantidad();
				total+=de.getSubtotal();
	    		itemTableModel.addEntity(de);
			}
			calculateItem();
			inhabilitarEdicion();
		}
		
	}

	private void inhabilitarEdicion() {
		btnGuardar.setVisible(false);
		tfCantDev.setEditable(false);
		chkDevolverTodos.setEnabled(false);
		tfNotaNro.setEditable(false);
		tfRefId.setEditable(false);
	}
	
	private void habilitarEdicion() {
		btnGuardar.setVisible(true);
		tfCantDev.setEditable(true);
		chkDevolverTodos.setEnabled(true);
		tfNotaNro.setEditable(true);
		tfRefId.setEditable(true);
	}
}
