package py.com.prestosoftware.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.table.ProductoComboBoxModel;
import py.com.prestosoftware.ui.table.ProveedorComboBoxModel;
import py.com.prestosoftware.util.ConnectionUtils;

@Component
public class HistoricoCompraDialog extends JDialog {

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
	private JLabel lblProducto;
	private JLabel lblProveedor;
	private ProveedorComboBoxModel proveedorComboBoxModel;
	private ProductoComboBoxModel productoComboBoxModel;
	private JComboBox<Proveedor> cbProveedor;
	private JComboBox<Producto> cbProducto;
	private ProductoService productoService;
	private ProveedorService proveedorService;

	@Autowired
	public HistoricoCompraDialog(ProveedorComboBoxModel proveedorComboBoxModel,
			ProductoComboBoxModel productoComboBoxModel, ProductoService productoService,
			ProveedorService proveedorService) {
		this.proveedorComboBoxModel = proveedorComboBoxModel;
		this.productoComboBoxModel = productoComboBoxModel;
		this.productoService = productoService;
		this.proveedorService = proveedorService;
		this.setSize(869, 315);
		this.setModal(true);
		this.setTitle("Historico de compras de productos");

		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.CENTER);
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

		lblProveedor = new JLabel("Proveedor"); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblProveedor = new GridBagConstraints();
		gbc_lblProveedor.anchor = GridBagConstraints.WEST;
		gbc_lblProveedor.insets = new Insets(0, 0, 5, 5);
		gbc_lblProveedor.gridx = 1;
		gbc_lblProveedor.gridy = 2;
		pnlBuscador.add(lblProveedor, gbc_lblProveedor);

