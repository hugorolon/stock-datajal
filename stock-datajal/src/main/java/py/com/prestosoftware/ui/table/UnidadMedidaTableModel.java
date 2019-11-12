package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class UnidadMedidaTableModel extends DefaultTableModel<UnidadMedida> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "Nombre", "Activo" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	UnidadMedida uMedida = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return uMedida.getNombre();
            case 1:
                return uMedida.getActivo();
            default:
                return "";
        }
    }
}
