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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.table.ConsultaVentaTableModel;
import py.com.prestosoftware.ui.table.VentaTableModel;

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

	@Autowired
	public ConsultaVentasDelDiaDialog(VentaService service, VentaTableModel tableModel,
			ConsultaPrecioProducto consultaPrecioProducto) {
		this.service = service;
		this.tableModel = tableModel;

		setTitle("CONSULTA DE VENTAS DEL DIA");
		setSize(700, 350);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);

		scrollPane = new JScrollPane();
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

	public VentaInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(VentaInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	public void setVentasDelDia() {
		ventas= service.getNotasPorFechas(new Date(),new Date());
		tableModel.clear();
		tableModel.addEntities(ventas);
		table.requestFocus();
	}
	
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(ventas.get(c));         
	    }
		dispose();
	}

}
