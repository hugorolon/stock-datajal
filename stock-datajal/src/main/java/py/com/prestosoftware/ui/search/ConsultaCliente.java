package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.ClienteConsultaTableModel;
import java.awt.Font;

@Component
public class ConsultaCliente extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;

	private ClienteService service;
	private ClienteConsultaTableModel tableModel;
	private ClienteInterfaz interfaz;

	private List<Cliente> clientes;
	private JButton btnActualizarLista;

	@Autowired
	public ConsultaCliente(ClienteService service, ClienteConsultaTableModel tableModel) {
		this.service = service;
		this.tableModel = tableModel;

		initializeUI();
	}

	private void initializeUI() {
		setTitle("CONSULTA DE CLIENTES");
		setSize(900, 500);
		setModal(true);

		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);

		JLabel lblBuscador = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.lblBuscador.text")); //$NON-NLS-1$
		lblBuscador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(lblBuscador);

		tfBuscador = new JTextField();
		tfBuscador.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		btnBuscar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.btnBuscar.text")); //$NON-NLS-1$
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getClientes(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					getClientes(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
			}
		});
		pnlBuscador.add(btnBuscar);
		
		btnActualizarLista = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ConsultaCliente.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnActualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getClientes("");
			}
		});
		btnActualizarLista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(btnActualizarLista);

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					aceptar();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfBuscador.requestFocus();
				}
				if (e.getKeyCode() == KeyEvent.VK_F11) {
//					aceptar(); MOSTRAR LA PANTALLA DE CUENTAS	
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable jTable = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = jTable.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
					aceptar();
				}
			}
		});
		scrollPane.setViewportView(table);

		JPanel pnlBotonera = new JPanel();
		getContentPane().add(pnlBotonera, BorderLayout.SOUTH);

		btnAceptar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.btnAceptar.text")); //$NON-NLS-1$
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.btnCancelar.text")); //$NON-NLS-1$
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
	}

	public void getClientes(String name) {
		if (name.isEmpty()) {
			clientes = service.findAll();
		} else {
			clientes = service.findByNombre(name.toUpperCase());
		}

		tableModel.clear();
		tableModel.addEntities(clientes);
	}

	public ClienteInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(ClienteInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	private void aceptar() {
		int[] selectedRow = table.getSelectedRows();
		Long selectedId = (Long) table.getValueAt(selectedRow[0], 0);

		Cliente cliente= clientes.stream().filter(customer -> customer.getId().equals(selectedId.longValue()))
				  .findAny()
				  .orElse(null);
		if(cliente!=null) {
			interfaz.getEntity(cliente);
		}else {
			Optional<Cliente> c = service.findById(selectedId);
			interfaz.getEntity(c.get());
		}
		dispose();
	}

}
