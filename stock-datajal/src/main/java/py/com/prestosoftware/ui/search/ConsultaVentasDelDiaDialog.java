package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
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

import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.VentaTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
	private JButton btnBuscar;
	private JLabel lblBuscador_2;
	private JXDatePicker dtpFechaInicio;
	private JXDatePicker dtpFechaFin;

	@Autowired
	public ConsultaVentasDelDiaDialog(VentaService service, VentaTableModel tableModel,
			ConsultaPrecioProducto consultaPrecioProducto) {
		this.service = service;
		this.tableModel = tableModel;

		setTitle("CONSULTA DE VENTAS DEL DIA");
		setSize(767, 467);
		
		setupUI();
		
		Util.setupScreen(this);
		
		//setModal(true);

	}
	
	private void setupUI() {
		getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(6, 6, 737, 93);
		getContentPane().add(pnlBuscador);
				GridBagLayout gbl_pnlBuscador = new GridBagLayout();
				gbl_pnlBuscador.columnWidths = new int[]{101, 84, 366, 75, 0};
				gbl_pnlBuscador.rowHeights = new int[]{25, 0, 0};
				gbl_pnlBuscador.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
				gbl_pnlBuscador.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
				pnlBuscador.setLayout(gbl_pnlBuscador);
								
								JLabel lblBuscador_1 = new JLabel("Fecha Desde:");
								lblBuscador_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
								lblBuscador_1.setBounds(6, 10, 105, 38);
								GridBagConstraints gbc_lblBuscador_1 = new GridBagConstraints();
								gbc_lblBuscador_1.anchor = GridBagConstraints.WEST;
								gbc_lblBuscador_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblBuscador_1.gridx = 0;
								gbc_lblBuscador_1.gridy = 0;
								pnlBuscador.add(lblBuscador_1, gbc_lblBuscador_1);
						
						lblBuscador_2 = new JLabel("Fecha Hasta:");
						lblBuscador_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
						GridBagConstraints gbc_lblBuscador_2 = new GridBagConstraints();
						gbc_lblBuscador_2.anchor = GridBagConstraints.WEST;
						gbc_lblBuscador_2.insets = new Insets(0, 0, 5, 5);
						gbc_lblBuscador_2.gridx = 1;
						gbc_lblBuscador_2.gridy = 0;
						pnlBuscador.add(lblBuscador_2, gbc_lblBuscador_2);
				
						btnBuscar = new JButton("Buscar");
						btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 14));
						GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
						gbc_btnBuscar.insets = new Insets(0, 0, 5, 0);
						gbc_btnBuscar.anchor = GridBagConstraints.NORTHWEST;
						gbc_btnBuscar.gridx = 3;
						gbc_btnBuscar.gridy = 0;
						pnlBuscador.add(btnBuscar, gbc_btnBuscar);
								
								dtpFechaInicio = new JXDatePicker();
								dtpFechaInicio.setFormats("dd/MM/yyyy");
								dtpFechaInicio.setDate(new Date());
								GridBagConstraints gbc_textField = new GridBagConstraints();
								gbc_textField.insets = new Insets(0, 0, 0, 5);
								gbc_textField.fill = GridBagConstraints.HORIZONTAL;
								gbc_textField.gridx = 0;
								gbc_textField.gridy = 1;
								pnlBuscador.add(dtpFechaInicio, gbc_textField);
								//dtpFechaInicio.setColumns(10);
								
								dtpFechaFin = new JXDatePicker();
								dtpFechaFin.setFormats("dd/MM/yyyy");
								dtpFechaFin.setDate(new Date());
								GridBagConstraints gbc_textField_1 = new GridBagConstraints();
								gbc_textField_1.insets = new Insets(0, 0, 0, 5);
								gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
								gbc_textField_1.gridx = 1;
								gbc_textField_1.gridy = 1;
								pnlBuscador.add(dtpFechaFin, gbc_textField_1);
								//dtpFechaFin.setColumns(10);
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
				
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 111, 737, 309);
		getContentPane().add(scrollPane);
		
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
		ventas = service.getNotasPorFechas(new Date(), new Date());
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
