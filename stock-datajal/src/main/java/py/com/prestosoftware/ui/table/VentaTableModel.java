
package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class VentaTableModel extends DefaultTableModel<Venta> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
        		"COD.", "FECHA", "CLIENTE", "TOTAL", "CONDICION", "ESTADO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Venta v = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return v.getId();
            case 1:
                return v.getFecha();
            case 2:
                return v.getClienteNombre();
            case 3:
                return v.getTotalGeneral();
            case 4:
                return v.getCondicion();
            case 5:
                return v.getSituacion();
            default:
                return "";
        }
    }
}
