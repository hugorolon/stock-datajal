package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class EmpresaTableModel extends DefaultTableModel<Empresa> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre",
                "RUC",
                "Representante"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Empresa empresa = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return empresa.getNombre();
            case 1:
                return empresa.getRuc();
            case 2:
                return empresa.getRepresentante();
            default:
                return "";
        }
    }
}
