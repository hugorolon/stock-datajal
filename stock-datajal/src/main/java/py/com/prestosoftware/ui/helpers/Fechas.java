package py.com.prestosoftware.ui.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public class Fechas {
	
	public static Date stringToDate(Object object) {
		if(object.equals("") || object==null || object.equals("__/__/____")){
			return null;
		}
		
		String auxFecha[] = ((String) object).split("/");
		Integer dia = Integer.valueOf(auxFecha[0]);
		Integer mes = Integer.valueOf(auxFecha[1]);
		Integer anho = Integer.valueOf(auxFecha[2]);
		
		if (dia > 31||dia==0){
			JOptionPane.showMessageDialog(null, "El dia tiene que ser mayor que cero\n y menor que 32");
			return null;
		}	
		
		if (mes > 12||mes==0){
			JOptionPane.showMessageDialog(null, "El mes tiene que ser mayor que cero pero menor a 13");
			return null;
		}	
		
		if (anho<=1900 ){
			JOptionPane.showMessageDialog(null, "El a�o tiene que ser mayor que 1900");
			return null;
		}
		//Validacion de bisiestos
		Integer res=0; 	Integer var=0;
		
		if(anho>=2000) {
			var=anho-2000;
		} else {
			var=anho-1900;
		}
		
		res=var%4;
		
		if(res==0) {
			if(mes==2) {
				if(dia>29) {
					JOptionPane.showMessageDialog(null, "El dia tiene que ser mayor\nque cero y menor que 30");
					return null;
				}
			}
		} else {
			if (mes==2) {
				if (dia>28) {
					JOptionPane.showMessageDialog(null, "El dia tiene que ser mayor\nque cero y menor que 29");
					return null;
				}
			}
		}
		//Para otros meses
		if(mes==4||mes==6||mes==9||mes==11){
			if(dia>30){
				JOptionPane.showMessageDialog(null, "El dia tiene que ser mayor\nque cero y menor que 31");
				return null;
			}
		}
		
		Calendar auxCalendar = Calendar.getInstance();
		auxCalendar.set(Calendar.DATE, dia);
		auxCalendar.set(Calendar.MONTH, mes-1);
		auxCalendar.set(Calendar.YEAR, anho);
		
		return auxCalendar.getTime();	
	}
	
	public static String formatoHHmm(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		return formato.format(fecha);
	}
	
	
	public static String formatoDDMMAAAA(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy") ;		
		return formato.format(fecha);
	}
	
	public static String formatoDDMMAA(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy") ;		
		return formato.format(fecha);
	}
	
	public static String formatoAAAAMMDD(Date fecha) 	{
		SimpleDateFormat formato= new SimpleDateFormat("yyyy-MM-dd") ;	
		return formato.format(fecha);
	}
	
	public static String formatoAAMMDD(Date fecha){
		SimpleDateFormat formato= new SimpleDateFormat("yy_MM_dd") ;	
		return formato.format(fecha);
	}
	
	public static String formatoMMAAAA(Date fecha){
		SimpleDateFormat formato = new SimpleDateFormat("MM/yyyy") ;		
		return formato.format(fecha);
	}
	
	public static String formatoAAAA(Date fecha){
		SimpleDateFormat formato = new SimpleDateFormat("yyyy") ;		
		return formato.format(fecha);
	}
	
	public static Integer diferenciaFechasDias(String fechaInicio, String fechaFin){		
		String auxFechaInicio[] = ((String) fechaInicio).split("/");
		Integer diaInicio = Integer.valueOf(auxFechaInicio[0]);
		Integer mesInicio = Integer.valueOf(auxFechaInicio[1]);
		Integer anhoInicio = Integer.valueOf(auxFechaInicio[2]);
		GregorianCalendar date1=new GregorianCalendar(anhoInicio,mesInicio,diaInicio);
		
		String auxFechaFin[] = ((String) fechaFin).split("/");
		Integer diaFin = Integer.valueOf(auxFechaFin[0]);
		Integer mesFin = Integer.valueOf(auxFechaFin[1]);
		Integer anhoFin = Integer.valueOf(auxFechaFin[2]);
		GregorianCalendar date2=new GregorianCalendar(anhoFin,mesFin,diaFin);
		
		Integer diff = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);
		GregorianCalendar dateTemp=(GregorianCalendar)date1.clone();
		Integer sum=0;
		
		for(Integer i =0; i<diff; i++){
			sum+= dateTemp.isLeapYear(dateTemp.get(Calendar.YEAR))?366:365;
			dateTemp.add(Calendar.YEAR,1);
		}

		Integer numeroCorrectoDias= sum+ date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR) ;
		
		return numeroCorrectoDias;
	}
	
	public static Date sumarFecha(Integer dias, Integer semanas, Integer meses, String fechaOriginal) {
		String [] dataTemp = fechaOriginal.split("/");
		Calendar calendario = Calendar.getInstance();
		calendario.set(Integer.valueOf(dataTemp[2]), Integer.valueOf(dataTemp[1])- 1, Integer.valueOf(dataTemp[0]));
		calendario.add(Calendar.DATE, dias);
		calendario.add(Calendar.DATE, semanas * 7);
		calendario.add(Calendar.MONTH, meses);
		
		return calendario.getTime();
	}
	
	public static Date restarFecha(Integer dias, Integer semanas, Integer meses, String fechaOriginal) {
		String [] dataTemp = fechaOriginal.split("/");
		Calendar calendario = Calendar.getInstance();
		calendario.set(Integer.valueOf(dataTemp[2]), Integer.valueOf(dataTemp[1])- 1, Integer.valueOf(dataTemp[0]));
		calendario.add(Calendar.DATE, - dias);
		calendario.add(Calendar.DATE, - semanas*7);
		calendario.add(Calendar.MONTH, - meses);
		
		return calendario.getTime();
	}
	
	public static Date domingos(String fecha) {
		String [] dataTemp = fecha.split("/");
		Calendar calendario = Calendar.getInstance();
		calendario.set(Integer.valueOf(dataTemp[2]), Integer.valueOf(dataTemp[1])- 1, Integer.valueOf(dataTemp[0]));
		
		if(calendario.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			calendario.add(Calendar.DATE, 1);
		}
		
		return calendario.getTime();
	}
	
    public static Integer ultimoDiaDelMes(Integer mes, Integer anho) {//con fallas
    	Calendar cal = GregorianCalendar.getInstance();
    	cal.set(anho, mes-1, 1); // Los meses empiezan con 0
    	
    	return cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    }
    
    public static String diaSemana(Date fecha) {
        String[] dias={"Domingo","Lunes","Martes", "Miercoles","Jueves","Viernes","Sabado"};
        Calendar cal= Calendar.getInstance();
        cal.setTime(fecha);
        Integer numeroDia=cal.get(Calendar.DAY_OF_WEEK);
        
        return dias[numeroDia - 1];
    }
    
    public static String mes(Date fecha) {
    	String[] meses={"Enero","Febrero","Marzo", "Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    	SimpleDateFormat formato=new SimpleDateFormat("MM");
    	Integer mesInt=Integer.valueOf(formato.format(fecha));
    	
    	return meses[mesInt- 1];
    }
    
    public static Integer horaHH(String horaHHMMSS) {
    	return Integer.valueOf(horaHHMMSS.substring(0, horaHHMMSS.indexOf(":")));
    }
    
    public static Integer horaMM(String horaHHMMSS) {
    	return Integer.valueOf(horaHHMMSS.substring(3,5));
    }
    
    //Nuevas implementaciones
	private static java.sql.Date fechaSQL;
	private static java.util.Date fechaUtil;
	
	public static java.sql.Date stringDDMMAAAAADateSQL(String fecha){
		fechaSQL = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fechaSQL = new java.sql.Date(formatter.parse(fecha).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaSQL;
	}
	
	public static String dateSQLAStringDDMMAAAA(java.sql.Date fecha){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		String fechaString = df.format(fecha);
		
		return fechaString; 
	}

	public static java.util.Date dateSQLAdateUtil(java.sql.Date fechaSQL){
		java.util.Date utilDate = new java.util.Date(fechaSQL.getTime()); 
		return utilDate;
	}
	
	public static java.sql.Date dateUtilAdateSQL(java.util.Date fechaUtil){
		java.sql.Date dateSQL = new java.sql.Date(fechaUtil.getTime()); 
		return dateSQL;
	}
	
	public static java.util.Date stringDDMMAAAAADateUtil(String fecha){
		fechaUtil = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fechaUtil = new java.util.Date(formatter.parse(fecha).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaUtil;
	}
	
	public static String dateUtilAStringDDMMAAAA(java.util.Date fecha){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		String fechaString = df.format(fecha);
		return fechaString; 
	}
}
