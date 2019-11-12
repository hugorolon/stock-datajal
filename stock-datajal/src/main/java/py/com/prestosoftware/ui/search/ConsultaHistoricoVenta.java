package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
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
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class ConsultaHistoricoVenta extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProductoService service;
	private ProductTableModel tableModel;
	private ProductoInterfaz interfaz;
	
	private List<Producto> productos;
	private JLabel lblCodigo;
	private JTextField textField;
	private JLabel lblDescripcion;
	private JTextField textField_1;
	private JLabel lblGrupo;
	private JTextField textField_2;
	private JLabel lblSubgrupo;
	private JTextField textField_3;
	private JLabel lblMarca;
	private JTextField textField_4;
	private JLabel lblCtdXCj;
	private JTextField textField_5;
	private JLabel lblPeso;
	private JTextField textField_6;
	private JLabel lblReferencia;
	private JTextField textField_7;
	private JLabel lblUltCompra;
	private JTextField textField_8;
	private JLabel lblUltVenta;
	private JTextField textField_9;
	private JLabel lblMedioFob;
	private JTextField textField_10;
	private JLabel lblCostoFob;
	private JTextField textField_11;
	private JLabel lblMedioCif;
	private JLabel lblCostoCif;
	private JTextField textField_12;
	private JTextField textField_13;
	private JPanel panel;
	private JTextField textField_14;
	private JLabel lblTamao;

	@Autowired
	public ConsultaHistoricoVenta(ProductoService service, ProductTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("CONSULTA SALDO DEPOSITOS");
		this.setSize(737, 395);
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
		
		lblUltCompra = new JLabel("ULT. COMPRA");
		lblUltCompra.setBounds(6, 294, 95, 16);
		getContentPane().add(lblUltCompra);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(102, 289, 130, 26);
		getContentPane().add(textField_8);
		
		lblUltVenta = new JLabel("ULT. VENTA");
		lblUltVenta.setBounds(6, 327, 95, 16);
		getContentPane().add(lblUltVenta);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(102, 322, 130, 26);
		getContentPane().add(textField_9);
		
		lblMedioFob = new JLabel("MEDIO FOB");
		lblMedioFob.setBounds(244, 289, 95, 16);
		getContentPane().add(lblMedioFob);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(340, 284, 130, 26);
		getContentPane().add(textField_10);
		
		lblCostoFob = new JLabel("COSTO FOB");
		lblCostoFob.setBounds(244, 322, 95, 16);
		getContentPane().add(lblCostoFob);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(340, 317, 130, 26);
		getContentPane().add(textField_11);
		
		lblMedioCif = new JLabel("MEDIO CIF");
		lblMedioCif.setBounds(505, 289, 95, 16);
		getContentPane().add(lblMedioCif);
		
		lblCostoCif = new JLabel("COSTO CIF");
		lblCostoCif.setBounds(505, 322, 95, 16);
		getContentPane().add(lblCostoCif);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(601, 284, 130, 26);
		getContentPane().add(textField_12);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBounds(601, 317, 130, 26);
		getContentPane().add(textField_13);
		
		panel = new JPanel();
		panel.setBounds(6, 6, 725, 98);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblCodigo = new JLabel("COD.:");
		lblCodigo.setBounds(6, 5, 61, 26);
		panel.add(lblCodigo);
		
		textField = new JTextField();
		textField.setBounds(66, 5, 80, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		lblReferencia = new JLabel("REF.:");
		lblReferencia.setBounds(6, 36, 61, 26);
		panel.add(lblReferencia);
		
		textField_7 = new JTextField();
		textField_7.setBounds(66, 36, 140, 26);
		panel.add(textField_7);
		textField_7.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(66, 67, 140, 26);
		panel.add(textField_6);
		textField_6.setColumns(10);
		
		lblPeso = new JLabel("PESO:");
		lblPeso.setBounds(6, 68, 61, 26);
		panel.add(lblPeso);
		
		lblDescripcion = new JLabel("DESCRI:");
		lblDescripcion.setBounds(259, 12, 61, 16);
		panel.add(lblDescripcion);
		
		textField_1 = new JTextField();
		textField_1.setBounds(322, 5, 140, 26);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(322, 36, 140, 26);
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		lblCtdXCj = new JLabel("CTD X CJ:");
		lblCtdXCj.setBounds(259, 40, 61, 16);
		panel.add(lblCtdXCj);
		
		lblGrupo = new JLabel("GRUPO:");
		lblGrupo.setBounds(507, 12, 61, 16);
		panel.add(lblGrupo);
		
		textField_2 = new JTextField();
		textField_2.setBounds(579, 2, 140, 26);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		lblSubgrupo = new JLabel("SUBGRUPO:");
		lblSubgrupo.setBounds(488, 40, 80, 16);
		panel.add(lblSubgrupo);
		
		textField_3 = new JTextField();
		textField_3.setBounds(579, 30, 140, 26);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		lblMarca = new JLabel("MARCA:");
		lblMarca.setBounds(507, 68, 61, 16);
		panel.add(lblMarca);
		
		textField_4 = new JTextField();
		textField_4.setBounds(579, 67, 140, 26);
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBounds(322, 67, 140, 26);
		panel.add(textField_14);
		
		lblTamao = new JLabel("TAMAÑO");
		lblTamao.setBounds(259, 68, 61, 16);
		panel.add(lblTamao);
		
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
