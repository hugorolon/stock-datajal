package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.ProductTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.FlowLayout;

@Component
public class ProductoDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProductoService service;
	private ProductTableModel tableModel;
	private ProductoInterfaz interfaz;
	
	private List<Producto> productos;
	
	private ConsultaCompraDialog consultaCompraDialog;
	private ConsultaVentasDialog consultaVentaDialog;
	private ConsultaPrecioProducto consultaPrecioProducto;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JLabel label;

	@Autowired
	public ProductoDialog(ProductoService service, 
			ProductTableModel tableModel,
			ConsultaCompraDialog consultaCompraDialog,
			ConsultaVentasDialog consultaVentaDialog,
			ConsultaPrecioProducto consultaPrecioProducto) {
		this.service = service;
		this.tableModel = tableModel;
		this.consultaCompraDialog = consultaCompraDialog;
		this.consultaVentaDialog = consultaVentaDialog;
		this.consultaPrecioProducto = consultaPrecioProducto;
		
		setTitle("LISTA DE STOCK");
		setSize(900, 500);
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 900, 35);
		getContentPane().add(pnlBuscador);
		
		JLabel lblBuscador = new JLabel("Buscador");
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
		tfBuscador.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfBuscador.selectAll();
			}
		});
		((AbstractDocument) tfBuscador.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
					
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			    	dispose();
			    } else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			    	table.requestFocus();
			    } else if (e.getKeyCode()==KeyEvent.VK_F5) {
			    	
			    }
			}
		});
		pnlBuscador.add(tfBuscador);
		tfBuscador.setColumns(30);
		
		btnBuscar = new JButton("Buscar");
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
		scrollPane.setBounds(0, 35, 614, 408);
		getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					aceptar();
				} else if (e.getKeyCode()==KeyEvent.VK_INSERT) {
					aceptar();
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 tfBuscador.requestFocus();
				} else if (e.getKeyCode()==KeyEvent.VK_F4) { //PRECIOS
					consultaPrecioProducto.setDataProducto(getProducto());
					consultaPrecioProducto.setVisible(true);
			    } else if (e.getKeyCode()==KeyEvent.VK_F5) { //HISTORICO DE VENTA
			    	consultaVentaDialog.setProductoId(getProductoId());
			    	consultaVentaDialog.setVisible(true);
			    } else if (e.getKeyCode()==KeyEvent.VK_F6) { //FOTOS
			    	
			    } else if (e.getKeyCode()==KeyEvent.VK_F7) { //HISTORICO DE COMPRA
			    	consultaCompraDialog.setProductoId(getProductoId());
			    	consultaCompraDialog.setVisible(true);
			    }
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(0, 443, 900, 35);
		getContentPane().add(pnlBotonera);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					aceptar();
				}
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
		pnlBotonera.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotonera.add(btnCancelar);
		
		panel = new JPanel();
		panel.setBounds(626, 35, 262, 197);
		getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setSize(0, 30);
		panel.add(lblNewLabel);
		
		panel_1 = new JPanel();
		panel_1.setBounds(626, 244, 262, 199);
		getContentPane().add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		label = new JLabel("");
		panel_1.add(label);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		loadProductos("");	
	}
	
	private void loadProductos(String filter) {
		if (filter.isEmpty()) {
			productos = service.findAll();
		} else {
			//productos = service.findByNombre(name);
			productos = service.findProductByFilter(filter);
		}
		
        tableModel.clear();
        tableModel.addEntities(productos);
        table.requestFocus();
    }
	
	public ProductoInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(ProductoInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(productos.get(c));         
	    }
		dispose();
	}
	
	private Producto getProducto() {
		return productos.get(table.getSelectedRow());         
	}
	
	public Long getProductoId() {
		return productos.get(table.getSelectedRow()).getId();         
	}

	public void getProductos() {
		loadProductos("");
	}
}
