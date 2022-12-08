package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.CobroCliente;
import py.com.prestosoftware.domain.services.CobroClienteService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.table.CobroClienteTableModel;

@Component
public class CobroClienteDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private CobroClienteService service;
	private CobroClienteTableModel tableModel;
	private CobroClienteInterfaz interfaz;
	
	private List<CobroCliente> ingresos;
	private JXDatePicker dtFecha;
	private JLabel lblFecha;
	
	@Autowired
	public CobroClienteDialog(CobroClienteService service, CobroClienteTableModel tableModel) throws ParseException{
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("Buscador de Cobros a Clientes");
		this.setSize(600, 300);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		lblFecha = new JLabel("Fecha");
		pnlBuscador.add(lblFecha);
		
		dtFecha = new JXDatePicker();
		pnlBuscador.add(dtFecha);
		dtFecha.setFormats("dd/MM/yyyy");
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					loadCobroClientes(dtFecha.getDate());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
						loadCobroClientes(dtFecha.getDate());
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
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
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
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		dtFecha.setDate(new Date());
		loadCobroClientes(new Date());	
	}
	
	public void loadCobroClientes(Date fecha) {
		ingresos = service.findByFecha(fecha);
		tableModel.clear();
        tableModel.addEntities(ingresos);
    }
	
	public CobroClienteInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(CobroClienteInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(ingresos.get(c));         
	    }
		dispose();
	}
	
}
