package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CiudadTableModel extends DefaultTableModel<Ciudad> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "Nombre", "Activo" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Ciudad ciudad = entities.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return ciudad.getNombre();
            case 1:
                return ciudad.getActivo();
            default:
                return "";
        }
    }
}
