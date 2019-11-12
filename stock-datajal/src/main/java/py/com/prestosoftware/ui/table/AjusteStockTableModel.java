package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.AjusteStockDetalle;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class AjusteStockTableModel extends DefaultTableModel<AjusteStockDetalle> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "CODIGO", "CANTIDAD", "DESCRIPCION", "PRECIO", "TOTAL"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	AjusteStockDetalle item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getProductoId();
            case 1:
            	return item.getCantidadNueva();
            case 2:
            	return item.getProducto();
            case 3:
            	return item.getCosto();
            case 4:
            	return item.getSubtotalCosto();
            default:
                return "";
        }
    }
}
