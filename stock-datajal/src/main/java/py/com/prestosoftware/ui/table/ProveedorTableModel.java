package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ProveedorTableModel extends DefaultTableModel<Proveedor> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "CODIGO", "NOMBRE", "RAZÓN SOCIAL", "C.I./R.U.C", "TELEFONO", "EMAIL"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proveedor proveedor = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return proveedor.getId();
            case 1:
                return proveedor.getNombre();
            case 2:
                return proveedor.getRazonSocial();
            case 3:
                return proveedor.getRuc();
            case 4:
                return proveedor.getTelefono();
            case 5:
                return proveedor.getEmail();
            default:
                return "";
        }
    }
}
