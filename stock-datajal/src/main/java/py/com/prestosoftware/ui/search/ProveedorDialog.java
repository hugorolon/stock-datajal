package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.ProveedorTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class ProveedorDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProveedorService service;
	private ProveedorTableModel tableModel;
	private ProveedorInterfaz interfaz;
	
	private List<Proveedor> proveedores;

	@Autowired
	public ProveedorDialog(ProveedorService service, ProveedorTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("Buscador de Proveedores");
		this.setSize(600, 300);
		
		getContentPane().setLayout(new BorderLayout());
		setModal(true);
		//getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		
		JLabel lblBuscador = new JLabel("Buscador");
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loadEntities(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			    	dispose();
			    }
			    if(e.getKeyCode()==KeyEvent.VK_DOWN){
			    	table.requestFocus();
			    }
			}
			
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
				loadEntities(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
					loadEntities(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
			}
		});
		pnlBuscador.add(btnBuscar);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                loadData(); 
            }
        });
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
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		loadEntities("");	
	}
	
	private void loadEntities(String name) {
		if (name.isEmpty()) {
			proveedores = service.findAll();
		} else {
			proveedores = service.findByNombre(name.toUpperCase());
		}
		
        tableModel.clear();
        tableModel.addEntities(proveedores);
    }
	
	private void loadData() {
		int selectedRow = table.getSelectedRow();
        
		if (selectedRow != -1) {
			Long proveedorId = tableModel.getEntityByRow(selectedRow).getId();
			Optional<Proveedor> p = service.findById(proveedorId);
			
			if (p.get() != null) {
//				getStockProductosByDeposito(p);
//				getPreciosByProducto(p);
			}
	    }
	}
	
	public ProveedorInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(ProveedorInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
//	private void aceptar() {
//		for (Integer c : table.getSelectedRows()) {
//			interfaz.getEntity(proveedores.get(c));         
//	    }
//		dispose();
//	}
	
	private void aceptar() {
		try {
			int[] selectedRow = table.getSelectedRows();
			Long selectedId = (Long) table.getValueAt(selectedRow[0], 0);

			Proveedor proveedor= proveedores.stream().filter(p -> p.getId().equals(selectedId.longValue()))
					  .findAny()
					  .orElse(null);
			if(proveedor!=null) {
				interfaz.getEntity(proveedor);
			}else {
				Optional<Proveedor> p = service.findById(selectedId);
				interfaz.getEntity(p.get());
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		dispose();
	}

	private Proveedor getProveedor() {
		return proveedores.get(table.getSelectedRow());         
	}
	
	public Long getProveedorId() {
		return proveedores.get(table.getSelectedRow()).getId();         
	}

	public void getProveedores() {
		loadEntities("");
	}
	public void inicializaProveedores() {
		proveedores=new ArrayList<Proveedor>();
	}
}
