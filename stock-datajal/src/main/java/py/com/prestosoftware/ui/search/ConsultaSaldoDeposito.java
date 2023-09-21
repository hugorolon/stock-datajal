package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.ProductoDeposito;
import py.com.prestosoftware.data.models.ProductoPrecio;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import net.miginfocom.swing.MigLayout;

@Component
public class ConsultaSaldoDeposito extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JTable tbProductos;
	private JScrollPane scrollPane;
	private JTable tbDeposito;
	private JScrollPane scrollPaneDeposito;
	private JTable tbPrecioIva;
	private JScrollPane scrollPanePrecioIva;
	private JPanel pnlPrecioIva;
	private JPanel pnlDeposito;
	
	private List<Producto> productos;
	
	private ProductTableModel tableModel;
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private ProductoService service;
	private DepositoService depositoService;

	@Autowired
	public ConsultaSaldoDeposito(ProductoService service, ProductTableModel tableModel, ProductoPrecioTableModel productoPrecioTableModel, 
			ProductoDepositoTableModel productoDepositoTableModel, DepositoService depositoService) {
		this.service = service;
		this.tableModel = tableModel;
		this.precioTableModel = productoPrecioTableModel;
		this.depositoTableModel = productoDepositoTableModel;
		this.depositoService = depositoService;
		
		setupUI();
		Util.setupScreen(this);
	}
	
	private void setupUI() {
		this.setTitle("CONSULTA SALDO DEPOSITOS");
		this.setSize(900, 500);
		getContentPane().setLayout(new MigLayout("", "[508px][364px]", "[49px][2px][194px][12px][204px]"));
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, "cell 0 0,grow");
		pnlBuscador.setLayout(null);
		
		JLabel lblBuscador = new JLabel("BUSCADOR");
		lblBuscador.setBounds(6, 5, 70, 38);
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
		((AbstractDocument) tfBuscador.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfBuscador.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfBuscador.selectAll();
			}
		});
		tfBuscador.setBounds(88, 5, 300, 38);
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			    	dispose();
			    } else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			    	tbProductos.requestFocus();
			    }
			}
		});
		pnlBuscador.add(tfBuscador);
		tfBuscador.setColumns(30);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(393, 5, 109, 38);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
			}
		});
		pnlBuscador.add(btnBuscar);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 2 1 3,grow");
		
		tbProductos = new JTable(tableModel);
		tbProductos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfBuscador.requestFocus();
				}
			}
		});
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                loadData(); 
            }
        });
		scrollPane.setViewportView(tbProductos);
		
		pnlDeposito = new JPanel();
		pnlDeposito.setBorder(new TitledBorder(null, "DEPOSITOS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlDeposito, "cell 1 0 1 3,grow");
		pnlDeposito.setLayout(null);
		
		scrollPaneDeposito = new JScrollPane();
		scrollPaneDeposito.setBounds(6, 18, 352, 215);
		pnlDeposito.add(scrollPaneDeposito);
		
		tbDeposito = new JTable(depositoTableModel);
		tbDeposito.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPaneDeposito.setViewportView(tbDeposito);
		
		pnlPrecioIva = new JPanel();
		pnlPrecioIva.setBorder(new TitledBorder(null, "PRECIO CON IVA", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlPrecioIva, "cell 1 4,grow");
		pnlPrecioIva.setLayout(null);
		
		scrollPanePrecioIva = new JScrollPane();
		scrollPanePrecioIva.setBounds(6, 18, 348, 173);
		pnlPrecioIva.add(scrollPanePrecioIva);
		
		tbPrecioIva = new JTable(precioTableModel);
		tbPrecioIva.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPanePrecioIva.setViewportView(tbPrecioIva);
	}
	
	public void loadProductos(String name) {
		if (name.isEmpty()) {
			productos = service.findAllByNombre();
		} else {
			productos = service.findByNombre(name);	
		}
		
        tableModel.clear();
        tableModel.addEntities(productos);
    }
	
	private void getStockProductosByDeposito(Producto p) {
		depositoTableModel.clear();
		
		if (p != null) {
			String deposito = depositoService.findById(1L).get().getNombre();
			
			if (p.getDepO1() != null) {
				ProductoDeposito dep01 = new ProductoDeposito(deposito, p.getDepO1());
				depositoTableModel.addEntity(dep01);
				
				String deposito2 = depositoService.findById(2L).get().getNombre();
				if (p.getDepO2() != null) {
					ProductoDeposito dep02 = new ProductoDeposito(deposito2, p.getDepO2());
					depositoTableModel.addEntity(dep02);
					
				}
			}	
		}
	}
	//TODO Buscar por codigo y referencia
	private void getPreciosByProducto(Producto p) {
		precioTableModel.clear();
		
		if (p != null) {
			if (p.getPrecioVentaA() != null) {
				ProductoPrecio precio01 = new ProductoPrecio("Precio A", (p.getPrecioVentaA() != null ? p.getPrecioVentaA():0));
				precioTableModel.addEntity(precio01);
			
				if (p.getPrecioVentaB() != null) {
					ProductoPrecio precio02 = new ProductoPrecio("Precio B", (p.getPrecioVentaB() != null ? p.getPrecioVentaB():0));
					precioTableModel.addEntity(precio02);
					
					if (p.getPrecioVentaC() != null) {
						ProductoPrecio precio03 = new ProductoPrecio("Precio C", (p.getPrecioVentaC() != null ? p.getPrecioVentaC():0));
						precioTableModel.addEntity(precio03);
						
					}
				}
			}	
		}
	}
	

	private void loadData() {
		int selectedRow = tbProductos.getSelectedRow();
        
		if (selectedRow != -1) {
			Long productoId = tableModel.getEntityByRow(selectedRow).getId();
			Producto p = service.getStockDepositoByProductoId(productoId);
			
			if (p != null) {
				getStockProductosByDeposito(p);
				getPreciosByProducto(p);
			}
	    }
	}

}
