package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.ClientTableModel;
import py.com.prestosoftware.util.ConnectionUtils;
import py.com.prestosoftware.util.Notifications;


@Component
public class CuentaRecibirDialog extends JDialog implements ClienteInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int CLIENTE_CODE = 1;

	private JTextField tfClienteID;
	private JButton btnPrevisualizar;
	private JButton btnCancelar;

	private JXDatePicker tfFechaInicial;
	private JXDatePicker tfFechaFinal;
	private ClienteService service;
	private ClientTableModel tableModel;
	private ClienteInterfaz interfaz;

	private ConsultaCliente clientDialog;
	private List<Cliente> clientes;
	private JComboBox<String> cbPeriodo;
	private JLabel lblNewLabel;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JButton btnImprimir;
	private JTextField tfNombreCliente;
	private JComboBox<String> cbOrden;
	private JLabel lblOrdenadoPor;

	@Autowired
	public CuentaRecibirDialog(ClienteService service, ClientTableModel tableModel, ConsultaCliente clientDialog) {
		this.service = service;
		this.tableModel = tableModel;
		this.clientDialog = clientDialog;
		this.setTitle("Vencimientos de movimiento del cliente"); //$NON-NLS-1$ //$NON-NLS-2$
		this.setSize(668, 288);
		this.setModal(true);

		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		GridBagLayout gbl_pnlBuscador = new GridBagLayout();
		gbl_pnlBuscador.columnWidths = new int[] { 18, 88, 116, 119, 65, 0 };
		gbl_pnlBuscador.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlBuscador.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_pnlBuscador.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlBuscador.setLayout(gbl_pnlBuscador);

		// pnlBuscador.add(tfFechaInicial);

		JPanel pnlBotonera = new JPanel();
		GridBagConstraints gbc_pnlBotonera = new GridBagConstraints();
		gbc_pnlBotonera.fill = GridBagConstraints.VERTICAL;
		gbc_pnlBotonera.gridheight = 8;
		gbc_pnlBotonera.gridx = 4;
		gbc_pnlBotonera.gridy = 0;
		pnlBuscador.add(pnlBotonera, gbc_pnlBotonera);
		GridBagLayout gbl_pnlBotonera = new GridBagLayout();
		gbl_pnlBotonera.columnWidths = new int[] { 75, 0, 0, 0 };
		gbl_pnlBotonera.rowHeights = new int[] { 23, 23, 0, 0, 0, 0, 0 };
		gbl_pnlBotonera.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlBotonera.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlBotonera.setLayout(gbl_pnlBotonera);

		btnPrevisualizar = new JButton("Previsualizar"); //$NON-NLS-1$ //$NON-NLS-2$
		btnPrevisualizar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					preview();
				}
			}
		});
		btnPrevisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preview();
			}
		});
		GridBagConstraints gbc_btnPrevisualizar = new GridBagConstraints();
		gbc_btnPrevisualizar.anchor = GridBagConstraints.NORTH;
		gbc_btnPrevisualizar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrevisualizar.gridx = 1;
		gbc_btnPrevisualizar.gridy = 1;
		pnlBotonera.add(btnPrevisualizar, gbc_btnPrevisualizar);

		btnImprimir = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.btnImprimir.text")); //$NON-NLS-1$
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		GridBagConstraints gbc_btnImprimir = new GridBagConstraints();
		gbc_btnImprimir.insets = new Insets(0, 0, 5, 5);
		gbc_btnImprimir.gridx = 1;
		gbc_btnImprimir.gridy = 3;
		pnlBotonera.add(btnImprimir, gbc_btnImprimir);

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.btnCancelar.text")); //$NON-NLS-1$
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
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 5;
		pnlBotonera.add(btnCancelar, gbc_btnCancelar);

		JLabel lblBuscadorCliente = new JLabel("Cliente:"); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblBuscadorCliente = new GridBagConstraints();
		gbc_lblBuscadorCliente.anchor = GridBagConstraints.WEST;
		gbc_lblBuscadorCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscadorCliente.gridx = 1;
		gbc_lblBuscadorCliente.gridy = 1;
		pnlBuscador.add(lblBuscadorCliente, gbc_lblBuscadorCliente);

		tfClienteID = new JTextField();
		tfClienteID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteID.selectAll();
			}
		});
		tfClienteID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(CLIENTE_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfClienteID.getText().isEmpty()) {
						findClientById(Long.parseLong(tfClienteID.getText()));
						cbPeriodo.requestFocus();
					} else {
						showDialog(CLIENTE_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});

		GridBagConstraints gbc_tfClienteID = new GridBagConstraints();
		gbc_tfClienteID.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfClienteID.insets = new Insets(0, 0, 5, 5);
		gbc_tfClienteID.gridx = 2;
		gbc_tfClienteID.gridy = 1;
		pnlBuscador.add(tfClienteID, gbc_tfClienteID);
		tfClienteID.setColumns(20);

		tfNombreCliente = new JTextField();
		tfNombreCliente.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_tfNombreCliente = new GridBagConstraints();
		gbc_tfNombreCliente.insets = new Insets(0, 0, 5, 5);
		gbc_tfNombreCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNombreCliente.gridx = 3;
		gbc_tfNombreCliente.gridy = 1;
		pnlBuscador.add(tfNombreCliente, gbc_tfNombreCliente);
		tfNombreCliente.setColumns(20);

		lblNewLabel = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 3;
		pnlBuscador.add(lblNewLabel, gbc_lblNewLabel);

		cbPeriodo = new JComboBox<String>();
		cbPeriodo.setModel(new DefaultComboBoxModel(new String[] { "Hoy", "Este mes", "Este año" }));
		cbPeriodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date today = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(today);
				if (cbPeriodo.getSelectedItem().toString().equalsIgnoreCase("Hoy")) {
					tfFechaInicial.setDate(new Date());
					tfFechaFinal.setDate(new Date());
				} else if (cbPeriodo.getSelectedItem().toString().equalsIgnoreCase("Este mes")) {
					calendar = Calendar.getInstance();
					calendar.add(Calendar.MONTH, 1);
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					calendar.add(Calendar.DATE, -1);
					Date lastDayOfMonth = calendar.getTime();
					Calendar date = Calendar.getInstance();
					date.set(Calendar.DAY_OF_MONTH, 1);
					tfFechaInicial.setDate(date.getTime());
					tfFechaFinal.setDate(lastDayOfMonth);
				} else {
					String fechaIni = "01/01/" + calendar.get(Calendar.YEAR);
					String fechaFin = "31/12/" + calendar.get(Calendar.YEAR);
					try {
						tfFechaInicial.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(fechaIni));
						tfFechaFinal.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(fechaFin));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		GridBagConstraints gbc_cbPeriodo = new GridBagConstraints();
		gbc_cbPeriodo.insets = new Insets(0, 0, 5, 5);
		gbc_cbPeriodo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPeriodo.gridx = 2;
		gbc_cbPeriodo.gridy = 3;
		pnlBuscador.add(cbPeriodo, gbc_cbPeriodo);
		
		lblOrdenadoPor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("CuentaRecibirDialog.lblOrdenadoPor.text")); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblOrdenadoPor = new GridBagConstraints();
		gbc_lblOrdenadoPor.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrdenadoPor.anchor = GridBagConstraints.WEST;
		gbc_lblOrdenadoPor.gridx = 1;
		gbc_lblOrdenadoPor.gridy = 4;
		pnlBuscador.add(lblOrdenadoPor, gbc_lblOrdenadoPor);
		
		cbOrden = new JComboBox<String>();
		cbOrden.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre"}));
		cbOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo")) {
