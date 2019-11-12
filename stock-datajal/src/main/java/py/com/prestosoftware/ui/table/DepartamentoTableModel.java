package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class DepartamentoTableModel extends DefaultTableModel<Departamento> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"Nombre","Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Departamento departamento = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return departamento.getNombre();
            case 1:
                return departamento.getActivo();
            default:
                return "";
        }
    }
}
