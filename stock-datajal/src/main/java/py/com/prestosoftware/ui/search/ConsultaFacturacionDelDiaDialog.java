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
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.transaction.Transactional;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.VentaTemporal;
import py.com.prestosoftware.domain.services.VentaTemporalService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.table.VentaTemporalTableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

@Component
public class ConsultaFacturacionDelDiaDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;

	private VentaTemporalService service;
	private VentaTemporalTableModel tableModel;
	private VentaTemporalInterfaz interfaz;

	private List<VentaTemporal> ventas;
	private JLabel lblFecha;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JComboBox<String> tfSituacion;
	private JLabel lblFormaCobro;
	private JComboBox<String> tfCondicionPago;
	private JXDatePicker dtpFecha;
	private JXDatePicker dtpFechaFin;
	private JLabel lblFechaFin;

	@Autowired
	public ConsultaFacturacionDelDiaDialog(VentaTemporalService service, VentaTemporalTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;

		setTitle("CONSULTA DE FACTURACIONES DEL DIA");
		setSize(822, 467);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		pnlBuscador.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		//dtpFecha.setColumns(10);
		
		lblNewLabel = new JLabel("     Situación     ");
		pnlBuscador.add(lblNewLabel);
		
		tfSituacion = new JComboBox<String>();
		tfSituacion.setModel(new DefaultComboBoxModel(new String[] {"VIGENTE", "PAGADO", "ANULADO", "PROCESADO"}));
		tfSituacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(tfSituacion);
		
		lblFormaCobro = new JLabel("     Forma Cobro     ");
		pnlBuscador.add(lblFormaCobro);
		
		tfCondicionPago = new JComboBox<String>();
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
		setVentasTemporalDelDia();
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	@Transactional
	public VentaTemporalInterfaz getInterfaz() {
		return interfaz;
	}

	@Transactional
	public void setInterfaz(VentaTemporalInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	@Transactional
	public void setVentasTemporalDelDia() {
		String situacion= tfSituacion.getSelectedItem().toString();
		int forma=2;
		if(tfCondicionPago.getSelectedItem().toString().equalsIgnoreCase("CONTADO"))
			forma=1;
		ventas = service.getNotasPorFechas(dtpFecha.getDate(), dtpFechaFin.getDate());
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
