package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.ProductoPrecio;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ProductoPrecioTableModel extends DefaultTableModel<ProductoPrecio> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "Lista Precio", "Valor" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	ProductoPrecio product = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return product.getListaPrecio();
            case 1:
                return (Object) FormatearValor.doubleAString(product.getValor());
            default:
                return "";
        }
    }
}
