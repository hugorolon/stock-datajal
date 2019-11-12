package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.ProductoDeposito;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ProductoDepositoTableModel extends DefaultTableModel<ProductoDeposito> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"Deposito", "Stock"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	ProductoDeposito item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getDeposito();
            case 1:
                return (Object) FormatearValor.doubleAString(item.getStock());
            default:
                return "";
        }
    }
}
