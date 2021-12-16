package py.com.prestosoftware.ui.search;

import javax.transaction.Transactional;

import py.com.prestosoftware.data.models.Venta;

@Transactional
public interface VentaInterfaz {
	void getEntity(Venta v);
}