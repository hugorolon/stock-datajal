package py.com.prestosoftware.ui.transactions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.AjusteStock;
import py.com.prestosoftware.data.models.AjusteStockDetalle;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CuentaProveedor;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.CuentaProveedorService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.CellRendererTextFieldSeleccion;
import py.com.prestosoftware.ui.helpers.CellTextFieldSeleccion;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.table.AjusteCuentaTableModel;
import py.com.prestosoftware.ui.viewmodel.AjusteCuenta;
import py.com.prestosoftware.util.Notifications;
import javax.swing.JTabbedPane;
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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

@Component
public class AjusteCuentaCreditoPanel extends JDialog implements ClienteInterfaz, ProveedorInterfaz {

	private static final long serialVersionUID = 1L;
	
	private static final int CLIENTE_CODE = 1;
	private static final int PROVEEDOR_CODE = 2;
	
    private JTextField tfNombre;
    private JTextField tfCodigo;
    private JTable tbProductos;
    private JTextField tfObs;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    
    private AjusteCuentaTableModel itemTableModel;
    private ConsultaCliente clienteDialog;
    private ConsultaProveedor proveedorDialog;
    private ClienteService clienteService;
    private ProveedorService proveedorService;
 
//    private CuentaClienteService cService;
    private CuentaProveedorService pService;
    private VentaService ventaService;
    private CompraService compraService;
    
    @Autowired
    public AjusteCuentaCreditoPanel(AjusteCuentaTableModel itemTableModel, ConsultaCliente depositoDialog, 
    		ConsultaProveedor proveedorDialog, ClienteService clienteService, ProductoService productoService,
    		//CuentaClienteService cService, 
    		CuentaProveedorService pService,
    		VentaService ventaService, CompraService compraService) {
    	this.itemTableModel = itemTableModel;
    	this.clienteDialog = depositoDialog;
    	this.proveedorDialog = proveedorDialog;
    	this.clienteService = clienteService;
    	//this.cService = cService;
    	this.pService = pService;
    	this.ventaService = ventaService;
    	this.compraService = compraService;
    	
    	//setSize(750, 450);
    	
    	setSize(900, 550);
    	setTitle("AJUSTE DE CUENTAS");
    	setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);
        
