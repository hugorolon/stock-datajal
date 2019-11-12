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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.ui.table.ProductTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;

@Component
public class HistoricoVenta extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProductoService service;
	private ProductTableModel tableModel;
	private ProductoInterfaz interfaz;
	
	private List<Producto> productos;
	private JLabel lblPeso;
	private JLabel lblUltCompra;
	private JTextField textField_8;
	private JLabel lblMedioFob;
	private JTextField textField_10;
	private JLabel lblMedioCif;
	private JTextField textField_12;
	private JPanel panel;

	@Autowired
	public HistoricoVenta(ProductoService service, ProductTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("HISTORICO DE VENTAS");
		this.setSize(737, 351);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 105, 725, 160);
		getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					aceptar();
				} else if (e.getKeyCode()==KeyEvent.VK_INSERT) {
					aceptar();
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 //tfBuscador.requestFocus();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		lblUltCompra = new JLabel("CANT. VENTA");
		lblUltCompra.setBounds(6, 294, 95, 16);
		getContentPane().add(lblUltCompra);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(102, 289, 130, 26);
		getContentPane().add(textField_8);
		
		lblMedioFob = new JLabel("CANT. PRES.");
		lblMedioFob.setBounds(244, 289, 95, 16);
		getContentPane().add(lblMedioFob);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(340, 284, 130, 26);
		getContentPane().add(textField_10);
		
		lblMedioCif = new JLabel("GENERAL");
		lblMedioCif.setBounds(505, 289, 95, 16);
		getContentPane().add(lblMedioCif);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(601, 284, 130, 26);
		getContentPane().add(textField_12);
		
		panel = new JPanel();
		panel.setBounds(6, 6, 725, 42);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblPeso = new JLabel("OP, NOTA, COD CLIENTE, CLIENTE, PRECIO, CANTIDAD");
		lblPeso.setBounds(6, 6, 534, 26);
		panel.add(lblPeso);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		loadClients("");	
	}
	
	private void loadClients(String name) {
		if (name.isEmpty()) {
			productos = service.findAll();
		} else {
			productos = service.findByNombre(name);
		}
		
        tableModel.clear();
        tableModel.addEntities(productos);
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
}
