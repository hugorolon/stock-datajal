package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.MovimientoItemIngreso;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MovimientoItemIngresoTableModel extends DefaultTableModel<MovimientoItemIngreso> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "ID.", "TIPO INGRESO", "DESCRIP.", "MONTO", "NUMERO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoItemIngreso item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getId();
            case 1:
                return item.getMiiIngreso();
            case 2:
                return item.getMiiMonto();
            case 3:
                return item.getMiiNumero();
            default:
                return "";
        }
    }
}
