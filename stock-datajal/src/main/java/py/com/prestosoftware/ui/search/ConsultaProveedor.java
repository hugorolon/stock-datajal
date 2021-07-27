package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.ProveedorConsultaTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ConsultaProveedor extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProveedorService service;
	private ProveedorConsultaTableModel tableModel;
	private ProveedorInterfaz interfaz;
	
	private List<Proveedor> proveedores;

	@Autowired
	public ConsultaProveedor(ProveedorService service, ProveedorConsultaTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;
		
		setupUI();
	}
	
	private void setupUI() {
		setTitle("CONSULTA PROVEEDORES");
		setSize(900, 500);
		setModal(true);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		
		JLabel lblBuscador = new JLabel("Buscador");
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
		((AbstractDocument) tfBuscador.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				textField.setText(text.toUpperCase());
				DefaultTableModel table1 = (DefaultTableModel) table.getModel();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table1);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter("(?i)" + textField.getText()));
			}
		});
		pnlBuscador.add(tfBuscador);
		tfBuscador.setColumns(30);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getProveedores(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
					getProveedores(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
			}
		});
		pnlBuscador.add(btnBuscar);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					aceptar();
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 tfBuscador.requestFocus();
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable jTable = (JTable) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
					aceptar();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel pnlBotonera = new JPanel();
		getContentPane().add(pnlBotonera, BorderLayout.SOUTH);
		
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
	}
	
	public void getProveedores(String name) {
		if (name.isEmpty()&&proveedores==null) {
			proveedores = service.findAll();
		}
		
        tableModel.clear();
        tableModel.addEntities(proveedores);
    }
	
	public ProveedorInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(ProveedorInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		int[] selectedRow = table.getSelectedRows();
		Long selectedId = (Long) table.getValueAt(selectedRow[0], 0);

		Proveedor proveedor= proveedores.stream().filter(p -> p.getId().equals(selectedId.longValue()))
				  .findAny()
				  .orElse(null);
		if(proveedor!=null) {
			interfaz.getEntity(proveedor);
		}else {
			Optional<Proveedor> pro = service.findById(selectedId);
			interfaz.getEntity(pro.get());
		}
//		for (Integer c : table.getSelectedRows()) {
//			interfaz.getEntity(proveedores.get(c));         
//	    }
		
		dispose();
	}

}
