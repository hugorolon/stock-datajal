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
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.ProductoComboBoxModel;
import py.com.prestosoftware.ui.table.ProveedorComboBoxModel;
import py.com.prestosoftware.util.ConnectionUtils;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

@Component
public class HistoricoCompraDialog extends JDialog implements ProductoInterfaz, ProveedorInterfaz {

	private static final long serialVersionUID = 1L;
	private static final int PRODUCTO_CODE = 1;
	private static final int PROVEEDOR_CODE = 2;
	private JButton btnPrevisualizar;
	private JButton btnCancelar;
	private ProductoVistaDialog productoDialog;
	private ProveedorDialog proveedorDialog;
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
	private ProductoService productoService;
	private ProveedorService proveedorService;
	private JTextField tfProductoId;
	private JTextField tfDescripcion;
	private JTextField tfNombreProveedor;
	private JTextField tfCodigoProveedor;

	@Autowired
	public HistoricoCompraDialog(ProveedorComboBoxModel proveedorComboBoxModel,
			ProductoComboBoxModel productoComboBoxModel, ProductoService productoService,
			ProveedorService proveedorService,  ProductoVistaDialog productoDialog, ProveedorDialog proveedorDialog) {
		this.proveedorComboBoxModel = proveedorComboBoxModel;
		this.productoComboBoxModel = productoComboBoxModel;
		this.productoService = productoService;
		this.proveedorService = proveedorService;
		this.productoDialog= productoDialog;
		this.proveedorDialog= proveedorDialog;
		this.setSize(832, 298);
		this.setModal(true);
		this.setTitle("Historico de compras de productos");

		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 606, 244);
		getContentPane().add(pnlBuscador);
		pnlBuscador.setLayout(null);

		// pnlBuscador.add(tfFechaInicial);

		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(551, 10, 250, 238);
		pnlBuscador.add(pnlBotonera);
		pnlBotonera.setLayout(null);

		btnPrevisualizar = new JButton("Previsualizar");
		btnPrevisualizar.setBounds(74, 22, 105, 21); 
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
		pnlBotonera.add(btnPrevisualizar);

