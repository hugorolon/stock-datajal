package py.com.prestosoftware.ui.transactions;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.data.models.CompraDetalle;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.NotaCancelacionTableModel;
import py.com.prestosoftware.ui.viewmodel.Nota;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import org.jdesktop.swingx.JXDatePicker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class AnulacionBoletaPanel extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JTable tbNotas;
	private JScrollPane scrollPane;
	private JPanel tfCliente;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField tfDocumento;
	private JTextField tfClienteId;
	private JTextField textField_2;
	private JButton btnBuscar;
	private JLabel lblDesde;
	private JXDatePicker tfFechaInicial;
	private JLabel lblHasta;
	private JXDatePicker tfFechaFinal;
	private JButton btnLimpiar;
	
	private NotaCancelacionTableModel notaModel;
	private CompraService compraService;
	private VentaService ventaService;
	private UsuarioService usuarioService;
	private ProductoService productoService;
	
	@Autowired
	public AnulacionBoletaPanel(CompraService compraService, VentaService ventaService,
		UsuarioService usuarioService, NotaCancelacionTableModel notaModel, ProductoService productoService) {
		this.compraService = compraService;
		this.usuarioService = usuarioService;
		this.ventaService = ventaService;
		this.notaModel = notaModel;
		this.productoService = productoService;
		
		setupIU();
		
		Util.setupScreen(this);
	}
	
	private void setupIU() {
		getContentPane().setLayout(null);
		setTitle("BOLETAS PENDIENTES");
		setSize(900, 550);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 104, 888, 418);
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
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					openTransaction();
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 dispose();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cancelarNota();
				}
			}
		});
		scrollPane.setViewportView(tbNotas);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Filtros de Busquedas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(0, 4, 900, 90);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		tfCliente = new JPanel();
		tfCliente.setBounds(6, 18, 888, 66);
		panel_1.add(tfCliente);
		tfCliente.setLayout(null);
		
		lblNewLabel = new JLabel("DOCUMENTO:");
		lblNewLabel.setBounds(6, 6, 110, 26);
		tfCliente.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("CLIENTE:");
		lblNewLabel_1.setBounds(6, 34, 110, 26);
		tfCliente.add(lblNewLabel_1);
		
		tfDocumento = new JTextField();
		tfDocumento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		tfDocumento.setBounds(111, 6, 110, 26);
		tfCliente.add(tfDocumento);
		tfDocumento.setColumns(10);
		
		tfClienteId = new JTextField();
		tfClienteId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		tfClienteId.setBounds(111, 34, 110, 26);
		tfCliente.add(tfClienteId);
		tfClienteId.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(226, 34, 317, 26);
		tfCliente.add(textField_2);
		textField_2.setColumns(10);
		
		btnBuscar = new JButton("FILTRAR");
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					filtrar();
				}
			}
		});
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscar.setBounds(543, 34, 94, 29);
		tfCliente.add(btnBuscar);
		
		lblDesde = new JLabel("DESDE:");
		lblDesde.setBounds(233, 6, 54, 26);
		tfCliente.add(lblDesde);
		
		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setBounds(288, 6, 94, 26);
		tfCliente.add(tfFechaInicial);
		
		lblHasta = new JLabel("HASTA:");
		lblHasta.setBounds(394, 6, 54, 26);
		tfCliente.add(lblHasta);
		
		tfFechaFinal = new JXDatePicker();
		tfFechaFinal.setBounds(449, 6, 94, 26);
		tfCliente.add(tfFechaFinal);
		
		btnLimpiar = new JButton("LIMPIAR");
		btnLimpiar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					clearInputs();
				}
			}
		});
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInputs();
			}
		});
		btnLimpiar.setBounds(636, 34, 94, 29);
		tfCliente.add(btnLimpiar);
		
	}
	
	private void cancelarNota() {
		int selectedRow = tbNotas.getSelectedRow();
		
		if (selectedRow != -1) {
        	Nota nota = notaModel.getEntityByRow(selectedRow);
        	
        	switch (nota.getNota()) {
				case "COMPRAS":
					Optional<Compra> compra = compraService.findById(nota.getNotaNro());
					
					if (compra.isPresent()) {
						Compra c = compra.get();
						
						Integer respuesta = JOptionPane.showConfirmDialog(null, "Confirma Cancelacion");
						if (respuesta == 0) {
							c.setSituacion("CANCELADO");
							compraService.save(c);
							
							updateStockCompra(c.getItems(), c.getDeposito().getId());
						}
					}
					
					break;
				case "VENTAS":
					Optional<Venta> venta = ventaService.findById(nota.getNotaNro());
					
					if (venta.isPresent()) {
						Venta v = venta.get();
						
						Integer respuesta = JOptionPane.showConfirmDialog(null, "Confirma Cancelacion");
						if (respuesta == 0) {
							v.setSituacion("CANCELADO");
							ventaService.save(v);
							
							updateStock(v.getItems(), v.getDeposito().getId());
						}
					}
					
					break;
				default:
					break;
			}
        	
        	getNotasPendientes();
        }
	}
	
	//TODO Refactorizar, el update de Stock se puede hacer en un metodo static
	private void updateStock(List<VentaDetalle> items, Long depositoId) {
		List<Producto> productos = new ArrayList<>();
		for (VentaDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();
				
				int depID = Integer.parseInt(String.valueOf(depositoId));
				Double salidaPend = p.getSalidaPend() != null ?  p.getSalidaPend():0;
				Double cantItem = e.getCantidad();
				
				//Stock de Depositos
				Double cant = p.getDepO1() != null ?  p.getDepO1():0;
				Double cant02 = p.getDepO2() != null ?  p.getDepO2():0;
				
				//refactorizar
				switch (depID) {
					case 1:
						p.setDepO1(cant + cantItem);
						p.setSalidaPend(salidaPend - cantItem);
						break;
					case 2:
						p.setDepO2(cant02 + cantItem);
						p.setSalidaPend(salidaPend - cantItem);
						break;
					default:
						break;
				}
				
				productos.add(p);
			}
		}
		productoService.updateStock(productos);
	}
	
	private void updateStockCompra(List<CompraDetalle> items, Long depositoId) {
		List<Producto> productos = new ArrayList<>();

		for (CompraDetalle e : items) {
			Optional<Producto> pOptional = productoService.findById(e.getProductoId());

			if (pOptional.isPresent()) {
				Producto p = pOptional.get();
				
				int depID = Integer.parseInt(String.valueOf(depositoId));
				Double entradaPend = p.getEntPendiente() != null ?  p.getEntPendiente():0;
				Double cantItem = e.getCantidad();
				
				//Stock de Depositos
				Double cant = p.getDepO1() != null ?  p.getDepO1():0;
				Double cant02 = p.getDepO2() != null ?  p.getDepO2():0;
				
				//refactorizar
				switch (depID) {
					case 1:
						p.setDepO1(cant + cantItem);
						p.setEntPendiente(entradaPend - cantItem);
						break;
					case 2:
						p.setDepO2(cant02 + cantItem);
						p.setEntPendiente(entradaPend - cantItem);
						break;
					default:
						break;
				}
				
				productos.add(p);
			}			
		}

		productoService.updateStock(productos);
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
		
	}
	
	private void openVenta(Long id) {
		//CompraPanel compraPanel = new CompraPanel(itemTableModel, gastoTableModel, proveedorDialog, depositoDialog, monedaDialog, productoDialog, compraService, proveedorService, monedaService, depositoService, compraValidator, productoService)
		//VentaPanel venta = new VentaPanel();
	}
	
	public void getNotasPendientes() {
    	String situacion = "PENDIENTE"; 
    	List<Nota> notas = new ArrayList<>();
    	List<Venta> ventas = ventaService.getNotasPendientesBySituacion(situacion);
    	List<Compra> compras = compraService.getNotasPendientesBySituacion(situacion);
    	
    	for (Venta v : ventas) {
    		Optional<Usuario> u = usuarioService.findById(v.getVendedor().getId());
    		
    		if (u.isPresent()) {
    				notas.add(new Nota("1", "VENTAS", v.getId(), v.getCliente().getId(), v.getClienteNombre(), v.getTotalGeneral(),
    						u.get().getId(), u.get().getUsuario(), u.get().getEmpresa().getNombre(), v.getFecha()));
			}			
		}
    
    	for (Compra c : compras) {
    		Optional<Usuario> u = usuarioService.findById(c.getUsuario().getId());
    		
    		if (u.isPresent()) { 
    			notas.add(new Nota("2", "COMPRAS", c.getId(), c.getProveedor().getId(), c.getProveedorNombre(), c.getTotalGeneral(),
    					u.get().getId(), u.get().getUsuario(), u.get().getEmpresa().getNombre(), c.getFecha()));
    		}
		}
    	
    	notaModel.clear();
    	notaModel.addEntities(notas);
    }
	
	private void filtrar() {
		
	}
	
	private void clearInputs() {
		
	}
	
}
