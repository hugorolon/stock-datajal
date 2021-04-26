package py.com.prestosoftware.ui.reports;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;

public class ImpresionUtil {

	public static void performTicket(List<VentaDetalle> items, String condicion, String ventaId, String total) {
		JFrame f = new JFrame();
	    
	    PrintJob pjob = f.getToolkit().getPrintJob(f, 
	      "ticket", null);
	    
	    int a = 115;
	    
	    Graphics g = pjob.getGraphics();
	    
	    g.setColor(Color.black);
	    
	    g.setFont(new Font("Arial", 0, 8));
	    g.drawString("                   " + GlobalVars.EMPRESA + "                   ", 1, 10);
	    g.drawString("                                USO INTERNO                            ", 1, 20);
	    g.drawString("--------------------------------------------------------------------------------", 1, 25);
	    
	    g.drawString("  DIRECCIÓN:  " + GlobalVars.EMPRESA_DIR, 1, 35);
	    g.drawString("  CONDICIÓN:  " + condicion, 1, 45);
	    g.drawString("  FECHA: " + Fechas.formatoDDMMAAAA(new Date()), 1, 55);
	    g.drawString("  TICKET Nº: " + ventaId, 1, 65);
	    
	    g.drawString("  CAJA:  001 ", 1, 75);
	    g.drawString("--------------------------------------------------------------------------------", 1, 80);
	    g.drawString("DESCRIPCIÓN      	  CANT.        PRECIO     SUB-TOTAL", 1, 95);
	    g.drawString("--------------------------------------------------------------------------------", 1, 105);
	    
	    g.setColor(Color.black);
	    
	    for (VentaDetalle vd : items) {
	      String cant = FormatearValor.doubleAString(vd.getCantidad());
	      String precio = FormatearValor.doubleAString(vd.getPrecio());
	      String subtotal = FormatearValor.doubleAString(vd.getSubtotal());
	      
	      g.drawString((String) vd.getProducto(), 1, a);
	      g.drawString(cant, 60, a + 20);
	      g.drawString(precio, 90, a + 20);
	      g.drawString(subtotal, 130, a + 20);
	      a += 40;
	      
	    }
	    
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", 0, 8));
	    g.drawString("------------------------------------------------------------------------------------", 1, a + 20);
	    g.drawString("TOTAL A PAGAR:                       GS. " + total, 1, a + 40);
//	    g.drawString("VALOR RECIBIDO:                     GS. " + this.tfRecibido.getText(), 1, a + 60);
//	    g.drawString("VALOR VUELTO:                        GS. " + this.lblVuelto_1.getText(), 1, a + 80);
	    g.drawString("=======================================================================", 1, a + 100);
	    g.drawString("                  GRACIAS POR LA PREFERENCIA..!                   ", 10, a + 120);
	    
	    g.dispose();
	    
	    pjob.end();
	}
	
	public static void performNota(List<VentaDetalle> items, String condicion, String ventaId, String total) {
		JFrame f = new JFrame();
	    
	    PrintJob pjob = f.getToolkit().getPrintJob(f, 
	      "nota", null);
	    
	    int a = 115;
	    
	    Graphics g = pjob.getGraphics();
	    
	    g.setColor(Color.black);
	    
	    g.setFont(new Font("Arial", 0, 8));
	    g.drawString("                   " + GlobalVars.EMPRESA + "                   ", 1, 10);
	    g.drawString("                                NOTA DE INTERNO                            ", 1, 20);
	    g.drawString("--------------------------------------------------------------------------------------------", 1, 25);
	    
	    g.drawString("  DIRECCIÓN:  " + GlobalVars.EMPRESA_DIR, 1, 35);
	    g.drawString("  CONDICIÓN:  " + condicion, 1, 45);
	    g.drawString("  FECHA: " + Fechas.formatoDDMMAAAA(new Date()), 1, 55);
	    g.drawString("  TICKET Nº: " + ventaId, 1, 65);
	    
	    g.drawString("  CAJA:  001 ", 1, 75);
	    g.drawString("--------------------------------------------------------------------------------------------", 1, 80);
	    g.drawString("DESCRIPCIÓN      	  			                  CANT.        	   PRECIO            SUB-TOTAL", 1, 95);
	    g.drawString("--------------------------------------------------------------------------------------------", 1, 105);
	    
	    g.setColor(Color.black);
	    
	    for (VentaDetalle vd : items) {
	      String cant = FormatearValor.doubleAString(vd.getCantidad());
	      String precio = FormatearValor.doubleAString(vd.getPrecio());
	      String subtotal = FormatearValor.doubleAString(vd.getSubtotal());
	      
	      g.drawString((String) vd.getProducto(), 1, a);
	      g.drawString(cant, 90, a + 20);
	      g.drawString(precio, 120, a + 20);
	      g.drawString(subtotal, 150, a + 20);
	      a += 40;
	      
	    }
	    
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", 0, 8));
	    g.drawString("--------------------------------------------------------------------------------------------", 1, a + 20);
	    g.drawString("TOTAL A PAGAR:                       GS. " + total, 1, a + 40);
//	    g.drawString("VALOR RECIBIDO:                     GS. " + this.tfRecibido.getText(), 1, a + 60);
//	    g.drawString("VALOR VUELTO:                        GS. " + this.lblVuelto_1.getText(), 1, a + 80);
	    g.drawString("============================================================================================", 1, a + 100);
	    g.drawString("                  				GRACIAS POR SU PREFERENCIA..!                   ", 10, a + 120);
	    
	    g.dispose();
	    
	    pjob.end();
	}
	
