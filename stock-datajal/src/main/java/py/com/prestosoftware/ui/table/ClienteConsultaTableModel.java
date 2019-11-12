package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ClienteConsultaTableModel extends DefaultTableModel<Cliente> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
        		"CODIGO", "NOMBRE", "SALDO", "DIA CREDITO", "DISPONIBLE"};
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
                return (client.getTotalDebito() != null ? client.getTotalDebito():0) 
                		- (client.getTotalCredito() != null ? client.getTotalCredito():0);
            case 3:
                return client.getDiaCredito();
            case 4:
                return client.getCreditoDisponible() != null ? client.getCreditoDisponible():0;
            default:
                return "";
        }
    }
}
