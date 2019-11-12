package py.com.prestosoftware.ui.helpers;

import java.util.Date;
import javax.swing.JOptionPane;

public class FechaAutomatica {
	
	private static int diaDigitado=0,mesDigitado=0,anhoDigitado=0,diaActual=0,mesActual=0,anhoActual=0;
	private static String fechaSaliente,fechaEntrante;
	@SuppressWarnings("deprecation")
	public static String calcular(String fechaEntranteParametro){
		fechaEntrante=fechaEntranteParametro;
		chequearDigitos();
		diaActual = new Date().getDate();
		mesActual = new Date().getMonth() + 1;
		anhoActual = new Date().getYear() + 1900;
		if ((diaDigitado > 0) && (mesDigitado == 0))
			fechaAPartirDelDia();
		else if ((mesDigitado > 0) && (anhoDigitado == 0))
			fechaAPartirDelMes();
		else if ((anhoDigitado > 0) && (anhoDigitado < 100) && (fechaEntranteParametro.length() == 10))
			fechaAPartirDelAnho();
		else if (diaDigitado == 0) {
			fechaDelSistema();
		}else{
			fechaSaliente=fechaEntrante;
		}
		diaDigitado = (mesDigitado = anhoDigitado = 0);
		String msj = esValidoFecha((Object)fechaSaliente);
		if (!msj.equals("VALIDO")) {
			JOptionPane.showMessageDialog(null, msj);
			return null;
		}
		return fechaSaliente;
		}
		private static void fechaDelSistema() { if (diaActual > 9) {
			if (mesActual > 9)
				fechaSaliente=diaActual + "/" + mesActual + "/" + anhoActual;
			else {
				fechaSaliente=diaActual + "/0" + mesActual + "/" + anhoActual;
			}
		}
		else if (mesActual > 9)
			fechaSaliente="0" + diaActual + "/" + mesActual + "/" + anhoActual;
		else
			fechaSaliente="0" + diaActual + "/0" + mesActual + "/" + anhoActual;
		}
		
		private static void chequearDigitos(){
//			if (Validar.esNumero(fechaEntrante.substring(0, 2))) {
//				diaDigitado = Integer.parseInt(fechaEntrante.substring(0, 2).trim());
//			}
//			if (Validar.esNumero(fechaEntrante.substring(3, 5).trim())) {
//				mesDigitado = Integer.parseInt(fechaEntrante.substring(3, 5).trim());
//			}
//			if ((fechaEntrante.length() == 10) && 
//				(Validar.esNumero(fechaEntrante.substring(6, 10).trim())))
//				anhoDigitado = Integer.parseInt(fechaEntrante.substring(6, 10).trim());
		}
		
		private static void fechaAPartirDelDia() {
			if (diaDigitado > 9) {
				if (mesActual > 9)
					fechaSaliente=diaDigitado + "/" + mesActual + "/" + anhoActual;
				else {
					fechaSaliente=diaDigitado + "/0" + mesActual + "/" + anhoActual;
				}
			}else if (mesActual > 9)
				fechaSaliente="0" + diaDigitado + "/" + mesActual + "/" + anhoActual;
			else
				fechaSaliente="0" + diaDigitado + "/0" + mesActual + "/" + anhoActual;
			}

			private static void fechaAPartirDelMes()
			{
				if (diaDigitado > 9) {
					if (mesDigitado > 9)
						fechaSaliente=diaDigitado + "/" + mesDigitado + "/" + anhoActual;
					else {
						fechaSaliente=diaDigitado + "/0" + mesDigitado + "/" + anhoActual;
					}
				}
				else if (mesDigitado > 9)
					fechaSaliente="0" + diaDigitado + "/" + mesDigitado + "/" + anhoActual;
				else
					fechaSaliente="0" + diaDigitado + "/0" + mesDigitado + "/" + anhoActual;
			}

			@SuppressWarnings("deprecation")
			private static void fechaAPartirDelAnho()
			{
				if ((anhoDigitado > new Date().getYear() - 100) && (anhoDigitado < 100))
					fechaSaliente=diaDigitado + "/" + mesDigitado + "/" + (anhoDigitado + 1900);
				else if (anhoDigitado <= new Date().getYear() - 100)
					fechaSaliente=diaDigitado + "/" + mesDigitado + "/" + (anhoDigitado + 2000);
			}
			public static String esValidoFecha(Object object){
				String auxFecha[] = ((String)object).split("/");
				int dia = Integer.parseInt(auxFecha[0]);
				int mes = Integer.parseInt(auxFecha[1]);
				int anho = Integer.parseInt(auxFecha[2]);
				
				if ((dia > 31) || (dia == 0))
				{
					return "El dia tiene que ser mayor que cero y menor que 32";
				}

				if ((mes > 12) || (mes == 0)) {
					return "El mes tiene que ser mayor que cero pero menor a 13";
				}

				int res = 0;
				int var = 0;
				if (anho >= 2000)
					var = anho - 2000;
				var = anho - 1900;
				res = var % 4;

				if (res == 0) {
					if ((mes == 2) && 
							(dia > 29)) {
						return "El dia tiene que ser mayor que cero y menor que 30";
					}

				}
				else if ((mes == 2) && 
						(dia > 28)) {
					return "El dia tiene que ser mayor que cero y menor que 29";
				}

				if (((mes == 4) || (mes == 6) || (mes == 9) || (mes == 11)) && 
						(dia > 30)) {
					return "El dia tiene que ser mayor que cero y menor que 31";
				}
 
				return "VALIDO";
			}
}
