package py.com.prestosoftware.ui.transactions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.validations.CompraValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.CompraInterfaz;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.MonedaDialog;
import py.com.prestosoftware.ui.search.MonedaInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.shared.PanelCompraInterfaz;
import py.com.prestosoftware.ui.table.CompraImportacionTableModel;
import py.com.prestosoftware.ui.table.CompraItemTableModel;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@Component("compraConsignada")
public class CompraConsignadaPanel extends JFrame implements ProveedorInterfaz, 
	DepositoInterfaz, MonedaInterfaz, ProductoInterfaz, PanelCompraInterfaz, CompraInterfaz {

	private static final long serialVersionUID = 1L;
	
	private static final int PROVEEDOR_CODE = 1;
	private static final int MONEDA_CODE = 2;
	private static final int DEPOSITO_CODE = 3;
	private static final int PRODUCTO_CODE = 4;
	
    private JTextField tfNombre, tfDescripcion, tfProductoID, tfProveedorID, tfPrecioTotal, tfCompraId;
    private JTextField tfPrecio, tfMonedaID, tfMoneda, tfCondPago, tfCantidad, tfCantItem;
    private JTextField tfDescuento, tfObs, tfTotalFob, tfTotalCif, tfDepositoID, tfDeposito;
    private JTextField tfRuc, tfDireccion, tfGasto;
    private JTextField tfFactura;
    private JFormattedTextField tfFechaCompra;
    private JButton btnAdd, btnRemove;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JPanel panel;
    private JTable tbProductos;
    private JLabel lblBuscadorDeCompra;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
    
    private CompraItemTableModel itemTableModel;
    private CompraImportacionTableModel gastoTableModel;
    private ConsultaProveedor proveedorDialog;
    private DepositoDialog depositoDialog;
    private MonedaDialog monedaDialog;
    private ProductoDialog productoDialog;
    private CompraService compraService;
    private ProveedorService proveedorService;
    private MonedaService monedaService;
    private DepositoService depositoService;
    private ProductoService productoService;
    private CompraValidator compraValidator;
    
    public CompraConsignadaPanel(CompraItemTableModel itemTableModel, 
    		ConsultaProveedor proveedorDialog, DepositoDialog depositoDialog, MonedaDialog monedaDialog,
		ProductoDialog productoDialog, CompraService compraService, ProveedorService proveedorService, 
		MonedaService monedaService, DepositoService depositoService, CompraValidator compraValidator,
		ProductoService productoService ) {
    	this.itemTableModel = itemTableModel;
    	this.proveedorDialog = proveedorDialog;
    	this.depositoDialog = depositoDialog;
    	this.monedaDialog = monedaDialog;
    	this.productoDialog = productoDialog;
    	this.compraService = compraService;
    	this.proveedorService = proveedorService;
    	this.monedaService = monedaService;
    	this.depositoService = depositoService;
    	this.productoService = productoService;
    	this.compraValidator = compraValidator;
    	
    	setSize(920, 623);
    	setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    @SuppressWarnings("serial")
	private void initComponents() {
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    	tabbedPane.setBounds(12, 114, 896, 330);
        
        JPanel pnlProducto = new JPanel();
        tabbedPane.addTab("Productos", null, pnlProducto, null);
        pnlProducto.setLayout(null);
        
        JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblCodigo.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblCodigo.setBounds(6, 6, 98, 18);
        pnlProducto.add(lblCodigo);
        
        JLabel lblDescripcion = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblDescripcion.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblDescripcion.setBounds(203, 6, 299, 18);
        pnlProducto.add(lblDescripcion);
        
        JLabel lblSubtotal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblSubtotal.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblSubtotal.setBounds(617, 6, 138, 18);
        pnlProducto.add(lblSubtotal);
        
        JLabel lblPrecio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblPrecio.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblPrecio.setBounds(502, 6, 115, 18);
        pnlProducto.add(lblPrecio);
        
        tfDescripcion = new JTextField();
        tfDescripcion.setEditable(false);
        tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        tfDescripcion.setColumns(10);
        tfDescripcion.setBounds(203, 30, 299, 30);
        pnlProducto.add(tfDescripcion);
        
        tfPrecioTotal = new JTextField();
        tfPrecioTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        tfPrecioTotal.setEditable(false);
        tfPrecioTotal.setFont(new Font("Arial", Font.PLAIN, 14));
        tfPrecioTotal.setColumns(10);
        tfPrecioTotal.setBounds(617, 30, 138, 30);
        pnlProducto.add(tfPrecioTotal);
        
        tfPrecio = new JTextField();
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
						Notifications.showAlert("Digite un numero valido.!");
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
        tfPrecio.setBounds(502, 30, 115, 30);
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
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProductoById(Long.valueOf(tfProductoID.getText()));
					} else {
						showDialog(PRODUCTO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfCondPago.requestFocus();
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
        tfProductoID.setBounds(6, 30, 98, 30);
        pnlProducto.add(tfProductoID);
        tfProductoID.setColumns(10);
        
        btnRemove = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnRemove.text")); //$NON-NLS-1$ //$NON-NLS-2$
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
        btnRemove.setBounds(826, 30, 51, 30);
        pnlProducto.add(btnRemove);
        
        JScrollPane scrollProducto = new JScrollPane();
        scrollProducto.setBounds(6, 66, 871, 198);
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
        		getSelectedItem();
        	}
        });
        tbProductos.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getSelectedItem();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        scrollProducto.setViewportView(tbProductos);
        
        tfCantidad = new JTextField();
        tfCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
        tfCantidad.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.tfCantidad.text")); //$NON-NLS-1$ //$NON-NLS-2$
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
        				tfPrecio.requestFocus();
					} else {
						Notifications.showAlert("Debes digitar cantidad.!");
					}
        		} else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
        			tfProductoID.requestFocus();
        		} else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
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
        tfCantidad.setBounds(104, 30, 98, 30);
        pnlProducto.add(tfCantidad);
        
        JLabel lblCantidad = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblCantidad.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblCantidad.setBounds(104, 6, 98, 18);
        pnlProducto.add(lblCantidad);
        
        btnAdd = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnAdd.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnAdd.setFont(new Font("Dialog", Font.BOLD, 18));
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
        btnAdd.setBounds(767, 30, 57, 30);
        pnlProducto.add(btnAdd);
        
        label_7 = new JLabel("*");
        label_7.setToolTipText("Campos obligatorios");
        label_7.setHorizontalAlignment(SwingConstants.CENTER);
        label_7.setForeground(Color.RED);
        label_7.setFont(new Font("Dialog", Font.BOLD, 20));
        label_7.setBounds(67, 6, 14, 18);
        pnlProducto.add(label_7);
        
        JPanel pnlCabezera = new JPanel();
        pnlCabezera.setBounds(12, 12, 896, 101);
        pnlCabezera.setBorder(new TitledBorder(null, "COMPRA CONSIGNADA", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlCabezera.setLayout(null);
        
        JPanel pnlCliente = new JPanel();
        pnlCliente.setBounds(6, 18, 876, 74);
        pnlCabezera.add(pnlCliente);
        pnlCliente.setLayout(null);
        
        JLabel lblProveedor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblProveedor.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblProveedor.setBounds(170, 4, 68, 30);
        pnlCliente.add(lblProveedor);
        
        tfProveedorID = new JTextField();
        tfProveedorID.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfProveedorID.selectAll();
        	}
        });
        tfProveedorID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfProveedorID.getText().isEmpty()) {
        				Long cod = Long.valueOf(tfProveedorID.getText());
	        			if (cod != 0) {
	        				findProveedorById(cod);
						} else {
							habilitarText(true);
						}
        			} else {
        				//Notifications.showAlert("Debes digitar el codigo del Proveedor");
        				showDialog(PROVEEDOR_CODE);
        			}
				} else if (e.getKeyCode() == KeyEvent.VK_F12) {
        			showDialog(PROVEEDOR_CODE);
				} 
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfProveedorID.setText("");
       
        tfProveedorID.setBounds(252, 4, 51, 30);
        pnlCliente.add(tfProveedorID);
        tfProveedorID.setColumns(10);
        
        tfNombre = new JTextField();
        tfNombre.setEditable(false);
        tfNombre.setBounds(304, 4, 139, 30);
        pnlCliente.add(tfNombre);
        tfNombre.setColumns(10);
        
        JLabel lblVendedor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblVendedor.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblVendedor.setBounds(170, 38, 68, 30);
        pnlCliente.add(lblVendedor);
        
        tfMonedaID = new JTextField();
        tfMonedaID.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfMonedaID.selectAll();
        	}
        });
        tfMonedaID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
        			showDialog(MONEDA_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfMonedaID.getText().isEmpty()) {
						findMonedaById(Long.valueOf(tfMonedaID.getText()));
					} else {
						//Notifications.showAlert("");
						showDialog(MONEDA_CODE);
					}
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfMonedaID.setBounds(252, 38, 51, 30);
        pnlCliente.add(tfMonedaID);
        tfMonedaID.setToolTipText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.tfMonedaID.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
        tfMonedaID.setColumns(10);
        
        tfMoneda = new JTextField();
        tfMoneda.setEditable(false);
        tfMoneda.setBounds(304, 38, 139, 30);
        pnlCliente.add(tfMoneda);
        tfMoneda.setColumns(10);
        
        JLabel lblDeposito = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblDeposito.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblDeposito.setBounds(449, 38, 51, 30);
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
						findDepositoById(Long.valueOf(tfDepositoID.getText()));
					} else {
						showDialog(DEPOSITO_CODE);
						//Notifications.showAlert("Debes digita el codigo del Deposito.!");
					}
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfDepositoID.setColumns(10);
        tfDepositoID.setBounds(505, 38, 51, 30);
        pnlCliente.add(tfDepositoID);
        
        tfDeposito = new JTextField();
        tfDeposito.setEditable(false);
       
        tfDeposito.setColumns(10);
        tfDeposito.setBounds(557, 38, 157, 30);
        pnlCliente.add(tfDeposito);
        
        JLabel lblRuc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblRuc.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblRuc.setBounds(720, 4, 51, 30);
        pnlCliente.add(lblRuc);
        
        tfRuc = new JTextField();
        tfRuc.setEditable(false);
        tfRuc.setColumns(10);
        tfRuc.setBounds(777, 4, 93, 30);
        pnlCliente.add(tfRuc);
        
        JLabel lblDireccin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblDireccin.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblDireccin.setBounds(449, 4, 41, 30);
        pnlCliente.add(lblDireccin);
        
        tfDireccion = new JTextField();
        tfDireccion.setEditable(false);
        tfDireccion.setColumns(10);
        tfDireccion.setBounds(505, 4, 209, 30);
        pnlCliente.add(tfDireccion);
        
        tfFactura = new JTextField();
        tfFactura.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfFactura.selectAll();
        	}
        });
        tfFactura.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfDepositoID.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        
        tfFactura.setColumns(10);
        tfFactura.setBounds(777, 37, 93, 30);
        pnlCliente.add(tfFactura);
        
        JLabel lblFactura = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblFactura.text")); //$NON-NLS-1$ //$NON-NLS-2$
        lblFactura.setBounds(720, 38, 51, 30);
        pnlCliente.add(lblFactura);
        
        tfFechaCompra = new JFormattedTextField(getFormatoFecha());
        tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
        tfFechaCompra.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfProductoID.requestFocus();	
				}
        	}
        });
       
        tfFechaCompra.setColumns(8);
        tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
        tfFechaCompra.setBounds(73, 38, 93, 30);
        pnlCliente.add(tfFechaCompra);
        
        JLabel lblFCompra = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblFCompra.text")); //$NON-NLS-1$ //$NON-NLS-2$
        
        lblFCompra.setBounds(6, 38, 68, 30);
        pnlCliente.add(lblFCompra);
        
        JPanel pnlBotonera = new JPanel();
        pnlBotonera.setBounds(12, 554, 896, 41);
        
        btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnGuardar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnGuardar.setMnemonic('G');
        btnGuardar.setBounds(291, 5, 110, 34);
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
        pnlBotonera.setLayout(null);
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnCancelar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnCancelar.setMnemonic('C');
        btnCancelar.setBounds(406, 5, 110, 34);
        btnCancelar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			cancelar();
				}
        	}
        });
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cancelar();
        	}
        });
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnCerrar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        btnCerrar.setMnemonic('E');
        btnCerrar.setBounds(521, 5, 110, 34);
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btnCerrar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
        	}
        });
        pnlBotonera.add(btnCerrar);
        getContentPane().setLayout(null);
        getContentPane().add(tabbedPane);
        getContentPane().add(pnlCabezera);
        getContentPane().add(pnlBotonera);
        
        panel = new JPanel();
        panel.setBounds(12, 468, 896, 85);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblCantItem = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblCantItem.text"));
        lblCantItem.setBounds(2, 12, 74, 30);
        panel.add(lblCantItem);
        
        tfCantItem = new JTextField();
        tfCantItem.setBounds(85, 12, 65, 30);
        panel.add(tfCantItem);
        tfCantItem.setEditable(false);
        tfCantItem.setColumns(10);
        
        JLabel lblFlete = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblFlete.text"));
        lblFlete.setBounds(162, 12, 51, 30);
        panel.add(lblFlete);
        
        tfGasto = new JTextField();
        tfGasto.setHorizontalAlignment(SwingConstants.RIGHT);
        tfGasto.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.tfGasto.text")); //$NON-NLS-1$ //$NON-NLS-2$
        tfGasto.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfGasto.selectAll();
        	}
        });
        tfGasto.setBounds(213, 12, 119, 30);
        panel.add(tfGasto);
        tfGasto.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfGasto.getText().isEmpty()) {
        				Double subtotal = tfTotalFob.getText().isEmpty() ? 0 : FormatearValor.stringADouble(tfTotalFob.getText());
            			setTotals(0d, subtotal);     			
            			tfDescuento.requestFocus();
					}
        		}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfGasto.setColumns(10);
        
        JLabel lblCondicin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblCondicin.text"));
        lblCondicin.setBounds(2, 45, 74, 30);
        panel.add(lblCondicin);
        
        tfCondPago = new JTextField();
        tfCondPago.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfCondPago.selectAll();
        	}
        });
        tfCondPago.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCondPago.getText().isEmpty()) {
						tfGasto.requestFocus();
					} else {
						Notifications.showAlert("Digite condicion de Pago");
					}
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCondPago.setBounds(85, 46, 65, 30);
        panel.add(tfCondPago);
        tfCondPago.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.tfCondPago.text"));
        
        JLabel lblDesc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblDesc.text"));
        lblDesc.setBounds(162, 46, 51, 30);
        panel.add(lblDesc);
        
        tfDescuento = new JTextField();
        tfDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
        tfDescuento.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.tfDescuento.text")); //$NON-NLS-1$ //$NON-NLS-2$
        tfDescuento.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfDescuento.selectAll();
        	}
        });
        tfDescuento.setBounds(213, 46, 119, 30);
        panel.add(tfDescuento);
        tfDescuento.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfDescuento.getText().isEmpty()) {
        				Double subtotal = tfTotalFob.getText().isEmpty() ? 0 : FormatearValor.stringADouble(tfTotalFob.getText());
            			setTotals(0d, subtotal);
            			tfObs.requestFocus();
					}
        			
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfDescuento.setColumns(10);
        
        JLabel lblSubTotal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblSubTotal.text"));
        lblSubTotal.setBounds(344, 12, 74, 30);
        panel.add(lblSubTotal);
        
        JLabel lblObs = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblObs.text"));
        lblObs.setBounds(344, 45, 74, 30);
        panel.add(lblObs);
        
        tfTotalFob = new JTextField();
        tfTotalFob.setHorizontalAlignment(SwingConstants.RIGHT);
        tfTotalFob.setBounds(425, 12, 163, 30);
        panel.add(tfTotalFob);
        tfTotalFob.setForeground(Color.RED);
        tfTotalFob.setEditable(false);
        tfTotalFob.setColumns(10);
        
        JLabel lblTotal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblTotal.text"));
        lblTotal.setBounds(633, 12, 84, 30);
        panel.add(lblTotal);
        
        tfTotalCif = new JTextField();
        tfTotalCif.setHorizontalAlignment(SwingConstants.RIGHT);
        tfTotalCif.setBounds(715, 12, 163, 30);
        panel.add(tfTotalCif);
        tfTotalCif.setEditable(false);
        tfTotalCif.setFont(new Font("Arial", Font.BOLD, 16));
        tfTotalCif.setForeground(Color.RED);
        tfTotalCif.setColumns(10);
        
        tfObs = new JTextField();
        tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
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
        tfObs.setBounds(425, 45, 453, 30);
        panel.add(tfObs);
        tfObs.setColumns(10);
        
        label_4 = new JLabel("*");
        label_4.setVerticalAlignment(SwingConstants.BOTTOM);
        label_4.setToolTipText("Campos obligatorios");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setForeground(Color.RED);
        label_4.setFont(new Font("Dialog", Font.BOLD, 20));
        label_4.setBounds(70, 46, 14, 30);
        panel.add(label_4);
        
