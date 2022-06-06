package py.com.prestosoftware.ui.search;

import javax.transaction.Transactional;

import py.com.prestosoftware.data.models.Devolucion;

@Transactional
public interface DevolucionInterfaz {
	void getEntity(Devolucion v);
}