package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ProductDCTableModel extends DefaultTableModel<Producto> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"CODIGO", "DESCRIPCION"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto product = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return product.getId();
            case 1:
                return product.getDescripcion();
            default:
                return "";
        }
    }
}
