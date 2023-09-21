package py.com.prestosoftware.ui.reports;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.ProductoDeposito;
import py.com.prestosoftware.data.models.ProductoPrecio;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;
import py.com.prestosoftware.util.ConnectionUtils;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@Component
public class InformeStockDeposito extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JTable tbDeposito;
	private JScrollPane scrollPaneDeposito;
	private JTable tbPrecioIva;
	private JScrollPane scrollPanePrecioIva;
	private JPanel pnlPrecioIva;
	private JPanel pnlDeposito;
	
	private List<Producto> productos;
	
	private ProductTableModel tableModel;
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private ProductoService service;
	private DepositoService depositoService;
	private JPanel pnlOrden;
	private JComboBox<String> cbOrden;
	private JLabel lblOrdenadoPor;
	private JPanel pnlEstiloInforme;
	private JLabel lblEstiloInforme;
	private JComboBox<String> cbEstiloInforme;
	private JCheckBox chkPrecioVenta;
	private JCheckBox chkProductosSinStock;
	private JCheckBox chkMontoAlFinal;
	private JCheckBox chkSolamenteServicios;
	private JButton btnPrevisualizar;
	private JButton btnImprimir;
	private JButton btnCancelar;
	private JCheckBox chkMarcaVerificacion;
	private JCheckBox chkPrecioCompra;
	private JCheckBox chkStock;
	
	@Autowired
	public InformeStockDeposito(ProductoService service, ProductTableModel tableModel, ProductoPrecioTableModel productoPrecioTableModel, 
			ProductoDepositoTableModel productoDepositoTableModel, DepositoService depositoService) {
		this.service = service;
		this.tableModel = tableModel;
		this.precioTableModel = productoPrecioTableModel;
		this.depositoTableModel = productoDepositoTableModel;
		this.depositoService = depositoService;
		
		setupUI();
		Util.setupScreen(this);
	}
	
	private void setupUI() {
		this.setTitle("CONSULTA SALDO DEPOSITOS");
		this.setSize(807, 500);
		getContentPane().setLayout(new MigLayout("", "[410.00px,grow][227.00,grow][180.00px]", "[179.00px,grow][36.00px][159.00px][21.00px][73.00px,grow]"));
		
		pnlDeposito = new JPanel();
		pnlDeposito.setBorder(new TitledBorder(null, "DEPOSITOS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlDeposito, "cell 0 0,grow");
		pnlDeposito.setLayout(null);
		
		scrollPaneDeposito = new JScrollPane();
		scrollPaneDeposito.setBounds(6, 18, 335, 106);
		pnlDeposito.add(scrollPaneDeposito);
		
		tbDeposito = new JTable(depositoTableModel);
		tbDeposito.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPaneDeposito.setViewportView(tbDeposito);
		
		JPanel pnlOpciones = new JPanel();
		pnlOpciones.setBorder(new TitledBorder(null, "OPCIONES DE IMPRESIÓN", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlOpciones, "cell 1 0 1 3,alignx left,growy");
		GridBagLayout gbl_pnlOpciones = new GridBagLayout();
		gbl_pnlOpciones.columnWidths = new int[]{0, 0};
		gbl_pnlOpciones.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlOpciones.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pnlOpciones.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlOpciones.setLayout(gbl_pnlOpciones);
		
		JCheckBox chkStock = new JCheckBox("Imprimir Stock");
		GridBagConstraints gbc_chkStock = new GridBagConstraints();
		gbc_chkStock.anchor = GridBagConstraints.NORTHWEST;
		gbc_chkStock.insets = new Insets(0, 0, 5, 0);
		gbc_chkStock.gridx = 0;
		gbc_chkStock.gridy = 0;
		pnlOpciones.add(chkStock, gbc_chkStock);
		
		chkPrecioCompra = new JCheckBox("Imprimir precio compra");
		GridBagConstraints gbc_chkPrecioCompra = new GridBagConstraints();
		gbc_chkPrecioCompra.insets = new Insets(0, 0, 5, 0);
		gbc_chkPrecioCompra.anchor = GridBagConstraints.NORTHWEST;
		gbc_chkPrecioCompra.gridx = 0;
		gbc_chkPrecioCompra.gridy = 1;
		pnlOpciones.add(chkPrecioCompra, gbc_chkPrecioCompra);
		
		chkPrecioVenta = new JCheckBox("Imprimir precio venta");
		GridBagConstraints gbc_chkPrecioVenta = new GridBagConstraints();
		gbc_chkPrecioVenta.anchor = GridBagConstraints.WEST;
		gbc_chkPrecioVenta.insets = new Insets(0, 0, 5, 0);
		gbc_chkPrecioVenta.gridx = 0;
		gbc_chkPrecioVenta.gridy = 2;
		pnlOpciones.add(chkPrecioVenta, gbc_chkPrecioVenta);
		
		chkProductosSinStock = new JCheckBox("Mostrar productos sin stock");
		GridBagConstraints gbc_chkProductosSinStock = new GridBagConstraints();
		gbc_chkProductosSinStock.anchor = GridBagConstraints.WEST;
		gbc_chkProductosSinStock.insets = new Insets(0, 0, 5, 0);
		gbc_chkProductosSinStock.gridx = 0;
		gbc_chkProductosSinStock.gridy = 3;
		pnlOpciones.add(chkProductosSinStock, gbc_chkProductosSinStock);
		
		chkMontoAlFinal = new JCheckBox("Totalizar monto al final");
		GridBagConstraints gbc_chkMontoAlFinal = new GridBagConstraints();
		gbc_chkMontoAlFinal.anchor = GridBagConstraints.WEST;
		gbc_chkMontoAlFinal.insets = new Insets(0, 0, 5, 0);
		gbc_chkMontoAlFinal.gridx = 0;
		gbc_chkMontoAlFinal.gridy = 4;
		pnlOpciones.add(chkMontoAlFinal, gbc_chkMontoAlFinal);
		
		chkSolamenteServicios = new JCheckBox("Imprimir solamente los servicios");
		GridBagConstraints gbc_chkSolamenteServicios = new GridBagConstraints();
		gbc_chkSolamenteServicios.insets = new Insets(0, 0, 5, 0);
		gbc_chkSolamenteServicios.anchor = GridBagConstraints.WEST;
		gbc_chkSolamenteServicios.gridx = 0;
		gbc_chkSolamenteServicios.gridy = 5;
		pnlOpciones.add(chkSolamenteServicios, gbc_chkSolamenteServicios);
		
		chkMarcaVerificacion = new JCheckBox("Imprimir marca de verificación");
		GridBagConstraints gbc_chkMarcaVerificacion = new GridBagConstraints();
		gbc_chkMarcaVerificacion.anchor = GridBagConstraints.WEST;
		gbc_chkMarcaVerificacion.gridx = 0;
		gbc_chkMarcaVerificacion.gridy = 6;
		pnlOpciones.add(chkMarcaVerificacion, gbc_chkMarcaVerificacion);
		
		btnPrevisualizar = new JButton("Pre visualizar");
		btnPrevisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preview();
			}
		});
		getContentPane().add(btnPrevisualizar, "cell 2 0,alignx center");
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		getContentPane().add(btnImprimir, "cell 2 1,alignx center");
		
		pnlPrecioIva = new JPanel();
		pnlPrecioIva.setBorder(new TitledBorder(null, "CATEGORIAS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlPrecioIva, "cell 0 2,grow");
		pnlPrecioIva.setLayout(null);
		
		scrollPanePrecioIva = new JScrollPane();
		scrollPanePrecioIva.setBounds(6, 18, 335, 93);
		pnlPrecioIva.add(scrollPanePrecioIva);
		
		tbPrecioIva = new JTable(precioTableModel);
		tbPrecioIva.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPanePrecioIva.setViewportView(tbPrecioIva);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancelar, "cell 2 2,alignx center");
		
		pnlOrden = new JPanel();
		pnlOrden.setBorder(new TitledBorder(null, "ORDENADO POR", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		getContentPane().add(pnlOrden, "cell 0 4,grow");
		lblOrdenadoPor = new JLabel("Seleccione Orden");
		pnlOrden.add(lblOrdenadoPor);
		cbOrden = new JComboBox<String>();
		cbOrden.setModel(new DefaultComboBoxModel(new String[] { "Codigo", "Nombre" }));
		cbOrden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		pnlOrden.add(cbOrden);
		
		pnlEstiloInforme = new JPanel();
		pnlEstiloInforme.setBorder(new TitledBorder(null, "ESTILO DEL INFORME", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlEstiloInforme, "cell 1 4,grow");
		
		lblEstiloInforme = new JLabel("Mostrar en el Informe");
		pnlEstiloInforme.add(lblEstiloInforme);
		
		cbEstiloInforme = new JComboBox<String>();
		cbEstiloInforme.setModel(new DefaultComboBoxModel(new String[] {"Agrupado por Categoría","Agrupado por Marca"}));
		cbEstiloInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		pnlEstiloInforme.add(cbEstiloInforme);
	}
	
	public void loadProductos(String name) {
		if (name.isEmpty()) {
			productos = service.findAllByNombre();
		} else {
			productos = service.findByNombre(name);	
		}
		
        tableModel.clear();
        tableModel.addEntities(productos);
    }
	
	private void getStockProductosByDeposito(Producto p) {
		depositoTableModel.clear();
		
		if (p != null) {
			String deposito = depositoService.findById(1L).get().getNombre();
			
			if (p.getDepO1() != null) {
				ProductoDeposito dep01 = new ProductoDeposito(deposito, p.getDepO1());
				depositoTableModel.addEntity(dep01);
				
				String deposito2 = depositoService.findById(2L).get().getNombre();
				if (p.getDepO2() != null) {
					ProductoDeposito dep02 = new ProductoDeposito(deposito2, p.getDepO2());
					depositoTableModel.addEntity(dep02);
				}
			}	
		}
	}
	//TODO Buscar por codigo y referencia
	private void getPreciosByProducto(Producto p) {
		precioTableModel.clear();
		
		if (p != null) {
			if (p.getPrecioVentaA() != null) {
				ProductoPrecio precio01 = new ProductoPrecio("Precio A", (p.getPrecioVentaA() != null ? p.getPrecioVentaA():0));
				precioTableModel.addEntity(precio01);
			
				if (p.getPrecioVentaB() != null) {
					ProductoPrecio precio02 = new ProductoPrecio("Precio B", (p.getPrecioVentaB() != null ? p.getPrecioVentaB():0));
					precioTableModel.addEntity(precio02);
					
					if (p.getPrecioVentaC() != null) {
						ProductoPrecio precio03 = new ProductoPrecio("Precio C", (p.getPrecioVentaC() != null ? p.getPrecioVentaC():0));
						precioTableModel.addEntity(precio03);
						
					}
				}
			}	
		}
	}
	

	private void preview() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String sqlCategoria="";
		String sqlMarca="";
		String tituloGrupo="";
		if(cbEstiloInforme.getSelectedItem().toString().equalsIgnoreCase("Agrupado por Marca")) {
			tituloGrupo="Marca :";
			sqlCategoria=" p.marca_id AS id_grupo, f.nombre AS grupo, ";
			sqlMarca=" p.categoria_id AS id_grupo1, a.nombre AS grupo1, ";
		}else {
			tituloGrupo="Categoría :";
			sqlCategoria=" p.categoria_id AS id_grupo, a.nombre AS grupo, ";
			sqlMarca=" p.marca_id AS id_grupo1, f.nombre AS grupo1, ";
		}
		parametros.put("tituloGrupo", tituloGrupo);
		parametros.put("sqlCategoria", sqlCategoria);
		parametros.put("sqlMarca", sqlMarca);
		String sqlCompra=" 0 ";
		String tituloCompra=" ";
		if(chkPrecioCompra.isSelected()) {
			sqlCompra="p.precio_costo ";
			tituloCompra="Precio Compra";
		}
		parametros.put("tituloCompra", tituloCompra);			
		
		String sqlStock=" ";
		if(!chkProductosSinStock.isSelected()) {
			sqlStock=" and p.dep_01 > 0 ";
		}
		String sql="select p.ID AS codigo, p.DESCRIPCION AS nombre, \n"
				+ sqlCategoria
				+ sqlMarca 
				+ sqlCompra+" AS compra, p.precio_venta_a, p.precio_venta_b, p.precio_venta_c, \n"
				+ "p.dep_01 as stock, 'GS' AS mone \n"
				+ "FROM productos p, categorias a, marcas f  \n"
				+ "	where p.categoria_id = a.id  AND p.marca_id=f.id "+ sqlStock;
		if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			sql = sql +" ORDER BY codigo asc";
		else
			sql = sql +" ORDER BY nombre asc";
		parametros.put("sql", sql);
		
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportStockPorDeposito.jrxml";
			JasperDesign jasperDesign = JRXmlLoader.load(ruta);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);
			
			JFrame frame = new JFrame();
			frame.setTitle("Visualizar Stock por deposito");
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
		if (cbOrden.getSelectedItem().toString().equalsIgnoreCase("Codigo"))
			orden = " order by 10, 4 asc";
		else
			orden = " order by 11, 4 asc";
		parametros.put("orden", orden);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportStockPorDeposito.jrxml";
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
}
