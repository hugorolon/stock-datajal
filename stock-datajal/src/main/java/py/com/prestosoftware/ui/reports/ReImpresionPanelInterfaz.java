package py.com.prestosoftware.ui.reports;

import java.util.Date;

public interface ReImpresionPanelInterfaz {

	void cargaFecha(Date fecha);
	
	void imprimirNota();
	
	void imprimirFactura();
	
	void cancelarImpresion();

}
