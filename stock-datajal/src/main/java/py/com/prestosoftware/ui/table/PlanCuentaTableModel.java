package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class PlanCuentaTableModel extends DefaultTableModel<PlanCuenta> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "COD.", "CUENTA", "TIPO" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	PlanCuenta p = entities.get(rowIndex);

        switch (columnIndex) {
        	case 0:
        		return p.getId();
            case 1:
                return p.getNombre();
            case 2:
                return p.getTipo();
            default:
                return "";
        }
    }
}
