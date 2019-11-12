package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ListaPrecioTableModel extends DefaultTableModel<ListaPrecio> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre",
                "Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	ListaPrecio listaPrecio = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return listaPrecio.getNombre();
            case 1:
                return listaPrecio.getActivo();
            default:
                return "";
        }
    }
}
