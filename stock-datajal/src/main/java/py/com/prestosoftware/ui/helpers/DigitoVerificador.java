package py.com.prestosoftware.ui.helpers;

public class DigitoVerificador {
	
	public static int calcular(String ruc, int baseMax) {
	    int total, resto, k, aux, digito;
	    String v_numero_al = "";

	    for (int i = 0; i < ruc.length(); i++) {
	            char c = ruc.charAt(i);
	            if(Character.isDigit(c)) {
	                    v_numero_al += c;
	            } else {
	                    v_numero_al += (int) c;
	            }
	    }
	    k = 2;
	    total = 0;
	    for(int i = v_numero_al.length() - 1; i >= 0; i--) {
	            k = k > baseMax ? 2 : k;
	            aux = v_numero_al.charAt(i) - 48;
	            total += aux * k++;
	    }
	    resto = total % 11;
	    digito = resto > 1 ? 11 - resto : 0;
	    return digito;
	}
	
}
