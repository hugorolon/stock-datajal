package py.com.prestosoftware.ui.transactions;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.PedidoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.search.ProveedorDialog;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.table.ItemTableModel;
import py.com.prestosoftware.util.Notifications;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.util.Optional;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class PedidoPanel extends JDialog implements ProveedorInterfaz, ProductoInterfaz {

	private static final long serialVersionUID = 1L;
	
	private static final int PROVEEDOR_CODE = 1;
	private static final int PRODUCTO_CODE = 2;
	
    private JTextField tfClienteNombre;
    private JTextField tfDescripcion;
    private JTextField tfProductoID;
    private JTextField tfClienteID;
    private JTextField tfPrecioTotal;
    private JTextField tfPrecio;
    private JTable tbProductos;
    private JTextField tfCantidad;
    private JTextField tfTotalItems;
    private JTextField tfObs;
    private JTextField tfTotal;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    
    private ItemTableModel itemTableModel;
    
    private ProveedorDialog proveedorDialog;
    private ProductoDialog productoDialog;
    
    private ProveedorService proveedorService;
    private ProductoService productoService;
    private PedidoService pedidoService;

    @Autowired
    public PedidoPanel(ItemTableModel itemTableModel, ProveedorDialog clientDialog, 
    		ProductoDialog productoDialog, ProveedorService clienteService,
    		ProductoService productoService, PedidoService pedidoService) {
    	this.itemTableModel = itemTableModel;
    	this.proveedorDialog = clientDialog;
    	this.productoDialog = productoDialog;
    	this.proveedorService = clienteService;
    	this.productoService = productoService;
    	this.pedidoService = pedidoService;
    	
    	this.setSize(900, 569);
        getContentPane().setLayout(null);
        
        initComponents();
    }

    private void initComponents() {
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(6, 117, 888, 332);
        getContentPane().add(tabbedPane);
        
        JPanel pnlProducto = new JPanel();
        tabbedPane.addTab("Productos", null, pnlProducto, null);
        pnlProducto.setLayout(null);
        
        JLabel lblCodigo = new JLabel("CODIGO");
        lblCodigo.setBounds(6, 10, 98, 30);
        pnlProducto.add(lblCodigo);
        
        JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
        lblDescripcion.setBounds(217, 10, 299, 30);
        pnlProducto.add(lblDescripcion);
        
        JLabel lblSubtotal = new JLabel("PRECIO TOTAL");
        lblSubtotal.setBounds(617, 10, 138, 30);
        pnlProducto.add(lblSubtotal);
        
        JLabel lblPrecio = new JLabel("PRECIO UNIT.");
        lblPrecio.setBounds(502, 10, 141, 30);
        pnlProducto.add(lblPrecio);
        
        tfDescripcion = new JTextField();
        tfDescripcion.setEditable(false);
        tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        tfDescripcion.setColumns(10);
        tfDescripcion.setBounds(203, 40, 299, 30);
        pnlProducto.add(tfDescripcion);
        
        tfPrecioTotal = new JTextField();
        tfPrecioTotal.setEditable(false);
        tfPrecioTotal.setFont(new Font("Arial", Font.PLAIN, 14));
        tfPrecioTotal.setColumns(10);
        tfPrecioTotal.setBounds(617, 40, 138, 30);
        pnlProducto.add(tfPrecioTotal);
        
        tfPrecio = new JTextField();
        tfPrecio.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			calculatePrecioTotal();
				}
        		
        	}
        });
        tfPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
        tfPrecio.setColumns(10);
        tfPrecio.setBounds(502, 40, 115, 30);
        pnlProducto.add(tfPrecio);
        
        tfProductoID = new JTextField();
        tfProductoID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				}
        	}
        });
        tfProductoID.setFont(new Font("Arial", Font.PLAIN, 14));
        tfProductoID.setBounds(6, 40, 98, 30);
        pnlProducto.add(tfProductoID);
        tfProductoID.setColumns(10);
        
        btnRemove = new JButton(" - ");
        btnRemove.setBounds(809, 40, 51, 30);
        pnlProducto.add(btnRemove);
        
        JScrollPane scrollProducto = new JScrollPane();
        scrollProducto.setBounds(6, 81, 854, 199);
        pnlProducto.add(scrollProducto);
        
        tbProductos = new JTable(itemTableModel);
        scrollProducto.setViewportView(tbProductos);
        
        tfCantidad = new JTextField();
        tfCantidad.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
        			tfPrecio.requestFocus();
        		}
        		
        	}
        });
        tfCantidad.setFont(new Font("Arial", Font.PLAIN, 14));
        tfCantidad.setColumns(10);
        tfCantidad.setBounds(104, 40, 98, 30);
        pnlProducto.add(tfCantidad);
        
        JLabel lblCantidad = new JLabel("CANTIDAD");
        lblCantidad.setBounds(110, 10, 98, 30);
        pnlProducto.add(lblCantidad);
        
        btnAdd = new JButton(" + ");
        btnAdd.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					
				}
        	}
        });
        btnAdd.setBounds(754, 40, 57, 30);
        pnlProducto.add(btnAdd);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Seleccione", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(6, 6, 888, 110);
        getContentPane().add(panel_3);
        panel_3.setLayout(null);
        
        JPanel pnlCliente = new JPanel();
        pnlCliente.setBounds(6, 18, 876, 82);
        panel_3.add(pnlCliente);
        pnlCliente.setLayout(null);
        
        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setBounds(6, 6, 74, 30);
        pnlCliente.add(lblProveedor);
        
        tfClienteID = new JTextField();
        tfClienteID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
        			showDialog(PROVEEDOR_CODE);
				}
        	}
        });
        tfClienteID.setText("0");
        tfClienteID.setToolTipText("Codigo del Cliente");
        tfClienteID.setBounds(81, 6, 90, 30);
        pnlCliente.add(tfClienteID);
        tfClienteID.setColumns(10);
        
        tfClienteNombre = new JTextField();
        tfClienteNombre.setBounds(81, 41, 329, 30);
        pnlCliente.add(tfClienteNombre);
        tfClienteNombre.setColumns(10);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(6, 41, 74, 30);
        pnlCliente.add(lblNombre);
        
        JLabel lblFBuscar = new JLabel("F11 -> Buscar");
        lblFBuscar.setBounds(170, 6, 90, 30);
        pnlCliente.add(lblFBuscar);
        
        tfTotalItems = new JTextField();
        tfTotalItems.setEditable(false);
        tfTotalItems.setColumns(10);
        tfTotalItems.setBounds(91, 445, 173, 30);
        getContentPane().add(tfTotalItems);
        
        JLabel lblCantItem = new JLabel("Cant. Item:");
        lblCantItem.setBounds(6, 445, 74, 30);
        getContentPane().add(lblCantItem);
        
        JLabel lblObs = new JLabel("Obs.:");
        lblObs.setBounds(317, 478, 51, 30);
        getContentPane().add(lblObs);
        
        tfObs = new JTextField();
        tfObs.setColumns(10);
        tfObs.setBounds(368, 478, 526, 30);
        getContentPane().add(tfObs);
        
        tfTotal = new JTextField();
        tfTotal.setEditable(false);
        tfTotal.setFont(new Font("Arial", Font.BOLD, 16));
        tfTotal.setForeground(Color.RED);
        tfTotal.setColumns(10);
        tfTotal.setBounds(91, 478, 173, 30);
        getContentPane().add(tfTotal);
        
        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(6, 478, 74, 30);
        getContentPane().add(lblTotal);
        
        JPanel panel = new JPanel();
        panel.setBounds(6, 520, 888, 38);
        getContentPane().add(panel);
        
        btnGuardar = new JButton("Guardar");
        panel.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel.add(btnCerrar);
    }
    
    public void setCliente(Optional<Cliente> cliente) {
    	if (cliente.isPresent()) {
    		String nombre = cliente.get().getRazonSocial();
    		tfClienteNombre.setText(nombre);
    		tfProductoID.requestFocus(); 
    	}
    }
    
    public void setProducto(Optional<Producto> producto) {
    	if (producto.isPresent()) {
    		String nombre = producto.get().getDescripcion();
    		String precio = String.valueOf(producto.get().getPrecioCosto());
    		tfDescripcion.setText(nombre);
    		tfPrecio.setText(precio);
    		
    		//cantidad default
    		tfCantidad.setText("1");
    		tfCantidad.requestFocus();
    	}
    }

    public Venta getVentaFrom() {
        Venta venta = new Venta();
        
    	venta.setComprobante("SIN COMPROBANTE");
//    	venta.setCondicion(tfCondPago.getText().isEmpty() ? 1 : Integer.valueOf(tfCondPago.getText()));   
//    	venta.setVendedor(new Usuario(tfVendedorID.getText().isEmpty() ? 1 : Long.valueOf(tfVendedorID.getText())));
    	venta.setCliente(new Cliente(tfClienteID.getText().isEmpty() ? 1 : Long.valueOf(tfClienteID.getText())));
    	venta.setClienteNombre(tfClienteNombre.getText());
//    	venta.setDeposito(new Deposito(tfDepositoID.getText().isEmpty() ? 1 : Long.valueOf(tfDepositoID.getText())));
//    	venta.setMoneda(new Moneda(1L));
    	venta.setSituacion("ACTIVO");
    	venta.setObs(tfObs.getText());
//    	venta.setCantItem(tfCondPago.getText().isEmpty() ? 1 : Integer.parseInt(tfCondPago.getText()));
    	
    	//totales
//    	venta.setTotalGravada10(tfSubtotal.getText().isEmpty() ? 0 : Double.valueOf(tfSubtotal.getText())); 	//SUBTOTAL
//    	venta.setTotalDescuento(tfDescuento.getText().isEmpty() ? 0 : Double.valueOf(tfDescuento.getText())); 	//DESCUENTO
    	venta.setTotalGeneral(tfTotal.getText().isEmpty() ? 0 : Double.valueOf(tfTotal.getText())); 			//TOTAL GENERAL
//    	venta.setTotalFlete(tfFlete.getText().isEmpty() ? 0 : Double.valueOf(tfFlete.getText()));				//FLETE

        return venta;
    }
    
    public VentaDetalle getItem() {
    	VentaDetalle item = new VentaDetalle();
    	item.setProductoId(Long.valueOf(tfProductoID.getText()));
    	item.setProducto(tfDescripcion.getText());
    	item.setCantidad(Double.valueOf(tfCantidad.getText()));
    	item.setPrecio(Double.valueOf(tfPrecio.getText()));
    	item.setSubtotal(Double.valueOf(tfPrecioTotal.getText()));
    	
    	return item;
    }
    
    public void clearItem() {
    	tfProductoID.setText("");
    	tfDescripcion.setText("");
    	tfCantidad.setText("");
    	tfPrecio.setText("");
    	tfPrecioTotal.setText("");
    	
    	tfProductoID.requestFocus();
    }
    
    public void setTotals(Double cantItem, Double total) {
//    	Double descuento = tfDescuento.getText().isEmpty() ? 0d : Double.valueOf(tfDescuento.getText());
//    	Double flete = tfFlete.getText().isEmpty() ? 0d : Double.valueOf(tfFlete.getText());
//    	Double totalGeneral = (total + flete) - descuento;
    	
//    	tfSubtotal.setText(String.valueOf(total));
    	tfTotal.setText(String.valueOf(total));
    	
    	if (cantItem != 0) {
    		tfTotalItems.setText(String.valueOf(cantItem));
		}
    }

    public void clearForm() {
    	tfClienteID.setText("");
        tfClienteNombre.setText("");
    	tfProductoID.setText("");
    	tfCantidad.setText("");
    	tfDescripcion.setText("");
    	tfPrecio.setText("");
    	tfPrecioTotal.setText("");
    	tfObs.setText("");
    	tfTotal.setText("");
    	tfTotalItems.setText("");
    	
    	while (itemTableModel.getRowCount() > 0) {
    		itemTableModel.removeRow(0);
		}
    	
    	tfClienteID.requestFocus();
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
    
    private void calculatePrecioTotal() {
    	Double cantidad = Double.valueOf(tfCantidad.getText());
    	Double precioUnit = Double.valueOf(tfPrecio.getText());
    	
    	Double precioTotal = cantidad * precioUnit;
    	
    	tfPrecioTotal.setText(String.valueOf(precioTotal));
    	
    	btnAdd.requestFocus();
    }
    
    private void showDialog(int code) {
    	switch (code) {
			case PROVEEDOR_CODE:
				proveedorDialog.setInterfaz(this);
		    	proveedorDialog.setVisible(true);	
				break;
			case PRODUCTO_CODE:
				productoDialog.setInterfaz(this);
				productoDialog.setVisible(true);
				break;
			default:
				break;
		}
    }

	@Override
	public void getEntity(Proveedor proveedor) {
		if (proveedor != null) {
			tfClienteID.setText(String.valueOf(proveedor.getId()));
			tfClienteNombre.setText(proveedor.getRazonSocial());
			
			tfProductoID.requestFocus();
		}
	}

	@Override
	public void getEntity(Producto producto) {
		if (producto != null) {
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfPrecio.setText(String.valueOf(producto.getPrecioCosto()));
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();
		}
	}

}
