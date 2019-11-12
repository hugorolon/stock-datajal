package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MarcaTableModel extends DefaultTableModel<Marca> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre",
                "Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Marca marca = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return marca.getNombre();
            case 1:
                return marca.getActivo();
            default:
                return "";
        }
    }
}
