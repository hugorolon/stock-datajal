package py.com.prestosoftware.ui.helpers;

import java.io.File;

public class GlobalVars {

	public static Long USER_ID = 1L;
	public static String USER = "";
	public static Long EMPRESA_ID = 1L;
	public static String EMPRESA = "";
	public static String EMPRESA_DIR = "";
	public static String EMPRESA_RUC = "";
	public static String EMPRESA_TEL = "";
	public static Long DEPOSITO_ID = 1L;
	public static String PATH_REPORT = File.pathSeparator+"server"+File.pathSeparator+"reportes";
	
	//MONEDA BASE
	public static Long BASE_MONEDA_ID;

	public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 350;
    
    //para el check en las grillas
  	public static Boolean esSeleccionado=false;
  	public static Boolean salirGrilla=false;
  	
  	//punto decimal
  	public static Boolean esPuntoDecimal=false;
	
}
