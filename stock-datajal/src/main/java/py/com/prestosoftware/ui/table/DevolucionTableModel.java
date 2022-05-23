package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.DevolucionDetalle;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class DevolucionTableModel extends DefaultTableModel<DevolucionDetalle> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"COD.", "CANT.", "DESC.", "VALOR", "TOTAL", "CANT. DEV."};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	DevolucionDetalle item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getProductoId();
            case 1:
                return item.getCantidad();
            case 2:
                return item.getProducto();
            case 3:
                return item.getCosto();
            case 4:
                return item.getSubtotal();
            case 5:
                return item.getCantidaddev();    
            default:
                return "";
        }
    }
}
