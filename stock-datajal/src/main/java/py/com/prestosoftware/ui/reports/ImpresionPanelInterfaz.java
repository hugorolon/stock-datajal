package py.com.prestosoftware.ui.reports;

public interface ImpresionPanelInterfaz {

	void imprimirTicket();
	
	void imprimirNota(boolean impresora);
	
	void imprimirFactura(boolean impresora, boolean timbrado, String nroTimbrado);
	
	void cancelarImpresion();
	
	void cargaNumeroTimbrado(int numeroTimbrado);

}
