package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ClientTableModel extends DefaultTableModel<Cliente> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
        		"Codigo", "Nombre", "Razon Social", "C.I. Nro./R.U.C", "Telefono",};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente client = entities.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            return client.getId();
            case 1:
                return client.getNombre();
            case 2:
                return client.getRazonSocial();
            case 3:
                return client.getCiruc();
            case 4:
                return client.getTelefono();
//            case 5:
//                return Fechas.formatoDDMMAAAA(client.getFechaRegistro());
            default:
                return "";
        }
    }
}
