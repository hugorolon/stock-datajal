package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Color;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ColorTableModel extends DefaultTableModel<Color> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre",
                "Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Color c = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getActivo();
            default:
                return "";
        }
    }
}
