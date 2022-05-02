package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.transaction.Transactional;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.table.VentaTableModel;
import py.com.prestosoftware.ui.viewmodel.ConsultaNota;

@Component
public class ConsultaVentasDelDiaDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;

	private VentaService service;
	private VentaTableModel tableModel;
	private VentaInterfaz interfaz;

	private List<Venta> ventas;
	private JLabel lblFecha;
	private JLabel lblNewLabel;
	private JComboBox<String> tfSituacion;
	private JLabel lblFormaCobro;
	private JComboBox<String> tfCondicionPago;
	private JXDatePicker dtpFecha;
	private JXDatePicker dtpFechaFin;
	private JLabel lblFechaFin;
	private JButton btnBuscar;

	@Autowired
	public ConsultaVentasDelDiaDialog(VentaService service, VentaTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;

		setTitle("CONSULTA DE VENTAS DEL DIA");
		setSize(985, 557);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		pnlBuscador.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		//dtpFecha.setColumns(10);
		
		lblNewLabel = new JLabel("     Situación     ");
		pnlBuscador.add(lblNewLabel);
		
		tfSituacion = new JComboBox<String>();
		tfSituacion.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				//setVentasDelDia();
			}
		});
		tfSituacion.setModel(new DefaultComboBoxModel(new String[] {"PAGADO", "ANULADO", "PROCESADO", "VIGENTE"}));
		tfSituacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(tfSituacion);
		
		lblFormaCobro = new JLabel("     Forma Cobro     ");
		pnlBuscador.add(lblFormaCobro);
		
		tfCondicionPago = new JComboBox<String>();
		tfCondicionPago.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		tfCondicionPago.setModel(new DefaultComboBoxModel(new String[] {"Contado", "30 días"}));
		tfCondicionPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(tfCondicionPago);
		
		lblFecha = new JLabel("    Fecha Inicio :     ");
		pnlBuscador.add(lblFecha);
		dtpFecha = new JXDatePicker();
		dtpFecha.setFormats("dd/MM/yyyy");
		dtpFecha.setDate(new Date());
		pnlBuscador.add(dtpFecha);
		
		lblFechaFin = new JLabel("    Fecha Fin :     ");
		pnlBuscador.add(lblFechaFin);
		
		dtpFechaFin = new JXDatePicker();
		dtpFechaFin.setFormats("dd/MM/yyyy");
		dtpFechaFin.setDate(new Date());

		pnlBuscador.add(dtpFechaFin);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVentasDelDia();
			}
		});
		pnlBuscador.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
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

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		setVentasDelDia();
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	@Transactional
	public VentaInterfaz getInterfaz() {
		return interfaz;
	}

	@Transactional
	public void setInterfaz(VentaInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	@Transactional
	public void setVentasDelDia() {
		String situacion= tfSituacion.getSelectedItem().toString();
		int forma=2;
		if(tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("CONTADO"))
			forma=1;
				ventas=service.getVentasFiltro(dtpFecha.getDate(), dtpFechaFin.getDate(), situacion, forma);		
		tableModel.clear();
		tableModel.addEntities(ventas);
		table.requestFocus();
	}

	@Transactional
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(ventas.get(c));
		}
		dispose();
	}

}
