package py.com.prestosoftware.ui.search;

import javax.transaction.Transactional;

import py.com.prestosoftware.data.models.VentaTemporal;

@Transactional
public interface VentaTemporalInterfaz {
	void getEntity(VentaTemporal v);
}