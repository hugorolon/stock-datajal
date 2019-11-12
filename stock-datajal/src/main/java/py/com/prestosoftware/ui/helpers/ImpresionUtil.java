package py.com.prestosoftware.ui.helpers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.PrintJob;
//import java.util.HashMap;
//import jasper.ConfiguracaoReport;
//import jasper.SwingExporterService;
//import java.io.File;
import java.util.List;
//import java.util.Map;
//import negocio.datasource.DataSource;
import javax.swing.JFrame;

import py.com.prestosoftware.data.models.VentaDetalle;

public class ImpresionUtil {
//	@SuppressWarnings("unchecked")
//	public static void imprimir(List lista,List listaAux,List listaAux2, Map parametros,String nombreReporte) {
//		try {
//			DataSource ds=new DataSource();
//			ds.setLista(lista);
//			ds.setListaAux(listaAux);
//			ds.setListaAux2(listaAux2);
//			ConfiguracaoReport cf = new ConfiguracaoReport();
//			cf.setRepositorioName(new File("reportes").getAbsolutePath());
//			cf.setReportName(nombreReporte);
//			cf.setBeanDataSource(ds);
//			cf.setParametros(parametros);
//			SwingExporterService.generateReport(cf, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static void imprimir(List lista, Map parametros,String nombreReporte) {
//		try {
//			DataSource ds=new DataSource();
//			ds.setLista(lista);
//			ConfiguracaoReport cf = new ConfiguracaoReport();
//			cf.setRepositorioName(new File("reportes").getAbsolutePath());
//			cf.setReportName(nombreReporte);
//			cf.setBeanDataSource(ds);
//			cf.setParametros(parametros);
//			SwingExporterService.generateReport(cf, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void generaTicket(String fecha, String cliente, String ruc, String ticketNro, String cajaNro,
			String total, String recibido, String vuelto, List<VentaDetalle> items) {
		JFrame f = new JFrame();
	    
	    PrintJob pjob = f.getToolkit().getPrintJob(f, 
	      "prueba", null);
	    
	    int a = 115;
	    
	    Graphics g = pjob.getGraphics();
	    
	    g.setColor(Color.black);
	    
	    g.setFont(new Font("Arial", 0, 8));
	    g.drawString("                   " + GlobalVars.EMPRESA + "                   ", 1, 10);
	    g.drawString("                                USO INTERNO                            ", 1, 20);
	    g.drawString("--------------------------------------------------------------------------------", 1, 25);
	    
	    g.drawString("  CLIENTE:  " + cliente, 1, 35);
	    g.drawString("  RUC:  " + ruc, 1, 45);
	    g.drawString("  FECHA: " + fecha, 1, 55);
	    g.drawString("  TICKET Nº: " + ticketNro, 1, 65);
	    
	    g.drawString("  CAJA:  " + cajaNro, 1, 75);
	    g.drawString("--------------------------------------------------------------------------------", 1, 80);
	    g.drawString("DESCRIPCIÓN      	  CANT.        PRECIO     SUB-TOTAL", 1, 95);
	    g.drawString("--------------------------------------------------------------------------------", 1, 105);
	    
	    g.setColor(Color.black);
	    
	    for (VentaDetalle item: items) {
	    	String codigo 	= String.valueOf(item.getProductoId());
	    	String cant 	= FormatearValor.doubleAString(item.getCantidad());
		    String precio 	= FormatearValor.doubleAString(item.getPrecio());
		    String subtotal = FormatearValor.doubleAString(item.getSubtotal());
		      
		    g.drawString(codigo, 1, a);
		    g.drawString(cant, 60, a + 20);
		    g.drawString(precio, 90, a + 20);
		    g.drawString(subtotal, 130, a + 20);
		    a += 40;
	    }
	    
	    g.setColor(Color.black);
	    g.setFont(new Font("Arial", 0, 8));
	    g.drawString("------------------------------------------------------------------------------------", 1, a + 20);
	    g.drawString("TOTAL A PAGAR:                       GS. " + total, 1, a + 40);
	    g.drawString("VALOR RECIBIDO:                     GS. " + recibido, 1, a + 60);
	    g.drawString("VALOR VUELTO:                        GS. " + vuelto, 1, a + 80);
	    g.drawString("=======================================================================", 1, a + 100);
	    g.drawString("                  GRACIAS POR SU PREFERENCIA..!                   ", 10, a + 120);
	    
	    g.dispose();
	    
	    pjob.end();	  
	}
}