	public static void performFactura(String cliente, String ruc, String telefono, String direccion, String nroVenta,
			int condicion, String vendedor, String  total, String totalIva5, String totalIva10, 
			String totalExenta, String subTotalIva5, String subTotalIva10, List<VentaDetalle> items) {
		Map<String, Object> parametrosObj = new HashMap<String, Object>();
		parametrosObj.put("items", items);
		Map<String, String> parametros = new HashMap<String, String>();
	    parametros.put("clienteNombre", cliente);
	    parametros.put("clienteRucDv", ruc);
	    parametros.put("clienteCelular", telefono);
	    parametros.put("clienteDireccion", direccion);
	    parametros.put("comprobante", nroVenta);
	    parametros.put("numero", nroVenta);
	    
	    String condicionValue = "";
	    
	    if (condicion == 0) {
	    	parametros.put("contado", "X");
	    	condicionValue = "CONTADO";
	    } else {
	    	parametros.put("credito", "X");
	    	condicionValue = "CREDITO";
	    }
	    
//	    if (VariablesGlobales.codMoneda.compareTo("2") == 0)
//	      parametros.put("dolares", "X");
//	    else
//	      parametros.put("guaranies", "X");
	    
	    parametros.put("condicion", condicionValue);
	    //parametros.put("fecha", Fechas.formatoDDMMAAAA(new Date()));
	    parametros.put("empleadoNombre", vendedor);
	    parametros.put("subTotalExenta", totalExenta);
	    parametros.put("subTotalIva5", subTotalIva5);
	    parametros.put("subTotalIva10", subTotalIva10);
	    parametros.put("totalIva5", totalIva5);
	    parametros.put("totalIva10", totalIva10);
	    parametros.put("totalIva", String.valueOf( FormatearValor.stringADouble(totalIva5) + FormatearValor.stringADouble(totalIva10)) );
	    parametros.put("totalGeneral", total);
	    parametrosObj.putAll(parametros);
	    try {
//	    	if (this.boletaRemision.compareTo("REMISION") == 0) {
//	    		parametros.put("totalLetras", VariablesGlobales.monedaSimbolo + " " + 
//	    				this.lbTotalGeneralLetras.getText());
//	    		dataSourteReport(lista, parametros, "remisionFactura");
//	    	} else {
//	    		parametros.put("totalLetras", VariablesGlobales.monedaSimbolo + " " + 
//	    				this.lbTotalGeneralLetras.getText());
	        		dataSourteReport(items, parametrosObj, "facturaLegal");
//	    	}
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private static void dataSourteReport(List lista, Map parametros, String nombreReporte) {
		try {
			String ruta=new File("reportes").getAbsolutePath()+File.separator+nombreReporte+".jrxml";
			
	        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(lista);

	        JasperDesign jasperDesign = JRXmlLoader.load(ruta);
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, beanColDataSource);
	        JasperExportManager.exportReportToPdfFile(jasperPrint, "ganttchart.pdf");
	        
			
//			DataSource ds=new DataSource();
//			ds.setLista(lista);
//			ConfiguracaoReport cf = new ConfiguracaoReport();
//			cf.setRepositorioName(new File("reportes").getAbsolutePath());
//			cf.setReportName(nombreReporte);
//			cf.setBeanDataSource(ds);
//			cf.setParametros(parametros);
//			SwingExporterService.generateReport(cf, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