        initComponents();
    }

    private void initComponents() {
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(6, 77, 888, 358);
        getContentPane().add(tabbedPane);
        
        JPanel pnlProducto = new JPanel();
        tabbedPane.addTab("DETALLES", null, pnlProducto, null);
        pnlProducto.setLayout(null);
        
        JScrollPane scrollProducto = new JScrollPane();
        scrollProducto.setBounds(6, 6, 855, 300);
        pnlProducto.add(scrollProducto);
        
        tbProductos = new JTable(itemTableModel) {
        	public boolean isCellEditable(int fila, int columna) {
				return false;
			}
        };
        
        tipoColumna();
        scrollProducto.setViewportView(tbProductos);
        tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        
        JPanel panel = new JPanel();
        panel.setBounds(6, 483, 888, 39);
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
        panel.add(btnCerrar);
        
        tfObs = new JTextField();
        tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
        ((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfObs.setBounds(66, 447, 617, 30);
        getContentPane().add(tfObs);
        tfObs.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        tfObs.setColumns(10);
        
        JLabel lblObs = new JLabel("Obs.:");
        lblObs.setBounds(6, 447, 58, 30);
        getContentPane().add(lblObs);
        
        lblNota = new JLabel("Nota:");
        lblNota.setBounds(6, 5, 58, 30);
        getContentPane().add(lblNota);
        
        tfNota = new JTextField();
        tfNota.setBounds(69, 5, 86, 30);
        getContentPane().add(tfNota);
        tfNota.setEditable(false);
        tfNota.setToolTipText("");
        tfNota.setColumns(10);
        
        JLabel lblReferencia = new JLabel("Codigo:");
        lblReferencia.setBounds(6, 35, 62, 30);
        getContentPane().add(lblReferencia);
        
        tfCodigo = new JTextField();
        tfCodigo.setBounds(69, 35, 86, 30);
        getContentPane().add(tfCodigo);
        tfCodigo.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfCodigo.selectAll();
        	}
        });
        tfCodigo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
        			openDialog();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Long id = Long.valueOf(tfCodigo.getText());
					if (tipoCuenta.equals("CLIENTE")) {
						findClienteById(id);
					} else {
						findProveedorById(id);
					}
				}
        	}
			@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCodigo.setToolTipText("");
        tfCodigo.setColumns(10);
        
        tfNombre = new JTextField();
        tfNombre.setBounds(154, 35, 253, 30);
        getContentPane().add(tfNombre);
        tfNombre.setEditable(false);
        tfNombre.setColumns(10);
        
        tfTotal = new JTextField();
        tfTotal.setForeground(Color.RED);
        tfTotal.setFont(new Font("Arial", Font.BOLD, 16));
        tfTotal.setEditable(false);
        tfTotal.setColumns(10);
        tfTotal.setBounds(764, 447, 130, 30);
        getContentPane().add(tfTotal);
        
        lblTotal = new JLabel("Total:");
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBounds(708, 447, 51, 30);
        getContentPane().add(lblTotal);
        
        lblValor = new JLabel("Valor:");
        lblValor.setBounds(708, 35, 62, 30);
        getContentPane().add(lblValor);
        
        tfValor = new JTextField();
        tfValor.setToolTipText("");
        tfValor.setColumns(10);
        tfValor.setBounds(765, 35, 130, 30);
        getContentPane().add(tfValor);
    }
    
    private void tipoColumna() {
        tbProductos.getColumnModel().getColumn(4).setCellEditor(new CellTextFieldSeleccion() );
        tbProductos.getColumnModel().getColumn(4).setCellRenderer(new CellRendererTextFieldSeleccion());
	}
    
    private void openDialog() {
    	if (tipoCuenta.equals("CLIENTE")) {
			showDialog(CLIENTE_CODE);
		} else {
			showDialog(PROVEEDOR_CODE);
		}
	}
    
    
    public void setCliente(Optional<Cliente> cliente) {
    	if (cliente.isPresent()) {
    		String nombre = cliente.get().getRazonSocial();
    		tfNombre.setText(nombre);
    		//tfDocumento.requestFocus(); 
    	}
    }
    
    private JLabel lblNota;
    private JTextField tfNota;

    public AjusteStock getFormValue() {
    	AjusteStock t = new AjusteStock();
        
    	t.setFecha(new Date());
    	t.setDeposito(new Deposito(Long.valueOf(tfCodigo.getText())));
    	t.setUsuario(new Usuario(GlobalVars.USER_ID));
    	t.setTipoAjuste(tipoCuenta);
    	t.setSituacion("ACTIVO");
    	t.setObs(tfObs.getText());
        return t;
    }

    public void clearItem() {
//    	tfDocumento.setText("");
//    	tfValorTotal.setText("");
//    	tfFechaVenc.setText("");
//    	tfSaldoDeb.setText("");
//    	tfDocumento.requestFocus();
    }

    public void clearForm() {
    	tfCodigo.setText("");
        tfNombre.setText("");
//    	tfDocumento.setText("");
//    	tfFechaVenc.setText("");
//    	tfValorTotal.setText("");
    	tfObs.setText("");
    	tfTotal.setText("");
    	
    	while (itemTableModel.getRowCount() > 0) {
    		itemTableModel.removeRow(0);
		}
    	
    	tfCodigo.requestFocus();
    }
    
    
    
    private void findClienteById(Long id) {
    	Optional<Cliente> cliente = clienteService.findById(id);
    	
    	if (cliente.isPresent()) {
    		tfNombre.setText(cliente.get().getNombre());
    		
    		getFacturasPendientes();
		}
	}
    
    private void getFacturasPendientes() {
    	List<AjusteCuenta> ac = new ArrayList<>();
    	
    	if (tipoCuenta.equals("CLIENTE")) {
    		List<Venta> ventas = ventaService.getNotasPendientesBySituacion("PENDIENTE");
    		
    		System.out.println();
    		System.out.println(ventas.size());
    		
    		if (!ventas.isEmpty()) {
    			for (Venta venta : ventas) {
    				ac.add(new AjusteCuenta(String.valueOf(venta.getId()), venta.getFecha(), venta.getTotalGeneral(), venta.getTotalGeneral(), 0d));	
				}
    			
			} else {
				Integer respuesta = JOptionPane.showConfirmDialog(null, "CLIENTE NO POSEE FACTURAS PENDIENTES");
				if (respuesta == 0) {
					tbProductos.setValueAt(FormatearValor.stringADouble(tfValor.getText()), 0, 5);
				} else {
					tfCodigo.requestFocus();
				}
			}
		} else {
			List<Compra> compras = compraService.getNotasPendientesByFechaAndSituacion(new Date(), "PENDIENTE");
	    	
			if (!compras.isEmpty()) {
				for (Compra e : compras) {
					ac.add(new AjusteCuenta(String.valueOf(e.getId()), e.getFecha(), e.getTotalGeneral(), e.getTotalGeneral(), 0d));	
				}
			} else {
				Integer respuesta = JOptionPane.showConfirmDialog(null, "PROVEEDOR NO POSEE FACTURAS PENDIENTES");
				if (respuesta == 0) {
					
				} else {
					tfCodigo.requestFocus();
				}
			}
	    	
		}
    }
    
    private void findProveedorById(Long id) {
    	Optional<Proveedor> proveedor = proveedorService.findById(id);
    	
    	if (proveedor.isPresent()) {
    		tfNombre.setText(proveedor.get().getNombre());
		}
	}
    
    private void showDialog(int code) {
    	switch (code) {
			case CLIENTE_CODE:
				clienteDialog.setInterfaz(this);
		    	clienteDialog.setVisible(true);	
				break;
			case PROVEEDOR_CODE:
				proveedorDialog.setInterfaz(this);
				proveedorDialog.setVisible(true);
				break;
			default:
				break;
		}
    }
    
    private CuentaProveedor getProveedorValue() {
    	CuentaProveedor cp = new CuentaProveedor();
    	
    	cp.setProveedor(new Proveedor(Long.valueOf(tfCodigo.getText())));
    	cp.setProveedorNombre(tfNombre.getText());
    	cp.setTipo("AJUSTES");
//    	cp.setFecha(Fechas.stringDDMMAAAAADateSQL(tfFecha.getText()));
//    	cp.setVencimiento(Fechas.stringDDMMAAAAADateUtil(tfVencimiento.getText()));
//    	cp.setDocumento(tfDocumento.getText());
    	cp.setUsuarioId(GlobalVars.USER_ID);
//    	cp.setObs(tfHistorico.getText());
    	cp.setDebito(FormatearValor.stringADouble(tfValor.getText()));
    	
    	return cp;
    }

