package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.PresupuestoDetalle;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class PresupuestoTableModel extends DefaultTableModel<PresupuestoDetalle> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"COD.", "CANT.", "DESCRIP.", "PRECIO UNIT.", "PRECIO TOTAL"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PresupuestoDetalle item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getProductoId();
            case 1:
                return item.getCantidad();
            case 2:
                return item.getProducto();
            case 3:
                return item.getPrecio();
            case 4:
                return item.getSubtotal();
            default:
                return "";
        }
    }
}
