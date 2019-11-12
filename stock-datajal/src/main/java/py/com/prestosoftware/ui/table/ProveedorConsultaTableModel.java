package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ProveedorConsultaTableModel extends DefaultTableModel<Proveedor> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "CODIGO", "NOMBRE", "SALDO", "DIAS CREDITO", "DISPONIBLE" };
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
            	return (proveedor.getTotalDebito() != null ? proveedor.getTotalDebito():0) 
                		- (proveedor.getTotalCredito() != null ? proveedor.getTotalCredito():0);
            case 3:
                return proveedor.getDiaCredito();
            case 4:
                return proveedor.getCreditoDisponible();
            default:
                return "";
        }
    }
}