//    public CuentaCliente getClienteValue() {
//    	CuentaCliente cc = new CuentaCliente();
//        
//    	cc.setCliente(new Cliente(Long.valueOf(tfCodigo.getText())));
//    	cc.setClienteNombre(tfNombre.getText());
//    	cc.setTipo("AJUSTES");
//    	
////    	cc.setFecha(Fechas.stringDDMMAAAAADateSQL(tfFecha.getText()));
////    	cc.setVencimiento(Fechas.stringDDMMAAAAADateUtil(tfVencimiento.getText()));
////    	cc.setDocumento(tfDocumento.getText());
////    	cc.setUsuarioId(GlobalVars.USER_ID);
////    	cc.setObs(tfHistorico.getText());
//    	cc.setDebito(FormatearValor.stringADouble(tfValor.getText()));
//    	
//        return cc;
//    }
    
    private void save() {
    	Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (tipoCuenta.equals("CLIENTE")) {
//	    		CuentaCliente t = getClienteValue();
//	    		cService.save(t);
	    	} else {
	    		CuentaProveedor t = getProveedorValue();
	    		pService.save(t);
	    	} 
	    	
	    	Notifications.showAlert("Ajuste de Cuenta realizado con exito.!");
			clearForm();
			//newAjusteCuenta();
		} else {
			//tfDocumento.requestFocus();
		}
    }
    
//    private void updateStockProduct(List<AjusteStockDetalle> items) {
//    	List<Producto> productos = new ArrayList<>();
//    	
//    	items.forEach(e -> {
//    		Optional<Producto> pOptional = productoService.findById(e.getProductoId());
//    		
//    		if (pOptional.isPresent()) {
//    			Producto p = pOptional.get();
//    			Double stock = p.getStock() != null ? p.getStock():0;
//    			
//    			if (tipoCuenta.equals("ENTRADA")) {
//    				p.setStock(stock + e.getCantidadNueva());
//				} else {
//					p.setStock(stock - e.getCantidadNueva());
//				}
//				
//				productos.add(p);
//    		}	
//    	});
//    	
//    	productoService.updateStock(productos);
//	}
    
//    public void newTransferenciaProducto() {
//		long max = tService.getRowCount();
//		tfVentaId.setText(String.valueOf(max + 1));
//		tfClienteID.requestFocus();
//	}
    
    private boolean validateCabezera() {
    	//validar deposito
		if (tfCodigo.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Deposito Origen es obligatorio");
			tfCodigo.requestFocus();
    		return false;
		}
		
//    	Optional<Deposito> deposito = depositoService.findById(Long.valueOf(tfRefId.getText()));
    	
//    	if (!deposito.isPresent()) {
//    		Notifications.showAlert("El codigo del Deposito Origen no existe.!");
//    		tfRefId.requestFocus();
//			return false;
//		}
    	
    	return true;
    }
    
    private boolean validateItems(List<AjusteStockDetalle> items) {    		
//    	items.forEach(e -> {
//    		Optional<Producto> producto = productoService.findById(e.getProductoId());
//    		
//    		if (producto.isPresent()) {
//    			//verificar la cantidad
//    			Double stock = producto.get().getStock();
//    			
//    			if (stock < e.getCantidadNueva() ) {
//    				Notifications.showAlert("La cantidad Nueva del producto: " + producto.get().getDescripcion() + 
//    						" sobrepasa el Stock Actual: " + producto.get().getStock());
//    				return;
//				}
//			}
//    		
//    	});
    	
    	return true;
    }
    
    public void newAjuste() {
//		long max = tService.getRowCount();
//		tfNota.setText(String.valueOf(max + 1));
//		tfRefId.requestFocus();
	}
    
    private String tipoCuenta = "";
    private JTextField tfTotal;
    private JLabel lblTotal;
    private JLabel lblValor;
    private JTextField tfValor;
    
    public void setTipoCuenta(String tipo) {
    	this.tipoCuenta = tipo;
    }

	@Override
	public void getEntity(Proveedor p) {
		if (p != null) {
			tfNombre.setText(p.getNombre());
			tfValor.requestFocus();
		}
	}

	@Override
	public void getEntity(Cliente c) {
		if (c != null) {
			tfNombre.setText(c.getNombre());
			tfValor.requestFocus();
		}
		
	}
}
