package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.MovimientoItemEgreso;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MovimientoItemEgresoTableModel extends DefaultTableModel<MovimientoItemEgreso> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "EGRESO", "DESCRIPCIÓN", "MONTO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoItemEgreso item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getMieEgreso();
            case 1:
                return item.getMieDescripcion();
            case 2:
                return item.getMieMonto();
            default:
                return "";
        }
    }
}
