package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class TamanhoTableModel extends DefaultTableModel<Tamanho> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre",
                "Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Tamanho tamanho = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return tamanho.getNombre();
            case 1:
                return tamanho.getActivo();
            default:
                return "";
        }
    }
}