//					
//				} else {
//
//				}
			}
		});
		GridBagConstraints gbc_cbOrden = new GridBagConstraints();
		gbc_cbOrden.insets = new Insets(0, 0, 5, 5);
		gbc_cbOrden.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbOrden.gridx = 2;
		gbc_cbOrden.gridy = 4;
		pnlBuscador.add(cbOrden, gbc_cbOrden);

		lblFechaInicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel_1.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
		gbc_lblFechaInicio.anchor = GridBagConstraints.WEST;
		gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicio.gridx = 1;
		gbc_lblFechaInicio.gridy = 5;
		pnlBuscador.add(lblFechaInicio, gbc_lblFechaInicio);

		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setFormats("dd/MM/yyyy");
		// tfFechaInicial.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("CuentaRecibirDialog.textField.text"));
		// //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_tfFechaInicial = new GridBagConstraints();
		gbc_tfFechaInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfFechaInicial.insets = new Insets(0, 0, 5, 5);
		gbc_tfFechaInicial.gridx = 2;
		gbc_tfFechaInicial.gridy = 5;
		pnlBuscador.add(tfFechaInicial, gbc_tfFechaInicial);
		tfFechaInicial.setBounds(100, 100, 50, 50);
		tfFechaInicial.setDate(new Date());

		lblFechaFin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblFechaFin.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
		gbc_lblFechaFin.anchor = GridBagConstraints.WEST;
		gbc_lblFechaFin.insets = new Insets(0, 0, 0, 5);
		gbc_lblFechaFin.gridx = 1;
		gbc_lblFechaFin.gridy = 7;
		pnlBuscador.add(lblFechaFin, gbc_lblFechaFin);

		tfFechaFinal = new JXDatePicker();
		tfFechaFinal.setFormats("dd/MM/yyyy");
		GridBagConstraints gbc_tfFechaInicial_1 = new GridBagConstraints();
		gbc_tfFechaInicial_1.anchor = GridBagConstraints.WEST;
		gbc_tfFechaInicial_1.insets = new Insets(0, 0, 0, 5);
		gbc_tfFechaInicial_1.gridx = 2;
		gbc_tfFechaInicial_1.gridy = 7;
		pnlBuscador.add(tfFechaFinal, gbc_tfFechaInicial_1);
		tfFechaFinal.setDate(new Date());
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

		loadClients("");
	}

	private void loadClients(String name) {
		if (name.isEmpty()) {
			clientes = service.findAll();
		} else {
			clientes = service.findByNombre(name);
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

	private void findClientById(Long id) {
		Optional<Cliente> cliente = service.findById(id);
		if (cliente.isPresent()) {
			setCliente(cliente.get());
		} else {
			Notifications.showAlert("No existe Cliente con el codigo informado.!");
		}
	}

	private void preview() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String idCliente = "";
		String orden = "";
		if (!tfClienteID.getText().isEmpty())
			idCliente = "and id_cliente=" + tfClienteID.getText();
		parametros.put("idCliente", idCliente);
		if(cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " ORDER BY 5, 12,13,14 asc";
		else
			orden =" ORDER BY 6, 12,13,14 asc";
		DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		String fechaFiltro1 = " and ica_vencimiento >= to_Date('" + df.format(fechaIniSel)
				+ "', 'DD/MM/YYYY') and ica_vencimiento <= TO_Date('" + df.format(fechaFinSel) + "', 'DD/MM/YYYY') ";
		String fechaFiltro2 = " and ica_vencimiento < to_Date('" + df.format(fechaIniSel) + "', 'DD/MM/YYYY') ";
		parametros.put("fechaFiltro1", fechaFiltro1);
		parametros.put("fechaFiltro2", fechaFiltro2);
		parametros.put("orden", orden);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportMovimientoClienteCuenta.jrxml";
			JasperDesign jasperDesign = JRXmlLoader.load(ruta);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "cuentaRecibir.pdf");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void print() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String idCliente = "";
		String orden = "";
		if (!tfClienteID.getText().isEmpty())
			idCliente = "and id_cliente=" + tfClienteID.getText();
		parametros.put("idCliente", idCliente);
		if(cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " order by 5, 12,13,14 asc";
		else
			orden =" order by 6, 12,13,14 asc";
		DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		String fechaFiltro1 = " and ica_vencimiento >= to_Date('" + df.format(fechaIniSel)
				+ "', 'DD/MM/YYYY') and ica_vencimiento <= TO_Date('" + df.format(fechaFinSel) + "', 'DD/MM/YYYY') ";
		String fechaFiltro2 = " and ica_vencimiento < to_Date('" + df.format(fechaIniSel) + "', 'DD/MM/YYYY') ";
		parametros.put("fechaFiltro1", fechaFiltro1);
		parametros.put("fechaFiltro2", fechaFiltro2);
		parametros.put("orden", orden);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportMovimientoClienteCuenta.jrxml";
			JasperDesign jasperDesign = JRXmlLoader.load(ruta);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);
			JasperPrintManager.printReport(jasperPrint, true);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showDialog(int code) {
		switch (code) {
		case CLIENTE_CODE:
			clientDialog.setInterfaz(this);
			clientDialog.setVisible(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void getEntity(Cliente cliente) {
		setCliente(cliente);
	}

	private void setCliente(Cliente cliente) {
		tfNombreCliente.setText("");
		if (cliente != null) {
			tfClienteID.setText(String.valueOf(cliente.getId()));
			tfNombreCliente.setText(cliente.getRazonSocial());
		}
	}
}
