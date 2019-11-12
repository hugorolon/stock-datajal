package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.PlanCuentaService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.NotaTableModel;
import py.com.prestosoftware.ui.viewmodel.Nota;
import py.com.prestosoftware.util.Notifications;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ConsultaBoletaDialog extends JDialog implements ClienteInterfaz, 
	ProveedorInterfaz, VentaInterfaz, CompraInterfaz {
	
	private static final long serialVersionUID = 1L;
	
	private static final int VENTA_CODE = 1;
	private static final int COMPRA_CODE = 2;
	private static final int DEVOLUCION_CLIENTE = 4;
	private static final int DEVOLUCION_PROVEEDOR = 5;
	private static final int TRANSFERENCIA_CODE = 7;
	private static final int SOLICITUD_CODE = 8;
	private static final int PRESUPUESTO_CODE = 9;
	private static final int AJUSTE_ENTRADA_CODE = 10;
	private static final int AJUSTE_SALIDA_CODE = 11;
	
	private static final int CLIENTE_CODE = 15;
	private static final int PROVEEDOR_CODE = 20;
	
	private JTextField tfDocumento;
	private JTable tbNotas;
	private JScrollPane scrollPane;
	private JTextField tfOperacion;
	private JTextField lblOperacion;
	private JLabel lblDoc;
	private JTextField tfProveedorId;
	private JLabel lblProveedor;
	private JTextField tfProveedor;
	private JLabel lblCliente;
	private JTextField tfClienteId;
	private JTextField tfCliente;
	
	private PlanCuentaService planService;
	private CompraService compraService;
	private VentaService ventaService;
	private ClienteService clienteService;
	private ProveedorService proveedorService;
	private NotaTableModel notaModel;
	
	private ClienteDialog clienteDialog;
	private ProveedorDialog proveedorDialog;
	private VentaDialog ventaDialog;
	private CompraDialog compraDialog;
	
	@Autowired
	public ConsultaBoletaDialog(PlanCuentaService planService, CompraService compraService,
			VentaService ventaService, NotaTableModel notaModel, ClienteDialog clienteDialog, 
			ProveedorDialog proveedorDialog, VentaDialog ventaDialog, CompraDialog compraDialog,
			ClienteService clienteService, ProveedorService proveedorService) {
		this.planService = planService;
		this.compraService = compraService;
		this.ventaService = ventaService;
		this.notaModel = notaModel;
		this.clienteDialog = clienteDialog;
		this.proveedorDialog = proveedorDialog;
		this.compraDialog = compraDialog;
		this.ventaDialog = ventaDialog;
		this.clienteService = clienteService;
		this.proveedorService = proveedorService;
		
		setTitle("BUSCADOR DE BOLETAS");
		setSize(900, 550);

		setupUI();
		
		Util.setupScreen(this);
		
		getNotasPendientes();
	}
	
	private void setupUI() {
		getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(6, 6, 888, 93);
		getContentPane().add(pnlBuscador);
		
		JLabel lblBuscador = new JLabel("OPERACION:");
		lblBuscador.setBounds(6, 10, 105, 38);
		
		tfDocumento = new JTextField();
		tfDocumento.setBounds(531, 10, 115, 38);
		tfDocumento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfDocumento.getText().isEmpty()) {
						findBoleta();
					} else {
						if (VENTA_CODE == Integer.parseInt(tfOperacion.getText())) {
							tfClienteId.requestFocus();
						} else if (COMPRA_CODE == Integer.parseInt(tfOperacion.getText())) {
							tfProveedorId.requestFocus();
						}
					}
			    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearInputs();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		
		tfOperacion = new JTextField();
		tfOperacion.setBounds(94, 10, 115, 38);
		tfOperacion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Long id = Long.valueOf(tfOperacion.getText());
					findOperacionById(id);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearInputs();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfDocumento.setColumns(30);
		
		lblOperacion = new JTextField("");
		lblOperacion.setEditable(false);
		lblOperacion.setBounds(207, 10, 216, 38);
		
		
		lblDoc = new JLabel("DOCUMENTO:");
		lblDoc.setBounds(436, 10, 94, 38);
		
		tfProveedorId = new JTextField();
		tfProveedorId.setBounds(94, 48, 115, 38);
		tfProveedorId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProveedorId.getText().isEmpty()) {
						findNotasByProveedorId();
					} else {
						showDialog(PROVEEDOR_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearInputs();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfProveedorId.setColumns(10);
		
		lblProveedor = new JLabel("PROVEEDOR:");
		lblProveedor.setBounds(6, 48, 105, 38);
		
		tfProveedor = new JTextField("");
		tfProveedor.setEditable(false);
		tfProveedor.setBounds(207, 48, 216, 38);
		
		lblCliente = new JLabel("CLIENTE:");
		lblCliente.setBounds(437, 48, 94, 38);
		
		tfClienteId = new JTextField();
		tfClienteId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfClienteId.getText().isEmpty()) {
						findNotasByClienteId();
					} else {
						showDialog(CLIENTE_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearInputs();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfClienteId.setBounds(531, 48, 115, 38);
		tfClienteId.setColumns(10);
		pnlBuscador.setLayout(null);
		
		tfCliente = new JTextField("");
		tfCliente.setEditable(false);
		tfCliente.setBounds(644, 48, 238, 38);
		pnlBuscador.add(tfCliente);
		pnlBuscador.add(lblBuscador);
		pnlBuscador.add(lblProveedor);
		pnlBuscador.add(tfOperacion);
		pnlBuscador.add(tfProveedorId);
		pnlBuscador.add(lblOperacion);
		pnlBuscador.add(tfProveedor);
		pnlBuscador.add(lblCliente);
		pnlBuscador.add(lblDoc);
		pnlBuscador.add(tfDocumento);
		pnlBuscador.add(tfClienteId);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 111, 888, 411);
		getContentPane().add(scrollPane);
		
		tbNotas = new JTable(notaModel) {
        	public boolean isCellEditable(int fila, int columna) {
				return false;
			}
        };
        tbNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbNotas.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        tbNotas.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		openTransaction();
        	}
        });
		tbNotas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					openTransaction();
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 tfDocumento.requestFocus();
				}
			}
		});
		scrollPane.setViewportView(tbNotas);
		
	}
	
	private void findOperacionById(Long id) {
		Optional<PlanCuenta> operacion = planService.findById(id);
		
		if (operacion.isPresent()) {
			lblOperacion.setText(operacion.get().getNombre());
			tfDocumento.requestFocus();
		} else {
			Notifications.showAlert("No existe Operacion con este codigo.!");
		}
	}
	
	private void findBoleta() {
		List<Nota> notas = new ArrayList<>();
		if (!tfOperacion.getText().isEmpty() && !tfDocumento.getText().isEmpty()) {
			notaModel.clear();
			
			int operacion = Integer.valueOf(tfOperacion.getText());
			switch (operacion) {
				case VENTA_CODE:
					Optional<Venta> v = ventaService.findById(Long.valueOf(tfDocumento.getText()));
					
					if (v.isPresent()) {
						Venta venta = v.get();
						notas.add(new Nota("VENTAS", venta.getId(), venta.getClienteNombre(), venta.getTotalGeneral()));
					}
					break;
				case COMPRA_CODE:
					Optional<Compra> c = compraService.findById(Long.valueOf(tfDocumento.getText()));
					
					if (c.isPresent()) {
						Compra compra = c.get();
						notas.add(new Nota("COMPRAS", compra.getId(), compra.getProveedorNombre(), compra.getTotalGeneral()));
					}
					break;
				case DEVOLUCION_CLIENTE:
					break;
				case DEVOLUCION_PROVEEDOR:
					break;
				case TRANSFERENCIA_CODE:
					break;
				case SOLICITUD_CODE:
					break;
				case PRESUPUESTO_CODE:
					break;
				case AJUSTE_ENTRADA_CODE:
					break;
				case AJUSTE_SALIDA_CODE:
					break;
			}

	    	notaModel.addEntities(notas);
		} else {
			//buscar por documento nada mas
			Notifications.showAlert("Debes digitar la Operacion y el numero de Documento");
		}
	}
	
	private void openTransaction() {
		int selectedRow = tbNotas.getSelectedRow();
        
        if (selectedRow != -1) {
        	Nota nota = notaModel.getEntityByRow(selectedRow);
        	
        	switch (nota.getNota()) {
				case "COMPRAS":
					openCompra(nota.getNotaNro());
					break;
				case "VENTAS":
					openVenta(nota.getNotaNro());
					break;
	
				default:
					break;
			}
        	
        }
		
	}
	
	private void openCompra(Long id) {
		//VentaPanel venta = new VentaPanel();
	}
	
	private void openVenta(Long id) {
		//CompraPanel compraPanel = new CompraPanel(itemTableModel, gastoTableModel, proveedorDialog, depositoDialog, monedaDialog, productoDialog, compraService, proveedorService, monedaService, depositoService, compraValidator, productoService)
	}
	
	private void findNotasByProveedorId() {
		if (!tfProveedorId.getText().isEmpty()) {
			Optional<Proveedor> proveedor = proveedorService.findById(Long.valueOf(tfProveedorId.getText()));
			
			if (proveedor.isPresent()) {
				tfProveedor.setText(proveedor.get().getNombre());
				
				notaModel.clear();
				List<Nota> notas = new ArrayList<>();
				
				if (!tfDocumento.getText().isEmpty()) {
					Optional<Compra> c = compraService.findByCompraIdAndProveedorId(Long.valueOf(tfDocumento.getText()), Long.valueOf(tfProveedorId.getText()));
				
					if (c.isPresent()) {
						notas.add(new Nota("COMPRAS", c.get().getId(), c.get().getProveedorNombre(), c.get().getTotalGeneral()));
					}
				} else {
					List<Compra> compras = compraService.findByProveedorId(Long.valueOf(tfProveedorId.getText()));
					
					if (!compras.isEmpty()) {
						for (Compra e : compras) {
							notas.add(new Nota("COMPRAS", e.getId(), e.getProveedorNombre(), e.getTotalGeneral()));
						}
					}
				}
				
		    	notaModel.addEntities(notas);
			} else {
				Notifications.showAlert("Proveedor no existe con este codigo.!");
			}
		}
	}
	
	private void findNotasByClienteId() {
		if (!tfClienteId.getText().isEmpty()) {
			
			Optional<Cliente> cliente = clienteService.findById(Long.valueOf(tfClienteId.getText()));
			
			if (cliente.isPresent()) {
				tfCliente.setText(cliente.get().getNombre());
				
				notaModel.clear();
				List<Nota> notas = new ArrayList<>();
				
				if (!tfDocumento.getText().isEmpty()) {
					Optional<Venta> v = ventaService.findByVentaIdAndClienteId(Long.valueOf(tfDocumento.getText()), Long.valueOf(tfProveedorId.getText()));
					
					if (v.isPresent()) {
						notas.add(new Nota("VENTAS", v.get().getId(), v.get().getClienteNombre(), v.get().getTotalGeneral()));
					}
				} else {
					List<Venta> ventas = ventaService.findByClienteId(Long.valueOf(tfClienteId.getText()));
					
					if (!ventas.isEmpty()) {
						for (Venta e : ventas) {
							notas.add(new Nota("VENTAS", e.getId(), e.getClienteNombre(), e.getTotalGeneral()));	
						}
					}
				}
				
				notaModel.addEntities(notas);
			} else {
				Notifications.showAlert("Cliente no existe con este codigo.!");
			}
		}
	}
	
	private void getNotasPendientes() {
    	String situacion = "PENDIENTE"; 
    	List<Nota> notas = new ArrayList<>();
    	List<Venta> ventas = ventaService.getNotasPendientesByFechaAndSituacion(new Date(), situacion);
    	List<Compra> compras = compraService.getNotasPendientes(situacion); //new Date()
    	
    	ventas.forEach(v -> {
    		notas.add(new Nota("VENTAS", v.getId(), v.getClienteNombre(), v.getTotalGeneral()));
    	});
    
    	compras.forEach(c -> {
    		notas.add(new Nota("COMPRAS", c.getId(), c.getProveedorNombre(), c.getTotalGeneral()));
    	});
    	
    	notaModel.clear();
    	notaModel.addEntities(notas);
    }
	
	private void clearInputs() {
		tfOperacion.setText("");
		lblOperacion.setText("");
		tfDocumento.setText("");
		tfProveedorId.setText("");
		tfProveedor.setText("");
		tfClienteId.setText("");
		tfCliente.setText("");
		
		while (notaModel.getRowCount() > 0) {
			notaModel.removeRow(0);
		}
	}
	
