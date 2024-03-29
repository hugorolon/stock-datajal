package py.com.prestosoftware.ui.helpers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public class FormatearValor {
	
	public static String doubleAString(Double valor) {
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
	    simbolo.setDecimalSeparator(',');
	    simbolo.setGroupingSeparator('.');
	    DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo); 
	    
	    return formateador.format(valor);
	}
	
	public static Double stringToDouble(String valor) {
		try {
			if(valor!="" && valor.length()>0) {
				DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
				simbolo.setDecimalSeparator(',');
				simbolo.setGroupingSeparator('.');
				DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo); 
				Number numero = formateador.parse(valor);
				return numero.doubleValue();				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	public static Double stringToDoubleFormat(String valor) {
		try {
			DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
			simbolo.setDecimalSeparator('.');
			simbolo.setGroupingSeparator(',');
			DecimalFormat formateador = new DecimalFormat("###,###.##",simbolo); 
			Number numero = formateador.parse(valor);
			return numero.doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String formatearValor(Double valor) {
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
		simbolo.setDecimalSeparator(',');
		simbolo.setGroupingSeparator('.');
		DecimalFormat formateador = new DecimalFormat("###,###,##0");
		
		return formateador.format(valor);
	}
	
	public static Double desformatearValor(String valor) {
		try {
			DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
			simbolo.setDecimalSeparator(',');
			simbolo.setGroupingSeparator('.');
			DecimalFormat formateador = new DecimalFormat("###,###,##0");
			Number numero = formateador.parse(valor);
			
			return Double.valueOf(numero.doubleValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return Double.MIN_VALUE;
	}
	
	public static String bigDecimalAStringDosDecimales(BigDecimal valor) {
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
		simbolo.setDecimalSeparator(',');
		simbolo.setGroupingSeparator('.');
		NumberFormat formatter = new DecimalFormat("###,###.##",simbolo);
		
		return formatter.format(valor);
	}
	
	public static String bigDecimalAStringTresDecimales(BigDecimal valor){
		DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
		simbolo.setDecimalSeparator(',');
		simbolo.setGroupingSeparator('.');
		NumberFormat formatter = new DecimalFormat("###,###.###",simbolo);
		return formatter.format(valor);
	}
	
	public static BigDecimal stringABigDecimal(String valor) {
		String formatoValido = valor.replace(".", "").replace(",", ".");
		
		return new BigDecimal(formatoValido);
	}
	
	public static Double stringADouble(String valor) {
		String formatoValido = valor.replace(".", "").replace(",", ".");
		
		return new Double(formatoValido);
	}
	
	public static Double stringADoubleFormat(String valor) {
		String formatoValido = valor.replace(".", "").replace(",", ".");
		
		return new Double(formatoValido);
	}

	public static String sinSeparadorDeMiles(String valor) {
		String formatoValido = valor.replace(".", "");
	    return formatoValido;
	}

	public static double parseDecimal(String input) throws ParseException{
		  NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		  ParsePosition parsePosition = new ParsePosition(0);
		  Number number = numberFormat.parse(input, parsePosition);

		  if(parsePosition.getIndex() != input.length()){
		    throw new ParseException("Invalid input", parsePosition.getIndex());
		  }

		  return number.doubleValue();
		}
	
}
