package py.com.prestosoftware.ui.reports;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.util.MontoEnLetras;

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
	
	public static void performNota(String cliente, String ruc, String telefono, String direccion, String nroVenta,
			int condicion, String vendedor, String  total, List<VentaDetalle> items) {
		Double totalIva5=0d; Double totalIva10=0d;Double totalExenta=0d; Double subTotalIva5=0d;Double subTotalIva10=0d;
		try {
			for (VentaDetalle vd : items) {
				Double iva10 = (vd.getIva().intValue()==10?vd.getCantidad()*vd.getPrecio():0d);
				if(iva10>0) {
					vd.setIva10(FormatearValor.doubleAString(iva10));
					subTotalIva10=subTotalIva10+iva10;
					iva10= (double) Math.round(iva10/11);
					totalIva10=totalIva10+iva10;
				}else {
					vd.setIva10(FormatearValor.doubleAString(iva10));
				}
				Double iva5 = (vd.getIva().intValue()==5?vd.getCantidad()*vd.getPrecio():0d);
				if(iva5>0) {
					vd.setIva5(FormatearValor.doubleAString(iva5));
					subTotalIva5=subTotalIva5+iva5;
					iva5= (double)Math.round(iva5/21);
					totalIva5=totalIva5+iva5;
				}else {
					vd.setIva5(FormatearValor.doubleAString(iva5));
				}
				Double exentas = (vd.getIva().intValue()==0?vd.getCantidad()*vd.getPrecio():0d);
				vd.setExenta(FormatearValor.doubleAString(exentas));
				if(exentas>0)
					totalExenta=totalExenta+totalExenta;
				total=FormatearValor.doubleAString(totalIva5+totalIva10+exentas);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, String> parametros = new HashMap<String, String>();
		SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
		String fecha= sd.format(new Date());
		parametros.put("fecha", fecha);
	    parametros.put("clienteNombre", cliente);
	    parametros.put("clienteRucDv", ruc);
	    parametros.put("clienteCelular", telefono);
	    parametros.put("clienteDireccion", direccion);
	    parametros.put("comprobante", nroVenta);
	    
	    String condicionValue = "";
	    
	    if (condicion == 0) {
	    	parametros.put("contado", "X");
	    	parametros.put("credito", "");
	    	condicionValue = "CONTADO";
	    } else {
	    	parametros.put("credito", "X");
	    	parametros.put("contado", "");
	    	condicionValue = "CREDITO";
	    }
	    parametros.put("condicion", condicionValue);
	    parametros.put("empleadoNombre", vendedor);
	    parametros.put("subTotalExenta",FormatearValor.doubleAString(totalExenta));
	    parametros.put("subTotalIva5", FormatearValor.doubleAString(subTotalIva5));
	    parametros.put("subTotalIva10", FormatearValor.doubleAString(subTotalIva10));
	    parametros.put("totalIva5", FormatearValor.doubleAString(totalIva5));
	    parametros.put("totalIva10", FormatearValor.doubleAString(totalIva10));
	    parametros.put("totalIva", FormatearValor.doubleAString(totalIva5+totalIva10));
	    parametros.put("totalGeneral", total);
	    try {
			String ruta=new File("c:")+File.separator+"reportes"+File.separator+"notaInterna.jrxml";

	        		dataSourteReport(items, parametros, ruta);
//	    	}
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public static void performFactura(String cliente, String ruc, String telefono, String direccion, String nroVenta,
			int condicion, String vendedor, String  total, List<VentaDetalle> items, Date fechaImpresion) {
		Double totalIva5=0d; Double totalIva10=0d;Double totalExenta=0d; Double subTotalIva5=0d;Double subTotalIva10=0d;
		try {
			for (VentaDetalle vd : items) {
				Double iva10 = (vd.getIva().intValue()==10?vd.getCantidad()*vd.getPrecio():0d);
				if(iva10>0) {
					vd.setIva10(FormatearValor.doubleAString(iva10));
					subTotalIva10=subTotalIva10+iva10;
					iva10= (double) Math.round(iva10/11);
					totalIva10=totalIva10+iva10;
				}else {
					vd.setIva10(FormatearValor.doubleAString(iva10));
				}
				Double iva5 = (vd.getIva().intValue()==5?vd.getCantidad()*vd.getPrecio():0d);
				if(iva5>0) {
					vd.setIva5(FormatearValor.doubleAString(iva5));
					subTotalIva5=subTotalIva5+iva5;
					iva5= (double)Math.round(iva5/21);
					totalIva5=totalIva5+iva5;
				}else {
					vd.setIva5(FormatearValor.doubleAString(iva5));
				}
				Double exentas = (vd.getIva().intValue()==0?vd.getCantidad()*vd.getPrecio():0d);
				vd.setExenta(FormatearValor.doubleAString(exentas));
				if(exentas>0)
					totalExenta=totalExenta+totalExenta;
				//total= total + FormatearValor.doubleAString(Double.valueOf(vd.getIva10())+Double.valueOf(vd.getIva5())+Double.valueOf(vd.getExenta()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Map<String, String> parametros = new HashMap<String, String>();
		Locale espanol = new Locale("es","ES");
		SimpleDateFormat sd=new SimpleDateFormat("dd ' de ' MMMM ' de ' yyyy", espanol);
		String fecha= sd.format(fechaImpresion);
		parametros.put("fecha", fecha);
		parametros.put("montoEnLetras", MontoEnLetras.convertir(total,",","",true));
		parametros.put("clienteNombre", cliente);
	    parametros.put("clienteRucDv", ruc);
	    parametros.put("clienteCelular", telefono);
	    parametros.put("clienteDireccion", direccion);
	    parametros.put("comprobante", nroVenta);
	    
	    String condicionValue = "";
	    
	    if (condicion == 0) {
	    	parametros.put("contado", "X");
	    	parametros.put("credito", "");
	    	condicionValue = "CONTADO";
	    } else {
	    	parametros.put("credito", "X");
	    	parametros.put("contado", "");
	    	condicionValue = "CREDITO";
	    }
	    parametros.put("condicion", condicionValue);
	    parametros.put("empleadoNombre", vendedor);
	    parametros.put("subTotalExenta",FormatearValor.doubleAString(totalExenta));
	    parametros.put("subTotalIva5", FormatearValor.doubleAString(subTotalIva5));
	    parametros.put("subTotalIva10", FormatearValor.doubleAString(subTotalIva10));
	    parametros.put("totalIva5", FormatearValor.doubleAString(totalIva5));
	    parametros.put("totalIva10", FormatearValor.doubleAString(totalIva10));
	    parametros.put("totalIva", FormatearValor.doubleAString(totalIva5+totalIva10));
	    parametros.put("totalGeneral", total);
	    //parametrosObj.putAll(parametros);
	    try {
//	    	if (this.boletaRemision.compareTo("REMISION") == 0) {
//	    		parametros.put("totalLetras", VariablesGlobales.monedaSimbolo + " " + 
//	    				this.lbTotalGeneralLetras.getText());
//	    		dataSourteReport(lista, parametros, "remisionFactura");
//	    	} else {
//	    		parametros.put("totalLetras", VariablesGlobales.monedaSimbolo + " " + 
//	    				this.lbTotalGeneralLetras.getText());
			String ruta=new File("c:")+File.separator+"reportes"+File.separator+"facturaLegal.jrxml";

	        		dataSourteReport(items, parametros, ruta);
//	    	}
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	private static void dataSourteReport(List lista, Map parametros, String ruta) {
		try {
	        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(lista);
	        JasperDesign jasperDesign = JRXmlLoader.load(ruta);
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, beanColDataSource);
	        JasperPrintManager.printReport(jasperPrint, true);
			} catch (JRException e) {
				e.printStackTrace();
			} 
	}
	
}
