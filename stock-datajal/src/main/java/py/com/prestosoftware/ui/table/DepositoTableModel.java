package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class DepositoTableModel extends DefaultTableModel<Deposito> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "NOMBRE", "EMPRESA", "ACTIVO" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Deposito dep = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return dep.getNombre();
            case 1:
                return dep.getEmpresa().getNombre(); 
            case 2:
                return dep.getActivo() == 1 ? "SI":"NO";
            default:
                return "";
        }
    }
}
