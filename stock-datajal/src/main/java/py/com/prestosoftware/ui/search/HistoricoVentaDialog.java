package py.com.prestosoftware.ui.search;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.ui.table.ClienteComboBoxModel;
import py.com.prestosoftware.ui.table.ProductoComboBoxModel;
import py.com.prestosoftware.util.ConnectionUtils;

@Component
public class HistoricoVentaDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton btnPrevisualizar;
	private JButton btnCancelar;

	private JXDatePicker tfFechaInicial;
	private JXDatePicker tfFechaFinal;
	private JComboBox<String> cbPeriodo;
	private JLabel lblNewLabel;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JButton btnImprimir;
	private JComboBox<String> cbOrden;
	private JLabel lblOrdenadoPor;
	private JComboBox<Cliente> cbCliente;
	private JComboBox<Producto> cbProducto;
	private JLabel lblProducto;
	private JLabel lblCliente;
	private ClienteService clienteService;
	private ClienteComboBoxModel clienteComboBoxModel;
	private ProductoComboBoxModel productoComboBoxModel;
	private ProductoService productoService;
	private JPanel panel;

	@Autowired
	public HistoricoVentaDialog(ProductoComboBoxModel productoComboBoxModel, ProductoService productoService,
			ClienteComboBoxModel clienteComboBoxModel, ClienteService clienteService) {
		this.clienteComboBoxModel = clienteComboBoxModel;
		this.clienteService = clienteService;
		this.productoComboBoxModel = productoComboBoxModel;
		this.productoService = productoService;

		this.setSize(803, 288);
		this.setModal(true);
		this.setTitle("Historico de ventas de productos");
		getContentPane().setLayout(null);

		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 606, 244);
		getContentPane().add(pnlBuscador);
		pnlBuscador.setLayout(null);

		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 34, 55, 13);
		pnlBuscador.add(lblCliente);

		cbCliente = new JComboBox<Cliente>(clienteComboBoxModel);
		cbCliente.setBounds(102, 30, 494, 21);
		pnlBuscador.add(cbCliente);

		lblProducto = new JLabel("Producto");
		lblProducto.setBounds(10, 60, 78, 13);
		pnlBuscador.add(lblProducto);

		cbProducto = new JComboBox<Producto>(productoComboBoxModel);
		cbProducto.setBounds(102, 56, 494, 21);
		pnlBuscador.add(cbProducto);

		lblNewLabel = new JLabel("Periodo");
		lblNewLabel.setBounds(10, 85, 78, 13);
		pnlBuscador.add(lblNewLabel);

		cbPeriodo = new JComboBox<String>();
		cbPeriodo.setBounds(102, 82, 103, 19);
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
		pnlBuscador.add(cbPeriodo);

		lblFechaInicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel_1.text"));
		lblFechaInicio.setBounds(10, 110, 82, 13);
		pnlBuscador.add(lblFechaInicio);

		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setFormats("dd/MM/yyyy");
		pnlBuscador.add(tfFechaInicial);
		tfFechaInicial.setBounds(102, 106, 103, 21);
		tfFechaInicial.setDate(new Date());

		lblFechaFin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblFechaFin.text"));
		lblFechaFin.setBounds(10, 136, 68, 13);
		pnlBuscador.add(lblFechaFin);

		tfFechaFinal = new JXDatePicker();
		tfFechaFinal.setBounds(102, 132, 103, 21);
		tfFechaFinal.setFormats("dd/MM/yyyy");
		pnlBuscador.add(tfFechaFinal);
		tfFechaFinal.setDate(new Date());

		lblOrdenadoPor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblOrdenadoPor.text"));
		lblOrdenadoPor.setBounds(10, 161, 96, 13);
		pnlBuscador.add(lblOrdenadoPor);

		cbOrden = new JComboBox<String>();
		cbOrden.setBounds(102, 158, 103, 19);
		cbOrden.setModel(new DefaultComboBoxModel(new String[] { "Codigo", "Nombre" }));
		cbOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo")) {
