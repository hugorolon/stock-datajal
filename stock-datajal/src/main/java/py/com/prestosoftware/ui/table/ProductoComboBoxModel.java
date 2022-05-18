package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.ui.shared.DefaultComboBoxModel;

@Component
public class ProductoComboBoxModel extends DefaultComboBoxModel<Producto> {

	private static final long serialVersionUID = 1L;
}