//	private void findClienteById(long id) {
//		Optional<Cliente> cliente = clienteService.findById(id);
//		
//		if (cliente.isPresent()) {
//			String nombre = cliente.get().getNombre();
//			tfCliente.setText(nombre);
//		}
//	}
//	
//	private void findProveedorById(long id) {
//		Optional<Proveedor> proveedor = proveedorService.findById(id);
//		
//		if (proveedor.isPresent()) {
//			String nombre = proveedor.get().getNombre();
//			tfProveedor.setText(nombre);
//		}
//	}
	
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
//			case DEVOLUCION_CLIENTE:
//				depositoDialog.setInterfaz(this);
//				depositoDialog.setVisible(true);
//				break;
//			case DEVOLUCION_PROVEEDOR:
//				productoDialog.setInterfaz(this);
//				productoDialog.setVisible(true);
//				break;
//			case TRANSFERENCIA_CODE:
//				productoDialog.setInterfaz(this);
//				productoDialog.setVisible(true);
//				break;
//			case SOLICITUD_CODE:
//				productoDialog.setInterfaz(this);
//				productoDialog.setVisible(true);
//				break;
//			case PRESUPUESTO_CODE:
//				productoDialog.setInterfaz(this);
//				productoDialog.setVisible(true);
//				break;
//			case AJUSTE_ENTRADA_CODE:
//				productoDialog.setInterfaz(this);
//				productoDialog.setVisible(true);
//				break;
//			case AJUSTE_SALIDA_CODE:
//				productoDialog.setInterfaz(this);
//				productoDialog.setVisible(true);
//				break;
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

	@Override
	public void getEntity(Compra compra) {
		if (compra != null) {
			tfDocumento.setText(String.valueOf(compra.getId()));
		}
	}

	@Override
	public void getEntity(Venta venta) {
		if (venta != null) {
			tfDocumento.setText(String.valueOf(venta.getId()));
		}
	}

	@Override
	public void getEntity(Proveedor proveedor) {
		if (proveedor != null) {
			tfProveedorId.setText(String.valueOf(proveedor.getId()));
			tfProveedor.setText(proveedor.getNombre());
		}
	}

	@Override
	public void getEntity(Cliente cliente) {
		if (cliente != null) {
			tfClienteId.setText(String.valueOf(cliente.getId()));
			tfCliente.setText(cliente.getNombre());
		}
	}
}