		cbProveedor = new JComboBox<Proveedor>(proveedorComboBoxModel);
		cbProveedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					cbProducto.requestFocus();
				}
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 2;
		pnlBuscador.add(cbProveedor, gbc_comboBox);

		lblProducto = new JLabel("Producto"); //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_lblProducto = new GridBagConstraints();
		gbc_lblProducto.anchor = GridBagConstraints.WEST;
		gbc_lblProducto.insets = new Insets(0, 0, 5, 5);
		gbc_lblProducto.gridx = 1;
		gbc_lblProducto.gridy = 3;
		pnlBuscador.add(lblProducto, gbc_lblProducto);

		cbProducto = new JComboBox<Producto>(productoComboBoxModel);
		cbProducto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					cbPeriodo.requestFocus();
				}
			}
		});
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 2;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 3;
		pnlBuscador.add(cbProducto, gbc_comboBox_1);

		lblNewLabel = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
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
		gbc_cbPeriodo.gridy = 4;
		pnlBuscador.add(cbPeriodo, gbc_cbPeriodo);

		lblOrdenadoPor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblOrdenadoPor.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblOrdenadoPor = new GridBagConstraints();
		gbc_lblOrdenadoPor.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrdenadoPor.anchor = GridBagConstraints.WEST;
		gbc_lblOrdenadoPor.gridx = 1;
		gbc_lblOrdenadoPor.gridy = 5;
		pnlBuscador.add(lblOrdenadoPor, gbc_lblOrdenadoPor);

		cbOrden = new JComboBox<String>();
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
		GridBagConstraints gbc_cbOrden = new GridBagConstraints();
		gbc_cbOrden.insets = new Insets(0, 0, 5, 5);
		gbc_cbOrden.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbOrden.gridx = 2;
		gbc_cbOrden.gridy = 5;
		pnlBuscador.add(cbOrden, gbc_cbOrden);

		lblFechaInicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel_1.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
		gbc_lblFechaInicio.anchor = GridBagConstraints.WEST;
		gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicio.gridx = 1;
		gbc_lblFechaInicio.gridy = 6;
		pnlBuscador.add(lblFechaInicio, gbc_lblFechaInicio);

		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setFormats("dd/MM/yyyy");
		// tfFechaInicial.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("CuentaRecibirDialog.textField.text"));
		// //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_tfFechaInicial = new GridBagConstraints();
		gbc_tfFechaInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfFechaInicial.insets = new Insets(0, 0, 5, 5);
		gbc_tfFechaInicial.gridx = 2;
		gbc_tfFechaInicial.gridy = 6;
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
		loadProductos();
		loadProveedores();
		AutoCompleteDecorator.decorate(cbProveedor);
		AutoCompleteDecorator.decorate(cbProducto);
	}

	private void preview() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String orden = "";
		String proveedor = "";
		String sql = "SELECT pro.id, pro.id as codigoProducto, pro.descripcion, "
				+ "sum(ico.cantidad) as stock,ico.precio, sum(ico.cantidad * ico.precio)	as subtotal, com.id as nro, com.fecha, p.nombre as proveedor  "
				+ "FROM		productos pro, compras com, compra_detalles ico , proveedores p "
				+ "WHERE	(com.situacion = 'PAGADO' or com.situacion = 'PROCESADO' or com.situacion = '1') AND  com.ID = ico.compra_id "
				+ "AND pro.id = ico.producto_id and com.proveedor_id = p.id ";
		if (cbProveedor.getSelectedItem() != null && cbProveedor.getSelectedItem().toString().length() > 0) {
			Proveedor p = (Proveedor) cbProveedor.getSelectedItem();
			proveedor = p.getId() + " - " + p.getNombre();
			sql += " and com.proveedor_id = " + p.getId();
		}
		if (cbProducto.getSelectedItem() != null && cbProducto.getSelectedItem().toString().length() > 0) {
			Producto pro = (Producto) cbProducto.getSelectedItem();
			sql += " and ico.producto_id = " + pro.getId();
		}
		// + "ORDER BY com.id ";
		if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " ORDER BY	com.id asc";
		else
			orden = " ORDER BY pro.descripcion asc";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		String fechaFiltro = " and com.fecha >= to_Date('" + df.format(fechaIniSel)
				+ "', 'DD/MM/YYYY') and com.fecha <= TO_Date('" + df.format(fechaFinSel) + "', 'DD/MM/YYYY') ";
		sql += fechaFiltro;
		sql += " GROUP BY	pro.id, pro.descripcion, com.id, com.fecha, ico.precio, p.nombre ";
		sql += orden;
		parametros.put("fechaFiltro", fechaFiltro);
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		parametros.put("proveedor", proveedor);
		parametros.put("orden", orden);
		parametros.put("sql", sql);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportProductoComprasHistorico.jrxml";
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
		String proveedor = "";
		String sql = "SELECT pro.id, pro.id as codigoProducto, pro.descripcion, "
				+ "sum(ico.cantidad) as stock,ico.precio, sum(ico.cantidad * ico.precio)	as subtotal, com.id as nro, com.fecha  "
				+ "FROM		productos pro, compras com, compra_detalles ico  "
				+ "WHERE	(com.situacion = 'PAGADO' or com.situacion = 'PROCESADO'  or com.situacion = '1') AND  com.ID = ico.compra_id "
				+ "AND pro.id = ico.producto_id  ";
		if (cbProveedor.getSelectedItem() != null && cbProveedor.getSelectedItem().toString().length() > 0) {
			Proveedor p = (Proveedor) cbProveedor.getSelectedItem();
			proveedor = p.getId() + " - " + p.getNombre();
			sql += " and com.proveedor_id = " + p.getId();
		}
		if (cbProducto.getSelectedItem() != null && cbProducto.getSelectedItem().toString().length() > 0) {
			Producto pro = (Producto) cbProducto.getSelectedItem();
			sql += " and ico.producto_id = " + pro.getId();
		}
		// + "ORDER BY com.id ";
		if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " ORDER BY	com.id asc";
		else
			orden = " ORDER BY pro.descripcion asc";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		String fechaFiltro = " and com.fecha >= to_Date('" + df.format(fechaIniSel)
				+ "', 'DD/MM/YYYY') and com.fecha <= TO_Date('" + df.format(fechaFinSel) + "', 'DD/MM/YYYY') ";
		sql += fechaFiltro;
		sql += " GROUP BY	pro.id, pro.descripcion, com.id, com.fecha, ico.precio ";
		sql += orden;
		parametros.put("fechaFiltro", fechaFiltro);
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		parametros.put("proveedor", proveedor);
		parametros.put("orden", orden);
		parametros.put("sql", sql);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportProductoCompraHistorico.jrxml";
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

	private void loadProveedores() {
		if(proveedorComboBoxModel.getSize()==0) {
			List<Proveedor> proveedores = proveedorService.findAll();
			proveedorComboBoxModel.clear();
			proveedorComboBoxModel.addElement(null);
			proveedorComboBoxModel.addElements(proveedores);	
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
