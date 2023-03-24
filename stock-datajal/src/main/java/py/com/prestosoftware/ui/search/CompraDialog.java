package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Compra;
import py.com.prestosoftware.domain.services.CompraService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.table.CompraTableModel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

@Component
public class CompraDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private CompraService service;
	private CompraTableModel tableModel;
	private CompraInterfaz interfaz;
	
	private List<Compra> compras;
	private JLabel lblSituacion;
	private JComboBox cmbSituacion;
	private JLabel lblForma;
	private JComboBox cmbForma;
	private JLabel lblFecha;
	private JTextField textField;
	private JLabel lblFechaFin;
	private JTextField textField_1;
	private JButton btnBuscar;
	private JXDatePicker dtpFecha;
	private JXDatePicker dtpFechaFin;

	@Autowired
	public CompraDialog(CompraService service, CompraTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;
		
		this.setTitle("BUSCADOR DE COMPRAS");
		this.setSize(807, 494);
		this.setModal(true);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlBuscador = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlBuscador.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		
		lblSituacion = new JLabel("  Situación  "); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBuscador.add(lblSituacion);
		
		cmbSituacion = new JComboBox();
		cmbSituacion.setModel(new DefaultComboBoxModel(new String[] {"PAGADO", "ANULADO", "PROCESADO", "PENDIENTE", "DEVOLUCIÓN"}));
		pnlBuscador.add(cmbSituacion);
		
		lblForma = new JLabel(" Forma Pago :"); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBuscador.add(lblForma);
		
		cmbForma = new JComboBox();
		cmbForma.setModel(new DefaultComboBoxModel(new String[] {"Contado", "30 días"}));
		pnlBuscador.add(cmbForma);
		
		lblFecha = new JLabel("Fecha Inicial"); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBuscador.add(lblFecha);
		
		dtpFecha = new JXDatePicker();
		dtpFecha.setFormats("dd/MM/yyyy");
		dtpFecha.setDate(new Date());
		
		pnlBuscador.add(dtpFecha);
		//textField.setColumns(10);
		
		lblFechaFin = new JLabel("Fecha Fin"); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBuscador.add(lblFechaFin);
		
		dtpFechaFin = new JXDatePicker();
		dtpFechaFin.setFormats("dd/MM/yyyy");
		dtpFechaFin.setDate(new Date());
		pnlBuscador.add(dtpFechaFin);
		//textField_1.setColumns(10);
		
		btnBuscar = new JButton("Buscar"); //$NON-NLS-1$ //$NON-NLS-2$
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCompras();
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
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) { // to detect doble click events
					aceptar();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

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
		
		loadCompras();	
	}
	
	public void loadCompras() {
		String situacion= cmbSituacion.getSelectedItem().toString();
		int forma=2;
		if(cmbForma.getSelectedItem().toString().equalsIgnoreCase("CONTADO"))
			forma=1;
		compras = service.getComprasFiltro(dtpFecha.getDate(), dtpFechaFin.getDate(), situacion, forma);
		tableModel.clear();
        tableModel.addEntities(compras);
    }
	
	public CompraInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(CompraInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(compras.get(c));         
	    }
		dispose();
	}

}
