package py.com.prestosoftware.util;


import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.math.BigInteger;
import java.util.regex.Pattern;
import net.sf.jasperreports.engine.JRScriptletException;

public class MontoEnLetras extends JRDefaultScriptlet{
   
	private static final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
	private static final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",	    "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ", "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
	private static final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
	    "setecientos ", "ochocientos ", "novecientos "};
    
	public static String convertir(String numero, String punto,  String vacio, boolean mayusculas) {
        String literal = "";
        String parte_decimal;
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if (numero.indexOf(",") == -1) {
            numero = numero + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");
            //de da formato al numero decimal
            //parte_decimal = "y " + Num[1] + "/100 Soles.";
            //se convierte el numero a literal
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal ).toUpperCase();
            } else {
                return (literal );
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }
	


	/* funciones para convertir los numeros a literales */
	private static String getUnidades(String numero) {// 1 - 9
	    //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
	    String num = numero.substring(numero.length() - 1);
	    return UNIDADES[Integer.parseInt(num)];
	}

	private static String getDecenas(String num) {// 99 
		int n = Integer.parseInt(num); 
		if (n < 10) {//para casos como -> 01 - 09 
			return getUnidades(num); 
		} else if (n > 19) {//para 20...99 
			String u = getUnidades(num); 
			if (u.equals("")) { //para 20,30,40,50,60,70,80,90 
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8]; 
			} else { 
				if(n == 21) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 22) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 23) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 24) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 25) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 26) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 27) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 28) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;} 
				if(n == 29) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;} 
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u; 
			} 
		} else {//numeros entre 11 y 19 
			return DECENAS[n - 10];
		} 
	}

	private static String getCentenas(String num) {// 999 o 099
	    if (Integer.parseInt(num) > 99) {//es centena
	        if (Integer.parseInt(num) == 100) {//caso especial
	            return " cien ";
	        } else {
	            return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
	        }
	    } else {//por Ej. 099 
	        //se quita el 0 antes de convertir a decenas
	        return getDecenas(Integer.parseInt(num) + "");
	    }
	}

	private static String getMiles(String numero) {// 999 999
	    //obtiene las centenas
	    String c = numero.substring(numero.length() - 3);
	    //obtiene los miles
	    String m = numero.substring(0, numero.length() - 3);
	    String n = "";
	    //se comprueba que miles tenga valor entero
	    if (Integer.parseInt(m) > 0) {
	        n = getCentenas(m);
	        return n + "mil " + getCentenas(c);
	    } else {
	        return "" + getCentenas(c);
	    }

	}

	private static String getMillones(String numero) { //000 000 000        
	    //se obtiene los miles
	    String miles = numero.substring(numero.length() - 6);
	    //se obtiene los millones
	    String millon = numero.substring(0, numero.length() - 6);
	    String n = "";
	    if (Integer.parseInt(millon) > 0) {
	    	if (Integer.parseInt(millon) == 1) {
	    		n = getUnidades(millon) + "millon ";
	    	} else {
	    		n = getCentenas(millon) + "millones ";
	    	}
	    }
	    
	    return n + getMiles(miles);
	}

	private static String getBillones(String numero) { //000 000 000 000        
	    //se obtiene los miles
	    String miles = numero.substring(numero.length() - 9);
	    //se obtiene los millones
	    String millon = numero.substring(0, numero.length() - 9);
	    String n = "";
	    if (Integer.parseInt(millon) == 1) {
	    	n = getUnidades(millon) + "billon ";
	    } else {
	    	n = getCentenas(millon) + "billones ";
	    }
	    
	    return n + getMillones(miles);
	}

 
//	public static void main(String[] args) throws JRScriptletException {
//		//MontoEnLetras.Convertir(EXCEPTION_MESSAGE_KEY_PARAMETER_NOT_FOUND, EXCEPTION_MESSAGE_KEY_FIELD_NOT_FOUND, false)
//		System.out.println(MontoEnLetras.Convertir("458000",".","",true));
//		System.out.println(MontoEnLetras.Convertir("4580000",".","",true));
//		System.out.println(MontoEnLetras.Convertir("45800000",".","",true));
//		System.out.println(MontoEnLetras.Convertir("458000000",".","",true));
//		System.out.println(MontoEnLetras.Convertir("9000000",".","",true));
//		System.out.println(MontoEnLetras.Convertir("2007234",".","",true));
//	}
}