		btnImprimir = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.btnImprimir.text")); //$NON-NLS-1$
		btnImprimir.setBounds(74, 79, 105, 21);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		
		pnlBotonera.add(btnImprimir);

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("ClienteDialog.btnCancelar.text")); //$NON-NLS-1$
		btnCancelar.setBounds(74, 135, 105, 21);
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
		
				
		lblProveedor = new JLabel("Proveedor"); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBuscador.add(lblProveedor);

		cbProveedor = new JComboBox<Proveedor>(proveedorComboBoxModel);
		cbProveedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfProductoId.requestFocus();
				}
			}
		});
		pnlBuscador.add(cbProveedor);

		lblProducto = new JLabel("Producto");
		lblProducto.setBounds(21, 67, 55, 13);
		pnlBuscador.add(lblProducto);
		
		tfProductoId = new JTextField();
		tfProductoId.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		tfProductoId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfProductoId.selectAll();
			}
		});
		tfProductoId.setBounds(113, 63, 45, 21);
		tfProductoId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoId.getText().isEmpty()) {
						findProducto(tfProductoId.getText());
					} else {
						showDialog(PRODUCTO_CODE);
					}
				} 
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Util.validateNumero(e);
			}
		});
		pnlBuscador.add(tfProductoId);
		tfProductoId.setColumns(10);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setText("");
		tfDescripcion.setBounds(168, 63, 319, 21);
		pnlBuscador.add(tfDescripcion);
		tfDescripcion.setColumns(10);

		lblNewLabel = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel.text")); //$NON-NLS-1$
		pnlBuscador.add(lblNewLabel);
		lblNewLabel.setBounds(21, 97, 78, 13);

		cbPeriodo = new JComboBox<String>();
		cbPeriodo.setBounds(113, 94, 103, 19);
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

		lblOrdenadoPor = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblOrdenadoPor.text")); //$NON-NLS-1$
		lblOrdenadoPor.setBounds(21, 188, 84, 21);
		pnlBuscador.add(lblOrdenadoPor);

		cbOrden = new JComboBox<String>();
		cbOrden.setBounds(113, 188, 105, 21);
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

		lblFechaInicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel_1.text")); //$NON-NLS-1$
		lblFechaInicio.setBounds(21, 123, 78, 21);
				pnlBuscador.add(lblFechaInicio);

		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setFormats("dd/MM/yyyy");
		pnlBuscador.add(tfFechaInicial);
		tfFechaInicial.setBounds(113, 123, 105, 21);
		tfFechaInicial.setDate(new Date());

		lblFechaFin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblFechaFin.text")); //$NON-NLS-1$
		lblFechaFin.setBounds(21, 157, 78, 21);
		pnlBuscador.add(lblFechaFin);

		tfFechaFinal = new JXDatePicker();
		tfFechaFinal.setBounds(111, 157, 105, 21);
		tfFechaFinal.setFormats("dd/MM/yyyy");
		pnlBuscador.add(tfFechaFinal);
		tfFechaFinal.setDate(new Date());

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		loadProductos();
		loadProveedores();
		AutoCompleteDecorator.decorate(cbProveedor);
		
		tfNombreProveedor = new JTextField();
		tfNombreProveedor.setBounds(168, 30, 319, 20);
		pnlBuscador.add(tfNombreProveedor);
		tfNombreProveedor.setColumns(10);
		
		tfCodigoProveedor = new JTextField();
		tfCodigoProveedor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCodigoProveedor.selectAll();
			}
		});
		tfCodigoProveedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PROVEEDOR_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfCodigoProveedor.getText().isEmpty()) {
						findProveedorById(Long.parseLong(tfCodigoProveedor.getText()));
						tfProductoId.requestFocus();
					} else {
						showDialog(PROVEEDOR_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					showDialog(PROVEEDOR_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCodigoProveedor.setText("");
		tfCodigoProveedor.setBounds(113, 30, 45, 20);
		pnlBuscador.add(tfCodigoProveedor);
		tfCodigoProveedor.setColumns(10);
		
		JLabel lblProveedores = new JLabel("Proveedor"); //$NON-NLS-1$ //$NON-NLS-2$
		lblProveedores.setBounds(21, 33, 78, 14);
		pnlBuscador.add(lblProveedores);
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
//		if (cbProveedor.getSelectedItem() != null && cbProveedor.getSelectedItem().toString().length() > 0) {
//			Proveedor p = (Proveedor) cbProveedor.getSelectedItem();
//			proveedor = p.getId() + " - " + p.getNombre();
//			sql += " and com.proveedor_id = " + p.getId();
//		}
		if (tfCodigoProveedor.getText() != null && tfCodigoProveedor.getText().toString().length() > 0) {
			sql += " and com.proveedor_id = " + tfCodigoProveedor.getText();
			proveedor = tfNombreProveedor.getText();
		}
		if (tfProductoId.getText() != null && tfProductoId.getText().toString().length() > 0) {
			sql += " and ico.producto_id = " + tfProductoId.getText();
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
//		if (cbProveedor.getSelectedItem() != null && cbProveedor.getSelectedItem().toString().length() > 0) {
//			Proveedor p = (Proveedor) cbProveedor.getSelectedItem();
//			proveedor = p.getId() + " - " + p.getNombre();
//			sql += " and com.proveedor_id = " + p.getId();
//		}
		if (tfCodigoProveedor.getText() != null && tfCodigoProveedor.getText().toString().length() > 0) {
			sql += " and com.proveedor_id = " + tfCodigoProveedor.getText();
			proveedor = tfNombreProveedor.getText();
		}

		if (tfProductoId.getText() != null && tfProductoId.getText().toString().length() > 0) {
			sql += " and ico.producto_id = " + tfProductoId.getText();
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
	
	private void findProducto(String id) {
		try {
			Optional<Producto> producto = null;
			producto = productoService.findById(Long.valueOf(id.trim()));
			
			if (producto.isPresent()) {
				setProducto(producto.get());
			} else {
				Notifications.showAlert("No existe producto informado. Verifique por favor.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas con el Producto, intente nuevamente!");
		}
	}
	
	private void showDialog(int code) {
		switch (code) {
		case PRODUCTO_CODE:
			productoDialog.setInterfaz(this);
			productoDialog.getProductos();
			productoDialog.limpiaDatosComplementarios();
			productoDialog.setVisible(true);
			break;
		case PROVEEDOR_CODE:
			proveedorDialog.setInterfaz(this);
			proveedorDialog.getProveedores();
			//proveedorDialog.limpiaDatosComplementarios();
			proveedorDialog.setVisible(true);
		default:
			break;
		}
	}
	
	

	@Override
	public void getEntity(Proveedor proveedor) {
		try {
			if(proveedor!=null) {
				tfCodigoProveedor.setText(proveedor.getId().toString());
				tfNombreProveedor.setText(proveedor.getRazonSocial());
			}
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el proveedor seleccionado, intente nuevamente!");
			// TODO: handle exception
		}
	}
	
	private void findProveedorById(Long id) {
		try {
			Optional<Proveedor> proveedor = proveedorService.findById(id);
			if (proveedor.isPresent()) {
				tfCodigoProveedor.setText(proveedor.get().getId().toString());
				tfNombreProveedor.setText(proveedor.get().getRazonSocial());
			} else {
				Notifications.showAlert("No existe Proveedor con el codigo informado.!");
			}
		} catch (Exception e) {
			Notifications.showAlert("Problemas en la busqueda, intente nuevamente!");
		}
	}
	
	private void setProducto(Producto producto) {
		try {
			if (producto != null) {
				
				
				tfProductoId.setText(String.valueOf(producto.getId()));
				tfDescripcion.setText(producto.getDescripcion());
				
			}
		} catch (Exception e) {
			Notifications.showAlert("Producto sin Stock, verifique datos del producto!");
		}
	}


	@Override
	public void getEntity(Producto producto) {
		try {
			setProducto(producto);
		} catch (Exception e) {
			Notifications.showAlert("Hubo problemas con el Producto, intente nuevamente!");
			// TODO: handle exception
		}
	}
}
