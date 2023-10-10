package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CategoriaTableModel extends DefaultTableModel<Categoria> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "ID", "Nombre", "Activo" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria categoria = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
            	return categoria.getId();
            case 1:
                return categoria.getNombre();
            case 2:
                return categoria.getActivo();
            default:
                return "";
        }
    }
}
