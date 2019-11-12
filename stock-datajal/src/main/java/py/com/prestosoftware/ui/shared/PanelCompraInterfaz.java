package py.com.prestosoftware.ui.shared;

import org.springframework.stereotype.Component;

@Component
public interface PanelCompraInterfaz {
	
	void goToCompraLocal();
	
	void goToCompraImportacion();
	
	void goToCompraConsiganada();
	
	void goToPedidoCompra();

}
