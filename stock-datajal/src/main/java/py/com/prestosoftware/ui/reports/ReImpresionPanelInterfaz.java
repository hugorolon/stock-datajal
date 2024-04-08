package py.com.prestosoftware.ui.reports;

import java.util.Date;

public interface ReImpresionPanelInterfaz {

	void cargaFecha(Date fecha);
	
	void imprimirTicket();
	
	void imprimirNota(boolean impresora);
	
	void imprimirFactura(boolean impresora);
	
	void cancelarImpresion();

}
