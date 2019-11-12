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
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.table.VentaTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

@Component
public class VentaDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private VentaService service;
	private VentaTableModel tableModel;
	private VentaInterfaz interfaz;
	
	private List<Venta> ventas;

	@Autowired
	public VentaDialog(VentaService service, VentaTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("BUSCADOR DE NOTAS DE VENTAS");
		this.setSize(600, 300);
		this.setModal(true);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		
		JLabel lblBuscador = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ClienteDialog.lblBuscador.text")); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loadVentas(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			    	dispose();
			    }
			    if(e.getKeyCode()==KeyEvent.VK_DOWN){
			    	table.requestFocus();
			    }
			}
		});
		pnlBuscador.add(tfBuscador);
		tfBuscador.setColumns(30);
		
		btnBuscar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ClienteDialog.btnBuscar.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadVentas(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
					loadVentas(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
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
		scrollPane.setViewportView(table);
		
		JPanel pnlBotonera = new JPanel();
		getContentPane().add(pnlBotonera, BorderLayout.SOUTH);
		
		btnAceptar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ClienteDialog.btnAceptar.text")); //$NON-NLS-1$ //$NON-NLS-2$
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
		
		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ClienteDialog.btnCancelar.text")); //$NON-NLS-1$ //$NON-NLS-2$
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
		
		loadVentas("");	
	}
	
	private void loadVentas(String filter) {
		if (filter.isEmpty()) {
			ventas = service.findAll();
		} else {
			ventas = service.findByFilter(filter);
		}
		
        tableModel.clear();
        tableModel.addEntities(ventas);
    }
	
	public VentaInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(VentaInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(ventas.get(c));         
	    }
		dispose();
	}

}
