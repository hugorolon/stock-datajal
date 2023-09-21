package py.com.prestosoftware.ui.transactions;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXDatePicker;
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
import py.com.prestosoftware.util.Notifications;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class EntregaBoletaPanel extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JTable tbNotas;
	private JScrollPane scrollPane;
	
	private NotaCancelacionTableModel notaModel;
	private CompraService compraService;
	private VentaService ventaService;
	private UsuarioService usuarioService;
	private ProductoService productoService;
	private JTextField tfClienteId;
	private JTextField tfDocumento;
	private JTextField tfClienteNombre;
	private JXDatePicker tfFechaInicial;
	private JXDatePicker tfFechaFinal;
	private JButton btnFiltrar;
	private JButton btnLimpiar;
	
	
	@Autowired
	public EntregaBoletaPanel(CompraService compraService, VentaService ventaService,
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
		scrollPane.setBounds(6, 135, 888, 387);
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Filtros de Busqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(4, 13, 876, 121);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 16, 864, 81);
		panel_1.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CLIENTE:");
		lblNewLabel.setBounds(23, 36, 80, 14);
		panel.add(lblNewLabel);
		
		JLabel lblDesde = new JLabel("DESDE:");
		lblDesde.setBounds(283, 11, 46, 14);
		panel.add(lblDesde);
		
		JLabel lblDocumento = new JLabel("DOCUMENTO:");
		lblDocumento.setBounds(23, 11, 80, 14);
		panel.add(lblDocumento);
		
		JLabel lblHasta = new JLabel("HASTA:");
		lblHasta.setBounds(456, 14, 46, 14);
		panel.add(lblHasta);
		
		tfClienteId = new JTextField();
		tfClienteId.setBounds(113, 33, 46, 20);
		panel.add(tfClienteId);
		tfClienteId.setColumns(10);
		
		tfDocumento = new JTextField();
		tfDocumento.setColumns(10);
		tfDocumento.setBounds(113, 8, 86, 20);
		panel.add(tfDocumento);
		
		tfClienteNombre = new JTextField();
		tfClienteNombre.setBounds(169, 33, 160, 20);
		panel.add(tfClienteNombre);
		tfClienteNombre.setColumns(10);
		
		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setBounds(339, 8, 86, 20);
		panel.add(tfFechaInicial);
		
		tfFechaFinal = new JXDatePicker();
		tfFechaFinal.setBounds(499, 11, 86, 20);
		panel.add(tfFechaFinal);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}
		});
		btnFiltrar.setBounds(397, 47, 89, 23);
		panel.add(btnFiltrar);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLimpiar.setBounds(496, 47, 89, 23);
		panel.add(btnLimpiar);
		
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
        	
        	getNotasPagadas();
        }
	}
	
	private void filtrar() {
		if (tfDocumento.getText().isEmpty()) {
			Notifications.showAlert("Debes digitar documento");
			return;
		}
		
		Long documento = Long.valueOf(tfDocumento.getText());
		Optional<Venta> venta = ventaService.findById(documento);
//		Long documento = Long.valueOf(tfDocumento.getText());
//		Long.valueOf(
//		venta = ventaService.findByClienteId(id)(tfClienteId.getText());
		
		if (venta.isPresent()) {
			Venta v = venta.get();
			
			notaModel.clear();
			notaModel.addEntity(new Nota("1", "VENTAS", v.getId(), 
					v.getCliente().getId(), v.getClienteNombre(), v.getTotalGeneral(),
    				v.getVendedor().getId(), 	v.getVendedor().getUsuario(), 
    				v.getVendedor().getEmpresa().getNombre(), v.getFecha()));
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
	
	public void getNotasPagadas() {
    	String situacion = "PAGADO"; 
    	List<Nota> notas = new ArrayList<>();
    	List<Venta> ventas = ventaService.getNotasPendientesBySituacion(situacion);
    	
    	ventas.forEach(v -> {
    		
    		Optional<Usuario> u = usuarioService.findById(v.getVendedor().getId());
    		
    		if (u.isPresent()) {
    				notas.add(new Nota("1", "VENTAS", v.getId(), v.getCliente().getId(), v.getClienteNombre(), v.getTotalGeneral(),
    						u.get().getId(), u.get().getUsuario(), u.get().getEmpresa().getNombre(), v.getFecha()));
			}
    	});

    	
    	notaModel.clear();
    	notaModel.addEntities(notas);
    }
}