//					
//				} else {
//
//				}
			}
		});
		pnlBuscador.add(cbOrden);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

		loadProductos();
		loadClientes();
		AutoCompleteDecorator.decorate(cbCliente);
		AutoCompleteDecorator.decorate(cbProducto);

		panel = new JPanel();
		panel.setBounds(606, 0, 173, 244);
		getContentPane().add(panel);
		panel.setLayout(null);
		btnPrevisualizar = new JButton("Previsualizar");
		btnPrevisualizar.setBounds(38, 58, 103, 21);
		panel.add(btnPrevisualizar);

		btnImprimir = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.btnImprimir.text"));
		btnImprimir.setBounds(38, 108, 103, 21);
		panel.add(btnImprimir);

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.btnCancelar.text"));
		btnCancelar.setBounds(38, 161, 103, 21);
		panel.add(btnCancelar);
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
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
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
	}

	private void preview() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String orden = "";
		String cliente = "";
		String sql = "SELECT p.id, p.descripcion, v.id as nro, i.precio AS precio, "
				+ "i.cantidad AS stock, i.cantidad * i.precio AS subtotal, v.fecha, c.nombre as cliente "
				+ "FROM productos p, ventas v, venta_detalles i , clientes c "
				+ "WHERE v.id  = i.venta_id AND p.id = i.producto_id and v.cliente_id = c.id "
				+ "and (v.situacion = 'PAGADO' or v.situacion = 'PROCESADO'  or v.situacion = '1') ";
		if (cbCliente.getSelectedItem() != null && cbCliente.getSelectedItem().toString().length() > 0) {
			String clienteNombre = cbCliente.getSelectedItem().toString();
			Cliente c = (Cliente) clienteService.findByNombreEquals(clienteNombre);
			cliente = c.getId() + " - " + c.getNombre();
			sql += " and v.cliente_id = " + c.getId();
		}
		if (cbProducto.getSelectedItem() != null && cbProducto.getSelectedItem().toString().length() > 0) {
			Producto pro = (Producto) cbProducto.getSelectedItem();
			sql += " and i.producto_id = " + pro.getId();
		}

		if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " ORDER BY	v.id asc";
		else
			orden = " ORDER BY p.descripcion asc";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		String fechaFiltro = " and fecha >= to_Date('" + df.format(fechaIniSel)
				+ "', 'DD/MM/YYYY') and fecha <= TO_Date('" + df.format(fechaFinSel) + "', 'DD/MM/YYYY') ";
		sql += fechaFiltro;
		sql += orden;

		parametros.put("fechaFiltro", fechaFiltro);
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		parametros.put("orden", orden);
		parametros.put("cliente", cliente);
		parametros.put("sql", sql);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportProductoVentasHistorico.jrxml";
			JasperDesign jasperDesign = JRXmlLoader.load(ruta);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);
			JFrame frame = new JFrame();
			frame.setTitle("Visualizar Historico de compras de productos");
			frame.setBounds(100, 100, 800, 600);
			frame.getContentPane().add(new JRViewer(jasperPrint));
			frame.setModalExclusionType(getModalExclusionType().APPLICATION_EXCLUDE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void print() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String orden = "";
		String cliente = "";
		String sql = "SELECT p.id as codigoProducto, p.descripcion, v.id as nro, i.precio AS precio, "
				+ "i.cantidad AS cantidad, i.cantidad * i.precio AS total "
				+ "FROM productos p, ventas v, venta_detalles i  "
				+ "WHERE v.id  = i.venta_id AND p.id = i.producto_id "
				+ "and (v.situacion = 'PAGADO' or v.situacion = 'PROCESADO'  or v.situacion = '1') ";
		if (cbCliente.getSelectedItem() != null && cbCliente.getSelectedItem().toString().length() > 0) {
			Cliente c = (Cliente) cbCliente.getSelectedItem();
			cliente = c.getId() + " - " + c.getNombre();
			sql += " and v.cliente_id = " + c.getId();
		}
		if (cbProducto.getSelectedItem() != null && cbProducto.getSelectedItem().toString().length() > 0) {
			Producto pro = (Producto) cbProducto.getSelectedItem();
			sql += " and i.producto_id = " + pro.getId();
		}

		if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " ORDER BY	v.id asc";
		else
			orden = " ORDER BY p.descripcion asc";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		String fechaFiltro = " and fecha >= to_Date('" + df.format(fechaIniSel)
				+ "', 'DD/MM/YYYY') and fecha <= TO_Date('" + df.format(fechaFinSel) + "', 'DD/MM/YYYY') ";
		sql += fechaFiltro;
		sql += orden;

		parametros.put("fechaFiltro", fechaFiltro);
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		parametros.put("orden", orden);
		parametros.put("cliente", cliente);
		parametros.put("sql", sql);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportProductoVentasHistorico.jrxml";
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

	private void loadClientes() {
		if (clienteComboBoxModel.getSize() == 0) {
			clienteComboBoxModel.clear();
			clienteComboBoxModel.addElement(null);
			List<Cliente> clientes = clienteService.findAllOrderByName();
			clienteComboBoxModel.addElements(clientes);
		}
	}

	private void loadProductos() {
		if (productoComboBoxModel.getSize() == 0) {
			List<Producto> productos = productoService.findAllByNombre();
			productoComboBoxModel.clear();
			productoComboBoxModel.addElement(null);
			productoComboBoxModel.addElements(productos);
		}
	}

}
