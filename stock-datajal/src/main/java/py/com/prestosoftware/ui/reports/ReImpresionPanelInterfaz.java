package py.com.prestosoftware.ui.reports;

import java.util.Date;

public interface ReImpresionPanelInterfaz {

	void cargaFecha(Date fecha);
	
	void imprimirNota(boolean impresora);
	
	void imprimirFactura(boolean impresora, boolean timbrado, String nroTimbrado);
	
	void cancelarImpresion();
	
	void cargaNumeroTimbrado(int numeroTimbrado);

}
