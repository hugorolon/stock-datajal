package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CajaTableModel extends DefaultTableModel<Caja> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "NOMBRE", "EMPRESA", "ACTIVO"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Caja caja = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return caja.getNombre();
            case 1:
                return caja.getEmpresa().getNombre();
            case 2:
                return caja.getActivo() == 1 ? "SI":"NO";
            default:
                return "";
        }
    }
}
