package py.com.prestosoftware.util;

/**
 *
 * @author kelcom
 */
public class MontoEnLetras {
   
   private static final int  Unidad = 1;
   private static final int  Decena = 10;
   private static final int  Centena = 100;
    
  public static String convertir(String monto, String separadorDecimal, String moneda,boolean esMayuscula ){
       
      String  V[] = initVector(), s = "", z = "", c = "", espacio = " ", t;
      int l = monto.length(), k = monto.indexOf( separadorDecimal ), u = 1, n = 0, j = 0, b = 0, d, p, r;
      
      try{
            if( k >= 0 ) { c = monto.substring( k + 1, l );  l = k; }
            
            if ( l <= 15 )
            {
                        for( int i = l ; i >= 1; i-- ){
                            d = Integer.parseInt( String.valueOf( monto.charAt( i - 1 ) )); 
                            n = d * u + n;

                                switch( u ){
                                    case Unidad: 
                                        s = V[ n ]; 
                                        if ( i == l && n == 1 ) b++;
                                    break;
                                    case Decena:                                                
                                        p = d - 2;
                                    
                                        if( p < 0 )   
                                            s = V[ n ];                                      
                                        else{ 
                                            t =  V[ 20 + p ];
                                            
                                            if( n % 10 != 0 )
                                               s  =  (d == 2)? "veinti" + s : t + " y " + s;
                                            else    
                                               s = t;
                                         }
                                    break;
                                    case Centena:
                                       p = d - 1; 
                                       t = V[ 30 + p ];
         
                                       if( n % 100  == 0 ) 
                                       { s = ""; espacio = ""; }  
                                       else 
                                         if( d == 1 ) t += "to";  
                                       
                                       s = t + espacio + s;                                       
                                       z = ( s + z );   
                                    break;          
                                }  
                                
                                espacio = " ";     
                              //ini. calcula los miles, millones, billones
                                r = l - i;                                
                                if( r > 0 && r % 3 == 0  ){
                                        p = ( r > 10 )?  2 : j++ & 1;     
                                        t = V[ 40 + p ];
                                        
                                        if( p > 0 )
                                           if( ( n == 1 && i > 1 ) || n > 1  ) t += "es";
                                        z = espacio + t + espacio + z;
                                        	//z = espacio + t + z;
                                }
                              //fin.
                                
                                // reiniciar variables
                               if ( u == Centena ){  u = 1;  n = 0;  s = "";  } else u *= 10;                                 
                         } 
                       
             }      
           //ini. adiciona la moneda y los centavos
                if ( !c.equals("") && !c.equals("00")) c = " con " + c + " centavos";            
                if ( !moneda.equals("") )        
                	moneda = " " + moneda; 
                else
//                    if( b > 0 ) z += "o";//orig
                	if( b > 0 ) z += "";  
           //fin. 
           //si es cero la parte de centavos lo oculta
                if ( !c.equals("") && !c.equals("00"))
                	z = ( s + z ) + moneda + c;
                else
                	z = ( s + z ) + moneda;
      }
      catch(NumberFormatException ex){
            z = "ERROR [readNumber]: Formato numerico incorrecto.";
      }
     //si se recibe true como parametro, se convierte todo a mayuscula
     if(esMayuscula){
    	 z=z.toUpperCase();
     }
     z=cortarExpresion(z);
     return z;
     
   }
 
   private static String[] initVector(  ){
       String V[] = new String[43];
        
        V[0] = "cero";
        V[1] = "un";
        V[2] = "dos";
        V[3] = "tres";
        V[4] = "cuatro";
        V[5] = "cinco";
        V[6] = "seis";
        V[7] = "siete";
        V[8] = "ocho";
        V[9] = "nueve";
        V[10] = "diez";
        V[11] = "once";
        V[12] = "doce";
        V[13] = "trece";
        V[14] = "catorce";
        V[15] = "quince";
        V[16] = "dieciseis";
        V[17] = "diecisiete";
        V[18] = "dieciocho";
        V[19] = "diecinueve";
        V[20] = "veinte";
        V[21] = "treinta";
        V[22] = "cuarenta";
        V[23] = "cincuenta";
        V[24] = "secenta";
        V[25] = "setenta";
        V[26] = "ochenta";
        V[27] = "noventa";
        V[28] = "";
        V[29] = "";
        V[30] = "cien";
        V[31] = "doscientos";
        V[32] = "trescientos";
        V[33] = "cuatrocientos";
        V[34] = "quinientos";
        V[35] = "seiscientos";
        V[36] = "setecientos";
        V[37] = "ochocientos";
        V[38] = "novecientos";
        V[39] = "";
        V[40] = "mil";
        V[41] = "millon";
        V[42] = "billon";
        
        return V;
   }
   
   private static String cortarExpresion(String expresionAnterior) {
	   //Separa la cadena en un vector
	 	String[] array = expresionAnterior.split(" ");
		String resultado="";
		for (int i = 0; i < array.length; i++) {
			String palabra=array[i];
			if(palabra.equals(""))//por el tema del doble espacio q queda
				i++;
			//procesa a partir de la segunda palabra
			if(i>0){
				//compara si el anteriorpa es MILLON y el actual espa MIL"
				if((array[i-1].toUpperCase().equals("MILLON") || array[i-1].toUpperCase().equals("MILLONES")) && array[i].toUpperCase().equals("MIL")){
					palabra=palabra.replace("MIL", "");
				}
			}
			//VA CREANDO UNA NUEVA CADENA POR CADA CICLO
			resultado+=palabra+" ";
		}
		return resultado;
   }
 
//	public static void main(String[] args) {
	//	System.out.println(MontoEnLetras.convertir("2000000.00",".","",true));
	//	System.out.println(MontoEnLetras.convertir("1000000.00",".","",true));
	//	System.out.println(MontoEnLetras.convertir("9999800000000.00",".","",true));
	//	System.out.println(MontoEnLetras.convertir("999999999999999.00",".","",true));
//	}
}
