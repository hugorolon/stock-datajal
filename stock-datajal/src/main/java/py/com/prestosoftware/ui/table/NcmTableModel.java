package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class NcmTableModel extends DefaultTableModel<Ncm> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre",
                "Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Ncm ncm = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return ncm.getNombre();
            case 1:
                return ncm.getActivo();
            default:
                return "";
        }
    }
}
