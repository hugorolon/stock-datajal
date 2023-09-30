package py.com.prestosoftware.ui.reports;

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
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.util.ConnectionUtils;
import py.com.prestosoftware.util.Notifications;

@Component
public class InformeResumenCajaDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton btnPrevisualizar;
	private JButton btnCancelar;

	private JXDatePicker tfFechaInicial;
	private JXDatePicker tfFechaFinal;
	private MovimientoCajaService service;
	
	private JComboBox<String> cbPeriodo;
	private JLabel lblNewLabel;
	private JLabel lblFechaInicio;
	private JLabel lblFechaFin;
	private JButton btnImprimir;

	@Autowired
	public InformeResumenCajaDialog(MovimientoCajaService service) {
		this.service = service;
		this.setSize(668, 288);
		this.setModal(true);
		this.setTitle("Informe Resumen Movimiento Ingreso/Egreso caja"); //$NON-NLS-1$

		getContentPane().setLayout(new BorderLayout());

		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		GridBagLayout gbl_pnlBuscador = new GridBagLayout();
		gbl_pnlBuscador.columnWidths = new int[] { 18, 88, 116, 119, 65, 0 };
		gbl_pnlBuscador.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0 };
		gbl_pnlBuscador.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_pnlBuscador.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlBuscador.setLayout(gbl_pnlBuscador);

		// pnlBuscador.add(tfFechaInicial);

		JPanel pnlBotonera = new JPanel();
		GridBagConstraints gbc_pnlBotonera = new GridBagConstraints();
		gbc_pnlBotonera.fill = GridBagConstraints.VERTICAL;
		gbc_pnlBotonera.gridheight = 6;
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

		lblNewLabel = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		pnlBuscador.add(lblNewLabel, gbc_lblNewLabel);

		GridBagConstraints gbc_cbPeriodo = new GridBagConstraints();
		gbc_cbPeriodo.insets = new Insets(0, 0, 5, 5);
		gbc_cbPeriodo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPeriodo.gridx = 2;
		gbc_cbPeriodo.gridy = 1;
		pnlBuscador.add(cbPeriodo, gbc_cbPeriodo);

		lblFechaInicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblNewLabel_1.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
		gbc_lblFechaInicio.anchor = GridBagConstraints.WEST;
		gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicio.gridx = 1;
		gbc_lblFechaInicio.gridy = 3;
		pnlBuscador.add(lblFechaInicio, gbc_lblFechaInicio);

		tfFechaInicial = new JXDatePicker();
		tfFechaInicial.setFormats("dd/MM/yyyy");
		// tfFechaInicial.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("CuentaRecibirDialog.textField.text"));
		// //$NON-NLS-1$ //$NON-NLS-2$
		GridBagConstraints gbc_tfFechaInicial = new GridBagConstraints();
		gbc_tfFechaInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfFechaInicial.insets = new Insets(0, 0, 5, 5);
		gbc_tfFechaInicial.gridx = 2;
		gbc_tfFechaInicial.gridy = 3;
		pnlBuscador.add(tfFechaInicial, gbc_tfFechaInicial);
		tfFechaInicial.setBounds(100, 100, 50, 50);
		tfFechaInicial.setDate(new Date());

		lblFechaFin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages") //$NON-NLS-1$
				.getString("CuentaRecibirDialog.lblFechaFin.text")); //$NON-NLS-1$
		GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
		gbc_lblFechaFin.anchor = GridBagConstraints.WEST;
		gbc_lblFechaFin.insets = new Insets(0, 0, 0, 5);
		gbc_lblFechaFin.gridx = 1;
		gbc_lblFechaFin.gridy = 5;
		pnlBuscador.add(lblFechaFin, gbc_lblFechaFin);

		tfFechaFinal = new JXDatePicker();
		tfFechaFinal.setFormats("dd/MM/yyyy");
		GridBagConstraints gbc_tfFechaInicial_1 = new GridBagConstraints();
		gbc_tfFechaInicial_1.anchor = GridBagConstraints.WEST;
		gbc_tfFechaInicial_1.insets = new Insets(0, 0, 0, 5);
		gbc_tfFechaInicial_1.gridx = 2;
		gbc_tfFechaInicial_1.gridy = 5;
		pnlBuscador.add(tfFechaFinal, gbc_tfFechaInicial_1);
		tfFechaFinal.setDate(new Date());

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize();
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	

	private void preview() {
		if(tfFechaInicial.getDate().after(tfFechaFinal.getDate())) {
			Notifications.showAlert("La fecha fin no puede ser menor que fecha Inicio!");
			return;	
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		Caja caja=new Caja();
		caja.setId((long) 1);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		Date fechaUltimoMovimientoAux=service.findLastDateMov(fechaIniSel);
		Date fechaUltimoMovimiento=(fechaUltimoMovimientoAux==null?fechaIniSel:fechaUltimoMovimientoAux);
		Optional<Double> ventaContado=service.totalVentaContado(fechaUltimoMovimiento);
		Optional<Double> otrosIng=service.totalOtrosIngresos(fechaUltimoMovimiento, caja);
		Double entradaAnterior = (ventaContado.get()==null?0:ventaContado.get()) + (otrosIng.get()==null?0:otrosIng.get());
		//Optional<Double> entradaAnterior= contado + otrosIng;
		Optional<Double> compraContado= service.totalCompraContado(fechaUltimoMovimiento, caja);
		Optional<Double> otrosEgr= service.totalOtrosEgresos(fechaUltimoMovimiento, caja);
		Double salidaAnterior =  (compraContado.get()==null?0:compraContado.get()) + (otrosEgr.get()==null?0:otrosEgr.get());
//		Optional<Double> entrada= service.totalEntrada(fechaIniSel,fechaFinSel, caja);
//		Optional<Double> salida= service.totalSalida(fechaIniSel,fechaFinSel, caja);
		Double saldoAnterior= (entradaAnterior - salidaAnterior);
		parametros.put("entradaAnterior", (entradaAnterior));
		parametros.put("salidaAnterior", salidaAnterior);
		parametros.put("saldoAnterior", saldoAnterior);
//		parametros.put("entrada", (entrada.isPresent()?entrada.get():0d));
//		parametros.put("salida", (salida.isPresent()?salida.get():0d));
//		Double saldo=(entrada.isPresent()?entrada.get():0d)-(salida.isPresent()?salida.get():0d);
//		parametros.put("saldo", saldo);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			//List lista=new ArrayList<Object>();
			//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(lista);
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportResumenCaja.jrxml";
			//inicio
			JasperDesign jasperDesign = JRXmlLoader.load(ruta);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);
			//fin
			//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
			JFrame frame = new JFrame();
			frame.setTitle("Visualizar Resumen de Caja");
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
		if(tfFechaInicial.getDate().after(tfFechaFinal.getDate())) {
			Notifications.showAlert("La fecha fin no puede ser menor que fecha Inicio!");
			return;	
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		Caja caja=new Caja();
		caja.setId((long) 1);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIniSel = tfFechaInicial.getDate();
		Date fechaFinSel = tfFechaFinal.getDate();
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		
	    df = new SimpleDateFormat("dd/MM/yyyy");
		fechaIniSel = tfFechaInicial.getDate();
		fechaFinSel = tfFechaFinal.getDate();
		parametros.put("fechaInicio", df.format(fechaIniSel));
		parametros.put("fechaFin", df.format(fechaFinSel));
		Date fechaUltimoMovimiento=service.findLastDateMov(fechaIniSel);
		Optional<Double> ventaContado=service.totalVentaContado(fechaIniSel);
		Optional<Double> otrosIng=service.totalOtrosIngresos(fechaIniSel, caja);
		Double entradaAnterior = (ventaContado.get()==null?0:ventaContado.get()) + (otrosIng.get()==null?0:otrosIng.get());
		//Optional<Double> entradaAnterior= contado + otrosIng;
		Optional<Double> compraContado= service.totalCompraContado(fechaUltimoMovimiento, caja);
		Optional<Double> otrosEgr= service.totalOtrosEgresos(fechaUltimoMovimiento, caja);
		Double salidaAnterior =  (compraContado.get()==null?0:compraContado.get()) + (otrosEgr.get()==null?0:otrosEgr.get());
//		Optional<Double> entrada= service.totalEntrada(fechaIniSel,fechaFinSel, caja);
//		Optional<Double> salida= service.totalSalida(fechaIniSel,fechaFinSel, caja);
		Double saldoAnterior= (entradaAnterior - salidaAnterior);
		parametros.put("entradaAnterior", (entradaAnterior));
		parametros.put("salidaAnterior", salidaAnterior);
		parametros.put("saldoAnterior", saldoAnterior);
//		parametros.put("entrada", (entrada.isPresent()?entrada.get():0d));
//		parametros.put("salida", (salida.isPresent()?salida.get():0d));
//		Double saldo=(entrada.isPresent()?entrada.get():0d)-(salida.isPresent()?salida.get():0d);
//		parametros.put("saldo", saldo);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			//List lista=new ArrayList<Object>();
			//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(lista);
			String ruta = new File("reportes").getAbsolutePath() + File.separator
					+ "reportResumenCaja.jrxml";
			//inicio
			JasperDesign jasperDesign = JRXmlLoader.load(ruta);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conn);
			JasperPrintManager.printReport(jasperPrint, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
