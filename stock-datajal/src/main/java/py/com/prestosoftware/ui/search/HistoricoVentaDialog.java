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
	private JComboBox<String> cbCliente;
	private JComboBox<Producto> cbProducto;
	private JLabel lblProducto;
	private JLabel lblCliente;
	private ClienteService clienteService;
	private ClienteComboBoxModel clienteComboBoxModel;
	private ProductoComboBoxModel productoComboBoxModel;
	private ProductoService productoService;

	@Autowired
	public HistoricoVentaDialog(
			ProductoComboBoxModel productoComboBoxModel,			ProductoService productoService
			,ClienteComboBoxModel clienteComboBoxModel, ClienteService clienteService
			) {
		this.clienteComboBoxModel =clienteComboBoxModel;
		this.clienteService=clienteService;
		this.productoComboBoxModel=productoComboBoxModel;
		this.productoService =productoService;
		
		this.setSize(754, 395);
		this.setModal(true);
		this.setTitle("Historico de ventas de productos");

		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.WEST);
								GridBagLayout gbl_pnlBuscador = new GridBagLayout();
								gbl_pnlBuscador.columnWidths = new int[]{34, 67, 63, 61, 54, 0};
								gbl_pnlBuscador.rowHeights = new int[]{21, 0, 0, 0, 0, 0, 0, 0, 0, 0};
								gbl_pnlBuscador.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
								gbl_pnlBuscador.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
								pnlBuscador.setLayout(gbl_pnlBuscador);
																						
																						lblCliente = new JLabel("Cliente"); //$NON-NLS-1$ //$NON-NLS-2$
																						GridBagConstraints gbc_lblCliente = new GridBagConstraints();
																						gbc_lblCliente.anchor = GridBagConstraints.WEST;
																						gbc_lblCliente.insets = new Insets(0, 0, 5, 5);
																						gbc_lblCliente.gridx = 1;
																						gbc_lblCliente.gridy = 1;
																						pnlBuscador.add(lblCliente, gbc_lblCliente);
																						
																						cbCliente = new JComboBox<String>(clienteComboBoxModel);
																						GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
																						gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
																						gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
																						gbc_comboBox_1.gridx = 2;
																						gbc_comboBox_1.gridy = 1;
																						pnlBuscador.add(cbCliente, gbc_comboBox_1);
																						
																						lblProducto = new JLabel("Producto"); //$NON-NLS-1$ //$NON-NLS-2$
																						GridBagConstraints gbc_lblProducto = new GridBagConstraints();
																						gbc_lblProducto.anchor = GridBagConstraints.WEST;
																						gbc_lblProducto.insets = new Insets(0, 0, 5, 5);
																						gbc_lblProducto.gridx = 1;
																						gbc_lblProducto.gridy = 2;
																						pnlBuscador.add(lblProducto, gbc_lblProducto);
																						
																						cbProducto = new JComboBox<Producto>(productoComboBoxModel);
																						GridBagConstraints gbc_comboBox = new GridBagConstraints();
																						gbc_comboBox.insets = new Insets(0, 0, 5, 5);
																						gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
																						gbc_comboBox.gridx = 2;
																						gbc_comboBox.gridy = 2;
																						pnlBuscador.add(cbProducto, gbc_comboBox);
																				
																						lblNewLabel = new JLabel("Periodo"); //$NON-NLS-1$
																						GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
																						gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
																						gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
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
																								gbc_cbPeriodo.anchor = GridBagConstraints.WEST;
																								gbc_cbPeriodo.insets = new Insets(0, 0, 5, 5);
																								gbc_cbPeriodo.gridx = 2;
																								gbc_cbPeriodo.gridy = 3;
																								pnlBuscador.add(cbPeriodo, gbc_cbPeriodo);
																								
																										lblFechaInicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
																												.getString("CuentaRecibirDialog.lblNewLabel_1.text")); //$NON-NLS-1$
																										GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
																										gbc_lblFechaInicio.anchor = GridBagConstraints.WEST;
																										gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
																										gbc_lblFechaInicio.gridx = 1;
																										gbc_lblFechaInicio.gridy = 4;
																										pnlBuscador.add(lblFechaInicio, gbc_lblFechaInicio);
																								
																										tfFechaInicial = new JXDatePicker();
																										tfFechaInicial.setFormats("dd/MM/yyyy");
																										// tfFechaInicial.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("CuentaRecibirDialog.textField.text"));
																										// //$NON-NLS-1$ //$NON-NLS-2$
																										
																										GridBagConstraints gbc_tfFechaInicial = new GridBagConstraints();
																										gbc_tfFechaInicial.anchor = GridBagConstraints.NORTHWEST;
																										gbc_tfFechaInicial.insets = new Insets(0, 0, 5, 5);
																										gbc_tfFechaInicial.gridx = 2;
																										gbc_tfFechaInicial.gridy = 4;
																										pnlBuscador.add(tfFechaInicial, gbc_tfFechaInicial);
																										tfFechaInicial.setBounds(100, 100, 50, 50);
																										tfFechaInicial.setDate(new Date());
																								
																										lblFechaFin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
																												.getString("CuentaRecibirDialog.lblFechaFin.text")); //$NON-NLS-1$
																										GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
																										gbc_lblFechaFin.anchor = GridBagConstraints.WEST;
																										gbc_lblFechaFin.insets = new Insets(0, 0, 5, 5);
																										gbc_lblFechaFin.gridx = 1;
																										gbc_lblFechaFin.gridy = 5;
																										pnlBuscador.add(lblFechaFin, gbc_lblFechaFin);
																								
																										tfFechaFinal = new JXDatePicker();
																										tfFechaFinal.setFormats("dd/MM/yyyy");
																										GridBagConstraints gbc_tfFechaFinal = new GridBagConstraints();
																										gbc_tfFechaFinal.insets = new Insets(0, 0, 5, 5);
																										gbc_tfFechaFinal.anchor = GridBagConstraints.NORTHWEST;
																										gbc_tfFechaFinal.gridx = 2;
																										gbc_tfFechaFinal.gridy = 5;
																										pnlBuscador.add(tfFechaFinal, gbc_tfFechaFinal);
																										tfFechaFinal.setDate(new Date());
																						
																								lblOrdenadoPor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
																										.getString("CuentaRecibirDialog.lblOrdenadoPor.text")); //$NON-NLS-1$
																								GridBagConstraints gbc_lblOrdenadoPor = new GridBagConstraints();
																								gbc_lblOrdenadoPor.anchor = GridBagConstraints.WEST;
																								gbc_lblOrdenadoPor.insets = new Insets(0, 0, 5, 5);
																								gbc_lblOrdenadoPor.gridx = 1;
																								gbc_lblOrdenadoPor.gridy = 6;
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
																										gbc_cbOrden.anchor = GridBagConstraints.WEST;
																										gbc_cbOrden.insets = new Insets(0, 0, 5, 5);
																										gbc_cbOrden.gridx = 2;
																										gbc_cbOrden.gridy = 6;
																										pnlBuscador.add(cbOrden, gbc_cbOrden);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		loadProductos();
		loadClientes();
		AutoCompleteDecorator.decorate(cbCliente);
		AutoCompleteDecorator.decorate(cbProducto);
								btnPrevisualizar = new JButton("Previsualizar"); //$NON-NLS-1$ //$NON-NLS-2$
								GridBagConstraints gbc_btnPrevisualizar = new GridBagConstraints();
								gbc_btnPrevisualizar.insets = new Insets(0, 0, 0, 5);
								gbc_btnPrevisualizar.gridx = 1;
								gbc_btnPrevisualizar.gridy = 8;
								pnlBuscador.add(btnPrevisualizar, gbc_btnPrevisualizar);
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
						
								btnImprimir = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
										.getString("CuentaRecibirDialog.btnImprimir.text")); //$NON-NLS-1$
								GridBagConstraints gbc_btnImprimir = new GridBagConstraints();
								gbc_btnImprimir.insets = new Insets(0, 0, 0, 5);
								gbc_btnImprimir.gridx = 2;
								gbc_btnImprimir.gridy = 8;
								pnlBuscador.add(btnImprimir, gbc_btnImprimir);
								btnImprimir.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										print();
									}
								});
				
						btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
								.getString("ClienteDialog.btnCancelar.text")); //$NON-NLS-1$
						GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
						gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
						gbc_btnCancelar.gridx = 3;
						gbc_btnCancelar.gridy = 8;
						pnlBuscador.add(btnCancelar, gbc_btnCancelar);
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
	}

	



	private void preview() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String orden = "";
		String cliente= "";
		String sql ="SELECT p.id, p.descripcion, v.id as nro, i.precio AS precio, "
				+ "i.cantidad AS stock, i.cantidad * i.precio AS subtotal, v.fecha "
				+ "FROM productos p, ventas v, venta_detalles i  "
				+ "WHERE v.id  = i.venta_id AND p.id = i.producto_id "
				+ "and (v.situacion = 'PAGADO' or v.situacion = 'PROCESADO') ";
		if (cbCliente.getSelectedItem() != null && cbCliente.getSelectedItem().toString().length() > 0) {
			 String clienteNombre= cbCliente.getSelectedItem().toString();
			 Cliente c = (Cliente)clienteService.findByNombreEquals(clienteNombre);
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
		sql+= fechaFiltro;
		sql+=orden;
		
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
			frame.setBounds(100, 100, 800,600);
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
		String cliente= "";
		String sql ="SELECT p.id as codigoProducto, p.descripcion, v.id as nro, i.precio AS precio, "
				+ "i.cantidad AS cantidad, i.cantidad * i.precio AS total "
				+ "FROM productos p, ventas v, venta_detalles i  "
				+ "WHERE v.id  = i.venta_id AND p.id = i.producto_id "
				+ "and (v.situacion = 'PAGADO' or v.situacion = 'PROCESADO') ";
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
		sql+= fechaFiltro;
		sql+=orden;
		
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
		if(clienteComboBoxModel.getSize()==0) {
			clienteComboBoxModel.clear();
			clienteComboBoxModel.addElement(null);
			List<Cliente> clientes= clienteService.findAllOrderByName();
			for(Cliente c : clientes) {
				clienteComboBoxModel.addElement(c.getNombre());
			}
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