//        btnBuscar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.btnBuscar.text")); //$NON-NLS-1$ //$NON-NLS-2$
//        btnBuscar.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		showDialog();
//        	}
//        });
//        btnBuscar.setBounds(316, 2, 150, 32);
//        pnlBuscador.add(btnBuscar);
        
        tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
        
        lblBuscadorDeCompra = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.lblBuscadorDeCompra.text"));
        lblBuscadorDeCompra.setBounds(6, 4, 68, 30);
        pnlCliente.add(lblBuscadorDeCompra);
        
        tfCompraId = new JTextField();
        tfCompraId.setEditable(false);
        tfCompraId.setBounds(73, 3, 93, 32);
        pnlCliente.add(tfCompraId);
        tfCompraId.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findCompraById(Long.valueOf(tfCompraId.getText()));
				} else if(e.getKeyCode() == KeyEvent.VK_F3) {
					// DIALOGO -> OP/DOCUMENTO/PROVEEDOR
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCompraId.setToolTipText((String) null);
        tfCompraId.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.transactions.messages").getString("CompraPanel.tfCompraId.text")); //$NON-NLS-1$ //$NON-NLS-2$
        tfCompraId.setColumns(10);
        
        label = new JLabel("*");
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setToolTipText("Campos obligatorios");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        label.setFont(new Font("Dialog", Font.BOLD, 20));
        label.setBounds(236, 4, 14, 30);
        pnlCliente.add(label);
        
        label_2 = new JLabel("*");
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setToolTipText("Campos obligatorios");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.RED);
        label_2.setFont(new Font("Dialog", Font.BOLD, 20));
        label_2.setBounds(236, 38, 14, 30);
        pnlCliente.add(label_2);
        
        label_3 = new JLabel("*");
        label_3.setVerticalAlignment(SwingConstants.BOTTOM);
        label_3.setToolTipText("Campos obligatorios");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setForeground(Color.RED);
        label_3.setFont(new Font("Dialog", Font.BOLD, 20));
        label_3.setBounds(488, 38, 14, 30);
        pnlCliente.add(label_3);
        
        label_5 = new JLabel("Campos Obligatorios");
        label_5.setFont(new Font("Dialog", Font.BOLD, 20));
        label_5.setBounds(32, 443, 299, 25);
        getContentPane().add(label_5);
        
        label_6 = new JLabel("*");
        label_6.setVerticalAlignment(SwingConstants.BOTTOM);
        label_6.setToolTipText("Campos obligatorios");
        label_6.setHorizontalAlignment(SwingConstants.CENTER);
        label_6.setForeground(Color.RED);
        label_6.setFont(new Font("Dialog", Font.BOLD, 20));
        label_6.setBounds(12, 443, 14, 25);
        getContentPane().add(label_6);
    }
    
    private MaskFormatter formatoFecha;
    private JLabel label_7;
    
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
    
    private void getSelectedItem() {
    	int selectedRow = tbProductos.getSelectedRow();
        
        if (selectedRow != -1) {
        	CompraDetalle item = itemTableModel.getEntityByRow(selectedRow);
        	
        	tfProductoID.setText(String.valueOf(item.getProductoId()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidad()));
			tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfPrecio.setText(FormatearValor.doubleAString(item.getPrecio()));
			tfPrecioTotal.setText(FormatearValor.doubleAString(item.getSubtotal()));
        }
    }
    
    private Compra getCompra() {
    	Compra compra = new Compra();
    	compra.setComprobante("SIN COMPROBANTE");
    	compra.setFecha(new Date());
    	compra.setTipoCompra("CONSIGNADA");
    	compra.setFactura(tfFactura.getText());
    	compra.setFechaCompra(Fechas.stringToDate(tfFechaCompra.getText()));
    	compra.setCondicion(Integer.parseInt(tfCondPago.getText()));
    	compra.setMoneda(new Moneda(tfMonedaID.getText().isEmpty() ? 1 : Long.valueOf(tfMonedaID.getText()))); //REFACTORAR A MONEDA BASE
    	compra.setProveedor(new Proveedor(Long.valueOf(tfProveedorID.getText())));
    	compra.setProveedorNombre(tfNombre.getText());
    	compra.setDeposito(new Deposito(Long.valueOf(tfDepositoID.getText())));
    	compra.setMoneda(new Moneda(GlobalVars.BASE_MONEDA_ID));
    	compra.setTotalItem(tfCantItem.getText().isEmpty() ? 1 : FormatearValor.stringToDouble(tfCantItem.getText()));
    	compra.setSituacion("PENDIENTE");
    	compra.setObs(tfObs.getText());
    	
    	compra.setGastos(tfGasto.getText().isEmpty() ? 0 : Double.valueOf(tfGasto.getText()));
    	compra.setDescuento(tfDescuento.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfDescuento.getText())); 
    	compra.setTotalFob(tfTotalFob.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalFob.getText())); 
    	compra.setTotalCif(tfTotalCif.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalCif.getText())); 
    	compra.setTotalGeneral(tfTotalFob.getText().isEmpty() ? 0 : FormatearValor.stringToDouble(tfTotalFob.getText()));
    	
    	compra.setItems(itemTableModel.getEntities());
    	
    	return compra;
    }
    
    private void updateStockProduct(Compra compra, List<CompraDetalle> items, Deposito deposito, Date fechaCompra) {
    	List<Producto> productos = new ArrayList<>();
//        List<ProductoDeposito> pDepositos = new ArrayList<>();
        for (CompraDetalle e: items) {
    		Optional<Producto> pOptional = productoService.findById(e.getProductoId());
    		
    		if (pOptional.isPresent()) {
    			Producto p = pOptional.get();
    			
    			//precio promedio actual
//    			Double stock = p.getStock() != null ? p.getStock():0;
//    			Double precio = p.getPrecioCosto() != null ? p.getPrecioCosto():0;
//    			Double precioPromedioActual = stock * precio;
    			
    			//precio promedio compra
    			Double cantCompra = e.getCantidad();
    			Double precioCompra = e.getPrecio();
    			//Double precioPromedioCompra = cantCompra * precioCompra;
    		
    			//update products
//    			Double newQt = stock + cantCompra;
//				p.setStock(newQt);
				p.setEntPendiente(cantCompra);
				
				p.setCostoFob(precioCompra);
				//p.setPrecioCostoPromedio((precioPromedioActual + precioPromedioCompra) / newQt);
				
				//update deposito products
//				ProductoDeposito pd = new ProductoDeposito();
//				pd.setCompra(compra);
//				pd.setDeposito(deposito);
//				pd.setStock(e.getCantidad());
//				
//				pd.setFecha(fechaCompra);
				
//				pDepositos.add(pd);
//				p.setDepositos(pDepositos);
				
				productos.add(p);
    		}	
		}	
        	
        productoService.updateStock(productos);
    }

    private void save() {
    	if (validateCabezera()) { 
    		Compra compra = getCompra();
    	
	    	Optional<ValidationError> errors = compraValidator.validate(compra);
		        
	        if (errors.isPresent()) {
	            ValidationError validationError = errors.get();
	            Notifications.showFormValidationAlert(validationError.getMessage());
	        } else {
	        	Compra c = compraService.save(compra);
	        	
	        	if (c != null) {
	        		updateStockProduct(c, c.getItems(), c.getDeposito(), c.getFechaCompra());
        		}
	        	
	        	Notifications.showAlert("Compra registrado correctamente.!");
				clearForm();
				newCompra();
			}
        }
    }
    
    private boolean validateCabezera() {
    	//validar cliente, deposito, vendedor
    	if (tfProveedorID.getText().isEmpty()) {
    		Notifications.showAlert("El codigo del Proveedor es obligatorio");
    		tfProveedorID.requestFocus();
    		return false;
		} else if (tfDepositoID.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Deposito es obligatorio");
			tfDepositoID.requestFocus();
    		return false;
		}
    	
    	Optional<Proveedor> proveedor = proveedorService.findById(Long.valueOf(tfProveedorID.getText()));
    	
    	if (!proveedor.isPresent()) {
    		Notifications.showAlert("No codigo del Proveedor no existe.!");
    		tfProveedorID.requestFocus();
			return false;
		} 
		
    	Optional<Deposito> deposito = depositoService.findById(Long.valueOf(tfDepositoID.getText()));
    	
    	if (!deposito.isPresent()) {
    		Notifications.showAlert("El codigo del Deposito no existe.!");
			tfDepositoID.requestFocus();
			return false;
		}
    	
    	return true;
    }
    
    private CompraDetalle getItem() {
    	CompraDetalle item = new CompraDetalle();
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
    
    public void setTotals(Double cantItem, Double total) {
    	Double descuento = tfDescuento.getText().isEmpty() ? 0d : Double.valueOf(tfDescuento.getText());
    	Double flete = tfGasto.getText().isEmpty() ? 0d : Double.valueOf(tfGasto.getText());
    	Double totalGeneral = (total + flete) - descuento;
    	
    	tfTotalFob.setText(FormatearValor.doubleAString(total));
    	tfTotalCif.setText(FormatearValor.doubleAString(totalGeneral));
    	
    	if (cantItem != 0) {
    		tfCantItem.setText(FormatearValor.doubleAString(cantItem));
		}
    }

    public void clearForm() {
    	tfFactura.setText("");
    	tfFechaCompra.setText("");
    	tfProveedorID.setText("");
        tfNombre.setText("");
        tfRuc.setText("");
        tfDireccion.setText("");
        tfMonedaID.setText("");
    	tfMoneda.setText("");
    	tfDepositoID.setText("");
    	tfDeposito.setText("");
    	tfProductoID.setText("");
    	tfCantidad.setText("");
    	tfDescripcion.setText("");
    	tfPrecio.setText("");
    	tfPrecioTotal.setText("");
    	tfObs.setText("");
    	tfTotalCif.setText("");
    	tfTotalFob.setText("");
    	tfCantItem.setText("");
    	tfDescuento.setText("");
    	tfGasto.setText("");
    	tfCondPago.setText("");
    	
    	tfFechaCompra.setText(Fechas.formatoDDMMAAAA(new Date()));
    	
    	//isLocal = isImportacion = isConsiganada = isPedido = false;
    	
    	while (itemTableModel.getRowCount() > 0) {
    		itemTableModel.removeRow(0);
		}
    	
    	while (gastoTableModel.getRowCount() > 0) {
    		gastoTableModel.removeRow(0);
		}
    	
    	tfCompraId.requestFocus();
    }
    
    private void removeItem() {
    	int row[] = tbProductos.getSelectedRows();
    	
    	if (tbProductos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				itemTableModel.removeRow(row[i - 1]);
			}
			
			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}
  
    	calculateItem();
    }
    
    private void calculatePrecioTotal() {
    	Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
    	Double precioUnit = FormatearValor.stringToDouble(tfPrecio.getText());
    	Double precioTotal = cantidad * precioUnit;
    	
    	tfPrecioTotal.setText(FormatearValor.doubleAString(precioTotal));
    	btnAdd.requestFocus();
    }
    
    private void cancelar() {
    	clearForm();
		newCompra();
    }
    
    private void showDialog(int code) {
    	switch (code) {
			case PROVEEDOR_CODE:
				proveedorDialog.setInterfaz(this);
		    	proveedorDialog.setVisible(true);	
				break;
			case MONEDA_CODE:
				monedaDialog.setInterfaz(this);
				monedaDialog.setVisible(true);
				break;
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
    
    private void findProveedorById(Long id) {
    	Optional<Proveedor> proveedor = proveedorService.findById(id);
    	
    	if (proveedor.isPresent()) {
    		tfProveedorID.setText(String.valueOf(proveedor.get().getId()));
    		tfNombre.setText(proveedor.get().getRazonSocial());
			tfRuc.setText(proveedor.get().getRuc());
			tfDireccion.setText(proveedor.get().getDireccion());
			tfFechaCompra.requestFocus();
		} else {
			Notifications.showAlert("No existe Proveedor con ese codigo.!");
			tfProductoID.requestFocus();
		}
    }
    
    private void findMonedaById(Long id) {
    	Optional<Moneda> moneda = monedaService.findById(id);
    	
    	if (moneda.isPresent()) {
    		tfMonedaID.setText(String.valueOf(moneda.get().getId()));
    		tfMoneda.setText(moneda.get().getNombre());
			tfDepositoID.requestFocus();
		} else {
			Notifications.showAlert("No existe Moneda con ese codigo.!");
			tfDepositoID.requestFocus();
		}
    }
    
    private void findDepositoById(Long id) {
    	Optional<Deposito> deposito = depositoService.findById(id);
    	
    	if (deposito.isPresent()) {
    		tfDepositoID.setText(String.valueOf(deposito.get().getId()));
    		tfDeposito.setText(deposito.get().getNombre());
    		tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("No existe Deposito con ese codigo.!");
			tfDeposito.requestFocus();
		}
    }
    
    private void findProductoById(Long id) {
    	Optional<Producto> producto = productoService.findById(id);
    	
    	if (producto.isPresent()) {
    		Double ultimoPrecioCosto = producto.get().getCostoFob() != null ? producto.get().getCostoFob() : 0;
    		tfDescripcion.setText(producto.get().getDescripcion());
    		tfPrecio.setText(FormatearValor.doubleAString(ultimoPrecioCosto));
    		tfCantidad.setText("1");
			tfCantidad.requestFocus();
		} else {
			Notifications.showAlert("No existe Producto con ese codigo.!");
			tfProductoID.requestFocus();
		}
    }
    
    private void habilitarText(boolean habil) {
    	tfNombre.setEditable(habil);
    	tfRuc.setEditable(habil);
    	tfDireccion.setEditable(habil);
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
			Integer respuesta = JOptionPane.showConfirmDialog(null, "Registro ya existe en la grilla, desea actualizar los datos?");
			if (respuesta == 0) {
				if (isValidItem()) {
					actualizarRegristroGrilla(fila, String.valueOf(tbProductos.getValueAt(fila, 1)));
					tfProductoID.requestFocus();
				}
			} else {
				tfProductoID.requestFocus();
			}
		} else {
			addItemToList();
		}
    	
    	calculateItem();
    	clearItem();
    }
    
    private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
    	itemTableModel.removeRow(fila);
    	
    	Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());   	
    	Double cantidadTotal = Double.valueOf(cantAnteriorItem) + cantidad;
    	Double precio = FormatearValor.stringADouble(tfPrecio.getText()); 
    	
    	tfCantidad.setText(FormatearValor.doubleAString(cantidadTotal));
    	tfPrecioTotal.setText(FormatearValor.doubleAString(cantidadTotal * precio));
    	
    	addItemToList();
    	calculateItem();
    	clearItem();
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
		} else if (!tfCantidad.getText().isEmpty() && FormatearValor.stringADouble(tfPrecio.getText()) <= 0) {
			Notifications.showAlert("El precio debe ser mayor a cero");
			tfPrecio.requestFocus();
			return false;
		}
		
		return true;
	}
    
    private void addItemToList() {
    	itemTableModel.addEntity(getItem());
    }
    
    private void calculateItem() {
    	Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidad()).sum();
    	Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotal()).sum();
    	setTotals(cantItem, total);
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
		if (producto != null) {
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();
		}
	}

	@Override
	public void getEntity(Moneda moneda) {
		if (moneda != null) {
			tfMonedaID.setText(String.valueOf(moneda.getId()));
			tfMoneda.setText(moneda.getNombre());
			tfDeposito.requestFocus();
		}
	}

	@Override
	public void getEntity(Proveedor p) {
		if (p != null) {
			tfProveedorID.setText(String.valueOf(p.getId()));
			tfRuc.setText(p.getRuc());
			tfDireccion.setText(p.getDireccion());
		}
	}

	@Override
	public void goToCompraLocal() {
		tfMonedaID.setEditable(false);
		tfMoneda.setEditable(false);
	}

	@Override
	public void goToCompraImportacion() {
		tfMonedaID.setEditable(true);
		tfMoneda.setEditable(true);
	}

	@Override
	public void goToCompraConsiganada() {
		tfCondPago.setEditable(false);
		tfCondPago.setText("30");
		tfMonedaID.setEditable(false);
		tfMoneda.setEditable(false);
		tfFactura.setEditable(false);
		
		//Setear por configuracion general
		tfDepositoID.setText("30");
		tfDeposito.setText("Deposito 30");
		tfDepositoID.setEnabled(false);
		tfDeposito.setEnabled(false);
	}

	@Override
	public void goToPedidoCompra() {
		tfMonedaID.setEditable(false);
		tfMoneda.setEditable(false);
		tfDepositoID.setEditable(false);
		tfDeposito.setEnabled(false);
		tfFactura.setEditable(false);
		tfFechaCompra.setEditable(false);
	}

	@Override
	public void getEntity(Compra c) {
		if (c != null) {
			loadCompra(c);
		}
	}
	
	private void findCompraById(Long id) {
		Optional<Compra> compra = compraService.findById(id);
		
		if (compra.isPresent()) {
			loadCompra(compra.get());
		} else {
			tfProveedorID.requestFocus();
		}
	}
	
	private void loadCompra(Compra c) {
		tfProveedorID.setText(String.valueOf(c.getProveedor().getId())); 
    	tfMonedaID.setText(String.valueOf(c.getMoneda().getId()));
    	tfDepositoID.setText(String.valueOf(c.getDeposito().getId()));
    	tfFactura.setText(c.getFactura());
    	tfFechaCompra.setText(c.getFechaCompra() == null ? "" : String.valueOf(c.getFechaCompra()));
    	tfCondPago.setText(String.valueOf(c.getCondicion()));
    	tfCantItem.setText(FormatearValor.doubleAString(c.getTotalItem()));
    	tfObs.setText(c.getObs());
    	tfGasto.setText(FormatearValor.doubleAString(c.getGastos()));
    	tfDescuento.setText(FormatearValor.doubleAString(c.getDescuento()));
    	tfTotalFob.setText(FormatearValor.doubleAString(c.getTotalFob()));
    	tfTotalCif.setText(FormatearValor.doubleAString(c.getTotalCif()));
    	
    	findProveedorById(c.getProveedor().getId());
    	findDepositoById(c.getDeposito().getId());
    	findMonedaById(c.getMoneda().getId());
    	
    	itemTableModel.addEntities(c.getItems());
	}
	
	public void newCompra() {
		long max = compraService.getRowCount();
		long newId = max + 1;
		tfCompraId.setText(String.valueOf(newId));
		tfProveedorID.requestFocus();
	}
	
}